package com.events.bean.event.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventWebsiteBean {
    // GTEVENTWEBSITE ( EVENTWEBSITEID VARCHAR(45) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL,  FK_WEBSITETHEMEID VARCHAR(45) NOT NULL,
    // FK_WEBSITEFONTID VARCHAR(45) NOT NULL,FK_WEBSITECOLORID VARCHAR(45) NOT NULL, FK_USERID  VARCHAR(45) NOT NULL

    private String eventWebsiteId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String websiteThemeId = Constants.EMPTY;
    private String websiteFontId = Constants.EMPTY;
    private String websiteColorId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String urlUniqueName = Constants.EMPTY;

    public EventWebsiteBean(){}
    public EventWebsiteBean(HashMap<String,String> hmResult) {
        this.eventWebsiteId = ParseUtil.checkNull(hmResult.get("EVENTWEBSITEID"));
        this.eventId = ParseUtil.checkNull(hmResult.get("FK_EVENTID"));
        this.websiteThemeId = ParseUtil.checkNull(hmResult.get("FK_WEBSITETHEMEID"));
        this.websiteFontId = ParseUtil.checkNull(hmResult.get("FK_WEBSITEFONTID"));
        this.websiteColorId = ParseUtil.checkNull(hmResult.get("FK_WEBSITECOLORID"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
        this.urlUniqueName = ParseUtil.checkNull(hmResult.get("URL_UNIQUE_NAME"));
    }

    public String getUrlUniqueName() {
        return urlUniqueName;
    }

    public void setUrlUniqueName(String urlUniqueName) {
        this.urlUniqueName = urlUniqueName;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getWebsiteThemeId() {
        return websiteThemeId;
    }

    public void setWebsiteThemeId(String websiteThemeId) {
        this.websiteThemeId = websiteThemeId;
    }

    public String getWebsiteFontId() {
        return websiteFontId;
    }

    public void setWebsiteFontId(String websiteFontId) {
        this.websiteFontId = websiteFontId;
    }

    public String getWebsiteColorId() {
        return websiteColorId;
    }

    public void setWebsiteColorId(String websiteColorId) {
        this.websiteColorId = websiteColorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventWebsiteBean{");
        sb.append("eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", websiteThemeId='").append(websiteThemeId).append('\'');
        sb.append(", websiteFontId='").append(websiteFontId).append('\'');
        sb.append(", websiteColorId='").append(websiteColorId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", urlUniqueName='").append(urlUniqueName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_website_id", this.eventWebsiteId );
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("website_theme_id", this.websiteThemeId );
            jsonObject.put("website_font_id", this.websiteFontId );
            jsonObject.put("website_color_id", this.websiteColorId );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("url_unique_name", this.urlUniqueName );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
