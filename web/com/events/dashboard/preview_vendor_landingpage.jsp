<%@ page import="com.events.bean.vendors.VendorLandingPageFeatureBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.events.vendors.AccessVendorLandingPage" %>
<%@ page import="com.events.vendors.VendorLandingPageFeature" %>
<%@ page import="com.events.bean.vendors.VendorLandingPageRequestBean" %>
<%@ page import="com.events.bean.vendors.VendorLandingPageResponseBean" %>
<%@ page import="com.events.bean.vendors.VendorLandingPageBean" %>
<%@ page import="com.events.common.*" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="com.events.vendors.website.AccessVendorWebsite" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteRequestBean" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteResponseBean" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteBean" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteFeatureBean" %>
<%@ page import="com.events.vendors.website.VendorWebsiteFeature" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Preview Landing Page</title>
    <!--[if lte IE 9]>
    <script type="text/javascript" src="/s/html5shiv.js"></script>
    <![endif]-->

    <link rel="stylesheet" type="text/css" href="/css/flexslider.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="/css/style.css" />
    <link type="text/css" rel="stylesheet" href="/css/color/modern_blue.css" />
    <%
        if( session.getAttribute("SUBDOMAIN_COLORS") != null ) {
            String vendorOverRideColorCss = (String) session.getAttribute("SUBDOMAIN_COLORS");

            %><style type="text/css"><%=ParseUtil.checkNull(vendorOverRideColorCss)%></style><!--Overriding Vendor CSS --><%
        }
    %>
    <link rel="stylesheet" type="text/css" href="/css/flexslider.css">
</head>
<%

    Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    String sFeatureType = ParseUtil.checkNull(request.getParameter("featuretype"));
    String sVendorWebsiteId = ParseUtil.checkNull(request.getParameter("vendor_website_id"));

    String sLogo = Constants.EMPTY;
    String sLandingPagePic = Constants.EMPTY;
    String sUserFolderName = Constants.EMPTY;
    String sFacebookFeed = Constants.EMPTY;
    String sPinterestFeed = Constants.EMPTY;


    String imageHost = Utility.getImageUploadHost();
    if(!Utility.isNullOrEmpty(sVendorWebsiteId) ) {

        AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();

        VendorWebsiteRequestBean vendorWebsiteRequestBean = new VendorWebsiteRequestBean();
        vendorWebsiteRequestBean.setVendorWebsiteId( sVendorWebsiteId );

        VendorWebsiteResponseBean vendorWebsiteResponseBean = accessVendorWebsite.getVendorWebsiteByWebsiteId( vendorWebsiteRequestBean );
        VendorWebsiteBean vendorWebsiteBean = vendorWebsiteResponseBean.getVendorWebsiteBean();


        Folder folder = new Folder();
        sUserFolderName = folder.getFolderName( Constants.USER_TYPE.VENDOR, vendorWebsiteBean.getVendorId() );

        String sImageLocation =  imageHost + "/"+sUserFolderName+"/";



        ArrayList<VendorWebsiteFeatureBean> arrVendorWebsiteFeatureBean = new ArrayList<VendorWebsiteFeatureBean>();
        arrVendorWebsiteFeatureBean.add(accessVendorWebsite.generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_logo));
        arrVendorWebsiteFeatureBean.add(accessVendorWebsite.generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_landingpagephoto));
        arrVendorWebsiteFeatureBean.add(accessVendorWebsite.generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_facebook_feed_script));
        arrVendorWebsiteFeatureBean.add(accessVendorWebsite.generateVendorWebsiteFeatureBean(Constants.VENDOR_WEBSITE_FEATURETYPE.saved_pinterest_feed_script));

        VendorWebsiteFeature vendorWebsiteFeature = new VendorWebsiteFeature();
        ArrayList<VendorWebsiteFeatureBean> arrMultipleFeatureBean = vendorWebsiteFeature.getMultipleFeatures( arrVendorWebsiteFeatureBean, sVendorWebsiteId );
        appLogging.info(" arrMultipleFeatureBean : " + arrMultipleFeatureBean );
        for (VendorWebsiteFeatureBean vendorWebsiteFeatureBean : arrMultipleFeatureBean) {
            String sFeatureName = vendorWebsiteFeatureBean.getFeatureName();
            String sValue = vendorWebsiteFeatureBean.getValue();
            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_WEBSITE_FEATURETYPE.saved_logo.toString())) {
                if(!Utility.isNullOrEmpty(sValue)) {
                    sLogo = sImageLocation + sValue;
                }

            }


            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_WEBSITE_FEATURETYPE.saved_landingpagephoto.toString())) {
                if(!Utility.isNullOrEmpty(sValue)) {
                    sLandingPagePic = sImageLocation + sValue;
                }
            }

            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_WEBSITE_FEATURETYPE.saved_facebook_feed_script.toString())) {
                sFacebookFeed =   sValue;
            }

            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_WEBSITE_FEATURETYPE.saved_pinterest_feed_script.toString())) {
                sPinterestFeed =   sValue;
            }
        }
    }
    if(Utility.isNullOrEmpty(sLogo)) {
        sLogo = "/img/logo.png";
    }
    if(Utility.isNullOrEmpty(sLandingPagePic)) {
        sLandingPagePic = "/img/landingpage_wedding.jpg";
    }
%>
<body>
    <div class="page_wrap">
        <div class="top_navbar_format">
            <div class="container">
                <div class="top_navbar_links">
                    <ul class="nav navbar-nav navbar-right menu">
                        <li><a href="#"><i class="fa fa-sign-in"></i> Sign In</a></li>
                        <li><a href="#">Register</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="menu_bar">
            <div class="container">
                <div class="menu_logo">
                    <a href="#"><img src="<%=sLogo%>" alt=""></a>
                </div>
                <div class="menu_links">
                    <ul class="nav navbar-nav navbar-right menu">
                        <li class="currently_active"><a href="/index.jsp">Home</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="flexslider">
                <ul class="slides">
                    <li>
                        <img src="<%=sLandingPagePic%>" />
                    </li>
                </ul>
            </div>
        </div>
        <%
            if(!Utility.isNullOrEmpty(sFacebookFeed) && !Utility.isNullOrEmpty(sPinterestFeed) ){
        %>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <table width="100%">
                    <tr>
                        <td  style="background-color:#3b5999;vertical-align: top;">
                            <div class="col-md-12">

                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div style="width: 55px;  float:left;">
                                            <a href="#"><img src="/img/FB-f-Logo__blue_50.png" alt=""></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row" style="text-align:center;">
                                    <div class="col-md-12">
                                        <%=sFacebookFeed%>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td   style="background-color:#555;vertical-align: top;" >
                            <div class="col-md-12">
                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div style="width: 200px;  float:left;">
                                            <a href="#"><img src="/img/pinterest_logo_white.png" alt=""></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row" style="text-align:center;">
                                    <div class="col-md-12">
                                        <%=sPinterestFeed%>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        &nbsp;
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </table>
                </div>
            </div>
        </div>

        <%
            }
        %>
    </div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.flexslider-min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        $('.flexslider').flexslider({
            animation: "slide",
            smoothHeight: true,
            useCSS: false
        });
        (function(d){
            var f = d.getElementsByTagName('SCRIPT')[0], p = d.createElement('SCRIPT');
            p.type = 'text/javascript';
            p.async = true;
            p.src = '//assets.pinterest.com/js/pinit.js';
            f.parentNode.insertBefore(p, f);
        }(document));
    });
</script>
</html>