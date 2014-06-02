<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.ParentTypeBean" %>
<%@ page import="com.events.users.AccessUsers" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Perm" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<%
    String sessionId = "NA";
    if(session!=null){
        sessionId = ParseUtil.checkNull(session.getId());
    }
    UserBean loggedInUserBean = new UserBean();
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
    }

    AccessUsers accessUsers = new AccessUsers();
    ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser(loggedInUserBean);
    boolean isLoggedInUserVendor = parentTypeBean.isUserAVendor();
    boolean isLoggedInUserClient = parentTypeBean.isUserAClient();

    CheckPermission checkPermission = null;
    if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
        checkPermission = new CheckPermission(loggedInUserBean);
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
                                    <i class="fa fa-bell-o"></i>
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
                <%
                    if (isLoggedInUserVendor){
                        boolean canUserAccessClientsTab = false;
                        boolean canUserManageTeamMembers = false;
                        if(checkPermission!=null && checkPermission.can(Perm.ACCESS_CLIENTS_TAB)) {
                            canUserAccessClientsTab = true;
                        }
                        if(checkPermission!=null && checkPermission.can(Perm.MANAGE_TEAM_MEMBERS)) {
                            canUserManageTeamMembers = true;
                        }
                %>
                        <div class="col-md-4">
                            <%if(canUserAccessClientsTab) {%>
                                <a href="/com/events/clients/clients.jsp" style="text-decoration: none;">
                            <% } %>
                                    <div class="info_tile red" id="clients_tile" style="cursor:pointer;">
                                        <div class="widget">
                                            <div class="content">
                                                <div class="icon_display">
                                                    <i class="fa fa-group"></i>
                                                </div>
                                                <div>
                                                    <h3 id="num_of_clients">0</h3>
                                                    <h5>Clients</h5>
                                                    <h7 style="text-decoration: underline;">View</h7>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                            <%if(canUserAccessClientsTab) {%>
                                </a>
                            <% } %>
                        </div>
                        <%

                        %>
                        <div class="col-md-4">
                            <%if(canUserManageTeamMembers) {%>
                                <a href="/com/events/dashboard/our_team.jsp" style="text-decoration: none;">
                            <% } %>
                                    <div class="info_tile green"  id="team_members_tile" >
                                        <div class="widget">
                                            <div class="content">
                                                <div class="icon_display">
                                                    <i class="fa fa-rocket"></i>
                                                </div>
                                                <div>
                                                    <h3 id="num_of_team_members">0</h3>
                                                    <h5>Team Members</h5>
                                                    <h7 style="text-decoration: underline;">View</h7>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                            <%if(canUserAccessClientsTab) {%>
                                </a>
                            <% } %>
                        </div>
                <%
                    }
                %>
                <%
                if (isLoggedInUserClient || isLoggedInUserVendor){
                    boolean canUserAccessEventsTab = false;
                    if(checkPermission!=null && checkPermission.can(Perm.ACCESS_EVENTS_TAB)) {
                        canUserAccessEventsTab = true;
                    }
                %>
                    <div class="col-md-4">
                        <%if(canUserAccessEventsTab) {%>
                        <a href="/com/events/event/events.jsp" style="text-decoration: none;">
                            <% } %>
                            <div class="info_tile green"  id="events_tile" >
                                <div class="widget">
                                    <div class="content">
                                        <div class="icon_display">
                                            <i class="fa fa-desktop"></i>
                                        </div>
                                        <div>
                                            <h3 id="num_of_events">0</h3>
                                            <h5>Events</h5>
                                            <h7 style="text-decoration: underline;">View</h7>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%if(canUserAccessEventsTab) {%>
                        </a>
                        <% } %>
                    </div>
                <%
                    }
                %>

            </div>
            <div class = row>
                <div class="col-md-6">
                    &nbsp;
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
        invokeMixpanel("dashboard", "<%=sessionId%>");
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
                        $('#num_of_clients').text(varDashboardSummary.num_of_clients);
                        $('#num_of_team_members').text(varDashboardSummary.num_of_team_members);
                        $('#num_of_events').text(varDashboardSummary.num_of_events);
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
