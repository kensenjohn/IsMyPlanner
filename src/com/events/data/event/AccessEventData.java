package com.events.data.event;

import com.events.bean.common.todo.AssignedToDoEventsBean;
import com.events.bean.common.todo.ToDoRequestBean;
import com.events.bean.event.EventBean;
import com.events.bean.event.EventRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public EventBean getEvent(EventRequestBean eventRequestBean) {
        EventBean eventBean = new EventBean();
        if(eventRequestBean!=null && !"".equalsIgnoreCase(eventRequestBean.getEventId())) {
            String sQuery  = "SELECT * FROM GTEVENT WHERE EVENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventRequestBean.getEventId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventData.java", "getEvent()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    eventBean = new EventBean(hmResult);
                }
            }
        }
        return eventBean;
    }

    public ArrayList<EventBean> getVendorEvents(EventRequestBean eventRequestBean ){
        ArrayList<EventBean> arrEventBean = new ArrayList<EventBean>();
        if(eventRequestBean!=null && !Utility.isNullOrEmpty(eventRequestBean.getEventVendorId())) {
            String sQuery  = "SELECT * FROM GTEVENT WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventRequestBean.getEventVendorId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventData.java", "getVendorEvents()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventBean eventBean = new EventBean(hmResult);
                    arrEventBean.add(eventBean);
                }
            }
        }
        return  arrEventBean;
    }

    public ArrayList<EventBean> getClientEvents(EventRequestBean eventRequestBean ){
        ArrayList<EventBean> arrEventBean = new ArrayList<EventBean>();
        if(eventRequestBean!=null && !Utility.isNullOrEmpty(eventRequestBean.getEventClient())) {
            String sQuery  = "SELECT * FROM GTEVENT WHERE FK_CLIENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventRequestBean.getEventClient());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventData.java", "getClientEvents()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventBean eventBean = new EventBean(hmResult);
                    arrEventBean.add(eventBean);
                }
            }
        }
        return  arrEventBean;
    }

}
