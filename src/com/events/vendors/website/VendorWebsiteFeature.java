package com.events.vendors.website;

import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.website.VendorWebsiteFeatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/27/14
 * Time: 5:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsiteFeature {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public VendorWebsiteFeatureBean getFeature(VendorWebsiteFeatureBean vendorWebsiteFeatureBean) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBeanFromDB = new VendorWebsiteFeatureBean();
        if(vendorWebsiteFeatureBean!=null &&  !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getFeatureType().toString()) ) {
            VendorWebsiteFeatureData vendorWebsiteFeatureData = new VendorWebsiteFeatureData();
            vendorWebsiteFeatureBeanFromDB = vendorWebsiteFeatureData.getVendorWebsiteFeature(vendorWebsiteFeatureBean);
        }
        return vendorWebsiteFeatureBeanFromDB;
    }

    public ArrayList<VendorWebsiteFeatureBean> getMultipleFeatures(ArrayList<VendorWebsiteFeatureBean> arrVendorWebsiteFeatureBean, String sVendorLandingPageId) {
        ArrayList<VendorWebsiteFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorWebsiteFeatureBean>();
        if(arrVendorWebsiteFeatureBean!=null && !arrVendorWebsiteFeatureBean.isEmpty()) {
            VendorWebsiteFeatureData vendorWebsiteFeatureData = new VendorWebsiteFeatureData();
            arrMultipleFeatureBean = vendorWebsiteFeatureData.getMultipleFeatures(arrVendorWebsiteFeatureBean , sVendorLandingPageId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(VendorWebsiteFeatureBean vendorWebsiteFeatureBean) {
        Integer iNumOfRows = 0;
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue()) && !Utility.isNullOrEmpty(ParseUtil.checkNull(vendorWebsiteFeatureBean.getFeatureType().toString()))) {

            VendorWebsiteFeatureBean currentVendorWebsiteFeatureBean = getFeature(vendorWebsiteFeatureBean);
            VendorWebsiteFeatureData vendorWebsiteFeatureData = new VendorWebsiteFeatureData();

            if(currentVendorWebsiteFeatureBean!=null && !"".equalsIgnoreCase(currentVendorWebsiteFeatureBean.getVendorWebsiteFeatureId())) {
                vendorWebsiteFeatureBean.setVendorWebsiteFeatureId(currentVendorWebsiteFeatureBean.getVendorWebsiteFeatureId());
                iNumOfRows = vendorWebsiteFeatureData.updateFeature(vendorWebsiteFeatureBean);
            } else {
                vendorWebsiteFeatureBean.setVendorWebsiteFeatureId(Utility.getNewGuid());
                iNumOfRows = vendorWebsiteFeatureData.insertFeature(vendorWebsiteFeatureBean);
            }

        }
        return iNumOfRows;
    }
}
