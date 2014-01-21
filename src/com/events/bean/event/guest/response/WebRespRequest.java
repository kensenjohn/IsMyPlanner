package com.events.bean.event.guest.response;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebRespRequest {
    private String linkId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Constants.GUEST_WEBRESPONSE_TYPE guestWebResponseType = Constants.GUEST_WEBRESPONSE_TYPE.none;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
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

    public Constants.GUEST_WEBRESPONSE_TYPE getGuestWebResponseType() {
        return guestWebResponseType;
    }

    public void setGuestWebResponseType(Constants.GUEST_WEBRESPONSE_TYPE guestWebResponseType) {
        this.guestWebResponseType = guestWebResponseType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebRespRequest{");
        sb.append("linkId='").append(linkId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", guestWebResponseType=").append(guestWebResponseType);
        sb.append('}');
        return sb.toString();
    }
}
