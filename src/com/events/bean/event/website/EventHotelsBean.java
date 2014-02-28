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
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventHotelsBean {
    // CREATE TABLE GTEVENTHOTELS(EVENTHOTELID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL,
    // PHONE VARCHAR(45), ADDRESS TEXT, URL TEXT , PRIMARY KEY (EVENTHOTELID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String eventHotelId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String phone = Constants.EMPTY;
    private String address = Constants.EMPTY;
    private String url = Constants.EMPTY;
    private String instructions = Constants.EMPTY;


    public EventHotelsBean(){}
    public EventHotelsBean(HashMap<String,String> hmResult) {
        this.eventHotelId = ParseUtil.checkNull(hmResult.get("EVENTHOTELID"));
        this.eventWebsiteId = ParseUtil.checkNull(hmResult.get("FK_EVENTWEBSITEID"));
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));
        this.phone = ParseUtil.checkNull(hmResult.get("PHONE"));
        this.address = ParseUtil.checkNull(hmResult.get("ADDRESS"));
        this.url = ParseUtil.checkNull(hmResult.get("URL"));
        this.instructions = ParseUtil.checkNull(hmResult.get("INSTRUCTIONS"));
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getEventHotelId() {
        return eventHotelId;
    }

    public void setEventHotelId(String eventHotelId) {
        this.eventHotelId = eventHotelId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventHotelsBean{");
        sb.append("eventHotelId='").append(eventHotelId).append('\'');
        sb.append(", eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", instructions='").append(instructions).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_hotel_id", this.eventHotelId );
            jsonObject.put("event_website_id", this.eventWebsiteId );
            jsonObject.put("name", this.name );
            jsonObject.put("phone", this.phone );
            jsonObject.put("address", this.address );
            jsonObject.put("url", this.url );
            jsonObject.put("instructions", this.instructions );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
