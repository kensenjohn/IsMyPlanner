package com.events.data.event.checklist;

import com.events.bean.event.checklist.EventChecklistBean;
import com.events.bean.event.checklist.EventChecklistItemBean;
import com.events.bean.event.checklist.EventChecklistRequestBean;
import com.events.bean.event.checklist.EventChecklistTodoBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 7/1/14
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventChecklistData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //  GTEVENTCHECKLIST(EVENTCHECKLISTID VARCHAR(45) NOT NULL,  NAME VARCHAR(500) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL, FK_VENDORID  VARCHAR(45) NOT NULL ,
    // FK_CLIENTID  VARCHAR(45) NOT NULL , CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),

    public Integer insertEventChecklist( EventChecklistBean eventChecklistBean ){
        Integer numOfRowsInserted = 0;
        if(eventChecklistBean!=null && !Utility.isNullOrEmpty(eventChecklistBean.getEventChecklistId())){
            String sQuery = "INSERT INTO GTEVENTCHECKLIST (EVENTCHECKLISTID,NAME,FK_EVENTID,   FK_VENDORID,FK_CLIENTID,CREATEDATE, " +
                    "  HUMANCREATEDATE) VALUES (?,?,?,   ?,?,?,    ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventChecklistBean.getEventChecklistId(),eventChecklistBean.getName(),eventChecklistBean.getEventId(),
                    eventChecklistBean.getVendorId(),eventChecklistBean.getClientId(), DateSupport.getEpochMillis(),
                    DateSupport.getUTCDateTime());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "insertChecklistTemplate() ");
        }
        return numOfRowsInserted;
    }
    public Integer updateEventChecklist( EventChecklistBean eventChecklistBean  ){
        Integer numOfRowsUpdated = 0;
        if(eventChecklistBean!=null && !Utility.isNullOrEmpty(eventChecklistBean.getEventChecklistId())  && !Utility.isNullOrEmpty(eventChecklistBean.getName()) ){
            String sQuery = "UPDATE GTEVENTCHECKLIST SET NAME = ? WHERE EVENTCHECKLISTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventChecklistBean.getName()  , eventChecklistBean.getEventChecklistId());
            numOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "insertChecklistTemplate() ");
        }
        return numOfRowsUpdated;
    }

    public Integer deleteEventChecklist( EventChecklistRequestBean eventChecklistRequestBean ){
        Integer numOfRowsDeleted = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId() )) {
            String sQuery = "DELETE FROM GTEVENTCHECKLIST WHERE EVENTCHECKLISTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventChecklistRequestBean.getChecklistId());
            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "deleteEventChecklist() ");
        }
        return numOfRowsDeleted;
    }

    public Integer deleteEventChecklistItem( EventChecklistRequestBean eventChecklistRequestBean ){
        Integer numOfRowsDeleted = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistItemId() )) {
            String sQuery = "DELETE FROM GTEVENTCHECKLISTITEM WHERE EVENTCHECKLISTITEMID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventChecklistRequestBean.getChecklistItemId());
            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "deleteEventChecklistItem() ");
        }
        return numOfRowsDeleted;
    }

    public Integer deleteEventChecklistTodo( EventChecklistRequestBean eventChecklistRequestBean ){
        Integer numOfRowsDeleted = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistTodoId() )) {
            String sQuery = "DELETE FROM GTEVENTCHECKLISTTODO WHERE EVENTCHECKLISTTODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventChecklistRequestBean.getChecklistTodoId());
            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "deleteEventChecklistTodo() ");
        }
        return numOfRowsDeleted;
    }

    public Integer updateEventChecklistItemAction( EventChecklistRequestBean eventChecklistRequestBean ){
        Integer numOfRowsDeleted = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistItemId())) {
            String sQuery = "UPDATE GTEVENTCHECKLISTITEM SET IS_COMPLETE =? WHERE EVENTCHECKLISTITEMID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventChecklistRequestBean.isComplete()?"1":"0",eventChecklistRequestBean.getChecklistItemId());
            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "updateEventChecklistItemAction() ");
        }
        return numOfRowsDeleted;
    }

    // GTEVENTCHECKLISTITEM( EVENTCHECKLISTITEMID VARCHAR(45) NOT NULL, FK_EVENTCHECKLISTID VARCHAR(45) NOT NULL, NAME VARCHAR(500) NOT NULL,
    // CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANCREATEDATE VARCHAR(45), SORT_ORDER  BIGINT(20) NOT NULL DEFAULT -1, IS_COMPLETE INT(1) NOT NULL DEFAULT 0 ,
    // PRIMARY KEY (EVENTCHECKLISTITEMID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public Integer createEventChecklistItem( ArrayList<EventChecklistItemBean> arrEventChecklistItemBean ){
        Integer numOfRowsInserted = 0;
        if(arrEventChecklistItemBean!=null && !arrEventChecklistItemBean.isEmpty() ) {

            String sQuery = "INSERT INTO GTEVENTCHECKLISTITEM (EVENTCHECKLISTITEMID,FK_EVENTCHECKLISTID,NAME,   CREATEDATE,HUMANCREATEDATE,SORT_ORDER, " +
                    "  IS_COMPLETE) VALUES (?,?,?,   ?,?,?,    ?)";

            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(EventChecklistItemBean eventChecklistItemBean : arrEventChecklistItemBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(
                        eventChecklistItemBean.getEventChecklistItemId(),eventChecklistItemBean.getEventChecklistId(), eventChecklistItemBean.getName(),
                        DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),eventChecklistItemBean.getSortOrder(),
                        eventChecklistItemBean.isComplete()?"1":"0" );
                aBatchParams.add( aParams );
            }

            int[] aNumOfRowsInserted= DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "createEventChecklistItem() " );

            if(aNumOfRowsInserted!=null && aNumOfRowsInserted.length > 0 ) {
                for(int iCount : aNumOfRowsInserted ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }

    // GTEVENTCHECKLISTTODO( EVENTCHECKLISTTODOID VARCHAR(45) NOT NULL, FK_EVENTCHECKLISTITEMID VARCHAR(45) NOT NULL,
    // FK_EVENTCHECKLISTID VARCHAR(45) NOT NULL, FK_TODOID VARCHAR(45) NOT NULL, NAME VARCHAR(500) NOT NULL,
    // CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),IS_COMPLETE INT(1) NOT NULL DEFAULT 0 , PRIMARY KEY (EVENTCHECKLISTTODOID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public Integer createChecklistItemTodo( ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean){
        Integer numOfRowsInserted = 0;
        if(arrEventChecklistTodoBean!=null && !arrEventChecklistTodoBean.isEmpty() ) {

            String sQuery = "INSERT INTO GTEVENTCHECKLISTTODO (EVENTCHECKLISTTODOID,FK_EVENTCHECKLISTITEMID,FK_EVENTCHECKLISTID,   FK_TODOID,NAME,CREATEDATE," +
                    " HUMANCREATEDATE,IS_COMPLETE ) VALUES (?,?,?,   ?,?,?,    ?,?)";

            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(EventChecklistTodoBean eventChecklistTodoBean : arrEventChecklistTodoBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(
                        eventChecklistTodoBean.getEventChecklistTodoId() , eventChecklistTodoBean.getEventChecklistItemId(),eventChecklistTodoBean.getEventChecklistId(),
                        eventChecklistTodoBean.getTodoId(), eventChecklistTodoBean.getName(),DateSupport.getEpochMillis(),
                        DateSupport.getUTCDateTime(), eventChecklistTodoBean.isComplete()?"1":"0"  );
                aBatchParams.add( aParams );
            }

            int[] aNumOfRowsInserted= DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "createChecklistItemTodo() " );

            if(aNumOfRowsInserted!=null && aNumOfRowsInserted.length > 0 ) {
                for(int iCount : aNumOfRowsInserted ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }

        return numOfRowsInserted;
    }
}
