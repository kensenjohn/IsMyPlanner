<%@ page import="com.events.common.ParseUtil" %>
<%
    String sDashBoardActive = ParseUtil.checkNull(request.getParameter("dashboard_active"));
    String sDashBoardOurWebsiteActive = ParseUtil.checkNull(request.getParameter("our_website_active"));
    String sDashBoardOurTeamActive = ParseUtil.checkNull(request.getParameter("our_team_active"));
    String sDashBoardRolesAndPermissionsActive = ParseUtil.checkNull(request.getParameter("roles_and_permissions_active"));
    String sDashBoardVendorsActive = ParseUtil.checkNull(request.getParameter("vendors_active"));
%>
<ul class="tabs">
    <li class="<%=sDashBoardActive%>"><a href="/com/events/dashboard/dashboard.jsp">Summary</a></li>
    <li class="<%=sDashBoardOurWebsiteActive%>"><a href="/com/events/dashboard/our_website.jsp">Website</a></li>
    <li class="<%=sDashBoardOurTeamActive%>"><a href="/com/events/dashboard/our_team.jsp">Team</a></li>
    <li class="<%=sDashBoardRolesAndPermissionsActive%>"><a href="/com/events/dashboard/roles.jsp">Roles and Permissions</a></li>
    <li class="<%=sDashBoardVendorsActive%>"><a href="/com/events/dashboard/roles.jsp">Vendors</a></li>
</ul>