<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.*" %>
<%

    String sHomeUrl = Utility.createSiteDomainUrl(  ParseUtil.checkNull(request.getParameter("subdomain")) );

    String sLogo = Constants.EMPTY;
    if( session.getAttribute("SUBDOMAIN_LOGO") != null ) {
        sLogo = ParseUtil.checkNull( (String) session.getAttribute("SUBDOMAIN_LOGO"));
    }
    if(Utility.isNullOrEmpty(sLogo)) {
        sLogo = "/img/logo.png";
    }



    UserBean loggedInUserBean = new UserBean();
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
    }
    CheckPermission checkPermission = null;
    if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
        checkPermission = new CheckPermission(loggedInUserBean);
    }
%>
<div class="">
    <div class="container">
        <nav class="navbar menu-navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#menu_bar_collapse_1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <div class="menu_logo">
                        <a class="navbar-brand"  style="padding: 0px 0px;" href="<%=sHomeUrl%>"><img src="<%=sLogo%>" alt=""></a>
                    </div>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="menu_bar_collapse_1" style="margin-top: 15px;">

                <%

                    String sHomeLinkCurrentlyActive = ParseUtil.checkNull(request.getParameter("home_active"));
                    String sDashBoardLinkCurrentlyActive = ParseUtil.checkNull(request.getParameter("dashboard_active"));
                    String sLeadLinkCurrentlyActive = ParseUtil.checkNull(request.getParameter("lead_active"));
                    String sClientLinkCurrentlyActive = ParseUtil.checkNull(request.getParameter("client_active"));
                    String sEventLinkCurrentlyActive = ParseUtil.checkNull(request.getParameter("event_active"));
                    boolean isHideMenu = ParseUtil.sTob(request.getParameter("hide_menu"));

                    if(!isHideMenu){


                        UserBean userBean = new  UserBean();
                        if(request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
                            userBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);
                        }
                        if(userBean!=null && !"".equalsIgnoreCase(userBean.getUserId())) {
                            // if user is logged in
                %>
                    <ul class="nav navbar-nav navbar-right ">
                        <%
                            if(checkPermission!=null && checkPermission.can(Perm.CREATE_NEW_EVENT)) {
                        %>
                        <li style="margin-right: 10px;">
                            <button  type="button" class="btn  btn-filled navbar-btn" id="btn_create_event">
                                <span class="glyphicon glyphicon-plus"></span>  Create Event
                            </button>
                        </li>
                        <%
                            }
                        %>
                        <li>&nbsp;</li>
                        <%
                            if(checkPermission!=null && checkPermission.can(Perm.ACCESS_DASHBOARD_TAB)) {
                        %>
                        <li class="<%=sDashBoardLinkCurrentlyActive%>"><a href="/com/events/dashboard/dashboard.jsp">Dashboard</a></li>
                        <%
                            }
                        %>
                        <%
                            if(checkPermission!=null && checkPermission.can(Perm.ACCESS_CLIENTS_TAB)) {
                        %>
                        <li class="<%=sLeadLinkCurrentlyActive%>"><a href="/com/events/leads/leads.jsp">Leads</a></li>
                        <%
                            }
                        %>
                        <%
                            if(checkPermission!=null && checkPermission.can(Perm.ACCESS_CLIENTS_TAB)) {
                        %>
                        <li class="<%=sClientLinkCurrentlyActive%>"><a href="/com/events/clients/clients.jsp">Clients</a></li>
                        <%
                            }
                        %>

                        <%
                            if(checkPermission!=null && checkPermission.can(Perm.ACCESS_EVENTS_TAB)) {
                        %>
                        <li class="<%=sEventLinkCurrentlyActive%>"><a href="/com/events/event/events.jsp">Events</a></li>
                        <%
                            }
                        %>
                    </ul>
            <%
            } else {  // when user is not logged in, then only show Home
            %>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="<%=sHomeLinkCurrentlyActive%>"><a href="/index.jsp">Home</a></li>
                    </ul>
            <%
                }
            %>
            <%
                }
            %>
                </div><!-- /.navbar-collapse -->
            </div>
        </nav>
    </div>
</div>
<%
    if(!isHideMenu){
%>
        <form id="frm_create_event" action="/com/events/event/edit_event.jsp">
        </form>
<%
    }
%>