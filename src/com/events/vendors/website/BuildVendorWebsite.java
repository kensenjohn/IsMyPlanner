package com.events.vendors.website;

import com.events.bean.vendors.VendorLandingPageFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.bean.vendors.website.VendorWebsiteResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.website.BuildVendorWebsiteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendorWebsite extends VendorWebsite {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    private VendorWebsiteResponseBean saveWebsite( VendorWebsiteRequestBean vendorWebsiteRequestBean ) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        if(vendorWebsiteRequestBean!=null){
            if(Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorWebsiteId() ))  {
                vendorWebsiteRequestBean.setVendorWebsiteId(Utility.getNewGuid());
                vendorWebsiteResponseBean = createWebsite(vendorWebsiteRequestBean);
            } else {
                vendorWebsiteResponseBean = updateWebsite(vendorWebsiteRequestBean);
            }
        }
        return vendorWebsiteResponseBean;
    }
    public VendorWebsiteResponseBean saveLandingPageLayoutContent(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = saveWebsite( vendorWebsiteRequestBean );
        boolean isError = false;
        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteResponseBean.getVendorWebsiteId();
            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getLandingPageImage() )) {
                VendorWebsiteFeatureBean landingPagePicFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_landingpagephoto,
                        vendorWebsiteRequestBean.getLandingPageImage(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( landingPagePicFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getThemeName() )) {
                VendorWebsiteFeatureBean landingPageThemeNameFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_themename,
                        vendorWebsiteRequestBean.getThemeName(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( landingPageThemeNameFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getFacebookFeedScript() )) {
                VendorWebsiteFeatureBean landingPageFacebookFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_facebook_feed_script,
                        vendorWebsiteRequestBean.getFacebookFeedScript(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( landingPageFacebookFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getPinterestFeedScript() )) {
                VendorWebsiteFeatureBean landingPagePinterestFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_pinterest_feed_script,
                        vendorWebsiteRequestBean.getPinterestFeedScript(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( landingPagePinterestFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }


        }  else {
            isError = true;
        }
        if(isError) {
            vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        }
        return vendorWebsiteResponseBean;
    }

    public VendorWebsiteResponseBean publishLandingPageLayoutContent(VendorWebsiteRequestBean vendorWebsiteRequestBean){
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        appLogging.info(" vendor website id : " + vendorWebsiteRequestBean.getVendorWebsiteId());
        boolean isError = false;
        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteRequestBean.getVendorWebsiteId();

            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();
            if(!isError){
                VendorWebsiteFeatureBean tmpLandingPagePicFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_landingpagephoto,
                        Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpLandingPagePicFeatureBean = vendorWebsiteFeature.getFeature( tmpLandingPagePicFeatureBean );
                if(!Utility.isNullOrEmpty(tmpLandingPagePicFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpLandingPagePicFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_landingpagephoto );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpLandingPagePicFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpThemeNameFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_themename,
                        Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpThemeNameFeatureBean = vendorWebsiteFeature.getFeature( tmpThemeNameFeatureBean );
                if(!Utility.isNullOrEmpty(tmpThemeNameFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpThemeNameFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_themename );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpThemeNameFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpFacebookFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_facebook_feed_script,
                        Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpFacebookFeatureBean = vendorWebsiteFeature.getFeature( tmpFacebookFeatureBean );
                if(!Utility.isNullOrEmpty(tmpFacebookFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpFacebookFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_facebook_feed_script );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpFacebookFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpPinterstFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_pinterest_feed_script,
                        Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpPinterstFeatureBean = vendorWebsiteFeature.getFeature( tmpPinterstFeatureBean );
                if(!Utility.isNullOrEmpty(tmpPinterstFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpPinterstFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_pinterest_feed_script );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpPinterstFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }


            if(!isError){
                vendorWebsiteResponseBean.setVendorWebsiteId(sVendorWebsiteId );
            }
        } else {
            isError = true;
        }

        if(isError) {
            vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
            vendorWebsiteResponseBean.setMessage("Oops!! We were unable to publish your changes. Please \"Save\" changes before trying to publish it.");
        }
        return vendorWebsiteResponseBean;
    }

    public VendorWebsiteResponseBean saveWebsiteLogo(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = saveWebsite( vendorWebsiteRequestBean );
        boolean isError = false;
        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteResponseBean.getVendorWebsiteId();
            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getLogoImage() )) {
                VendorWebsiteFeatureBean logoFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_logo,
                        vendorWebsiteRequestBean.getLogoImage(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( logoFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }
        }  else {
            isError = true;
        }
        if(isError) {
            vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        }
        return vendorWebsiteResponseBean;
    }

    public VendorWebsiteResponseBean publishWebsiteLogo(VendorWebsiteRequestBean vendorWebsiteRequestBean){
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
         appLogging.info(" vendor website id : " + vendorWebsiteRequestBean.getVendorWebsiteId());
        boolean isError = false;
        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteRequestBean.getVendorWebsiteId();

            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();
            if(!isError){
                VendorWebsiteFeatureBean tmpLogoFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_logo,
                        Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpLogoFeatureBean = vendorWebsiteFeature.getFeature( tmpLogoFeatureBean );
                if(!Utility.isNullOrEmpty(tmpLogoFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpLogoFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_logo );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpLogoFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                vendorWebsiteResponseBean.setVendorWebsiteId(sVendorWebsiteId );
            }
        } else {
            isError = true;
        }

        if(isError) {
            vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
            vendorWebsiteResponseBean.setMessage("Oops!! We were unable to publish your changes. Please \"Save\" changes before trying to publish it.");
        }
        return vendorWebsiteResponseBean;
    }

    public VendorWebsiteResponseBean saveWebsiteColor(VendorWebsiteRequestBean vendorWebsiteRequestBean){
        VendorWebsiteResponseBean vendorWebsiteResponseBean = saveWebsite( vendorWebsiteRequestBean );
        boolean isError = false;

        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteResponseBean.getVendorWebsiteId();
            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getBackground())) {
                VendorWebsiteFeatureBean bkgColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_bkg_color,
                        vendorWebsiteRequestBean.getBackground(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( bkgColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getHighlightedTextOrLink())) {
                VendorWebsiteFeatureBean highlighedColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_highlighted_color,
                        vendorWebsiteRequestBean.getHighlightedTextOrLink(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( highlighedColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getText())) {
                VendorWebsiteFeatureBean textColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_text_color,
                        vendorWebsiteRequestBean.getText(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( textColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getNavBreadTabBackground())) {
                VendorWebsiteFeatureBean navBreadTabColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_navbar_breadcrumb_tab_color,
                        vendorWebsiteRequestBean.getNavBreadTabBackground(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( navBreadTabColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getBorder())) {
                VendorWebsiteFeatureBean borderColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_border_color,
                        vendorWebsiteRequestBean.getBorder(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( borderColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getFilledButton())) {
                VendorWebsiteFeatureBean filledButtonColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_filled_button_color,
                        vendorWebsiteRequestBean.getFilledButton(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( filledButtonColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getFilledButtonText() )) {
                VendorWebsiteFeatureBean filledButtonTextColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_filled_button_text_color,
                        vendorWebsiteRequestBean.getFilledButtonText(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( filledButtonTextColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getPlainButton() )) {
                VendorWebsiteFeatureBean plainButtonColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_plain_button_color,
                        vendorWebsiteRequestBean.getPlainButton(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( plainButtonColorFeatureBean );
                if(iNumOfRows<=0){
                    isError = true;
                }
            }

            if(!Utility.isNullOrEmpty(vendorWebsiteRequestBean.getPlainButtonText() )) {
                VendorWebsiteFeatureBean plainButtonTextColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_plain_button_text_color,
                        vendorWebsiteRequestBean.getPlainButtonText(), vendorWebsiteRequestBean.getModifiedByUserId() );
                Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( plainButtonTextColorFeatureBean );
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
        if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorWebsiteId())) {
            String sVendorWebsiteId = vendorWebsiteRequestBean.getVendorWebsiteId();

            VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();
            if(!isError){
                VendorWebsiteFeatureBean tmpBkgColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_bkg_color,
                        Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpBkgColorFeatureBean = vendorWebsiteFeature.getFeature( tmpBkgColorFeatureBean );
                if(!Utility.isNullOrEmpty(tmpBkgColorFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpBkgColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_bkg_color );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpBkgColorFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }
            appLogging.info("isError 2" + isError );
            if(!isError){
                VendorWebsiteFeatureBean tmpHighlightedColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId, Constants.VENDOR_WEBSITE_FEATURETYPE.saved_highlighted_color,
                        Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpHighlightedColorFeatureBean = vendorWebsiteFeature.getFeature( tmpHighlightedColorFeatureBean );
                if(!Utility.isNullOrEmpty(tmpHighlightedColorFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpHighlightedColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_highlighted_color );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpHighlightedColorFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpTextColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_text_color, Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpTextColorFeatureBean = vendorWebsiteFeature.getFeature( tmpTextColorFeatureBean );
                if(!Utility.isNullOrEmpty(tmpTextColorFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpTextColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_text_color );
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpTextColorFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpNavBradTabColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_navbar_breadcrumb_tab_color, Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpNavBradTabColorFeatureBean = vendorWebsiteFeature.getFeature( tmpNavBradTabColorFeatureBean );
                if(!Utility.isNullOrEmpty(tmpNavBradTabColorFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpNavBradTabColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_navbar_breadcrumb_tab_color);
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpNavBradTabColorFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpFilledButtonColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_filled_button_color, Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpFilledButtonColorFeatureBean = vendorWebsiteFeature.getFeature( tmpFilledButtonColorFeatureBean );
                if(!Utility.isNullOrEmpty(tmpFilledButtonColorFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpFilledButtonColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_filled_button_color);
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpFilledButtonColorFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                }else {
                    isError = true;
                }
            }
            if(!isError){
                VendorWebsiteFeatureBean tmpFilledButtonTextColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_filled_button_text_color, Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpFilledButtonTextColorFeatureBean = vendorWebsiteFeature.getFeature( tmpFilledButtonTextColorFeatureBean );
                if(!Utility.isNullOrEmpty(tmpFilledButtonTextColorFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpFilledButtonTextColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_filled_button_text_color);
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpFilledButtonTextColorFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                }else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpPlainButtonColorFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_plain_button_color, Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpPlainButtonColorFeatureBean = vendorWebsiteFeature.getFeature( tmpPlainButtonColorFeatureBean );
                if(!Utility.isNullOrEmpty(tmpPlainButtonColorFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpPlainButtonColorFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_plain_button_color);
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpPlainButtonColorFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpPlainButtonColorTextFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_plain_button_text_color, Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpPlainButtonColorTextFeatureBean = vendorWebsiteFeature.getFeature( tmpPlainButtonColorTextFeatureBean );
                if(!Utility.isNullOrEmpty(tmpPlainButtonColorTextFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpPlainButtonColorTextFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_plain_button_text_color);
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpPlainButtonColorTextFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }

            if(!isError){
                VendorWebsiteFeatureBean tmpBorderColorTextFeatureBean = generateVendorWebsiteFeatureBean(sVendorWebsiteId,
                        Constants.VENDOR_WEBSITE_FEATURETYPE.saved_border_color, Constants.EMPTY , vendorWebsiteRequestBean.getModifiedByUserId() );
                tmpBorderColorTextFeatureBean = vendorWebsiteFeature.getFeature( tmpBorderColorTextFeatureBean );
                if(!Utility.isNullOrEmpty(tmpBorderColorTextFeatureBean.getVendorWebsiteFeatureId())) {
                    tmpBorderColorTextFeatureBean.setFeatureType( Constants.VENDOR_WEBSITE_FEATURETYPE.published_border_color);
                    Integer iNumOfRows =  vendorWebsiteFeature.setFeatureValue( tmpBorderColorTextFeatureBean );
                    if(iNumOfRows<=0){
                        isError = true;
                    }
                } else {
                    isError = true;
                }
            }
            appLogging.info("isError last" + isError );
            if(!isError) {
                vendorWebsiteResponseBean.setVendorWebsiteId( sVendorWebsiteId );
            }

        } else {
            isError = true;
        }
        if(isError) {
            vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
            vendorWebsiteResponseBean.setMessage("Oops!! We were unable to publish your changes. Please \"Save\" changes before trying to publish it.");
        }
        return vendorWebsiteResponseBean;
    }

    private VendorWebsiteResponseBean createWebsite(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        if(vendorWebsiteRequestBean!=null && !Utility.isNullOrEmpty( vendorWebsiteRequestBean.getVendorWebsiteId() )) {
            VendorWebsiteBean vendorWebsiteBean = generateVendorWebsiteBean(vendorWebsiteRequestBean);
            BuildVendorWebsiteData buildVendorWebsiteData = new BuildVendorWebsiteData();
            Integer iNumOfRows = buildVendorWebsiteData.insertVendorWebsite( vendorWebsiteBean );
            if(iNumOfRows>0){
                vendorWebsiteResponseBean.setVendorWebsiteBean(vendorWebsiteBean);
                vendorWebsiteResponseBean.setVendorWebsiteId( vendorWebsiteRequestBean.getVendorWebsiteId() );
            }
        }
        return vendorWebsiteResponseBean;
    }

    public VendorWebsiteResponseBean updateWebsite(VendorWebsiteRequestBean vendorWebsiteRequestBean){
        VendorWebsiteResponseBean vendorWebsiteResponseBean = new VendorWebsiteResponseBean();
        if(vendorWebsiteRequestBean!=null && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorWebsiteId())
                && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getVendorId()) ) {
            VendorWebsiteBean vendorWebsiteBean = generateVendorWebsiteBean(vendorWebsiteRequestBean);
            BuildVendorWebsiteData buildVendorWebsiteData = new BuildVendorWebsiteData();
            Integer iNumOfRows = buildVendorWebsiteData.updateVendorWebsite(vendorWebsiteBean);
            if(iNumOfRows>0){
                vendorWebsiteResponseBean.setVendorWebsiteBean(vendorWebsiteBean);
                vendorWebsiteResponseBean.setVendorWebsiteId( vendorWebsiteRequestBean.getVendorWebsiteId() );
            }
        }
        return vendorWebsiteResponseBean;
    }
}
