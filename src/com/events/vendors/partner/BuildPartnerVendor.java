package com.events.vendors.partner;

import com.events.bean.vendors.VendorBean;
import com.events.vendors.VendorFeature;
import com.events.bean.vendors.VendorFeatureBean;
import com.events.bean.vendors.partner.PartnerVendorBean;
import com.events.bean.vendors.partner.PartnerVendorRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.vendors.EditVendorException;
import com.events.data.vendors.partner.BuildPartnerVendorData;
import com.events.vendors.BuildVendors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildPartnerVendor {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public PartnerVendorBean savePartnerVendor(PartnerVendorRequestBean partnerVendorRequest) {
        PartnerVendorBean  partnerVendorBean = new PartnerVendorBean();
        if(partnerVendorRequest!=null && !Utility.isNullOrEmpty(partnerVendorRequest.getVendorId()) ) {
            appLogging.info("Partner ID : " + (partnerVendorRequest.getPartnerId()) );
            if(Utility.isNullOrEmpty(partnerVendorRequest.getPartnerId())) {
                partnerVendorBean = createPartnerVendor(partnerVendorRequest);
            } else {
                partnerVendorBean = updatePartnerVendor(partnerVendorRequest);
            }

            if(partnerVendorBean!=null && !Utility.isNullOrEmpty(partnerVendorBean.getPartnerVendorId() )) {
                VendorFeatureBean websiteVendorFeatureBean = new VendorFeatureBean();
                websiteVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                websiteVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.website );
                websiteVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getWebsite()));
                websiteVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );

                VendorFeature vendorFeature = new VendorFeature();
                Integer iNumOfWebsiteRow = vendorFeature.setFeatureValue(websiteVendorFeatureBean);

                VendorFeatureBean firstNameVendorFeatureBean = new VendorFeatureBean();
                firstNameVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                firstNameVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_first_name );
                firstNameVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getFirstName()));
                firstNameVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfFirstNameRow = vendorFeature.setFeatureValue(firstNameVendorFeatureBean);

                VendorFeatureBean lastNameVendorFeatureBean = new VendorFeatureBean();
                lastNameVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                lastNameVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_last_name );
                lastNameVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getLastName()));
                lastNameVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfLastNameRow = vendorFeature.setFeatureValue(lastNameVendorFeatureBean);

                VendorFeatureBean emailVendorFeatureBean = new VendorFeatureBean();
                emailVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                emailVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_email );
                emailVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getEmail()));
                emailVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfEmailRow = vendorFeature.setFeatureValue(emailVendorFeatureBean);

                VendorFeatureBean workPhoneVendorFeatureBean = new VendorFeatureBean();
                workPhoneVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                workPhoneVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_work_phone );
                workPhoneVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getPhoneNum()));
                workPhoneVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfWorkPhoneRow = vendorFeature.setFeatureValue(workPhoneVendorFeatureBean);

                VendorFeatureBean cellPhoneVendorFeatureBean = new VendorFeatureBean();
                cellPhoneVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                cellPhoneVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_cell_phone);
                cellPhoneVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getCellPhone()));
                cellPhoneVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfCellPhoneRow = vendorFeature.setFeatureValue(cellPhoneVendorFeatureBean);

                VendorFeatureBean address1VendorFeatureBean = new VendorFeatureBean();
                address1VendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                address1VendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_address_1);
                address1VendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getAddress1()));
                address1VendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfAddress1Row = vendorFeature.setFeatureValue(address1VendorFeatureBean);

                VendorFeatureBean address2VendorFeatureBean = new VendorFeatureBean();
                address2VendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                address2VendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_address_2);
                address2VendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getAddress2()));

                address2VendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfAddress2Row = vendorFeature.setFeatureValue(address2VendorFeatureBean);

                VendorFeatureBean cityVendorFeatureBean = new VendorFeatureBean();
                cityVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                cityVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_city);
                cityVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getCity()));
                cityVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfCityRow = vendorFeature.setFeatureValue(cityVendorFeatureBean);

                VendorFeatureBean stateVendorFeatureBean = new VendorFeatureBean();
                stateVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                stateVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_state);
                stateVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getState()));
                stateVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfStateRow = vendorFeature.setFeatureValue(stateVendorFeatureBean);

                VendorFeatureBean zipCodeVendorFeatureBean = new VendorFeatureBean();
                zipCodeVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                zipCodeVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_zipcode);
                zipCodeVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getZipcode()));
                zipCodeVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfZipCodeRow = vendorFeature.setFeatureValue(zipCodeVendorFeatureBean);

                VendorFeatureBean countryVendorFeatureBean = new VendorFeatureBean();
                countryVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                countryVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_country);
                countryVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getCountry()));
                countryVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOCountryRow = vendorFeature.setFeatureValue(countryVendorFeatureBean);

                VendorFeatureBean typeVendorFeatureBean = new VendorFeatureBean();
                typeVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                typeVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.partner_vendor_type);
                typeVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getVendorType()));
                typeVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfTypeRow = vendorFeature.setFeatureValue(typeVendorFeatureBean);

                VendorFeatureBean priceVendorFeatureBean = new VendorFeatureBean();
                priceVendorFeatureBean.setVendorId(partnerVendorBean.getPartnerVendorId());
                priceVendorFeatureBean.setFeatureType(Constants.VENDOR_FEATURETYPE.price);
                priceVendorFeatureBean.setValue(ParseUtil.checkNull(partnerVendorRequest.getPrice()));
                priceVendorFeatureBean.setUserId( partnerVendorRequest.getUserId() );
                Integer iNumOfPriceRow = vendorFeature.setFeatureValue(priceVendorFeatureBean);
            } else {
                appLogging.info("Could not identify a valid partner vendor id to create its features." + ParseUtil.checkNullObject(partnerVendorBean));
            }
        }  else {
            appLogging.info("Invalid Vendor Id (Parent) : " + ParseUtil.checkNull(partnerVendorRequest.getVendorId()));
        }
        return partnerVendorBean;
    }

    public PartnerVendorBean createPartnerVendor( PartnerVendorRequestBean partnerVendorRequest ) {
        PartnerVendorBean  partnerVendorBean = new PartnerVendorBean();
        if(partnerVendorRequest!=null && !Utility.isNullOrEmpty(partnerVendorRequest.getVendorId()) ) {

            try{
                BuildVendors buildVendors = new BuildVendors();
                VendorBean vendorBean =  buildVendors.registerVendor( partnerVendorRequest.getBusinessName() );

                if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {
                    partnerVendorRequest.setPartnerVendorId( vendorBean.getVendorId() );
                    partnerVendorRequest.setPartnerId( Utility.getNewGuid() );
                    partnerVendorBean = generatePartnerVendorBean( partnerVendorRequest );

                    BuildPartnerVendorData buildPartnerVendorData = new BuildPartnerVendorData();
                    Integer iNumOfRows = buildPartnerVendorData.insertPartnerVendor(partnerVendorBean);
                    if(iNumOfRows<=0){
                        partnerVendorBean = new PartnerVendorBean();
                    }
                }


            }catch(EditVendorException e){
                appLogging.error("Was not able to create the Partner Vendor:" + ExceptionHandler.getStackTrace(e));
            }

        }
        return partnerVendorBean;
    }

    public PartnerVendorBean updatePartnerVendor( PartnerVendorRequestBean partnerVendorRequest ) {
        PartnerVendorBean partnerVendorBean = new PartnerVendorBean();
        if(partnerVendorRequest!=null && !Utility.isNullOrEmpty(partnerVendorRequest.getVendorId())
                && !Utility.isNullOrEmpty(partnerVendorRequest.getPartnerVendorId())
                && !Utility.isNullOrEmpty(partnerVendorRequest.getPartnerId()) ) {
            try{

                BuildVendors buildVendors = new BuildVendors();
                VendorBean vendorBean = new VendorBean();
                vendorBean.setVendorId( partnerVendorRequest.getPartnerVendorId() );
                vendorBean.setVendorName( partnerVendorRequest.getBusinessName() );
                Integer iNumOfRecords = buildVendors.updateVendor(vendorBean);
                if(iNumOfRecords>0) {
                    partnerVendorBean.setPartnerId( partnerVendorRequest.getPartnerId() );
                    partnerVendorBean.setPartnerVendorId( partnerVendorRequest.getPartnerVendorId() );
                    partnerVendorBean.setVendorId(partnerVendorRequest.getVendorId() );
                }

            }catch(EditVendorException e){
                appLogging.error("Was not able to update  the Partner Vendor:" + ExceptionHandler.getStackTrace(e));
            }
        }
        return partnerVendorBean;
    }

    public boolean deletePartnerVendor( PartnerVendorRequestBean partnerVendorRequest ) {
        boolean isSuccessfulyDelete = false;
        if(partnerVendorRequest!=null  && !Utility.isNullOrEmpty(partnerVendorRequest.getPartnerVendorId()) ) {

            BuildPartnerVendorData buildPartnerVendorData = new BuildPartnerVendorData();
            int numOfRowsInserted = buildPartnerVendorData.deletePartnerVendor(partnerVendorRequest);
            if(numOfRowsInserted>0) {
                isSuccessfulyDelete = true;
            }

        }
        return isSuccessfulyDelete;
    }

    private PartnerVendorBean generatePartnerVendorBean(PartnerVendorRequestBean partnerVendorRequest) {
        PartnerVendorBean partnerVendorBean = new PartnerVendorBean();
        if(partnerVendorRequest!=null ) {
            partnerVendorBean.setPartnerId( partnerVendorRequest.getPartnerId() );
            partnerVendorBean.setVendorId( partnerVendorRequest.getVendorId() );
            partnerVendorBean.setPartnerVendorId( partnerVendorRequest.getPartnerVendorId() );
        }
        return partnerVendorBean;
    }
}
