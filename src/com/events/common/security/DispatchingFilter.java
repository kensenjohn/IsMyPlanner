package com.events.common.security;

import com.events.bean.users.*;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.users.AccessUsers;
import com.events.users.CookieUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchingFilter implements Filter {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)  servletRequest;
        UserBean userBean = (UserBean)httpRequest.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);
        boolean isUserLoggedIn = false;
        if(userBean!=null && !"".equalsIgnoreCase(userBean.getUserId())) {
            isUserLoggedIn = true;
        }
        if(!isUserLoggedIn){
            Cookie[] cookies = httpRequest.getCookies();
            AccessUsers accessUsers = new AccessUsers();
            userBean = accessUsers.getLoggedInUserBean(cookies);
            if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())){
                httpRequest.getSession().setAttribute(Constants.USER_LOGGED_IN_BEAN,userBean);
                isUserLoggedIn = true;
            }
        }

        String path = ParseUtil.checkNull(((HttpServletRequest) servletRequest).getRequestURI());

        boolean isInsecureParamUsed = ParseUtil.sTob((ParseUtil.checkNullObject(servletRequest.getAttribute(Constants.INSECURE_PARAMS_ERROR))));
        if( isInsecureParamUsed ) {
            servletRequest.getRequestDispatcher("/com/events/common/error/security_warning.jsp").forward(servletRequest, servletResponse);
        } else if( !isUserLoggedIn && !path.endsWith("/credentials.jsp") && !path.endsWith("/forgot.jsp")&& !path.endsWith("/reset_password.jsp")
                && !path.endsWith("/set_password.jsp")
                && !path.endsWith("/privacy.jsp") && !path.endsWith("/contact.jsp") && !path.endsWith("/about_us.jsp")) {
            servletRequest.getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);
        }  else  if( (isUserLoggedIn && !isInsecureParamUsed) || path.endsWith("/credentials.jsp")  || path.endsWith("/forgot.jsp")
                || path.endsWith("/reset_password.jsp") || path.endsWith("/set_password.jsp")
                || path.endsWith("/privacy.jsp") || path.endsWith("/contact.jsp") || path.endsWith("/about_us.jsp")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
