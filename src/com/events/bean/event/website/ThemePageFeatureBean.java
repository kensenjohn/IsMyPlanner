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
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThemePageFeatureBean {
    //GTTHEMEPAGEFEATURES ( THEMEPAGEFEATUREID VARCHAR(45) NOT NULL, FK_THEMEPAGEID  VARCHAR(45) NOT NULL,
    // FEATUREDESCRIPTION  VARCHAR(100) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL

    private String themePageFeatureId = Constants.EMPTY;
    private String themePageId = Constants.EMPTY;
    private String featureDescription = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private String value = Constants.EMPTY;
    private Constants.EVENT_WEBSITE_PAGE_FEATURETYPE featureType = Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.none;


    public ThemePageFeatureBean(){}
    public ThemePageFeatureBean(HashMap<String,String> hmResult) {
        this.themePageFeatureId = ParseUtil.checkNull(hmResult.get("THEMEPAGEFEATUREID"));
        this.themePageId = ParseUtil.checkNull(hmResult.get("FK_THEMEPAGEID"));
        this.featureDescription = ParseUtil.checkNull(hmResult.get("FEATUREDESCRIPTION"));
        this.featureName = ParseUtil.checkNull(hmResult.get("FEATURENAME"));
        this.value = ParseUtil.checkNull(hmResult.get("VALUE"));
        if(this.featureName!=null && !"".equalsIgnoreCase(this.featureName)) {
            this.featureType = Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.valueOf(this.featureName);
        }
    }

    public String getThemePageFeatureId() {
        return themePageFeatureId;
    }

    public void setThemePageFeatureId(String themePageFeatureId) {
        this.themePageFeatureId = themePageFeatureId;
    }

    public String getThemePageId() {
        return themePageId;
    }

    public void setThemePageId(String themePageId) {
        this.themePageId = themePageId;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ThemePageFeatureBean{");
        sb.append("themePageFeatureId='").append(themePageFeatureId).append('\'');
        sb.append(", themePageId='").append(themePageId).append('\'');
        sb.append(", featureDescription='").append(featureDescription).append('\'');
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", featureType=").append(featureType);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("theme_page_feature_id", this.themePageFeatureId );
            jsonObject.put("theme_page_id", this.themePageId );
            jsonObject.put("feature_description", this.featureDescription );
            jsonObject.put("feature_name", this.featureName );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
