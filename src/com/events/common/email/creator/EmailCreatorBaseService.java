package com.events.common.email.creator;

import com.events.bean.common.email.*;
import com.events.bean.event.guest.EveryEventGuestGroupBean;
import com.events.bean.event.guest.GuestGroupEmailBean;
import com.events.bean.event.guest.GuestRequestBean;
import com.events.bean.event.guest.GuestResponseBean;
import com.events.common.*;
import com.events.common.email.setting.AccessEventEmail;
import com.events.common.email.setting.EventEmailFeature;
import com.events.data.email.EmailSchedulerData;
import com.events.data.email.EmailServiceData;
import com.events.event.guest.AccessGuest;
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
        ArrayList<EmailSchedulerBean> arrSchedulerBean = emailSchedulerData.getArrEmailSchedule(lScheduleTime, lCurrentTime, Constants.SCHEDULER_STATUS.NEW_SCHEDULE, Constants.SCHEDULE_PICKUP_TYPE.OLD_RECORDS);

        return arrSchedulerBean;
    }

    public ArrayList<EmailSchedulerBean> getNewEmailSchedules( Long lCurrentTime )  {
        EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
        Long lScheduleTime = DateSupport.subtractTime( lCurrentTime , ParseUtil.sToI( emailerConfig.get(Constants.PROP_EMAIL_SCHEDULE_PICKUPTIME_PADDING) ) , Constants.TIME_UNIT.MINUTES );
        ArrayList<EmailSchedulerBean> arrSchedulerBean = emailSchedulerData.getArrEmailSchedule(lScheduleTime, lCurrentTime,  Constants.SCHEDULER_STATUS.NEW_SCHEDULE,Constants.SCHEDULE_PICKUP_TYPE.NEW_RECORDS);

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
                emailObject.setHtmlBody(sEmailHtmlBody);
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
                    for( GuestGroupEmailBean guestGroupEmailBean : arrGuestGroupEmailBean  )  {
                        emailObject.setToAddress(guestGroupEmailBean.getemailId());
                        emailObject.setToAddressName(guestGroupEmailBean.getemailId());
                    }
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
        return sSourceText;
    }
}
