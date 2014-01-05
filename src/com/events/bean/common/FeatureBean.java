package com.events.bean.common;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.feature.FeatureType;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/22/13
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeatureBean {
    private String featureId = Constants.EMPTY;
    private String featureName = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String value = Constants.EMPTY;
    private FeatureType featureType = null;

    public FeatureBean(){}
    public FeatureBean(HashMap<String,String> hmResult){
        this.featureId = ParseUtil.checkNull( hmResult.get("FEATUREID") ) ;
        this.eventId = ParseUtil.checkNull( hmResult.get("FK_EVENTID") ) ;
        this.value = ParseUtil.checkNull( hmResult.get("VALUE") ) ;

        this.featureName =  ParseUtil.checkNull( hmResult.get("FEATURENAME") ) ;
        if(this.featureName!=null && !"".equalsIgnoreCase(this.featureName)) {
            this.featureType = FeatureType.valueOf(this.featureName);
        }
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeatureBean{");
        sb.append("featureId='").append(featureId).append('\'');
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", featureType=").append(featureType);
        sb.append('}');
        return sb.toString();
    }
}
