package com.events.data.event.website;

import com.events.bean.event.website.*;
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
 * Date: 2/18/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessWebsiteColorData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<WebsiteColorBean> getWebsiteColor(ArrayList<WebsiteThemeBean> arrWebsiteTheme) {
        // GTWEBSITECOLOR ( WEBSITECOLORID , FK_WEBSITETHEMEID , COLOR_NAME , COLOR_CSS_NAME,COLOR_SWATCH_NAME)
        ArrayList<WebsiteColorBean> arrWebsiteColors = new ArrayList<WebsiteColorBean>();
        if( arrWebsiteTheme!=null && !arrWebsiteTheme.isEmpty()) {
            ArrayList<Object> aParams = new ArrayList<Object>();
            String sQuery = "SELECT * FROM GTWEBSITECOLOR WHERE FK_WEBSITETHEMEID in (" + DBDAO.createParamQuestionMarks(arrWebsiteTheme.size()) + ")";
            for(WebsiteThemeBean websiteThemeBean : arrWebsiteTheme ) {
                aParams.add(websiteThemeBean.getWebsiteThemeId());
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessWebsiteColorData.java", "getWebsiteColor()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    WebsiteColorBean websiteColorBean = new WebsiteColorBean(hmResult);
                    arrWebsiteColors.add(websiteColorBean);
                }
            }
        }
        return arrWebsiteColors;
    }

    public WebsiteColorBean getWebsiteColor(EventWebsiteRequestBean eventWebsiteRequestBean ) {
        // GTWEBSITECOLOR ( WEBSITECOLORID , FK_WEBSITETHEMEID , COLOR_NAME , COLOR_CSS_NAME,COLOR_SWATCH_NAME)
        WebsiteColorBean websiteColorBean = new WebsiteColorBean();
        if( eventWebsiteRequestBean!=null && !Utility.isNullOrEmpty(eventWebsiteRequestBean.getWebsiteColorId())) {

            String sQuery = "SELECT * FROM GTWEBSITECOLOR WHERE WEBSITECOLORID  = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventWebsiteRequestBean.getWebsiteColorId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessWebsiteColorData.java", "getWebsiteColor()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    websiteColorBean = new WebsiteColorBean(hmResult);
                }
            }
        }
        return websiteColorBean;
    }

    public WebsiteColorBean getDefaultWebsiteColor( WebsiteThemeBean websiteThemeBean ) {
        WebsiteColorBean websiteColorBean = new WebsiteColorBean();
        if(websiteThemeBean!=null && !Utility.isNullOrEmpty(websiteThemeBean.getWebsiteThemeId())) {
            String sQuery = "SELECT * FROM GTWEBSITECOLOR WHERE FK_WEBSITETHEMEID = ? AND IS_DEFAULT = 1";
            ArrayList<Object> aParams = DBDAO.createConstraint( websiteThemeBean.getWebsiteThemeId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessWebsiteColorData.java", "getDefaultWebsiteColor()");

            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    websiteColorBean = new WebsiteColorBean(hmResult);
                }
            }
        }
        return websiteColorBean;
    }

}
