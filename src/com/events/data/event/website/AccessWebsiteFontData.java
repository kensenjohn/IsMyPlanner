package com.events.data.event.website;

import com.events.bean.event.website.AllWebsiteThemeRequestBean;
import com.events.bean.event.website.WebsiteFontBean;
import com.events.bean.event.website.WebsiteThemeBean;
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
 * Date: 2/18/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessWebsiteFontData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<WebsiteFontBean> getWebsiteFont(ArrayList<WebsiteThemeBean> arrWebsiteTheme) {
        // GTWEBSITEFONT ( WEBSITEFONTID , FK_WEBSITETHEMEID , FONT_NAME , FONT_CSS_NAME)
        ArrayList<WebsiteFontBean> arrWebsiteFonts = new ArrayList<WebsiteFontBean>();
        if( arrWebsiteTheme!=null && !arrWebsiteTheme.isEmpty()) {
            ArrayList<Object> aParams = new ArrayList<Object>();
            String sQuery = "SELECT * FROM GTWEBSITEFONT WHERE FK_WEBSITETHEMEID in (" + DBDAO.createParamQuestionMarks(arrWebsiteTheme.size()) + ")";
            for(WebsiteThemeBean websiteThemeBean : arrWebsiteTheme ) {
                aParams.add(websiteThemeBean.getWebsiteThemeId());
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessWebsiteFontData.java", "getWebsiteFont()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    WebsiteFontBean websiteFontBean = new WebsiteFontBean(hmResult);
                    arrWebsiteFonts.add(websiteFontBean);
                }
            }
        }
        return arrWebsiteFonts;
    }
}
