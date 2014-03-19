package com.events.common.email.setting;

import com.events.bean.common.FeatureBean;
import com.events.bean.common.email.EventEmailFeatureBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.email.EventEmailFeatureData;
import com.events.data.feature.FeatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 8:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventEmailFeature {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EventEmailFeatureBean getFeature(EventEmailFeatureBean eventEmailFeatureBean) {

        EventEmailFeatureBean eventEmailFeatureBeanFromDB = new EventEmailFeatureBean();
        if(eventEmailFeatureBean!=null &&  !Utility.isNullOrEmpty(eventEmailFeatureBean.getFeatureType().toString()) ) {
            EventEmailFeatureData eventEmailFeatureData = new EventEmailFeatureData();
            eventEmailFeatureBeanFromDB = eventEmailFeatureData.getEventEmailFeature(eventEmailFeatureBean);
        }
        return eventEmailFeatureBeanFromDB;
    }

    public ArrayList<EventEmailFeatureBean> getMultipleFeatures(ArrayList<EventEmailFeatureBean> arrEventEmailFeatureBean, String sEventEmailId) {
        ArrayList<EventEmailFeatureBean> arrMultipleFeatureBean = new ArrayList<EventEmailFeatureBean>();
        if(arrEventEmailFeatureBean!=null && !arrEventEmailFeatureBean.isEmpty()) {
            EventEmailFeatureData eventEmailFeatureData = new EventEmailFeatureData();
            arrMultipleFeatureBean = eventEmailFeatureData.getMultipleFeatures(arrEventEmailFeatureBean , sEventEmailId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(EventEmailFeatureBean eventEmailFeatureBean) {
        Integer iNumOfRows = 0;
        if(eventEmailFeatureBean!=null && !"".equalsIgnoreCase(eventEmailFeatureBean.getValue()) && !"".equalsIgnoreCase(ParseUtil.checkNull(eventEmailFeatureBean.getFeatureType().toString()))) {

            EventEmailFeatureBean currentEventEmailFeatureBean = getFeature(eventEmailFeatureBean);
            EventEmailFeatureData eventEmailFeatureData = new EventEmailFeatureData();

            if(currentEventEmailFeatureBean!=null && !"".equalsIgnoreCase(currentEventEmailFeatureBean.getEventEmailFeatureId())) {
                eventEmailFeatureBean.setEventEmailFeatureId(currentEventEmailFeatureBean.getEventEmailFeatureId());
                iNumOfRows = eventEmailFeatureData.updateFeature(eventEmailFeatureBean);
            } else {
                eventEmailFeatureBean.setEventEmailFeatureId(Utility.getNewGuid());
                iNumOfRows = eventEmailFeatureData.insertFeature(eventEmailFeatureBean);
            }

        }
        return iNumOfRows;
    }

    public Integer removeFeatureValue(EventEmailFeatureBean eventEmailFeatureBean) {
        Integer iNumOfRows = 0;
        if(eventEmailFeatureBean!=null && !Utility.isNullOrEmpty(eventEmailFeatureBean.getFeatureType().toString()) && !Utility.isNullOrEmpty( eventEmailFeatureBean.getEventEmailId()) ) {
            EventEmailFeatureData eventEmailFeatureData = new EventEmailFeatureData();
            iNumOfRows = eventEmailFeatureData.deleteFeature( eventEmailFeatureBean );
        }
        return iNumOfRows;
    }
}
