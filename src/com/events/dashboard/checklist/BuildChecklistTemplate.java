package com.events.dashboard.checklist;

import com.events.bean.dashboard.checklist.ChecklistTemplateBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateRequestBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateResponseBean;
import com.events.common.Utility;
import com.events.data.dashboard.checklist.BuildChecklistTemplateData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildChecklistTemplate {
    public ChecklistTemplateResponseBean saveChecklistTemplate( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();
        if(checklistTemplateRequestBean!=null ){

            ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
            if(Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())) {
                checklistTemplateBean = createChecklistTemplate(checklistTemplateRequestBean);
            } else {
                checklistTemplateBean = updateChecklistTemplate(checklistTemplateRequestBean);
            }

            if(checklistTemplateBean!=null && !Utility.isNullOrEmpty(checklistTemplateBean.getChecklistTemplateId())) {
                checklistTemplateResponseBean.setChecklistTemplateBean( checklistTemplateBean );
            }
        }
        return checklistTemplateResponseBean;
    }

    public Integer deleteChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        Integer iNumOfChecklistTemplateDeleted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())){
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            iNumOfChecklistTemplateDeleted = buildChecklistTemplateData.deleteChecklistTemplate( checklistTemplateRequestBean );
        }
        return iNumOfChecklistTemplateDeleted;
    }

    private ChecklistTemplateBean createChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateRequestBean.setChecklistTemplateId( Utility.getNewGuid() );
            checklistTemplateBean = generateChecklistTemplateBean(checklistTemplateRequestBean);
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            Integer iNumOfRowsInserted = buildChecklistTemplateData.insertChecklistTemplate( checklistTemplateBean );
            if(iNumOfRowsInserted<=0){
                checklistTemplateBean = new ChecklistTemplateBean();
            }
        }
        return checklistTemplateBean;
    }
    private ChecklistTemplateBean updateChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateBean = generateChecklistTemplateBean(checklistTemplateRequestBean);
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            Integer iNumOfRowsUpdated = buildChecklistTemplateData.updateChecklistTemplate( checklistTemplateBean );
            if(iNumOfRowsUpdated<=0){
                checklistTemplateBean = new ChecklistTemplateBean();
            }
        }
        return checklistTemplateBean;
    }

    private ChecklistTemplateBean generateChecklistTemplateBean( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateBean.setChecklistTemplateId( checklistTemplateRequestBean.getChecklistTemplateId() );
            checklistTemplateBean.setName( checklistTemplateRequestBean.getChecklistTemplateName() );
            checklistTemplateBean.setVendorId(checklistTemplateRequestBean.getVendorId());
            checklistTemplateBean.setDefault( checklistTemplateRequestBean.isDefault() );
        }
        return checklistTemplateBean;
    }
}
