package com.events.data.event.website;

import com.events.bean.common.email.EventEmailFeatureBean;
import com.events.bean.event.website.AllWebsiteThemeRequestBean;
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
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessWebsiteThemesData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<WebsiteThemeBean> getWebsiteThemesByVendor(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        ArrayList<WebsiteThemeBean> arrWebsiteTheme = new ArrayList<WebsiteThemeBean>();
        if(allWebsiteThemeRequestBean!=null  )    {
            ArrayList<String> arrVendorId = allWebsiteThemeRequestBean.getArrVendorId();
            if( arrVendorId!=null && !arrVendorId.isEmpty()) {
                ArrayList<Object> aParams = new ArrayList<Object>();
                String sQuery = "SELECT * FROM GTWEBSITETHEME WHERE FK_VENDORID in (" + DBDAO.createParamQuestionMarks(arrVendorId.size()) + ")";
                for(String sVendorId : arrVendorId ) {
                    aParams.add(sVendorId);
                }

                ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessWebsiteThemesData.java", "getWebsiteThemesByVendor()");
                if(arrResult!=null && !arrResult.isEmpty()){
                    for(HashMap<String, String> hmResult : arrResult ) {
                        WebsiteThemeBean websiteThemeBean = new WebsiteThemeBean(hmResult);
                        arrWebsiteTheme.add(websiteThemeBean);
                    }
                }
            }
        }
        return arrWebsiteTheme;
    }

    public WebsiteThemeBean getWebsiteTheme(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        WebsiteThemeBean websiteThemeBean = new WebsiteThemeBean();
        if(allWebsiteThemeRequestBean!=null  )    {
            String sQuery = "SELECT * FROM GTWEBSITETHEME WHERE  WEBSITETHEMEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( allWebsiteThemeRequestBean.getWebsiteThemeId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessWebsiteThemesData.java", "getWebsiteTheme()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    websiteThemeBean = new WebsiteThemeBean(hmResult);
                }
            }
        }
        return websiteThemeBean;
    }
}
