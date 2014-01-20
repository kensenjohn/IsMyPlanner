package com.events.common.email.scheduler;

import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailSchedulerRequestBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.email.EmailSchedulerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEmailScheduler {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EmailSchedulerBean getEmailScheduler( EmailSchedulerRequestBean emailScheduleRequestBean) {
        EmailSchedulerBean emailSchedulerBean = new EmailSchedulerBean();
        if(emailScheduleRequestBean!=null && !Utility.isNullOrEmpty(emailScheduleRequestBean.getEventEmailId())
                && !Utility.isNullOrEmpty(emailScheduleRequestBean.getEventId())) {

            EmailSchedulerBean tmpEmailSchedulerBean = new EmailSchedulerBean();
            tmpEmailSchedulerBean.setEventId( emailScheduleRequestBean.getEventId() );
            tmpEmailSchedulerBean.setEventEmailId( emailScheduleRequestBean.getEventEmailId() );

            EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
            emailSchedulerBean = emailSchedulerData.getEventEmailSchedule(tmpEmailSchedulerBean);
        }
        return emailSchedulerBean;
    }
}
