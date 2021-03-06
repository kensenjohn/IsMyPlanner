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
 * User: kensen
 * Date: 1/27/14
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSavePublishLandingPagePanel extends HttpServlet {
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
                    String sLandingPageAction = ParseUtil.checkNull(request.getParameter("website_landingpage_panel_action"));
                    String sVendorWebsiteId = ParseUtil.checkNull(request.getParameter("vendorwebsite_id"));
                    String sVendorId = ParseUtil.checkNull(request.getParameter("vendor_id"));
                    String sThemeName = ParseUtil.checkNull(request.getParameter("landingpage_theme"));



                    VendorWebsiteRequestBean vendorWebsiteRequestBean = new VendorWebsiteRequestBean();
                    vendorWebsiteRequestBean.setModifiedByUserId( loggedInUserBean.getUserId() );
                    vendorWebsiteRequestBean.setVendorId( sVendorId);
                    vendorWebsiteRequestBean.setVendorWebsiteId( sVendorWebsiteId );

                    boolean isErrorExists = false;
                    if("save_image".equalsIgnoreCase(sLandingPageAction)) {
                        String sLandingpagePictureName = ParseUtil.checkNull(request.getParameter("landingpage_picture"));
                        if ( Utility.isNullOrEmpty(sLandingpagePictureName)  )  {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request. Please upload a landing page picture.","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                            isErrorExists = true;
                        } else {
                            vendorWebsiteRequestBean.setLandingPageImage( sLandingpagePictureName );
                        }
                    } else if("save_greeting".equalsIgnoreCase(sLandingPageAction)){
                        String sLandingpageGreetingHeader = ParseUtil.checkNull(request.getParameter("landingpage_greeting_header"));
                        String sLandingpageGreetingText = ParseUtil.checkNull(request.getParameter("landingpage_greeting_text"));

                        if ( Utility.isNullOrEmpty(sLandingpageGreetingHeader)  )  {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request. Please use a valid Greeting Header.","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                            isErrorExists = true;
                        }  else {
                            vendorWebsiteRequestBean.setGreetingHeader( sLandingpageGreetingHeader );
                            vendorWebsiteRequestBean.setGreetingText( sLandingpageGreetingText );
                        }
                    } else if("save_social_media".equalsIgnoreCase(sLandingPageAction)){
                        String sFacebookFeedScript = ParseUtil.checkNull(request.getParameter("landingpage_facebook"));
                        String sPinterestFeedScript = ParseUtil.checkNull(request.getParameter("landingpage_pinterest"));

                        if ( Utility.isNullOrEmpty(sFacebookFeedScript)  )  {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request. Please use a valid Facebook script.","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                            isErrorExists = true;
                        } else if ( Utility.isNullOrEmpty(sPinterestFeedScript)  )  {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request. Please use a valid Pinterest script.","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                            isErrorExists = true;
                        }  else {
                            /*
                            <a data-pin-do="embedBoard" href="http://www.pinterest.com/pinterest/pin-pets/">Follow Pinterest's board Pin pets on Pinterest.</a>
<!-- Please call pinit.js only once per page -->
<script type="text/javascript" async src="//assets.pinterest.com/js/pinit.js"></script>
                             */
                            sPinterestFeedScript = sPinterestFeedScript.replace("<script type=\"text/javascript\" async src=\"//assets.pinterest.com/js/pinit.js\"></script>" , "");
                            vendorWebsiteRequestBean.setFacebookFeedScript( sFacebookFeedScript );
                            vendorWebsiteRequestBean.setPinterestFeedScript( sPinterestFeedScript );
                        }
                    }


                    if(!isErrorExists) {
                        BuildVendorWebsite buildVendorWebsite = new BuildVendorWebsite();
                        VendorWebsiteResponseBean savedVendorWebsiteResponseBean = buildVendorWebsite.saveLandingPageLayoutContent(vendorWebsiteRequestBean);
                        VendorWebsiteResponseBean publishedVendorWebsiteResponseBean = buildVendorWebsite.publishLandingPageLayoutContent(vendorWebsiteRequestBean);

                        if(savedVendorWebsiteResponseBean!=null && publishedVendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(savedVendorWebsiteResponseBean.getVendorWebsiteId())
                                && !Utility.isNullOrEmpty(publishedVendorWebsiteResponseBean.getVendorWebsiteId()) ) {
                            jsonResponseObj.put("vendorwebsite_id",publishedVendorWebsiteResponseBean.getVendorWebsiteId());
                            Text okText = new OkText("The landing page layout was successfully updated.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to save the landing page layout. Please try again later.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    }

                    if ( Utility.isNullOrEmpty(sLandingPageAction) ) {
                        appLogging.info("An invalid action used by the user : " + loggedInUserBean.getUserId() );
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(landingPage - 003)","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(landingPage - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(landingPage - 001)","err_mssg") ;
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