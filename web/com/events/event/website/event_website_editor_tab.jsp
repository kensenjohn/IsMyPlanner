<%@ page import="com.events.common.ParseUtil" %>

<%
    String sEditThemeActive = ParseUtil.checkNull(request.getParameter("edit_event_website_theme_active"));
    String sEditColorFontActive = ParseUtil.checkNull(request.getParameter("edit_event_website_colorfont_active"));
    String sEditPagesActive = ParseUtil.checkNull(request.getParameter("edit_event_website_pages_active"));
    String sEditWebLinks = ParseUtil.checkNull(request.getParameter("edit_event_website_links_active"));
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <!-- Brand and toggle get grouped for better mobile display -->
            <button type="button" class="navbar-toggle pull-left" data-toggle="collapse" data-target="#event_tab_collapse_1">
                <span class="sr-only">Toggle Dashboard Tab</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="event_tab_collapse_1">
                <ul class="nav navbar-nav  navbar-right">
                    <li class="<%=sEditThemeActive%>"><a href="/com/events/event/website/edit_event_website_theme.jsp?event_id=<%=sEventId%>">Themes</a></li>
                    <li class="<%=sEditColorFontActive%>"><a href="/com/events/event/website/edit_event_website_color_font.jsp?event_id=<%=sEventId%>">Colors and Fonts</a></li>
                    <li class="<%=sEditPagesActive%>"><a href="/com/events/event/website/edit_event_website_pages.jsp?event_id=<%=sEventId%>">Pages</a></li>
                    <li class="<%=sEditWebLinks%>"><a href="/com/events/event/website/edit_event_website_link.jsp?event_id=<%=sEventId%>">Website URL/Link</a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div>
    </div>
</nav>