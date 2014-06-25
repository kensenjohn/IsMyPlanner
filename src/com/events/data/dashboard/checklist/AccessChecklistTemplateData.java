package com.events.data.dashboard.checklist;

import com.events.bean.dashboard.checklist.ChecklistTemplateBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateItemBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateRequestBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateTodoBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessChecklistTemplateData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ChecklistTemplateBean getChecklistTemplateBean(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())){
            String sQuery = "SELECT * FROM GTCHECKLISTTEMPLATE WHERE CHECKLISTTEMPLATEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateRequestBean.getChecklistTemplateId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessChecklistTemplateData.java", "getChecklistTemplateBean()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    checklistTemplateBean = new ChecklistTemplateBean( hmResult );
                }
            }
        }
        return checklistTemplateBean;
    }

    public ArrayList<ChecklistTemplateBean> getAllChecklistTemplatesByVendor(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ArrayList<ChecklistTemplateBean> arrChecklistTemplateBean = new ArrayList<ChecklistTemplateBean>();
        if(checklistTemplateRequestBean!=null  && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getVendorId())){
            String sQuery = "SELECT * FROM GTCHECKLISTTEMPLATE WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateRequestBean.getVendorId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessChecklistTemplateData.java", "getChecklistTemplateBean()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean( hmResult );
                    arrChecklistTemplateBean.add( checklistTemplateBean );
                }
            }
        }
        return arrChecklistTemplateBean;
    }

    //CREATE TABLE GTCHECKLISTTEMPLATEITEM( CHECKLISTTEMPLATEITEMID VARCHAR(45) NOT NULL, FK_CHECKLISTTEMPLATEID VARCHAR(45) NOT NULL,
    // NAME VARCHAR(500) NOT NULL, CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    public ChecklistTemplateItemBean getChecklistTemplateItemBean(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())
                && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId())){
            String sQuery = "SELECT * FROM GTCHECKLISTTEMPLATEITEM WHERE CHECKLISTTEMPLATEITEMID = ? AND FK_CHECKLISTTEMPLATEID=?";
            ArrayList<Object> aParams = DBDAO.createConstraint( checklistTemplateRequestBean.getChecklistTemplateItemId(), checklistTemplateRequestBean.getChecklistTemplateId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessChecklistTemplateData.java", "getChecklistTemplateItemBean()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    checklistTemplateItemBean = new ChecklistTemplateItemBean( hmResult );
                }
            }
        }
        return checklistTemplateItemBean;
    }

    public HashMap<Long, ChecklistTemplateItemBean> getAllChecklistTemplateItemBean(ChecklistTemplateRequestBean checklistTemplateRequestBean) {
        HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean = new HashMap<Long, ChecklistTemplateItemBean> ();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId()) ){
            String sQuery = "SELECT * FROM GTCHECKLISTTEMPLATEITEM WHERE FK_CHECKLISTTEMPLATEID=? ORDER BY SORT_ORDER ASC";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateRequestBean.getChecklistTemplateId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessChecklistTemplateData.java", "getAllChecklistTemplateItemBean()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean( hmResult );
                    hmChecklistTemplateItemBean.put( checklistTemplateItemBean.getSortOrder(), checklistTemplateItemBean );
                }
            }
        }
        return hmChecklistTemplateItemBean;
    }

    // GTCHECKLISTTEMPLATETODO( CHECKLISTTEMPLATETODOID VARCHAR(45) NOT NULL, FK_CHECKLISTTEMPLATEITEMID VARCHAR(45) NOT NULL,
    // FK_CHECKLISTTEMPLATEID VARCHAR(45) NOT NULL, NAME VARCHAR(500) NOT NULL, CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    // KEY (CHECKLISTTEMPLATETODOID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    public ArrayList<ChecklistTemplateTodoBean> getAllChecklistTemplateItemTodo(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = new ArrayList<ChecklistTemplateTodoBean>();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId()) ){
            String sQuery = "SELECT * FROM GTCHECKLISTTEMPLATETODO WHERE FK_CHECKLISTTEMPLATEITEMID=? ORDER BY CREATEDATE ASC";
            ArrayList<Object> aParams = DBDAO.createConstraint(checklistTemplateRequestBean.getChecklistTemplateItemId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessChecklistTemplateData.java", "getAllChecklistTemplateItemTodo()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ChecklistTemplateTodoBean checklistTemplateTodoBean = new ChecklistTemplateTodoBean( hmResult );
                    arrChecklistTemplateTodoBean.add( checklistTemplateTodoBean );
                }
            }
        }
        return arrChecklistTemplateTodoBean;
    }

    public HashMap<String, ArrayList<ChecklistTemplateTodoBean> > getAllChecklistTemplateTodoBean(HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean) {
        HashMap<String, ArrayList<ChecklistTemplateTodoBean>> hmChecklistTemplateTodoBean = new HashMap<String, ArrayList<ChecklistTemplateTodoBean>>();
        if( hmChecklistTemplateItemBean!=null && !hmChecklistTemplateItemBean.isEmpty() ) {
            String sQuery = "SELECT * FROM GTCHECKLISTTEMPLATETODO WHERE FK_CHECKLISTTEMPLATEITEMID IN (" + DBDAO.createParamQuestionMarks(hmChecklistTemplateItemBean.size()) + ") ORDER BY CREATEDATE ASC";
            ArrayList<Object> aParams = new ArrayList<Object>();
            for(Map.Entry<Long, ChecklistTemplateItemBean> mapChecklistTemplateItemBean : hmChecklistTemplateItemBean.entrySet() ) {
                ChecklistTemplateItemBean checklistTemplateItemBean = mapChecklistTemplateItemBean.getValue();
                aParams.add( checklistTemplateItemBean.getChecklistTemplateItemId() );
            }
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessChecklistTemplateData.java", "getAllChecklistTemplateItemBean()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ChecklistTemplateTodoBean checklistTemplateTodoBean = new ChecklistTemplateTodoBean( hmResult );
                    ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = hmChecklistTemplateTodoBean.get( checklistTemplateTodoBean.getChecklistTemplateItemId() );
                    if(arrChecklistTemplateTodoBean == null ){
                        arrChecklistTemplateTodoBean = new ArrayList<ChecklistTemplateTodoBean>();
                    }
                    arrChecklistTemplateTodoBean.add(checklistTemplateTodoBean);

                    hmChecklistTemplateTodoBean.put( checklistTemplateTodoBean.getChecklistTemplateItemId(), arrChecklistTemplateTodoBean );
                }
            }

        }
        return  hmChecklistTemplateTodoBean;
    }
}
