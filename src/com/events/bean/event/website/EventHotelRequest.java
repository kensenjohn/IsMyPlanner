package com.events.bean.event.website;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventHotelRequest {
    private String eventHotelId = Constants.EMPTY;
    private String eventWebsiteId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String phone = Constants.EMPTY;
    private String address = Constants.EMPTY;
    private String url = Constants.EMPTY;
    private String instructions = Constants.EMPTY;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getEventHotelId() {
        return eventHotelId;
    }

    public void setEventHotelId(String eventHotelId) {
        this.eventHotelId = eventHotelId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventHotelRequest{");
        sb.append("eventHotelId='").append(eventHotelId).append('\'');
        sb.append(", eventWebsiteId='").append(eventWebsiteId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
