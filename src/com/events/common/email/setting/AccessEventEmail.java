package com.events.common.email.setting;

import com.events.bean.common.email.*;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.email.scheduler.AccessEmailScheduler;
import com.events.data.email.EventEmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 6:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventEmail {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EventEmailResponseBean getEventEmail( EventEmailRequestBean eventEmailRequestBean ) {
        EventEmailResponseBean eventEmailResponseBean = new EventEmailResponseBean();
        if(eventEmailRequestBean!=null && !Utility.isNullOrEmpty(eventEmailRequestBean.getEventEmailId())) {
            EventEmailData eventEmailData = new EventEmailData();
            EventEmailBean eventEmailBean = eventEmailData.getEventEmail(eventEmailRequestBean);
            eventEmailResponseBean.setEventEmailBean(eventEmailBean);
        }
        return eventEmailResponseBean;
    }


    public EventEmailFeatureBean getEventEmailFeatureTypeBean( Constants.EventEmailFeatureType eventEmailFeatureType) {
        EventEmailFeatureBean eventEmailFeatureBean = new EventEmailFeatureBean();
        eventEmailFeatureBean.setFeatureType(eventEmailFeatureType);
        return eventEmailFeatureBean;
    }

    // Email Schedule
    public EmailSchedulerBean getEventEmailSchedule( EventEmailBean eventEmailBean ) {
        EmailSchedulerBean emailSchedulerBean = new EmailSchedulerBean();
        if(eventEmailBean !=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId())&& !Utility.isNullOrEmpty(eventEmailBean.getEventId())) {
            EmailSchedulerRequestBean emailSchedulerRequestBean = new EmailSchedulerRequestBean();
            emailSchedulerRequestBean.setEventEmailId(eventEmailBean.getEventEmailId());
            emailSchedulerRequestBean.setEventId( eventEmailBean.getEventId() );

            AccessEmailScheduler accessEmailScheduler = new AccessEmailScheduler();
            emailSchedulerBean = accessEmailScheduler.getEmailScheduler(emailSchedulerRequestBean);
        }
        return emailSchedulerBean;
    }
}
