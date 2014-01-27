package com.events.bean.vendors.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsiteFeatureBean {
    //VENDORWEBSITEFEATUREID VARCHAR(45) NOT NULL, FK_VENDORWEBSITEID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL,
    // VALUE  TEXT NOT NULL,MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),FK_USERID
    private String vendorWebsiteFeatureId = Constants.EMPTY;
    private String vendorWebsiteId = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private Constants.VENDOR_WEBSITE_FEATURETYPE featureType = Constants.VENDOR_WEBSITE_FEATURETYPE.none;
    private String value = Constants.EMPTY;
    private Long modifiedDate = 0L;
    private String humanModifiedDate = Constants.EMPTY;
    private String modifiedByUserId = Constants.EMPTY;

    public VendorWebsiteFeatureBean(){}
    public VendorWebsiteFeatureBean(HashMap<String,String> hmResult) {
        this.vendorWebsiteFeatureId = ParseUtil.checkNull(hmResult.get("VENDORWEBSITEFEATUREID"));
        this.vendorWebsiteId = ParseUtil.checkNull(hmResult.get("FK_VENDORWEBSITEID"));
        this.featureName = ParseUtil.checkNull(hmResult.get("FEATURENAME"));
        this.value = ParseUtil.checkNull(hmResult.get("VALUE"));
        this.modifiedDate = ParseUtil.sToL(hmResult.get("MODIFIEDDATE"));
        this.humanModifiedDate = ParseUtil.checkNull(hmResult.get("HUMANMODIFIEDDATE"));
        this.modifiedByUserId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
        if(!Utility.isNullOrEmpty(this.value)){
            this.featureType = Constants.VENDOR_WEBSITE_FEATURETYPE.valueOf(this.featureName);
        }
    }

    public String getVendorWebsiteFeatureId() {
        return vendorWebsiteFeatureId;
    }

    public void setVendorWebsiteFeatureId(String vendorWebsiteFeatureId) {
        this.vendorWebsiteFeatureId = vendorWebsiteFeatureId;
    }

    public String getVendorWebsiteId() {
        return vendorWebsiteId;
    }

    public void setVendorWebsiteId(String vendorWebsiteId) {
        this.vendorWebsiteId = vendorWebsiteId;
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

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getHumanModifiedDate() {
        return humanModifiedDate;
    }

    public void setHumanModifiedDate(String humanModifiedDate) {
        this.humanModifiedDate = humanModifiedDate;
    }

    public String getModifiedByUserId() {
        return modifiedByUserId;
    }

    public void setModifiedByUserId(String modifiedByUserId) {
        this.modifiedByUserId = modifiedByUserId;
    }

    public Constants.VENDOR_WEBSITE_FEATURETYPE getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Constants.VENDOR_WEBSITE_FEATURETYPE featureType) {
        this.featureType = featureType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorWebsiteFeatureBean{");
        sb.append("vendorWebsiteFeatureId='").append(vendorWebsiteFeatureId).append('\'');
        sb.append(", vendorWebsiteId='").append(vendorWebsiteId).append('\'');
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append(", featureType=").append(featureType);
        sb.append(", value='").append(value).append('\'');
        sb.append(", modifiedDate=").append(modifiedDate);
        sb.append(", humanModifiedDate='").append(humanModifiedDate).append('\'');
        sb.append(", modifiedByUserId='").append(modifiedByUserId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
