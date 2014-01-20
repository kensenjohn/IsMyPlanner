package com.events.bean.vendors;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorLandingPageResponseBean {

    private VendorLandingPageBean vendorLandingPageBean = new VendorLandingPageBean();
    private ArrayList<VendorLandingPageFeatureBean> arrVendorLandingPageFeatureBean = new ArrayList<VendorLandingPageFeatureBean>();

    public VendorLandingPageBean getVendorLandingPageBean() {
        return vendorLandingPageBean;
    }

    public void setVendorLandingPageBean(VendorLandingPageBean vendorLandingPageBean) {
        this.vendorLandingPageBean = vendorLandingPageBean;
    }

    public ArrayList<VendorLandingPageFeatureBean> getArrVendorLandingPageFeatureBean() {
        return arrVendorLandingPageFeatureBean;
    }

    public void setArrVendorLandingPageFeatureBean(ArrayList<VendorLandingPageFeatureBean> arrVendorLandingPageFeatureBean) {
        this.arrVendorLandingPageFeatureBean = arrVendorLandingPageFeatureBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorLandingPageResponseBean{");
        sb.append("vendorLandingPageBean=").append(vendorLandingPageBean);
        sb.append(", arrVendorLandingPageFeatureBean=").append(arrVendorLandingPageFeatureBean);
        sb.append('}');
        return sb.toString();
    }
}
