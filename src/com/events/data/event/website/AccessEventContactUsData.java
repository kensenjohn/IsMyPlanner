package com.events.data.event.website;

import com.events.bean.event.website.EventContactUsBean;
import com.events.bean.event.website.EventContactUsRequest;
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
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventContactUsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //CREATE TABLE GTEVENTCONTACTUS(EVENTCONTACTUSID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , NAME TEXT NOT NULL, PHONE TEXT ,  
    // EMAIL VARCHAR(250) , PRIMARY KEY (EVENTCONTACTUSID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    public EventContactUsBean getEventContactUs(EventContactUsRequest eventContactUsRequest) {
        EventContactUsBean eventContactUssBean = new EventContactUsBean();
        if(eventContactUsRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTCONTACTUS WHERE EVENTCONTACTUSID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventContactUsRequest.getEventContactUsId());

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventContactUsData.java", "getEventContactUs()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    eventContactUssBean = new EventContactUsBean(hmResult);
                }
            }
        }
        return eventContactUssBean;
    }

    public ArrayList<EventContactUsBean> getEventContactUsByWebsite(EventContactUsRequest eventContactUsRequest) {
        ArrayList<EventContactUsBean> arrEventContactUsBean = new ArrayList<EventContactUsBean>();
        if(eventContactUsRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTCONTACTUS WHERE FK_EVENTWEBSITEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventContactUsRequest.getEventWebsiteId() );

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventContactUsData.java", "getEventContactUsByWebsite()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    EventContactUsBean eventContactUssBean = new EventContactUsBean(hmResult);
                    arrEventContactUsBean.add( eventContactUssBean );
                }
            }
        }
        return arrEventContactUsBean;
    }
}
