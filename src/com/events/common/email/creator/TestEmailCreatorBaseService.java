package com.events.common.email.creator;

import com.events.bean.common.email.*;
import com.events.bean.event.guest.EveryEventGuestGroupBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.email.setting.AccessEventEmail;
import com.events.common.email.setting.EventEmailFeature;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 7:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestEmailCreatorBaseService extends MailCreatorBase {
    private TestEmailCreator testEmailCreator;

    public TestEmailCreatorBaseService(MailCreator mailCreator) {
        this.testEmailCreator = (TestEmailCreator)mailCreator;
    }

    public void invokeTestEmailCreator(TestEmailRequestBean testEmailRequestBean) {

        EmailSchedulerBean emailSchedulerBean = createNewTestEmailSendSchedule();
        ArrayList<EmailObject> arrEmailObject = createEmailObject(testEmailRequestBean);
        if(arrEmailObject!=null && !arrEmailObject.isEmpty()){
            for(EmailObject emailObject : arrEmailObject ) {
                this.testEmailCreator.create( emailObject , emailSchedulerBean );
            }
        }

    }

    public EmailSchedulerBean createNewTestEmailSendSchedule() {
        EmailSchedulerBean emailSchedulerBean = new EmailSchedulerBean();

        return emailSchedulerBean;
    }

    public ArrayList<EmailObject> createEmailObject( TestEmailRequestBean testEmailRequestBean ) {
        ArrayList<EmailObject> arrEmailObject = new ArrayList<EmailObject>();
        if( testEmailRequestBean!=null && !Utility.isNullOrEmpty(testEmailRequestBean.getEventEmailId())) {
            EventEmailBean eventEmailBean = getEventEmailBean(testEmailRequestBean.getEventEmailId());
            arrEmailObject = getProcessedTestEmailObject(eventEmailBean , testEmailRequestBean.getEmail() );
        }
        return arrEmailObject;
    }

    private  ArrayList<EmailObject>  getProcessedTestEmailObject( EventEmailBean eventEmailBean, String sEmail) {
        ArrayList<EmailObject> arrEmailObject = new ArrayList<EmailObject>();
        if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId())) {

            String sEmailSubject =  replaceGuestText(eventEmailBean.getSubject());
            String sEmailHtmlBody =  replaceGuestText(eventEmailBean.getHtmlBody());
            String sEmailTextBody =  replaceGuestText(eventEmailBean.getTextBody());

            EmailObject emailObject = new EmailQueueBean();
            emailObject.setEmailSubject(sEmailSubject);
            emailObject.setHtmlBody(sEmailHtmlBody);
            emailObject.setTextBody(sEmailTextBody);

            emailObject.setFromAddress( eventEmailBean.getFromAddressEmail() );
            emailObject.setFromAddressName( eventEmailBean.getFromAddressEmail() );

            emailObject.setToAddress( sEmail );
            emailObject.setToAddressName( sEmail );
            emailObject.setStatus( Constants.EMAIL_STATUS.NEW.getStatus() );
            arrEmailObject.add(emailObject);
        }
        return arrEmailObject;
    }

    private String replaceGuestText(String sSourceText){
        return sSourceText;
    }
}
