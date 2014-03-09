<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
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
            <div class="page-title">Dashboard</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="dashboard_active" value="active"/>
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
                <div class="col-md-4" >
                    <div class="info_tile" id="notifications_tile" style="cursor:pointer">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <span class="glyphicon glyphicon-bell"></span>
                                </div>
                                <div>
                                    <h3 id="num_of_notification">0</h3>
                                    <h5>Notifications</h5>
                                    <h7 style="text-decoration: underline;">View</h7>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info_tile red">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <span class="glyphicon glyphicon-signal"></span>
                                </div>
                                <div>
                                    <h3>35</h3>
                                    <h5>Events This Week</h5>
                                    <h7>&nbsp;</h7>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info_tile green">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <span class="glyphicon glyphicon-envelope"></span>
                                </div>
                                <div>
                                    <h3>34</h3>
                                    <h5>Emails Scheduled For Today</h5>
                                    <h7>&nbsp;</h7>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class = row>
                <div class="col-md-6">
                    &nbsp;
                </div>
            </div>
            <div class = row>
                <div class="col-md-6">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <h4>Notifications</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <h4>TODO</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_view_notifiations">

</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.colorbox-min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        loadDashboardSummary(populateDashboardSummary);

        $( '#notifications_tile').click( function(){
            //$.colorbox({href:varImagePath});
            $.colorbox({
                href:'view_notifications.jsp',
                iframe:true,
                innerWidth: '80%',
                innerHeight: '60%',
                scrolling: true,
                onClosed : function() {
                    loadDashboardSummary(populateDashboardSummary)
                }});
        });
    });
    function loadDashboardSummary(callbackmethod) {
        var actionUrl = "/proc_load_dashboard_summary.aeve";
        var methodType = "POST";
        var dataString = '';
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateDashboardSummary(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varDashboardSummary = jsonResponseObj.dashboard_summary;

                    if(varDashboardSummary!=undefined) {
                        $('#num_of_notification').text(varDashboardSummary.num_of_unread_notifications);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateClientList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientList - 2)", true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
