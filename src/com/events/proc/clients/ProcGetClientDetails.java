package com.events.proc.clients;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.vendors.AccessVendors;
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
 * Date: 12/18/13
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcGetClientDetails    extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {

                String sClientId = ParseUtil.checkNull(request.getParameter("clientid"));
                String sClientDataType = ParseUtil.checkNull(request.getParameter("clientdatatype"));

                if("".equalsIgnoreCase(sClientId)) {
                    appLogging.info("An invalid client ID was used");
                    Text errorText = new ErrorText("Please select a valid client","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                } else {
                    UserBean loggedInUserBean = new UserBean();
                    if(request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
                        loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);
                    }

                    if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {

                        VendorRequestBean vendorRequestBean = new VendorRequestBean();
                        vendorRequestBean.setUserId( loggedInUserBean.getUserId() );
                        AccessVendors accessVendor = new AccessVendors();
                        VendorBean vendorBean = accessVendor.getVendorByUserId( vendorRequestBean ) ;  // get  vendor from user id

                        if(vendorBean!=null && !"".equalsIgnoreCase(vendorBean.getVendorId() ))  {
                            ClientRequestBean clientRequestBean = new ClientRequestBean();
                            clientRequestBean.setClientId(sClientId);
                            clientRequestBean.setVendorId( vendorBean.getVendorId() );


                            AccessClients accessClients = new AccessClients();
                            if("contact_info".equalsIgnoreCase(sClientDataType)){

                                ClientResponseBean clientResponseBean = accessClients.getClientContactInfo(clientRequestBean);
                                if(clientResponseBean!=null ){

                                    ClientBean clientbean = clientResponseBean.getClientBean();
                                    UserBean userBean = clientResponseBean.getUserBean();
                                    UserInfoBean userInfoBean = clientResponseBean.getUserInfoBean();

                                    if( clientbean!=null && userBean!=null && userInfoBean!=null && !"".equalsIgnoreCase(clientResponseBean.getClientId()) &&
                                            !"".equalsIgnoreCase(clientResponseBean.getUserId()) && !"".equalsIgnoreCase(clientResponseBean.getUserInfoId() )) {
                                        jsonResponseObj.put("client_data",clientbean.toJson());
                                        jsonResponseObj.put("user_data",userBean.toJson());
                                        jsonResponseObj.put("user_info_data",userInfoBean.toJson());

                                        Text okText = new OkText("The client contact_info were loaded successfully","status_mssg") ;
                                        arrOkText.add(okText);
                                        responseStatus = RespConstants.Status.OK;

                                    } else {
                                        appLogging.info("One or more of the clients data was invalid - " + clientResponseBean);
                                        Text errorText = new ErrorText("Oops!! We were unable to process your request. Please try again later.(getclientdetails - 001)","err_mssg") ;
                                        arrErrorText.add(errorText);

                                        responseStatus = RespConstants.Status.ERROR;
                                    }
                                } else {
                                    appLogging.info("The client id " + sClientId + " could not be loaded");
                                    Text errorText = new ErrorText("Oops!! We were unable to process your request. Please try again later.(getclientdetails - 002)","err_mssg") ;
                                    arrErrorText.add(errorText);

                                    responseStatus = RespConstants.Status.ERROR;
                                }
                            } else{
                                Text errorText = new ErrorText("Oops!! We were unable to process your request. Please try again later.(getclientdetails - 003)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        }
                    }

                }
            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;

            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(getclientdetails - 001)","err_mssg") ;
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
