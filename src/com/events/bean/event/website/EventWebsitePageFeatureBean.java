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
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventWebsitePageFeatureBean {
    //GTEVENTWEBSITEPAGEFEATURES ( EVENTWEBSITEPAGEFEATUREID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEPAGEID  VARCHAR(45) NOT NULL,
    // FEATUREDESCRIPTION  VARCHAR(100) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL,
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),FK_USERID

    private String eventWebsitePageFeatureId = Constants.EMPTY;
    private String eventWebsitePageId = Constants.EMPTY;
    private String featureDescription = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private Constants.EVENT_WEBSITE_PAGE_FEATURETYPE featureType = Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.none;
    private String value = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    public EventWebsitePageFeatureBean(){}
    public EventWebsitePageFeatureBean(HashMap<String, String> hmResult) {
        this.eventWebsitePageFeatureId = ParseUtil.checkNull(hmResult.get("EVENTWEBSITEPAGEFEATUREID"));
        this.eventWebsitePageId = ParseUtil.checkNull(hmResult.get("FK_EVENTWEBSITEPAGEID"));
        this.featureDescription = ParseUtil.checkNull(hmResult.get("FEATUREDESCRIPTION"));
        this.featureName = ParseUtil.checkNull(hmResult.get("FEATURENAME"));
        this.value = ParseUtil.checkNull(hmResult.get("VALUE"));

        if(this.featureName!=null && !"".equalsIgnoreCase(this.featureName)) {
            this.featureType = Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.valueOf(this.featureName);
        }
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
    }

    public String getEventWebsitePageFeatureId() {
        return eventWebsitePageFeatureId;
    }

    public void setEventWebsitePageFeatureId(String eventWebsitePageFeatureId) {
        this.eventWebsitePageFeatureId = eventWebsitePageFeatureId;
    }

    public String getEventWebsitePageId() {
        return eventWebsitePageId;
    }

    public void setEventWebsitePageId(String eventWebsitePageId) {
        this.eventWebsitePageId = eventWebsitePageId;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Constants.EVENT_WEBSITE_PAGE_FEATURETYPE getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Constants.EVENT_WEBSITE_PAGE_FEATURETYPE featureType) {
        this.featureType = featureType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventWebsitePageFeatureBean{");
        sb.append("eventWebsitePageFeatureId='").append(eventWebsitePageFeatureId).append('\'');
        sb.append(", eventWebsitePageId='").append(eventWebsitePageId).append('\'');
        sb.append(", featureDescription='").append(featureDescription).append('\'');
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append(", featureType=").append(featureType);
        sb.append(", value='").append(value).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_website_page_feature_id", this.eventWebsitePageFeatureId );
            jsonObject.put("event_website_page_id", this.eventWebsitePageId );
            jsonObject.put("feature_description", this.featureDescription );
            jsonObject.put("feature_name", this.featureName );
            jsonObject.put("value", this.value );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
