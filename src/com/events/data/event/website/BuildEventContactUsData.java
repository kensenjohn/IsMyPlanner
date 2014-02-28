package com.events.data.event.website;

import com.events.bean.event.website.EventContactUsBean;
import com.events.bean.event.website.EventContactUsRequest;
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
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventContactUsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //CREATE TABLE GTEVENTCONTACTUS(EVENTCONTACTUSID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL,
    // PHONE TEXT ,  EMAIL VARCHAR(250) , PRIMARY KEY (EVENTCONTACTUSID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public Integer insertEventContactUs(EventContactUsBean eventContactUssBean) {
        Integer numOfRowsInserted = 0;
        if(eventContactUssBean!=null) {
            String sQuery = "INSERT into GTEVENTCONTACTUS(EVENTCONTACTUSID,FK_EVENTWEBSITEID,NAME,    PHONE,EMAIL) VALUES " +
                    " (?,?,?,    ?,? )";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventContactUssBean.getEventContactUsId(), eventContactUssBean.getEventWebsiteId(), eventContactUssBean.getName(),
                    eventContactUssBean.getPhone(), eventContactUssBean.getEmail());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventContactUsData.java", "insertEventContactUs() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateEventContactUs(EventContactUsBean eventContactUssBean) {
        Integer numOfRowsInserted = 0;
        if(eventContactUssBean!=null) {
            String sQuery = "UPDATE GTEVENTCONTACTUS SET NAME = ?, PHONE = ?,EMAIL= ? WHERE   EVENTCONTACTUSID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventContactUssBean.getName(), eventContactUssBean.getPhone(),eventContactUssBean.getEmail(),
                    eventContactUssBean.getEventContactUsId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventContactUsData.java", "updateEventContactUs() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteEventContactUs(EventContactUsRequest eventContactUsRequest) {
        Integer numOfRowsInserted = 0;
        if(eventContactUsRequest!=null) {
            String sQuery = "DELETE FROM GTEVENTCONTACTUS  WHERE   EVENTCONTACTUSID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventContactUsRequest.getEventContactUsId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventWebsiteData.java", "deleteEventContactUs() ");
        }
        return numOfRowsInserted;
    }
}
