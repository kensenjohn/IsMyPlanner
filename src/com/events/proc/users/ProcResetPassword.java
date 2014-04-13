package com.events.proc.users;

import com.events.bean.users.ForgotPasswordBean;
import com.events.bean.users.PasswordRequestBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.data.users.ForgotPasswordData;
import com.events.json.*;
import com.events.users.AccessUsers;
import com.events.users.ForgotPassword;
import com.events.users.ManageUserPassword;
import org.json.JSONObject;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Validator;
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
 * Date: 1/9/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcResetPassword   extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                String sEmailAddress = ParseUtil.checkNull(request.getParameter("resetEmail"));
                String sPassword = ParseUtil.checkNull(request.getParameter("resetPassword"));
                String sConfirmPassword = ParseUtil.checkNull(request.getParameter("resetConfirmPassword"));
                String sSecureTokenId = ParseUtil.checkNull(request.getParameter("lotophagi"));
                Validator instance = ESAPI.validator();
                if( Utility.isNullOrEmpty(sSecureTokenId)) {
                    appLogging.info("Invalid Link Used " + ParseUtil.checkNull(sSecureTokenId) );
                    Text errorText = new ErrorText("An invalid link was used. The link was sent to your email.","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                } else if(!instance.isValidInput( "resetEmail",sEmailAddress,"Email",250,false )||  Utility.isNullOrEmpty(sPassword) || Utility.isNullOrEmpty(sConfirmPassword) ) {
                    appLogging.info("Invalid Email Address or password used " + ParseUtil.checkNull(sEmailAddress) + " " + ParseUtil.checkNull(sPassword) + " " + ParseUtil.checkNull(sConfirmPassword));
                    Text errorText = new ErrorText("Please fill in all required fields ","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }  else if( !sPassword.equalsIgnoreCase(sConfirmPassword)  ) {
                    appLogging.info("Password do not match" + ParseUtil.checkNull(sPassword) + " " + ParseUtil.checkNull(sConfirmPassword));
                    Text errorText = new ErrorText("Passwords do not match","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                } else {
                    UserRequestBean userRequestBean = new UserRequestBean();
                    userRequestBean.setEmail( sEmailAddress );

                    AccessUsers accessUsers = new AccessUsers();
                    UserBean userBean = accessUsers.getUserByEmail(userRequestBean);
                    if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())){
                        ForgotPassword forgotPassword = new ForgotPassword(sEmailAddress);
                        ForgotPasswordBean forgotPasswordBean = forgotPassword.getForgotPasswordBean(sSecureTokenId, userBean.getUserId());

                        if( forgotPasswordBean!=null && forgotPasswordBean.isUsable() && (DateSupport.getEpochMillis() - forgotPasswordBean.getCreateDate()) < Constants.HOURS24_IN_MILLISEC ) {
                            PasswordRequestBean passwordRequestBean = new PasswordRequestBean();
                            passwordRequestBean.setPassword(sPassword);
                            passwordRequestBean.setUserId( userBean.getUserId() );
                            passwordRequestBean.setPasswordStatus(Constants.PASSWORD_STATUS.ACTIVE);

                            ManageUserPassword manageUserPassword = new ManageUserPassword();
                            Integer iNumOfRows = manageUserPassword.updatePassword(passwordRequestBean);
                            if(iNumOfRows>0){
                                Text okText = new OkText("Password was reset. Please login with the new password.","err_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;

                                ForgotPasswordData forgotpasswordData = new ForgotPasswordData();
                                forgotpasswordData.deactivateForgotPassword(forgotPasswordBean);  // deactivating the old request for forgot password
                            } else {
                                appLogging.info("Password cannot be reset " + ParseUtil.checkNullObject(forgotPasswordBean));
                                Text errorText = new ErrorText("Oops! You are trying to use an expired link. Please click \'Forgot Password\' and get a new link","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        } else {
                            appLogging.info("Password cannot be reset " + ParseUtil.checkNullObject(forgotPasswordBean));
                            Text errorText = new ErrorText("Oops! You are trying to use an expired link. Please click \'Forgot Password\' and get a new link","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
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