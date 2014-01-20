package com.events.common.email.send;

import com.events.bean.common.email.EmailQueueBean;
import com.events.common.Constants;
import com.events.data.email.EmailServiceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 9:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailSenderService {

    private MailSender mailSender;
    private EmailServiceData emailServiceData = new EmailServiceData();
    private static final Logger emailerLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);

    public MailSenderService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void invokeMailSender() {
        ArrayList<EmailQueueBean> arrEmailQueueBean = getAllNewEmails();
        if (arrEmailQueueBean != null && !arrEmailQueueBean.isEmpty()) {
            emailerLogging.debug("Number of emails " + arrEmailQueueBean.size());
            for (EmailQueueBean emailQueueBean : arrEmailQueueBean) {
                emailerLogging.debug("EmailQueueBean going to email : " + emailQueueBean);
                Integer iNumOfRecs = setEmailStatus(emailQueueBean,
                        Constants.EMAIL_STATUS.PICKED_TO_SEND);
                emailerLogging.debug("Picked to send records : " + iNumOfRecs);
                boolean isSucess = false;

                Constants.EMAIL_STATUS emailStatus = Constants.EMAIL_STATUS.ERROR;
                if (iNumOfRecs > 0) {
                    emailerLogging.debug("Sending email to  " + emailQueueBean);
                    if( this.mailSender.send(emailQueueBean) ) {
                        emailStatus = Constants.EMAIL_STATUS.SENT;
                    }
                }
                setEmailStatus(emailQueueBean, emailStatus);
            }
        }
    }

    private ArrayList<EmailQueueBean> getAllNewEmails() {
        return emailServiceData.getEmailsFromQueue(Constants.EMAIL_STATUS.NEW);
    }

    private Integer setEmailStatus(EmailQueueBean emailQueueBean, Constants.EMAIL_STATUS emailStatus) {
        return emailServiceData.updateEmailStatus(emailQueueBean, emailStatus);
    }
}
