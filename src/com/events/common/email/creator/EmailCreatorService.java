package com.events.common.email.creator;

import com.events.bean.common.email.EmailObject;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.common.*;
import com.events.data.email.EmailSchedulerData;
import com.events.data.email.EmailServiceData;
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
public class EmailCreatorService {

    EmailCreator emailCreator;
    private static final Logger emailerLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);
    private EmailServiceData emailServiceData = new EmailServiceData();
    private Configuration emailerConfig = Configuration.getInstance(Constants.EMAILER_PROP);

    public EmailCreatorService() { }

    public EmailCreatorService(  MailCreator emailCreator ) {
        this.emailCreator = (EmailCreator) emailCreator;
    }

    public void invokeEmailCreator() {
        if(emailCreator!=null) {
            emailerLogging.debug("Start execution of email creator.");
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
            EmailTemplateBean emailTemplateBean = getEmailTemplateBean(emailNewScheduleBean.getEventEmailId()) ;
            if(emailTemplateBean!=null && !Utility.isNullOrEmpty(emailTemplateBean.getEmailTemplateId())) {
                arrEmailObject = getEmailObjects( emailNewScheduleBean , emailTemplateBean );
            } else {
                emailerLogging.info( " Invalid template ID :  " + emailNewScheduleBean );
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
}
