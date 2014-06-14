package com.events.data.dashboard.checklist;

import com.events.bean.dashboard.checklist.ChecklistTemplateBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateRequestBean;
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
}
