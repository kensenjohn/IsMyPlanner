package com.events.proc.upload;

import com.events.bean.common.files.SharedFilesBean;
import com.events.bean.common.files.SharedFilesRequestBean;
import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.common.files.AccessSharedFiles;
import com.events.common.files.BuildSharedFiles;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
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
 * Date: 5/1/14
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcDeleteSharedFile  extends HttpServlet {
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

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty( loggedInUserBean.getUserId() )  ) {

                    String sUploadId = ParseUtil.checkNull(request.getParameter("upload_id"));
                    if(!Utility.isNullOrEmpty(sUploadId)){

                        AccessUsers accessUser = new AccessUsers();
                        ParentTypeBean parentTypeBean = accessUser.getParentTypeBeanFromUser( loggedInUserBean );
                        if(parentTypeBean!=null && parentTypeBean.isUserAVendor()){
                            UploadRequestBean uploadRequestBean = new UploadRequestBean();
                            uploadRequestBean.setUploadId(sUploadId);

                            UploadFile uploadFile = new UploadFile();
                            UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo(uploadRequestBean);

                            if(uploadResponseBean!=null){
                                UploadBean uploadBean = uploadResponseBean.getUploadBean();

                                SharedFilesRequestBean sharedFilesRequestBean = new SharedFilesRequestBean();
                                sharedFilesRequestBean.setUploadId( sUploadId );

                                AccessSharedFiles accessSharedFiles = new AccessSharedFiles();
                                SharedFilesBean sharedFilesBean = accessSharedFiles.getSharedFilesFromUploadId( sharedFilesRequestBean );
                                if(sharedFilesBean!=null){

                                    sharedFilesRequestBean.setSharedFileId( sharedFilesBean.getSharedFilesId() );

                                    Folder folder = new Folder();
                                    boolean isFileDeleted = folder.deleteS3File( uploadBean.getFilename(), uploadBean.getPath() );

                                    BuildSharedFiles buildSharedFiles = new BuildSharedFiles();
                                    buildSharedFiles.deleteSharedFiles(sharedFilesRequestBean);

                                    jsonResponseObj.put("is_deleted",true);
                                    jsonResponseObj.put("deleted_upload_id",sUploadId);

                                    Text okText = new OkText("The file was deleted successfully","status_mssg") ;
                                    arrOkText.add(okText);
                                    responseStatus = RespConstants.Status.OK;
                                }



                            } else {
                                Text errorText = new ErrorText("The file you are trying to delete does not exist.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        } else {
                            Text errorText = new ErrorText("You are not allowed to perform this action. Please contact your support representative.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }


                    } else {
                        Text errorText = new ErrorText("We were unable to delete the file. Please select a valid file","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
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
