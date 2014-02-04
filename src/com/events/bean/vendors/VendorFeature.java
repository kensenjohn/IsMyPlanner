package com.events.bean.vendors;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.VendorFeatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorFeature {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public VendorFeatureBean getFeature(VendorFeatureBean vendorFeatureBean) {

        VendorFeatureBean vendorFeatureBeanFromDB = new VendorFeatureBean();
        if(vendorFeatureBean!=null &&  !Utility.isNullOrEmpty(vendorFeatureBean.getFeatureType().toString()) ) {
            VendorFeatureData vendorFeatureData = new VendorFeatureData();
            vendorFeatureBeanFromDB = vendorFeatureData.getVendorFeature(vendorFeatureBean);
        }
        return vendorFeatureBeanFromDB;
    }

    public ArrayList<VendorFeatureBean> getMultipleFeatures(ArrayList<VendorFeatureBean> arrVendorFeatureBean, String sVendorId) {
        ArrayList<VendorFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorFeatureBean>();
        if(arrVendorFeatureBean!=null && !arrVendorFeatureBean.isEmpty()) {
            VendorFeatureData vendorFeatureData = new VendorFeatureData();
            arrMultipleFeatureBean = vendorFeatureData.getMultipleFeatures(arrVendorFeatureBean , sVendorId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(VendorFeatureBean vendorFeatureBean) {
        Integer iNumOfRows = 0;
        if(vendorFeatureBean!=null && !"".equalsIgnoreCase(vendorFeatureBean.getValue()) && !"".equalsIgnoreCase(ParseUtil.checkNull(vendorFeatureBean.getFeatureType().toString()))) {

            VendorFeatureBean currentVendorFeatureBean = getFeature(vendorFeatureBean);
            VendorFeatureData vendorFeatureData = new VendorFeatureData();

            if(currentVendorFeatureBean!=null && !"".equalsIgnoreCase(currentVendorFeatureBean.getVendorFeatureId())) {
                vendorFeatureBean.setVendorFeatureId(currentVendorFeatureBean.getVendorFeatureId());
                iNumOfRows = vendorFeatureData.updateFeature(vendorFeatureBean);
            } else {
                vendorFeatureBean.setVendorFeatureId(Utility.getNewGuid());
                iNumOfRows = vendorFeatureData.insertFeature(vendorFeatureBean);
            }

        }
        return iNumOfRows;
    }

    public static VendorFeatureBean generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE vendorFeaturetype) {
        VendorFeatureBean vendorFeatureBean = new VendorFeatureBean();
        vendorFeatureBean.setFeatureType(vendorFeaturetype);
        return vendorFeatureBean;
    }
}
