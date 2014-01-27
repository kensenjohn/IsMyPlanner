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
 * Date: 1/26/14
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSavePublishWebsiteColorPanel extends HttpServlet {
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
                /*
                        $('#website_color_bkg').spectrum("set", colorCombination.bkg);
        $('#website_color_highlighted').spectrum("set", colorCombination.highlighted);
        $('#website_color_text').spectrum("set", colorCombination.text);
        $('#website_color_nav_bread_tab_bkg').spectrum("set", colorCombination.nav_breadcrumb_tab_bkg);

        $('#website_color_border').spectrum("set", colorCombination.border);
        $('#website_color_filled_button').spectrum("set", colorCombination.filled_button);
        $('#website_color_filled_button_txt').spectrum("set", colorCombination.filled_button_txt);
        $('#website_color_plain_button').spectrum("set", colorCombination.plain_button);
        $('#website_color_plain_button_txt').spectrum("set", colorCombination.plain_button_txt);
                 */
                    String sBackgroundColor = ParseUtil.checkNull(request.getParameter("website_color_bkg"));
                    String sHighlightedColor = ParseUtil.checkNull(request.getParameter("website_color_highlighted"));
                    String sTextColor = ParseUtil.checkNull(request.getParameter("website_color_text"));
                    String sNavbarBreadcrumbTabBackgroundColor = ParseUtil.checkNull(request.getParameter("website_color_nav_bread_tab_bkg"));

                    String sBorderColor = ParseUtil.checkNull(request.getParameter("website_color_border"));
                    String sFilledButtonColor = ParseUtil.checkNull(request.getParameter("website_color_filled_button"));
                    String sFilledButtonTextColor = ParseUtil.checkNull(request.getParameter("website_color_filled_button_txt"));
                    String sPlainButtonColor = ParseUtil.checkNull(request.getParameter("website_color_plain_button"));
                    String sPlainButtonTextColor = ParseUtil.checkNull(request.getParameter("website_color_plain_button_txt"));

                    String sColorAction = ParseUtil.checkNull(request.getParameter("website_color_panel_action"));

                    String sVendorWebsiteId = ParseUtil.checkNull(request.getParameter("vendorwebsite_id"));
                    String sVendoId = ParseUtil.checkNull(request.getParameter("vendor_id"));

                    if( Utility.isNullOrEmpty(sBackgroundColor)  || Utility.isNullOrEmpty(sHighlightedColor)  || Utility.isNullOrEmpty(sTextColor)
                            || Utility.isNullOrEmpty(sNavbarBreadcrumbTabBackgroundColor) || Utility.isNullOrEmpty(sBorderColor)|| Utility.isNullOrEmpty(sFilledButtonColor)
                            || Utility.isNullOrEmpty(sFilledButtonTextColor)|| Utility.isNullOrEmpty(sPlainButtonColor)|| Utility.isNullOrEmpty(sPlainButtonTextColor) ) {

                        Text errorText = new ErrorText("Oops!! Please make sure to assign a color to every required option.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;


                    } else if ( Utility.isNullOrEmpty(sColorAction) ) {
                        appLogging.info("An invalid action used by the user : " + loggedInUserBean.getUserId() );
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(websiteColor - 003)","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        VendorWebsiteRequestBean vendorWebsiteRequestBean = new VendorWebsiteRequestBean();
                        vendorWebsiteRequestBean.setBackground( sBackgroundColor );
                        vendorWebsiteRequestBean.setHighlightedTextOrLink( sHighlightedColor );
                        vendorWebsiteRequestBean.setText( sTextColor );
                        vendorWebsiteRequestBean.setNavBreadTabBackground( sNavbarBreadcrumbTabBackgroundColor );

                        vendorWebsiteRequestBean.setBorder( sBorderColor );
                        vendorWebsiteRequestBean.setFilledButton(sFilledButtonColor);
                        vendorWebsiteRequestBean.setFilledButtonText(sFilledButtonTextColor);
                        vendorWebsiteRequestBean.setPlainButton( sPlainButtonColor );
                        vendorWebsiteRequestBean.setPlainButtonText( sPlainButtonTextColor );

                        vendorWebsiteRequestBean.setModifiedByUserId( loggedInUserBean.getUserId() );
                        vendorWebsiteRequestBean.setVendorId( sVendoId);
                        vendorWebsiteRequestBean.setVendorWebsiteId( sVendorWebsiteId );

                        BuildVendorWebsite buildVendorWebsite = new BuildVendorWebsite();
                        if("save".equalsIgnoreCase(sColorAction)){
                            VendorWebsiteResponseBean vendorWebsiteResponseBean = buildVendorWebsite.saveWebsiteColor(vendorWebsiteRequestBean);

                            if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteId())) {
                                jsonResponseObj.put("vendorwebsite_id",vendorWebsiteResponseBean.getVendorWebsiteId());
                                Text okText = new OkText("The colors for the website were saved successfully.","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                Text errorText = new ErrorText("Oops!! We were unable to save the colors. Please try again later.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        } else if( "publish".equalsIgnoreCase(sColorAction)){
                            VendorWebsiteResponseBean vendorWebsiteResponseBean = buildVendorWebsite.publishWebsiteColor(vendorWebsiteRequestBean);

                            if(vendorWebsiteResponseBean!=null && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteId())) {
                                jsonResponseObj.put("vendorwebsite_id",vendorWebsiteResponseBean.getVendorWebsiteId());
                                Text okText = new OkText("The colors for the website were saved successfully..","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                Text errorText = new ErrorText("Oops!! We were unable to publish the website colors. Please try again later.","err_mssg") ;
                                arrErrorText.add(errorText);
                                responseStatus = RespConstants.Status.ERROR;
                            }
                        }

                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(websiteColor - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(websiteColor - 001)","err_mssg") ;
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