package com.events.data.event.website;

import com.events.bean.event.website.EventWebsiteBean;
import com.events.bean.event.website.EventWebsiteRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventWebsiteData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTWEBSITE ( EVENTWEBSITEID VARCHAR(45) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL,  FK_WEBSITETHEMEID VARCHAR(45) NOT NULL,
    // FK_WEBSITEFONTID VARCHAR(45) NOT NULL,FK_WEBSITECOLORID VARCHAR(45) NOT NULL, FK_USERID  VARCHAR(45) NOT NULL
    public Integer insertEventWebsite(EventWebsiteBean eventWebsiteBean) {
        Integer numOfRowsInserted = 0;
        if(eventWebsiteBean!=null) {
            String sQuery = "INSERT into GTEVENTWEBSITE(EVENTWEBSITEID,FK_EVENTID,FK_WEBSITETHEMEID,    FK_WEBSITEFONTID,FK_WEBSITECOLORID,FK_USERID ) VALUES " +
                    " (?,?,?,    ?,?,? )";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventWebsiteBean.getEventWebsiteId() , eventWebsiteBean.getEventId(), eventWebsiteBean.getWebsiteThemeId(),
                    eventWebsiteBean.getWebsiteFontId(), eventWebsiteBean.getWebsiteColorId(), eventWebsiteBean.getUserId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventWebsiteData.java", "insertEventWebsite() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateEventWebsite(EventWebsiteBean eventWebsiteBean) {
        Integer numOfRowsInserted = 0;
        if(eventWebsiteBean!=null) {
            String sQuery = "UPDATE GTEVENTWEBSITE SET FK_WEBSITETHEMEID = ?, FK_WEBSITEFONTID = ?,FK_WEBSITECOLORID= ?,     FK_USERID = ?   WHERE   EVENTWEBSITEID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventWebsiteBean.getWebsiteThemeId(), eventWebsiteBean.getWebsiteFontId(), eventWebsiteBean.getWebsiteColorId(),
                    eventWebsiteBean.getUserId() , eventWebsiteBean.getEventWebsiteId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventWebsiteData.java", "updateEventWebsite() ");
        }
        return numOfRowsInserted;
    }
}
