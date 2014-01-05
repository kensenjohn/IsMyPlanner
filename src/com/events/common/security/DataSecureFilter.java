package com.events.common.security;

import com.events.bean.security.DataSecurityRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.PropertyFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 6:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataSecureFilter implements Filter {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

    private DataSecurityRequestBean dataSecurityRequestBean = new DataSecurityRequestBean();

    @Override
    public void destroy() {
        appLogging.info("Killing the Security Filter");
        dataSecurityRequestBean = null;

    }

    @Override
    public void doFilter(ServletRequest servReq, ServletResponse servResp, FilterChain filterChain) throws IOException, ServletException {
        boolean isUnsafeParametersDetected = true;
        appLogging.info("DataSecurity Invoked");
        Map<String, String[]> reqParams = servReq.getParameterMap();
        if( dataSecurityRequestBean!=null) {
            if( reqParams!=null && !reqParams.isEmpty() ) {
                try {
                    if( DataSecurityChecker.isDataSecurityRequestBeanOld(dataSecurityRequestBean) ) {

                        appLogging.info("The DataSecurityRequestBean was reloaded");
                        appLogging.debug("After reload : "  + dataSecurityRequestBean);
                        dataSecurityRequestBean = DataSecurityChecker.loadDataSecurityRequestBean();
                    }
                    if( dataSecurityRequestBean.isFilterEnabled() ) {
                        for( Map.Entry<String,String[]> mapRequestParams : reqParams.entrySet() ) {
                            String[] sRequestValueArray = mapRequestParams.getValue();
                            if( sRequestValueArray!=null && sRequestValueArray.length > 0) {
                                for( String sInputData : sRequestValueArray ) {
                                    isUnsafeParametersDetected = DataSecurityChecker.isStringSafe(sInputData,dataSecurityRequestBean);
                                    if(isUnsafeParametersDetected) {
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        isUnsafeParametersDetected = false; //
                        appLogging.info("Warning : Security Filter has been disabled.");
                    }
                } catch (PropertyFileException e) {
                    dataSecurityRequestBean.setFilterEnabled(true);
                    appLogging.error("PropertyFileException - " + e.getMessage() + " - " + ExceptionHandler.getStackTrace(e));
                } catch (Exception e) {
                    isUnsafeParametersDetected = true;
                    dataSecurityRequestBean.setFilterEnabled(true);
                    appLogging.error("Exception - " + e.getMessage() + " - " + ExceptionHandler.getStackTrace(e));
                }
            } else {
                isUnsafeParametersDetected = false;   // There are no parameters, so no exception was/
            }

        }

        if(isUnsafeParametersDetected) {
            servReq.setAttribute(Constants.INSECURE_PARAMS_ERROR,"true");
        } else {
            servReq.setAttribute(Constants.INSECURE_PARAMS_ERROR,"false");
        }
        filterChain.doFilter(servReq, servResp);
    }

    ServletContext servletContext = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        appLogging.info("Initializing the DataSecurityRequestBean");
        try {
            dataSecurityRequestBean = DataSecurityChecker.bootstrapDataSecurityRequestBean();
            appLogging.info("After Bootstrapping : " + dataSecurityRequestBean);
        } catch (PropertyFileException e) {
            dataSecurityRequestBean.setFilterEnabled(true);
            appLogging.error(e.getMessage() + " - " + ExceptionHandler.getStackTrace(e));
        }
    }
}
