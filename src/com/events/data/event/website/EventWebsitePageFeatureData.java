package com.events.data.event.website;

import com.events.bean.event.website.EventWebsitePageFeatureBean;
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
 * User: root
 * Date: 2/21/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventWebsitePageFeatureData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTWEBSITEPAGEFEATURES ( EVENTWEBSITEPAGEFEATUREID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEPAGEID  VARCHAR(45) NOT NULL,
    // FEATUREDESCRIPTION  VARCHAR(100) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL,
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),FK_USERID

    public Integer insertFeature(EventWebsitePageFeatureBean eventWebsitePageFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(eventWebsitePageFeatureBean!=null  && !Utility.isNullOrEmpty(eventWebsitePageFeatureBean.getEventWebsitePageFeatureId()) ){
            String sQuery = "INSERT INTO GTEVENTWEBSITEPAGEFEATURES (EVENTWEBSITEPAGEFEATUREID,FK_EVENTWEBSITEPAGEID,FEATUREDESCRIPTION," +
                    "     FEATURENAME,VALUE,MODIFIEDDATE,   HUMANMODIFIEDDATE,FK_USERID  ) VALUES (?,?,?,   ?,?,?,   ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventWebsitePageFeatureBean.getEventWebsitePageFeatureId(), eventWebsitePageFeatureBean.getEventWebsitePageId(), eventWebsitePageFeatureBean.getFeatureDescription(),
                    eventWebsitePageFeatureBean.getFeatureType().toString(), eventWebsitePageFeatureBean.getValue(), DateSupport.getEpochMillis(),
                    DateSupport.getUTCDateTime(), eventWebsitePageFeatureBean.getUserId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventWebsitePageFeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(EventWebsitePageFeatureBean eventWebsitePageFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(eventWebsitePageFeatureBean!=null && !Utility.isNullOrEmpty(eventWebsitePageFeatureBean.getEventWebsitePageFeatureId() ) ){
            String sQuery = "UPDATE GTEVENTWEBSITEPAGEFEATURES SET  VALUE = ? ,MODIFIEDDATE = ?,HUMANMODIFIEDDATE =?   WHERE FEATURENAME = ? AND FK_EVENTWEBSITEPAGEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventWebsitePageFeatureBean.getValue(),
                    eventWebsitePageFeatureBean.getUserId(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                    eventWebsitePageFeatureBean.getFeatureType().toString(),eventWebsitePageFeatureBean.getEventWebsitePageId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventWebsitePageFeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }


    public Integer deleteFeature(EventWebsitePageFeatureBean eventWebsitePageFeatureBean) {
        Integer numOfRowsDeleted = 0;
        if(eventWebsitePageFeatureBean!=null && !Utility.isNullOrEmpty(eventWebsitePageFeatureBean.getEventWebsitePageId()) && eventWebsitePageFeatureBean.getFeatureType()!=null ){
            String sQuery = "DELETE FROM GTEVENTWEBSITEPAGEFEATURES WHERE FEATURENAME = ? AND FK_EVENTWEBSITEPAGEID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventWebsitePageFeatureBean.getFeatureType().toString(),eventWebsitePageFeatureBean.getEventWebsitePageId());

            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "EventWebsitePageFeatureData.java", "deleteFeature() ");
        }
        return  numOfRowsDeleted;
    }

    public EventWebsitePageFeatureBean getEventWebsitePageFeature(EventWebsitePageFeatureBean eventWebsitePageFeatureBean) {
        EventWebsitePageFeatureBean finalFeatureBean = new EventWebsitePageFeatureBean();
        if(eventWebsitePageFeatureBean!=null && !Utility.isNullOrEmpty(eventWebsitePageFeatureBean.getEventWebsitePageId())){
            String sQuery = "SELECT * FROM GTEVENTWEBSITEPAGEFEATURES WHERE  FEATURENAME = ? AND FK_EVENTWEBSITEPAGEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventWebsitePageFeatureBean.getFeatureType().toString(),eventWebsitePageFeatureBean.getEventWebsitePageId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "EventWebsitePageFeatureData.java", "getVendorFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new EventWebsitePageFeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }
    //GTEVENTWEBSITEPAGEFEATURES ( EVENTWEBSITEPAGEFEATUREID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEPAGEID  VARCHAR(45) NOT NULL,
    // FEATUREDESCRIPTION  VARCHAR(100) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL,MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANMODIFIEDDATE VARCHAR(45),FK_USERID
    public ArrayList<EventWebsitePageFeatureBean> getMultipleFeatures(ArrayList<EventWebsitePageFeatureBean> arrEventWebsitePageFeatureBean, String sEventWebsitePageId) {

        ArrayList<EventWebsitePageFeatureBean> arrMultipleFeatureBean = new ArrayList<EventWebsitePageFeatureBean>();

        String sQuery = "SELECT * FROM GTEVENTWEBSITEPAGEFEATURES WHERE FK_EVENTWEBSITEPAGEID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sEventWebsitePageId);
        if(arrEventWebsitePageFeatureBean!=null && !arrEventWebsitePageFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrEventWebsitePageFeatureBean.size()) + ")";
            for(EventWebsitePageFeatureBean tmpEventWebsitePageFeatureBean : arrEventWebsitePageFeatureBean ) {
                aParams.add(tmpEventWebsitePageFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "EventWebsitePageFeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                EventWebsitePageFeatureBean eventWebsitePageFeatureBean = new EventWebsitePageFeatureBean(hmResult);
                arrMultipleFeatureBean.add(eventWebsitePageFeatureBean);
            }
        }
        return arrMultipleFeatureBean;
    }
}
