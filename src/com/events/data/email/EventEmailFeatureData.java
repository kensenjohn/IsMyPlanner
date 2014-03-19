package com.events.data.email;

import com.events.bean.common.FeatureBean;
import com.events.bean.common.email.EventEmailFeatureBean;
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
 * Date: 1/13/14
 * Time: 8:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventEmailFeatureData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);


    public Integer insertFeature(EventEmailFeatureBean eventEmailFeatureBean) {
        Integer numOfRowsInserted = 0;
        // EVENTEMAILFEATUREID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, FK_EVENTEMAILID  VARCHAR(45) NOT NULL, VALUE  VARCHAR(500) NOT NULL
        if(eventEmailFeatureBean!=null  && !Utility.isNullOrEmpty(eventEmailFeatureBean.getEventEmailFeatureId()) ){
            String sQuery = "INSERT INTO GTEVENTEMAILFEATURES (EVENTEMAILFEATUREID,FEATURENAME,FK_EVENTEMAILID,     VALUE) VALUES (?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventEmailFeatureBean.getEventEmailFeatureId(), eventEmailFeatureBean.getFeatureType().toString(),
                    eventEmailFeatureBean.getEventEmailId(), eventEmailFeatureBean.getValue());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventEmailFeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer deleteFeature(EventEmailFeatureBean eventEmailFeatureBean) {
        Integer numOfRowsInserted = 0;
        // EVENTEMAILFEATUREID  VARCHAR(45) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, FK_EVENTEMAILID  VARCHAR(45) NOT NULL, VALUE  VARCHAR(500) NOT NULL
        if(eventEmailFeatureBean!=null  && !Utility.isNullOrEmpty(eventEmailFeatureBean.getFeatureType().toString()) && !Utility.isNullOrEmpty(eventEmailFeatureBean.getEventEmailId()) ){
            String sQuery = "DELETE FROM GTEVENTEMAILFEATURES WHERE FEATURENAME = ? AND FK_EVENTEMAILID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventEmailFeatureBean.getFeatureType().toString(),  eventEmailFeatureBean.getEventEmailId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventEmailFeatureData.java", "deleteFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(EventEmailFeatureBean eventEmailFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(eventEmailFeatureBean!=null && !Utility.isNullOrEmpty(eventEmailFeatureBean.getEventEmailFeatureId()) ){
            String sQuery = "UPDATE GTEVENTEMAILFEATURES SET  VALUE = ? WHERE FEATURENAME = ? AND FK_EVENTEMAILID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventEmailFeatureBean.getValue(),eventEmailFeatureBean.getFeatureType().toString(),eventEmailFeatureBean.getEventEmailId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventEmailFeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }

    public EventEmailFeatureBean getEventEmailFeature(EventEmailFeatureBean eventEmailFeatureBean) {
        EventEmailFeatureBean finalFeatureBean = new EventEmailFeatureBean();
        if(eventEmailFeatureBean!=null && !Utility.isNullOrEmpty(eventEmailFeatureBean.getEventEmailId())){
            String sQuery = "SELECT * FROM GTEVENTEMAILFEATURES WHERE  FEATURENAME = ? AND FK_EVENTEMAILID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventEmailFeatureBean.getFeatureType().toString(),eventEmailFeatureBean.getEventEmailId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "EventEmailFeatureData.java", "getEventEmailFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new EventEmailFeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }

    public ArrayList<EventEmailFeatureBean> getMultipleFeatures(ArrayList<EventEmailFeatureBean> arrEventEmailFeatureBean, String sEventEmailId) {

        ArrayList<EventEmailFeatureBean> arrMultipleFeatureBean = new ArrayList<EventEmailFeatureBean>();

        String sQuery = "SELECT * FROM GTEVENTEMAILFEATURES WHERE FK_EVENTEMAILID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sEventEmailId);
        if(arrEventEmailFeatureBean!=null && !arrEventEmailFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrEventEmailFeatureBean.size()) + ")";
            for(EventEmailFeatureBean tmpventEmailFeatureBean : arrEventEmailFeatureBean ) {
                aParams.add(tmpventEmailFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "EventEmailFeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                EventEmailFeatureBean eventEmailFeatureBean = new EventEmailFeatureBean(hmResult);
                arrMultipleFeatureBean.add(eventEmailFeatureBean);
            }
        }
        return arrMultipleFeatureBean;
    }
}
