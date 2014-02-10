package com.events.event.vendor;

import com.events.bean.event.vendor.EventVendorFeatureBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.vendor.EventVendorFeatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 5:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventVendorFeature {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EventVendorFeatureBean getFeature(EventVendorFeatureBean eventVendorFeatureBean) {

        EventVendorFeatureBean eventVendorFeatureBeanFromDB = new EventVendorFeatureBean();
        if(eventVendorFeatureBean!=null &&  !Utility.isNullOrEmpty(eventVendorFeatureBean.getFeatureType().toString()) ) {
            EventVendorFeatureData vendorFeatureData = new EventVendorFeatureData();
            eventVendorFeatureBeanFromDB = vendorFeatureData.getVendorFeature(eventVendorFeatureBean);
        }
        return eventVendorFeatureBeanFromDB;
    }

    public ArrayList<EventVendorFeatureBean> getMultipleFeatures(ArrayList<EventVendorFeatureBean> arrEventVendorFeatureBean, String sVendorId) {
        ArrayList<EventVendorFeatureBean> arrMultipleFeatureBean = new ArrayList<EventVendorFeatureBean>();
        if(arrEventVendorFeatureBean!=null && !arrEventVendorFeatureBean.isEmpty()) {
            EventVendorFeatureData vendorFeatureData = new EventVendorFeatureData();
            arrMultipleFeatureBean = vendorFeatureData.getMultipleFeatures(arrEventVendorFeatureBean , sVendorId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(EventVendorFeatureBean eventVendorFeatureBean) {
        Integer iNumOfRows = 0;
        if(eventVendorFeatureBean!=null && !"".equalsIgnoreCase(eventVendorFeatureBean.getValue()) && !"".equalsIgnoreCase(ParseUtil.checkNull(eventVendorFeatureBean.getFeatureType().toString()))) {

            EventVendorFeatureBean currentEventVendorFeatureBean = getFeature(eventVendorFeatureBean);
            EventVendorFeatureData vendorFeatureData = new EventVendorFeatureData();

            if(currentEventVendorFeatureBean!=null && !"".equalsIgnoreCase(currentEventVendorFeatureBean.getEventVendorFeatureId())) {
                eventVendorFeatureBean.setEventVendorFeatureId(currentEventVendorFeatureBean.getEventVendorFeatureId());
                iNumOfRows = vendorFeatureData.updateFeature(eventVendorFeatureBean);
            } else {
                eventVendorFeatureBean.setEventVendorFeatureId(Utility.getNewGuid());
                iNumOfRows = vendorFeatureData.insertFeature(eventVendorFeatureBean);
            }

        }
        return iNumOfRows;
    }

    public static EventVendorFeatureBean generateEventVendorFeatureBean(Constants.EVENT_VENDOR_FEATURETYPE eventVendorFeaturetype) {
        EventVendorFeatureBean eventVendorFeatureBean = new EventVendorFeatureBean();
        eventVendorFeatureBean.setFeatureType(eventVendorFeaturetype);
        return eventVendorFeatureBean;
    }
}
