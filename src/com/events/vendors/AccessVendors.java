package com.events.vendors;

import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.common.Constants;
import com.events.data.vendors.AccessVendorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 8:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessVendors {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public VendorBean getVendorByUserId(VendorRequestBean vendorRequestBean) {
        VendorBean vendorBean = new VendorBean();
        if(vendorRequestBean!=null && !"".equalsIgnoreCase(vendorRequestBean.getUserId()) ) {
            AccessVendorData accessVendorData = new AccessVendorData();
            vendorBean = accessVendorData.getVendorFromUserId(vendorRequestBean);
        }
        return vendorBean;
    }

    public VendorBean getVendor(VendorRequestBean vendorRequestBean) {
        VendorBean vendorBean = new VendorBean();
        if(vendorRequestBean!=null && !"".equalsIgnoreCase(vendorRequestBean.getVendorId()) ) {
            AccessVendorData accessVendorData = new AccessVendorData();
            vendorBean = accessVendorData.getVendor(vendorRequestBean);
        }
        return vendorBean;
    }
}
