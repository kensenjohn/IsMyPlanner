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
<div class="menu_bar">
    <div class="container">
        <div class="menu_logo">
            <a href="<%=sHomeUrl%>"><img src="<%=sLogo%>" alt=""></a>
        </div>
        <div class="menu_links">
            <%

                String sHomeLinkCurrentlyActive = ParseUtil.checkNull(request.getParameter("home_active"));
                String sDashBoardLinkCurrentlyActive = ParseUtil.checkNull(request.getParameter("dashboard_active"));
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
                        <ul class="nav navbar-nav navbar-right menu">
                            <%
                                if(checkPermission!=null && checkPermission.can(Perm.CREATE_NEW_EVENT)) {
                            %>
                                    <li class="navbar-btn navbar-btn-format">
                                        <button  type="button" class="btn  btn-filled" id="btn_create_event">
                                            <span class="glyphicon glyphicon-plus"></span><a>  Create Event </a>
                                        </button>
                                    </li>
                            <%
                                }
                            %>
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
                        <ul class="nav navbar-nav navbar-right menu">
                            <li class="<%=sHomeLinkCurrentlyActive%>"><a href="/index.jsp">Home</a></li>
                        </ul>
                <%
                    }
                %>

            <%
                }
            %>

        </div>
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