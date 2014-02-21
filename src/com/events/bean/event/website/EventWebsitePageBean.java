package com.events.bean.event.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/21/14
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventWebsitePageBean {
    //GTEVENTWEBSITEPAGE(EVENTWEBSITEPAGEID  VARCHAR(45) NOT NULL ,   FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL ,
    // FK_WEBSITETHEMEID  VARCHAR(45) NOT NULL , TYPE  VARCHAR(45) NOT NULL, IS_SHOW INT(1) NOT NULL DEFAULT 0,
    private String eventWebsitePageId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private String websiteThemeId = Constants.EMPTY;
    private String type = Constants.EMPTY;
    private boolean isShow = false;

    public EventWebsitePageBean(){}
    public EventWebsitePageBean(HashMap<String,String> hmResult) {
        this.eventWebsitePageId = ParseUtil.checkNull(hmResult.get("EVENTWEBSITEPAGEID"));
        this.eventWebsiteId = ParseUtil.checkNull(hmResult.get("FK_EVENTWEBSITEID"));
        this.websiteThemeId = ParseUtil.checkNull(hmResult.get("FK_WEBSITETHEMEID"));
        this.type = ParseUtil.checkNull(hmResult.get("TYPE"));
        this.isShow = ParseUtil.sTob(hmResult.get("IS_SHOW"));
    }

    public String getEventWebsitePageId() {
        return eventWebsitePageId;
    }

    public void setEventWebsitePageId(String eventWebsitePageId) {
        this.eventWebsitePageId = eventWebsitePageId;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public String getWebsiteThemeId() {
        return websiteThemeId;
    }

    public void setWebsiteThemeId(String websiteThemeId) {
        this.websiteThemeId = websiteThemeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventWebsitePageBean{");
        sb.append("eventWebsitePageId='").append(eventWebsitePageId).append('\'');
        sb.append(", eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", websiteThemeId='").append(websiteThemeId).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", isShow=").append(isShow);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_website_page_id", this.eventWebsitePageId );
            jsonObject.put("event_website_id", this.eventWebsiteId );
            jsonObject.put("website_theme_id", this.websiteThemeId );
            jsonObject.put("type", this.type );
            jsonObject.put("is_show", this.isShow );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
