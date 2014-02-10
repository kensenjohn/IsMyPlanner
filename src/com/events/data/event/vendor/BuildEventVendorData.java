package com.events.data.event.vendor;

import com.events.bean.event.vendor.EventVendorRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventVendorData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertEventVendor(EventVendorRequestBean eventVendorRequestBean) {
        Integer numOfRowsInserted = 0;
        if(eventVendorRequestBean!=null) {
            //GTEVENTVENDORS(EVENTVENDORID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL,
            String sQuery = "INSERT into GTEVENTVENDORS(EVENTVENDORID,FK_EVENTID,FK_VENDORID) VALUES " +
                    " (?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventVendorRequestBean.getEventVendorId(), eventVendorRequestBean.getEventId(), eventVendorRequestBean.getVendorId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventVendorData.java", "insertEventVendor() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteEventVendor(EventVendorRequestBean eventVendorRequestBean) {
        Integer numOfRowsInserted = 0;
        if(eventVendorRequestBean!=null) {
            //GTEVENTVENDORS(EVENTVENDORID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL,
            String sQuery = "DELETE FROM GTEVENTVENDORS WHERE FK_EVENTID = ? AND FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventVendorRequestBean.getEventId(), eventVendorRequestBean.getVendorId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventVendorData.java", "deleteEventVendor() ");
        }
        return numOfRowsInserted;
    }
}
