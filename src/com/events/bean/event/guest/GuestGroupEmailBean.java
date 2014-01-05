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
public class GuestGroupEmailBean {
    
    //GUESTGROUPEMAILID  VARCHAR(45) NOT NULL, FK_GUESTGROUPID VARCHAR(45) NOT NULL, FK_GUESTID   VARCHAR(45), EMAIL_ID VARCHAR(500) NOT NULL,
    private String guestGroupEmailId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String guestId = Constants.EMPTY;
    private String emailId = Constants.EMPTY;
    private boolean isPrimaryContact = false;

    public GuestGroupEmailBean() {
    }

    public GuestGroupEmailBean(HashMap<String,String> hmResult) {
        this.guestGroupEmailId = ParseUtil.checkNull(hmResult.get("GUESTGROUPEMAILID"));
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        this.guestId = ParseUtil.checkNull(hmResult.get("FK_GUESTID"));
        this.emailId = ParseUtil.checkNull(hmResult.get("EMAIL_ID"));
        isPrimaryContact = ParseUtil.sTob(hmResult.get("PRIMARY_CONTACT"));
    }

    public String getguestGroupEmailId() {
        return guestGroupEmailId;
    }

    public void setguestGroupEmailId(String guestGroupEmailId) {
        this.guestGroupEmailId = guestGroupEmailId;
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

    public String getemailId() {
        return emailId;
    }

    public void setemailId(String emailId) {
        this.emailId = emailId;
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
        sb.append("guestGroupEmailId='").append(guestGroupEmailId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", guestId='").append(guestId).append('\'');
        sb.append(", emailId='").append(emailId).append('\'');
        sb.append(", isPrimaryContact=").append(isPrimaryContact);
        sb.append('}');
        return sb.toString();
    }



    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guestgroupphone_id", this.guestGroupEmailId);
            jsonObject.put("guest_id", this.guestId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("email_id", this.emailId);
            jsonObject.put("is_primary_contact", this.isPrimaryContact);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
