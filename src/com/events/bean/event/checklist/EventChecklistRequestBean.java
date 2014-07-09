package com.events.bean.event.checklist;

import com.events.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;

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
    private String checklistItemId = Constants.EMPTY;
    private String checklistTodoId = Constants.EMPTY;
    private String checklistItemName = Constants.EMPTY;
    private boolean isComplete = false;
    private ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean = new ArrayList<EventChecklistTodoBean>();
    private Long sortOrder = 0L;
    private HashMap<String,Long> hmEventChecklistItemId = new HashMap<String, Long>();


    public HashMap<String, Long> getHmEventChecklistItemId() {
        return hmEventChecklistItemId;
    }

    public void setHmEventChecklistItemId(HashMap<String, Long> hmEventChecklistItemId) {
        this.hmEventChecklistItemId = hmEventChecklistItemId;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getChecklistItemName() {
        return checklistItemName;
    }

    public void setChecklistItemName(String checklistItemName) {
        this.checklistItemName = checklistItemName;
    }

    public ArrayList<EventChecklistTodoBean> getArrEventChecklistTodoBean() {
        return arrEventChecklistTodoBean;
    }

    public void setArrEventChecklistTodoBean(ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean) {
        this.arrEventChecklistTodoBean = arrEventChecklistTodoBean;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getChecklistTodoId() {
        return checklistTodoId;
    }

    public void setChecklistTodoId(String checklistTodoId) {
        this.checklistTodoId = checklistTodoId;
    }

    public String getChecklistItemId() {
        return checklistItemId;
    }

    public void setChecklistItemId(String checklistItemId) {
        this.checklistItemId = checklistItemId;
    }

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
