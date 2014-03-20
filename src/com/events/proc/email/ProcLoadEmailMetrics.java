package com.events.proc.email;

import com.events.bean.common.TrackerEmailBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.common.tracker.AccessTrackerEmailOpened;
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
 * Date: 3/20/14
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadEmailMetrics extends HttpServlet {
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
                    String sMetricsType =  ParseUtil.checkNull(request.getParameter("metric_type"));

                    if(Utility.isNullOrEmpty(sEventId) || Utility.isNullOrEmpty(sEventEmailId) || Utility.isNullOrEmpty(sMetricsType)){
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEmailMetrics - 003)","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        TrackerEmailBean trackerEmailBean = new TrackerEmailBean();
                        trackerEmailBean.setEventEmailId( sEventEmailId );
                        trackerEmailBean.setMetricType( Constants.EMAIL_METRIC_TYPE.valueOf( sMetricsType ));

                        if( Constants.EMAIL_METRIC_TYPE.valueOf( sMetricsType ).toString().equalsIgnoreCase(Constants.EMAIL_METRIC_TYPE.EMAIL_OPENED.toString())){
                            AccessTrackerEmailOpened accessTrackerEmailOpened = new AccessTrackerEmailOpened();
                            Long lNumOfTimesViewed = accessTrackerEmailOpened.getTotalNumOfTimesEmailViewed( trackerEmailBean );
                            jsonResponseObj.put("total_num_of_times_viewed" , ParseUtil.LToS(lNumOfTimesViewed) );

                            ArrayList<TrackerEmailBean> arrAllUsersWhoOpenedEmail = accessTrackerEmailOpened.getUsersWhoViewedEmail(trackerEmailBean);
                            Long lTotalNumOfUsers = 0L;
                            JSONObject jsonAllUsersWhoViewedEvent = accessTrackerEmailOpened.getJsonUsersWhoViewedEmail(arrAllUsersWhoOpenedEmail);
                            if(jsonAllUsersWhoViewedEvent!=null){
                                lTotalNumOfUsers = jsonAllUsersWhoViewedEvent.optLong("total_num_of_users");
                                if(lTotalNumOfUsers>0){
                                    jsonResponseObj.put("users_who_viewed_email", jsonAllUsersWhoViewedEvent );
                                }
                            }
                            jsonResponseObj.put("total_num_of_users_who_viewed_email" , lTotalNumOfUsers);

                            Text okText = new OkText("Email Opened Metric was successfully loaded.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;

                        } else if( Constants.EMAIL_METRIC_TYPE.valueOf( sMetricsType ).toString().equalsIgnoreCase(Constants.EMAIL_METRIC_TYPE.LINKS_CLICKED.toString()) ) {

                        } else {
                            Text errorText = new ErrorText("We were unable to process your request at this time. Please try again later.(loadEmailMetrics - 004)","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                        }
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEmailMetrics - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEmailMetrics - 001)","err_mssg") ;
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