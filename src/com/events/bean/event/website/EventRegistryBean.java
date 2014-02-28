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
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventRegistryBean {
    //GTEVENTREGISTRY(EVENTREGISTRYID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL, URL TEXT ,
    // INSTRUCTIONS TEXT , PRIMARY KEY (EVENTREGISTRYID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String eventRegistryId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String url = Constants.EMPTY;
    private String instructions = Constants.EMPTY;

    public EventRegistryBean() { }
    public EventRegistryBean(HashMap<String,String> hmResult) {
        this.eventRegistryId = ParseUtil.checkNull(hmResult.get("EVENTREGISTRYID"));
        this.eventWebsiteId = ParseUtil.checkNull(hmResult.get("FK_EVENTWEBSITEID"));
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));
        this.url = ParseUtil.checkNull(hmResult.get("URL"));
        this.instructions = ParseUtil.checkNull(hmResult.get("INSTRUCTIONS"));
    }

    public String getEventRegistryId() {
        return eventRegistryId;
    }

    public void setEventRegistryId(String eventRegistryId) {
        this.eventRegistryId = eventRegistryId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventRegistryBean{");
        sb.append("eventRegistryId='").append(eventRegistryId).append('\'');
        sb.append(", eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", instructions='").append(instructions).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_registry_id", this.eventRegistryId );
            jsonObject.put("event_website_id", this.eventWebsiteId );
            jsonObject.put("name", this.name );
            jsonObject.put("url", this.url );
            jsonObject.put("instructions", this.instructions );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
