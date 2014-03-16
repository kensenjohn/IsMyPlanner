<%@ page import="com.events.common.ParseUtil" %>

<%
    String sEditThemeActive = ParseUtil.checkNull(request.getParameter("edit_event_website_theme_active"));
    String sEditColorFontActive = ParseUtil.checkNull(request.getParameter("edit_event_website_colorfont_active"));
    String sEditPagesActive = ParseUtil.checkNull(request.getParameter("edit_event_website_pages_active"));
    String sEditWebLinks = ParseUtil.checkNull(request.getParameter("edit_event_website_links_active"));
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
%>
<ul class="tabs">
    <li class="<%=sEditThemeActive%>"><a href="/com/events/event/website/edit_event_website_theme.jsp?event_id=<%=sEventId%>">Themes</a></li>
    <li class="<%=sEditColorFontActive%>"><a href="/com/events/event/website/edit_event_website_color_font.jsp?event_id=<%=sEventId%>">Colors and Fonts</a></li>
    <li class="<%=sEditPagesActive%>"><a href="/com/events/event/website/edit_event_website_pages.jsp?event_id=<%=sEventId%>">Pages</a></li>
    <li class="<%=sEditWebLinks%>"><a href="/com/events/event/website/edit_event_website_link.jsp?event_id=<%=sEventId%>">Website URL/Link</a></li>
</ul>