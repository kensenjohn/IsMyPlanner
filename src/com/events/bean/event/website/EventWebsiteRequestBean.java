package com.events.bean.event.website;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventWebsiteRequestBean {
    private String eventWebsiteId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String websiteThemId = Constants.EMPTY;
    private String websiteFontId = Constants.EMPTY;
    private String websiteColorId = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    private String urlUniqueName = Constants.EMPTY;

    public String getUrlUniqueName() {
        return urlUniqueName;
    }

    public void setUrlUniqueName(String urlUniqueName) {
        this.urlUniqueName = urlUniqueName;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getWebsiteThemId() {
        return websiteThemId;
    }

    public void setWebsiteThemId(String websiteThemId) {
        this.websiteThemId = websiteThemId;
    }

    public String getWebsiteFontId() {
        return websiteFontId;
    }

    public void setWebsiteFontId(String websiteFontId) {
        this.websiteFontId = websiteFontId;
    }

    public String getWebsiteColorId() {
        return websiteColorId;
    }

    public void setWebsiteColorId(String websiteColorId) {
        this.websiteColorId = websiteColorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
