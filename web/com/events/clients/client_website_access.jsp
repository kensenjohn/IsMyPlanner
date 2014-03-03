<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="client_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Client <span id="client_name_title"></span> Website Access</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/clients/client_tab.jsp">
                            <jsp:param name="client_website_access_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row" id="enable_access">
                <div class="col-md-12">
                    <button type="button" class="btn  btn-filled" id="btn_create_login_username_password">Enable Access To Website And Tools</button> &nbsp;&nbsp;
                    <span>You client will be emailed a link to access the website.</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row" id="disable_access">
                <div class="col-md-3">
                    <button type="button" class="btn" id="btn_reset_password"><i class="fa fa-refresh"></i> Reset Password</button>
                </div>
                <div class="col-md-3">
                    <button type="button" class="btn  btn-filled" id="btn_disable_login"><i class="fa fa-ban red"></i> Disable Access To Website</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/clients/clientcontactinfo.js"></script>
<script   type="text/javascript">
    var varClientId = '<%=sClientId%>';
    $(window).load(function() {
        if(varClientId !='') {
            loadClientDetail(varClientId, 'event_info' , populateClientMinimum);
        } else {
            displayMssgBoxAlert("We were unable to process your request. Please try again later (loadClientEvent - 1)", true);
        }
    });

    function loadwebsiteEnableStatus(varClientId, varClientDataType , callbackmethod) {
        var actionUrl = "/proc_load_website_enable_status.aeve";
        var methodType = "POST";
        var dataString = 'client_id=' + varClientId + '&client_datatype='+varClientDataType;
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>