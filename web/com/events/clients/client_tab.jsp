<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>

<%
    String sClientAccountTabActive = ParseUtil.checkNull(request.getParameter("client_account_active"));
    String sClientEventTabActive = ParseUtil.checkNull(request.getParameter("client_events_active"));
    String sClientWebsiteAccessTabActive = ParseUtil.checkNull(request.getParameter("client_website_access_active"));
    String sClientsFilesTabActive = ParseUtil.checkNull(request.getParameter("client_files_active"));
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
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
                    <li class="<%=sClientAccountTabActive%>"><a href="/com/events/clients/client_account.jsp?client_id=<%=sClientId%>&client_datatype=contact_info">Contact Account</a></li>
                    <li class="<%=sClientEventTabActive%>"><a href="/com/events/clients/client_events.jsp?client_id=<%=sClientId%>&client_datatype=event_info">Events</a></li>
                    <li class="<%=sClientWebsiteAccessTabActive%>"><a href="/com/events/clients/client_website_access.jsp?client_id=<%=sClientId%>">Website Access </a></li>
                    <li class="<%=sClientsFilesTabActive%>"><a href="/com/events/dashboard/files.jsp?client_id=<%=sClientId%>&view=<%=Constants.USER_TYPE.CLIENT.getType()%>">Files</a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div>
    </div>
</nav>