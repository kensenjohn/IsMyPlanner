package com.events.proc.notify;

import com.events.bean.common.notify.NotifyBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.notify.Notification;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/8/14
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadUnreadNotifications extends HttpServlet {
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

                    NotifyBean notifyBean = new NotifyBean();
                    notifyBean.setTo(loggedInUserBean.getUserId());

                    Notification notification = new Notification();
                    HashMap<String,NotifyBean> hmUnReadRecords = notification.getUnreadNotifyRecords(notifyBean);
                    Integer iNumOfUnReadRecords = 0;
                    if(hmUnReadRecords!=null && !hmUnReadRecords.isEmpty()){
                        JSONObject jsonUnReadNotifyBean = new JSONObject();

                        for(Map.Entry<String,NotifyBean> mapUnReadRecords : hmUnReadRecords.entrySet()) {
                            jsonUnReadNotifyBean.put( ParseUtil.iToS(iNumOfUnReadRecords),mapUnReadRecords.getValue().toJson());
                            iNumOfUnReadRecords++;
                        }
                        if(iNumOfUnReadRecords>0){
                            jsonResponseObj.put("unread_notifications" , jsonUnReadNotifyBean );
                        }
                    }
                    jsonResponseObj.put("num_of_unread_notifications" , iNumOfUnReadRecords );

                    Text okText = new OkText("UnRead Notifications were successfully loaded","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllEventEmail - 002)","err_mssg") ;
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