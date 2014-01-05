package com.events.bean.event.guest;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/2/14
 * Time: 7:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventGuestGroupBean {
    //EVENTGUESTGROUPID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, FK_GUESTGROUPID VARCHAR(45) NOT NULL,
    // TOTAL_INVITED_SEATS INT(11), RSVP_SEATS INT(11), CREATEDATE BIGINT(20) NOT NULL DEFAULT 0 , HUMANCREATEDATE VARCHAR(45)
    //  WILL_ATTEND INT(1) NOT NULL DEFAULT 0, HAS_RESPONDED INT(1) NOT NULL DEFAULT 0
    private String eventGuestGroupId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String invitedSeats = Constants.EMPTY;
    private String rsvpSeats = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private boolean willNotAttending = false;
    private boolean hasResponded = false;

    public EventGuestGroupBean(){}
    public EventGuestGroupBean(HashMap<String,String> hmResult) {

        this.eventGuestGroupId = ParseUtil.checkNull(hmResult.get("EVENTGUESTGROUPID"));
        this.eventId = ParseUtil.checkNull(hmResult.get("FK_EVENTID"));
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        this.invitedSeats = ParseUtil.checkNull(hmResult.get("TOTAL_INVITED_SEATS"));
        this.rsvpSeats = ParseUtil.checkNull(hmResult.get("RSVP_SEATS"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMAN_CREATEDATE"));
        this.willNotAttending = ParseUtil.sTob(hmResult.get("WILL_NOT_ATTEND"));
        this.hasResponded = ParseUtil.sTob(hmResult.get("HAS_RESPONDED"));
    }

    public String getEventGuestGroupId() {
        return eventGuestGroupId;
    }

    public void setEventGuestGroupId(String eventGuestGroupId) {
        this.eventGuestGroupId = eventGuestGroupId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getInvitedSeats() {
        return invitedSeats;
    }

    public void setInvitedSeats(String invitedSeats) {
        this.invitedSeats = invitedSeats;
    }

    public String getRsvpSeats() {
        return rsvpSeats;
    }

    public void setRsvpSeats(String rsvpSeats) {
        this.rsvpSeats = rsvpSeats;
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

    public boolean isWillNotAttend() {
        return willNotAttending;
    }

    public void setWillNotAttend(boolean willNotAttending) {
        this.willNotAttending = willNotAttending;
    }

    public boolean getHasResponded() {
        return hasResponded;
    }

    public void setHasResponded(boolean hasResponded) {
        this.hasResponded = hasResponded;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventGuestGroupBean{");
        sb.append("eventGuestGroupId='").append(eventGuestGroupId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", invitedSeats='").append(invitedSeats).append('\'');
        sb.append(", rsvpSeats='").append(rsvpSeats).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", isNotAttending=").append(willNotAttending);
        sb.append(", hasResponded=").append(hasResponded);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_guestgroup_id", this.eventGuestGroupId);
            jsonObject.put("event_id", this.eventId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("invite_seats", this.invitedSeats);
            jsonObject.put("rsvp_seats", this.rsvpSeats);
            jsonObject.put("will_not_attend", this.willNotAttending);
            jsonObject.put("has_responded", this.hasResponded);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
