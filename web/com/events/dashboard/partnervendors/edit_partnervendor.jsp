<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    String sPartnerVendorId = ParseUtil.checkNull(request.getParameter("vendor_id"));
    String sPartnerId = ParseUtil.checkNull(request.getParameter("partner_id"));
    boolean loadPartnerVendor = false;
    if(!Utility.isNullOrEmpty(sPartnerVendorId)) {
        loadPartnerVendor = true;
    }
%>

<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="dashboard_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Vendor</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="vendors_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <form method="post" id="frm_save_team_members">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="partner_vendorName" class="form_label">Business Name</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="partner_vendorName" name="partner_vendorName" placeholder="Business Name">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="partner_vendorWebsite" class="form_label">Website</label>
                                    <input type="email" class="form-control" id="partner_vendorWebsite" name="partner_vendorWebsite" placeholder="Website">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="partner_vendorWorkPhone" class="form_label">Phone</label>
                                    <input type="tel" class="form-control" id="partner_vendorWorkPhone" name="partner_vendorWorkPhone" placeholder="Phone">
                                </div>
                                <div class="col-md-4">
                                    <label for="partner_vendorCellPhone" class="form_label">Cell Phone</label>
                                    <input type="tel" class="form-control" id="partner_vendorCellPhone" name="partner_vendorCellPhone" placeholder="Cell Phone">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="partner_vendorFirstName" class="form_label">First Name</label>
                                    <input type="text" class="form-control" id="partner_vendorFirstName" name="partner_vendorFirstName" placeholder="First Name">
                                </div>
                                <div class="col-md-4">
                                    <label for="partner_vendorLastName" class="form_label">Last Name</label>
                                    <input type="text" class="form-control" id="partner_vendorLastName" name="partner_vendorLastName" placeholder="Last Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="partner_vendorEmail" class="form_label">Email</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="partner_vendorEmail" name="partner_vendorEmail" placeholder="Email">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <button type="button" class="btn  btn-filled" id="btn_save_partner_vendor">Save Changes</button>
                            </div>
                        </div>
                        <input type="hidden"  id="partner_vendor_id" name="partner_vendor_id" value="">
                        <input type="hidden"  id="partner_id" name="partner_id" value="">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="frm_load_partner_vendor">
    <input type="hidden" name="partner_vendor_id" value="<%=sPartnerVendorId%>">
    <input type="hidden" name="partner_id" value="<%=sPartnerId%>">
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    $(window).load(function() {

    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>