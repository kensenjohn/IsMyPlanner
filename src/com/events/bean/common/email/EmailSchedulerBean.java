package com.events.bean.common.email;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailSchedulerBean {
    public EmailSchedulerBean(){}
    public EmailSchedulerBean( HashMap<String,String> hmEmailScheduleBean) {
        this.emailScheduleId = ParseUtil.checkNull(hmEmailScheduleBean.get("EMAILSCHEDULEID"));
        this.eventEmailId = ParseUtil.checkNull( hmEmailScheduleBean.get("FK_EVENTEMAILID") );
        this.eventId = ParseUtil.checkNull( hmEmailScheduleBean.get("FK_EVENTID") );
        this.userId = ParseUtil.checkNull( hmEmailScheduleBean.get("FK_USERID") );
        this.createDate = ParseUtil.sToL( hmEmailScheduleBean.get("CREATEDATE") );
        this.humanCreateDate =  ParseUtil.checkNull( hmEmailScheduleBean.get("HUMANCREATEDATE") );
        this.scheduledSendDate = ParseUtil.sToL( hmEmailScheduleBean.get("SCHEDULEDSENDDATE") );
        this.humanScheduledSendDate =  ParseUtil.checkNull( hmEmailScheduleBean.get("HUMANSCHEDULEDSENDDATE") );
        this.scheduleStatus = ParseUtil.checkNull( hmEmailScheduleBean.get("SCHEDULE_STATUS") );
    }
    private String emailScheduleId = Constants.EMPTY;
    private String eventEmailId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private Long scheduledSendDate = 0L;
    private String humanScheduledSendDate = Constants.EMPTY;
    private String scheduleStatus = Constants.EMPTY;

    public String getEmailScheduleId() {
        return emailScheduleId;
    }

    public void setEmailScheduleId(String emailScheduleId) {
        this.emailScheduleId = emailScheduleId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    public Long getScheduledSendDate() {
        return scheduledSendDate;
    }

    public void setScheduledSendDate(Long scheduledSendDate) {
        this.scheduledSendDate = scheduledSendDate;
    }

    public String getHumanScheduledSendDate() {
        return humanScheduledSendDate;
    }

    public void setHumanScheduledSendDate(String humanScheduledSendDate) {
        this.humanScheduledSendDate = humanScheduledSendDate;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmailSchedulerBean{");
        sb.append("emailScheduleId='").append(emailScheduleId).append('\'');
        sb.append(", eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", scheduledSendDate=").append(scheduledSendDate);
        sb.append(", humanScheduledSendDate='").append(humanScheduledSendDate).append('\'');
        sb.append(", scheduleStatus='").append(scheduleStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
