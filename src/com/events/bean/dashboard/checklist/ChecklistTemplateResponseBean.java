package com.events.bean.dashboard.checklist;

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean = new HashMap<Long, ChecklistTemplateItemBean>();
    private ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = new ArrayList<ChecklistTemplateTodoBean>();
    private HashMap<String, ArrayList<ChecklistTemplateTodoBean>> hmChecklistTemplateTodoBean = new HashMap<String, ArrayList<ChecklistTemplateTodoBean>>();

    public HashMap<String, ArrayList<ChecklistTemplateTodoBean>> getHmChecklistTemplateTodoBean() {
        return hmChecklistTemplateTodoBean;
    }

    public void setHmChecklistTemplateTodoBean(HashMap<String, ArrayList<ChecklistTemplateTodoBean>> hmChecklistTemplateTodoBean) {
        this.hmChecklistTemplateTodoBean = hmChecklistTemplateTodoBean;
    }

    public ArrayList<ChecklistTemplateTodoBean> getArrChecklistTemplateTodoBean() {
        return arrChecklistTemplateTodoBean;
    }

    public void setArrChecklistTemplateTodoBean(ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean) {
        this.arrChecklistTemplateTodoBean = arrChecklistTemplateTodoBean;
    }

    public HashMap<Long, ChecklistTemplateItemBean> getHmChecklistTemplateItemBean() {
        return hmChecklistTemplateItemBean;
    }

    public void setHmChecklistTemplateItemBean(HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean) {
        this.hmChecklistTemplateItemBean = hmChecklistTemplateItemBean;
    }

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
