package com.events.data.event.website;

import com.events.bean.event.website.EventPartyBean;
import com.events.bean.event.website.EventPartyRequest;
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
 * Date: 2/26/14
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventPartyData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //CREATE TABLE GTEVENTPARTY(EVENTPARTYID VARCHAR(45) NOT NULL, FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , EVENTPARTYTYPE VARCHAR(100) NOT NULL, NAME VARCHAR(250) NOT NULL, DESCRIPTION TEXT,  PRIMARY KEY (EVENTPARTYID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    public EventPartyBean getEventParty(EventPartyRequest eventPartyRequest) {
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTPARTY WHERE EVENTPARTYID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventPartyRequest.getEventPartyId() );

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventPartyData.java", "getEventParty()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    eventPartyBean = new EventPartyBean(hmResult);
                }
            }
        }
        return eventPartyBean;
    }

    public ArrayList<EventPartyBean> getEventPartyByWebsite(EventPartyRequest eventPartyRequest) {
        ArrayList<EventPartyBean> arrEventPartyBean = new ArrayList<EventPartyBean>();
        if(eventPartyRequest!=null) {
            String sQuery = "SELECT * FROM  GTEVENTPARTY WHERE FK_EVENTWEBSITEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventPartyRequest.getEventWebsiteId() );

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventPartyData.java", "getEventPartyByWebsite()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    EventPartyBean eventPartyBean = new EventPartyBean(hmResult);
                    arrEventPartyBean.add( eventPartyBean );
                }
            }
        }
        return arrEventPartyBean;
    }

    public ArrayList<EventPartyBean> getEventPartyListByTypeAndWebsite(EventPartyRequest eventPartyRequest) {
        ArrayList<EventPartyBean> arrEventPartyBean = new ArrayList<EventPartyBean>();
        if(eventPartyRequest!=null) {
            ArrayList<Constants.EVENT_PARTY_TYPE> arrEventPartyType = eventPartyRequest.getArrEventPartyType();
            if(arrEventPartyType!=null && !arrEventPartyType.isEmpty()){
                String sQuery = "SELECT * FROM  GTEVENTPARTY WHERE FK_EVENTWEBSITEID = ? AND EVENTPARTYTYPE IN ( " + DBDAO.createParamQuestionMarks(arrEventPartyType.size()) + ")";
                ArrayList<Object> aParams = DBDAO.createConstraint(eventPartyRequest.getEventWebsiteId() );
                for(Constants.EVENT_PARTY_TYPE eventPartyType : arrEventPartyType ) {
                    aParams.add( eventPartyType.toString() );
                }

                ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventPartyData.java", "getEventPartyListByTypeAndWebsite()");
                if(arrResult!=null && !arrResult.isEmpty()){
                    for(HashMap<String, String> hmResult : arrResult ) {
                        EventPartyBean eventPartyBean = new EventPartyBean(hmResult);
                        arrEventPartyBean.add( eventPartyBean );
                    }
                }
            }

        }
        return arrEventPartyBean;
    }

    public EventPartyBean getEventPartyByTypeAndWebsite(EventPartyRequest eventPartyRequest) {
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null && eventPartyRequest.getEventPartyType()!=null
                && !Constants.EVENT_PARTY_TYPE.NONE.getText().equalsIgnoreCase(eventPartyRequest.getEventPartyType().getText() )) {
            String sQuery = "SELECT * FROM  GTEVENTPARTY WHERE FK_EVENTWEBSITEID = ? AND EVENTPARTYTYPE = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventPartyRequest.getEventWebsiteId() , eventPartyRequest.getEventPartyType().getText() );

            ArrayList<HashMap<String, String>> arrResult =  DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventPartyData.java", "getEventPartyByTypeAndWebsite()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    eventPartyBean = new EventPartyBean(hmResult);
                }
            }
        }
        return eventPartyBean;
    }
}
