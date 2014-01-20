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
</head>
<%
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    String sFeatureType = ParseUtil.checkNull(request.getParameter("featuretype"));
    String sVendorLandingPageId = ParseUtil.checkNull(request.getParameter("vendor_landingpage_id"));

    String sLogo = Constants.EMPTY;
    String sLandingPagePic = Constants.EMPTY;
    String sUserFolderName = Constants.EMPTY;
    String sFacebookFeed = Constants.EMPTY;
    String sPinterstFeed = Constants.EMPTY;

    String imageHost = ParseUtil.checkNull(applicationConfig.get(Constants.IMAGE_HOST));

    if(!Utility.isNullOrEmpty(sVendorLandingPageId) ) {

        AccessVendorLandingPage accessVendorLandingPage = new AccessVendorLandingPage();

        VendorLandingPageRequestBean vendorLandingPageRequestBean = new VendorLandingPageRequestBean();
        vendorLandingPageRequestBean.setVendorLandingPageId(sVendorLandingPageId);

        VendorLandingPageResponseBean vendorLandingPageResponseBean = accessVendorLandingPage.getVendorLandingPageByLandingPageId(vendorLandingPageRequestBean);
        VendorLandingPageBean vendorLandingPageBean = vendorLandingPageResponseBean.getVendorLandingPageBean();
        appLogging.info("vendorLandingPageBean " + vendorLandingPageBean);

        Folder folder = new Folder();
        sUserFolderName = folder.getFolderName( Constants.USER_TYPE.VENDOR, vendorLandingPageBean.getVendorId() );
        appLogging.info("sUserFolderName : " + sUserFolderName);

        String sImageLocation =  imageHost + "/"+sUserFolderName+"/";



        ArrayList<VendorLandingPageFeatureBean> arrVendorLandingPageFeatureBean = new ArrayList<VendorLandingPageFeatureBean>();
        arrVendorLandingPageFeatureBean.add( accessVendorLandingPage.generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.logo) );
        arrVendorLandingPageFeatureBean.add( accessVendorLandingPage.generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.landingpagephoto) );
        arrVendorLandingPageFeatureBean.add( accessVendorLandingPage.generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.facebook_url) );
        arrVendorLandingPageFeatureBean.add( accessVendorLandingPage.generateVendorLandingPageFeatureBean(Constants.VENDOR_LANDINGPAGE_FEATURETYPE.pinterest_url) );

        VendorLandingPageFeature vendorLandingPageFeature = new VendorLandingPageFeature();
        ArrayList<VendorLandingPageFeatureBean> arrMultipleFeatureBean = vendorLandingPageFeature.getMultipleFeatures(arrVendorLandingPageFeatureBean, sVendorLandingPageId );
        for (VendorLandingPageFeatureBean vendorLandingPageFeatureBean : arrMultipleFeatureBean) {
            String sFeatureName = vendorLandingPageFeatureBean.getFeatureName();
            String sValue = vendorLandingPageFeatureBean.getValue();
            appLogging.info("sFeatureName : " + sFeatureName);
            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_LANDINGPAGE_FEATURETYPE.logo.toString())) {
                sLogo = sImageLocation + sValue;
            }


            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_LANDINGPAGE_FEATURETYPE.landingpagephoto.toString())) {
                sLandingPagePic = sImageLocation + sValue;
            }

            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_LANDINGPAGE_FEATURETYPE.facebook_url.toString())) {
                sFacebookFeed =   sValue;
            }

            if(sFeatureName.equalsIgnoreCase( Constants.VENDOR_LANDINGPAGE_FEATURETYPE.pinterest_url.toString())) {
                sPinterstFeed =   sValue;
            }
        }


    }
%>
<body>
    <div class="page_wrap">
        <div class="top_navbar_format">
            <div class="container">
                <div class="top_navbar_links">
                    <ul class="nav navbar-nav navbar-right menu">
                        <li><a href="#">Login</a></li>
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
        <div>
            <div class="flexslider">
                <ul class="slides">
                    <li>
                        <img src="<%=sLandingPagePic%>" />
                    </li>
                </ul>
            </div>
        </div>
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
                                        <%=sPinterstFeed%>
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
    </div>
</body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="/js/jquery.flexslider-min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        $('.flexslider').flexslider({
            animation: "slide"
        });
    });
</script>
</html>