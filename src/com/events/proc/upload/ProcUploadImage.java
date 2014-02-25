package com.events.proc.upload;

import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.json.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
 * Date: 1/15/14
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcUploadImage extends HttpServlet {
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

        String imageHost = Utility.getImageUploadHost();

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

        String imageUploadLocation = applicationConfig.get(Constants.IMAGE_LOCATION);

        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) ) {
            try {
                List<FileItem> items = uploadHandler.parseRequest(request);
                if(items!=null){
                    for (FileItem item : items) {
                        if (item.isFormField()) {
                            //place to read other parameters
                        } else if (!item.isFormField()) {
                            Folder folder = new Folder();
                            String sUserFolderName = folder.getFolderForUser( loggedInUserBean, imageUploadLocation );
                            String sFolderPath = imageUploadLocation + "/" + sUserFolderName ;

                            String sRandomFilename = Utility.getNewGuid() + "_" + item.getName();
                            File  file = new  File(sFolderPath,sRandomFilename );
                            item.write(file);

                            UploadRequestBean uploadRequestBean = new UploadRequestBean();
                            uploadRequestBean.setFilename( sRandomFilename );
                            uploadRequestBean.setPath( sFolderPath );

                            UploadFile uploadFile = new UploadFile();
                            UploadResponseBean uploadResponseBean = uploadFile.saveUploadFileInfo(uploadRequestBean);
                            if(uploadResponseBean!=null && !Utility.isNullOrEmpty(uploadResponseBean.getUploadId())){
                                jsonResponseObj.put("name", item.getName());
                                jsonResponseObj.put("size", item.getSize());

                                Text okText = new OkText("The guest groups was successfully saved","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;

                                uploadRequestBean.setFolderName(sUserFolderName);
                                uploadRequestBean.setImageHost( imageHost );
                                uploadRequestBean.setImageSize( item.getSize() );
                                uploadRequestBean.setJsonResponseObj(jsonResponseObj );
                                JSONObject jsono = UploadFile.generateSuccessUploadJson(uploadRequestBean);
                                json.put(jsono);
                            }else {
                                appLogging.info("There was error uploading file" + uploadRequestBean );
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEvent - 002)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;

                                JSONObject jsono = new JSONObject();
                                jsono.put("success", false );
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

        /*responseObject.setErrorMessages(arrErrorText);
        responseObject.setOkMessages(arrOkText);
        responseObject.setResponseStatus(responseStatus);
        responseObject.setJsonResponseObj(jsonResponseObj);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write( responseObject.getJson().toString() );
        response.getWriter().close();*/

    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpg")) {
                mimetype = "image/jpg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpeg")) {
                mimetype = "image/jpeg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("gif")) {
                mimetype = "image/gif";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }


    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
}
