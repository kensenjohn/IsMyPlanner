package com.events.data.vendors;

import com.events.bean.vendors.VendorFeatureBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorFeatureData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    //GTVENDORFEATURES ( VENDORFEATUREID VARCHAR(45) NOT NULL, FK_VENDORID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL,MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),FK_USERID VARCHAR(45) NOT NULL, PRIMARY KEY (VENDORFEATUREID)

    public Integer insertFeature(VendorFeatureBean vendorFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(vendorFeatureBean!=null  && !Utility.isNullOrEmpty(vendorFeatureBean.getVendorId()) ){
            String sQuery = "INSERT INTO GTVENDORFEATURES (VENDORFEATUREID,FEATURENAME,FK_VENDORID," +
                    "     VALUE,MODIFIEDDATE,HUMANMODIFIEDDATE,   FK_USERID  ) VALUES (?,?,?,   ?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(vendorFeatureBean.getVendorFeatureId(), vendorFeatureBean.getFeatureType().toString(), vendorFeatureBean.getVendorId(),
                    vendorFeatureBean.getValue(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                    vendorFeatureBean.getUserId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "VendorFeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(VendorFeatureBean vendorFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(vendorFeatureBean!=null && !Utility.isNullOrEmpty(vendorFeatureBean.getVendorFeatureId()) ){
            String sQuery = "UPDATE GTVENDORFEATURES SET  VALUE = ? , FK_USERID = ?,MODIFIEDDATE = ?,HUMANMODIFIEDDATE =?   WHERE FEATURENAME = ? AND FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( vendorFeatureBean.getValue(),
                    vendorFeatureBean.getUserId(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                    vendorFeatureBean.getFeatureType().toString(),vendorFeatureBean.getVendorId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "VendorFeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }

    public VendorFeatureBean getVendorFeature(VendorFeatureBean vendorFeatureBean) {
        VendorFeatureBean finalFeatureBean = new VendorFeatureBean();
        if(vendorFeatureBean!=null && !Utility.isNullOrEmpty(vendorFeatureBean.getVendorId())){
            String sQuery = "SELECT * FROM GTVENDORFEATURES WHERE  FEATURENAME = ? AND FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( vendorFeatureBean.getFeatureType().toString(),vendorFeatureBean.getVendorId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorFeatureData.java", "getVendorFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new VendorFeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }

    public ArrayList<VendorFeatureBean> getMultipleFeatures(ArrayList<VendorFeatureBean> arrVendorFeatureBean, String sVendorId) {

        ArrayList<VendorFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorFeatureBean>();

        String sQuery = "SELECT * FROM GTVENDORFEATURES WHERE FK_VENDORID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sVendorId);
        if(arrVendorFeatureBean!=null && !arrVendorFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrVendorFeatureBean.size()) + ")";
            for(VendorFeatureBean tmpventEmailFeatureBean : arrVendorFeatureBean ) {
                aParams.add(tmpventEmailFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorFeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                VendorFeatureBean vendorFeatureBean = new VendorFeatureBean(hmResult);
                arrMultipleFeatureBean.add(vendorFeatureBean);
            }
        }
        return arrMultipleFeatureBean;
    }

}
