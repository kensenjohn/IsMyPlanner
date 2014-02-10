package com.events.data.event.vendor;

import com.events.bean.event.vendor.EventVendorFeatureBean;
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
 * Date: 2/10/14
 * Time: 5:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventVendorFeatureData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    //GTEVENTVENDORFEATURES (EVENTVENDORFEATUREID VARCHAR(45) NOT NULL, FK_EVENTVENDORID VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL,MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),FK_USERID VARCHAR(45) NOT NULL

    public Integer insertFeature(EventVendorFeatureBean eventVendorFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(eventVendorFeatureBean!=null  && !Utility.isNullOrEmpty(eventVendorFeatureBean.getEventVendorId()) ){
            String sQuery = "INSERT INTO GTEVENTVENDORFEATURES (EVENTVENDORFEATUREID,FEATURENAME,FK_EVENTVENDORID," +
                    "     VALUE,MODIFIEDDATE,HUMANMODIFIEDDATE,   FK_USERID  ) VALUES (?,?,?,   ?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventVendorFeatureBean.getEventVendorFeatureId(), eventVendorFeatureBean.getFeatureType().toString(), eventVendorFeatureBean.getEventVendorId(),
                    eventVendorFeatureBean.getValue(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                    eventVendorFeatureBean.getUserId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "VendorFeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(EventVendorFeatureBean eventVendorFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(eventVendorFeatureBean!=null && !Utility.isNullOrEmpty(eventVendorFeatureBean.getEventVendorFeatureId()) ){
            String sQuery = "UPDATE GTEVENTVENDORFEATURES SET  VALUE = ? , FK_USERID = ?,MODIFIEDDATE = ?,HUMANMODIFIEDDATE =?   WHERE FEATURENAME = ? AND FK_EVENTVENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventVendorFeatureBean.getValue(),
                    eventVendorFeatureBean.getUserId(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                    eventVendorFeatureBean.getFeatureType().toString(),eventVendorFeatureBean.getEventVendorId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "VendorFeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer deleteFeature(EventVendorFeatureBean eventVendorFeatureBean) {
        Integer numOfRowsDeleted = 0;
        if(eventVendorFeatureBean!=null && !Utility.isNullOrEmpty(eventVendorFeatureBean.getEventVendorId()) && eventVendorFeatureBean.getFeatureType()!=null ){
            String sQuery = "DELETE FROM GTEVENTVENDORFEATURES WHERE FEATURENAME = ? AND FK_EVENTVENDORID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventVendorFeatureBean.getFeatureType().toString(),eventVendorFeatureBean.getEventVendorId());

            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "VendorFeatureData.java", "deleteFeature() ");
        }
        return  numOfRowsDeleted;
    }

    public EventVendorFeatureBean getVendorFeature(EventVendorFeatureBean eventVendorFeatureBean) {
        EventVendorFeatureBean finalFeatureBean = new EventVendorFeatureBean();
        if(eventVendorFeatureBean!=null && !Utility.isNullOrEmpty(eventVendorFeatureBean.getEventVendorId())){
            String sQuery = "SELECT * FROM GTEVENTVENDORFEATURES WHERE  FEATURENAME = ? AND FK_EVENTVENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventVendorFeatureBean.getFeatureType().toString(),eventVendorFeatureBean.getEventVendorId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorFeatureData.java", "getVendorFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new EventVendorFeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }

    public ArrayList<EventVendorFeatureBean> getMultipleFeatures(ArrayList<EventVendorFeatureBean> arrEventVendorFeatureBean, String sVendorId) {

        ArrayList<EventVendorFeatureBean> arrMultipleFeatureBean = new ArrayList<EventVendorFeatureBean>();

        String sQuery = "SELECT * FROM GTEVENTVENDORFEATURES WHERE FK_EVENTVENDORID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sVendorId);
        if(arrEventVendorFeatureBean!=null && !arrEventVendorFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrEventVendorFeatureBean.size()) + ")";
            for(EventVendorFeatureBean tmpventEmailFeatureBean : arrEventVendorFeatureBean ) {
                aParams.add(tmpventEmailFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "VendorFeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                EventVendorFeatureBean eventVendorFeatureBean = new EventVendorFeatureBean(hmResult);
                arrMultipleFeatureBean.add(eventVendorFeatureBean);
            }
        }
        return arrMultipleFeatureBean;
    }
}
