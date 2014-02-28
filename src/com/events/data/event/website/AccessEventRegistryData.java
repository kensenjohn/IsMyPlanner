package com.events.data.event.website;

import com.events.bean.event.website.EventRegistryBean;
import com.events.bean.event.website.EventRegistryRequest;
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
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventRegistryData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTREGISTRY(EVENTREGISTRYID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL, URL TEXT ,
    // INSTRUCTIONS TEXT , PRIMARY KEY (EVENTREGISTRYID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    public EventRegistryBean getEventRegistry(EventRegistryRequest eventHotelRequest) {
        EventRegistryBean eventRegistryBean = new EventRegistryBean();
        if(eventHotelRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTREGISTRY WHERE EVENTREGISTRYID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventHotelRequest.getEventRegistryId());

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventRegistryData.java", "getEventRegistry()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    eventRegistryBean = new EventRegistryBean(hmResult);
                }
            }
        }
        return eventRegistryBean;
    }

    public ArrayList<EventRegistryBean> getEventRegistryByWebsite(EventRegistryRequest eventHotelRequest) {
        ArrayList<EventRegistryBean> arrEventRegistryBean = new ArrayList<EventRegistryBean>();
        if(eventHotelRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTREGISTRY WHERE FK_EVENTWEBSITEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventHotelRequest.getEventWebsiteId() );

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventRegistryData.java", "getEventRegistryByWebsite()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    EventRegistryBean eventRegistryBean = new EventRegistryBean(hmResult);
                    arrEventRegistryBean.add( eventRegistryBean );
                }
            }
        }
        return arrEventRegistryBean;
    }
}
