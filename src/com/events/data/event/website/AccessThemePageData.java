package com.events.data.event.website;

import com.events.bean.event.website.AllWebsiteThemeRequestBean;
import com.events.bean.event.website.ThemePageBean;
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
 * Date: 2/21/14
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessThemePageData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTTHEMEPAGE(THEMEPAGEID  VARCHAR(45) NOT NULL ,   FK_WEBSITETHEMEID  VARCHAR(45) NOT NULL , TYPE  VARCHAR(45) NOT NULL, IS_SHOW INT(1) NOT NULL DEFAULT 0,
    public ArrayList<ThemePageBean> getThemePage(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        ArrayList<ThemePageBean> arrThemePageBean = new ArrayList<ThemePageBean>();
        if(allWebsiteThemeRequestBean!=null  && !Utility.isNullOrEmpty(allWebsiteThemeRequestBean.getWebsiteThemeId()) ) {
            String sQuery = "SELECT * FROM GTTHEMEPAGE WHERE FK_WEBSITETHEMEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( allWebsiteThemeRequestBean.getWebsiteThemeId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessThemePageData.java", "getThemePage()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    ThemePageBean themeWebsitePageBean = new ThemePageBean(hmResult);
                    arrThemePageBean.add( themeWebsitePageBean );
                }
            }
        }
        return arrThemePageBean;
    }
}
