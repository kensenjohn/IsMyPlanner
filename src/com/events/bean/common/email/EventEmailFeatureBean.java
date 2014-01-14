package com.events.bean.common.email;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventEmailFeatureBean {
    //EVENTEMAILFEATUREID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, FK_EVENTEMAILID
    private String eventEmailFeatureId = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private Constants.EventEmailFeatureType featureType = Constants.EventEmailFeatureType.none;
    private String eventEmailId = Constants.EMPTY;
    private String value = Constants.EMPTY;

    public EventEmailFeatureBean(){}
    public EventEmailFeatureBean(HashMap<String,String> hmResult){
        this.eventEmailFeatureId = ParseUtil.checkNull(hmResult.get("EVENTEMAILFEATUREID")) ;
        this.value = ParseUtil.checkNull( hmResult.get("VALUE") ) ;

        this.featureName =  ParseUtil.checkNull( hmResult.get("FEATURENAME") ) ;
        if(this.featureName!=null && !"".equalsIgnoreCase(this.featureName)) {
            this.featureType = Constants.EventEmailFeatureType.valueOf(this.featureName);
        }
    }

    public Constants.EventEmailFeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Constants.EventEmailFeatureType featureType) {
        this.featureType = featureType;
    }

    public String getEventEmailFeatureId() {
        return eventEmailFeatureId;
    }

    public void setEventEmailFeatureId(String eventEmailFeatureId) {
        this.eventEmailFeatureId = eventEmailFeatureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getEventEmailId() {
        return eventEmailId;
    }

    public void setEventEmailId(String eventEmailId) {
        this.eventEmailId = eventEmailId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventEmailFeatureBean{");
        sb.append("eventEmailFeatureId='").append(eventEmailFeatureId).append('\'');
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append(", featureType='").append(featureType.toString()).append('\'');
        sb.append(", eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
