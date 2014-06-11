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
            hmVendorWebsiteFeatureBean =  accessVendorWebsite.getPublishedFeaturesForWebPages(vendorWebsiteBean);
        }
    }

    String sAboutUs = Constants.EMPTY;
    boolean isShowAboutUs = false;
    if(isVendorSite && hmVendorWebsiteFeatureBean!=null && !hmVendorWebsiteFeatureBean.isEmpty()) {
        VendorWebsiteFeatureBean vendorWebsiteShowAboutUsFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.show_footer_about_us);
        if(vendorWebsiteShowAboutUsFeatureBean!=null && ParseUtil.sTob(vendorWebsiteShowAboutUsFeatureBean.getValue()))  {
            VendorWebsiteFeatureBean vendorWebsiteAboutUsFeatureBean =  hmVendorWebsiteFeatureBean.get(Constants.VENDOR_WEBSITE_FEATURETYPE.published_footer_about_us);
            if(vendorWebsiteAboutUsFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteAboutUsFeatureBean.getValue())){
                sAboutUs =   ParseUtil.checkNull(vendorWebsiteAboutUsFeatureBean.getValue());
                isShowAboutUs = true;
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
        <jsp:param name="none" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">About</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-8" >
                            <%
                                if(isVendorSite) {
                                    if(isShowAboutUs) {
                            %>
                                        <%=sAboutUs%>
                            <%
                                    }
                                } else if(!isVendorSite) {
                            %>
                                    <p><h5>IsMyPlanner.com is an essential hub and portal for Event Planners and Wedding Planners.</h5></p>
                                    <p>
                                        <ul  style="list-style: none;" >
                                            <li  style="list-style: none;" ><h6><i class="fa fa-check-square-o"></i> &nbsp;&nbsp;Create branded sites targeted for their clients</h6></li>
                                            <li  style="list-style: none;" ><h6><i class="fa fa-check-square-o"></i> &nbsp;&nbsp;Manage clients, vendors and events all in one place</h6></li>
                                            <li  style="list-style: none;" ><h6><i class="fa fa-check-square-o"></i> &nbsp;&nbsp;Track progress and plans</h6></li>
                                            <li  style="list-style: none;" ><h6><i class="fa fa-check-square-o"></i> &nbsp;&nbsp;Collaborate and communicate with clients</h6></li>
                                            <li  style="list-style: none;" ><h6><i class="fa fa-check-square-o"></i> &nbsp;&nbsp;Give clients access to planning tools(website creation, rsvp management, guest list tracker etc.)</h6></li>
                                            <li  style="list-style: none;" ><h6><i class="fa fa-check-square-o"></i> &nbsp;&nbsp;Your clients can build website with modern themes and high resolution images for every device</h6></li>
                                        </ul>
                                    </p>

                            <%
                                }
                            %>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>