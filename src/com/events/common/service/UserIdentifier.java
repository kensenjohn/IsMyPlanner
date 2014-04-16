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
import java.util.Enumeration;
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
        String sQueryString = request.getQueryString();
        String sSubDomain = ParseUtil.checkNull(request.getParameter("subdomain"));

        if(!Utility.isNullOrEmpty(sSubDomain) && !sSubDomain.startsWith("www") ) {



            AccessVendorWebsite  accessVendorWebsite = new AccessVendorWebsite();
            boolean isShowRegistrationForm=true;
            //for(String subDomain : arraySubDomain ) {
                isShowRegistrationForm = false;
                boolean loadFromDB = false;
                HttpSession httpSession = request.getSession(true);

                if(httpSession!=null ){
                    if(httpSession.getAttribute("SUBDOMAIN_TIME")!=null){
                        Long sSubDomainCreatedTime = (Long)httpSession.getAttribute("SUBDOMAIN_TIME");

                        Long lTimeDifference =(DateSupport.getEpochMillis() - sSubDomainCreatedTime);
                        if( lTimeDifference > 60000) {
                            httpSession.removeAttribute("SUBDOMAIN");
                            httpSession.removeAttribute("SUBDOMAIN_VENDOR");
                            httpSession.removeAttribute("SUBDOMAIN_VENDOR_WEBSITE");
                            httpSession.removeAttribute("SUBDOMAIN_TIME");
                            httpSession.removeAttribute("SUBDOMAIN_COLORS");
                            httpSession.removeAttribute("SUBDOMAIN_LOGO");


                            loadFromDB = true;
                        }
                    }else {
                        loadFromDB = true;
                    }
                }

                if(loadFromDB) {
                    VendorWebsiteRequestBean vendorWebsiteRequestBean = new VendorWebsiteRequestBean();
                    vendorWebsiteRequestBean.setSubDomain(sSubDomain);
                    VendorResponseBean vendorResponseBean = accessVendorWebsite.getVendorBySubDomain( vendorWebsiteRequestBean );
                    //appLogging.info(vendorBean.toString());
                    if(vendorResponseBean!=null &&  vendorResponseBean.getVendorBean()!=null ) {
                        appLogging.info("User's sub domain from feature : " + vendorResponseBean.getSubDomain());
                        VendorBean vendorBean = vendorResponseBean.getVendorBean();
                        if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {

                            vendorWebsiteRequestBean.setVendorId( vendorBean.getVendorId() );
                            vendorWebsiteRequestBean.setVendorWebsiteId( vendorResponseBean.getVendorWebsiteId() );


                            HashMap<Constants.VENDOR_WEBSITE_FEATURETYPE , VendorWebsiteFeatureBean> hmVendorWebsiteFeatureBean = accessVendorWebsite.getPublishedFeaturesForWebPages( vendorResponseBean.getVendorWebsiteBean() );
                            HttpSession httpNewSession = request.getSession(true);

                            httpNewSession.setAttribute("SUBDOMAIN", sSubDomain );
                            httpNewSession.setAttribute("SUBDOMAIN_VENDOR", vendorBean );
                            httpNewSession.setAttribute("SUBDOMAIN_TIME", DateSupport.getEpochMillis());
                            httpNewSession.setAttribute("SUBDOMAIN_VENDOR_WEBSITE", vendorResponseBean.getVendorWebsiteBean() );


                            // Color for Vendor based Website
                            {
                                ColorCSSBean colorCSSBean = new ColorCSSBean( hmVendorWebsiteFeatureBean );
                                BuildColorCss buildColorCss = new BuildColorCss();
                                String sOverridingColorCss = buildColorCss.getColorCss( colorCSSBean ) ;
                                httpNewSession.setAttribute("SUBDOMAIN_COLORS", sOverridingColorCss );
                            }

                            // Logo for Vendor based Website
                            {

                                VendorWebsiteFeatureBean vendorWebsiteFeatureBean = hmVendorWebsiteFeatureBean.get( Constants.VENDOR_WEBSITE_FEATURETYPE.published_logo );
                                if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue()))  {
                                    String imageHost = Utility.getImageUploadHost();
                                    String sBucket = Utility.getS3Bucket();
                                    String sFolderName = ParseUtil.checkNull(vendorBean.getFolder());


                                    httpNewSession.setAttribute("SUBDOMAIN_LOGO", imageHost + "/" + sBucket + "/" +  sFolderName + "/" + vendorWebsiteFeatureBean.getValue() );
                                }
                            }

                        } else {

                            HttpServletResponse response = (HttpServletResponse) servletResponse;
                            response.sendRedirect( "http://" + APPLICATION_DOMAIN );
                            appLogging.info("User's sub domain has changed : " + sSubDomain);
                            return;
                        }
                    }
                }
            //}

            HttpSession hideRegSession = request.getSession(true);

            if(hideRegSession!=null ){
                hideRegSession.setAttribute("SUBDOMAIN_SHOW_REGISTRATION",isShowRegistrationForm);
            }

            if(hideRegSession!=null && hideRegSession.getAttribute("SUBDOMAIN_SHOW_REGISTRATION") !=null){
                //appLogging.info("Is Show Registration : " + (Boolean) hideRegSession.getAttribute("SUBDOMAIN_SHOW_REGISTRATION"));
            } else {

                appLogging.info("Show Registration Session Is Null : " );
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        appLogging.info("Destroy UserIdentifier: " );
    }
}
