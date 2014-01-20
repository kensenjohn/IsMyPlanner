package com.events.bean.vendors;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorLandingPageBean {

    private String vendorLandingPageId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String theme = Constants.EMPTY;

    public VendorLandingPageBean(){}
    public VendorLandingPageBean(HashMap<String, String> hmResult) {
        this.vendorLandingPageId = ParseUtil.checkNull( hmResult.get("VENDORLANDINGPAGEID") );
        this.vendorId = ParseUtil.checkNull( hmResult.get("FK_VENDORID") );
        this.theme = ParseUtil.checkNull( hmResult.get("THEME") );
    }
    public String getVendorLandingPageId() {
        return vendorLandingPageId;
    }

    public void setVendorLandingPageId(String vendorLandingPageId) {
        this.vendorLandingPageId = vendorLandingPageId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorLandingPageBean{");
        sb.append("vendorLandingPageId='").append(vendorLandingPageId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", theme='").append(theme).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vendor_landingpage_id", this.vendorLandingPageId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("theme", this.theme );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
