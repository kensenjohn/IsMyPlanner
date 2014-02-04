package com.events.vendors.partner;

import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorFeature;
import com.events.bean.vendors.VendorFeatureBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.partner.PartnerVendorBean;
import com.events.bean.vendors.partner.PartnerVendorRequestBean;
import com.events.bean.vendors.partner.PartnerVendorResponseBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.vendors.AccessVendorData;
import com.events.data.vendors.partner.AccessPartnerVendorData;
import com.events.vendors.AccessVendors;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessPartnerVendor {
    public void getAllPartnerVendorsForVendor(PartnerVendorRequestBean partnerVendorRequestBean) {
        if(partnerVendorRequestBean!=null && !Utility.isNullOrEmpty(partnerVendorRequestBean.getPartnerVendorId() )) {
            AccessPartnerVendorData accessPartnerVendorData = new AccessPartnerVendorData();
            ArrayList<PartnerVendorBean> arrPartnerVendorBean =  accessPartnerVendorData.getAllPartnerVendorBean(partnerVendorRequestBean);
            if(arrPartnerVendorBean!=null && !arrPartnerVendorBean.isEmpty() ) {

            }
        }
    }
    public PartnerVendorResponseBean getPartnerVendorDetails(PartnerVendorRequestBean partnerVendorRequestBean) {
        PartnerVendorResponseBean partnerVendorResponseBean = new PartnerVendorResponseBean() ;
        if(partnerVendorRequestBean!=null && !Utility.isNullOrEmpty(partnerVendorRequestBean.getPartnerVendorId() )) {
            AccessPartnerVendorData accessPartnerVendorData = new AccessPartnerVendorData();
            PartnerVendorBean partnerVendorBean = accessPartnerVendorData.getPartnerVendorBean(partnerVendorRequestBean);
            if(partnerVendorBean!=null && !Utility.isNullOrEmpty(partnerVendorBean.getPartnerVendorId())) {

                VendorRequestBean vendorRequestBean = new VendorRequestBean();
                vendorRequestBean.setVendorId( partnerVendorBean.getPartnerVendorId() );

                AccessVendors accessVendor = new AccessVendors();
                VendorBean vendorBean = accessVendor.getVendor(vendorRequestBean);

                ArrayList<VendorFeatureBean> arrMultipleFeatureBean = getPartnerVendorFeatures( partnerVendorRequestBean );

                partnerVendorResponseBean.setVendorBean( vendorBean );
                partnerVendorResponseBean.setPartnerVendorId(partnerVendorBean.getPartnerVendorId());
                partnerVendorResponseBean.setPartnerVendorBean( partnerVendorBean );
                partnerVendorResponseBean.setArrMultipleFeatureBean( arrMultipleFeatureBean );
            }
        }
        return partnerVendorResponseBean;
    }

    public ArrayList<VendorFeatureBean> getPartnerVendorFeatures(PartnerVendorRequestBean partnerVendorRequestBean){
        ArrayList<VendorFeatureBean> arrMultipleFeatureBean = new ArrayList<VendorFeatureBean>();
        if(partnerVendorRequestBean!=null && !Utility.isNullOrEmpty(partnerVendorRequestBean.getPartnerVendorId() )) {
            ArrayList<VendorFeatureBean> arrTmpMultipleFeatureBean = new ArrayList<VendorFeatureBean>();

            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.website) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.price) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_first_name) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_last_name) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_email));
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_cell_phone) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_work_phone) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_address_1) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_address_2) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_city) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_state) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_zipcode) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_country) );
            arrTmpMultipleFeatureBean.add(VendorFeature.generateVendorFeatureBean(Constants.VENDOR_FEATURETYPE.partner_vendor_type) );

            VendorFeature vendorFeature = new VendorFeature();
            arrMultipleFeatureBean =  vendorFeature.getMultipleFeatures(arrTmpMultipleFeatureBean ,  partnerVendorRequestBean.getPartnerVendorId()  );
        }
        return arrMultipleFeatureBean;
    }


}
