package com.events.proc.event.guest;

import com.events.bean.event.guest.GuestRequestBean;
import com.events.bean.event.guest.GuestResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.guest.BuildGuest;
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
 * User: kensen
 * Date: 1/22/14
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcDeleteEventGuest extends HttpServlet {
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
                    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
                    String sGuestGroupId = ParseUtil.checkNull(request.getParameter("guestgroup_id"));
                    String sEventGuestGroupId = ParseUtil.checkNull(request.getParameter("event_guestgroup_id"));

                    if(!Utility.isNullOrEmpty(sGuestGroupId)) {
                        GuestRequestBean guestRequestBean = new GuestRequestBean();
                        guestRequestBean.setEventId( sEventId );
                        guestRequestBean.setGuestGroupId( sGuestGroupId );
                        guestRequestBean.setEventGuestGroupId(sEventGuestGroupId );

                        BuildGuest buildGuest = new BuildGuest();
                        GuestResponseBean deleteEventGuestResponseBean = buildGuest.deleteEventGuestGroup( guestRequestBean );
                        if(deleteEventGuestResponseBean!=null && deleteEventGuestResponseBean.isGuestDeleted()) {
                            Text okText = new OkText("The guest was deleted successfully. ","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {

                            Text errorText = new ErrorText("Oops!! We were unable to delete the guest. Please refresh the screen and try again.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                        jsonResponseObj.put("is_deleted",deleteEventGuestResponseBean.isGuestDeleted());
                        jsonResponseObj.put("deleted_eventguestgroup_id",sEventGuestGroupId);

                    } else {
                        Text errorText = new ErrorText("Please select a valid guest to delete","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEvent - 002)","err_mssg") ;
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
