package com.events.proc.todo;

import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
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
 * Date: 5/13/14
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadTodoFilterParams extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) ) {

                    AccessUsers accessUsers = new AccessUsers();
                    ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser(loggedInUserBean);

                    if(parentTypeBean!=null){
                        if(parentTypeBean.isUserAVendor()) {
                            UserRequestBean userRequestBean = new UserRequestBean();
                            userRequestBean.setVendorId( parentTypeBean.getVendorId() );
                            ArrayList<UserBean> arrVendorUserBean = accessUsers.getAllVendorUsers( userRequestBean );
                            ArrayList<UserBean> arrVendorClientUserBean = accessUsers.getAllVendorClientUsers( userRequestBean );

                            JSONObject jsonVendorUserBean = accessUsers.getJsonUserBean( arrVendorUserBean );
                            Long lNumfOfVendorUsers = 0L;
                            if(jsonVendorUserBean!=null){
                                lNumfOfVendorUsers = jsonVendorUserBean.optLong("num_of_userbean");
                                if(lNumfOfVendorUsers>0){
                                    jsonResponseObj.put("vendor_users", jsonVendorUserBean );
                                }
                            }
                            jsonResponseObj.put("num_of_vendor_userbean", lNumfOfVendorUsers );

                            JSONObject jsonVendorClientUserBean = accessUsers.getJsonUserBean( arrVendorClientUserBean );
                            Long lNumfOfVendorClientUsers = 0L;
                            if(jsonVendorClientUserBean!=null){
                                lNumfOfVendorClientUsers = jsonVendorClientUserBean.optLong("num_of_userbean");
                                if(lNumfOfVendorClientUsers>0){
                                    jsonResponseObj.put("vendor_client_users", jsonVendorClientUserBean );
                                }
                            }
                            jsonResponseObj.put("num_of_vendor_client_userbean", lNumfOfVendorClientUsers );
                        }
                    }

                    Text okText = new OkText("Loading of ToDo Filter params complete","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadTodoParam - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadTodoParam - 001)","err_mssg") ;
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

