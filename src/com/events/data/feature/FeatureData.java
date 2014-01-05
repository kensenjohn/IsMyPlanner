package com.events.data.feature;

import com.events.bean.common.FeatureBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/22/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeatureData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertFeature(FeatureBean featureBean) {
        Integer numOfRowsInserted = 0;
        if(featureBean!=null && !"".equalsIgnoreCase(featureBean.getFeatureId())){
            String sQuery = "INSERT INTO GTFEATURES (FEATUREID,FEATURENAME,FK_EVENTID,     VALUE) VALUES (?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint( featureBean.getFeatureId(),featureBean.getFeatureType().toString(),featureBean.getEventId(),  featureBean.getValue());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "FeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(FeatureBean featureBean) {
        Integer numOfRowsInserted = 0;
        if(featureBean!=null){
            String sQuery = "UPDATE GTFEATURES SET  VALUE = ? WHERE FEATURENAME = ? AND FK_EVENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( featureBean.getValue(),featureBean.getFeatureType().toString(),featureBean.getEventId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "FeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }

    public FeatureBean getFeature(FeatureBean featureBean) {
        FeatureBean finalFeatureBean = new FeatureBean();
        if(featureBean!=null){
            String sQuery = "SELECT * FROM GTFEATURES WHERE  FEATURENAME = ? AND FK_EVENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( featureBean.getFeatureType().toString(),featureBean.getEventId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "FeatureData.java", "getFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new FeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }

    public ArrayList<FeatureBean> getMultipleFeatures(String sEventId) {
        ArrayList<FeatureBean> arrFeatureBean = new ArrayList<FeatureBean>();
        if(sEventId!=null && !"".equalsIgnoreCase(sEventId)){
            arrFeatureBean = getMultipleFeatures(new ArrayList<FeatureBean>(), sEventId );
        }
        return arrFeatureBean;
    }

    public ArrayList<FeatureBean> getMultipleFeatures(ArrayList<FeatureBean> arrFeatureBean, String sEventId) {

        ArrayList<FeatureBean> arrMultipleFeatureBean = new ArrayList<FeatureBean>();

        String sQuery = "SELECT * FROM GTFEATURES WHERE FK_EVENTID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sEventId);
        if(arrFeatureBean!=null && !arrFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrFeatureBean.size()) + ")";
            for(FeatureBean tmpFeatureBean : arrFeatureBean ) {
                aParams.add(tmpFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "FeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                FeatureBean featureBean = new FeatureBean(hmResult);
                arrMultipleFeatureBean.add(featureBean);
            }
        }
        return arrMultipleFeatureBean;
    }
}
