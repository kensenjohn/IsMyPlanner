package com.events.proc.email;

import com.events.bean.common.email.EveryEventEmailRequestBean;
import com.events.bean.common.email.EveryEventEmailResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.email.EveryEventEmail;
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
 * Date: 1/13/14
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadAllEventEmails extends HttpServlet {
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
                    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));

                    EveryEventEmailRequestBean everyEventEmailRequestBean = new EveryEventEmailRequestBean();
                    everyEventEmailRequestBean.setEventId( sEventId );

                    EveryEventEmail everyEventEmail = new EveryEventEmail();
                    EveryEventEmailResponseBean everyEventEmailResponseBean = everyEventEmail.getEveryEventEmail(everyEventEmailRequestBean);
                    if(everyEventEmailResponseBean!=null){
                        JSONObject jsonEveryEventEmailObject = everyEventEmail.getEveryEventEmailJson(everyEventEmailResponseBean);

                        Integer iNumOfEventEmailObjs =  0;
                        if(jsonEveryEventEmailObject!=null) {
                            iNumOfEventEmailObjs =  jsonEveryEventEmailObject.optInt("num_of_eventemails");
                            jsonResponseObj.put("every_eventemail",jsonEveryEventEmailObject);
                        }
                        jsonResponseObj.put("num_of_eventemails" , ParseUtil.iToS(iNumOfEventEmailObjs) );

                    }
                    Text okText = new OkText("Oops!! We were unable to process your request. Please try again later.(loadAllEventEmail - 003)","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllEventEmail - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString() );
                responseObject = DataSecurityChecker.getInsecureInputResponse( this.getClass().getName() );
            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllEventEmail - 001)","err_mssg") ;
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