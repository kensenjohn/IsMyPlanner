package com.events.bean.event.vendor;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 5:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventVendorFeatureBean {

    private String eventVendorFeatureId = Constants.EMPTY;
    private String eventVendorId = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private Constants.EVENT_VENDOR_FEATURETYPE featureType = Constants.EVENT_VENDOR_FEATURETYPE.none;
    private String value = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    public EventVendorFeatureBean(){}
    public EventVendorFeatureBean(HashMap<String,String> hmResult){
        this.eventVendorFeatureId = ParseUtil.checkNull(hmResult.get("EVENTVENDORFEATUREID")) ;
        this.eventVendorId = ParseUtil.checkNull(hmResult.get("FK_EVENTVENDORID")) ;
        this.value = ParseUtil.checkNull( hmResult.get("VALUE") ) ;

        this.featureName =  ParseUtil.checkNull( hmResult.get("FEATURENAME") ) ;
        if(this.featureName!=null && !"".equalsIgnoreCase(this.featureName)) {
            this.featureType = Constants.EVENT_VENDOR_FEATURETYPE.valueOf(this.featureName);
        }
    }

    public String getEventVendorFeatureId() {
        return eventVendorFeatureId;
    }

    public void setEventVendorFeatureId(String eventVendorFeatureId) {
        this.eventVendorFeatureId = eventVendorFeatureId;
    }

    public String getEventVendorId() {
        return eventVendorId;
    }

    public void setEventVendorId(String eventVendorId) {
        this.eventVendorId = eventVendorId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Constants.EVENT_VENDOR_FEATURETYPE getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Constants.EVENT_VENDOR_FEATURETYPE featureType) {
        this.featureType = featureType;
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
