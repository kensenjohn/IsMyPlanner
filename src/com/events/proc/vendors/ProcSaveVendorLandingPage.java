package com.events.proc.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorLandingPageRequestBean;
import com.events.bean.vendors.VendorLandingPageResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.vendors.BuildVendorLandingPage;
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
 * Date: 1/17/14
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveVendorLandingPage extends HttpServlet {
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

                if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {
                    String sVendorLandingPageId = ParseUtil.checkNull(request.getParameter("vendor_landingpage_id"));
                    String sVendorId = ParseUtil.checkNull(request.getParameter("vendor_id"));
                    String sThemeName = ParseUtil.checkNull(request.getParameter("landingPageTheme"));
                    String sLogoImg = ParseUtil.checkNull(request.getParameter("landingpage_logo"));
                    String sLandingPagePicture = ParseUtil.checkNull(request.getParameter("landingpage_picture"));
                    String sFacebookFeedURL = ParseUtil.checkNull(request.getParameter("landingPage_facebook"));
                    String sPinteredFeedURL = ParseUtil.checkNull(request.getParameter("landingPage_pinterest"));

                    if( Utility.isNullOrEmpty(sThemeName) ) {
                        appLogging.info("Please select a valid theme.");
                        Text errorText = new ErrorText("Please select a valid theme.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if(  Utility.isNullOrEmpty(sLogoImg)  || Utility.isNullOrEmpty(sLandingPagePicture) )  {
                        appLogging.info("Please upload a logo and landing page picture.");
                        Text errorText = new ErrorText("Please upload a logo and landing page picture.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if ( "simple_landingpage_socialmedia".equalsIgnoreCase(sThemeName) && (Utility.isNullOrEmpty(sFacebookFeedURL)  || Utility.isNullOrEmpty(sPinteredFeedURL) ) ) {
                        appLogging.info("Please fill in all the required fields. ");
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEmail - 002)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        VendorLandingPageRequestBean buildVendorLandingPageRequestBean = new VendorLandingPageRequestBean();
                        buildVendorLandingPageRequestBean.setThemeName(sThemeName);
                        buildVendorLandingPageRequestBean.setVendorId( sVendorId );
                        buildVendorLandingPageRequestBean.setVendorLandingPageId( sVendorLandingPageId );
                        buildVendorLandingPageRequestBean.setLogoImage( sLogoImg );
                        buildVendorLandingPageRequestBean.setLandingPageImage( sLandingPagePicture );
                        buildVendorLandingPageRequestBean.setFacebookUrl( sFacebookFeedURL );
                        buildVendorLandingPageRequestBean.setPinterestUrl(  sPinteredFeedURL );

                        BuildVendorLandingPage buildVendorLandingPage = new BuildVendorLandingPage();
                        VendorLandingPageResponseBean vendorLandingPageResponseBean = buildVendorLandingPage.saveLandingPage(buildVendorLandingPageRequestBean);
                        if(vendorLandingPageResponseBean!=null && vendorLandingPageResponseBean.getVendorLandingPageBean()!=null
                                && !Utility.isNullOrEmpty(vendorLandingPageResponseBean.getVendorLandingPageBean().getVendorLandingPageId())) {

                            jsonResponseObj.put("vendor_landingpage",vendorLandingPageResponseBean.getVendorLandingPageBean().toJson());
                            Text okText = new OkText("The landing page was saved successfully.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            appLogging.info("Unable to save the landing page " + buildVendorLandingPageRequestBean );
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveVendorLandingPage - 003)","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                        }
                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveVendorLandingPage - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveVendorLandingPage - 001)","err_mssg") ;
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