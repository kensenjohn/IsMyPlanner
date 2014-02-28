package com.events.data.event.website;

import com.events.bean.event.website.EventHotelRequest;
import com.events.bean.event.website.EventHotelsBean;
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
 * Date: 2/28/14
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventHotelData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTHOTELS(EVENTHOTELID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL, PHONE VARCHAR(45), ADDRESS TEXT, URL TEXT ,

    public EventHotelsBean getEventHotel(EventHotelRequest eventHotelRequest) {
        EventHotelsBean eventHotelsBean = new EventHotelsBean();
        if(eventHotelRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTHOTELS WHERE EVENTHOTELID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventHotelRequest.getEventHotelId());

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventHotelData.java", "getEventHotel()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    eventHotelsBean = new EventHotelsBean(hmResult);
                }
            }
        }
        return eventHotelsBean;
    }

    public ArrayList<EventHotelsBean> getEventHotelByWebsite(EventHotelRequest eventHotelRequest) {
        ArrayList<EventHotelsBean> arrEventHotelsBean = new ArrayList<EventHotelsBean>();
        if(eventHotelRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTHOTELS WHERE FK_EVENTWEBSITEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventHotelRequest.getEventWebsiteId() );

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventHotelData.java", "getEventHotelByWebsite()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    EventHotelsBean eventHotelsBean = new EventHotelsBean(hmResult);
                    arrEventHotelsBean.add( eventHotelsBean );
                }
            }
        }
        return arrEventHotelsBean;
    }
}
