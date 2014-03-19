package com.events.bean.common.email;

import com.events.bean.DateObject;
import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/10/14
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventEmailRequestBean {
    public enum ACTION {
        ONLY_SAVE_SETTING,SCHEDULE_ENABLED;
    }

    private EventEmailBean eventEmailBean = new EventEmailBean();
    private DateObject eventEmailScheduleDate = new DateObject();
    private Constants.SEND_EMAIL_RULES sendEmailRules = Constants.SEND_EMAIL_RULES.NO_RULE_SELECTED;
    private String eventEmailId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;

    private String emailSendDay = Constants.EMPTY;
    private String emailSendTime = Constants.EMPTY;
    private String emailSendTimeZone = Constants.EMPTY;
    private boolean isEmailScheduleEnabled = false;

    private  ACTION userAction = ACTION.ONLY_SAVE_SETTING;

    public boolean isEmailScheduleEnabled() {
        return isEmailScheduleEnabled;
    }

    public void setEmailScheduleEnabled(boolean isEmailScheduleEnabled) {
        this.isEmailScheduleEnabled = isEmailScheduleEnabled;
    }

    public ACTION getUserAction() {
        return userAction;
    }

    public void setUserAction(ACTION userAction) {
        this.userAction = userAction;
    }

    public String getEmailSendDay() {
        return emailSendDay;
    }

    public void setEmailSendDay(String emailSendDay) {
        this.emailSendDay = emailSendDay;
    }

    public String getEmailSendTime() {
        return emailSendTime;
    }

    public void setEmailSendTime(String emailSendTime) {
        this.emailSendTime = emailSendTime;
    }

    public String getEmailSendTimeZone() {
        return emailSendTimeZone;
    }

    public void setEmailSendTimeZone(String emailSendTimeZone) {
        this.emailSendTimeZone = emailSendTimeZone;
    }

    public EventEmailBean getEventEmailBean() {
        return eventEmailBean;
    }

    public void setEventEmailBean(EventEmailBean eventEmailBean) {
        this.eventEmailBean = eventEmailBean;
    }

    public DateObject getEventEmailScheduleDate() {
        return eventEmailScheduleDate;
    }

    public void setEventEmailScheduleDate(DateObject eventEmailScheduleDate) {
        this.eventEmailScheduleDate = eventEmailScheduleDate;
    }

    public Constants.SEND_EMAIL_RULES getSendEmailRules() {
        return sendEmailRules;
    }

    public void setSendEmailRules(Constants.SEND_EMAIL_RULES sendEmailRules) {
        this.sendEmailRules = sendEmailRules;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventEmailRequestBean{");
        sb.append("eventEmailBean=").append(eventEmailBean);
        sb.append(", eventEmailScheduleDate=").append(eventEmailScheduleDate);
        sb.append(", sendEmailRules=").append(sendEmailRules);
        sb.append(", eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
