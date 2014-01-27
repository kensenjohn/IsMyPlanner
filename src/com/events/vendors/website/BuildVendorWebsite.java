package com.events.vendors.website;

import com.events.bean.vendors.VendorLandingPageFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.bean.vendors.website.VendorWebsiteResponseBean;
import com.events.common.Constants;
import com.events.common.Utility;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendorWebsite {
    public VendorWebsiteResponseBean saveWebsiteColor(VendorWebsiteRequestBean vendorWebsiteRequestBean){
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        boolean isError = false;
        if(vendorWebsiteRequestBean!=null){
            if(Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorWebsiteId() ))  {
                vendorWebsiteRequestBean.setVendorWebsiteId(Utility.getNewGuid());
                vendorWebsiteResponseBean = createWebsite(vendorWebsiteRequestBean);
            }
        }


        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteResponseBean.getVendorWebsiteId();
            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getBackground())) {
                VendorWebsiteFeatureBean bkgColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_bkg_color, vendorWebsiteRequestBean.getBackground());
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( bkgColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!isError) {
                vendorWebsiteResponseBean.setVendorWebsiteId( sVendorWebsiteId );
            }

        } else {
            isError = true;
        }
        if(isError) {
            vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        }
        return vendorWebsiteResponseBean;
    }

    public VendorWebsiteResponseBean publishWebsiteColor(VendorWebsiteRequestBean vendorWebsiteRequestBean){
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();

        boolean isError = false;
        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteResponseBean.getVendorWebsiteId();
            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();

            VendorWebsiteFeatureBean tmpBkgColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_bkg_color, Constants.EMPTY );
            tmpBkgColorFeatureBean = vendorWebsiteFeature.getFeature( tmpBkgColorFeatureBean );
            if(!Utility.isNullOrEmpty(tmpBkgColorFeatureBean.getVendorWebsiteFeatureId())) {
                tmpBkgColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_bkg_color );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpBkgColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!isError) {
                vendorWebsiteResponseBean.setVendorWebsiteId( sVendorWebsiteId );
            }

        } else {
            isError = true;
        }
        if(isError) {
            vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        }
        return vendorWebsiteResponseBean;
    }

    private VendorWebsiteFeatureBean generateVendorWebsiteFeatureBean(String sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE vendorWebsiteFeaturetype, String sValue ){
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean = new VendorWebsiteFeatureBean();
        if(!Utility.isNullOrEmpty(sVendorWebsiteId) && vendorWebsiteFeaturetype!=null){
            vendorWebsiteFeatureBean.setVendorWebsiteId(sVendorWebsiteId);
            vendorWebsiteFeatureBean.setFeatureType(vendorWebsiteFeaturetype);
            vendorWebsiteFeatureBean.setValue( sValue );
        }
        return vendorWebsiteFeatureBean;
    }

    private VendorWebsiteResponseBean createWebsite(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();

        return vendorWebsiteResponseBean;  //To change body of created methods use File | Settings | File Templates.
    }
}
