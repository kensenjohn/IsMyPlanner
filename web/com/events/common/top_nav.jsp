<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.bean.users.UserInfoBean" %>
<%
    String sRedirectTo = com.events.common.ParseUtil.checkNull(request.getParameter(Constants.AFTER_LOGIN_REDIRECT));
    if(!"".equalsIgnoreCase(sRedirectTo)) {
        sRedirectTo = com.events.common.ParseUtil.checkNullObject(session.getAttribute(Constants.AFTER_LOGIN_REDIRECT));
        session.removeAttribute(Constants.AFTER_LOGIN_REDIRECT);
        session.setAttribute(Constants.AFTER_LOGIN_REDIRECT,sRedirectTo);
    }

    UserBean userBean = new  UserBean();
    if(request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        userBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);
    }
%>
<div class="top_navbar_format">
    <div class="container">
        <div class="top_navbar_links">
            <ul class="nav navbar-nav navbar-right menu">
                <%
                    if(userBean!=null && !"".equalsIgnoreCase(userBean.getUserId())) {
                        UserInfoBean userInfoBean = userBean.getUserInfoBean();

                        String sName =  userInfoBean.getFirstName();
                        if(sName.length()>8) {
                            sName = sName.substring(0,8);
                        }
                %>
                        <li><a href="/com/events/common/credentials.jsp">Hi <%=sName%></a></li>
                        <li><a href="/com/events/common/logout.jsp">Logout</a></li>
                <%
                    } else {
                %>
                        <li><a href="/com/events/common/credentials.jsp">Login</a></li>
                        <li><a href="/com/events/common/credentials.jsp">Register</a></li>
                <%
                    }
                %>

            </ul>
        </div>
    </div>
</div>