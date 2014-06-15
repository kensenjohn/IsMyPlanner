package com.events.bean.dashboard.checklist;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChecklistTemplateResponseBean {
    private ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
    private ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
    private ArrayList<ChecklistTemplateItemBean> arrChecklistTemplateItemBean = new ArrayList<ChecklistTemplateItemBean>();

    public ArrayList<ChecklistTemplateItemBean> getArrChecklistTemplateItemBean() {
        return arrChecklistTemplateItemBean;
    }

    public void setArrChecklistTemplateItemBean(ArrayList<ChecklistTemplateItemBean> arrChecklistTemplateItemBean) {
        this.arrChecklistTemplateItemBean = arrChecklistTemplateItemBean;
    }

    public ChecklistTemplateBean getChecklistTemplateBean() {
        return checklistTemplateBean;
    }

    public void setChecklistTemplateBean(ChecklistTemplateBean checklistTemplateBean) {
        this.checklistTemplateBean = checklistTemplateBean;
    }


    public ChecklistTemplateItemBean getChecklistTemplateItemBean() {
        return checklistTemplateItemBean;
    }

    public void setChecklistTemplateItemBean(ChecklistTemplateItemBean checklistTemplateItemBean) {
        this.checklistTemplateItemBean = checklistTemplateItemBean;
    }
}
