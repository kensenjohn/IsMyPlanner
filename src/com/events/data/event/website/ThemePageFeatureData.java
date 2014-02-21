package com.events.data.event.website;

import com.events.bean.event.website.ThemePageFeatureBean;
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
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThemePageFeatureData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTTHEMEPAGEFEATURES ( THEMEPAGEFEATUREID VARCHAR(45) NOT NULL, FK_THEMEPAGEID  VARCHAR(45) NOT NULL, 
    // FEATUREDESCRIPTION  VARCHAR(100) NOT NULL, FEATURENAME  VARCHAR(75) NOT NULL, VALUE  TEXT NOT NULL

    public Integer insertFeature(ThemePageFeatureBean themePageFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(themePageFeatureBean!=null  && !Utility.isNullOrEmpty(themePageFeatureBean.getThemePageFeatureId()) ){
            String sQuery = "INSERT INTO GTTHEMEPAGEFEATURES (THEMEPAGEFEATUREID,FK_THEMEPAGEID,FEATUREDESCRIPTION," +
                    "     FEATURENAME,VALUE ) VALUES (?,?,?,   ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(themePageFeatureBean.getThemePageFeatureId(), themePageFeatureBean.getThemePageId(), themePageFeatureBean.getFeatureDescription(),
                    themePageFeatureBean.getFeatureType().toString(),themePageFeatureBean.getValue() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "ThemePageFeatureData.java", "insertFeature() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateFeature(ThemePageFeatureBean themePageFeatureBean) {
        Integer numOfRowsInserted = 0;
        if(themePageFeatureBean!=null && !Utility.isNullOrEmpty(themePageFeatureBean.getThemePageFeatureId() ) ){
            String sQuery = "UPDATE GTTHEMEPAGEFEATURES SET  VALUE = ?   WHERE FEATURENAME = ? AND FK_THEMEPAGEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( themePageFeatureBean.getValue(),
                    themePageFeatureBean.getFeatureType().toString(),themePageFeatureBean.getThemePageId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "ThemePageFeatureData.java", "updateFeature() ");
        }
        return  numOfRowsInserted;
    }


    public Integer deleteFeature(ThemePageFeatureBean themePageFeatureBean) {
        Integer numOfRowsDeleted = 0;
        if(themePageFeatureBean!=null && !Utility.isNullOrEmpty(themePageFeatureBean.getThemePageId()) && themePageFeatureBean.getFeatureType()!=null ){
            String sQuery = "DELETE FROM GTTHEMEPAGEFEATURES WHERE FEATURENAME = ? AND FK_THEMEPAGEID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( themePageFeatureBean.getFeatureType().toString(),themePageFeatureBean.getThemePageId());

            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "ThemePageFeatureData.java", "deleteFeature() ");
        }
        return  numOfRowsDeleted;
    }

    public ThemePageFeatureBean getEventWebsitePageFeature(ThemePageFeatureBean themePageFeatureBean) {
        ThemePageFeatureBean finalFeatureBean = new ThemePageFeatureBean();
        if(themePageFeatureBean!=null && !Utility.isNullOrEmpty(themePageFeatureBean.getThemePageId())){
            String sQuery = "SELECT * FROM GTTHEMEPAGEFEATURES WHERE  FEATURENAME = ? AND FK_THEMEPAGEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( themePageFeatureBean.getFeatureType().toString(),themePageFeatureBean.getThemePageId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "ThemePageFeatureData.java", "getVendorFeature()");

            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    finalFeatureBean = new ThemePageFeatureBean(hmResult);
                }
            }
        }
        return  finalFeatureBean;
    }

    public ArrayList<ThemePageFeatureBean> getMultipleFeatures(ArrayList<ThemePageFeatureBean> arrThemePageFeatureBean, String sThemePageId) {

        ArrayList<ThemePageFeatureBean> arrMultipleFeatureBean = new ArrayList<ThemePageFeatureBean>();

        String sQuery = "SELECT * FROM GTTHEMEPAGEFEATURES WHERE FK_THEMEPAGEID = ? ";
        ArrayList<Object> aParams = DBDAO.createConstraint(sThemePageId);
        if(arrThemePageFeatureBean!=null && !arrThemePageFeatureBean.isEmpty()) {
            sQuery = sQuery +" AND FEATURENAME IN (" + DBDAO.createParamQuestionMarks(arrThemePageFeatureBean.size()) + ")";
            for(ThemePageFeatureBean tmpThemePageFeatureBean : arrThemePageFeatureBean ) {
                aParams.add(tmpThemePageFeatureBean.getFeatureType().toString());
            }
        }
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "ThemePageFeatureData.java", "getMultipleFeatures()");
        if( arrResult!=null && !arrResult.isEmpty() ) {
            for(HashMap<String, String> hmResult : arrResult ) {
                ThemePageFeatureBean themePageFeatureBean = new ThemePageFeatureBean(hmResult);
                arrMultipleFeatureBean.add(themePageFeatureBean);
            }
        }
        return arrMultipleFeatureBean;
    }
}
