package com.events.common.service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/1/14
 * Time: 7:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class RoutingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURL().toString();
        String number = url.substring(url.lastIndexOf("/")).replace("/", "");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/com/events/common/routing_test.jsp?user=" + number );
        dispatcher.forward(servletRequest, servletResponse);

        //filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
