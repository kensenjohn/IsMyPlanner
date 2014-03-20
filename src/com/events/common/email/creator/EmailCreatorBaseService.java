package com.events.common.email.creator;

import com.events.bean.common.email.*;
import com.events.bean.event.guest.*;
import com.events.bean.event.guest.response.GuestWebResponseBean;
import com.events.bean.event.guest.response.WebRespRequest;
import com.events.bean.event.guest.response.WebRespResponse;
import com.events.common.*;
import com.events.common.email.setting.AccessEventEmail;
import com.events.common.email.setting.EventEmailFeature;
import com.events.data.email.EmailSchedulerData;
import com.events.data.email.EmailServiceData;
import com.events.event.guest.AccessGuest;
import com.events.event.guest.GuestWebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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
                emailerLogging.debug( " Old Email Scheudler Beans " + arrOldSchedulerBean);
                for(EmailSchedulerBean emailOldScheduleBean : arrOldSchedulerBean )
                {
                    emailOldScheduleBean.setScheduleStatus( Constants.SCHEDULER_STATUS.ERROR.getStatus()  );
                    this.emailCreator.update( emailOldScheduleBean );
                }
            }

            // Get all new emails that are scheduled to be sent.
            ArrayList<EmailSchedulerBean> arrNewSchedulerBean = getNewEmailSchedules(lCurrentTime);
            if(arrNewSchedulerBean!=null && !arrNewSchedulerBean.isEmpty())
            {
                emailerLogging.debug( " New Email Scheduler Beans " + arrNewSchedulerBean);
                for(EmailSchedulerBean emailNewScheduleBean : arrNewSchedulerBean )
                {
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
        emailerLogging.debug("getOldEmailSchedules lScheduleTime : " + lScheduleTime + " lCurrentTime : " + lCurrentTime);
        ArrayList<EmailSchedulerBean> arrSchedulerBean = emailSchedulerData.getArrEmailSchedule(lScheduleTime - (lCurrentTime - lScheduleTime ), lScheduleTime, Constants.SCHEDULER_STATUS.NEW_SCHEDULE, Constants.SCHEDULE_PICKUP_TYPE.OLD_RECORDS);
        emailerLogging.debug("Old Emails start time  : " + (lScheduleTime - (lCurrentTime - lScheduleTime )) + " endtime : " + lScheduleTime);

        return arrSchedulerBean;
    }

    public ArrayList<EmailSchedulerBean> getNewEmailSchedules( Long lCurrentTime )  {
        EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
        Long lScheduleTime = DateSupport.subtractTime( lCurrentTime , ParseUtil.sToI( emailerConfig.get(Constants.PROP_EMAIL_SCHEDULE_PICKUPTIME_PADDING) ) , Constants.TIME_UNIT.MINUTES );
        emailerLogging.debug("New Emails lScheduleTime : " + lScheduleTime + " lCurrentTime : " + lCurrentTime);

        ArrayList<EmailSchedulerBean> arrSchedulerBean = emailSchedulerData.getArrEmailSchedule(lScheduleTime, lCurrentTime+(lCurrentTime-lScheduleTime),  Constants.SCHEDULER_STATUS.NEW_SCHEDULE,Constants.SCHEDULE_PICKUP_TYPE.NEW_RECORDS);
        emailerLogging.debug("New Emails start time  : " +lScheduleTime + " endtime : " +  (lCurrentTime+(lCurrentTime-lScheduleTime)));

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
                String sEmailSubject =  replaceGuestText(eventEmailBean.getSubject(),eventEmailBean,everyEventGuestGroupBean);
                String sEmailHtmlBody =  replaceGuestText(eventEmailBean.getHtmlBody(),eventEmailBean,everyEventGuestGroupBean);
                String sEmailTextBody =  replaceGuestText(eventEmailBean.getTextBody(),eventEmailBean,everyEventGuestGroupBean);

                EmailObject emailObject = new EmailQueueBean();
                emailObject.setEmailSubject(sEmailSubject);
                emailObject.setTextBody(sEmailTextBody);

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
                    sEmailHtmlBody = sEmailHtmlBody + "<img src=\""+sProtocol+"://"+sApplicationDomain+"/i.gif?_ek="+eventEmailBean.getEventEmailId()+"&_gk="+sGuestId+"\" width=1 height=1 style=\"display:none;\"/>";


                    emailObject.setHtmlBody(sEmailHtmlBody);
                    emailObject.setStatus( Constants.EMAIL_STATUS.NEW.getStatus() );
                    arrEmailObject.add(emailObject);
                }
            }

        }
        return arrEmailObject;
    }

    private ArrayList<EveryEventGuestGroupBean> getGuests(EventEmailBean eventEmailBean, EventEmailFeatureBean sendEmailRuleFeatureBean){

        ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup = new ArrayList<EveryEventGuestGroupBean>();
        emailerLogging.info("Going to get Guests : " + ParseUtil.checkNull(sendEmailRuleFeatureBean.getValue()) );
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

            if( sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_GIVEN_NAME.toString()) ) {
                if ( sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_GIVEN_NAME.toString()) ) {
                    sSourceText = sSourceText.replaceAll("\\{__GUEST_GIVEN_NAME__\\}", ParseUtil.checkNull(everyEventGuestGroupBean.getGroupName()));
                }
            }
            if( sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_FIRST_NAME.toString()) ||
                    sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_LAST_NAME.toString()) ) {
                sSourceText = replaceGuestBeanTemplates(sSourceText,guestBean );
            }

            if( sSourceText.contains(  Constants.EMAIL_TEMPLATE_TEXT.GUEST_RSVP_LINK.toString() )) {
                emailerLogging.info(" RSVP Link template exists");
                WebRespRequest webRespRequest = new WebRespRequest();
                webRespRequest.setEventId( eventEmailBean.getEventId() );
                webRespRequest.setGuestGroupId( guestBean.getGuestGroupId() );
                webRespRequest.setGuestWebResponseType( Constants.GUEST_WEBRESPONSE_TYPE.RSVP);
                GuestWebResponse guestWebResponse = new GuestWebResponse();
                WebRespResponse webRespResponse = guestWebResponse.createGuestWebResponse( webRespRequest );
                emailerLogging.info(" webRespResponse : " + webRespResponse);
                if(webRespResponse!=null && webRespResponse.getGuestWebResponseBean()!=null && !Utility.isNullOrEmpty(webRespResponse.getGuestWebResponseBean().getGuestWebResponseId()) ) {
                    GuestWebResponseBean guestWebResponseBean =  webRespResponse.getGuestWebResponseBean();

                    sSourceText = sSourceText.replaceAll("\\{__GUEST_RSVP_LINK__\\}", "http://" + guestWebResponseBean.getLinkDomain() +"/r/rsvp.jsp?id="+guestWebResponseBean.getLinkId() ) ;
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
                sSourceText = sSourceText.replaceAll("\\{__GUEST_FIRST_NAME__\\}", sFirstName );
            }

            if ( sSourceText.contains( Constants.EMAIL_TEMPLATE_TEXT.GUEST_LAST_NAME.toString()) ) {
                String sLastName = Constants.EMPTY;
                if( guestBean!=null ){
                    sLastName = ParseUtil.checkNull(guestBean.getLastName());
                }
                sSourceText = sSourceText.replaceAll("\\{__GUEST_LAST_NAME__\\}", sLastName );
            }
        }
        return sSourceText;
    }
}
