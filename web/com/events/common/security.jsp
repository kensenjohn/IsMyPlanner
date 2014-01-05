<%@page import="com.events.common.*"%>
<%@page import="com.events.bean.*"%>
<%@page import="com.events.manager.*"%>
<%@page import="java.util.*"%>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="com.events.bean.users.UserBean" %>

<% request.setCharacterEncoding("UTF-8"); %>
<% response.setHeader("X-XSS-Protection", "0"); %>
<% response.setHeader("X-CONTENT-TYPE-OPTIONS", "NOSNIFF"); %>
<% response.setHeader("X-DOWNLOAD-OPTIONS","NOOPEN"); %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
<% response.setHeader("Pragma", "no-cache"); %>
<% response.setDateHeader("Expires", 0); %>

<%
    Logger securityLogging = LoggerFactory.getLogger("JspLogging");
    String sAdminIdSecure = "";
    //This section will identify whether there was insecure params used in a request
    String sErrorParam = com.events.common.ParseUtil.checkNull( (String)request.getAttribute("INSECURE_PARAMS_ERROR") );
    boolean isSecurityError = com.events.common.ParseUtil.sTob(sErrorParam) ;
    //securityLogging.error("isSecurityError -" + isSecurityError);
    if(isSecurityError)
    {
        response.sendRedirect("/web/com/gs/common/error/error.jsp");
    }

    //This one checks to see whether the user was logged in.
    boolean isFromCookie = ParseUtil.sTob(ParseUtil.checkNullObject(session.getAttribute("from_cookie")));
    if(isFromCookie)
    {
        session.removeAttribute("from_cookie");  //reset the session.
    }
    //securityLogging.error("Session from cookie -" + isFromCookie);
    boolean isSignedIn = false;
    HttpSession  reqSession = request.getSession(false);
    if(reqSession!=null)
    {
        UserBean userBean = (UserBean) reqSession.getAttribute(Constants.USER_SESSION);

        //securityLogging.error("Admin Bean - " + adminBean);
        String sFirstName = "";
        if( userBean!=null && userBean.isAdminExists() )  {
            isSignedIn = true;
        }

        if(isFromCookie && !isSignedIn)
        {
            String sUserId = ParseUtil.checkNullObject(session.getAttribute("u_id"));


            if(sUserId!=null && !"".equalsIgnoreCase(sUserId))
            {
                session.removeAttribute("u_id"); //reset the session.
                AdminManager adminManager = new AdminManager();
                adminBean = adminManager.getAdmin(sUserId);
            }
        }
        //securityLogging.error("11 is signe in - " + isSignedIn);
        if( adminBean!=null && adminBean.isAdminExists() )
        {
            isSignedIn = true;

            sAdminIdSecure = adminBean.getAdminId();
            reqSession.setAttribute(Constants.USER_SESSION,adminBean);
        }
        //securityLogging.error("22 is signe in - " + isSignedIn);
    }


%>