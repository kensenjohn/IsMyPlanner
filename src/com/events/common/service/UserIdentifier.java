package com.events.common.service;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/10/14
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserIdentifier implements Filter {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String APPLICATION_DOMAIN = applicationConfig.get(Constants.APPLICATION_DOMAIN);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String contextPath = request.getContextPath();

        StringBuffer requestURL = request.getRequestURL();
        String sLocalName = ParseUtil.checkNull(request.getLocalName());
        if(!Utility.isNullOrEmpty(sLocalName) && sLocalName.endsWith("." + APPLICATION_DOMAIN)) {
            String[] arraySubDomain = sLocalName.split("." + APPLICATION_DOMAIN);
            for(String subDomain : arraySubDomain ) {
                //appLogging.info("subDomain : " + subDomain);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
