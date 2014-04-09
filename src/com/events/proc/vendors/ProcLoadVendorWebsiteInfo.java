package com.events.proc.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.bean.vendors.website.VendorWebsiteResponseBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.vendors.AccessVendors;
import com.events.vendors.website.AccessVendorWebsite;
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
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadVendorWebsiteInfo extends HttpServlet {
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
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
                    String sUserId = ParseUtil.checkNull(loggedInUserBean.getUserId());

                    VendorRequestBean vendorRequestBean = new VendorRequestBean();
                    vendorRequestBean.setUserId(sUserId);

                    AccessVendors accessVendors = new AccessVendors();
                    VendorBean vendorBean = accessVendors.getVendorByUserId(vendorRequestBean);

                    if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {
                        VendorWebsiteRequestBean vendorWebsiteRequestBean = new VendorWebsiteRequestBean();
                        vendorWebsiteRequestBean.setVendorId( vendorBean.getVendorId() );

                        AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
                        VendorWebsiteResponseBean vendorWebsiteResponseBean = accessVendorWebsite.getVendorWebsiteByVendorId(vendorWebsiteRequestBean);

                        if(vendorWebsiteResponseBean!=null && vendorWebsiteResponseBean.getVendorWebsiteBean()!=null
                                && !Utility.isNullOrEmpty(vendorWebsiteResponseBean.getVendorWebsiteBean().getVendorWebsiteId())){
                            jsonResponseObj.put("vendor_website", vendorWebsiteResponseBean.getVendorWebsiteBean().toJson() );

                            ArrayList<VendorWebsiteFeatureBean> arrVendorWebsiteFeatureBean = vendorWebsiteResponseBean.getArrVendorWebsiteFeatureBean();
                            if(arrVendorWebsiteFeatureBean!=null && !arrVendorWebsiteFeatureBean.isEmpty()) {
                                for(VendorWebsiteFeatureBean vendorWebsiteFeatureBean : arrVendorWebsiteFeatureBean ) {
                                    jsonResponseObj.put( vendorWebsiteFeatureBean.getFeatureName() , vendorWebsiteFeatureBean.getValue() );
                                }
                            }
                        }
                        String imageHost = ParseUtil.checkNull(applicationConfig.get(Constants.IMAGE_HOST));
                        jsonResponseObj.put("imagehost", imageHost );
                        jsonResponseObj.put("bucket", ParseUtil.checkNull(applicationConfig.get(Constants.AMAZON.S3_BUCKET.getPropName())) );

                        Folder folder = new Folder();
                        String sUserFolderName = folder.getFolderName( Constants.USER_TYPE.VENDOR, vendorBean.getVendorId() );
                        jsonResponseObj.put("foldername", sUserFolderName );

                        jsonResponseObj.put("vendor", vendorBean.toJson() );
                        Text okText = new OkText("The landing page was loaded successfully.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;


                    }  else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadVendorWebsite - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadVendorWebsite - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadVendorWebsite - 001)","err_mssg") ;
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