package com.events.proc.upload;

import com.events.bean.common.files.*;
import com.events.bean.upload.UploadBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.files.AccessSharedFiles;
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

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/30/14
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadSharedFile  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
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
                    String sFileGroupId = ParseUtil.checkNull(request.getParameter("file_group_id"));

                    if(!Utility.isNullOrEmpty(sFileGroupId)){
                        SharedFilesRequestBean sharedFilesRequestBean = new SharedFilesRequestBean();
                        sharedFilesRequestBean.setSharedFilesGroupId( sFileGroupId );

                        AccessSharedFiles accessSharedFiles = new AccessSharedFiles();
                        SharedFilesResponseBean sharedFilesResponseBean = accessSharedFiles.loadAllFilesFromGroup( sharedFilesRequestBean );
                        if(sharedFilesResponseBean!=null && !Utility.isNullOrEmpty(sharedFilesResponseBean.getSharedFileGroupId())){

                            SharedFilesGroupBean sharedFilesGroupBean = sharedFilesResponseBean.getSharedFilesGroupBean();
                            ArrayList<SharedFilesBean> arrSharedFilesBean = sharedFilesResponseBean.getArrSharedFilesBean();
                            HashMap<String,UploadBean> hmUploadBean = sharedFilesResponseBean.getHmUploadBean();
                            String sharedFileGroupId = sharedFilesResponseBean.getSharedFileGroupId();
                            jsonResponseObj.put("file_group_id" , sharedFileGroupId );


                            JSONObject jsonFilesBean = accessSharedFiles.getJsonSharedFiles( arrSharedFilesBean, hmUploadBean);
                            if(jsonFilesBean!=null){
                                Integer iNumOfFiles = jsonFilesBean.optInt("num_of_files");
                                if(iNumOfFiles>0){
                                    jsonResponseObj.put( "files_bean" , jsonFilesBean);
                                }
                                jsonResponseObj.put("num_of_files", iNumOfFiles);
                            }
                            jsonResponseObj.put("shared_files_group", sharedFilesGroupBean.toJson() );

                            String sharedFileUploadHost = Utility.getSharedFileHost();
                            String bucket = Utility.getS3Bucket();
                            jsonResponseObj.put("shared_file_host", sharedFileUploadHost);
                            jsonResponseObj.put("bucket", bucket);

                            ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean = sharedFilesResponseBean.getArrSharedFilesViewersBean();
                            JSONObject jsonFilesViewersBean = accessSharedFiles.getJsonSharedFilesViewers( arrSharedFilesViewersBean ) ;
                            if(jsonFilesViewersBean!=null){
                                Integer iNumOfFilesViewers = jsonFilesViewersBean.optInt("num_of_files_viewers");
                                if(iNumOfFilesViewers>0){
                                    jsonResponseObj.put( "file_viewers_bean" , jsonFilesViewersBean);
                                }
                                jsonResponseObj.put("num_of_files_viewers", iNumOfFilesViewers);
                            }

                            Text okText = new OkText("The files were saved successfully.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;

                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please refresh and try again.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    } else {
                        responseStatus = RespConstants.Status.OK;
                    }


                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadFileGroup - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            }  else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }

        }  catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadFileGroup - 001)","err_mssg") ;
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
