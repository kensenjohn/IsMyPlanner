<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link href="/css/font-awesome.min.css" rel="stylesheet">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
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
            <div class="page-title">Event Vendor Selection - <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <%if(loadEventInfo) {%>
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_vendors_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_potential_partner_vendor" >
                        <thead>
                        <tr role="row">
                            <th class="sorting  col-md-3" role="columnheader">Name</th>
                            <th class="sorting" role="columnheader">Type</th>
                            <th class="sorting" role="columnheader">Website</th>
                            <th class="sorting" role="columnheader">Phone</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>

                        <tbody role="alert" id="every_potential_partner_vendor_rows">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h4>Prefer a different vendor?</h4>
                                    </div>
                                </div>
                                <form method="post" id="frm_manual_vendor_recommend">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="partner_vendorName" class="form_label">Business Name</label><span class="required"> *</span>
                                                <input type="email" class="form-control" id="partner_vendorName" name="partner_vendorName" placeholder="Business Name">
                                            </div>
                                            <div class="col-md-6">
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
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="partner_vendorWorkPhone" class="form_label">Phone</label>
                                                <input type="tel" class="form-control" id="partner_vendorWorkPhone" name="partner_vendorWorkPhone" placeholder="Phone">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="partner_vendorWebsite" class="form_label">Website</label>
                                                <input type="email" class="form-control" id="partner_vendorWebsite" name="partner_vendorWebsite" placeholder="Website">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                &nbsp;
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-8">
                                                <button  type="button" class="btn  btn-filled btn-sm" id="btn_create_email">
                                                    <span>Create New Email</span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_potential_event_vendors">
    <input type="hidden" id="ld_pot_event_vendor_event_id" name="event_id" value="<%=sEventId%>" />
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        loadEventPartnerVendors(populateEventPartnerVendors)
    });
    function loadEventPartnerVendors(callbackmethod) {
        var actionUrl = "/proc_load_all_event_partner_vendors.aeve";
        var methodType = "POST";
        var dataString = $('#frm_load_potential_event_vendors').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateEventPartnerVendors(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfPotentialEventVendors = jsonResponseObj.num_of_potential_event_vendors;
                    if(varNumOfPotentialEventVendors!=undefined && varNumOfPotentialEventVendors>0){
                        processPotentialEventVendorsList(varNumOfPotentialEventVendors, jsonResponseObj.potential_event_vendors );
                    }
                    initializeTable();
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }

    }
    function processPotentialEventVendorsList(varNumOfPotentialEventVendors , everyPotEventVendorList  ) {
        for(i=0;i<varNumOfPotentialEventVendors;i++){
            var varPotEventVendorBean = everyPotEventVendorList[i];
            var varEventVendorBean = varPotEventVendorBean.event_vendor_bean;

            var varEventVendorId =  varEventVendorBean.eventvendor_id;
            var varVendorId =  varEventVendorBean.vendor_id;

            var varPartnerVendorBean = varPotEventVendorBean.every_partner_vendor_bean;

            var varEventVendorName = varPartnerVendorBean.name;
            var varEventVendorEmail = varPartnerVendorBean.email;
            var varEventVendorPhone = varPartnerVendorBean.phone;
            var varEventVendorWebsite = varPartnerVendorBean.website;
            var varEventVendorType =  varPartnerVendorBean.type;

            var varIsAssignedToEvent = varPotEventVendorBean.is_assigned_to_event;
            var varIsRecommendedForEvent = varPotEventVendorBean.is_recommended_for_event;
            var varIsShortListedForEvent = varPotEventVendorBean.is_recommended_for_event;

            var varPartnerVendorId =  varPartnerVendorBean.partner_vendor_id;


            var rowEveryEventVendor= $('<tr id="row_'+varPartnerVendorId+'" ></tr>');
            rowEveryEventVendor.append(
                    '<td>'+varEventVendorName +'</td>'+
                            '<td>'+varEventVendorType +'</td>' +
                            '<td>'+varEventVendorWebsite +'</td>' +
                            '<td>'+varEventVendorEmail +'</td>' +
                            '<td></td>');
                            //'<td  class="center" >'+ createButtons(varPartnerVendorId) +'</td>');
            $('#every_potential_partner_vendor_rows').append(rowEveryEventVendor);

            //addDeleteClickEvent(varPartnerVendorId,varPartnerVendorName, i)
        }
    }
    function initializeTable(){

        objPotentialEventVendorTable =  $('#every_potential_partner_vendor').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>