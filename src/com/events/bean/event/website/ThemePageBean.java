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
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThemePageBean {
    // GTTHEMEPAGE(THEMEPAGEID  VARCHAR(45) NOT NULL ,   FK_WEBSITETHEMEID  VARCHAR(45) NOT NULL , TYPE  VARCHAR(45) NOT NULL,
    // IS_SHOW INT(1) NOT NULL DEFAULT 0,  PRIMARY KEY (THEMEPAGEID) )
    private String themePageId = Constants.EMPTY;
    private String websiteThemeId = Constants.EMPTY;
    private String type = Constants.EMPTY;
    private boolean isShow = false;

    public ThemePageBean(){}
    public ThemePageBean(HashMap<String,String> hmResult) {
        this.themePageId = ParseUtil.checkNull(hmResult.get("THEMEPAGEID"));
        this.websiteThemeId = ParseUtil.checkNull(hmResult.get("FK_WEBSITETHEMEID"));
        this.type = ParseUtil.checkNull(hmResult.get("TYPE"));
        this.isShow = ParseUtil.sTob(hmResult.get("IS_SHOW"));
    }

    public String getThemePageId() {
        return themePageId;
    }

    public void setThemePageId(String themePageId) {
        this.themePageId = themePageId;
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
        final StringBuilder sb = new StringBuilder("ThemePageBean{");
        sb.append("themePageId='").append(themePageId).append('\'');
        sb.append(", websiteThemeId='").append(websiteThemeId).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", isShow=").append(isShow);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("theme_page_id", this.themePageId );
            jsonObject.put("website_theme_id", this.websiteThemeId );
            jsonObject.put("type", this.type );
            jsonObject.put("is_show", this.isShow );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
