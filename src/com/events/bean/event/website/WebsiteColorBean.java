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
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteColorBean {
    //GTWEBSITECOLOR ( WEBSITECOLORID , FK_WEBSITETHEMEID , COLOR_NAME , COLOR_CSS_NAME,COLOR_SWATCH_NAME

    private String websiteColorId = Constants.EMPTY;
    private String websiteThemeId = Constants.EMPTY;
    private String colorName = Constants.EMPTY;
    private String colorCssName = Constants.EMPTY;
    private String colorSwatchName = Constants.EMPTY;

    public WebsiteColorBean(){}
    public WebsiteColorBean(HashMap<String,String> hmResult) {
        this.websiteColorId = ParseUtil.checkNull(hmResult.get("WEBSITECOLORID"));
        this.websiteThemeId = ParseUtil.checkNull(hmResult.get("FK_WEBSITETHEMEID"));
        this.colorName = ParseUtil.checkNull(hmResult.get("COLOR_NAME"));
        this.colorCssName = ParseUtil.checkNull(hmResult.get("COLOR_CSS_NAME"));
        this.colorSwatchName = ParseUtil.checkNull(hmResult.get("COLOR_SWATCH_NAME"));
    }

    public String getWebsiteColorId() {
        return websiteColorId;
    }

    public void setWebsiteColorId(String websiteColorId) {
        this.websiteColorId = websiteColorId;
    }

    public String getWebsiteThemeId() {
        return websiteThemeId;
    }

    public void setWebsiteThemeId(String websiteThemeId) {
        this.websiteThemeId = websiteThemeId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCssName() {
        return colorCssName;
    }

    public void setColorCssName(String colorCssName) {
        this.colorCssName = colorCssName;
    }

    public String getColorSwatchName() {
        return colorSwatchName;
    }

    public void setColorSwatchName(String colorSwatchName) {
        this.colorSwatchName = colorSwatchName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebsiteColorBean{");
        sb.append("websiteColorId='").append(websiteColorId).append('\'');
        sb.append(", websiteThemeId='").append(websiteThemeId).append('\'');
        sb.append(", colorName='").append(colorName).append('\'');
        sb.append(", colorCssName='").append(colorCssName).append('\'');
        sb.append(", colorSwatchName='").append(colorSwatchName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("website_color_id", this.websiteColorId );
            jsonObject.put("website_theme_id", this.websiteThemeId );
            jsonObject.put("color_name", this.colorName );
            jsonObject.put("color_swatch_name", this.colorSwatchName );
            jsonObject.put("color_css_name", this.colorCssName );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
