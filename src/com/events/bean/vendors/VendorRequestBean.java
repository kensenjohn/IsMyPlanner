package com.events.bean.vendors;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorRequestBean {
    private String userId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
