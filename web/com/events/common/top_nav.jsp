<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.bean.users.UserInfoBean" %>
<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
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

    // If a user is loggin in from a sub domain do not show the registration form
    // This will have to be fixed based on some feed back
    boolean isShowRegistrationForm = true;
    if( session.getAttribute("SUBDOMAIN_SHOW_REGISTRATION") != null ) {
        isShowRegistrationForm = (Boolean)session.getAttribute("SUBDOMAIN_SHOW_REGISTRATION");
    }

    boolean isDisableAccountLink = com.events.common.ParseUtil.sTob(request.getParameter("disable_account_link"));
%>
<div class="top_navbar_format">
    <div class="container">
        <div class="top_navbar_links">
            <ul class="nav navbar-nav navbar-right menu">
                <%
                    if(userBean!=null && !"".equalsIgnoreCase(userBean.getUserId())) {
                        UserInfoBean userInfoBean = userBean.getUserInfoBean();

                        String sName =  ParseUtil.checkNull(userInfoBean.getFirstName());
                        if(Utility.isNullOrEmpty(sName)) {
                            sName = "My Account";
                        } else {
                            sName = sName + "'s Account";
                        }
                %>

                            <%
                                if(isDisableAccountLink) {
                                } else {
                                %><li><a href="/com/events/common/my_account.jsp"><i class="fa fa-user"></i> <span id="top_nave_hello_name"><%=sName%></span></a></li><%
                                }
                            %>


                        <li><a href="/com/events/common/logout.jsp"> Logout <i class="fa fa-sign-out"></i></a></li>
                <%
                    } else {
                %>
                        <li><a href="/com/events/common/credentials.jsp"><i class="fa fa-sign-in"></i> Sign In</a></li>
                        <%
                            if(isShowRegistrationForm) {
                        %>
                                <li><a href="/com/events/common/credentials.jsp">Register</a></li>
                        <%
                            }
                        %>

                <%
                    }
                %>

            </ul>
        </div>
    </div>
</div>