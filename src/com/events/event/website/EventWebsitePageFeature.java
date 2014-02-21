package com.events.event.website;

import com.events.bean.event.website.EventWebsitePageFeatureBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.EventWebsitePageFeatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/21/14
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventWebsitePageFeature {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EventWebsitePageFeatureBean getFeature(EventWebsitePageFeatureBean eventEventWebsitePageFeatureBean) {

        EventWebsitePageFeatureBean eventEventWebsitePageFeatureBeanFromDB = new EventWebsitePageFeatureBean();
        if(eventEventWebsitePageFeatureBean!=null &&  !Utility.isNullOrEmpty(eventEventWebsitePageFeatureBean.getFeatureType().toString()) ) {
            EventWebsitePageFeatureData eventWebsitePageFeatureData = new EventWebsitePageFeatureData();
            eventEventWebsitePageFeatureBeanFromDB = eventWebsitePageFeatureData.getEventWebsitePageFeature(eventEventWebsitePageFeatureBean);
        }
        return eventEventWebsitePageFeatureBeanFromDB;
    }

    public ArrayList<EventWebsitePageFeatureBean> getMultipleFeatures(ArrayList<EventWebsitePageFeatureBean> arrEventWebsitePageFeatureBean,  String sEventWebsitePageId) {
        ArrayList<EventWebsitePageFeatureBean> arrMultipleFeatureBean = new ArrayList<EventWebsitePageFeatureBean>();
        if(arrEventWebsitePageFeatureBean!=null && !arrEventWebsitePageFeatureBean.isEmpty()) {
            EventWebsitePageFeatureData eventWebsitePageFeatureData = new EventWebsitePageFeatureData();
            arrMultipleFeatureBean = eventWebsitePageFeatureData.getMultipleFeatures(arrEventWebsitePageFeatureBean , sEventWebsitePageId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(EventWebsitePageFeatureBean eventWebsitePageFeatureBean) {
        Integer iNumOfRows = 0;
        if(eventWebsitePageFeatureBean!=null && !"".equalsIgnoreCase(eventWebsitePageFeatureBean.getValue())
                && !"".equalsIgnoreCase(ParseUtil.checkNull(eventWebsitePageFeatureBean.getFeatureType().toString()))) {

            EventWebsitePageFeatureBean currentEventWebsitePageFeatureBean = getFeature(eventWebsitePageFeatureBean);
            EventWebsitePageFeatureData eventWebsitePageFeatureData = new EventWebsitePageFeatureData();

            if(currentEventWebsitePageFeatureBean!=null && !"".equalsIgnoreCase(currentEventWebsitePageFeatureBean.getEventWebsitePageFeatureId())) {
                eventWebsitePageFeatureBean.setEventWebsitePageFeatureId(currentEventWebsitePageFeatureBean.getEventWebsitePageFeatureId());
                iNumOfRows = eventWebsitePageFeatureData.updateFeature(eventWebsitePageFeatureBean);
            } else {
                eventWebsitePageFeatureBean.setEventWebsitePageFeatureId(Utility.getNewGuid());
                iNumOfRows = eventWebsitePageFeatureData.insertFeature(eventWebsitePageFeatureBean);
            }

        }
        return iNumOfRows;
    }

    public Integer deleteFeatureValue(EventWebsitePageFeatureBean eventWebsitePageFeatureBean) {
        Integer iNumOfRows = 0;
        if(eventWebsitePageFeatureBean!=null && !Utility.isNullOrEmpty(eventWebsitePageFeatureBean.getEventWebsitePageId())
                && eventWebsitePageFeatureBean.getFeatureType()!=null ) {
            EventWebsitePageFeatureData eventVendorFeatureData = new EventWebsitePageFeatureData();
            iNumOfRows = eventVendorFeatureData.deleteFeature( eventWebsitePageFeatureBean );
        }
        return iNumOfRows;
    }

    public static EventWebsitePageFeatureBean generateEventWebsitePageFeatureBean(Constants.EVENT_WEBSITE_PAGE_FEATURETYPE eventWebsitePageFeaturetype) {
        EventWebsitePageFeatureBean eventWebsitePageFeatureBean = new EventWebsitePageFeatureBean();
        eventWebsitePageFeatureBean.setFeatureType(eventWebsitePageFeaturetype);
        return eventWebsitePageFeatureBean;
    }
}
