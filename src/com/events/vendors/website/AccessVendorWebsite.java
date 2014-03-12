package com.events.vendors.website;

import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.bean.vendors.website.VendorWebsiteResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.website.AccessVendorWebsiteData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessVendorWebsite extends VendorWebsite{

    public VendorResponseBean getVendorBySubDomain(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorResponseBean vendorResponseBean = new VendorResponseBean();
        if(vendorWebsiteRequestBean != null && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getSubDomain())
                && !"www".equalsIgnoreCase(vendorWebsiteRequestBean.getSubDomain())) {
            AccessVendorWebsiteData accessVendorWebsiteData = new AccessVendorWebsiteData();
            vendorResponseBean = accessVendorWebsiteData.getVendorBySubDomain(vendorWebsiteRequestBean);
        }
        return vendorResponseBean;
    }
    public VendorWebsiteResponseBean getVendorWebsiteByVendorId(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        if(vendorWebsiteRequestBean!=null && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorId())) {
            VendorWebsiteBean vendorWebsiteBean = generateVendorWebsiteBean( vendorWebsiteRequestBean );

            AccessVendorWebsiteData accessVendorWebsiteData = new AccessVendorWebsiteData();
            vendorWebsiteBean = accessVendorWebsiteData.getVendorWebsiteByVendorId(vendorWebsiteBean);

            ArrayList<VendorWebsiteFeatureBean> arrMultipleFeatureBean = getFeatures(vendorWebsiteBean);

            vendorWebsiteResponseBean.setVendorWebsiteBean( vendorWebsiteBean);
            vendorWebsiteResponseBean.setArrVendorWebsiteFeatureBean( arrMultipleFeatureBean );
        }
        return vendorWebsiteResponseBean;
    }

    public VendorWebsiteResponseBean getVendorWebsiteByWebsiteId(VendorWebsiteRequestBean vendorLandingPageRequestBean) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        if(vendorLandingPageRequestBean!=null && !Utility.isNullOrEmpty(vendorLandingPageRequestBean.getVendorWebsiteId())) {
            VendorWebsiteBean vendorWebsiteBean = generateVendorWebsiteBean( vendorLandingPageRequestBean );

            AccessVendorWebsiteData accessVendorWebsiteData = new AccessVendorWebsiteData();
            vendorWebsiteBean= accessVendorWebsiteData.getVendorWebsiteByWebsiteId(vendorWebsiteBean);

            vendorWebsiteResponseBean.setVendorWebsiteBean( vendorWebsiteBean);
        }
        return vendorWebsiteResponseBean;
    }

    public ArrayList<VendorWebsiteFeatureBean> getFeatures( VendorWebsiteBean vendorWebsiteBean) {
        ArrayList<VendorWebsiteFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorWebsiteFeatureBean>();
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())) {


            ArrayList<VendorWebsiteFeatureBean> arrVendorWebsiteFeatureBean = new ArrayList<VendorWebsiteFeatureBean>();
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_bkg_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_border_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_facebook_feed_script) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_filled_button_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_filled_button_text_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_highlighted_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_landingpagephoto) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_logo) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_navbar_breadcrumb_tab_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_pinterest_feed_script) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_plain_button_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_plain_button_text_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_text_color) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_themename) );
            arrVendorWebsiteFeatureBean.add( generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.subdomain_name) );

            String sVendorWebsiteId = ParseUtil.checkNull(vendorWebsiteBean.getVendorWebsiteId());
            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();
            arrMultipleFeatureBean = vendorWebsiteFeature.getMultipleFeatures(arrVendorWebsiteFeatureBean, sVendorWebsiteId );

        }
        return arrMultipleFeatureBean;
    }

    public HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> getPublishedFeaturesForWebPages( VendorWebsiteBean vendorWebsiteBean) {
        HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmVendorWebsiteFeatureBean =   new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE, VendorWebsiteFeatureBean>();
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())) {


            HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmDefaultVendorWebsiteFeatureBean = new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean>();
            hmDefaultVendorWebsiteFeatureBean.put( Constants.VENDOR_WEBSITE_FEATURETYPE.published_bkg_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_bkg_color , "#ffffff") );
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_border_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_border_color, "#dbf1ff"));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_filled_button_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_filled_button_color, "#3F9CFF"));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_filled_button_text_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_filled_button_text_color, "#ffffff"));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_highlighted_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_highlighted_color, "#3F9CFF"));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_logo, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_logo));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_navbar_breadcrumb_tab_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_navbar_breadcrumb_tab_color, "#FCFCFC"));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_plain_button_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_plain_button_color, "#ffffff"));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_plain_button_text_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_plain_button_text_color, "#333333"));
            hmDefaultVendorWebsiteFeatureBean.put(Constants.VENDOR_WEBSITE_FEATURETYPE.published_text_color, generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.published_text_color, "#666666"));

            String sVendorWebsiteId = ParseUtil.checkNull(vendorWebsiteBean.getVendorWebsiteId());
            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();
            hmVendorWebsiteFeatureBean = vendorWebsiteFeature.getMultipleFeaturesWithDefaultValue(hmDefaultVendorWebsiteFeatureBean, sVendorWebsiteId);

        }
        return hmVendorWebsiteFeatureBean;
    }
}
