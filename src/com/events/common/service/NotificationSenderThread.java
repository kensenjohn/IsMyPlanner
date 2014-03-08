package com.events.common.service;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.exception.ExceptionHandler;
import com.events.common.notify.NotificationSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/8/14
 * Time: 7:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationSenderThread implements Runnable {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private Configuration appConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    @Override
    public void run() {
        try {
            boolean isNotificationSendingEnabled = ParseUtil.sTob(appConfig.get(Constants.PROP_ENABLE_NOTIFICATION_SENDER_THREAD));
            if(isNotificationSendingEnabled) {
                NotificationSenderService notificationSenderService = new NotificationSenderService();
                notificationSenderService.invokeNotificationSender();
            }
        } catch (Exception e) {
            appLogging.error("Error while invoking the mailman service " + ExceptionHandler.getStackTrace(e));
        }
    }
}
