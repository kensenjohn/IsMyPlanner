package com.events.proc.upload;

import com.events.bean.clients.ClientBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.json.*;
import com.events.users.AccessUsers;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/30/14
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcUploadSharedFile extends HttpServlet {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    /**
     * @see HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        if (!ServletFileUpload.isMultipartContent(request)) {
            appLogging.error("Request is not multipart, please 'multipart/form-data' enctype for your form.");
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        String sharedFileHost = Utility.getSharedFileHost();

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

        String sharedFileLocation = applicationConfig.get(Constants.SHARED_FILE_LOCATION);

        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) ) {
            try {
                List<FileItem> items = uploadHandler.parseRequest(request);
                if(items!=null){
                    for (FileItem item : items) {
                        if (item.isFormField()) {
                            //place to read other parameters
                        } else if (!item.isFormField()) {
                            String sOriginalName = item.getName();

                            Folder folder = new Folder();
                            String sUserFolderName = folder.getFolderForUser( loggedInUserBean, sharedFileLocation );
                            if(!Utility.isNullOrEmpty(sharedFileLocation) && sharedFileLocation.endsWith("/")) {
                                sharedFileLocation = sharedFileLocation + "/";
                            }
                            String sFolderPath = sharedFileLocation + sUserFolderName ;

                            String sRandomFilename = Utility.getNewGuid() + "_" + sOriginalName;
                            File  file = new  File(sFolderPath,sRandomFilename );
                            item.write(file);

                            String sFileMimeType = UploadFile.getFileMimeType( file );

                            if( "image/png".equalsIgnoreCase(sFileMimeType) || "image/jpeg".equalsIgnoreCase(sFileMimeType)
                                    || "image/jpeg".equalsIgnoreCase(sFileMimeType) ||  "application/pdf".equalsIgnoreCase(sFileMimeType)
                                    ||  "application/msword".equalsIgnoreCase(sFileMimeType) ||  "application/vnd.ms-excel".equalsIgnoreCase(sFileMimeType)
                                    ||  "application/x-excel".equalsIgnoreCase(sFileMimeType) ||  "application/x-msexcel".equalsIgnoreCase(sFileMimeType)
                                    ||  "application/excel".equalsIgnoreCase(sFileMimeType) ||  "application/vnd.ms-powerpoint".equalsIgnoreCase(sFileMimeType)
                                    ||  "application/vnd.ms-powerpoint".equalsIgnoreCase(sFileMimeType)) {

                                UploadRequestBean uploadRequestBean = new UploadRequestBean();
                                uploadRequestBean.setFilename( sRandomFilename );
                                uploadRequestBean.setPath( sUserFolderName );
                                uploadRequestBean.setOriginalFileName( sOriginalName );
                                uploadRequestBean.setUserId( loggedInUserBean.getUserId() );

                                folder.createS3FolderForUser( sFolderPath, sRandomFilename, sUserFolderName );

                                item.delete();
                                UploadFile uploadFile = new UploadFile();
                                UploadResponseBean uploadResponseBean = uploadFile.saveUploadFileInfo(uploadRequestBean);
                                if(uploadResponseBean!=null && !Utility.isNullOrEmpty(uploadResponseBean.getUploadId())){
                                    jsonResponseObj.put("name", item.getName());
                                    jsonResponseObj.put("size", item.getSize());

                                    Text okText = new OkText("The guest groups was successfully saved","status_mssg") ;
                                    arrOkText.add(okText);
                                    responseStatus = RespConstants.Status.OK;

                                    uploadRequestBean.setFolderName(sUserFolderName);
                                    uploadRequestBean.setSharedFileHost( sharedFileHost );
                                    uploadRequestBean.setImageSize( item.getSize() );
                                    uploadRequestBean.setS3Bucket( applicationConfig.get(Constants.AMAZON.S3_BUCKET.getPropName())  );
                                    uploadRequestBean.setJsonResponseObj(jsonResponseObj );
                                    uploadRequestBean.setMimeType( sFileMimeType );
                                    uploadRequestBean.setUploadId( uploadResponseBean.getUploadId() );

                                    AccessUsers accessUsers = new AccessUsers();
                                    String sUploadedBy = Constants.EMPTY;

                                    UserRequestBean userRequestBean = new UserRequestBean();
                                    userRequestBean.setUserId( loggedInUserBean.getUserId() );
                                    UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId(userRequestBean);
                                    if(userInfoBean!=null ) {
                                        sUploadedBy = ParseUtil.checkNull( userInfoBean.getFirstName() ) + " " + ParseUtil.checkNull( userInfoBean.getLastName() )  ;
                                    }

                                    if(Utility.isNullOrEmpty(sUploadedBy)){
                                        sUploadedBy = "User";
                                    }
                                    uploadRequestBean.setUploadedBy(sUploadedBy );

                                    JSONObject jsono = UploadFile.generateSuccessUploadJson(uploadRequestBean);


                                    String sharedFileUploadHost = Utility.getSharedFileHost();
                                    String bucket = Utility.getS3Bucket();
                                    jsono.put("shared_file_host", sharedFileUploadHost);
                                    jsono.put("bucket", bucket);

                                    json.put(jsono);

                                    appLogging.info("JSON Respond" + json.toString() );
                                }else {
                                    appLogging.info("There was error uploading file" + uploadRequestBean );
                                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEvent - 002)","err_mssg") ;
                                    arrErrorText.add(errorText);

                                    responseStatus = RespConstants.Status.ERROR;

                                    JSONObject jsono = new JSONObject();
                                    jsono.put("success", false );
                                    jsono.put("err_mssg", "Oops!! We were unable to process your request at this time. Please try again later.(saveEvent - 002)" );
                                    json.put(jsono);
                                }

                            } else {
                                appLogging.debug("You are trying to upload an invalid file. : ");

                                Text errorText = new ErrorText("You are trying to upload an invalid file. You may only upload a PNG, JPG, Word, PDF or Excel.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;

                                JSONObject jsono = new JSONObject();
                                jsono.put("success", false );
                                jsono.put("err_mssg", "You are trying to upload an invalid file. You may only upload a PNG, JPG, Word, PDF or Excel." );
                                json.put(jsono);
                            }
                        }
                    }
                } else {
                    JSONObject jsono = new JSONObject();
                    jsono.put("success", false );
                    json.put(jsono);
                    appLogging.info(" item list is null : ");
                }

            } catch (FileUploadException e) {
                JSONObject jsono = new JSONObject();
                jsono.put("success", false );
                json.put(jsono);
                appLogging.error("FileUploadException : " + ExceptionHandler.getStackTrace(e));
            } catch (Exception e) {
                JSONObject jsono = new JSONObject();
                jsono.put("success", false );
                json.put(jsono);
                appLogging.error("Exception : " + ExceptionHandler.getStackTrace(e));
            }finally {
                appLogging.debug("After Upload is complete : " + json.toString());
                writer.write(json.toString());
                writer.close();
            }
        }
    }
}
