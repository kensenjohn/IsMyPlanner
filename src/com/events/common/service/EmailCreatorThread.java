package com.events.common.service;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.EmailCreatorBaseService;
import com.events.common.email.creator.MailCreator;
import com.events.common.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailCreatorThread implements Runnable {
    private static final Logger schedulerLogging = LoggerFactory.getLogger(Constants.SCHEDULER_LOGS);
    private Configuration emailerConfig = Configuration.getInstance(Constants.EMAILER_PROP);

    @Override
    public void run() {
        try {
            boolean isEmailCreatorEnabled = ParseUtil.sTob(emailerConfig.get(Constants.PROP_ENABLE_EMAIL_CREATOR_THREAD));
            if(isEmailCreatorEnabled) {
                MailCreator emailCreator = new EmailCreator();
                EmailCreatorBaseService emailCreatorService = new EmailCreatorBaseService( emailCreator );
                emailCreatorService.invokeEmailCreator();
            } else {
                schedulerLogging.info("Email creator not enabled");
            }
        } catch (Exception e) {
            schedulerLogging.error("Error while invoking the SmsCreatorThread service "
                    + ExceptionHandler.getStackTrace(e));
        }
    }
}
