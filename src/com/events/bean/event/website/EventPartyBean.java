package com.events.bean.event.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventPartyBean {
    //(EVENTPARTYID VARCHAR(45) NOT NULL,FK_EVENTWEBSITEID, EVENTPARTYTYPE VARCHAR(100) NOT NULL, NAME VARCHAR(250) NOT NULL, DESCRIPTION TEX
    private String eventPartyId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private Constants.EVENT_PARTY_TYPE eventPartyType = Constants.EVENT_PARTY_TYPE.NONE;
    private String eventPartyTypeName = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String description = Constants.EMPTY;
    private String uploadId = Constants.EMPTY;

    public EventPartyBean(){}
    public EventPartyBean(HashMap<String,String> hmResult) {
        this.eventPartyId = ParseUtil.checkNull(hmResult.get("EVENTPARTYID"));
        this.eventWebsiteId = ParseUtil.checkNull(hmResult.get("FK_EVENTWEBSITEID"));
        this.eventPartyTypeName = ParseUtil.checkNull(hmResult.get("EVENTPARTYTYPE"));
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));
        this.description = ParseUtil.checkNull(hmResult.get("DESCRIPTION"));
        this.uploadId = ParseUtil.checkNull(hmResult.get("FK_UPLOADID"));


        if(!Utility.isNullOrEmpty(this.eventPartyTypeName)) {
            eventPartyType = Constants.EVENT_PARTY_TYPE.valueOf( this.eventPartyTypeName );
        }

    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getEventPartyId() {
        return eventPartyId;
    }

    public void setEventPartyId(String eventPartyId) {
        this.eventPartyId = eventPartyId;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public Constants.EVENT_PARTY_TYPE getEventPartyType() {
        return eventPartyType;
    }

    public void setEventPartyType(Constants.EVENT_PARTY_TYPE eventPartyType) {
        this.eventPartyType = eventPartyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventPartyTypeName() {
        return eventPartyTypeName;
    }

    public void setEventPartyTypeName(String eventPartyTypeName) {
        this.eventPartyTypeName = eventPartyTypeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventPartyBean{");
        sb.append("eventPartyId='").append(eventPartyId).append('\'');
        sb.append(", eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", eventPartyType=").append(eventPartyType);
        sb.append(", eventPartyTypeName='").append(eventPartyTypeName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_party_id", this.eventPartyId );
            jsonObject.put("event_website_id", this.eventWebsiteId );
            jsonObject.put("event_party_type", this.eventPartyTypeName );
            jsonObject.put("name", this.name );
            jsonObject.put("description", this.description );
            jsonObject.put("upload_id", this.uploadId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
