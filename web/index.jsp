<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.vendors.VendorBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.vendors.website.AccessVendorWebsite" %>
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
    String sBucket = Utility.getS3Bucket();
    String sFolderName = Constants.EMPTY;
    HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmVendorWebsiteFeatureBean = new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE, VendorWebsiteFeatureBean>();
    boolean isVendorSite =false;
    if( session.getAttribute("SUBDOMAIN_VENDOR") != null && session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE") !=null ) {
        VendorBean vendorBean = (VendorBean)  session.getAttribute("SUBDOMAIN_VENDOR");
        VendorWebsiteBean vendorWebsiteBean = (VendorWebsiteBean) session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE");
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())){

            AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
            hmVendorWebsiteFeatureBean =  accessVendorWebsite.getPublishedFeaturesForLandingPage(vendorWebsiteBean);

            sFolderName = ParseUtil.checkNull(vendorBean.getFolder());
            isVendorSite = true;
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
            sLandingPagePhoto =   imageHost + "/" + sBucket + "/" +  sFolderName + "/" +vendorWebsiteFeatureBean.getValue();
        }
    }
    if(Utility.isNullOrEmpty(sLandingPagePhoto)){
        sLandingPagePhoto = "/img/landingpage_wedding.jpg";
    }

    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_greeting_header);
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
            sGreetingHeader =  vendorWebsiteFeatureBean.getValue();
        }
    }
    if(Utility.isNullOrEmpty(sGreetingHeader)){
        sGreetingHeader = "";
    }

    if(hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_greeting_text);
        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
            sGreetingText =  vendorWebsiteFeatureBean.getValue();
        }
    }
    if(Utility.isNullOrEmpty(sGreetingText)){
        sGreetingText = "A Platform to Manage Your Clients, Events, Vendors and Team";
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
      <div class="container-fluid">
          <!--<div style="background-image: url('<%=sLandingPagePhoto%>'); margin-left: 0;margin-right: 0;background-position: 50% 0;">
            &nbsp;
          </div> -->
          <div class="flexslider">
              <ul class="slides">
                  <li>
                      <img src="<%=sLandingPagePhoto%>" />
                      <!--<div class="container hidden-sm ">
                          <div class="slide-caption bottom-left">
                              <h1 style="color: #ffffff;">An Event Planner's Management Portal</h1>
                              <p>Manage your team, clients and vendors from one place.</p>
                          </div>
                      </div>  -->
                  </li>
              </ul>
          </div>
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
              <div class="col-md-12 col-sm-12 col-xs-12"  style="text-align:center;">
                  <h2><%=sGreetingText%></h2>
              </div>
          </div>
          <div class="row">
              <div class="col-md-12">
                  &nbsp;
              </div>
          </div>
          <%
              if(!isVendorSite) {
          %>
          <div class="row " style="text-align: center;">
              <div class="col-sm-12">
                  <div class="row ">
                      <div class="col-sm-4">
                          <h2><i class="fa fa-cloud"></i></h2>
                          <h5>Management</h5>
                          <p>Manage your clients, events, and vendors online. Track their progress and status from anywhere. </p>
                      </div>
                      <div class="col-sm-4">
                          <h2><i class="fa fa-facebook"></i><i class="fa fa-twitter"></i><i class="fa fa-pinterest"></i></h2>
                          <h5>Social Media Marketing</h5>
                          <p>Create your custom landing pages with a targeted marketing message. Flaunt your social media feeds to attract for potential leads and clients. </p>
                      </div>
                      <div class="col-sm-4 ">
                          <h2><i class="fa fa-wrench"></i></h2>
                          <h5>Client Tools</h5>
                          <p>Give access to clients to track and manage their event, vendors, guests, website.</p>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-md-12">
                          &nbsp;
                      </div>
                  </div>

                  <div class="row">
                      <div class="col-md-12">
                          &nbsp;
                      </div>
                  </div>

                  <div class="row ">
                      <div class="col-sm-4">
                          <h2><i class="fa fa-comments"></i></h2>
                          <h5>Collaboration</h5>
                          <p>Collaborate with your clients during the event planning phase. Send and receive notifications to be immediately updated of any change. </p>
                      </div>
                      <div class="col-sm-4">
                          <h2><i class="fa fa-users"></i></h2>
                          <h5>Team Members</h5>
                          <p>Give access to your team members to login and work with clients. Quickly assign roles and permissions for individuals to specific areas of your business.  </p>
                      </div>
                      <div class="col-sm-4 ">
                          <h2><i class="fa fa-dollar"></i>&nbsp;<i class="fa fa-shopping-cart"></i></h2>
                          <h5>Invoices</h5>
                          <p>Create branded invoices online with links to download and email to clients.  </p>
                      </div>
                  </div>

                  <div class="row">
                      <div class="col-md-12">
                          &nbsp;
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-md-12">
                          &nbsp;
                      </div>
                  </div>
                  <div class="row ">
                      <div class="col-sm-3">
                          &nbsp;
                      </div>
                      <div class="col-sm-6">
                          <h2><i class="fa fa-gears"></i> New Features Coming Soon</h2>
                          <p>Online Payments <br>Evernote Integration<br>RSVP by phone<br>Guest reminders by sms and emails.<br>And lots more..</p>
                      </div>
                      <div class="col-sm-3 ">
                          &nbsp;
                      </div>
                  </div>
              </div>
          </div>
          <%
              }
          %>
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
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
</html>