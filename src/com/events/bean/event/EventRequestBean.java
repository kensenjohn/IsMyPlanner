package com.events.bean.event;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventRequestBean {
    private String eventId = Constants.EMPTY;
    private String eventName = Constants.EMPTY;
    private String eventDay = Constants.EMPTY;
    private String eventTime = Constants.EMPTY;
    private String eventTimeZone = Constants.EMPTY;
    private String eventClient =  Constants.EMPTY;
    private String eventClientName =  Constants.EMPTY;
    private String eventClientEmail =  Constants.EMPTY;
    private String eventVendorId  =  Constants.EMPTY;
    private String eventFolderId  =  Constants.EMPTY;
    private boolean isEventClientCorporate = false;
    private Long eventDate = 0L;
    private String eventHumanDate =  Constants.EMPTY;
    private boolean eventDelete = false;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventTimeZone() {
        return eventTimeZone;
    }

    public void setEventTimeZone(String eventTimeZone) {
        this.eventTimeZone = eventTimeZone;
    }

    public String getEventClient() {
        return eventClient;
    }

    public void setEventClient(String eventClient) {
        this.eventClient = eventClient;
    }

    public String getEventVendorId() {
        return eventVendorId;
    }

    public void setEventVendorId(String eventVendorId) {
        this.eventVendorId = eventVendorId;
    }

    public String getEventFolderId() {
        return eventFolderId;
    }

    public void setEventFolderId(String eventFolderId) {
        this.eventFolderId = eventFolderId;
    }

    public String getEventClientName() {
        return eventClientName;
    }

    public void setEventClientName(String eventClientName) {
        this.eventClientName = eventClientName;
    }

    public String getEventClientEmail() {
        return eventClientEmail;
    }

    public void setEventClientEmail(String eventClientEmail) {
        this.eventClientEmail = eventClientEmail;
    }

    public boolean isEventClientCorporate() {
        return isEventClientCorporate;
    }

    public void setEventClientCorporate(boolean eventClientCorporate) {
        isEventClientCorporate = eventClientCorporate;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventHumanDate() {
        return eventHumanDate;
    }

    public void setEventHumanDate(String eventHumanDate) {
        this.eventHumanDate = eventHumanDate;
    }

    public boolean isEventDelete() {
        return eventDelete;
    }

    public void setEventDelete(boolean eventDelete) {
        this.eventDelete = eventDelete;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventRequestBean{");
        sb.append("eventId='").append(eventId).append('\'');
        sb.append(", eventName='").append(eventName).append('\'');
        sb.append(", eventDay='").append(eventDay).append('\'');
        sb.append(", eventTime='").append(eventTime).append('\'');
        sb.append(", eventTimeZone='").append(eventTimeZone).append('\'');
        sb.append(", eventClient='").append(eventClient).append('\'');
        sb.append(", eventClientName='").append(eventClientName).append('\'');
        sb.append(", eventClientEmail='").append(eventClientEmail).append('\'');
        sb.append(", eventVendorId='").append(eventVendorId).append('\'');
        sb.append(", eventFolderId='").append(eventFolderId).append('\'');
        sb.append(", isEventClientCorporate=").append(isEventClientCorporate);
        sb.append('}');
        return sb.toString();
    }
}
