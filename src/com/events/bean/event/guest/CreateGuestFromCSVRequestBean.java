package com.events.bean.event.guest;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/22/14
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateGuestFromCSVRequestBean {

    private String uploadId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
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

    @Override
    public String toString() {
        return "CreateGuestFromCSVRequestBean{" +
                "uploadId='" + uploadId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
