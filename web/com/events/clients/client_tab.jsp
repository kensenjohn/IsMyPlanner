<%@ page import="com.events.common.ParseUtil" %>

<%
    String sClientContactInfoTabActive = ParseUtil.checkNull(request.getParameter("client_contact_info_active"));
    String sClientEventTabActive = ParseUtil.checkNull(request.getParameter("client_events_active"));
    String sClientId = ParseUtil.checkNull(request.getParameter("clientid"));
%>
<ul class="tabs">
    <li class="<%=sClientContactInfoTabActive%>"><a href="/com/events/clients/clients.jsp?clientid=<%=sClientId%>&clientdatatype=contact_info">Contact Info</a></li>
    <li class="<%=sClientEventTabActive%>"><a href="/com/events/clients/clients.jsp?clientid=<%=sClientId%>&clientdatatype=event_info">Events</a></li>
</ul>