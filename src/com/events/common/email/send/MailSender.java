package com.events.common.email.send;

import com.events.bean.common.email.EmailObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 8:34 AM
 * To change this template use File | Settings | File Templates.
 */


public interface MailSender {
    public boolean send(EmailObject emailObject);
}
