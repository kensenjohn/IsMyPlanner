package com.events.dashboard.checklist;

import com.events.bean.dashboard.checklist.ChecklistTemplateBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateRequestBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateResponseBean;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.dashboard.checklist.AccessChecklistTemplateData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessChecklistTemplate {

    public ChecklistTemplateResponseBean loadChecklistTemplateDetails( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();
        ChecklistTemplateBean checklistTemplateBean = getChecklistTemplateBean(checklistTemplateRequestBean);
        checklistTemplateResponseBean.setChecklistTemplateBean( checklistTemplateBean );
        return checklistTemplateResponseBean;
    }

    public ChecklistTemplateBean getChecklistTemplateBean( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            checklistTemplateBean = accessChecklistTemplateData.getChecklistTemplateBean( checklistTemplateRequestBean );
        }
        return checklistTemplateBean;
    }

    public ArrayList<ChecklistTemplateBean> getAllChecklistTemplatesByVendor( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ArrayList<ChecklistTemplateBean> arrChecklistTemplateBean = new ArrayList<ChecklistTemplateBean>();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getVendorId())) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            arrChecklistTemplateBean = accessChecklistTemplateData.getAllChecklistTemplatesByVendor(checklistTemplateRequestBean);
        }
        return arrChecklistTemplateBean;
    }

    public JSONObject getJsonAllChecklistTemplates(ArrayList<ChecklistTemplateBean> arrChecklistTemplateBean){
        JSONObject jsonAllChecklistTemplates = new JSONObject();
        Long lNumberOfChecklistTemplates = 0L;
        if(arrChecklistTemplateBean!=null && !arrChecklistTemplateBean.isEmpty()){
            for(ChecklistTemplateBean checklistTemplateBean : arrChecklistTemplateBean ){
                jsonAllChecklistTemplates.put(ParseUtil.LToS(lNumberOfChecklistTemplates) , checklistTemplateBean.toJson() ) ;
                lNumberOfChecklistTemplates++;
            }
        }
        jsonAllChecklistTemplates.put("num_of_checklist_templates",lNumberOfChecklistTemplates);
        return jsonAllChecklistTemplates;
    }


}
