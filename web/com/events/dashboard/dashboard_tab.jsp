<%@ page import="com.events.common.ParseUtil" %>
<%
    String sDashBoardActive = ParseUtil.checkNull(request.getParameter("dashboard_active"));
    String sDashBoardManageLandingPageActive = ParseUtil.checkNull(request.getParameter("dashboard_manage_landingpage_active"));
%>
<ul class="tabs">
    <li class="<%=sDashBoardActive%>"><a href="/com/events/dashboard/dashboard.jsp">Summary</a></li>
    <li class="<%=sDashBoardManageLandingPageActive%>"><a href="/com/events/dashboard/manage_landingpage.jsp">Landing Page</a></li>
</ul>