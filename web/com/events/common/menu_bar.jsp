<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%
    String sLogo = Constants.EMPTY;
    if( session.getAttribute("SUBDOMAIN_COLORS") != null ) {
        sLogo = ParseUtil.checkNull( (String) session.getAttribute("SUBDOMAIN_LOGO"));
    }
    if(Utility.isNullOrEmpty(sLogo)) {
        sLogo = "/img/logo.png";
    }

%>
<div class="menu_bar">
    <div class="container">
        <div class="menu_logo">
            <a href="#"><img src="<%=sLogo%>" alt=""></a>
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
                            <li class="navbar-btn navbar-btn-format">
                                <button  type="button" class="btn  btn-filled" id="btn_create_event">
                                    <span class="glyphicon glyphicon-plus"></span><a>  Create Event </a>
                                </button>
                            </li>
                            <li class="<%=sDashBoardLinkCurrentlyActive%>"><a href="/com/events/dashboard/dashboard.jsp">Dashboard</a></li>
                            <li class="<%=sClientLinkCurrentlyActive%>"><a href="/com/events/clients/clients.jsp">Clients</a></li>
                            <li class="<%=sEventLinkCurrentlyActive%>"><a href="/com/events/event/events.jsp">Events</a></li>
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