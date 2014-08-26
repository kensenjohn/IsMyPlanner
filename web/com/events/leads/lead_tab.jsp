<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>

<%
    String sLeadAccountTabActive = ParseUtil.checkNull(request.getParameter("lead_account_active"));
    String sLeadCommentsTabActive = ParseUtil.checkNull(request.getParameter("lead_comments_active"));
    String sLeadId = ParseUtil.checkNull(request.getParameter("lead_id"));
%>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <!-- Brand and toggle get grouped for better mobile display -->
            <button type="button" class="navbar-toggle pull-left" data-toggle="collapse" data-target="#lead_tab_collapse_1">
                <span class="sr-only">Toggle Dashboard Tab</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="lead_tab_collapse_1">
                <ul class="nav navbar-nav  navbar-right">
                    <li class="<%=sLeadAccountTabActive%>"><a href="/com/events/leads/leads_account.jsp?lead_id=<%=sLeadId%>&lead_datatype=contact_info">Contact Account</a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div>
    </div>
</nav>