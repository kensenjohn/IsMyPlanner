package com.events.common.email.creator;

import com.events.bean.common.email.*;
import com.events.bean.event.EventBean;
import com.events.bean.event.EventRequestBean;
import com.events.bean.event.EventResponseBean;
import com.events.bean.event.guest.*;
import com.events.bean.event.guest.response.GuestWebResponseBean;
import com.events.bean.event.guest.response.WebRespRequest;
import com.events.bean.event.guest.response.WebRespResponse;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.common.*;
import com.events.common.email.setting.AccessEventEmail;
import com.events.common.email.setting.EventEmailFeature;
import com.events.data.email.EmailSchedulerData;
import com.events.data.email.EmailServiceData;
import com.events.event.AccessEvent;
import com.events.event.guest.AccessGuest;
import com.events.event.guest.GuestWebResponse;
import com.events.vendors.AccessVendors;
import com.events.vendors.website.AccessVendorWebsite;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailCreatorBaseService extends MailCreatorBase {

    EmailCreator emailCreator;
    private static final Logger emailerLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);
    private EmailServiceData emailServiceData = new EmailServiceData();
    private Configuration emailerConfig = Configuration.getInstance(Constants.EMAILER_PROP);
    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String sApplicationDomain = applicationConfig.get(Constants.APPLICATION_DOMAIN);
    private String sProtocol = applicationConfig.get(Constants.PROP_LINK_PROTOCOL,"http");

    public EmailCreatorBaseService() { }

    public EmailCreatorBaseService(MailCreator emailCreator) {
        this.emailCreator = (EmailCreator) emailCreator;
    }

    public void invokeEmailCreator() {
        if(emailCreator!=null) {
            Long lCurrentTime =  DateSupport.getEpochMillis();

            // First identify all old emails from previous run which did not finish.
            // Mark them as error so that they dont get picked up again.
            // These emails should be sent again.
            ArrayList<EmailSchedulerBean> arrOldSchedulerBean = getOldEmailSchedules(lCurrentTime);
            if(arrOldSchedulerBean!=null && !arrOldSchedulerBean.isEmpty()) {
                for(EmailSchedulerBean emailOldScheduleBean : arrOldSchedulerBean )
                {
                    emailOldScheduleBean.setScheduleStatus( Constants.SCHEDULER_STATUS.ERROR.getStatus()  );
                    this.emailCreator.update( emailOldScheduleBean );
                }
            }

            // Get all new emails that are scheduled to be sent.
            ArrayList<EmailSchedulerBean> arrNewSchedulerBean = getNewEmailSchedules(lCurrentTime);
            if(arrNewSchedulerBean!=null && !arrNewSchedulerBean.isEmpty())  {
                for(EmailSchedulerBean emailNewScheduleBean : arrNewSchedulerBean ) {
                    ArrayList<EmailObject> arrEmailObject = createEmailObject(emailNewScheduleBean);
                    if(arrEmailObject!=null && !arrEmailObject.isEmpty() ) {
                        for( EmailObject emailObject : arrEmailObject  ) {
                            this.emailCreator.create( emailObject , emailNewScheduleBean );
                        }
                    }
                }
            }

        } else {
            emailerLogging.debug("Email Creator Object was null");
        }
    }

    public ArrayList<EmailSchedulerBean> getOldEmailSchedules(Long lCurrentTime ) {
        EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
        Long lScheduleTime = DateSupport.subtractTime( lCurrentTime , ParseUtil.sToI(emailerConfig.get(Constants.PROP_EMAIL_SCHEDULE_PICKUPTIME_PADDING)), Constants.TIME_UNIT.MINUTES );
        ArrayList<EmailSchedulerBean> arrSchedulerBean = emailSchedulerData.getArrEmailSchedule(lScheduleTime - (lCurrentTime - lScheduleTime ), lScheduleTime, Constants.SCHEDULER_STATUS.NEW_SCHEDULE, Constants.SCHEDULE_PICKUP_TYPE.OLD_RECORDS);

        return arrSchedulerBean;
    }

    public ArrayList<EmailSchedulerBean> getNewEmailSchedules( Long lCurrentTime )  {
        EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
        Long lScheduleTime = DateSupport.subtractTime( lCurrentTime , ParseUtil.sToI( emailerConfig.get(Constants.PROP_EMAIL_SCHEDULE_PICKUPTIME_PADDING) ) , Constants.TIME_UNIT.MINUTES );

        ArrayList<EmailSchedulerBean> arrSchedulerBean = emailSchedulerData.getArrEmailSchedule(lScheduleTime, lCurrentTime+(lCurrentTime-lScheduleTime),  Constants.SCHEDULER_STATUS.NEW_SCHEDULE,Constants.SCHEDULE_PICKUP_TYPE.NEW_RECORDS);

        return arrSchedulerBean;
    }

    public ArrayList<EmailObject> createEmailObject( EmailSchedulerBean emailNewScheduleBean ) {
        ArrayList<EmailObject> arrEmailObject = new ArrayList<EmailObject>();
        if(emailNewScheduleBean != null ) {
            EventEmailBean eventEmailBean =  getEventEmailBean(emailNewScheduleBean.getEventEmailId());
            if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId())){
                arrEmailObject = getEmailObjects( emailNewScheduleBean , eventEmailBean );
            }  else {
                emailerLogging.info( " Invalid Event Email Bean ID :  " + ParseUtil.checkNullObject(eventEmailBean)   +  ParseUtil.checkNullObject(emailNewScheduleBean));
            }
        } else  {
            emailerLogging.info( "Invalid Email Schedule Bean. Unable to create the Email Object" );
        }

        return arrEmailObject;
    }
    private  EmailTemplateBean getEmailTemplateBean(String sTemplateId) {
        EmailTemplateBean emailTemplateBean = new EmailTemplateBean();
        if( !Utility.isNullOrEmpty( sTemplateId ) ) {
            emailTemplateBean = emailServiceData.getEmailTemplateById(sTemplateId);
        }
        return emailTemplateBean;
    }

    private ArrayList<EmailObject> getEmailObjects( EmailSchedulerBean emailScheduleBean ,EventEmailBean eventEmailBean ) {
        ArrayList<EmailObject> arrEmailObject = new ArrayList<EmailObject>();

        AccessEventEmail accessEventEmail = new AccessEventEmail();
        EventEmailFeatureBean tmpSendEmailRuleFeatureBean = accessEventEmail.getEventEmailFeatureTypeBean( Constants.EventEmailFeatureType.send_email_rule );
        tmpSendEmailRuleFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId() );

        EventEmailFeature eventEmailFeature = new EventEmailFeature();
        EventEmailFeatureBean sendEmailRuleFeatureBean = eventEmailFeature.getFeature(tmpSendEmailRuleFeatureBean);

        arrEmailObject = getProcessedEmailObject(emailScheduleBean, eventEmailBean, sendEmailRuleFeatureBean);
        return arrEmailObject;
    }
    private ArrayList<EmailObject> getEmailObjects( EmailSchedulerBean emailScheduleBean , EmailTemplateBean emailTemplateBean ) {
        ArrayList<EmailObject> arrEmailObject = new ArrayList<EmailObject>();
        if( emailScheduleBean!=null && emailTemplateBean!=null ) {
            if( Constants.EMAIL_TEMPLATE.RSVP_CONFIRMATION_EMAIL.getEmailTemplate().equalsIgnoreCase( emailTemplateBean.getTemplateName() )) {
                //arrEmailObject.add( getRSVPEmailObject(emailScheduleBean, emailTemplateBean) );
            } else if( Constants.EMAIL_TEMPLATE.SEATING_CONFIRMATION_EMAIL.getEmailTemplate().equalsIgnoreCase( emailTemplateBean.getTemplateName() )) {
                //arrEmailObject = getSeatingConfirmationEmailObject(emailScheduleBean, emailTemplateBean);
            } else if( Constants.EMAIL_TEMPLATE.RSVPRESPONSE.getEmailTemplate().equalsIgnoreCase( emailTemplateBean.getTemplateName() ) ||
                    Constants.EMAIL_TEMPLATE.RSVPRESPONSEDEMO.getEmailTemplate().equalsIgnoreCase( emailTemplateBean.getTemplateName() )) {
                //arrEmailObject = getRsvpResponseEmail(emailScheduleBean , emailTemplateBean );
            }
        }
        return arrEmailObject;
    }

    private  ArrayList<EmailObject>  getProcessedEmailObject(EmailSchedulerBean emailScheduleBean ,EventEmailBean eventEmailBean, EventEmailFeatureBean sendEmailRuleFeatureBean ) {
        ArrayList<EmailObject> arrEmailObject = new ArrayList<EmailObject>();
        ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup = getGuests(eventEmailBean,sendEmailRuleFeatureBean);
        if(eventEmailBean!=null && arrEveryEventGuestGroup!=null && !arrEveryEventGuestGroup.isEmpty()) {


            for(EveryEventGuestGroupBean everyEventGuestGroupBean : arrEveryEventGuestGroup ){
                Map<String, Object> mapGuestValues = getReplacementMapForGuestInfo(eventEmailBean,everyEventGuestGroupBean);
                String sEmailSubject =  ParseUtil.checkNull(eventEmailBean.getSubject());
                String sEmailHtmlBody =  ParseUtil.checkNull(eventEmailBean.getHtmlBody());
                String sEmailTextBody =  ParseUtil.checkNull(eventEmailBean.getTextBody());

                MustacheFactory mf = new DefaultMustacheFactory();
                Mustache mustacheText =  mf.compile(new StringReader(sEmailTextBody), eventEmailBean.getEventEmailId()+"_text");
                Mustache mustacheHtml = mf.compile(new StringReader(sEmailHtmlBody),  eventEmailBean.getEventEmailId()+"_html");
                Mustache mustacheSubject = mf.compile( new StringReader(sEmailSubject),  eventEmailBean.getEventEmailId()+"_subject" );


                StringWriter txtWriter = new StringWriter();
                StringWriter htmlWriter = new StringWriter();
                StringWriter subjectWriter = new StringWriter();
                try {
                    mustacheText.execute(txtWriter, mapGuestValues).flush();
                    mustacheHtml.execute(htmlWriter, mapGuestValues).flush();
                    mustacheSubject.execute(subjectWriter, mapGuestValues ).flush();
                } catch (IOException e) {
                    txtWriter = new StringWriter();
                    htmlWriter = new StringWriter();
                    subjectWriter = new StringWriter();
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                EmailObject emailObject = new EmailQueueBean();
                emailObject.setEmailSubject(subjectWriter.toString() );
                emailObject.setTextBody(txtWriter.toString());

                emailObject.setFromAddress( eventEmailBean.getFromAddressEmail() );
                emailObject.setFromAddressName( eventEmailBean.getFromAddressEmail() );

                GuestRequestBean guestRequestBean = new GuestRequestBean();
                guestRequestBean.setEventId( eventEmailBean.getEventId() );
                guestRequestBean.setGuestGroupId( everyEventGuestGroupBean.getGuestGroupId() );

                AccessGuest accessGuest = new AccessGuest();
                GuestResponseBean guestResponseBean = accessGuest.loadGuest(guestRequestBean);

                ArrayList<GuestGroupEmailBean> arrGuestGroupEmailBean = guestResponseBean.getArrGuestGroupEmailBean();
                if(arrGuestGroupEmailBean!=null && !arrGuestGroupEmailBean.isEmpty()){
                    String sGuestId = Constants.EMPTY;
                    for( GuestGroupEmailBean guestGroupEmailBean : arrGuestGroupEmailBean  )  {
                        emailObject.setToAddress(guestGroupEmailBean.getemailId());
                        emailObject.setToAddressName(guestGroupEmailBean.getemailId());
                        sGuestId = guestGroupEmailBean.getGuestId();
                    }
                    String tmpEmailHtmlBody = htmlWriter.toString() + "<img src=\""+sProtocol+"://"+sApplicationDomain+"/i.gif?_ek="+eventEmailBean.getEventEmailId()+"&_gk="+sGuestId+"\" width=1 height=1 style=\"display:none;\"/>";


                    emailObject.setHtmlBody(tmpEmailHtmlBody);
                    emailObject.setStatus( Constants.EMAIL_STATUS.NEW.getStatus() );
                    arrEmailObject.add(emailObject);
                }
            }

        }
        return arrEmailObject;
    }

    private Map<String, Object> getReplacementMapForGuestInfo( EventEmailBean eventEmailBean, EveryEventGuestGroupBean everyEventGuestGroupBean) {
        Map<String, Object> mapGuestValues = new HashMap<String, Object>();
        if(eventEmailBean!=null && everyEventGuestGroupBean!=null) {
            GuestResponseBean guestResponseBean = getGuest(eventEmailBean, everyEventGuestGroupBean);
            GuestBean guestBean = guestResponseBean.getGuestBean();



            mapGuestValues.put("GUEST_GROUP_NAME", ParseUtil.checkNull(everyEventGuestGroupBean.getGroupName()));
            mapGuestValues.put("GUEST_GIVEN_NAME", ParseUtil.checkNull(Utility.getGivenName(guestBean.getFirstName() , guestBean.getLastName() )));
            mapGuestValues.put("GUEST_FIRST_NAME", ParseUtil.checkNull(guestBean.getFirstName()));
            mapGuestValues.put("GUEST_LAST_NAME", ParseUtil.checkNull(guestBean.getLastName()));

            WebRespRequest webRespRequest = new WebRespRequest();
            webRespRequest.setEventId( eventEmailBean.getEventId() );
            webRespRequest.setGuestGroupId( guestBean.getGuestGroupId() );
            webRespRequest.setGuestWebResponseType( Constants.GUEST_WEBRESPONSE_TYPE.RSVP);
            GuestWebResponse guestWebResponse = new GuestWebResponse();
            WebRespResponse webRespResponse = guestWebResponse.createGuestWebResponse( webRespRequest );
            emailerLogging.info(" webRespResponse : " + webRespResponse);

            if(webRespResponse!=null && webRespResponse.getGuestWebResponseBean()!=null && !Utility.isNullOrEmpty(webRespResponse.getGuestWebResponseBean().getGuestWebResponseId()) ) {
                GuestWebResponseBean guestWebResponseBean =  webRespResponse.getGuestWebResponseBean();


                String sResetDomain = ParseUtil.checkNull(applicationConfig.get(Constants.DOMAIN));
                if(sResetDomain!=null && !"".equalsIgnoreCase(sResetDomain)) {

                    EventRequestBean eventRequestBean = new EventRequestBean();
                    eventRequestBean.setEventId( eventEmailBean.getEventId() );

                    AccessEvent accessEvent = new AccessEvent();
                    EventResponseBean eventResponseBean = accessEvent.getEventInfo(eventRequestBean);
                    if(eventResponseBean!=null && !Utility.isNullOrEmpty(eventResponseBean.getEventId())) {

                        EventBean eventBean = eventResponseBean.getEventBean() ;
                        VendorRequestBean vendorRequestBean = new VendorRequestBean();
                        vendorRequestBean.setVendorId( eventBean.getVendorId() );

                        AccessVendors accessVendors = new AccessVendors();
                        VendorBean vendorBean = accessVendors.getVendor( vendorRequestBean );

                        AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
                        VendorWebsiteFeatureBean vendorWebsiteFeatureBean = accessVendorWebsite.getSubDomain( vendorBean );
                        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
                            sResetDomain = vendorWebsiteFeatureBean.getValue() + "." + sResetDomain;
                        }
                        String sProtocol = applicationConfig.get(Constants.PROP_LINK_PROTOCOL,"http");

                        guestWebResponseBean.setLinkDomain( sProtocol + "://" + sResetDomain );
                    }

                }
                mapGuestValues.put("GUEST_RSVP_LINK", guestWebResponseBean.getLinkDomain() +"/r/rsvp.jsp?id="+guestWebResponseBean.getLinkId() );
            }
        }
        return mapGuestValues;
    }

    private String createLinkTrackers(String emailBody, String eventEmailId, String sGuestId){
        String finalEmailBody = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(emailBody)&& !Utility.isNullOrEmpty(eventEmailId) && !Utility.isNullOrEmpty(sGuestId)) {
            boolean isParseComplete = false;
            while(!isParseComplete) {
                int hrefPosition = emailBody.indexOf("href");
                if(hrefPosition<0 ){
                    isParseComplete = true;
                }

            }
        }
        return finalEmailBody;
    }

    private ArrayList<EveryEventGuestGroupBean> getGuests(EventEmailBean eventEmailBean, EventEmailFeatureBean sendEmailRuleFeatureBean){

        ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup = new ArrayList<EveryEventGuestGroupBean>();
        if( sendEmailRuleFeatureBean!=null) {
            String sSendEmailRule = ParseUtil.checkNull(sendEmailRuleFeatureBean.getValue()) ;
            if( Constants.SEND_EMAIL_RULES.ALL_INVITED.getRule().equalsIgnoreCase(sSendEmailRule)
                    || Constants.SEND_EMAIL_RULES.DID_NOT_RESPOND.getRule().equalsIgnoreCase(sSendEmailRule)
                    || Constants.SEND_EMAIL_RULES.ALL_WHO_RESPONDED.getRule().equalsIgnoreCase(sSendEmailRule)
                    || Constants.SEND_EMAIL_RULES.WILL_ATTEND.getRule().equalsIgnoreCase(sSendEmailRule)
                    || Constants.SEND_EMAIL_RULES.WILL_NOT_ATTEND.getRule().equalsIgnoreCase(sSendEmailRule) )  {
                GuestRequestBean guestRequestBean = new GuestRequestBean();
                guestRequestBean.setEventId(eventEmailBean.getEventId());

                AccessGuest accessGuest = new AccessGuest();
                GuestResponseBean guestResponseBean = accessGuest.loadEveryEventsGuests(guestRequestBean);

                emailerLogging.info(" sSendEmailRule : " + sSendEmailRule + "Every guest : " + guestResponseBean + " ");
                if( guestResponseBean!=null && !guestResponseBean.getArrEveryEventGuestGroup().isEmpty() ) {
                    ArrayList<EveryEventGuestGroupBean> tmpArrEveryEventGuestGroup =  guestResponseBean.getArrEveryEventGuestGroup();
                    if( Constants.SEND_EMAIL_RULES.ALL_INVITED.getRule().equalsIgnoreCase(sSendEmailRule) ){
                        arrEveryEventGuestGroup = tmpArrEveryEventGuestGroup;
                        emailerLogging.info("All invited guests invoked : ");
                    } else if( Constants.SEND_EMAIL_RULES.DID_NOT_RESPOND.getRule().equalsIgnoreCase(sSendEmailRule) ) {
                        for( EveryEventGuestGroupBean everyEventGuestGroupBean : tmpArrEveryEventGuestGroup  ) {
                            if(!everyEventGuestGroupBean.isHasResponded()) {
                                tmpArrEveryEventGuestGroup.add( everyEventGuestGroupBean );
                            }
                        }
                    } else if( Constants.SEND_EMAIL_RULES.ALL_WHO_RESPONDED.getRule().equalsIgnoreCase(sSendEmailRule) ) {
                        for( EveryEventGuestGroupBean everyEventGuestGroupBean : tmpArrEveryEventGuestGroup  ) {
                            if(everyEventGuestGroupBean.isHasResponded()) {
                                tmpArrEveryEventGuestGroup.add( everyEventGuestGroupBean );
                            }
                        }
                    } else if( Constants.SEND_EMAIL_RULES.WILL_ATTEND.getRule().equalsIgnoreCase(sSendEmailRule) ) {
                        for( EveryEventGuestGroupBean everyEventGuestGroupBean : tmpArrEveryEventGuestGroup  ) {
                            if(everyEventGuestGroupBean.isHasResponded() && !everyEventGuestGroupBean.isWillNotAttend()) {
                                tmpArrEveryEventGuestGroup.add( everyEventGuestGroupBean );
                            }
                        }
                    }  else if( Constants.SEND_EMAIL_RULES.WILL_NOT_ATTEND.getRule().equalsIgnoreCase(sSendEmailRule) ) {
                        for( EveryEventGuestGroupBean everyEventGuestGroupBean : tmpArrEveryEventGuestGroup  ) {
                            if(everyEventGuestGroupBean.isHasResponded() && everyEventGuestGroupBean.isWillNotAttend()) {
                                tmpArrEveryEventGuestGroup.add( everyEventGuestGroupBean );
                            }
                        }
                    }

                }
            }
        }
        return arrEveryEventGuestGroup;
    }

    private String replaceGuestText(String sSourceText, EventEmailBean eventEmailBean, EveryEventGuestGroupBean everyEventGuestGroupBean ){
        if(!Utility.isNullOrEmpty(sSourceText)) {
            GuestResponseBean guestResponseBean = getGuest(eventEmailBean, everyEventGuestGroupBean);
            GuestBean guestBean = guestResponseBean.getGuestBean();


            Map<String, Object> mapGuestValues = new HashMap<String, Object>();
            mapGuestValues.put("GUEST_GROUP_NAME", ParseUtil.checkNull(everyEventGuestGroupBean.getGroupName()));
            mapGuestValues.put("GUEST_GIVEN_NAME", ParseUtil.checkNull(Utility.getGivenName(guestBean.getFirstName() , guestBean.getLastName() )));
            mapGuestValues.put("GUEST_FIRST_NAME", ParseUtil.checkNull(guestBean.getFirstName()));
            mapGuestValues.put("GUEST_LAST_NAME", ParseUtil.checkNull(guestBean.getLastName()));

            if( sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_FIRST_NAME.toString()) ||
                    sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_LAST_NAME.toString()) ) {
                sSourceText = replaceGuestBeanTemplates(sSourceText,guestBean );
            }

            if( sSourceText.contains(  Constants.EMAIL_TEMPLATE_TEXT.GUEST_RSVP_LINK.toString() )) {
                WebRespRequest webRespRequest = new WebRespRequest();
                webRespRequest.setEventId( eventEmailBean.getEventId() );
                webRespRequest.setGuestGroupId( guestBean.getGuestGroupId() );
                webRespRequest.setGuestWebResponseType( Constants.GUEST_WEBRESPONSE_TYPE.RSVP);
                GuestWebResponse guestWebResponse = new GuestWebResponse();
                WebRespResponse webRespResponse = guestWebResponse.createGuestWebResponse( webRespRequest );
                emailerLogging.info(" webRespResponse : " + webRespResponse);
                if(webRespResponse!=null && webRespResponse.getGuestWebResponseBean()!=null && !Utility.isNullOrEmpty(webRespResponse.getGuestWebResponseBean().getGuestWebResponseId()) ) {
                    GuestWebResponseBean guestWebResponseBean =  webRespResponse.getGuestWebResponseBean();


                    String sResetDomain = ParseUtil.checkNull(applicationConfig.get(Constants.DOMAIN));
                    if(sResetDomain!=null && !"".equalsIgnoreCase(sResetDomain)) {

                        EventRequestBean eventRequestBean = new EventRequestBean();
                        eventRequestBean.setEventId( eventEmailBean.getEventId() );

                        AccessEvent accessEvent = new AccessEvent();
                        EventResponseBean eventResponseBean = accessEvent.getEventInfo(eventRequestBean);
                        if(eventResponseBean!=null && !Utility.isNullOrEmpty(eventResponseBean.getEventId())) {

                            EventBean eventBean = eventResponseBean.getEventBean() ;
                            VendorRequestBean vendorRequestBean = new VendorRequestBean();
                            vendorRequestBean.setVendorId(eventBean.getVendorId());

                            AccessVendors accessVendors = new AccessVendors();
                            VendorBean vendorBean = accessVendors.getVendor( vendorRequestBean );

                            AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
                            VendorWebsiteFeatureBean vendorWebsiteFeatureBean = accessVendorWebsite.getSubDomain( vendorBean );
                            if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
                                sResetDomain = vendorWebsiteFeatureBean.getValue() + "." + sResetDomain;
                            }
                            String sProtocol = applicationConfig.get(Constants.PROP_LINK_PROTOCOL,"http");

                            guestWebResponseBean.setLinkDomain( sProtocol + "://" + sResetDomain );
                        }

                    }

                    sSourceText = sSourceText.replaceAll("\\{__GUEST_RSVP_LINK__\\}", guestWebResponseBean.getLinkDomain() +"/r/rsvp.jsp?id="+guestWebResponseBean.getLinkId() ) ;
                }
            }
        }

        return sSourceText;
    }

    private GuestResponseBean getGuest( EventEmailBean eventEmailBean, EveryEventGuestGroupBean everyEventGuestGroupBean ){
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId()) &&
                everyEventGuestGroupBean!=null && !Utility.isNullOrEmpty(everyEventGuestGroupBean.getGuestGroupId() )) {
            GuestRequestBean guestRequestBean = new GuestRequestBean();
            guestRequestBean.setEventId(eventEmailBean.getEventId());
            guestRequestBean.setGuestGroupId(everyEventGuestGroupBean.getGuestGroupId());

            AccessGuest accessGuest = new AccessGuest();
            guestResponseBean = accessGuest.loadGuest(guestRequestBean);
        }
        return guestResponseBean;
    }

    private String replaceGuestBeanTemplates(String sSourceText , GuestBean guestBean) {

        if(!Utility.isNullOrEmpty(sSourceText) ) {
            if ( sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_FIRST_NAME.toString()) ) {
                String sFirstName = Constants.EMPTY;
                if( guestBean!=null ){
                    sFirstName = ParseUtil.checkNull(guestBean.getFirstName());
                }
                sSourceText = sSourceText.replaceAll("{{GUEST_FIRST_NAME}}", sFirstName );
            }

            if ( sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_LAST_NAME.toString()) ) {
                String sLastName = Constants.EMPTY;
                if( guestBean!=null ){
                    sLastName = ParseUtil.checkNull(guestBean.getLastName());
                }
                sSourceText = sSourceText.replaceAll("\\{GUEST_LAST_NAME\\}", sLastName );
            }
        }
        return sSourceText;
    }
}
