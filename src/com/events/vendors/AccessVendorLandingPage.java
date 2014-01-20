package com.events.vendors;

import com.events.bean.vendors.VendorLandingPageBean;
import com.events.bean.vendors.VendorLandingPageFeatureBean;
import com.events.bean.vendors.VendorLandingPageRequestBean;
import com.events.bean.vendors.VendorLandingPageResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.AccessVendorLandingPageData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessVendorLandingPage extends VendorLandingPage  {
    public VendorLandingPageResponseBean getVendorLandingPageByVendorId(VendorLandingPageRequestBean vendorLandingPageRequestBean) {
        VendorLandingPageResponseBean vendorLandingPageResponseBean = new VendorLandingPageResponseBean();
        if(vendorLandingPageRequestBean!=null && !Utility.isNullOrEmpty(vendorLandingPageRequestBean.getVendorId())) {
            VendorLandingPageBean vendorLandingPageBean = generateVendorLandingPageBean( vendorLandingPageRequestBean );

            AccessVendorLandingPageData accessVendorLandingPageData = new AccessVendorLandingPageData();
            vendorLandingPageBean = accessVendorLandingPageData.getVendorLandingPageByVendorId(vendorLandingPageBean);

            ArrayList<VendorLandingPageFeatureBean> arrMultipleFeatureBean = getFeatures(vendorLandingPageBean);

            vendorLandingPageResponseBean.setVendorLandingPageBean( vendorLandingPageBean);
            vendorLandingPageResponseBean.setArrVendorLandingPageFeatureBean( arrMultipleFeatureBean );
        }
        return vendorLandingPageResponseBean;
    }

    public VendorLandingPageResponseBean getVendorLandingPageByLandingPageId(VendorLandingPageRequestBean vendorLandingPageRequestBean) {
        VendorLandingPageResponseBean vendorLandingPageResponseBean = new VendorLandingPageResponseBean();
        if(vendorLandingPageRequestBean!=null && !Utility.isNullOrEmpty(vendorLandingPageRequestBean.getVendorLandingPageId())) {
            VendorLandingPageBean vendorLandingPageBean = generateVendorLandingPageBean( vendorLandingPageRequestBean );

            AccessVendorLandingPageData accessVendorLandingPageData = new AccessVendorLandingPageData();
            vendorLandingPageBean= accessVendorLandingPageData.getVendorLandingPageByLandingPageId(vendorLandingPageBean);

            vendorLandingPageResponseBean.setVendorLandingPageBean( vendorLandingPageBean);
        }
        return vendorLandingPageResponseBean;
    }

    public ArrayList<VendorLandingPageFeatureBean> getFeatures( VendorLandingPageBean vendorLandingPageBean) {
        ArrayList<VendorLandingPageFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorLandingPageFeatureBean>();
        if(vendorLandingPageBean!=null && !Utility.isNullOrEmpty(vendorLandingPageBean.getVendorLandingPageId())) {


            ArrayList<VendorLandingPageFeatureBean> arrVendorLandingPageFeatureBean = new ArrayList<VendorLandingPageFeatureBean>();
            arrVendorLandingPageFeatureBean.add( generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.logo) );
            arrVendorLandingPageFeatureBean.add( generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.landingpagephoto) );
            arrVendorLandingPageFeatureBean.add( generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.facebook_url) );
            arrVendorLandingPageFeatureBean.add( generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.pinterest_url) );

            String sVendorLandingPageId = ParseUtil.checkNull(vendorLandingPageBean.getVendorLandingPageId());
            VendorLandingPageFeature vendorLandingPageFeature = new VendorLandingPageFeature();
            arrMultipleFeatureBean = vendorLandingPageFeature.getMultipleFeatures(arrVendorLandingPageFeatureBean, sVendorLandingPageId );

        }
        return arrMultipleFeatureBean;
    }
}
