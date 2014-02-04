package com.events.bean.vendors;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorFeatureBean {
    //GTVENDORFEATURES ( VENDORFEATUREID VARCHAR(45) NOT NULL, FK_VENDORID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL,MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),FK_USERID VARCHAR(45) NOT NULL, PRIMARY KEY (VENDORFEATUREID)
    private String vendorFeatureId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private Constants.VENDOR_FEATURETYPE featureType = Constants.VENDOR_FEATURETYPE.none;
    private String value = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    public VendorFeatureBean(){}
    public VendorFeatureBean(HashMap<String,String> hmResult){
        this.vendorFeatureId = ParseUtil.checkNull(hmResult.get("VENDORFEATUREID")) ;
        this.vendorId = ParseUtil.checkNull(hmResult.get("FK_VENDORID")) ;
        this.value = ParseUtil.checkNull( hmResult.get("VALUE") ) ;

        this.featureName =  ParseUtil.checkNull( hmResult.get("FEATURENAME") ) ;
        if(this.featureName!=null && !"".equalsIgnoreCase(this.featureName)) {
            this.featureType = Constants.VENDOR_FEATURETYPE.valueOf(this.featureName);
        }
    }

    public String getVendorFeatureId() {
        return vendorFeatureId;
    }

    public void setVendorFeatureId(String vendorFeatureId) {
        this.vendorFeatureId = vendorFeatureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Constants.VENDOR_FEATURETYPE getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Constants.VENDOR_FEATURETYPE featureType) {
        this.featureType = featureType;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
