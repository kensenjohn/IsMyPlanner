package com.events.vendors.website;

import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.bean.vendors.website.VendorWebsiteResponseBean;
import com.events.common.Utility;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendorWebsite {
    public VendorWebsiteResponseBean saveWebsite(VendorWebsiteRequestBean vendorWebsiteRequestBean){
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        if(vendorWebsiteRequestBean!=null){
            if(Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorWebsiteId() ))  {

                vendorWebsiteRequestBean.setVendorWebsiteId(Utility.getNewGuid());
                vendorWebsiteResponseBean = createWebsite(vendorWebsiteRequestBean);
            } else {

            }
        }

        return vendorWebsiteResponseBean;
    }

    private VendorWebsiteResponseBean createWebsite(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();

        return vendorWebsiteResponseBean;  //To change body of created methods use File | Settings | File Templates.
    }
}
