package com.events.proc.upload;

import com.events.bean.common.files.*;
import com.events.bean.upload.UploadBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
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
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/30/14
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveSharedFile extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

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

                    String sUploadId = ParseUtil.checkNull(request.getParameter("upload_id"));
                    String sFileGroupId = ParseUtil.checkNull(request.getParameter("file_group_id"));
                    String sFileGroupName = ParseUtil.checkNull(request.getParameter("uploadFileGroupName"));
                    String sFileGroupComment = ParseUtil.checkNull(request.getParameter("uploadFileGroupComment"));
                    String[] sArrViewerId = request.getParameterValues("uploadFileViewer");
                    boolean isSaveClicked = ParseUtil.sTob( request.getParameter("button_clicked") );

                    ArrayList<String>  arrViewerId = new ArrayList<String>();
                    if(sArrViewerId!=null && sArrViewerId.length>0){
                        for(String sViewerId :sArrViewerId){
                            arrViewerId.add(sViewerId);
                        }
                    }

                    boolean isError = false;
                    if( isSaveClicked ) {
                        if( Utility.isNullOrEmpty(sFileGroupName) ){
                            Text errorText = new ErrorText("Please select a valid title for your file group.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                            isError = true;
                        } else if ( arrViewerId.isEmpty()  ){
                            Text errorText = new ErrorText("Please select at least one client for your files.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                            isError = true;
                        }
                    }

                    if(!isError) {
                        AccessUsers accessUser = new AccessUsers();
                        ParentTypeBean parentTypeBean = accessUser.getParentTypeBeanFromUser( loggedInUserBean );
                        if(parentTypeBean!=null){

                            SharedFilesRequestBean sharedFilesRequestBean = new SharedFilesRequestBean();
                            sharedFilesRequestBean.setUploadId( sUploadId );
                            sharedFilesRequestBean.setSharedFilesGroupId( sFileGroupId );
                            sharedFilesRequestBean.setFileGroupName( sFileGroupName );
                            sharedFilesRequestBean.setComment( sFileGroupComment );
                            sharedFilesRequestBean.setVendorId( parentTypeBean.getVendorId() );
                            sharedFilesRequestBean.setClientId( parentTypeBean.getClientdId()  );
                            sharedFilesRequestBean.setUserId( loggedInUserBean.getUserId() );
                            sharedFilesRequestBean.setArrViewerId( arrViewerId );
                            sharedFilesRequestBean.setLoggedInUserAClient( parentTypeBean.isUserAClient() );
                            if(isSaveClicked){
                                sharedFilesRequestBean.setUploadFileInvoked( false );
                            } else {
                                sharedFilesRequestBean.setUploadFileInvoked( true );
                            }



                            BuildSharedFiles buildSharedFiles = new BuildSharedFiles();
                            SharedFilesResponseBean sharedFilesResponseBean = buildSharedFiles.saveSharedFiles( sharedFilesRequestBean );

                            if(sharedFilesResponseBean!=null && !Utility.isNullOrEmpty(sharedFilesResponseBean.getSharedFileGroupId())){

                                SharedFilesGroupBean sharedFilesGroupBean = sharedFilesResponseBean.getSharedFilesGroupBean();
                                AccessSharedFiles accessSharedFiles = new AccessSharedFiles();
                                ArrayList<SharedFilesBean> arrSharedFilesBean = sharedFilesResponseBean.getArrSharedFilesBean();
                                HashMap<String,UploadBean> hmUploadBean = sharedFilesResponseBean.getHmUploadBean();
                                SharedFilesCommentsBean sharedFilesCommentsBean = sharedFilesResponseBean.getSharedFilesCommentsBean();


                                sFileGroupId = sharedFilesResponseBean.getSharedFileGroupId();
                                jsonResponseObj.put("file_group_id" , sFileGroupId );



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
                                jsonResponseObj.put("comment",sharedFilesCommentsBean.toJson());

                                jsonResponseObj.put("show_alert", true);

                                Text okText = new OkText("The files were saved successfully.","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;

                                // Creating a notification
                                {
                                    if(isSaveClicked){
                                        String notifciationMessage = Constants.EMPTY;
                                        if( sharedFilesResponseBean.isNewFileGroup() ) {
                                            notifciationMessage = notifciationMessage + "Added one or more files. To view them go to 'Dashboard >> Files >> "+sFileGroupName+"'";
                                        } else {
                                            notifciationMessage = notifciationMessage + "Updated one or more files. To view them go to 'Dashboard >> Files >> "+sFileGroupName+"'";
                                        }
                                        buildSharedFiles.createNotifications( sharedFilesRequestBean , notifciationMessage );
                                    }
                                }

                            } else {
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveShareFile - 003)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }



                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveShareFile - 002)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    }
                    appLogging.info("sFileGroupId : " + ParseUtil.checkNull(sFileGroupId) + " sUploadId : " + ParseUtil.checkNull(sUploadId)  );

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveShareFile - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadWES - 001)","err_mssg") ;
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

