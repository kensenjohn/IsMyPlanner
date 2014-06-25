package com.events.data.event.budget;

import com.events.bean.event.budget.EventBudgetBean;
import com.events.bean.event.budget.EventBudgetCategoryBean;
import com.events.bean.event.budget.EventBudgetCategoryItemBean;
import com.events.bean.event.budget.EventBudgetRequestBean;
import com.events.common.*;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/16/14
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventBudgetData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTBUDGET ( EVENTBUDGETID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, ESTIMATE_BUDGET VARCHAR(45),
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45), FK_USERID VARCHAR(45)
    public Integer insertEventBudget(EventBudgetBean eventBudgetBean){
        Integer iNumOfRowsInserted = 0;
        if( eventBudgetBean!=null ) {
            String sQuery = "INSERT INTO GTEVENTBUDGET (EVENTBUDGETID,FK_EVENTID,ESTIMATE_BUDGET,     MODIFIEDDATE,HUMANMODIFIEDDATE,FK_USERID ) VALUES (?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetBean.getEventBudgetId(), eventBudgetBean.getEventId(), eventBudgetBean.getEstimateBudget(),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), eventBudgetBean.getUserId() );

            iNumOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "updateEventBudget() ");
        }
        return iNumOfRowsInserted;
    }

    public Integer updateEventBudget(EventBudgetBean eventBudgetBean){
        Integer iNumOfRowsUpdated = 0;
        if( eventBudgetBean!=null ) {
            String sQuery = "UPDATE GTEVENTBUDGET SET ESTIMATE_BUDGET = ?,MODIFIEDDATE=?,HUMANMODIFIEDDATE=?,     FK_USERID=? WHERE EVENTBUDGETID=?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetBean.getEstimateBudget(),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), eventBudgetBean.getUserId(),eventBudgetBean.getEventBudgetId() );

            iNumOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "updateEventBudget() ");
        }
        return iNumOfRowsUpdated;
    }

    // CREATE TABLE GTEVENTBUDGETCATEGORY( BUDGETCATEGORYID VARCHAR(45) NOT NULL, FK_EVENTBUDGETID VARCHAR(45) NOT NULL,
    // FK_EVENTID VARCHAR(45) NOT NULL,  CATEGORYNAME TEXT NOT NULL,  CREATEDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANCREATEDATE VARCHAR(45), FK_USERID VARCHAR(45), PRIMARY KEY (BUDGETCATEGORYID) ) ENGINE = MyISAM DEFAULT
    // CHARSET = utf8

    public Integer insertEventBudgetCategory(EventBudgetCategoryBean eventBudgetCategoryBean){
        Integer iNumOfRowsInserted = 0;
        if( eventBudgetCategoryBean!=null ) {
            String sQuery = "INSERT INTO GTEVENTBUDGETCATEGORY (BUDGETCATEGORYID,FK_EVENTBUDGETID,FK_EVENTID,    " +
                    "CATEGORYNAME,CREATEDATE,HUMANCREATEDATE,     FK_USERID ) VALUES (?,?,?,   ?,?,?,    ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(
                    eventBudgetCategoryBean.getBudgetCategoryId(),eventBudgetCategoryBean.getEventBudgetId(), eventBudgetCategoryBean.getEventId(),
                    eventBudgetCategoryBean.getCategoryName(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                    eventBudgetCategoryBean.getUserId() );

            iNumOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "insertEventBudgetCategory() ");
        }
        return iNumOfRowsInserted;
    }

    public Integer insertEventBudgetCategory( ArrayList<EventBudgetRequestBean> arrEventBudgetRequestBean ) {
        Integer iNumOfRowsInserted = 0;
        if(arrEventBudgetRequestBean!=null && !arrEventBudgetRequestBean.isEmpty() ){
            String sQuery = "INSERT INTO GTEVENTBUDGETCATEGORY (BUDGETCATEGORYID,FK_EVENTBUDGETID,FK_EVENTID,    " +
                    "CATEGORYNAME,CREATEDATE,HUMANCREATEDATE,     FK_USERID ) VALUES (?,?,?,   ?,?,?,    ?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(EventBudgetRequestBean eventBudgetRequestBean : arrEventBudgetRequestBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(
                        eventBudgetRequestBean.getBudgetCategoryId(),eventBudgetRequestBean.getEventBudgetId(), eventBudgetRequestBean.getEventId(),
                        eventBudgetRequestBean.getCategoryName(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                        eventBudgetRequestBean.getUserId() );
                aBatchParams.add( aParams );
            }
            int[] aNumOfRowsInserted= DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "insertEventBudgetCategory() " );

            if(aNumOfRowsInserted!=null && aNumOfRowsInserted.length > 0 ) {
                for(int iCount : aNumOfRowsInserted ) {
                    iNumOfRowsInserted = iNumOfRowsInserted + iCount;
                }
            }
        }
        return iNumOfRowsInserted;
    }

    public Integer updateEventBudgetCategory(EventBudgetCategoryBean eventBudgetCategoryBean){
        Integer iNumOfRowsUpdated = 0;
        if( eventBudgetCategoryBean!=null ) {
            String sQuery = "UPDATE GTEVENTBUDGETCATEGORY SET CATEGORYNAME = ? WHERE BUDGETCATEGORYID=? AND FK_EVENTBUDGETID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetCategoryBean.getCategoryName(),
                    eventBudgetCategoryBean.getBudgetCategoryId(),eventBudgetCategoryBean.getEventBudgetId() );

            iNumOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "updateEventBudgetCategory() ");
        }
        return iNumOfRowsUpdated;
    }

    public Integer deleteEventBudgetCategory(EventBudgetRequestBean eventBudgetRequestBean){
        Integer iNumOfRowsDeleted = 0;
        if( eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId()) ) {
            String sQuery = "DELETE FROM GTEVENTBUDGETCATEGORY WHERE BUDGETCATEGORYID = ? AND FK_EVENTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventBudgetRequestBean.getBudgetCategoryId() , eventBudgetRequestBean.getEventId() );

            iNumOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "deleteEventBudgetCategory() ");
        }
        return iNumOfRowsDeleted;
    }

    // CREATE TABLE GTEVENTBUDGETCATEGORYITEM( BUDGETCATEGORYITEMID VARCHAR(45) NOT NULL, FK_BUDGETCATEGORYID VARCHAR(45) NOT NULL,
    // FK_EVENTBUDGETID VARCHAR(45) NOT NULL,FK_EVENTID VARCHAR(45) NOT NULL,  ITEMNAME TEXT NOT NULL, ESTIMATE_AMOUNT VARCHAR(45),
    // ACTUAL_AMOUNT VARCHAR(45), IS_PAID INT(1) NOT NULL DEFAULT 0, MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),
    // FK_USERID VARCHAR(45), PRIMARY KEY (BUDGETCATEGORYITEMID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public Integer deleteAllEventBudgetCategoryItems( EventBudgetRequestBean eventBudgetRequestBean ) {
        Integer iNumOfRowsDeleted = 0;
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId() )) {
            String sQuery = "DELETE FROM GTEVENTBUDGETCATEGORYITEM WHERE FK_BUDGETCATEGORYID = ? AND FK_EVENTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetRequestBean.getBudgetCategoryId(),eventBudgetRequestBean.getEventId() );

            iNumOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "deleteAllEventBudgetCategoryItems() ");
        }
        return iNumOfRowsDeleted;
    }
    public Integer deleteEventBudgetCategoryItem( EventBudgetRequestBean eventBudgetRequestBean ) {
        Integer iNumOfRowsDeleted = 0;
        if(eventBudgetRequestBean!=null && eventBudgetRequestBean.getEventBudgetCategoryItemBean()!=null ){
            EventBudgetCategoryItemBean eventBudgetCategoryItemBean =  eventBudgetRequestBean.getEventBudgetCategoryItemBean();
            if(eventBudgetCategoryItemBean!=null && !Utility.isNullOrEmpty(eventBudgetCategoryItemBean.getBudgetCategoryItemId())
                    && !Utility.isNullOrEmpty(eventBudgetCategoryItemBean.getEventId())  ) {
                String sQuery = "DELETE FROM GTEVENTBUDGETCATEGORYITEM WHERE BUDGETCATEGORYITEMID = ? AND FK_EVENTID =?";
                ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetCategoryItemBean.getBudgetCategoryItemId(),eventBudgetCategoryItemBean.getEventId() );

                iNumOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "deleteEventBudgetCategoryItems() ");
            }
        }
        return iNumOfRowsDeleted;
    }


    public Integer insertEventBudgetCategoryItems( EventBudgetRequestBean eventBudgetRequestBean ) {
        Integer iNumOfRowsInserted = 0;
        if(eventBudgetRequestBean!=null){
            ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBeans = eventBudgetRequestBean.getArrEventBudgetCategoryItemBeans();
            if(arrEventBudgetCategoryItemBeans!=null && !arrEventBudgetCategoryItemBeans.isEmpty()) {
                String sQuery = "INSERT INTO GTEVENTBUDGETCATEGORYITEM (BUDGETCATEGORYITEMID,FK_BUDGETCATEGORYID,FK_EVENTBUDGETID," +
                        " FK_EVENTID,ITEMNAME,ESTIMATE_AMOUNT,     ACTUAL_AMOUNT,IS_PAID,MODIFIEDDATE,    HUMANMODIFIEDDATE,FK_USERID ) " +
                        " VALUES (?,?,?,     ?,?,?,   ?,?,?,    ?,?)";
                ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
                for(EventBudgetCategoryItemBean eventBudgetCategoryItemBean : arrEventBudgetCategoryItemBeans ) {
                    ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetCategoryItemBean.getBudgetCategoryItemId(),eventBudgetRequestBean.getBudgetCategoryId(),eventBudgetCategoryItemBean.getEventBudgetId(),
                            eventBudgetCategoryItemBean.getEventId(),eventBudgetCategoryItemBean.getItemName(),eventBudgetCategoryItemBean.getEstimatedAmount(),
                            eventBudgetCategoryItemBean.getActualAmount(),eventBudgetCategoryItemBean.isPaid()?"1":"0",DateSupport.getEpochMillis(),
                            DateSupport.getUTCDateTime(),eventBudgetRequestBean.getUserId() );
                    aBatchParams.add( aParams );
                }
                int[] aNumOfRowsInserted= DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildEventBudgetData.java", "insertEventBudgetCategoryItems() " );

                if(aNumOfRowsInserted!=null && aNumOfRowsInserted.length > 0 ) {
                    for(int iCount : aNumOfRowsInserted ) {
                        iNumOfRowsInserted = iNumOfRowsInserted + iCount;
                    }
                }
            }
        }
        return iNumOfRowsInserted;
    }
}
