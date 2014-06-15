package com.events.bean.dashboard.checklist;

import com.events.common.Constants;

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
