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
import com.events.json.ErrorText;
import com.events.json.RespConstants;
import com.events.json.RespObjectProc;
import com.events.json.Text;
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
public class ProcGuestCreateJob   extends HttpServlet {
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
                    /*
                        <input type="hidden" id="event_id" name="event_id" value="<%=sEventId%>">
    <input type="hidden" id="upload_id" name="upload_id" value="">
                     */
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
                        appLogging.info("GuestCreateJobId : " + guestCreateJobRequestBean.getGuestCreateJobId() );
                        GuestCreateJobResponseBean savedGuestCreateJobResponseBean = guestCreationJob.saveGuestCreationJob(guestCreateJobRequestBean);
                        appLogging.info("After saving the responde bean : " + savedGuestCreateJobResponseBean );

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
