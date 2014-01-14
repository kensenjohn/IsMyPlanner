package com.events.bean.common.email;

import com.events.common.Constants;
import com.events.common.Utility;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailSchedulerRequestBean {
    private EventEmailBean eventEmailBean = new EventEmailBean();
    private EmailSchedulerBean emailSchedulerBean = new EmailSchedulerBean();
    private EventEmailFeatureBean emailSendDay = new EventEmailFeatureBean();
    private EventEmailFeatureBean emailSendTime = new EventEmailFeatureBean();
    private EventEmailFeatureBean emailSendTimeZone = new EventEmailFeatureBean();
    private Constants.SCHEDULER_STATUS schedulerStatus = Constants.SCHEDULER_STATUS.NEW_SCHEDULE;

    private String eventEmailId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private Long scheduledSendDate = 0L;
    private String scheduledSendHumanDate = Constants.EMPTY;


    public Long getScheduledSendDate() {
        return scheduledSendDate;
    }

    public void setScheduledSendDate(Long scheduledSendDate) {
        this.scheduledSendDate = scheduledSendDate;
    }

    public String getScheduledSendHumanDate() {
        return scheduledSendHumanDate;
    }

    public void setScheduledSendHumanDate(String scheduledSendHumanDate) {
        this.scheduledSendHumanDate = scheduledSendHumanDate;
    }

    public String getEventEmailId() {
        return eventEmailId;
    }

    public void setEventEmailId(String eventEmailId) {
        this.eventEmailId = eventEmailId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EmailSchedulerBean getEmailSchedulerBean() {
        return emailSchedulerBean;
    }

    public void setEmailSchedulerBean(EmailSchedulerBean emailSchedulerBean) {
        this.emailSchedulerBean = emailSchedulerBean;
    }

    public EventEmailBean getEventEmailBean() {
        return eventEmailBean;
    }

    public void setEventEmailBean(EventEmailBean eventEmailBean) {
        this.eventEmailBean = eventEmailBean;
    }

    public EventEmailFeatureBean getEmailSendDay() {
        return emailSendDay;
    }

    public void setEmailSendDay(EventEmailFeatureBean emailSendDay) {
        this.emailSendDay = emailSendDay;
    }

    public EventEmailFeatureBean getEmailSendTime() {
        return emailSendTime;
    }

    public void setEmailSendTime(EventEmailFeatureBean emailSendTime) {
        this.emailSendTime = emailSendTime;
    }

    public EventEmailFeatureBean getEmailSendTimeZone() {
        return emailSendTimeZone;
    }

    public void setEmailSendTimeZone(EventEmailFeatureBean emailSendTimeZone) {
        this.emailSendTimeZone = emailSendTimeZone;
    }

    public boolean isValidScheduleDataPresent() {
        boolean isValidScheduleDataPresent = false;
        if( scheduledSendDate>0 && !Utility.isNullOrEmpty(scheduledSendHumanDate) &&
                eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId()) && !Utility.isNullOrEmpty(eventEmailBean.getEventId())  ) {
            isValidScheduleDataPresent = true;
        }
        return isValidScheduleDataPresent;
    }

    public Constants.SCHEDULER_STATUS getSchedulerStatus() {
        return schedulerStatus;
    }

    public void setSchedulerStatus(Constants.SCHEDULER_STATUS schedulerStatus) {
        this.schedulerStatus = schedulerStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmailSchedulerRequestBean{");
        sb.append("eventEmailBean=").append(eventEmailBean);
        sb.append(", emailSchedulerBean=").append(emailSchedulerBean);
        sb.append(", emailSendDay=").append(emailSendDay);
        sb.append(", emailSendTime=").append(emailSendTime);
        sb.append(", emailSendTimeZone=").append(emailSendTimeZone);
        sb.append(", schedulerStatus=").append(schedulerStatus);
        sb.append(", eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", scheduledSendDate=").append(scheduledSendDate);
        sb.append(", scheduledSendHumanDate='").append(scheduledSendHumanDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
