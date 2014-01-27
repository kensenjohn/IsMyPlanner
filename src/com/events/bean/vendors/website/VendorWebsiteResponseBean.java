package com.events.bean.vendors.website;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsiteResponseBean {
    private String vendorWebsiteId = Constants.EMPTY;

    public String getVendorWebsiteId() {
        return vendorWebsiteId;
    }

    public void setVendorWebsiteId(String vendorWebsiteId) {
        this.vendorWebsiteId = vendorWebsiteId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorWebsiteResponseBean{");
        sb.append("vendorWebsiteId='").append(vendorWebsiteId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
