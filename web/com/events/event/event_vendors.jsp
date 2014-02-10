<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
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
                        <span> Assign or Recommend Vendors</span>
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
                                                    <th class="center" role="columnheader"></th>
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
                                                    <th class="center" role="columnheader"></th>
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


            <div class="row">
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
                                                    <th class="center" role="columnheader"></th>
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
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
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

        $('#collapse_shortlisted').on('hide.bs.collapse', function () {
            $('#shortlisted_icon').removeClass("fa fa-chevron-circle-down");
            $('#shortlisted_icon').addClass("fa fa-chevron-circle-right");
        })
        $('#collapse_shortlisted').on('show.bs.collapse', function () {
            $('#shortlisted_icon').removeClass("fa fa-chevron-circle-right");
            $('#shortlisted_icon').addClass("fa fa-chevron-circle-down");
        })

        loadEventVendorList(populateEventVendorList)
    });
    function loadEventVendorList(callbackmethod) {
        var actionUrl = "/proc_load_event_vendors.aeve";
        var methodType = "POST";
        var dataString = $('#frm_load_potential_event_vendors').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>