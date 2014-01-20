package com.events.common.service;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.email.send.AmazonEmailSend;
import com.events.common.email.send.MailSender;
import com.events.common.email.send.MailSenderService;
import com.events.common.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class EmailSenderThread implements Runnable {
    private static final Logger schedulerLogging = LoggerFactory.getLogger(Constants.SCHEDULER_LOGS);
    private Configuration emailerConfig = Configuration.getInstance(Constants.EMAILER_PROP);
    @Override
    public void run() {
        try {
            boolean isEmailSenderThreadEnabled = ParseUtil.sTob(emailerConfig.get(Constants.PROP_ENABLE_EMAIL_SENDER_THREAD));

            if(isEmailSenderThreadEnabled) {
                MailSender mailSender = new AmazonEmailSend();
                MailSenderService mailingService = new MailSenderService(mailSender);
                mailingService.invokeMailSender();
            }
        } catch (Exception e) {
            schedulerLogging.error("Error while invoking the mailman service " + ExceptionHandler.getStackTrace(e));
        }
    }
}
