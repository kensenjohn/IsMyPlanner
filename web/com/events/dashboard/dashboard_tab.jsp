<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Perm" %>
<%
    String sDashBoardActive = ParseUtil.checkNull(request.getParameter("dashboard_active"));
    String sDashBoardOurWebsiteActive = ParseUtil.checkNull(request.getParameter("our_website_active"));
    String sDashBoardOurTeamActive = ParseUtil.checkNull(request.getParameter("our_team_active"));
    String sDashBoardRolesAndPermissionsActive = ParseUtil.checkNull(request.getParameter("roles_and_permissions_active"));
    String sDashBoardVendorsActive = ParseUtil.checkNull(request.getParameter("vendors_active"));
    String sDashBoardInvoicesActive = ParseUtil.checkNull(request.getParameter("invoices_active"));
    String sDashBoardFilesActive = ParseUtil.checkNull(request.getParameter("files_active"));
    String sDashBoardTodoActive = ParseUtil.checkNull(request.getParameter("todo_active"));
    String sDashBoardChecklistManagementActive = ParseUtil.checkNull(request.getParameter("checklist_management_active"));
    String sDashBoardConversationsActive = ParseUtil.checkNull(request.getParameter("conversations_active"));

    UserBean loggedInUserBean = new UserBean();
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
    }
    CheckPermission checkPermission = null;
    if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
        checkPermission = new CheckPermission(loggedInUserBean);
    }
%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <!-- Brand and toggle get grouped for better mobile display -->
            <button type="button" class="navbar-toggle pull-left" data-toggle="collapse" data-target="#dashboard_tab_collapse_1">
                <span class="sr-only">Toggle Dashboard Tab</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="dashboard_tab_collapse_1">
                <ul class="nav navbar-nav  navbar-right">
                    <li class="<%=sDashBoardActive%>"><a href="/com/events/dashboard/dashboard.jsp">Summary</a></li>

                    <%if(checkPermission!=null && checkPermission.can(Perm.MANAGE_VENDOR_WEBSITE)) {%>
                        <li class="<%=sDashBoardOurWebsiteActive%>"><a href="/com/events/dashboard/our_website.jsp">Website</a></li>
                    <% } %>

                    <% if(checkPermission!=null && checkPermission.can(Perm.MANAGE_TEAM_MEMBERS)) {  %>
                        <li class="<%=sDashBoardOurTeamActive%>"><a href="/com/events/dashboard/our_team.jsp">Team</a></li>
                    <% } %>

                    <% if(checkPermission!=null && checkPermission.can(Perm.MANAGE_ROLE_PERMISSION)) {  %>
                        <li class="<%=sDashBoardRolesAndPermissionsActive%>"><a href="/com/events/dashboard/roles.jsp">Roles and Permissions</a></li>
                    <% } %>

                    <% if(checkPermission!=null && checkPermission.can(Perm.MANAGE_PARTNER_VENDORS)) {  %>
                        <li class="<%=sDashBoardVendorsActive%>"><a href="/com/events/dashboard/partner_vendors.jsp">Vendors</a></li>
                    <% } %>

                    <li class="<%=sDashBoardInvoicesActive%>"><a href="/com/events/dashboard/invoices.jsp">Invoices</a></li>
                    <li class="<%=sDashBoardFilesActive%>"><a href="/com/events/dashboard/files.jsp">Files</a></li>
                    <li class="<%=sDashBoardTodoActive%>"><a href="/com/events/dashboard/todos.jsp">Todo</a></li>

                    <%if(checkPermission!=null && checkPermission.can(Perm.MANAGE_CHECKLIST_TEMPLATE_TAB)) {%>
                        <!-- <li class="<%=sDashBoardChecklistManagementActive%>"><a href="/com/events/dashboard/manage_checklist.jsp">Checklist</a></li> -->
                    <% } %>
                    <li class="<%=sDashBoardConversationsActive%>"><a href="/com/events/common/conversation/conversations.jsp">Conversations</a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div>
    </div>
</nav>