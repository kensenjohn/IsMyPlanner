package com.events.bean.common.email;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 7:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestEmailRequestBean {
    private String eventEmailId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String email = Constants.EMPTY;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestEmailRequestBean{");
        sb.append("eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
