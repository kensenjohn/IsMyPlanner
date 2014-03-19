package com.events.proc.email;

import com.events.bean.DateObject;
import com.events.bean.common.email.EmailSchedulerRequestBean;
import com.events.bean.common.email.EventEmailBean;
import com.events.bean.common.email.EventEmailRequestBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.email.scheduler.BuildEmailScheduler;
import com.events.common.email.setting.BuildEventEmail;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import org.json.JSONObject;
import org.owasp.esapi.ESAPI;
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
 * Date: 1/10/14
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveEmail extends HttpServlet {
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

                    String sEventEmailId = ParseUtil.checkNull(request.getParameter("event_email_id"));
                    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
                    String sEmailSubject = ParseUtil.checkNull(request.getParameter("email_subject"));
                    String sEmailBody = ESAPI.encoder().decodeForHTML(ParseUtil.checkNull(request.getParameter("email_body")));
                    String sEmailSendRule = ParseUtil.checkNull(request.getParameter("email_send_rules"));

                    boolean isEmailSendScheduleEnabled = ParseUtil.sTob(request.getParameter("email_schedule_enabled"));
                    String sEmailSendDay = ParseUtil.checkNull(request.getParameter("email_send_day"));
                    String sEmailSendTime = ParseUtil.checkNull(request.getParameter("email_send_time"));
                    String sEmailSendTimeZone = ParseUtil.checkNull(request.getParameter("email_send_timezone"));

                    DateObject eventEmailScheduleDate = new DateObject();
                    if(isEmailSendScheduleEnabled) {
                        eventEmailScheduleDate = DateSupport.convertTime(sEmailSendDay + " " + sEmailSendTime,
                                DateSupport.getTimeZone(sEmailSendTimeZone), "dd MMMMM, yyyy hh:mm aaa",
                                DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);
                    }

                    if(Utility.isNullOrEmpty(sEmailSubject) || Utility.isNullOrEmpty(sEmailBody)
                            || Utility.isNullOrEmpty(sEmailSendRule)  ){
                        Text errorText = new ErrorText("Please fill in all required fields.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else if ( isEmailSendScheduleEnabled && (Utility.isNullOrEmpty(sEmailSendDay) || Utility.isNullOrEmpty(sEmailSendTime)
                            || Utility.isNullOrEmpty(sEmailSendTimeZone)) ) {
                        Text errorText = new ErrorText("Please select a valid schedule time.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else if(isEmailSendScheduleEnabled && (eventEmailScheduleDate.getMillis() <= DateSupport.getEpochMillis())   ) {
                        Text errorText = new ErrorText("Please select a future schedule date to send the email.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        EventEmailBean tmpEventEmailBean = new EventEmailBean();
                        tmpEventEmailBean.setEventEmailId(sEventEmailId);
                        tmpEventEmailBean.setEventId(sEventId);
                        tmpEventEmailBean.setSubject(sEmailSubject);
                        tmpEventEmailBean.setHtmlBody( sEmailBody );
                        tmpEventEmailBean.setTextBody( sEmailBody );
                        tmpEventEmailBean.setUserId( loggedInUserBean.getUserId() );

                        EventEmailRequestBean eventEmailRequestBean = new EventEmailRequestBean();
                        eventEmailRequestBean.setEventEmailBean(tmpEventEmailBean);
                        eventEmailRequestBean.setEventEmailScheduleDate(eventEmailScheduleDate);
                        eventEmailRequestBean.setSendEmailRules( Constants.SEND_EMAIL_RULES.valueOf(sEmailSendRule));
                        eventEmailRequestBean.setEmailSendDay( sEmailSendDay );
                        eventEmailRequestBean.setEmailSendTime( sEmailSendTime);
                        eventEmailRequestBean.setEmailSendTimeZone( sEmailSendTimeZone );
                        eventEmailRequestBean.setEmailScheduleEnabled(isEmailSendScheduleEnabled );
                        if(isEmailSendScheduleEnabled) {
                            eventEmailRequestBean.setUserAction( EventEmailRequestBean.ACTION.SCHEDULE_ENABLED);
                        } else {
                            eventEmailRequestBean.setUserAction( EventEmailRequestBean.ACTION.ONLY_SAVE_SETTING);
                        }

                        BuildEventEmail buildEventEmail = new BuildEventEmail();
                        EventEmailBean eventEmailBean = buildEventEmail.saveEventEmail(eventEmailRequestBean);

                        if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId())) {

                            BuildEmailScheduler buildEmailScheduler = new BuildEmailScheduler();

                            EmailSchedulerRequestBean emailSchedulerRequestBean = new EmailSchedulerRequestBean();
                            emailSchedulerRequestBean.setEventEmailBean( eventEmailBean );
                            if( isEmailSendScheduleEnabled) {
                                emailSchedulerRequestBean.setScheduledSendDate( eventEmailScheduleDate.getMillis() );
                                emailSchedulerRequestBean.setScheduledSendHumanDate(eventEmailScheduleDate.getFormattedTime());
                                emailSchedulerRequestBean.setSchedulerStatus(Constants.SCHEDULER_STATUS.NEW_SCHEDULE);

                                buildEmailScheduler.saveSchedule( emailSchedulerRequestBean );
                            } else {
                                buildEmailScheduler.removeSchedule( emailSchedulerRequestBean );
                            }



                            appLogging.info("Save Email Send Schedule :  " + emailSchedulerRequestBean.getScheduledSendHumanDate() + " by userid : " + loggedInUserBean.getUserId() );



                            jsonResponseObj.put("event_email_bean",eventEmailBean.toJson());
                            Text okText = new OkText("The email was saved successfully.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            appLogging.info("Unable to save the email " + tmpEventEmailBean );
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEmail - 003)","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                        }
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEmail - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEmail - 001)","err_mssg") ;
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