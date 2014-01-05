package com.events.bean.vendors;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorResponseBean {
    private VendorBean vendorBean = new VendorBean();

    public VendorBean getVendorBean() {
        return vendorBean;
    }

    public void setVendorBean(VendorBean vendorBean) {
        this.vendorBean = vendorBean;
    }
}
