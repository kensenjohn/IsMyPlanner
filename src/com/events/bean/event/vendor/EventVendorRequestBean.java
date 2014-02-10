package com.events.bean.event.vendor;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 4:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventVendorRequestBean {

    private String vendorId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
