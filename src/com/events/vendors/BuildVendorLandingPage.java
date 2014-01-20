package com.events.vendors;

import com.events.bean.vendors.VendorLandingPageBean;
import com.events.bean.vendors.VendorLandingPageFeatureBean;
import com.events.bean.vendors.VendorLandingPageRequestBean;
import com.events.bean.vendors.VendorLandingPageResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.BuildVendorLandingPageData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendorLandingPage extends VendorLandingPage {
    public VendorLandingPageResponseBean saveLandingPage(VendorLandingPageRequestBean vendorLandingPageRequestBean){
        VendorLandingPageResponseBean vendorLandingPageResponseBean = new VendorLandingPageResponseBean();
        if( vendorLandingPageRequestBean!=null ) {

            if( Utility.isNullOrEmpty(vendorLandingPageRequestBean.getVendorLandingPageId())) {
                vendorLandingPageRequestBean.setVendorLandingPageId( Utility.getNewGuid());
                vendorLandingPageResponseBean = createLandingPage(vendorLandingPageRequestBean);
            } else {
                vendorLandingPageResponseBean = updateLandingPage(vendorLandingPageRequestBean);
            }

            if(vendorLandingPageResponseBean!=null && !Utility.isNullOrEmpty(vendorLandingPageResponseBean.getVendorLandingPageBean().getVendorLandingPageId())) {
                String sVendorLandingPageId = vendorLandingPageResponseBean.getVendorLandingPageBean().getVendorLandingPageId();
                VendorLandingPageFeature vendorLandingPageFeature = new VendorLandingPageFeature();

                if(!Utility.isNullOrEmpty(vendorLandingPageRequestBean.getLogoImage())) {
                    VendorLandingPageFeatureBean logoVLPageFeatureBean = new VendorLandingPageFeatureBean();
                    logoVLPageFeatureBean.setVendorLandingPageId( sVendorLandingPageId );
                    logoVLPageFeatureBean.setFeatureType(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.logo);
                    logoVLPageFeatureBean.setValue( vendorLandingPageRequestBean.getLogoImage() );
                    vendorLandingPageFeature.setFeatureValue( logoVLPageFeatureBean );
                }

                if(!Utility.isNullOrEmpty(vendorLandingPageRequestBean.getLandingPageImage())) {
                    VendorLandingPageFeatureBean pictureVLPageFeatureBean = new VendorLandingPageFeatureBean();
                    pictureVLPageFeatureBean.setVendorLandingPageId( sVendorLandingPageId );
                    pictureVLPageFeatureBean.setFeatureType(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.landingpagephoto);
                    pictureVLPageFeatureBean.setValue( vendorLandingPageRequestBean.getLandingPageImage() );
                    vendorLandingPageFeature.setFeatureValue( pictureVLPageFeatureBean );
                }

                if(!Utility.isNullOrEmpty(vendorLandingPageRequestBean.getFacebookUrl())) {
                    VendorLandingPageFeatureBean facebookUrlVLPageFeatureBean = new VendorLandingPageFeatureBean();
                    facebookUrlVLPageFeatureBean.setVendorLandingPageId( sVendorLandingPageId );
                    facebookUrlVLPageFeatureBean.setFeatureType(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.facebook_url);
                    facebookUrlVLPageFeatureBean.setValue( vendorLandingPageRequestBean.getFacebookUrl() );
                    vendorLandingPageFeature.setFeatureValue( facebookUrlVLPageFeatureBean );
                }

                if(!Utility.isNullOrEmpty(vendorLandingPageRequestBean.getPinterestUrl())) {
                    VendorLandingPageFeatureBean pinterestUrlVLPageFeatureBean = new VendorLandingPageFeatureBean();
                    pinterestUrlVLPageFeatureBean.setVendorLandingPageId( sVendorLandingPageId );
                    pinterestUrlVLPageFeatureBean.setFeatureType(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.pinterest_url);
                    pinterestUrlVLPageFeatureBean.setValue( vendorLandingPageRequestBean.getPinterestUrl() );
                    vendorLandingPageFeature.setFeatureValue( pinterestUrlVLPageFeatureBean );
                }
            }

        }
        return vendorLandingPageResponseBean;
    }

    public VendorLandingPageResponseBean createLandingPage(VendorLandingPageRequestBean vendorLandingPageRequestBean){
        VendorLandingPageResponseBean vendorLandingPageResponseBean = new VendorLandingPageResponseBean();
        if(vendorLandingPageRequestBean!=null && !Utility.isNullOrEmpty(vendorLandingPageRequestBean.getVendorLandingPageId())) {
            VendorLandingPageBean vendorLandingPageBean = generateVendorLandingPageBean(vendorLandingPageRequestBean);

            BuildVendorLandingPageData buildVendorLandingPageData = new BuildVendorLandingPageData();
            Integer iNumOfRows = buildVendorLandingPageData.insertVendorLandingPage(vendorLandingPageBean);
            if(iNumOfRows>0){
                vendorLandingPageResponseBean.setVendorLandingPageBean(vendorLandingPageBean);
            }
        }
        return vendorLandingPageResponseBean;
    }

    public VendorLandingPageResponseBean updateLandingPage(VendorLandingPageRequestBean vendorLandingPageRequestBean){
        VendorLandingPageResponseBean vendorLandingPageResponseBean = new VendorLandingPageResponseBean();
        if(vendorLandingPageRequestBean!=null && !Utility.isNullOrEmpty(vendorLandingPageRequestBean.getVendorLandingPageId())
                && !Utility.isNullOrEmpty(vendorLandingPageRequestBean.getVendorId()) ) {
            VendorLandingPageBean vendorLandingPageBean = generateVendorLandingPageBean(vendorLandingPageRequestBean);

            BuildVendorLandingPageData buildVendorLandingPageData = new BuildVendorLandingPageData();
            Integer iNumOfRows = buildVendorLandingPageData.updateVendorLandingPage(vendorLandingPageBean);
            if(iNumOfRows>0){
                vendorLandingPageResponseBean.setVendorLandingPageBean(vendorLandingPageBean);
            }
        }
        return vendorLandingPageResponseBean;
    }
}
