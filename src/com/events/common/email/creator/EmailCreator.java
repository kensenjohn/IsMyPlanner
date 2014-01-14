package com.events.common.email.creator;

import com.events.bean.common.email.EmailObject;
import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.email.EmailSchedulerData;
import com.events.data.email.EmailServiceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailCreator implements MailCreator {
    private static final Logger emailLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);

    @Override
    public void create(EmailObject emailObject, EmailSchedulerBean emailScheduleBean) {
        EmailServiceData emailServiceData = new EmailServiceData();

        EmailQueueBean emailQueueBean = (EmailQueueBean) emailObject;
        emailQueueBean.setEmailQueueId(Utility.getNewGuid());
        Integer iNumOfRows = emailServiceData.insertEmailQueue(emailQueueBean);

        if(emailScheduleBean!=null && !"".equalsIgnoreCase(emailScheduleBean.getEmailScheduleId())) {
            // updating Schedule so this record will not get picked up again
            if(iNumOfRows>0) {
                emailLogging.info("Successfully created entry in Email Queue : " + emailScheduleBean );
                // Successfully created entry in Queue.
                emailScheduleBean.setScheduleStatus( Constants.SCHEDULER_STATUS.COMPLETE.getStatus()  );
            } else {
                emailLogging.info("There was an error creating entry in Email : " + emailScheduleBean );
                // There as an error creating entry in Queue
                emailScheduleBean.setScheduleStatus(Constants.SCHEDULER_STATUS.ERROR.getStatus());
            }
            EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
            emailSchedulerData.updateEmailSchedule( emailScheduleBean );
        }
    }

    public void update( EmailSchedulerBean emailScheduleBean ) {
        if(emailScheduleBean!=null) {
            EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
            emailSchedulerData.updateEmailSchedule(emailScheduleBean);
        }
    }
}
