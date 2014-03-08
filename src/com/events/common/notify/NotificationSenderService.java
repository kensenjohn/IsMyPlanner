package com.events.common.notify;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/8/14
 * Time: 7:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationSenderService {

    public void invokeNotificationSender() {

    }


    private void getAllNewNotificationFromQueue() {
        Notification notification = new Notification();
        Notification.getNewNotificationFromQueue();
    }
}
