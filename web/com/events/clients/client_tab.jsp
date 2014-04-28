<%@ page import="com.events.common.ParseUtil" %>

<%
    String sClientAccountTabActive = ParseUtil.checkNull(request.getParameter("client_account_active"));
    String sClientEventTabActive = ParseUtil.checkNull(request.getParameter("client_events_active"));
    String sClientWebsiteAccessTabActive = ParseUtil.checkNull(request.getParameter("client_website_access_active"));
    String sClientFilesTabActive = ParseUtil.checkNull(request.getParameter("client_files_active"));
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
%>
<ul class="tabs">
    <li class="<%=sClientAccountTabActive%>"><a href="/com/events/clients/client_account.jsp?client_id=<%=sClientId%>&client_datatype=contact_info">Contact Account</a></li>
    <li class="<%=sClientEventTabActive%>"><a href="/com/events/clients/client_events.jsp?client_id=<%=sClientId%>&client_datatype=event_info">Events</a></li>
    <li class="<%=sClientWebsiteAccessTabActive%>"><a href="/com/events/clients/client_website_access.jsp?client_id=<%=sClientId%>">Website Access </a></li>
    <li class="<%=sClientFilesTabActive%>"><a href="/com/events/clients/client_files.jsp?client_id=<%=sClientId%>">Files </a></li>
</ul>