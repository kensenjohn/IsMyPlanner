package com.events.bean.event.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventContactUsBean {
    // GTEVENTCONTACTUS(EVENTCONTACTUSID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL, PHONE TEXT ,  EMAIL VARCHAR(250)

    private String eventContactUsId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String phone = Constants.EMPTY;
    private String email = Constants.EMPTY;

    public EventContactUsBean() {
    }

    public EventContactUsBean(HashMap<String,String> hmResult) {
        this.eventContactUsId = ParseUtil.checkNull(hmResult.get("EVENTCONTACTUSID"));;
        this.eventWebsiteId =ParseUtil.checkNull(hmResult.get("FK_EVENTWEBSITEID"));;
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));;
        this.phone = ParseUtil.checkNull(hmResult.get("PHONE"));;
        this.email = ParseUtil.checkNull(hmResult.get("EMAIL"));;
    }

    public String getEventContactUsId() {
        return eventContactUsId;
    }

    public void setEventContactUsId(String eventContactUsId) {
        this.eventContactUsId = eventContactUsId;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventContactUsBean{");
        sb.append("eventContactUsId='").append(eventContactUsId).append('\'');
        sb.append(", eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_contactus_id", this.eventContactUsId );
            jsonObject.put("event_website_id", this.eventWebsiteId );
            jsonObject.put("name", this.name );
            jsonObject.put("phone", this.phone );
            jsonObject.put("email", this.email );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
