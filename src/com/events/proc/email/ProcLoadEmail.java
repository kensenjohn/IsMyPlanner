package com.events.proc.email;

import com.events.bean.common.email.*;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.email.scheduler.AccessEmailScheduler;
import com.events.common.email.setting.AccessEventEmail;
import com.events.common.email.setting.EventEmailFeature;
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
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadEmail extends HttpServlet {
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
                    String sEventEmailId =  ParseUtil.checkNull(request.getParameter("eventemail_id"));

                    if( !Utility.isNullOrEmpty(sEventId) && !Utility.isNullOrEmpty(sEventEmailId) ) {
                        EventEmailRequestBean eventEmailRequestBean = new EventEmailRequestBean();
                        eventEmailRequestBean.setEventId( sEventId );
                        eventEmailRequestBean.setEventEmailId( sEventEmailId );

                        AccessEventEmail accessEventEmail = new AccessEventEmail();
                        EventEmailResponseBean eventEmailResponseBean = accessEventEmail.getEventEmail( eventEmailRequestBean );

                        if(eventEmailResponseBean!=null ){
                            EventEmailBean eventEmailBean = eventEmailResponseBean.getEventEmailBean();

                            if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId())) {
                                ArrayList < EventEmailFeatureBean > arrEventEmailFeatureBean = new ArrayList<EventEmailFeatureBean>();
                                arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.send_email_rule) );
                                arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.email_send_day) );
                                arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.email_send_time) );
                                arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.email_send_timezone) );
                                arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.action) );


                                EventEmailFeature eventEmailFeature = new EventEmailFeature();
                                ArrayList<EventEmailFeatureBean> arrMultipleFeatureBean = eventEmailFeature.getMultipleFeatures(arrEventEmailFeatureBean , eventEmailBean.getEventEmailId() );

                                jsonResponseObj.put("event_email_bean",eventEmailBean.toJson());
                                if(arrMultipleFeatureBean!=null && !arrMultipleFeatureBean.isEmpty()) {
                                    for(EventEmailFeatureBean eventEmailFeatureBean : arrMultipleFeatureBean ){
                                        jsonResponseObj.put(eventEmailFeatureBean.getFeatureName(),eventEmailFeatureBean.getValue());
                                    }
                                }

                                // Email Schedule
                                {
                                    EmailSchedulerBean emailSchedulerBean = accessEventEmail.getEventEmailSchedule( eventEmailBean );
                                    if(emailSchedulerBean!=null && !Utility.isNullOrEmpty(emailSchedulerBean.getEmailScheduleId())) {
                                        jsonResponseObj.put("schedule_status",emailSchedulerBean.getScheduleStatus());
                                    }
                                }

                                Text okText = new OkText("Email was successfully loaded..","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                appLogging.info("eventEmailBean was  null Unable to load " + ParseUtil.checkNullObject(eventEmailBean) );
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEventEmail - 004)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }


                        } else {
                            appLogging.info("Response was null and as Unable to load eventemail" + ParseUtil.checkNullObject(eventEmailResponseBean) );
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEventEmail - 003)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                    } else {

                    }

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