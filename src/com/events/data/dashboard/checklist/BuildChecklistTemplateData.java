package com.events.data.dashboard.checklist;

import com.events.bean.dashboard.checklist.ChecklistTemplateBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateItemBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateRequestBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateTodoBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildChecklistTemplateData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    // GTCHECKLISTTEMPLATE( CHECKLISTTEMPLATEID VARCHAR(45) NOT NULL, NAME VARCHAR(500) NOT NULL, FK_VENDORID  VARCHAR(45) NOT NULL,
    // IS_DEFAULT INT(1) NOT NULL DEFAULT 0 , CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45), PRIMARY KEY (CHECKLISTTEMPLATEID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    public Integer insertChecklistTemplate( ChecklistTemplateBean checklistTemplateBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateBean!=null && !Utility.isNullOrEmpty(checklistTemplateBean.getChecklistTemplateId()) && !Utility.isNullOrEmpty(checklistTemplateBean.getName()) ) {
            String sQuery = "INSERT INTO GTCHECKLISTTEMPLATE (CHECKLISTTEMPLATEID,NAME,FK_VENDORID,  IS_DEFAULT,CREATEDATE,HUMANCREATEDATE) VALUES (?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateBean.getChecklistTemplateId(), checklistTemplateBean.getName(),checklistTemplateBean.getVendorId(),
                    (checklistTemplateBean.isDefault()?"1":"0"),DateSupport.getEpochMillis(), DateSupport.getUTCDateTime());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "insertChecklistTemplate() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateChecklistTemplate( ChecklistTemplateBean checklistTemplateBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateBean!=null && !Utility.isNullOrEmpty(checklistTemplateBean.getChecklistTemplateId()) && !Utility.isNullOrEmpty(checklistTemplateBean.getName()) ) {
            String sQuery = "UPDATE GTCHECKLISTTEMPLATE SET NAME = ? WHERE CHECKLISTTEMPLATEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( checklistTemplateBean.getName(), checklistTemplateBean.getChecklistTemplateId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "updateChecklistTemplate() ");
        }
        return numOfRowsInserted;
    }

    // GTCHECKLISTTEMPLATEITEM( CHECKLISTTEMPLATEITEMID VARCHAR(45) NOT NULL, FK_CHECKLISTTEMPLATEID VARCHAR(45) NOT NULL,
    // NAME VARCHAR(500) NOT NULL, CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45), PRIMARY KEY (CHECKLISTTEMPLATEITEMID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public Integer insertChecklistTemplateItem( ChecklistTemplateItemBean checklistTemplateItemBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateItemBean!=null && !Utility.isNullOrEmpty(checklistTemplateItemBean.getChecklistTemplateId()) && !Utility.isNullOrEmpty(checklistTemplateItemBean.getName())
                && !Utility.isNullOrEmpty(checklistTemplateItemBean.getChecklistTemplateItemId())  ) {
            String sQuery = "INSERT INTO GTCHECKLISTTEMPLATEITEM (CHECKLISTTEMPLATEITEMID, FK_CHECKLISTTEMPLATEID,NAME,   CREATEDATE,HUMANCREATEDATE,SORT_ORDER) VALUES (?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateItemBean.getChecklistTemplateItemId() , checklistTemplateItemBean.getChecklistTemplateId(), checklistTemplateItemBean.getName(),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), checklistTemplateItemBean.getSortOrder() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "insertChecklistTemplateItem() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateChecklistTemplateItemSort( ChecklistTemplateItemBean checklistTemplateItemBean){
        Integer numOfRowsSortUpdated = 0;
        if(checklistTemplateItemBean!=null){
            String sQuery = "UPDATE GTCHECKLISTTEMPLATEITEM SET SORT_ORDER = ? WHERE CHECKLISTTEMPLATEITEMID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateItemBean.getSortOrder(), checklistTemplateItemBean.getChecklistTemplateItemId() );
            numOfRowsSortUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "updateChecklistTemplateItemSort() ");
        }
        return numOfRowsSortUpdated;
    }

    public Integer updateChecklistTemplateItem( ChecklistTemplateItemBean checklistTemplateItemBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateItemBean!=null && !Utility.isNullOrEmpty(checklistTemplateItemBean.getChecklistTemplateId()) && !Utility.isNullOrEmpty(checklistTemplateItemBean.getName())
                && !Utility.isNullOrEmpty(checklistTemplateItemBean.getChecklistTemplateItemId())  ) {
            String sQuery = "UPDATE GTCHECKLISTTEMPLATEITEM SET NAME = ? WHERE CHECKLISTTEMPLATEITEMID=? AND FK_CHECKLISTTEMPLATEID=?";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateItemBean.getName(),checklistTemplateItemBean.getChecklistTemplateItemId() , checklistTemplateItemBean.getChecklistTemplateId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "updateChecklistTemplateItem() ");
        }
        return numOfRowsInserted;
    }

    public Integer insertChecklistTemplateItems( ArrayList<ChecklistTemplateItemBean> arrChecklistTemplateItemBean ){
        Integer numOfRowsInserted = 0;
        if(arrChecklistTemplateItemBean!=null && !arrChecklistTemplateItemBean.isEmpty() ) {
            String sQuery = "INSERT INTO GTCHECKLISTTEMPLATEITEM (CHECKLISTTEMPLATEITEMID, FK_CHECKLISTTEMPLATEID,NAME,   CREATEDATE,HUMANCREATEDATE,SORT_ORDER) VALUES (?,?,?,   ?,?,?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(ChecklistTemplateItemBean checklistTemplateItemBean : arrChecklistTemplateItemBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateItemBean.getChecklistTemplateItemId() , checklistTemplateItemBean.getChecklistTemplateId(), checklistTemplateItemBean.getName(),
                        DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), checklistTemplateItemBean.getSortOrder() );
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "insertChecklistTemplateItems() " );
            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }

    public Integer insertChecklistTemplateTodo( ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean){
        Integer numOfRowsInserted = 0;
        if(arrChecklistTemplateTodoBean!=null && !arrChecklistTemplateTodoBean.isEmpty() ) {
            String sQuery = "INSERT INTO GTCHECKLISTTEMPLATETODO (CHECKLISTTEMPLATETODOID,FK_CHECKLISTTEMPLATEITEMID,FK_CHECKLISTTEMPLATEID,     NAME,CREATEDATE,HUMANCREATEDATE) VALUES (?,?,?,    ?,?,?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(ChecklistTemplateTodoBean checklistTemplateTodoBean : arrChecklistTemplateTodoBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateTodoBean.getChecklistTemplateTodoId(),checklistTemplateTodoBean.getChecklistTemplateItemId(),checklistTemplateTodoBean.getChecklistTemplateId(),
                        checklistTemplateTodoBean.getName(),DateSupport.getEpochMillis(),DateSupport.getUTCDateTime());
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "insertChecklistTemplateTodo() " );
            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }

    public Integer deleteChecklistTemplate( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId()) ) {
            String sQuery = "DELETE FROM GTCHECKLISTTEMPLATE WHERE CHECKLISTTEMPLATEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( checklistTemplateRequestBean.getChecklistTemplateId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "deleteChecklistTemplate() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteChecklistTemplateItem( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())
                && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId()) ) {
            String sQuery = "DELETE FROM GTCHECKLISTTEMPLATEITEM WHERE FK_CHECKLISTTEMPLATEID = ? AND CHECKLISTTEMPLATEITEMID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( checklistTemplateRequestBean.getChecklistTemplateId(),checklistTemplateRequestBean.getChecklistTemplateItemId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "deleteChecklistTemplateItem() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteChecklistTemplateTodo( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateTodoId())  ) {
            String sQuery = "DELETE FROM GTCHECKLISTTEMPLATETODO WHERE CHECKLISTTEMPLATETODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( checklistTemplateRequestBean.getChecklistTemplateTodoId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "deleteChecklistTemplateTodo() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteAllChecklistTemplateTodo( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        Integer numOfRowsInserted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId())  ) {
            String sQuery = "DELETE FROM GTCHECKLISTTEMPLATETODO WHERE FK_CHECKLISTTEMPLATEITEMID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( checklistTemplateRequestBean.getChecklistTemplateItemId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "deleteChecklistTemplateTodo() ");
        }
        return numOfRowsInserted;
    }
}
