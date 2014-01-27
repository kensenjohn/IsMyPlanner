package com.events.bean.vendors.website;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsiteResponseBean {
    private String vendorWebsiteId = Constants.EMPTY;
    private VendorWebsiteBean vendorWebsiteBean = new VendorWebsiteBean();
    private ArrayList<VendorWebsiteFeatureBean> arrVendorWebsiteFeatureBean = new ArrayList<VendorWebsiteFeatureBean>();
    private String message = Constants.EMPTY;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<VendorWebsiteFeatureBean> getArrVendorWebsiteFeatureBean() {
        return arrVendorWebsiteFeatureBean;
    }

    public void setArrVendorWebsiteFeatureBean(ArrayList<VendorWebsiteFeatureBean> arrVendorWebsiteFeatureBean) {
        this.arrVendorWebsiteFeatureBean = arrVendorWebsiteFeatureBean;
    }

    public String getVendorWebsiteId() {
        return vendorWebsiteId;
    }

    public void setVendorWebsiteId(String vendorWebsiteId) {
        this.vendorWebsiteId = vendorWebsiteId;
    }

    public VendorWebsiteBean getVendorWebsiteBean() {
        return vendorWebsiteBean;
    }

    public void setVendorWebsiteBean(VendorWebsiteBean vendorWebsiteBean) {
        this.vendorWebsiteBean = vendorWebsiteBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorWebsiteResponseBean{");
        sb.append("vendorWebsiteId='").append(vendorWebsiteId).append('\'');
        sb.append(", vendorWebsiteBean=").append(vendorWebsiteBean);
        sb.append('}');
        return sb.toString();
    }
}
