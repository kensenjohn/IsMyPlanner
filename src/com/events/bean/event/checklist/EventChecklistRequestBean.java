package com.events.bean.event.checklist;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/19/14
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventChecklistRequestBean {
    private String checklistId = Constants.EMPTY;
    private boolean loadChecklistTemplates = false;

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public boolean isLoadChecklistTemplates() {
        return loadChecklistTemplates;
    }

    public void setLoadChecklistTemplates(boolean loadChecklistTemplates) {
        this.loadChecklistTemplates = loadChecklistTemplates;
    }
}
