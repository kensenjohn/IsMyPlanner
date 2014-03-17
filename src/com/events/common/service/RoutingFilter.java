package com.events.common.service;

import com.events.bean.event.website.*;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.event.website.AccessEventWebsite;
import com.events.event.website.AccessWebsiteThemes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/1/14
 * Time: 7:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class RoutingFilter implements Filter {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURL().toString();
        appLogging.info("URL : " + url );
        String number = url.substring(url.lastIndexOf("/")).replace("/", "");

        String sThemeName = Constants.EMPTY;
        String sEventId = Constants.EMPTY;
        String sPageName = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(url)) {
            String sPath = url.replace("http://127.0.0.1:8080/ep/" , "");
            appLogging.info("Path : " + sPath );

            StringTokenizer strPathTokens = new StringTokenizer(sPath,"/",false);
            if(strPathTokens!=null){
                Integer iCount = 0;
                while(strPathTokens.hasMoreTokens() ) {
                    //appLogging.info((String)strPathTokens.nextToken());
                    String sToken = (String)strPathTokens.nextToken();
                    if( iCount == 0 && sToken!=null && !"css".equalsIgnoreCase(sToken) && !"img".equalsIgnoreCase(sToken) ) {

                        EventWebsiteRequestBean eventWebsiteRequestBean = new EventWebsiteRequestBean();
                        eventWebsiteRequestBean.setUrlUniqueName( sToken );

                        AccessEventWebsite accessEventWebsite = new AccessEventWebsite();
                        EventWebsiteBean eventWebsiteBean = accessEventWebsite.getEventWebsiteBeanFromUrl( eventWebsiteRequestBean );

                        if(eventWebsiteBean!=null && !Utility.isNullOrEmpty(eventWebsiteBean.getEventWebsiteId())) {
                            AllWebsiteThemeRequestBean allWebsiteThemeRequestBean = new AllWebsiteThemeRequestBean();
                            allWebsiteThemeRequestBean.setWebsiteThemeId(  eventWebsiteBean.getWebsiteThemeId() );

                            AccessWebsiteThemes accessWebsiteThemes = new AccessWebsiteThemes();
                            WebsiteThemeBean websiteThemeBean =  accessWebsiteThemes.getWebsiteTheme(allWebsiteThemeRequestBean);

                            if(websiteThemeBean!=null && !Utility.isNullOrEmpty(websiteThemeBean.getWebsiteThemeId())) {
                                sThemeName = ParseUtil.checkNull(websiteThemeBean.getName());
                                sEventId = ParseUtil.checkNull(eventWebsiteBean.getEventId() );
                                sPageName = "index.jsp";
                            }
                        }
                    }
                    iCount++;
                }
            }
        }

        if(!Utility.isNullOrEmpty(sThemeName) && !Utility.isNullOrEmpty(sEventId) && !Utility.isNullOrEmpty(sPageName) ) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cl/"+sThemeName+"/"+sPageName+"?ewi="+sEventId);
            dispatcher.forward(servletRequest, servletResponse);
        }else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(servletRequest, servletResponse);
        }

        //filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
