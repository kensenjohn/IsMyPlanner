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
    private String checklistTemplateId = Constants.EMPTY;
    private boolean loadChecklistTemplates = false;
    private String checklistName = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getChecklistTemplateId() {
        return checklistTemplateId;
    }

    public void setChecklistTemplateId(String checklistTemplateId) {
        this.checklistTemplateId = checklistTemplateId;
    }

    public String getChecklistName() {
        return checklistName;
    }

    public void setChecklistName(String checklistName) {
        this.checklistName = checklistName;
    }

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
