package com.events.vendors;

import com.events.bean.vendors.VendorLandingPageFeatureBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.VendorLandingPageFeatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorLandingPageFeature {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public VendorLandingPageFeatureBean getFeature(VendorLandingPageFeatureBean vendorLandingPageFeatureBean) {
        VendorLandingPageFeatureBean vendorLandingPageFeatureBeanFromDB = new VendorLandingPageFeatureBean();
        if(vendorLandingPageFeatureBean!=null &&  !Utility.isNullOrEmpty(vendorLandingPageFeatureBean.getFeatureType().toString()) ) {
            VendorLandingPageFeatureData vendorLandingPageFeatureData = new VendorLandingPageFeatureData();
            vendorLandingPageFeatureBeanFromDB = vendorLandingPageFeatureData.getVendorLandingPageFeature(vendorLandingPageFeatureBean);
        }
        return vendorLandingPageFeatureBeanFromDB;
    }

    public ArrayList<VendorLandingPageFeatureBean> getMultipleFeatures(ArrayList<VendorLandingPageFeatureBean> arrVendorLandingPageFeatureBean, String sVendorLandingPageId) {
        ArrayList<VendorLandingPageFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorLandingPageFeatureBean>();
        if(arrVendorLandingPageFeatureBean!=null && !arrVendorLandingPageFeatureBean.isEmpty()) {
            VendorLandingPageFeatureData vendorLandingPageFeatureData = new VendorLandingPageFeatureData();
            arrMultipleFeatureBean = vendorLandingPageFeatureData.getMultipleFeatures(arrVendorLandingPageFeatureBean , sVendorLandingPageId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(VendorLandingPageFeatureBean vendorLandingPageFeatureBean) {
        Integer iNumOfRows = 0;
        if(vendorLandingPageFeatureBean!=null && !Utility.isNullOrEmpty(vendorLandingPageFeatureBean.getValue()) && !Utility.isNullOrEmpty(ParseUtil.checkNull(vendorLandingPageFeatureBean.getFeatureType().toString()))) {

            VendorLandingPageFeatureBean currentVendorLandingPageFeatureBean = getFeature(vendorLandingPageFeatureBean);
            VendorLandingPageFeatureData vendorLandingPageFeatureData = new VendorLandingPageFeatureData();

            if(currentVendorLandingPageFeatureBean!=null && !"".equalsIgnoreCase(currentVendorLandingPageFeatureBean.getVendorLandingPageFeatureId())) {
                vendorLandingPageFeatureBean.setVendorLandingPageFeatureId(currentVendorLandingPageFeatureBean.getVendorLandingPageFeatureId());
                iNumOfRows = vendorLandingPageFeatureData.updateFeature(vendorLandingPageFeatureBean);
            } else {
                vendorLandingPageFeatureBean.setVendorLandingPageFeatureId(Utility.getNewGuid());
                iNumOfRows = vendorLandingPageFeatureData.insertFeature(vendorLandingPageFeatureBean);
            }

        }
        return iNumOfRows;
    }
}
