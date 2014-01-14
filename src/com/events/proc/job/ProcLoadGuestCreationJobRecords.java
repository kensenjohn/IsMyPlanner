package com.events.proc.job;

import com.events.bean.job.GuestCreateJobBean;
import com.events.bean.job.GuestCreateJobRequestBean;
import com.events.bean.job.GuestCreateJobResponseBean;
import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.UploadFile;
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
 * Date: 1/6/14
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadGuestCreationJobRecords  extends HttpServlet {
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

                    GuestCreateJobRequestBean guestCreateJobRequestBean = new GuestCreateJobRequestBean();
                    guestCreateJobRequestBean.setEventId(sEventId);

                    GuestCreateJob guestCreationJob = new GuestCreateJob();
                    GuestCreateJobResponseBean guestCreateJobResponseBean = guestCreationJob.getGuestCreationJobByEvent(guestCreateJobRequestBean);
                    if(guestCreateJobResponseBean!=null && guestCreateJobResponseBean.getGuestCreateJobBean()!=null
                            && !Utility.isNullOrEmpty(guestCreateJobResponseBean.getGuestCreateJobBean().getGuestCreateJobId())) {
                        GuestCreateJobBean guestCreateJobBean = guestCreateJobResponseBean.getGuestCreateJobBean();

                        UploadRequestBean uploadRequestBean = new UploadRequestBean();
                        uploadRequestBean.setUploadId( guestCreateJobBean.getUploadId() );

                        UploadFile uploadFile = new UploadFile();
                        UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo(uploadRequestBean);

                        UploadBean uploadBean = uploadResponseBean.getUploadBean();
                        if(uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId())){
                            jsonResponseObj.put("file_name",uploadBean.getFilename());
                            jsonResponseObj.put("upload_id",uploadBean.getUploadId());
                            jsonResponseObj.put("job_status",guestCreateJobBean.getJobStatus().getStatus());
                        }
                    }
                    Text okText = new OkText("Guest Create Job uploaded file info.","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;
                } else {
                    appLogging.info("Invalid request in Proc Page  (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(guestCreationJob - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString() );
                responseObject = DataSecurityChecker.getInsecureInputResponse( this.getClass().getName() );
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
