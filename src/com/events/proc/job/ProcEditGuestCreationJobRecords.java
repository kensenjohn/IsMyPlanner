package com.events.proc.job;

import com.events.bean.job.GuestCreateJobRequestBean;
import com.events.bean.job.GuestCreateJobResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.job.GuestCreateJob;
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
 * Date: 1/3/14
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcEditGuestCreationJobRecords   extends HttpServlet {
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
                    String sUploadId = ParseUtil.checkNull(request.getParameter("upload_id"));
                    String sJobStatus = ParseUtil.checkNull(request.getParameter("job_status"));

                    if( !Utility.isNullOrEmpty(sEventId) && !Utility.isNullOrEmpty(sUploadId) && !Utility.isNullOrEmpty(sJobStatus)) {
                        GuestCreateJobRequestBean guestCreateJobRequestBean = new GuestCreateJobRequestBean();
                        guestCreateJobRequestBean.setEventId(sEventId);
                        guestCreateJobRequestBean.setUploadId(sUploadId);
                        guestCreateJobRequestBean.setJobStatus( Constants.JOB_STATUS.valueOf(sJobStatus));
                        guestCreateJobRequestBean.setUserId(loggedInUserBean.getUserId());

                        GuestCreateJob guestCreationJob = new GuestCreateJob();
                        GuestCreateJobResponseBean guestCreateJobResponseBean = guestCreationJob.getGuestCreationJobByEvent(guestCreateJobRequestBean);

                        if(guestCreateJobResponseBean!=null && guestCreateJobResponseBean.getGuestCreateJobBean()!=null) {
                            guestCreateJobRequestBean.setGuestCreateJobId( ParseUtil.checkNull(guestCreateJobResponseBean.getGuestCreateJobBean().getGuestCreateJobId() ));
                        }

                        final String READY_TO_PICK_STATUS = Constants.JOB_STATUS.READY_TO_PICK.getStatus();
                        boolean isSaveRequest = false;
                        if(READY_TO_PICK_STATUS.equalsIgnoreCase(sJobStatus) &&
                                ( READY_TO_PICK_STATUS.equalsIgnoreCase(guestCreateJobRequestBean.getJobStatus().getStatus()) ||
                                        Constants.JOB_STATUS.PRELIM_STATE.getStatus().equalsIgnoreCase(guestCreateJobRequestBean.getJobStatus().getStatus()))) {
                            isSaveRequest = true;
                        } else if( Constants.JOB_STATUS.PRELIM_STATE.getStatus().equalsIgnoreCase(sJobStatus)  ) {
                            isSaveRequest = true;
                        }
                        if( isSaveRequest ) {
                            appLogging.info("GuestCreateJobId : " + guestCreateJobRequestBean.getGuestCreateJobId() );
                            GuestCreateJobResponseBean savedGuestCreateJobResponseBean = guestCreationJob.saveGuestCreationJob(guestCreateJobRequestBean);

                            if(savedGuestCreateJobResponseBean!=null && !Utility.isNullOrEmpty(savedGuestCreateJobResponseBean.getGuestCreateJobId())) {
                                jsonResponseObj.put("response_id",savedGuestCreateJobResponseBean.getGuestCreateJobId());
                                appLogging.info("Sucessful edited the Guest Creation job record. " );
                                Text okText = new OkText("The file has been successfully uploaded. You can now execute the Guest Creation process.","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                appLogging.error("Error while edited the Guest Creation job record. " + ParseUtil.checkNullObject(savedGuestCreateJobResponseBean));
                                Text errorText = new ErrorText("Oops!! There was an error processing your request. Please try again later.","status_mssg") ;
                                arrErrorText.add(errorText);
                                responseStatus = RespConstants.Status.ERROR;
                            }
                        } else {
                            appLogging.error("Error in Job status. Job status cannot be changed - " + ParseUtil.checkNull(sJobStatus) + " - " + ParseUtil.checkNullObject(guestCreateJobRequestBean));
                            Text errorText = new ErrorText("Oops!! There was an error processing your request. Please try again later.","status_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                        }




                    } else {
                        appLogging.info("Invalid request in Proc Page sEventId" + sEventId + " sUploadId : " + sUploadId + " sJobStatus : " + sJobStatus );
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(guestCreationJob - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }


                } else {
                    appLogging.info("Invalid request in Proc Page  (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(guestCreationJob - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(guestCreationJob - 001)","err_mssg") ;
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
