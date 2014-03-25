package com.events.proc.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.bean.vendors.website.VendorWebsiteResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.vendors.website.BuildVendorWebsite;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/23/14
 * Time: 9:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcShowHideVendorFeatures extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) ) {
                    String sLandingPageAction = ParseUtil.checkNull(request.getParameter("website_footer_panel_action"));
                    String sVendorWebsiteId = ParseUtil.checkNull(request.getParameter("vendorwebsite_id"));
                    String sVendorId = ParseUtil.checkNull(request.getParameter("vendor_id"));
                    String sPageType = ParseUtil.checkNull(request.getParameter("page_type"));
                    String sAction = ParseUtil.checkNull(request.getParameter("action"));



                    VendorWebsiteRequestBean vendorWebsiteRequestBean = new VendorWebsiteRequestBean();
                    vendorWebsiteRequestBean.setModifiedByUserId( loggedInUserBean.getUserId() );
                    vendorWebsiteRequestBean.setVendorId( sVendorId);
                    vendorWebsiteRequestBean.setVendorWebsiteId( sVendorWebsiteId );
                    vendorWebsiteRequestBean.setVendorWebsiteTypeName( sPageType );
                    vendorWebsiteRequestBean.setAction( sAction );

                    BuildVendorWebsite buildVendorWebsite = new BuildVendorWebsite();
                    VendorWebsiteResponseBean showHideVendorWebsiteResponseBean = buildVendorWebsite.showHideVendorFeature( vendorWebsiteRequestBean );

                    if(showHideVendorWebsiteResponseBean!=null  && !Utility.isNullOrEmpty(showHideVendorWebsiteResponseBean.getVendorWebsiteId()) ) {
                        jsonResponseObj.put("vendorwebsite_id",showHideVendorWebsiteResponseBean.getVendorWebsiteId());
                        Text okText = new OkText("The footer layout was successfully updated.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to save the footer layout. Please try again later.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(sHVendorFeature - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(sHVendorFeature - 001)","err_mssg") ;
            arrErrorText.add(errorText);

            responseStatus = RespConstants.Status.ERROR;
        }


        responseObject.setErrorMessages(arrErrorText);
        responseObject.setOkMessages(arrOkText);
        responseObject.setResponseStatus(responseStatus);
        responseObject.setJsonResponseObj(jsonResponseObj);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write( responseObject.getJson().toString() );
    }
}