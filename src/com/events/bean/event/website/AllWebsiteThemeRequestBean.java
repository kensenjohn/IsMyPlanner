package com.events.bean.event.website;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/18/14
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class AllWebsiteThemeRequestBean {
    private String eventId = Constants.EMPTY;
    private String websiteThemeId = Constants.EMPTY;
    private ArrayList<String> arrVendorId = new ArrayList<String>();

    public ArrayList<String> getArrVendorId() {
        return arrVendorId;
    }

    public void setArrVendorId(ArrayList<String> arrVendorId) {
        this.arrVendorId = arrVendorId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getWebsiteThemeId() {
        return websiteThemeId;
    }

    public void setWebsiteThemeId(String websiteThemeId) {
        this.websiteThemeId = websiteThemeId;
    }
}
