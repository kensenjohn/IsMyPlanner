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
    private EventEmailBean eventEmailBean = new EventEmailBean();
    private DateObject eventEmailScheduleDate = new DateObject();
    private boolean isSendEmailNow = false;
    private Constants.SEND_EMAIL_RULES sendEmailRules = Constants.SEND_EMAIL_RULES.NO_RULE_SELECTED;
    private String eventEmailId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;

    private String emailSendDay = Constants.EMPTY;
    private String emailSendTime = Constants.EMPTY;
    private String emailSendTimeZone = Constants.EMPTY;


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

    public boolean isSendEmailNow() {
        return isSendEmailNow;
    }

    public void setSendEmailNow(boolean sendEmailNow) {
        isSendEmailNow = sendEmailNow;
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
        sb.append(", isSendEmailNow=").append(isSendEmailNow);
        sb.append(", sendEmailRules=").append(sendEmailRules);
        sb.append(", eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
