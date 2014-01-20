package com.events.data.event;

import com.events.bean.event.EventRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertEvent(EventRequestBean eventRequestBean) {
        Integer numOfRowsInserted = 0;
        if(eventRequestBean!=null) {
            String sQuery = "INSERT into GTEVENT(EVENTID,EVENTNAME,FK_FOLDERID,      EVENTDATE,HUMANEVENTDATE,FK_CLIENTID,     FK_VENDORID,CREATEDATE,HUMANCREATEDATE ) VALUES " +
                    " (?,?,?,    ?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventRequestBean.getEventId(),eventRequestBean.getEventName(),eventRequestBean.getEventVendorId(),
                    eventRequestBean.getEventDate(),eventRequestBean.getEventHumanDate(),eventRequestBean.getEventClient(),
                    eventRequestBean.getEventVendorId(), DateSupport.getEpochMillis(),DateSupport.getUTCDateTime() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildClientData.java", "insertClientData() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateEvent(EventRequestBean eventRequestBean) {
        Integer numOfRowsInserted = 0;
        if(eventRequestBean!=null) {
            String sQuery = "UPDATE GTEVENT SET EVENTNAME = ?, EVENTDATE = ?,HUMANEVENTDATE= ?,     FK_CLIENTID = ?   WHERE   EVENTID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventRequestBean.getEventName(),
                    eventRequestBean.getEventDate(),eventRequestBean.getEventHumanDate(),eventRequestBean.getEventClient(),
                    eventRequestBean.getEventId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildClientData.java", "updateEvent() ");
        }
        return numOfRowsInserted;
    }

    public Integer toggleEventDelete(EventRequestBean eventRequestBean) {
        Integer numOfRowsUpdated = 0;
        if(eventRequestBean!=null) {
            String sQuery = "UPDATE GTEVENT SET DEL_ROW = ? WHERE EVENTID = ? AND FK_VENDORID= ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( (eventRequestBean.isEventDelete()?"1":"0"), eventRequestBean.getEventId(), eventRequestBean.getEventVendorId()) ;
            numOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildClientData.java", "toggleEventDelete() ");
        }
        return numOfRowsUpdated;
    }
}
