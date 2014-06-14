package com.events.bean.dashboard.checklist;

import com.events.common.Constants;

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
