<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.vendors.website.AccessVendorWebsite" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteBean" %>
<%@ page import="com.events.bean.vendors.VendorBean" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteFeatureBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="java.util.HashMap" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    boolean isVendorSite = false;
    HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmVendorWebsiteFeatureBean = new HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE, VendorWebsiteFeatureBean>();
    if( session.getAttribute("SUBDOMAIN_VENDOR") != null && session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE") !=null ) {
        isVendorSite = true;
        VendorBean vendorBean = (VendorBean)  session.getAttribute("SUBDOMAIN_VENDOR");
        VendorWebsiteBean vendorWebsiteBean = (VendorWebsiteBean) session.getAttribute("SUBDOMAIN_VENDOR_WEBSITE");
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())){

            AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
            hmVendorWebsiteFeatureBean =  accessVendorWebsite.getPublishedFeaturesForLandingPage(vendorWebsiteBean);
        }
    }

    String sContact = Constants.EMPTY;
    boolean isShowContact = false;
    if(isVendorSite && hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteShowContactFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.show_footer_contact);
        if(vendorWebsiteShowContactFeatureBean!=null && ParseUtil.sTob(vendorWebsiteShowContactFeatureBean.getValue()))  {
            VendorWebsiteFeatureBean vendorWebsiteContactFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_footer_contact);
            if(vendorWebsiteContactFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteContactFeatureBean.getValue())){
                sContact =   ParseUtil.checkNull(vendorWebsiteContactFeatureBean.getValue());
                isShowContact = true;
            }
        }
    }
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="event_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Contact</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12" >
                    <%
                        if(isVendorSite) {
                            if(isShowContact) {
                    %>
                                <div class="row">
                                    <div class="col-md-8" >
                                        <%=sContact%>
                                    </div>
                                </div>
                    <%
                        }
                    } else if(!isVendorSite) {
                    %>
                            <div class="row">
                                <div class="offset_0_5 col-md-8" >
                                    <span>You may contact us through email or phone for any question regarding our product, issues or cancellations.</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="span2" >
                                    &nbsp;
                                </div>
                            </div>
                            <div class="row">
                                <div class="offset_0_5 col-md-7" >
                                    <h4>Email:</h4><span> kjohn@smarasoft.com</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="span2" >
                                    &nbsp;
                                </div>
                            </div>
                            <div class="row">
                                <div class="offset_0_5 col-md-7" >
                                    <h4>Phone:</h4><span> (267) 250 2719</span>
                                </div>
                            </div>

                            <div class="row">
                                <div class="span2" >
                                    &nbsp;
                                </div>
                            </div>
                            <div class="row">
                                <div class="offset_0_5 col-md-7" >
                                    <h4>Address</h4>
                                </div>
                            </div>
                            <div class="row">
                                <div class="offset_0_5 col-md-7" >
                                    <span>5316 Carnaby St, Suite 155</span>  <br>
                                    <span>Irving, TX 75038</span>
                                </div>
                            </div>
                    <%
                        }
                    %>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>