package com.events.bean.event;

import com.events.bean.clients.ClientBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/29/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventBean {
    private String eventId = Constants.EMPTY;
    private String eventName = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String vendorName = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private String clientName = Constants.EMPTY;
    private String eventDay = Constants.EMPTY;
    private String eventTime = Constants.EMPTY;
    private String eventTimeZone = Constants.EMPTY;

    public EveryEventBean (){

    }

    public EveryEventBean (HashMap<String,String> hmResult) {
        this.eventId = ParseUtil.checkNull(hmResult.get("EVENTID"));
        this.eventName = ParseUtil.checkNull(hmResult.get("EVENTNAME"));
        this.clientId = ParseUtil.checkNull(hmResult.get("FK_CLIENTID"));
        this.vendorId = ParseUtil.checkNull(hmResult.get("FK_VENDORID"));
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventTimeZone() {
        return eventTimeZone;
    }

    public void setEventTimeZone(String eventTimeZone) {
        this.eventTimeZone = eventTimeZone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryEventBean{");
        sb.append("eventId='").append(eventId).append('\'');
        sb.append(", eventName='").append(eventName).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", vendorName='").append(vendorName).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", clientName='").append(clientName).append('\'');
        sb.append(", eventDay='").append(eventDay).append('\'');
        sb.append(", eventTime='").append(eventTime).append('\'');
        sb.append(", eventTimeZone='").append(eventTimeZone).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("event_name", this.eventName );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("vendor_name", this.vendorName );
            jsonObject.put("client_id", this.clientId );
            jsonObject.put("client_name", this.clientName );
            jsonObject.put("event_day", this.eventDay );
            jsonObject.put("event_time", this.eventTime );
            jsonObject.put("event_timezone", this.eventTimeZone );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
