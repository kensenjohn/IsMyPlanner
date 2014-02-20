package com.events.data.event.website;

import com.events.bean.event.website.EventWebsiteBean;
import com.events.bean.event.website.EventWebsiteRequestBean;
import com.events.bean.event.website.WebsiteColorBean;
import com.events.bean.event.website.WebsiteThemeBean;
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
 * Date: 2/19/14
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventWebsiteData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTWEBSITE ( EVENTWEBSITEID VARCHAR(45) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL,  FK_WEBSITETHEMEID VARCHAR(45) NOT NULL,
    // FK_WEBSITEFONTID VARCHAR(45) NOT NULL,FK_WEBSITECOLORID VARCHAR(45) NOT NULL, FK_USERID  VARCHAR(45) NOT NULL
    public EventWebsiteBean getEventWebsite(EventWebsiteRequestBean eventWebsiteRequestBean) {
        EventWebsiteBean eventWebsiteBean = new EventWebsiteBean();
        if(eventWebsiteRequestBean!=null && !Utility.isNullOrEmpty(eventWebsiteRequestBean.getEventId())) {
            String sQuery = "SELECT * FROM GTEVENTWEBSITE WHERE FK_EVENTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventWebsiteRequestBean.getEventId() ) ;

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventWebsiteData.java", "getEventWebsite()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    eventWebsiteBean = new EventWebsiteBean(hmResult);
                }
            }
        }
        return eventWebsiteBean;
    }

}
