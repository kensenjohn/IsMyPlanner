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
    String sPartnerVendorId = ParseUtil.checkNull(request.getParameter("partner_vendor_id"));
    String sPartnerId = ParseUtil.checkNull(request.getParameter("partner_id"));
    String sVendorId = ParseUtil.checkNull(request.getParameter("partner_id"));
    boolean loadPartnerVendor = false;
    String breadCrumbPageTitle = "Vendor - Add New";
    if(!Utility.isNullOrEmpty(sPartnerVendorId)) {
        loadPartnerVendor = true;
        breadCrumbPageTitle = "Vendor - Edit";
    }
%>

<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="dashboard_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title"><%=breadCrumbPageTitle%></div>
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
                    <form method="post" id="frm_save_partner_vendor">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="partner_vendorName" class="form_label">Business Name</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="partner_vendorName" name="partner_vendorName" placeholder="Business Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="partner_vendorWorkPhone" class="form_label">Phone</label><span class="required"> *</span>
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
                                <div class="col-md-8">
                                    <label for="partner_vendorType" class="form_label">Vendor Type</label><span class="required"> *</span>
                                    <select class="form-control" id="partner_vendorType" name="partner_vendorType">
                                        <option value="">Select Vendor Type</option>
                                        <%
                                            for(Constants.VENDOR_TYPE vendorType : Constants.VENDOR_TYPE.values()){
                                        %>
                                                <option value="<%=vendorType.toString()%>"><%=vendorType.getText()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="partner_vendorPrice" class="form_label">Approximate Price</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="partner_vendorPrice" name="partner_vendorPrice" placeholder="Approximate Price">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="partner_vendorEmail" class="form_label">Email</label>
                                    <input type="email" class="form-control" id="partner_vendorEmail" name="partner_vendorEmail" placeholder="Email">
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
                                <div class="col-md-4">
                                    <label for="partner_vendorAddress1" class="form_label">Address 1</label>
                                    <input type="text" class="form-control" id="partner_vendorAddress1" name="partner_vendorAddress1" placeholder="Address 1">
                                </div>
                                <div class="col-md-4">
                                    <label for="partner_vendorAddress2" class="form_label">Address 2</label>
                                    <input type="text" class="form-control" id="partner_vendorAddress2" name="partner_vendorAddress2" placeholder="Address 2">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="partner_vendorCity" class="form_label">City</label>
                                    <input type="text" class="form-control" id="partner_vendorCity" name="partner_vendorCity" placeholder="City">
                                </div>
                                <div class="col-md-4">
                                    <label for="partner_vendorState" class="form_label">State</label>
                                    <input type="text" class="form-control" id="partner_vendorState" name="partner_vendorState" placeholder="State">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="partner_vendorPostalCode" class="form_label">Postal Code</label>
                                    <input type="text" class="form-control" id="partner_vendorPostalCode" name="partner_vendorPostalCode" placeholder="Postal Code">
                                </div>
                                <div class="col-md-4">
                                    <label for="partner_vendorCountry" class="form_label">Country</label>
                                    <input type="text" class="form-control" id="partner_vendorCountry" name="partner_vendorCountry" placeholder="Country">
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
                        <input type="hidden"  id="vendor_id" name="vendor_id" value="">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="frm_load_partner_vendor">
    <input type="hidden"  id="load_partner_vendor_id"  name="partner_vendor_id" value="<%=sPartnerVendorId%>">
    <input type="hidden" id="load_partner_id"  name="partner_id" value="<%=sPartnerId%>">
    <input type="hidden" id="load_vendor_id"  name="vendor_id" value="<%=sVendorId%>">
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    $(window).load(function() {
        var varLoadPartnerVendor = <%=loadPartnerVendor%>
        if(varLoadPartnerVendor){
            var varPartnerVendorId = '<%=sPartnerVendorId%>';
            loadPartnerVendor(populatePartnerVendor,varPartnerVendorId);
        }
        $('#btn_save_partner_vendor').click(function(){
            savePartnerVendor(getResult);
        })
    });
    function loadPartnerVendor(callbackmethod, partnerVendorId) {
        if(partnerVendorId!=undefined) {
            var actionUrl = "/proc_load_partner_vendor.aeve";
            var methodType = "POST";
            var dataString = $('#frm_load_partner_vendor').serialize();
            makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
        }
    }

    function populatePartnerVendor (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                var varIsMessageExist = varResponseObj.is_message_exist;
                if(varIsMessageExist == true) {
                    var jsonResponseMessage = varResponseObj.messages;
                    var varArrErrorMssg = jsonResponseMessage.error_mssg;
                    displayMssgBoxMessages(varArrErrorMssg, true);
                }

            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    processPartnerVendor(jsonResponseObj.partner_vendor_bean);
                    processPartnerVendorFeatures(jsonResponseObj.partner_vendor_features);
                    var varVendorBean = jsonResponseObj.vendor_bean;

                    if(varVendorBean!=undefined) {
                        $('#partner_vendorName').val(varVendorBean.vendor_name);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateTeamMember - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateTeamMember - 2)", true);
        }
    }
    function processPartnerVendor(varPartnerVendorBean) {
        if(varPartnerVendorBean != undefined ) {

            $('#partner_vendor_id').val(varPartnerVendorBean.partner_vendor_id);
            $('#partner_id').val(varPartnerVendorBean.partner_id);
            $('#vendor_id').val(varPartnerVendorBean.vendor_id);

            $('#load_partner_vendor_id').val(varPartnerVendorBean.partner_vendor_id);
            $('#load_partner_id').val(varPartnerVendorBean.partner_id);
            $('#load_vendor_id').val(varPartnerVendorBean.vendor_id);
        }
    }
    function processPartnerVendorFeatures(varPartnerVendorFeaturesBean) {
        if(varPartnerVendorFeaturesBean!=undefined) {

            $('#partner_vendorWebsite').val(varPartnerVendorFeaturesBean.website);
            $('#partner_vendorWorkPhone').val(varPartnerVendorFeaturesBean.partner_work_phone);
            $('#partner_vendorCellPhone').val(varPartnerVendorFeaturesBean.partner_cell_name);
            $('#partner_vendorFirstName').val(varPartnerVendorFeaturesBean.partner_first_name);
            $('#partner_vendorLastName').val(varPartnerVendorFeaturesBean.partner_last_name);
            $('#partner_vendorEmail').val(varPartnerVendorFeaturesBean.partner_email);


            $('#partner_vendorAddress1').val(varPartnerVendorFeaturesBean.partner_address_1);
            $('#partner_vendorAddress2').val(varPartnerVendorFeaturesBean.partner_address_2);
            $('#partner_vendorCity').val(varPartnerVendorFeaturesBean.partner_city);
            $('#partner_vendorState').val(varPartnerVendorFeaturesBean.partner_state);
            $('#partner_vendorPostalCode').val(varPartnerVendorFeaturesBean.partner_zipcode);
            $('#partner_vendorCountry').val(varPartnerVendorFeaturesBean.partner_country);


            $('#partner_vendorType').val(varPartnerVendorFeaturesBean.partner_vendor_type);
            $('#partner_vendorPrice').val(varPartnerVendorFeaturesBean.price);

        }
    }
    function savePartnerVendor(callbackmethod) {
        var actionUrl = "/proc_save_partner_vendor.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_partner_vendor").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varPartnerVendorBean = jsonResponseObj.partner_vendor_bean;

                        $('#partner_vendor_id').val(varPartnerVendorBean.partner_vendor_id);
                        $('#partner_id').val(varPartnerVendorBean.partner_id);
                        $('#vendor_id').val(varPartnerVendorBean.vendor_id);

                        $('#load_partner_vendor_id').val(varPartnerVendorBean.partner_vendor_id);
                        $('#load_partner_id').val(varPartnerVendorBean.partner_id);
                        $('#load_vendor_id').val(varPartnerVendorBean.vendor_id);

                    }
                }
                displayMssgBoxAlert('Your changes were saved successfully.', false);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>