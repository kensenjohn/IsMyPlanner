package com.events.common.service;

import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.bean.vendors.website.ColorCSSBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.common.*;
import com.events.vendors.website.AccessVendorWebsite;
import com.events.vendors.website.css.BuildColorCss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        if(!Utility.isNullOrEmpty(sLocalName) && sLocalName.endsWith("." + APPLICATION_DOMAIN) && !sLocalName.startsWith("www")  && !sLocalName.startsWith(APPLICATION_DOMAIN)) {



            String[] arraySubDomain = sLocalName.split("." + APPLICATION_DOMAIN);
            AccessVendorWebsite  accessVendorWebsite = new AccessVendorWebsite();
            for(String subDomain : arraySubDomain ) {

                boolean loadFromDB = false;
                HttpSession httpSession = request.getSession(true);

                if(httpSession!=null ){

                    if(httpSession.getAttribute("SUBDOMAIN_TIME")!=null){
                        Long sSubDomainCreatedTime = (Long)httpSession.getAttribute("SUBDOMAIN_TIME");

                        Long lTimeDifference =(DateSupport.getEpochMillis() - sSubDomainCreatedTime);
                        if( lTimeDifference > 60000) {
                            httpSession.removeAttribute("SUBDOMAIN");
                            httpSession.removeAttribute("SUBDOMAIN_VENDOR");
                            httpSession.removeAttribute("SUBDOMAIN_TIME");
                            httpSession.removeAttribute("SUBDOMAIN_COLORS");

                            loadFromDB = true;
                        }
                    }else {
                        loadFromDB = true;
                    }
                }

                if(loadFromDB) {
                    VendorWebsiteRequestBean vendorWebsiteRequestBean = new VendorWebsiteRequestBean();
                    vendorWebsiteRequestBean.setSubDomain(subDomain);
                    VendorResponseBean vendorResponseBean = accessVendorWebsite.getVendorBySubDomain( vendorWebsiteRequestBean );
                    //appLogging.info(vendorBean.toString());
                    if(vendorResponseBean!=null &&  vendorResponseBean.getVendorBean()!=null ) {

                        VendorBean vendorBean = vendorResponseBean.getVendorBean();
                        if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {

                            vendorWebsiteRequestBean.setVendorId( vendorBean.getVendorId() );
                            vendorWebsiteRequestBean.setVendorWebsiteId( vendorResponseBean.getVendorWebsiteId() );


                            HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmVendorWebsiteFeatureBean = accessVendorWebsite.getPublishedFeaturesForWebPages( vendorResponseBean.getVendorWebsiteBean() );
                            HttpSession httpNewSession = request.getSession(true);

                            httpNewSession.setAttribute("SUBDOMAIN", subDomain );
                            httpNewSession.setAttribute("SUBDOMAIN_VENDOR", vendorBean );
                            httpNewSession.setAttribute("SUBDOMAIN_TIME", DateSupport.getEpochMillis());

                            ColorCSSBean colorCSSBean = new ColorCSSBean( hmVendorWebsiteFeatureBean );
                            BuildColorCss buildColorCss = new BuildColorCss();
                            String sOverridingColorCss = buildColorCss.getColorCss( colorCSSBean ) ;


                            httpNewSession.setAttribute("SUBDOMAIN_COLORS", sOverridingColorCss );
                        } else {

                            HttpServletResponse response = (HttpServletResponse) servletResponse;
                            response.sendRedirect( "http://" + APPLICATION_DOMAIN );
                            appLogging.info("User's sub domain has changed : " + subDomain);
                        }
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
