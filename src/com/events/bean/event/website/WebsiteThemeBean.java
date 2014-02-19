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
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteThemeBean {
    //GTWEBSITETHEME( WEBSITETHEMEID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL, NAME  VARCHAR(500) NOT NULL, SCREENSHOT_NAME  VARCHAR(500) NOT NULL
    private String websiteThemeId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String screenShot = Constants.EMPTY;

    public WebsiteThemeBean(){}
    public WebsiteThemeBean(HashMap<String,String> hmResult) {
        this.websiteThemeId = ParseUtil.checkNull(hmResult.get("WEBSITETHEMEID"));
        this.vendorId = ParseUtil.checkNull(hmResult.get("FK_VENDORID"));
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));
        this.screenShot = ParseUtil.checkNull(hmResult.get("SCREENSHOT_NAME"));
    }
    public String getWebsiteThemeId() {
        return websiteThemeId;
    }

    public void setWebsiteThemeId(String websiteThemeId) {
        this.websiteThemeId = websiteThemeId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(String screenShot) {
        this.screenShot = screenShot;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebsiteThemeBean{");
        sb.append("websiteThemeId='").append(websiteThemeId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", screenShot='").append(screenShot).append('\'');
        sb.append('}');
        return sb.toString();
    }
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("website_theme_id", this.websiteThemeId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("name", this.name );
            jsonObject.put("screen", this.screenShot );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
