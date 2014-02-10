package com.events.data.event.vendor;

import com.events.bean.event.vendor.EventVendorBean;
import com.events.bean.event.vendor.EventVendorRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 5:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventVendorData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    // GTEVENTVENDORS(EVENTVENDORID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL,
    public ArrayList<EventVendorBean> getEventVendorsByEventId(EventVendorRequestBean eventVendorRequestBean) {

        ArrayList<EventVendorBean> arrEventVendorBean = new ArrayList<EventVendorBean>();
        if(eventVendorRequestBean!=null && !"".equalsIgnoreCase(eventVendorRequestBean.getEventId())) {
            String sQuery  = "SELECT * FROM GTEVENTVENDORS WHERE FK_EVENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventVendorRequestBean.getEventId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventVendorData.java", "getEventVendorsByEventId()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventVendorBean eventVendorBean = new EventVendorBean(hmResult);
                    arrEventVendorBean.add(eventVendorBean);
                }
            }
        }
        return arrEventVendorBean;
    }

    public EventVendorBean getEventVendor(EventVendorRequestBean eventVendorRequestBean) {
        EventVendorBean eventVendorBean = new EventVendorBean();
        if(eventVendorRequestBean!=null && !"".equalsIgnoreCase(eventVendorRequestBean.getEventId())) {
            String sQuery  = "SELECT * FROM GTEVENTVENDORS WHERE FK_EVENTID = ? AND FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventVendorRequestBean.getEventId(), eventVendorRequestBean.getVendorId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventVendorData.java", "getEventVendorsByEventId()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    eventVendorBean = new EventVendorBean(hmResult);
                }
            }
        }
        return eventVendorBean;
    }
}
