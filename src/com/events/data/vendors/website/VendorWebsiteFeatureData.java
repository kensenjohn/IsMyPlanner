package com.events.data.vendors.website;

import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/27/14
 * Time: 6:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsiteFeatureData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTVENDORWEBSITEFEATURES VENDORWEBSITEFEATUREID VARCHAR(45) NOT NULL, FK_VENDORWEBSITEID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL,
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),FK_USERID
    public Integer insertFeature(VendorWebsiteFeatureBean vendorWebsiteFeatureBean) {
        Integer numOfRowsInserted = 0;
        // EVENTEMAILFEATUREID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, FK_EVENTEMAILID  VARCHAR(45) NOT NULL, VALUE  VARCHAR(500) NOT NULL
        if(vendorWebsiteFeatureBean!=null  && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getVendorWebsiteFeatureId()) ){
            String sQuery = "INSERT INTO GTVENDORWEBSITEFEATURES (VENDORWEBSITEFEATUREID,FK_VENDORWEBSITEID,FEATURENAME,     VALUE,MODIFIEDDATE,HUMANMODIFIEDDATE,   FK_USERID) VALUES (?,?,?,   ?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(vendorWebsiteFeatureBean.getVendorWebsiteFeatureId(), vendorWebsiteFeatureBean.getVendorWebsiteId(), vendorWebsiteFeatureBean.getFeatureType().toString(),
                    vendorWebsiteFeatureBean.getValue(), DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),    vendorWebsiteFeatureBean.getModifiedByUserId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "VendorWebsiteFeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(VendorWebsiteFeatureBean vendorWebsiteFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getVendorWebsiteId()) ){
            String sQuery = "UPDATE GTVENDORWEBSITEFEATURES SET  VALUE = ?,MODIFIEDDATE = ?,HUMANMODIFIEDDATE = ?,   FK_USERID = ? WHERE FEATURENAME = ? AND FK_VENDORWEBSITEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( vendorWebsiteFeatureBean.getValue(), DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),    vendorWebsiteFeatureBean.getModifiedByUserId(),vendorWebsiteFeatureBean.getFeatureType().toString(),vendorWebsiteFeatureBean.getVendorWebsiteId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventEmailFeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }

    public VendorWebsiteFeatureBean getVendorWebsiteFeature(VendorWebsiteFeatureBean vendorWebsiteFeatureBean) {
        VendorWebsiteFeatureBean finalFeatureBean = new VendorWebsiteFeatureBean();
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getVendorWebsiteId())){
            String sQuery = "SELECT * FROM GTVENDORWEBSITEFEATURES WHERE  FEATURENAME = ? AND FK_VENDORWEBSITEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( vendorWebsiteFeatureBean.getFeatureType().toString(),vendorWebsiteFeatureBean.getVendorWebsiteId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorWebsiteFeatureData.java", "getVendoWebsiteFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new VendorWebsiteFeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }

    public ArrayList<VendorWebsiteFeatureBean> getMultipleFeatures(ArrayList<VendorWebsiteFeatureBean> arrVendorWebsiteFeatureBean, String sVendorLandingPageId) {

        ArrayList<VendorWebsiteFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorWebsiteFeatureBean>();

        String sQuery = "SELECT * FROM GTVENDORWEBSITEFEATURES WHERE FK_VENDORWEBSITEID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sVendorLandingPageId);
        if(arrVendorWebsiteFeatureBean!=null && !arrVendorWebsiteFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrVendorWebsiteFeatureBean.size()) + ")";
            for(VendorWebsiteFeatureBean tmpVendorWebsiteFeatureBean : arrVendorWebsiteFeatureBean ) {
                aParams.add(tmpVendorWebsiteFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorWebsiteFeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                VendorWebsiteFeatureBean vendorWebsiteFeatureBean = new VendorWebsiteFeatureBean(hmResult);
                arrMultipleFeatureBean.add(vendorWebsiteFeatureBean);
            }
        }
        return arrMultipleFeatureBean;
    }

    public HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> getMultipleFeaturesWithDefaultValue(HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmDefaultVendorWebsiteFeatureBean, String sVendorLandingPageId) {

        HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmFinalVendorWebsiteFeatureBean = new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE, VendorWebsiteFeatureBean>();

        ArrayList<VendorWebsiteFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorWebsiteFeatureBean>();

        String sQuery = "SELECT * FROM GTVENDORWEBSITEFEATURES WHERE FK_VENDORWEBSITEID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sVendorLandingPageId);
        if(hmDefaultVendorWebsiteFeatureBean!=null && !hmDefaultVendorWebsiteFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(hmDefaultVendorWebsiteFeatureBean.size()) + ")";

            for(Map.Entry<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean > mapVendorWebsiteFeatureBean : hmDefaultVendorWebsiteFeatureBean.entrySet()) {
                aParams.add(mapVendorWebsiteFeatureBean.getKey().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorWebsiteFeatureData.java", "getMultipleFeatures()");
        HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmFromDBVendorWebsiteFeatureBean = new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE, VendorWebsiteFeatureBean>();

        if( arrResult!=null && !arrResult.isEmpty() ) {

            for(HashMap<String, String> hmResult : arrResult ) {
                VendorWebsiteFeatureBean vendorWebsiteFeatureBean = new VendorWebsiteFeatureBean(hmResult);
                hmFromDBVendorWebsiteFeatureBean.put(vendorWebsiteFeatureBean.getFeatureType() , vendorWebsiteFeatureBean );
            }
        }
        for(Map.Entry<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean > mapVendorWebsiteFeatureBean : hmDefaultVendorWebsiteFeatureBean.entrySet()) {
            VendorWebsiteFeatureBean tmpVendorWebsiteFeatureBean = hmFromDBVendorWebsiteFeatureBean.get( mapVendorWebsiteFeatureBean.getKey() );
            if(tmpVendorWebsiteFeatureBean != null && !Utility.isNullOrEmpty(tmpVendorWebsiteFeatureBean.getValue())) {
                hmFinalVendorWebsiteFeatureBean.put(tmpVendorWebsiteFeatureBean.getFeatureType() ,tmpVendorWebsiteFeatureBean );
            } else {
                hmFinalVendorWebsiteFeatureBean.put(mapVendorWebsiteFeatureBean.getKey() ,mapVendorWebsiteFeatureBean.getValue() );
            }
        }
        return hmFinalVendorWebsiteFeatureBean;
    }
}
