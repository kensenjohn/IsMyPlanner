<%@ page import="com.events.common.ParseUtil" %>
<%
    String sDashBoardActive = ParseUtil.checkNull(request.getParameter("dashboard_active"));
    String sDashBoardOurWebsiteActive = ParseUtil.checkNull(request.getParameter("our_website_active"));
    String sDashBoardOurTeamActive = ParseUtil.checkNull(request.getParameter("our_team_active"));
%>
<ul class="tabs">
    <li class="<%=sDashBoardActive%>"><a href="/com/events/dashboard/dashboard.jsp">Summary</a></li>
    <li class="<%=sDashBoardOurWebsiteActive%>"><a href="/com/events/dashboard/our_website.jsp">Our Website</a></li>
    <li class="<%=sDashBoardOurTeamActive%>"><a href="/com/events/dashboard/our_team.jsp">Our Team</a></li>
</ul>