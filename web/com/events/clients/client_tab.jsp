<%@ page import="com.events.common.ParseUtil" %>

<%
    String sClientContactInfoTabActive = ParseUtil.checkNull(request.getParameter("client_contact_info_active"));
    String sClientEventTabActive = ParseUtil.checkNull(request.getParameter("client_events_active"));
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
%>
<ul class="tabs">
    <li class="<%=sClientContactInfoTabActive%>"><a href="/com/events/clients/client_contact_form.jsp?client_id=<%=sClientId%>&client_datatype=contact_info">Contact Info</a></li>
    <li class="<%=sClientEventTabActive%>"><a href="/com/events/clients/client_events.jsp?client_id=<%=sClientId%>&client_datatype=event_info">Events</a></li>
</ul>