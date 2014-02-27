package com.events.bean.event.website;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventPartyRequest {
    private String eventPartyId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private Constants.EVENT_PARTY_TYPE eventPartyType = Constants.EVENT_PARTY_TYPE.NONE;
    private String eventPartyTypeName = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String description = Constants.EMPTY;
    private ArrayList<SocialMediaBean> arrSocialMediaBean = new ArrayList<SocialMediaBean>();

    public ArrayList<SocialMediaBean> getArrSocialMediaBean() {
        return arrSocialMediaBean;
    }

    public void setArrSocialMediaBean(ArrayList<SocialMediaBean> arrSocialMediaBean) {
        this.arrSocialMediaBean = arrSocialMediaBean;
    }

    public String getEventPartyId() {
        return eventPartyId;
    }

    public void setEventPartyId(String eventPartyId) {
        this.eventPartyId = eventPartyId;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public Constants.EVENT_PARTY_TYPE getEventPartyType() {
        return eventPartyType;
    }

    public void setEventPartyType(Constants.EVENT_PARTY_TYPE eventPartyType) {
        this.eventPartyType = eventPartyType;
    }

    public String getEventPartyTypeName() {
        return eventPartyTypeName;
    }

    public void setEventPartyTypeName(String eventPartyTypeName) {
        this.eventPartyTypeName = eventPartyTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
