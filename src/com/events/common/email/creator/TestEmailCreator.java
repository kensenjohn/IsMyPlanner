package com.events.common.email.creator;

import com.events.bean.common.email.EmailObject;
import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.common.Utility;
import com.events.data.email.EmailServiceData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 7:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestEmailCreator implements MailCreator {
    @Override
    public void create(EmailObject emailObject, EmailSchedulerBean emailScheduleBean) {

        if( emailObject!=null ) {
            EmailServiceData emailServiceData = new EmailServiceData();
            EmailQueueBean emailQueueBean = (EmailQueueBean) emailObject;
            emailQueueBean.setEmailQueueId(Utility.getNewGuid());
            Integer iNumOfRows = emailServiceData.insertEmailQueue(emailQueueBean);
        }
    }
}
