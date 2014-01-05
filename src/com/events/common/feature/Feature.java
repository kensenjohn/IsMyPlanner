package com.events.common.feature;

import com.events.bean.common.FeatureBean;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.feature.FeatureData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/27/13
 * Time: 6:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class Feature {
    public FeatureBean getFeature(FeatureBean featureBean) {
        FeatureBean featureBeanFromDB = new FeatureBean();
        if(featureBean!=null && !"".equalsIgnoreCase(featureBean.getValue()) && !"".equalsIgnoreCase(ParseUtil.checkNull(featureBean.getFeatureType().toString()))) {
            FeatureData featureData = new FeatureData();
            featureBeanFromDB = featureData.getFeature(featureBean);
        }
        return featureBeanFromDB;
    }

    public ArrayList<FeatureBean> getMultipleFeatures(ArrayList<FeatureBean> arrFeatureBean, String sEventId) {
        ArrayList<FeatureBean> arrMultipleFeatureBean = new ArrayList<FeatureBean>();
        if(arrFeatureBean!=null && !arrFeatureBean.isEmpty()) {
            FeatureData featureData = new FeatureData();
            arrMultipleFeatureBean = featureData.getMultipleFeatures(arrFeatureBean , sEventId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(FeatureBean featureBean) {
        Integer iNumOfRows = 0;
        if(featureBean!=null && !"".equalsIgnoreCase(featureBean.getValue()) && !"".equalsIgnoreCase(ParseUtil.checkNull(featureBean.getFeatureType().toString()))) {

            FeatureBean currentFeatureBean = getFeature(featureBean);
            FeatureData featureData = new FeatureData();

            if(currentFeatureBean!=null && !"".equalsIgnoreCase(currentFeatureBean.getFeatureId())) {
                featureBean.setFeatureId( currentFeatureBean.getFeatureId() );
                iNumOfRows = featureData.updateFeature(featureBean);
            } else {
                featureBean.setFeatureId(Utility.getNewGuid());
                iNumOfRows = featureData.insertFeature(featureBean);
            }

        }
        return iNumOfRows;
    }
}

