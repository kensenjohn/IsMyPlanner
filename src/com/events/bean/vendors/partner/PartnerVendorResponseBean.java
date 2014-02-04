package com.events.bean.vendors.partner;

import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorFeatureBean;
import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PartnerVendorResponseBean {
    private String partnerVendorId = Constants.EMPTY;

    private PartnerVendorBean partnerVendorBean = new PartnerVendorBean();
    private ArrayList<VendorFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorFeatureBean>();
    private VendorBean vendorBean = new VendorBean();

    public VendorBean getVendorBean() {
        return vendorBean;
    }

    public void setVendorBean(VendorBean vendorBean) {
        this.vendorBean = vendorBean;
    }

    public String getPartnerVendorId() {
        return partnerVendorId;
    }

    public void setPartnerVendorId(String partnerVendorId) {
        this.partnerVendorId = partnerVendorId;
    }

    public PartnerVendorBean getPartnerVendorBean() {
        return partnerVendorBean;
    }

    public void setPartnerVendorBean(PartnerVendorBean partnerVendorBean) {
        this.partnerVendorBean = partnerVendorBean;
    }

    public ArrayList<VendorFeatureBean> getArrMultipleFeatureBean() {
        return arrMultipleFeatureBean;
    }

    public void setArrMultipleFeatureBean(ArrayList<VendorFeatureBean> arrMultipleFeatureBean) {
        this.arrMultipleFeatureBean = arrMultipleFeatureBean;
    }
}
