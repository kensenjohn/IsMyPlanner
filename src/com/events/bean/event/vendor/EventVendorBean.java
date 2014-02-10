package com.events.bean.event.vendor;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 5:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventVendorBean {
    //GTEVENTVENDORS(EVENTVENDORID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL,
    private String eventVendorId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;

    public EventVendorBean() {

    }
    public EventVendorBean(HashMap<String,String> hmResult) {
        this.eventId = ParseUtil.checkNull(hmResult.get("FK_EVENTID"));
        this.eventVendorId = ParseUtil.checkNull(hmResult.get("EVENTVENDORID"));
        this.vendorId = ParseUtil.checkNull(hmResult.get("FK_VENDORID"));
    }
    public String getEventVendorId() {
        return eventVendorId;
    }

    public void setEventVendorId(String eventVendorId) {
        this.eventVendorId = eventVendorId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString() {
        return "EventVendorBean{" +
                "eventVendorId='" + eventVendorId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", vendorId='" + vendorId + '\'' +
                '}';
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("eventvendor_id", this.eventVendorId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
