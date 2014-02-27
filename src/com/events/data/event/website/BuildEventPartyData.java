package com.events.data.event.website;

import com.events.bean.event.website.EventPartyBean;
import com.events.bean.event.website.EventPartyRequest;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventPartyData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //CREATE TABLE GTEVENTPARTY(EVENTPARTYID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , EVENTPARTYTYPE VARCHAR(100) NOT NULL, NAME VARCHAR(250) NOT NULL, DESCRIPTION TEXT,  PRIMARY KEY (EVENTPARTYID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    public Integer insertEventParty(EventPartyBean eventPartyBean) {
        Integer numOfRowsInserted = 0;
        if(eventPartyBean!=null) {
            String sQuery = "INSERT into GTEVENTPARTY(EVENTPARTYID,FK_EVENTWEBSITEID,EVENTPARTYTYPE,    NAME,DESCRIPTION,FK_UPLOADID) VALUES " +
                    " (?,?,?,    ?,?,? )";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventPartyBean.getEventPartyId(), eventPartyBean.getEventWebsiteId(), eventPartyBean.getEventPartyType().toString(),
                    eventPartyBean.getName(), eventPartyBean.getDescription(),eventPartyBean.getUploadId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventPartyData.java", "insertEventParty() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateEventParty(EventPartyBean eventPartyBean) {
        Integer numOfRowsInserted = 0;
        if(eventPartyBean!=null) {
            String sQuery = "UPDATE GTEVENTPARTY SET NAME = ?, DESCRIPTION = ?,EVENTPARTYTYPE= ?,FK_UPLOADID = ? WHERE   EVENTPARTYID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventPartyBean.getName(), eventPartyBean.getDescription(),eventPartyBean.getEventPartyType().toString(),eventPartyBean.getUploadId(),
                    eventPartyBean.getEventPartyId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventPartyData.java", "updateEventParty() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteEventParty(EventPartyRequest eventPartyRequest) {
        Integer numOfRowsInserted = 0;
        if(eventPartyRequest!=null) {
            String sQuery = "DELETE FROM GTEVENTPARTY  WHERE   EVENTPARTYID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventPartyRequest.getEventPartyId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventWebsiteData.java", "deleteEventParty() ");
        }
        return numOfRowsInserted;
    }
}
