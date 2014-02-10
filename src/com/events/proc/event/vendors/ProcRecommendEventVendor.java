package com.events.proc.event.vendors;

import com.events.bean.event.vendor.EventVendorBean;
import com.events.bean.event.vendor.EventVendorRequestBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.vendor.BuildEventVendor;
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
 * Date: 2/10/14
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcRecommendEventVendor  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

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
                    String sUserId = ParseUtil.checkNull(loggedInUserBean.getUserId());
                    String sEventId =  ParseUtil.checkNull(request.getParameter("event_id"));
                    String sPartnerVendorId =  ParseUtil.checkNull(request.getParameter("partner_vendor_id"));
                    String sAction =  ParseUtil.checkNull(request.getParameter("action"));

                    EventVendorRequestBean eventVendorRequestBean = new EventVendorRequestBean();
                    eventVendorRequestBean.setEventId( sEventId );
                    eventVendorRequestBean.setVendorId( sPartnerVendorId );
                    eventVendorRequestBean.setUserId( sUserId );

                    BuildEventVendor buildEventVendor = new BuildEventVendor();
                    if("recommend".equalsIgnoreCase(  sAction )) {
                        EventVendorBean eventVendorBean = buildEventVendor.saveEventVendorRecommendation( eventVendorRequestBean );
                        if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId())){
                            jsonResponseObj.put("eventvendor_id" , eventVendorBean.getEventVendorId() );
                            jsonResponseObj.put("action_complete" ,"recommend");
                        }
                    } else if("remove_recommend".equalsIgnoreCase(  sAction )) {
                        if( buildEventVendor.deleteEventVendorRecommendation( eventVendorRequestBean ) ) {
                            jsonResponseObj.put("action_complete" ,"remove_recommend");
                        }
                    }

                    jsonResponseObj.put("partnervendor_id" ,sPartnerVendorId);
                    jsonResponseObj.put("event_id" ,sEventId);

                    Text okText = new OkText("Event Vendor changes are completed.","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(procRecommendEventVendor - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(procRecommendEventVendor - 001)","err_mssg") ;
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
