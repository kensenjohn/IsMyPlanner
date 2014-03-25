<%@ page import="com.events.bean.vendors.website.VendorWebsiteFeatureBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteBean" %>
<%@ page import="com.events.bean.vendors.VendorBean" %>
<%@ page import="com.events.vendors.website.AccessVendorWebsite" %>
<%@ page import="com.events.common.ParseUtil" %><%
    HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmVendorWebsiteFeatureBean = new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE, VendorWebsiteFeatureBean>();
    if( session.getAttribute("SUBDOMAIN_VENDOR") != null && session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE") !=null ) {
        VendorBean vendorBean = (VendorBean)  session.getAttribute("SUBDOMAIN_VENDOR");
        VendorWebsiteBean vendorWebsiteBean = (VendorWebsiteBean) session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE");
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())){

            AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
            hmVendorWebsiteFeatureBean =  accessVendorWebsite.getPublishedFeaturesForLandingPage(vendorWebsiteBean);
        }
    }

    boolean isAboutUsShown = false;
    boolean isContactShown = false;
    boolean isPrivacyShown = false;
    boolean isFollowusShown = false;


    String sFacebookUrl = Constants.EMPTY;
    String sTwitterUrl = Constants.EMPTY;
    String sPinterestUrl = Constants.EMPTY;
    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeaturAboutUsBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.show_footer_about_us);
        if(vendorWebsiteFeaturAboutUsBean!=null) {
            isAboutUsShown = ParseUtil.sTob(vendorWebsiteFeaturAboutUsBean.getValue() );
        }

        VendorWebsiteFeatureBean vendorWebsiteFeatueContactBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.show_footer_contact);
        if(vendorWebsiteFeatueContactBean!=null) {
            isContactShown = ParseUtil.sTob(vendorWebsiteFeatueContactBean.getValue() );
        }

        VendorWebsiteFeatureBean vendorWebsiteFeatuePrivacyBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.show_footer_contact);
        if(vendorWebsiteFeatuePrivacyBean!=null) {
            isPrivacyShown = ParseUtil.sTob(vendorWebsiteFeatuePrivacyBean.getValue() );
        }

        VendorWebsiteFeatureBean vendorWebsiteFeatueFollowUsBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.show_footer_followus);
        if(vendorWebsiteFeatueFollowUsBean!=null) {
            isFollowusShown = ParseUtil.sTob(vendorWebsiteFeatueFollowUsBean.getValue() );
        }

        if(isFollowusShown){
            VendorWebsiteFeatureBean vendorWebsiteFeatueFacebookUrlBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_footer_facebook);
            if(vendorWebsiteFeatueFacebookUrlBean!=null) {
                sFacebookUrl = ParseUtil.checkNull(vendorWebsiteFeatueFacebookUrlBean.getValue() );
            }

            VendorWebsiteFeatureBean vendorWebsiteFeatueTwitterUrlBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_footer_twitter);
            if(vendorWebsiteFeatueFacebookUrlBean!=null) {
                sTwitterUrl = ParseUtil.checkNull(vendorWebsiteFeatueFacebookUrlBean.getValue() );
            }

            VendorWebsiteFeatureBean vendorWebsiteFeatuePinterestUrlBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_footer_pinterest);
            if(vendorWebsiteFeatueFacebookUrlBean!=null) {
                sPinterestUrl = ParseUtil.checkNull(vendorWebsiteFeatueFacebookUrlBean.getValue() );
            }
        }
    }
    com.events.common.Configuration applicationConfig = com.events.common.Configuration.getInstance(com.events.common.Constants.APPLICATION_PROP);

    String sCopyrightYear = applicationConfig.get("copyright_year");
    String sCopyrightCompany = applicationConfig.get("copyright_company");
%>
<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-12 col-sm-12">
                <div class="row">
                    <div class="col-md-8 col-sm-8">
                        <div id="footer-tabs">
                            <ul class="footer-tabs">
                                <%if(isAboutUsShown){%><li><a href="#">About Us</a></li><%}%>
                                <%if(isContactShown){%><li><a href="#">Contact</a></li><%}%>
                                <%if(isPrivacyShown){%><li><a href="#">Privacy</a></li><%}%>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-4 col-sm-4" style="text-align:right;">
                        <%if(isFollowusShown){%>
                        <h4>
                            <%if(!Utility.isNullOrEmpty(sFacebookUrl)){%><a  href="<%=sFacebookUrl%>" target="_blank"><i class="fa fa-facebook"></i></a><%}%>
                            <%if(!Utility.isNullOrEmpty(sTwitterUrl)){%><a  href="<%=sTwitterUrl%>" target="_blank"><i class="fa fa-twitter"></i></a><%}%>
                            <%if(!Utility.isNullOrEmpty(sPinterestUrl)){%><a  href="<%=sPinterestUrl%>" target="_blank"><i class="fa fa-pinterest"></i></a><%}%>
                        </h4>
                        <%}%>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12">

                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-offset-10 col-md-2 col-sm-offset-10 col-sm-2" style="text-align:right;">
                        <%=sCopyrightYear %> <%=sCopyrightCompany %>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                        &nbsp;
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>