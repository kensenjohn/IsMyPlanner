package com.events.data.event.budget;

import com.events.bean.event.budget.EventBudgetBean;
import com.events.bean.event.budget.EventBudgetCategoryBean;
import com.events.bean.event.budget.EventBudgetCategoryItemBean;
import com.events.bean.event.budget.EventBudgetRequestBean;
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
 * Date: 5/16/14
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventBudgetData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTBUDGET ( EVENTBUDGETID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, ESTIMATE_BUDGET VARCHAR(45),
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45), FK_USERID VARCHAR(45)
    public EventBudgetBean getEventBudgetByEvent(EventBudgetRequestBean eventBudgetRequestBean ){
        EventBudgetBean eventBudgetBean = new EventBudgetBean();
        if( eventBudgetRequestBean!=null && !Utility.isNullOrEmpty( eventBudgetRequestBean.getEventId() )) {
            String sQuery = "SELECT * FROM GTEVENTBUDGET WHERE FK_EVENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetRequestBean.getEventId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventBudgetData.java", "getEventBudgetByEvent()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    eventBudgetBean = new EventBudgetBean(hmResult);
                }
            }
        }
        return eventBudgetBean;
    }

    //GTEVENTBUDGETCATEGORY  BUDGETCATEGORYID VARCHAR(45) NOT NULL, FK_EVENTBUDGETID VARCHAR(45) NOT NULL,FK_EVENTID VARCHAR(45) NOT NULL,
    // CATEGORYNAME TEXT NOT NULL,  CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45), FK_USERID VARCHAR(45),
    // PRIMARY KEY (BUDGETCATEGORYID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public ArrayList<EventBudgetCategoryBean> getEventBudgetCategoryByEvent(EventBudgetRequestBean eventBudgetRequestBean ){
        ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean= new ArrayList<EventBudgetCategoryBean>();

        if( eventBudgetRequestBean!=null && !Utility.isNullOrEmpty( eventBudgetRequestBean.getEventId() )) {
            String sQuery = "SELECT * FROM GTEVENTBUDGETCATEGORY WHERE FK_EVENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetRequestBean.getEventId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventBudgetData.java", "getEventBudgetCategoryByEvent()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean(hmResult);
                    arrEventBudgetCategoryBean.add( eventBudgetCategoryBean );
                }
            }
        }
        return arrEventBudgetCategoryBean;
    }

    public EventBudgetCategoryBean getEventBudgetCategoryByCategory(EventBudgetRequestBean eventBudgetRequestBean ){
        EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();

        if( eventBudgetRequestBean!=null && !Utility.isNullOrEmpty( eventBudgetRequestBean.getBudgetCategoryId() )) {
            String sQuery = "SELECT * FROM GTEVENTBUDGETCATEGORY WHERE BUDGETCATEGORYID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetRequestBean.getBudgetCategoryId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventBudgetData.java", "getEventBudgetCategoryByCategory()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    eventBudgetCategoryBean = new EventBudgetCategoryBean(hmResult);
                }
            }
        }
        return eventBudgetCategoryBean;
    }

    // GTEVENTBUDGETCATEGORYITEM( BUDGETCATEGORYITEMID VARCHAR(45) NOT NULL, FK_BUDGETCATEGORYID VARCHAR(45) NOT NULL,
    // FK_EVENTBUDGETID VARCHAR(45) NOT NULL,FK_EVENTID VARCHAR(45) NOT NULL,  ITEMNAME TEXT NOT NULL, ESTIMATE_AMOUNT VARCHAR(45),
    // ACTUAL_AMOUNT VARCHAR(45), IS_PAID INT(1) NOT NULL DEFAULT 0, MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),
    // FK_USERID VARCHAR(45), PRIMARY KEY (BUDGETCATEGORYITEMID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public ArrayList<EventBudgetCategoryItemBean> getEventBudgetItemsByEvent(EventBudgetRequestBean eventBudgetRequestBean ){
        ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBean = new ArrayList<EventBudgetCategoryItemBean>();
        EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
        if( eventBudgetRequestBean!=null && !Utility.isNullOrEmpty( eventBudgetRequestBean.getEventId() )) {
            String sQuery = "SELECT * FROM GTEVENTBUDGETCATEGORYITEM WHERE FK_EVENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetRequestBean.getEventId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventBudgetData.java", "getEventBudgetItemsByEvent()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventBudgetCategoryItemBean eventBudgetCategoryItemBean = new EventBudgetCategoryItemBean(hmResult);
                    arrEventBudgetCategoryItemBean.add(eventBudgetCategoryItemBean) ;
                }
            }
        }
        return arrEventBudgetCategoryItemBean;
    }

    public ArrayList<EventBudgetCategoryItemBean> getEventBudgetItemsByCategory(EventBudgetRequestBean eventBudgetRequestBean ){
        ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBean = new ArrayList<EventBudgetCategoryItemBean>();
        EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
        if( eventBudgetRequestBean!=null && !Utility.isNullOrEmpty( eventBudgetRequestBean.getBudgetCategoryId() )) {
            String sQuery = "SELECT * FROM GTEVENTBUDGETCATEGORYITEM WHERE FK_BUDGETCATEGORYID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventBudgetRequestBean.getBudgetCategoryId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventBudgetData.java", "getEventBudgetItemsByCategory()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventBudgetCategoryItemBean eventBudgetCategoryItemBean = new EventBudgetCategoryItemBean(hmResult);
                    arrEventBudgetCategoryItemBean.add(eventBudgetCategoryItemBean) ;
                }
            }
        }
        return arrEventBudgetCategoryItemBean;
    }
}
