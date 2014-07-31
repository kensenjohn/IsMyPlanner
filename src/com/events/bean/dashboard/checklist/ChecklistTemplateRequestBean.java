package com.events.bean.dashboard.checklist;

import com.events.common.Constants;
import com.events.common.Utility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChecklistTemplateRequestBean {
    private String checklistTemplateId = Constants.EMPTY;
    private String checklistTemplateName = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private boolean isDefault = false;
    private String checklistTemplateItemId =  Constants.EMPTY;
    private String checklistTemplateItemName = Constants.EMPTY;
    private Long sortOrder = 0L;
    private HashMap<String,Long> hmSortChecklistTemplateId = new HashMap<String, Long>();
    private ArrayList<String> arrChecklistTodoId = new ArrayList<String>();
    private ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = new ArrayList<ChecklistTemplateTodoBean>();
    private String checklistTemplateTodoId =  Constants.EMPTY;
    private String clientId = Constants.EMPTY;

    public void setChecklistTemplateBean(String checklistTemplateId, String vendorId, boolean isDefault, String checklistTemplateName ){
        this.checklistTemplateId = checklistTemplateId;
        this.vendorId = vendorId ;
        this.isDefault = isDefault;
        this.checklistTemplateName = checklistTemplateName;
    }

    public void setChecklistTemplateItemBean( String checklistTemplateItemId, String checklistTemplateId, String checklistTemplateItemName, Long sortOrder  ){
        this.checklistTemplateItemId = checklistTemplateItemId;
        this.checklistTemplateId = checklistTemplateId;
        this.sortOrder = sortOrder;
        this.checklistTemplateItemName = checklistTemplateItemName;
    }
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getChecklistTemplateTodoId() {
        return checklistTemplateTodoId;
    }

    public void setChecklistTemplateTodoId(String checklistTemplateTodoId) {
        this.checklistTemplateTodoId = checklistTemplateTodoId;
    }

    public ArrayList<ChecklistTemplateTodoBean> getArrChecklistTemplateTodoBean() {
        return arrChecklistTemplateTodoBean;
    }

    public void setArrChecklistTemplateTodoBean(ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean) {
        this.arrChecklistTemplateTodoBean = arrChecklistTemplateTodoBean;
    }

    public ArrayList<String> getArrChecklistTodoId() {
        return arrChecklistTodoId;
    }

    public void setArrChecklistTodoId(ArrayList<String> arrChecklistTodoId) {
        this.arrChecklistTodoId = arrChecklistTodoId;
    }

    public HashMap<String, Long> getHmSortChecklistTemplateId() {
        return hmSortChecklistTemplateId;
    }

    public void setHmSortChecklistTemplateId(HashMap<String, Long> hmSortChecklistTemplateId) {
        this.hmSortChecklistTemplateId = hmSortChecklistTemplateId;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getChecklistTemplateItemId() {
        return checklistTemplateItemId;
    }

    public void setChecklistTemplateItemId(String checklistTemplateItemId) {
        this.checklistTemplateItemId = checklistTemplateItemId;
    }

    public String getChecklistTemplateItemName() {
        return checklistTemplateItemName;
    }

    public void setChecklistTemplateItemName(String checklistTemplateItemName) {
        this.checklistTemplateItemName = checklistTemplateItemName;
    }

    public String getChecklistTemplateId() {
        return checklistTemplateId;
    }

    public void setChecklistTemplateId(String checklistTemplateId) {
        this.checklistTemplateId = checklistTemplateId;
    }

    public String getChecklistTemplateName() {
        return checklistTemplateName;
    }

    public void setChecklistTemplateName(String checklistTemplateName) {
        this.checklistTemplateName = checklistTemplateName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
