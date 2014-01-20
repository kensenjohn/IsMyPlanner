package com.events.data.vendors;

import com.events.bean.vendors.VendorLandingPageFeatureBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorLandingPageFeatureData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTVENDORLANDINGPAGEFEATURES ( VENDORLANDINGPAGEFEATUREID VARCHAR(45) NOT NULL, FK_VENDORLANDINGPAGEID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE
    public Integer insertFeature(VendorLandingPageFeatureBean vendorLandingPageFeatureBean) {
        Integer numOfRowsInserted = 0;
        // EVENTEMAILFEATUREID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, FK_EVENTEMAILID  VARCHAR(45) NOT NULL, VALUE  VARCHAR(500) NOT NULL
        if(vendorLandingPageFeatureBean!=null  && !Utility.isNullOrEmpty(vendorLandingPageFeatureBean.getVendorLandingPageFeatureId()) ){
            String sQuery = "INSERT INTO GTVENDORLANDINGPAGEFEATURES (VENDORLANDINGPAGEFEATUREID,FK_VENDORLANDINGPAGEID,FEATURENAME,     VALUE) VALUES (?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(vendorLandingPageFeatureBean.getVendorLandingPageFeatureId(), vendorLandingPageFeatureBean.getVendorLandingPageId(), vendorLandingPageFeatureBean.getFeatureType().toString(),
                    vendorLandingPageFeatureBean.getValue());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "VendorLandingPageFeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(VendorLandingPageFeatureBean vendorLandingPageFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(vendorLandingPageFeatureBean!=null && !Utility.isNullOrEmpty(vendorLandingPageFeatureBean.getVendorLandingPageId()) ){
            String sQuery = "UPDATE GTVENDORLANDINGPAGEFEATURES SET  VALUE = ? WHERE FEATURENAME = ? AND FK_VENDORLANDINGPAGEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( vendorLandingPageFeatureBean.getValue(),vendorLandingPageFeatureBean.getFeatureType().toString(),vendorLandingPageFeatureBean.getVendorLandingPageId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventEmailFeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }

    public VendorLandingPageFeatureBean getVendorLandingPageFeature(VendorLandingPageFeatureBean vendorLandingPageFeatureBean) {
        VendorLandingPageFeatureBean finalFeatureBean = new VendorLandingPageFeatureBean();
        if(vendorLandingPageFeatureBean!=null && !Utility.isNullOrEmpty(vendorLandingPageFeatureBean.getVendorLandingPageId())){
            String sQuery = "SELECT * FROM GTVENDORLANDINGPAGEFEATURES WHERE  FEATURENAME = ? AND FK_VENDORLANDINGPAGEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( vendorLandingPageFeatureBean.getFeatureType().toString(),vendorLandingPageFeatureBean.getVendorLandingPageId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorLandingPageFeatureData.java", "getVendorLandingPageFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new VendorLandingPageFeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }

    public ArrayList<VendorLandingPageFeatureBean> getMultipleFeatures(ArrayList<VendorLandingPageFeatureBean> arrVendorLandingPageFeatureBean, String sVendorLandingPageId) {

        ArrayList<VendorLandingPageFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorLandingPageFeatureBean>();

        String sQuery = "SELECT * FROM GTVENDORLANDINGPAGEFEATURES WHERE FK_VENDORLANDINGPAGEID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sVendorLandingPageId);
        if(arrVendorLandingPageFeatureBean!=null && !arrVendorLandingPageFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrVendorLandingPageFeatureBean.size()) + ")";
            for(VendorLandingPageFeatureBean tmpVendorLandingPageFeatureBean : arrVendorLandingPageFeatureBean ) {
                aParams.add(tmpVendorLandingPageFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorLandingPageFeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                VendorLandingPageFeatureBean vendorLandingPageFeatureBean = new VendorLandingPageFeatureBean(hmResult);
                arrMultipleFeatureBean.add(vendorLandingPageFeatureBean);
            }
        }
        return arrMultipleFeatureBean;
    }
}
