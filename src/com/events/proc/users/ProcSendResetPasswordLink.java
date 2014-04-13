package com.events.proc.users;

import com.events.bean.users.ForgotPasswordBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.ForgotPassword;
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
 * Date: 1/8/14
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSendResetPasswordLink   extends HttpServlet {
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
                Validator instance = ESAPI.validator();
                if( !instance.isValidInput( "resetEmail",sEmailAddress,"Email",250,false ) ) {
                    appLogging.debug("Invalid email used " + ParseUtil.checkNull(sEmailAddress) );
                    Text errorText = new ErrorText("Please use a valid email address to reset your password.","err_mssg") ;
                    arrErrorText.add(errorText);
                    responseStatus = RespConstants.Status.ERROR;
                } else {
                    ForgotPassword forgotPassword = new ForgotPassword(sEmailAddress);
                    ForgotPasswordBean forgotPasswordBean = forgotPassword.createUserRequest();

                    if(forgotPasswordBean!=null && Utility.isNullOrEmpty(forgotPasswordBean.getForgotPasswordId())) {
                        appLogging.info("There was error trying to send forgot password to email : " + ParseUtil.checkNull(sEmailAddress));
                    }
                    jsonResponseObj.put("email",sEmailAddress);
                    Text okText = new OkText("No event present","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;
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