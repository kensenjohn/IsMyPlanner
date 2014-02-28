package com.events.data.event.website;

import com.events.bean.event.website.EventRegistryBean;
import com.events.bean.event.website.EventRegistryRequest;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventRegistryData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTREGISTRY(EVENTREGISTRYID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL, URL TEXT , 
    // INSTRUCTIONS TEXT , PRIMARY KEY (EVENTREGISTRYID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public Integer insertEventRegistry(EventRegistryBean eventRegistryBean) {
        Integer numOfRowsInserted = 0;
        if(eventRegistryBean!=null) {
            String sQuery = "INSERT into GTEVENTREGISTRY(EVENTREGISTRYID,FK_EVENTWEBSITEID,NAME,    URL,INSTRUCTIONS) VALUES " +
                    " (?,?,?,    ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventRegistryBean.getEventRegistryId(), eventRegistryBean.getEventWebsiteId(), eventRegistryBean.getName(),
                    eventRegistryBean.getUrl(),eventRegistryBean.getInstructions());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventRegistryData.java", "insertEventRegistry() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateEventRegistry(EventRegistryBean eventRegistryBean) {
        Integer numOfRowsInserted = 0;
        if(eventRegistryBean!=null) {
            String sQuery = "UPDATE GTEVENTREGISTRY SET NAME = ?, URL = ?, INSTRUCTIONS = ? WHERE   EVENTREGISTRYID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventRegistryBean.getName(), eventRegistryBean.getUrl(),eventRegistryBean.getInstructions(),
                    eventRegistryBean.getEventRegistryId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventRegistryData.java", "updateEventRegistry() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteEventRegistry(EventRegistryRequest eventRegistryRequest) {
        Integer numOfRowsInserted = 0;
        if(eventRegistryRequest!=null) {
            String sQuery = "DELETE FROM GTEVENTREGISTRY  WHERE   EVENTREGISTRYID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventRegistryRequest.getEventRegistryId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventWebsiteData.java", "deleteEventRegistry() ");
        }
        return numOfRowsInserted;
    }
}
