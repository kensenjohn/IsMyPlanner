package com.events.common.email.scheduler;

import com.events.bean.DateObject;
import com.events.bean.common.email.*;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.data.email.EmailSchedulerData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEmailScheduler {
    public EmailSchedulerResponseBean saveSchedule( EmailSchedulerRequestBean emailScheduleRequestBean) {
        EmailSchedulerResponseBean emailSchedulerResponseBean = new EmailSchedulerResponseBean();
        if(emailScheduleRequestBean!=null && emailScheduleRequestBean.isValidScheduleDataPresent()) {
            EventEmailBean eventEmailBean = emailScheduleRequestBean.getEventEmailBean();

            EmailSchedulerRequestBean tmpEmailScheduleRequestBean = new EmailSchedulerRequestBean();
            tmpEmailScheduleRequestBean.setEventEmailId(eventEmailBean.getEventEmailId());
            tmpEmailScheduleRequestBean.setEventId(eventEmailBean.getEventId());

            AccessEmailScheduler accessEmailScheduler = new AccessEmailScheduler();
            EmailSchedulerBean currentEmailSchedulerBean =  accessEmailScheduler.getEmailScheduler(tmpEmailScheduleRequestBean);

            EmailSchedulerBean emailSchedulerBean = new EmailSchedulerBean();

            emailSchedulerBean.setEventEmailId( eventEmailBean.getEventEmailId() );
            emailSchedulerBean.setEventId( eventEmailBean.getEventId() );
            emailSchedulerBean.setUserId( eventEmailBean.getUserId() );
            emailSchedulerBean.setScheduledSendDate( emailScheduleRequestBean.getScheduledSendDate() );
            emailSchedulerBean.setHumanScheduledSendDate( emailScheduleRequestBean.getScheduledSendHumanDate() );
            emailSchedulerBean.setScheduleStatus( emailScheduleRequestBean.getSchedulerStatus().getStatus());

            Integer iNumberOfRows = 0;

            EmailSchedulerData emailSchedulerData = new EmailSchedulerData();
            if(currentEmailSchedulerBean!=null && !Utility.isNullOrEmpty(currentEmailSchedulerBean.getEmailScheduleId())) {
                emailSchedulerBean.setEmailScheduleId( currentEmailSchedulerBean.getEmailScheduleId());
                iNumberOfRows = emailSchedulerData.updateEmailSchedule(emailSchedulerBean);
            } else {
                emailSchedulerBean.setEmailScheduleId(Utility.getNewGuid());
                iNumberOfRows = emailSchedulerData.insertEmailSchedule(emailSchedulerBean);
            }

            if(iNumberOfRows<=0) {
                emailSchedulerBean = new EmailSchedulerBean();
            }
            emailSchedulerResponseBean.setEmailSchedulerBean(emailSchedulerBean);
        }
        return emailSchedulerResponseBean;
    }
}
