<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.vendors.VendorBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.vendors.website.AccessVendorWebsite" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteRequestBean" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteResponseBean" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteBean" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteFeatureBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="/css/flexslider.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%


    String imageHost = Utility.getImageUploadHost();
    String sFolderName = Constants.EMPTY;
    HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmVendorWebsiteFeatureBean = new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE, VendorWebsiteFeatureBean>();
    if( session.getAttribute("SUBDOMAIN_VENDOR") != null && session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE") !=null ) {
        VendorBean vendorBean = (VendorBean)  session.getAttribute("SUBDOMAIN_VENDOR");
        VendorWebsiteBean vendorWebsiteBean = (VendorWebsiteBean) session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE");
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())){

            AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
            hmVendorWebsiteFeatureBean =  accessVendorWebsite.getPublishedFeaturesForLandingPage(vendorWebsiteBean);

            sFolderName = ParseUtil.checkNull(vendorBean.getFolder());

        }
    }

    String sLandingPagePhoto = Constants.EMPTY;
    String sGreetingHeader = Constants.EMPTY;
    String sGreetingText = Constants.EMPTY;
    String sFacebookFeed = Constants.EMPTY;
    String sPinterestFeed = Constants.EMPTY;
    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_landingpagephoto);
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue()) && !Utility.isNullOrEmpty(sFolderName) && !Utility.isNullOrEmpty(imageHost)){
            sLandingPagePhoto =   imageHost + "/" +  sFolderName + "/" +vendorWebsiteFeatureBean.getValue();
        }
    }
    if(Utility.isNullOrEmpty(sLandingPagePhoto)){
        sLandingPagePhoto = "http://d33np9n32j53g7.cloudfront.net/assets/new/home-hero-a4778806742a55871d57663035e77b64.jpg";
    }

    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_greeting_header);
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
            sGreetingHeader =  vendorWebsiteFeatureBean.getValue();
        }
    }
    if(Utility.isNullOrEmpty(sGreetingHeader)){
        sGreetingHeader = "Welcome";
    }

    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_greeting_text);
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
            sGreetingText =  vendorWebsiteFeatureBean.getValue();
        }
    }
    if(Utility.isNullOrEmpty(sGreetingText)){
        sGreetingText = "Manage Your Client Events, Vendors and Team Members";
    }

    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_facebook_feed_script);
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
            sFacebookFeed =  vendorWebsiteFeatureBean.getValue();
        }
    }

    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_pinterest_feed_script);
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
            sPinterestFeed =  vendorWebsiteFeatureBean.getValue();
        }
    }

%>
<body>
  <div class="page_wrap">
      <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
      </jsp:include>
      <jsp:include page="/com/events/common/menu_bar.jsp">
          <jsp:param name="home_active" value="currently_active"/>
      </jsp:include>
      <div>
      <div class="container-fluid">
          <div style="background-image: url('<%=sLandingPagePhoto%>');height: 447px;margin-left: 0;margin-right: 0;background-position: 50% 0;">

          </div>
          <!--<div class="flexslider">
              <ul class="slides">
                  <li>
                      <img src="http://www.smarasoft.com/img/slider/slider1.jpg" />
                      <div class="container hidden-sm ">
                          <div class="slide-caption bottom-left">
                              <h1 style="color: #ffffff;">An Event Planner's Management Portal</h1>
                              <p>Manage your team, clients and vendors from one place.</p>
                          </div>
                      </div>
                  </li>
                  <li>
                      <img src="http://www.smarasoft.com/img/slider/slider2.jpg" />
                      <div class="container hidden-sm">
                          <div class="slide-caption hidden-sm bottom-left">
                              <h1 style="color: #ffffff;">Communcate Effeciently and Quickly</h1>
                              <p>Notifications, Reminders, Emails for you and your clients to be in touch.</p>
                          </div>
                      </div>
                  </li>
                  <li>
                      <img src="http://www.smarasoft.com/img/slider/slider3.jpg" />
                      <div class="container  hidden-sm ">
                          <div class="slide-caption hidden-sm bottom-left">
                              <h1 style="color: #ffffff;">Tools For You And Your Clients</h1>
                              <p>Customizable event planning tools</p>
                          </div>
                      </div>
                  </li>
              </ul>
          </div> -->
      </div>
      <div class="container">
          <div class="row">
              <div class="col-md-12">
                  &nbsp;
              </div>
          </div>
          <div class="row">
              <div class="col-md-12"  style="text-align:center;">
                  <h1><%=sGreetingHeader%></h1>
              </div>
          </div>

          <div class="row">
              <div class="col-md-12">
                  &nbsp;
              </div>
          </div>
          <div class="row">
              <div class="col-md-12"  style="text-align:center;">
                  <h1><%=sGreetingText%></h1>
              </div>
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
                              <td  style="background-color:#3b5999;vertical-align: top; " width="50%">
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
                              <td   style="background-color:#555;vertical-align: top;"  width="50%" >
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
  </div>
  </body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.flexslider-min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        $('.flexslider').flexslider({
            animation: "slide",
            smoothHeight: true
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
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
</html>