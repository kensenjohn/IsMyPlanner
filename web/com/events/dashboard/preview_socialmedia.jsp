<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.vendors.VendorLandingPageRequestBean" %>
<%@ page import="com.events.bean.vendors.VendorLandingPageFeatureBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.vendors.AccessVendorLandingPage" %>
<%@ page import="com.events.vendors.VendorLandingPageFeature" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>

<div class="page_wrap">
<%
    String sFeatureType = ParseUtil.checkNull(request.getParameter("featuretype"));
    String sVendorLandingPageId = ParseUtil.checkNull(request.getParameter("vendor_landingpage_id"));

    if(!Utility.isNullOrEmpty(sFeatureType) &&  !Utility.isNullOrEmpty(sVendorLandingPageId)    &&
            (Constants.VENDOR_LANDINGPAGE_FEATURETYPE.facebook_url.toString().equalsIgnoreCase(sFeatureType) ||
            Constants.VENDOR_LANDINGPAGE_FEATURETYPE.pinterest_url.toString().equalsIgnoreCase(sFeatureType))  ) {
        VendorLandingPageFeature vendorLandingPageFeature = new VendorLandingPageFeature();

        VendorLandingPageFeatureBean vendorLandingPageFeatureBean = new VendorLandingPageFeatureBean();
        vendorLandingPageFeatureBean.setFeatureType( Constants.VENDOR_LANDINGPAGE_FEATURETYPE.valueOf(sFeatureType)) ;
        vendorLandingPageFeatureBean.setVendorLandingPageId(sVendorLandingPageId);

        VendorLandingPageFeatureBean currentVendorLandingPageFeatureBean = vendorLandingPageFeature.getFeature(vendorLandingPageFeatureBean);

        if(currentVendorLandingPageFeatureBean!=null && !Utility.isNullOrEmpty(currentVendorLandingPageFeatureBean.getVendorLandingPageFeatureId())) {
        %>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <%=currentVendorLandingPageFeatureBean.getValue()%>

                </div>
            </div>
        </div>
    </div>

    <%
        }  else {
    %>

    <%
        }
    }

%>
    </div>

</body>
</html>
