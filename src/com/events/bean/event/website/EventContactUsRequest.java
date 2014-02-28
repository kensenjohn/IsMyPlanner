package com.events.bean.event.website;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventContactUsRequest {
    private String eventContactUsId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String phone = Constants.EMPTY;
    private String email = Constants.EMPTY;

    public String getEventContactUsId() {
        return eventContactUsId;
    }

    public void setEventContactUsId(String eventContactUsId) {
        this.eventContactUsId = eventContactUsId;
    }

    public String getEventWebsiteId() {
        return eventWebsiteId;
    }

    public void setEventWebsiteId(String eventWebsiteId) {
        this.eventWebsiteId = eventWebsiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
