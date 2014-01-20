package com.events.bean.vendors;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorLandingPageFeatureBean {
    private String vendorLandingPageFeatureId = Constants.EMPTY;
    private String vendorLandingPageId = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private Constants.VENDOR_LANDINGPAGE_FEATURETYPE featureType = Constants.VENDOR_LANDINGPAGE_FEATURETYPE.none;
    private String value = Constants.EMPTY;

    //VENDORLANDINGPAGEFEATUREID VARCHAR(45) NOT NULL, FK_VENDORLANDINGPAGEID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE
    public VendorLandingPageFeatureBean() { }
    public VendorLandingPageFeatureBean(HashMap<String,String> hmResult) {
        this.vendorLandingPageFeatureId = ParseUtil.checkNull(hmResult.get("VENDORLANDINGPAGEFEATUREID"));
        this.vendorLandingPageId = ParseUtil.checkNull(hmResult.get("FK_VENDORLANDINGPAGEID"));
        this.featureName = ParseUtil.checkNull(hmResult.get("FEATURENAME"));
        this.value = ParseUtil.checkNull(hmResult.get("VALUE"));
        if(!Utility.isNullOrEmpty(this.value )){
            this.featureType = Constants.VENDOR_LANDINGPAGE_FEATURETYPE.valueOf(this.featureName);
        }
    }

    public String getVendorLandingPageFeatureId() {
        return vendorLandingPageFeatureId;
    }

    public void setVendorLandingPageFeatureId(String vendorLandingPageFeatureId) {
        this.vendorLandingPageFeatureId = vendorLandingPageFeatureId;
    }

    public String getVendorLandingPageId() {
        return vendorLandingPageId;
    }

    public void setVendorLandingPageId(String vendorLandingPageId) {
        this.vendorLandingPageId = vendorLandingPageId;
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

    public Constants.VENDOR_LANDINGPAGE_FEATURETYPE getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Constants.VENDOR_LANDINGPAGE_FEATURETYPE featureType) {
        this.featureType = featureType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorLandingPageFeatureBean{");
        sb.append("vendorLandingPageFeatureId='").append(vendorLandingPageFeatureId).append('\'');
        sb.append(", vendorLandingPageId='").append(vendorLandingPageId).append('\'');
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append(", featureType=").append(featureType);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vendor_landingpage_feature_id", this.vendorLandingPageFeatureId );
            jsonObject.put("vendor_landingpage_id", this.vendorLandingPageId );
            jsonObject.put("feature_name", this.featureName );
            jsonObject.put("value", this.value );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
