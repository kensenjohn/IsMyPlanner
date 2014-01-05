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
 * Time: 7:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestGroupPhoneBean {
    //GUESTGROUPPHONEID  VARCHAR(45) NOT NULL, FK_GUESTGROUPID VARCHAR(45) NOT NULL, FK_GUESTID   VARCHAR(45), PHONE_NUM VARCHAR(50) NOT NULL
    private String guestGroupPhoneId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String guestId = Constants.EMPTY;
    private String phoneNumber = Constants.EMPTY;
    private boolean isPrimaryContact = false;

    public GuestGroupPhoneBean() {
    }

    public GuestGroupPhoneBean(HashMap<String,String> hmResult) {
        this.guestGroupPhoneId = ParseUtil.checkNull(hmResult.get("GUESTGROUPPHONEID"));
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        this.guestId = ParseUtil.checkNull(hmResult.get("FK_GUESTID"));
        this.phoneNumber = ParseUtil.checkNull(hmResult.get("PHONE_NUM"));
        isPrimaryContact = ParseUtil.sTob(hmResult.get("PRIMARY_CONTACT"));
    }

    public String getGuestGroupPhoneId() {
        return guestGroupPhoneId;
    }

    public void setGuestGroupPhoneId(String guestGroupPhoneId) {
        this.guestGroupPhoneId = guestGroupPhoneId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPrimaryContact() {
        return isPrimaryContact;
    }

    public void setPrimaryContact(boolean primaryContact) {
        isPrimaryContact = primaryContact;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestGroupPhoneBean{");
        sb.append("guestGroupPhoneId='").append(guestGroupPhoneId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", guestId='").append(guestId).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", isPrimaryContact=").append(isPrimaryContact);
        sb.append('}');
        return sb.toString();
    }



    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guestgroupphone_id", this.guestGroupPhoneId);
            jsonObject.put("guest_id", this.guestId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("phone_num", this.phoneNumber);
            jsonObject.put("is_primary_contact", this.isPrimaryContact);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
