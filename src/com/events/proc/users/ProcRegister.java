package com.events.proc.users;

import com.events.bean.users.PasswordRequestBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.users.EditUserException;
import com.events.common.exception.users.EditUserInfoException;
import com.events.common.exception.users.ManagePasswordException;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import com.events.users.BuildUsers;
import com.events.vendors.BuildVendors;
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
 * Date: 12/12/13
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcRegister  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {

                String sFirstName = ParseUtil.checkNull(request.getParameter("registerFirstName"));
                String sLastName = ParseUtil.checkNull(request.getParameter("registerLastName"));
                String sEmail = ParseUtil.checkNull(request.getParameter("registerEmail"));
                String sPassword = ParseUtil.checkNull(request.getParameter("registerPassword"));
                String sVerifyPassword = ParseUtil.checkNull(request.getParameter("registerVerifyPassword"));
                boolean isPlanner = ParseUtil.sTob( request.getParameter("registerIsPlanner") );
                isPlanner = true; // default set it to true.

                if("".equalsIgnoreCase(sFirstName) || "".equalsIgnoreCase(sLastName) || "".equalsIgnoreCase(sEmail)
                        || "".equalsIgnoreCase(sPassword) || "".equalsIgnoreCase(sVerifyPassword)) {
                    appLogging.info("Please fill in all required fields");
                    Text errorText = new ErrorText("Please fill in all required fields","account_num") ;
                    arrErrorText.add(errorText);
                    responseStatus = RespConstants.Status.ERROR;

                } else if ( !sPassword.equalsIgnoreCase(sVerifyPassword)) {
                    appLogging.info("Password does not match.");
                    Text errorText = new ErrorText("Password does not match","account_num") ;
                    arrErrorText.add(errorText);
                    responseStatus = RespConstants.Status.ERROR;
                } else {

                    UserRequestBean userRequestBean = new UserRequestBean();
                    userRequestBean.setEmail( sEmail );

                    boolean isError = false;
                    AccessUsers accessUsers = new AccessUsers();
                    UserBean tmpExistingUserBean = accessUsers.getUserByEmail(userRequestBean);

                    if( tmpExistingUserBean!=null && "".equalsIgnoreCase(tmpExistingUserBean.getUserId() )) {


                        userRequestBean.setFirstName(sFirstName);
                        userRequestBean.setLastName(sLastName);

                        PasswordRequestBean passwordRequestBean = new PasswordRequestBean();
                        passwordRequestBean.setPassword(sPassword);
                        userRequestBean.setPasswordRequestBean(passwordRequestBean);

                        BuildUsers buildUsers = new BuildUsers();
                        try {
                            userRequestBean.setPlanner(isPlanner);

                            UserBean userBean = buildUsers.registerUser(userRequestBean);
                            if(userBean!=null && "".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserId())))  {
                                appLogging.info("There was an error registering a new user " + ParseUtil.checkNullObject(userBean));
                                isError = true;
                            } else {
                                userRequestBean.setUserInfoId( userBean.getUserInfoId() );
                                UserInfoBean userInfoBean = accessUsers.getUserInfoFromInfoId(userRequestBean);
                                userBean.setUserInfoBean( userInfoBean );

                                appLogging.info("Successfully registered user "  + ParseUtil.checkNullObject(userBean) );
                                request.getSession(false).setAttribute(Constants.USER_LOGGED_IN_BEAN,userBean);
                            }
                        } catch (EditUserException e) {
                            appLogging.error("Could not create User " + ExceptionHandler.getStackTrace(e));
                            isError = true;
                        } catch (EditUserInfoException e) {
                            appLogging.error("Could not create User Info Data " + ExceptionHandler.getStackTrace(e));
                            isError = true;
                        } catch (ManagePasswordException e) {
                            appLogging.error("Creation of Password failed " + ExceptionHandler.getStackTrace(e));
                            isError = true;
                        }

                        if(!isError) {
                            Text okText = new OkText("Registration was completed successfully.","status_mssg") ;
                            arrOkText.add(okText);

                            responseStatus = RespConstants.Status.OK;
                        } else {
                            appLogging.error("An error occurred while processing registration request." + ParseUtil.checkNullObject(userRequestBean));
                            Text errText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.","err_mssg") ;
                            arrErrorText.add(errText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                    } else {
                        appLogging.info("This user already exists " + userRequestBean.getEmail());
                        Text errorText = new ErrorText("Email already exists. Please login or use the forgot password feature.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(001)","err_mssg") ;
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
