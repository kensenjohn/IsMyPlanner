package com.events.data.event;

import com.events.bean.event.EveryEventBean;
import com.events.bean.event.EveryEventRequestBean;
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
 * Date: 12/29/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEveryEventData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<EveryEventBean> getEveryEventByVendor(EveryEventRequestBean everyEventRequestBean){
        ArrayList<EveryEventBean> arrEveryEventBean  = new ArrayList<EveryEventBean>();
        if(everyEventRequestBean!=null && !"".equalsIgnoreCase(everyEventRequestBean.getVendorId())) {
            String sQuery = "SELECT * FROM GTEVENT WHERE FK_VENDORID= ? AND DEL_ROW = ? ORDER BY EVENTDATE ASC";
            ArrayList<Object> aParams = DBDAO.createConstraint( everyEventRequestBean.getVendorId() , everyEventRequestBean.isDeletedEvent()?"1":"0" );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEveryEventData.java", "getEveryEventByVendor()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmEveryEventResult : arrResult) {
                    EveryEventBean everyEventBean = new EveryEventBean(hmEveryEventResult);
                    arrEveryEventBean.add( everyEventBean );
                }
            }

        }
        return arrEveryEventBean;
    }

    public ArrayList<EveryEventBean> getEveryEventByClient(EveryEventRequestBean everyEventRequestBean){
        ArrayList<EveryEventBean> arrEveryEventBean  = new ArrayList<EveryEventBean>();
        if(everyEventRequestBean!=null && !"".equalsIgnoreCase(everyEventRequestBean.getClientId())) {
            String sQuery = "SELECT * FROM GTEVENT WHERE FK_CLIENTID= ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( everyEventRequestBean.getClientId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEveryEventData.java", "getEveryEventByVendor()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmEveryEventResult : arrResult) {
                    EveryEventBean everyEventBean = new EveryEventBean(hmEveryEventResult);
                    arrEveryEventBean.add( everyEventBean );
                }
            }

        }
        return arrEveryEventBean;
    }
}
