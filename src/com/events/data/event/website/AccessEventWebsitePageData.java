package com.events.data.event.website;

import com.events.bean.event.website.EventWebsiteBean;
import com.events.bean.event.website.EventWebsitePageBean;
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
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventWebsitePageData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTWEBSITEPAGE(EVENTWEBSITEPAGEID  VARCHAR(45) NOT NULL ,   FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , FK_WEBSITETHEMEID  VARCHAR(45) NOT NULL , TYPE  VARCHAR(45) NOT NULL, IS_SHOW INT(1) NOT NULL DEFAULT 0
    public ArrayList<EventWebsitePageBean> getEventWebsitePage(EventWebsiteBean eventWebsiteBean) {
        ArrayList<EventWebsitePageBean> arrEventWebsitePageBean = new ArrayList<EventWebsitePageBean>();
        if(eventWebsiteBean!=null && !Utility.isNullOrEmpty(eventWebsiteBean.getEventWebsiteId())
                && !Utility.isNullOrEmpty(eventWebsiteBean.getWebsiteThemeId()) ) {
            String sQuery = "SELECT * FROM GTEVENTWEBSITEPAGE WHERE FK_EVENTWEBSITEID = ? AND FK_WEBSITETHEMEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventWebsiteBean.getEventWebsiteId() , eventWebsiteBean.getWebsiteThemeId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventWebsitePageData.java", "getEventWebsitePage()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    EventWebsitePageBean eventWebsitePageBean = new EventWebsitePageBean(hmResult);
                    arrEventWebsitePageBean.add( eventWebsitePageBean );
                }
            }
        }
        return arrEventWebsitePageBean;
    }

    public EventWebsitePageBean getEventWebsitePageByType(EventWebsitePageBean eventWebsitePageBean) {
        EventWebsitePageBean eventWebsitePageBeanFromDB = new EventWebsitePageBean();
        if(eventWebsitePageBean!=null && !Utility.isNullOrEmpty(eventWebsitePageBean.getEventWebsiteId())
                && !Utility.isNullOrEmpty(eventWebsitePageBean.getWebsiteThemeId()) && !Utility.isNullOrEmpty(eventWebsitePageBean.getType())) {
            String sQuery = "SELECT * FROM GTEVENTWEBSITEPAGE WHERE FK_EVENTWEBSITEID = ? AND FK_WEBSITETHEMEID = ? AND TYPE = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventWebsitePageBean.getEventWebsiteId() , eventWebsitePageBean.getWebsiteThemeId(), eventWebsitePageBean.getType() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventWebsitePageData.java", "getEventWebsitePageByType()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    eventWebsitePageBeanFromDB = new EventWebsitePageBean(hmResult);
                }
            }
        }
        return eventWebsitePageBeanFromDB;
    }
}
