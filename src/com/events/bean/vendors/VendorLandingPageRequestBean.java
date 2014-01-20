package com.events.bean.vendors;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorLandingPageRequestBean {

    private String vendorLandingPageId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String themeName = Constants.EMPTY;
    private String logoImage = Constants.EMPTY;
    private String landingPageImage = Constants.EMPTY;
    private String facebookUrl = Constants.EMPTY;
    private String pinterestUrl = Constants.EMPTY;

    public String getVendorLandingPageId() {
        return vendorLandingPageId;
    }

    public void setVendorLandingPageId(String vendorLandingPageId) {
        this.vendorLandingPageId = vendorLandingPageId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getLandingPageImage() {
        return landingPageImage;
    }

    public void setLandingPageImage(String landingPageImage) {
        this.landingPageImage = landingPageImage;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getPinterestUrl() {
        return pinterestUrl;
    }

    public void setPinterestUrl(String pinterestUrl) {
        this.pinterestUrl = pinterestUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorLandingPageRequestBean{");
        sb.append("vendorLandingPageId='").append(vendorLandingPageId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", themeName='").append(themeName).append('\'');
        sb.append(", logoImage='").append(logoImage).append('\'');
        sb.append(", landingPageImage='").append(landingPageImage).append('\'');
        sb.append(", facebookUrl='").append(facebookUrl).append('\'');
        sb.append(", pinterestUrl='").append(pinterestUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
