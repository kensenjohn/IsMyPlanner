package com.events.bean.event;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/29/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventRequestBean {
    private String searchText = Constants.EMPTY;
    private String startDate = Constants.EMPTY;
    private String endDate = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String eventType = Constants.EMPTY;
    private boolean isDeletedEvent = false;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isDeletedEvent() {
        return isDeletedEvent;
    }

    public void setDeletedEvent(boolean deletedEvent) {
        isDeletedEvent = deletedEvent;
    }
}
