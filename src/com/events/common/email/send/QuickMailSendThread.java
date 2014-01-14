package com.events.common.email.send;

import com.events.bean.common.email.EmailObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/9/14
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuickMailSendThread implements Runnable {

    private EmailObject emailObject;

    public QuickMailSendThread(EmailObject emailObject) {
        this.emailObject = emailObject;
    }

    @Override
    public void run() {

        if (emailObject != null) {
            MailSender mailSender = new AmazonEmailSend();
            mailSender.send(emailObject);
        }

    }

}
