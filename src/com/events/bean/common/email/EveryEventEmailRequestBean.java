package com.events.bean.common.email;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventEmailRequestBean {
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryEventEmailRequestBean{");
        sb.append("eventId='").append(eventId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
