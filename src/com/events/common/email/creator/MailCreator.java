package com.events.common.email.creator;

import com.events.bean.common.email.EmailObject;
import com.events.bean.common.email.EmailSchedulerBean;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MailCreator {
    public void create(EmailObject emailObject, EmailSchedulerBean emailScheduleBean);
}
