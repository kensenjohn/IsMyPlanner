<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.clients.ClientRequestBean" %>
<%@ page import="com.events.clients.AccessClients" %>
<%@ page import="com.events.bean.clients.ClientBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }
    boolean isLoggedInUserAClient = false;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
            ClientRequestBean clientRequestBean = new ClientRequestBean();
            clientRequestBean.setClientId( loggedInUserBean.getParentId());

            AccessClients accessClients = new AccessClients();
            ClientBean clientBean = accessClients.getClient( clientRequestBean );
            if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getClientId())) {
                isLoggedInUserAClient = true;
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
        <jsp:param name="event_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Event Vendors - <span id="event_title"></span></div>
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
                <div class="col-md-4">
                    <a href="/com/events/event/vendors/edit_event_vendors.jsp?event_id=<%=sEventId%>" class="btn btn-filled">
                        <%
                            if(isLoggedInUserAClient) {
                        %>
                                <span>Recommend Vendors</span>
                        <%
                            } else {
                        %>
                                <span> Assign or Recommend Vendors</span>
                        <%
                            }
                        %>

                    </a>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel-group" id="collapse_assigned_event_vendors">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#collapse_assigned_event_vendors" href="#collapse_assigned">
                                                <i id="assigned_icon" class="fa fa-chevron-circle-right"></i> Assigned Vendors
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse_assigned" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_assigned_event_vendor" >
                                                <thead>
                                                <tr role="row">
                                                    <th class="sorting  col-md-3" role="columnheader">Name</th>
                                                    <th class="sorting" role="columnheader">Type</th>
                                                    <th class="sorting" role="columnheader">Website</th>
                                                    <th class="sorting" role="columnheader">Phone</th>
                                                </tr>
                                                </thead>

                                                <tbody role="alert" id="every_assigned_event_vendor_rows">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel-group" id="collapse_recommended_event_vendors">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#collapse_recommended_event_vendors" href="#collapse_recommended">
                                                <i id="recommended_icon" class="fa fa-chevron-circle-right"></i> Recommended Vendors
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse_recommended" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_recommended_event_vendor" >
                                                <thead>
                                                <tr role="row">
                                                    <th class="sorting  col-md-3" role="columnheader">Name</th>
                                                    <th class="sorting" role="columnheader">Type</th>
                                                    <th class="sorting" role="columnheader">Website</th>
                                                    <th class="sorting" role="columnheader">Phone</th>
                                                </tr>
                                                </thead>

                                                <tbody role="alert" id="every_recommended_event_vendor_rows">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>


             <%--<div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel-group" id="collapse_shortlisted_event_vendors">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#collapse_shortlisted_event_vendors" href="#collapse_shortlisted">
                                                <i id="shortlisted_icon" class="fa fa-chevron-circle-right"></i> Shortlisted Vendors
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse_shortlisted" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_shortlisted_event_vendor" >
                                                <thead>
                                                <tr role="row">
                                                    <th class="sorting  col-md-3" role="columnheader">Name</th>
                                                    <th class="sorting" role="columnheader">Type</th>
                                                    <th class="sorting" role="columnheader">Website</th>
                                                    <th class="sorting" role="columnheader">Phone</th>
                                                </tr>
                                                </thead>

                                                <tbody role="alert" id="every_shortlisted_event_vendor_rows">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>--%>
        </div>
    </div>
</div>
</body>
<form id="frm_load_event_vendors">
    <input type="hidden" name="event_id" id="load_event_id" value="<%=sEventId%>">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/collapse.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        $('#collapse_assigned').on('hide.bs.collapse', function () {
            $('#assigned_icon').removeClass("fa fa-chevron-circle-down");
            $('#assigned_icon').addClass("fa fa-chevron-circle-right");
        })
        $('#collapse_assigned').on('show.bs.collapse', function () {
            $('#assigned_icon').removeClass("fa fa-chevron-circle-right");
            $('#assigned_icon').addClass("fa fa-chevron-circle-down");
        })

        $('#collapse_recommended').on('hide.bs.collapse', function () {
            $('#recommended_icon').removeClass("fa fa-chevron-circle-down");
            $('#recommended_icon').addClass("fa fa-chevron-circle-right");
        })
        $('#collapse_recommended').on('show.bs.collapse', function () {
            $('#recommended_icon').removeClass("fa fa-chevron-circle-right");
            $('#recommended_icon').addClass("fa fa-chevron-circle-down");
        })

        /*$('#collapse_shortlisted').on('hide.bs.collapse', function () {
            $('#shortlisted_icon').removeClass("fa fa-chevron-circle-down");
            $('#shortlisted_icon').addClass("fa fa-chevron-circle-right");
        })
        $('#collapse_shortlisted').on('show.bs.collapse', function () {
            $('#shortlisted_icon').removeClass("fa fa-chevron-circle-right");
            $('#shortlisted_icon').addClass("fa fa-chevron-circle-down");
        })*/

        loadEventVendorList(populateEventVendorList)
    });
    function loadEventVendorList(callbackmethod) {
        var actionUrl = "/proc_load_event_vendors.aeve";
        var methodType = "POST";
        var dataString = $('#frm_load_event_vendors').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateEventVendorList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfEventVendors = jsonResponseObj.num_of_event_vendors;
                    if(varNumOfEventVendors!=undefined && varNumOfEventVendors>0){
                        processEventVendorsList(varNumOfEventVendors, jsonResponseObj.event_vendors );
                    }
                    initializeTable('every_assigned_event_vendor');
                    initializeTable('every_recommended_event_vendor');
                    //initializeTable('every_shortlisted_event_vendor');
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }

    function processEventVendorsList(varNumOfEventVendors, everEventVendorsList) {
        for(i=0;i<varNumOfEventVendors;i++){
            var varEveryEventVendorBean = everEventVendorsList[i];
            var varEventId =  varEveryEventVendorBean.event_id;
            var varEventVendorBean = varEveryEventVendorBean.event_vendor_bean;

            var varEventVendorId =  varEventVendorBean.eventvendor_id;
            var varVendorId =  varEventVendorBean.vendor_id;

            var varPartnerVendorBean = varEveryEventVendorBean.every_partner_vendor_bean;

            var varEventVendorName = varPartnerVendorBean.name;
            var varEventVendorEmail = varPartnerVendorBean.email;
            var varEventVendorPhone = varPartnerVendorBean.phone;
            var varEventVendorWebsite = varPartnerVendorBean.website;
            var varEventVendorType =  varPartnerVendorBean.type;

            var varIsAssignedToEvent = varEveryEventVendorBean.is_assigned_to_event;
            var varIsRecommendedForEvent = varEveryEventVendorBean.is_recommended_for_event;
            var varIsShortListedForEvent = varEveryEventVendorBean.is_shortlisted_for_event;

            var varPartnerVendorId =  varPartnerVendorBean.partner_vendor_id;

            var rowEveryEventVendor= $('<tr id="row_'+varPartnerVendorId+'" ></tr>');
            rowEveryEventVendor.append(
                    '<td>'+varEventVendorName +'</td>'+
                            '<td>'+varEventVendorType +'</td>' +
                            '<td>'+varEventVendorWebsite +'</td>' +
                            '<td><a href="tel:'+varEventVendorPhone +'">'+varEventVendorPhone +'</a></td>' );
                            //'<td  class="center" >'+ createButtons(varPartnerVendorId , varAssigedAction , varRecommendAction) +'</td>');
            if(varIsAssignedToEvent)  {
                $('#every_assigned_event_vendor_rows').append(rowEveryEventVendor);
            } else if( varIsRecommendedForEvent ) {
                $('#every_recommended_event_vendor_rows').append(rowEveryEventVendor);
            } else if( varIsShortListedForEvent) {
                $('#every_shortlisted_event_vendor_rows').append(rowEveryEventVendor);
            }

        }
    }

    function initializeTable(varTableId){

        var objEventVendorTable =  $('#'+varTableId).dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                {"bSortable": true,"sClass":"col-md-3"},
                null,
                null,
                null
            ]
        });
        return objEventVendorTable;
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>