package com.events.vendors.website;

import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/27/14
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsite {

    protected VendorWebsiteFeatureBean generateVendorWebsiteFeatureBean(String sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE vendorWebsiteFeaturetype, String sValue, String sModifiedByUserId ){
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean = new VendorWebsiteFeatureBean();
        if(!Utility.isNullOrEmpty(sVendorWebsiteId) && vendorWebsiteFeaturetype!=null){
            vendorWebsiteFeatureBean.setVendorWebsiteId(sVendorWebsiteId);
            vendorWebsiteFeatureBean.setFeatureType(vendorWebsiteFeaturetype);
            vendorWebsiteFeatureBean.setValue( sValue );
            vendorWebsiteFeatureBean.setModifiedByUserId( ParseUtil.checkNull(sModifiedByUserId) );
        }
        return vendorWebsiteFeatureBean;
    }

    public VendorWebsiteFeatureBean generateVendorWebsiteFeatureBean (Constants.VENDOR_WEBSITE_FEATURETYPE vendorWebsiteFeaturetype, String defaultValue) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean = new VendorWebsiteFeatureBean();
        vendorWebsiteFeatureBean.setFeatureType( vendorWebsiteFeaturetype );
        vendorWebsiteFeatureBean.setValue( ParseUtil.checkNull(defaultValue));
        return vendorWebsiteFeatureBean;
    }

    public VendorWebsiteFeatureBean generateVendorWebsiteFeatureBean (Constants.VENDOR_WEBSITE_FEATURETYPE vendorWebsiteFeaturetype) {
        return generateVendorWebsiteFeatureBean(vendorWebsiteFeaturetype , Constants.EMPTY) ;
    }

    protected VendorWebsiteBean generateVendorWebsiteBean(VendorWebsiteRequestBean vendorWebsiteRequestBean ) {
        VendorWebsiteBean vendorWebsiteBean = new VendorWebsiteBean();
        if(vendorWebsiteRequestBean!=null){
            vendorWebsiteBean.setVendorWebsiteId( ParseUtil.checkNull(vendorWebsiteRequestBean.getVendorWebsiteId()) );
            vendorWebsiteBean.setVendorId( ParseUtil.checkNull(vendorWebsiteRequestBean.getVendorId()) );
            vendorWebsiteBean.setUserId( ParseUtil.checkNull(vendorWebsiteRequestBean.getModifiedByUserId()) );
        }
        return vendorWebsiteBean;
    }
}
