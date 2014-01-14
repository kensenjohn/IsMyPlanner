package com.events.common.email.send;

import com.events.bean.common.email.EmailObject;
import com.events.common.Configuration;
import com.events.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 8:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmtpEmailSend  implements MailSender{
    private Logger emailerLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);
    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

    @Override
    public boolean send(EmailObject emailObject) {
        boolean isSuccess = true;

        emailerLogging.info("Complete Simple send of email");

        return isSuccess;
    }
}
