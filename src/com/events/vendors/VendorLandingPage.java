package com.events.vendors;

import com.events.bean.vendors.VendorLandingPageBean;
import com.events.bean.vendors.VendorLandingPageFeatureBean;
import com.events.bean.vendors.VendorLandingPageRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorLandingPage {
    protected VendorLandingPageBean generateVendorLandingPageBean(VendorLandingPageRequestBean vendorLandingPageRequestBean ) {
        VendorLandingPageBean vendorLandingPageBean = new VendorLandingPageBean();
        if(vendorLandingPageRequestBean!=null){
            vendorLandingPageBean.setVendorLandingPageId(ParseUtil.checkNull(vendorLandingPageRequestBean.getVendorLandingPageId()));
            vendorLandingPageBean.setVendorId( ParseUtil.checkNull(vendorLandingPageRequestBean.getVendorId()  ));
            vendorLandingPageBean.setTheme( ParseUtil.checkNull(vendorLandingPageRequestBean.getThemeName()  ));
        }
        return vendorLandingPageBean;
    }

    public VendorLandingPageFeatureBean generateVendorLandingPageFeatureBean (Constants.VENDOR_LANDINGPAGE_FEATURETYPE vendorLandingpageFeaturetype) {
        VendorLandingPageFeatureBean vendorLandingPageFeatureBean = new VendorLandingPageFeatureBean();
        vendorLandingPageFeatureBean.setFeatureType( vendorLandingpageFeaturetype );
        return vendorLandingPageFeatureBean;
    }
}
