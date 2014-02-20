package com.events.bean.event.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/18/14
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteFontBean {
    //GTWEBSITEFONT ( WEBSITEFONTID , FK_WEBSITETHEMEID , FONT_NAME , FONT_CSS_NAME)
    private String websiteFontId = Constants.EMPTY;
    private String websiteThemeId = Constants.EMPTY;
    private String fontName = Constants.EMPTY;
    private String fontCssName = Constants.EMPTY;
    private boolean isDefault = false;

    public WebsiteFontBean(){}
    public WebsiteFontBean(HashMap<String,String> hmResult) {
        this.websiteThemeId = ParseUtil.checkNull(hmResult.get("FK_WEBSITETHEMEID"));
        this.websiteFontId = ParseUtil.checkNull(hmResult.get("WEBSITEFONTID"));
        this.fontName = ParseUtil.checkNull(hmResult.get("FONT_NAME"));
        this.fontCssName = ParseUtil.checkNull(hmResult.get("FONT_CSS_NAME"));
        this.isDefault = ParseUtil.sTob(hmResult.get("IS_DEFAULT"));
    }
    public String getWebsiteFontId() {
        return websiteFontId;
    }

    public void setWebsiteFontId(String websiteFontId) {
        this.websiteFontId = websiteFontId;
    }

    public String getWebsiteThemeId() {
        return websiteThemeId;
    }

    public void setWebsiteThemeId(String websiteThemeId) {
        this.websiteThemeId = websiteThemeId;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontCssName() {
        return fontCssName;
    }

    public void setFontCssName(String fontCssName) {
        this.fontCssName = fontCssName;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebsiteFontBean{");
        sb.append("websiteFontId='").append(websiteFontId).append('\'');
        sb.append(", websiteThemeId='").append(websiteThemeId).append('\'');
        sb.append(", fontName='").append(fontName).append('\'');
        sb.append(", fontCssName='").append(fontCssName).append('\'');
        sb.append(", isDefault=").append(isDefault);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("website_font_id", this.websiteFontId );
            jsonObject.put("website_theme_id", this.websiteThemeId );
            jsonObject.put("font_name", this.fontName );
            jsonObject.put("font_css_name", this.fontCssName );
            jsonObject.put("is_default", this.isDefault );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
