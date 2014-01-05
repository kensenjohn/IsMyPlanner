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
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventGuestGroupBean {
    private String eventGuestGroupId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String groupName = Constants.EMPTY;
    private Integer invitedSeats = 0;
    private Integer rsvpSeats = 0;
    private boolean hasResponded = false;
    private boolean willNotAttend = false;

    public EveryEventGuestGroupBean() {

    }

    public EveryEventGuestGroupBean(HashMap<String,String> hmResult) {
        this.eventGuestGroupId = ParseUtil.checkNull(hmResult.get("EVENTGUESTGROUPID"));
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        this.groupName = ParseUtil.checkNull(hmResult.get("GROUPNAME"));

        this.invitedSeats = ParseUtil.sToI(hmResult.get("TOTAL_INVITED_SEATS"));
        this.rsvpSeats = ParseUtil.sToI(hmResult.get("RSVP_SEATS"));

        this.hasResponded = ParseUtil.sTob(hmResult.get("HAS_RESPONDED"));
        this.willNotAttend = ParseUtil.sTob(hmResult.get("WILL_NOT_ATTEND"));
    }
    public String getEventGuestGroupId() {
        return eventGuestGroupId;
    }

    public void setEventGuestGroupId(String eventGuestGroupId) {
        this.eventGuestGroupId = eventGuestGroupId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getInvitedSeats() {
        return invitedSeats;
    }

    public void setInvitedSeats(Integer invitedSeats) {
        this.invitedSeats = invitedSeats;
    }

    public Integer getRsvpSeats() {
        return rsvpSeats;
    }

    public void setRsvpSeats(Integer rsvpSeats) {
        this.rsvpSeats = rsvpSeats;
    }

    public boolean isHasResponded() {
        return hasResponded;
    }

    public void setHasResponded(boolean hasResponded) {
        this.hasResponded = hasResponded;
    }

    public boolean isWillNotAttend() {
        return willNotAttend;
    }

    public void setWillNotAttend(boolean willNotAttend) {
        this.willNotAttend = willNotAttend;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryGuestGroupBean{");
        sb.append("eventGuestGroupId='").append(eventGuestGroupId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", invitedSeats=").append(invitedSeats);
        sb.append(", rsvpSeats=").append(rsvpSeats);
        sb.append(", hasResponded=").append(hasResponded);
        sb.append(", willNotAttend=").append(willNotAttend);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_guestgroup_id", this.eventGuestGroupId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("group_name", this.groupName);
            jsonObject.put("invited_seats", this.invitedSeats);
            jsonObject.put("rsvp_seats", this.rsvpSeats);
            jsonObject.put("has_responded", this.hasResponded);
            jsonObject.put("will_not_attend", this.willNotAttend);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
