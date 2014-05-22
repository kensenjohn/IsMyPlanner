package com.events.proc.dashboard;

import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.email.send.QuickMailSendThread;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
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
 * Date: 5/21/14
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcRequestDemo extends HttpServlet {
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
                String sName = ParseUtil.checkNull(request.getParameter("demo_name"));
                String sEmail = ParseUtil.checkNull( request.getParameter("demo_email") );

                boolean isSuccess = false;
                if(Utility.isNullOrEmpty(sEmail)){
                    Text errorText = new ErrorText("Please use a valid email address.","err_mssg") ;
                    arrErrorText.add(errorText);
                    responseStatus = RespConstants.Status.ERROR;
                } else {
                    EmailQueueBean emailQueueBean = new EmailQueueBean();
                    emailQueueBean.setEmailSubject("IsMyPlanner.com : Demo Request");
                    emailQueueBean.setFromAddress( "webmaster@ismyplanner.com" );
                    emailQueueBean.setFromAddressName( "webmaster@ismyplanner.com" );

                    emailQueueBean.setToAddress( "kjohn@smarasoft.com" );
                    emailQueueBean.setToAddressName( "kjohn@smarasoft.com" );
                    emailQueueBean.setHtmlBody("Demo request from : "+ sEmail + " name : " + sName );
                    emailQueueBean.setTextBody("Demo request from : "+ sEmail + " name : " + sName );

                    Thread quickEmail = new Thread(new QuickMailSendThread( emailQueueBean), "Quick Email Password Reset");
                    quickEmail.start();

                    Text okText = new OkText("Thank you. We will get in touch with you to schedule a demo.","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;
                    isSuccess = true;

                }
                jsonResponseObj.put("demo_request_complete",isSuccess);

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEventBudgetCategory - 001)","err_mssg") ;
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
