package com.events.data.event.website;

import com.events.bean.event.website.EventHotelRequest;
import com.events.bean.event.website.EventHotelsBean;
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
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventHotelsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    
    //GTEVENTHOTELS(EVENTHOTELID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL, PHONE VARCHAR(45), ADDRESS TEXT, URL TEXT ,INSTRUCTIONS TEXT
    public Integer insertEventHotel(EventHotelsBean eventHotelsBean) {
        Integer numOfRowsInserted = 0;
        if(eventHotelsBean!=null) {
            String sQuery = "INSERT into GTEVENTHOTELS(EVENTHOTELID,FK_EVENTWEBSITEID,NAME,    PHONE,ADDRESS,URL,    INSTRUCTIONS) VALUES " +
                    " (?,?,?,    ?,?,?,   ? )";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventHotelsBean.getEventHotelId(), eventHotelsBean.getEventWebsiteId(), eventHotelsBean.getName(),
                    eventHotelsBean.getPhone(), eventHotelsBean.getAddress(), eventHotelsBean.getUrl(),
                    eventHotelsBean.getInstructions() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventHotelsData.java", "insertEventHotel() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateEventHotel(EventHotelsBean eventHotelsBean) {
        Integer numOfRowsInserted = 0;
        if(eventHotelsBean!=null) {
            String sQuery = "UPDATE GTEVENTHOTELS SET NAME = ?, PHONE = ?,ADDRESS= ?,URL = ?, INSTRUCTIONS = ? WHERE   EVENTHOTELID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventHotelsBean.getName(), eventHotelsBean.getPhone(),eventHotelsBean.getAddress(),eventHotelsBean.getUrl(),eventHotelsBean.getInstructions(),
                    eventHotelsBean.getEventHotelId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventHotelData.java", "updateEventHotel() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteEventHotel(EventHotelRequest eventHotelRequest) {
        Integer numOfRowsInserted = 0;
        if(eventHotelRequest!=null) {
            String sQuery = "DELETE FROM GTEVENTHOTELS  WHERE   EVENTHOTELID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventHotelRequest.getEventHotelId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventWebsiteData.java", "deleteEventHotel() ");
        }
        return numOfRowsInserted;
    }
}
