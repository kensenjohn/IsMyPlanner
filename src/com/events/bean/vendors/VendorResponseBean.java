package com.events.bean.vendors;

import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorResponseBean {
    private String subDomain = Constants.EMPTY;
    private String vendorWebsiteId = Constants.EMPTY;
    private VendorWebsiteBean vendorWebsiteBean = new VendorWebsiteBean();

    public VendorWebsiteBean getVendorWebsiteBean() {
        return vendorWebsiteBean;
    }

    public void setVendorWebsiteBean(VendorWebsiteBean vendorWebsiteBean) {
        this.vendorWebsiteBean = vendorWebsiteBean;
    }

    public String getVendorWebsiteId() {
        return vendorWebsiteId;
    }

    public void setVendorWebsiteId(String vendorWebsiteId) {
        this.vendorWebsiteId = vendorWebsiteId;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    private VendorBean vendorBean = new VendorBean();

    public VendorBean getVendorBean() {
        return vendorBean;
    }

    public void setVendorBean(VendorBean vendorBean) {
        this.vendorBean = vendorBean;
    }
}
