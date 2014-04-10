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
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/30/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcUploadCSV extends HttpServlet {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();

        String fileUploadHost = Utility.getFileUploadHost();
        String s3Bucket = Utility.getS3Bucket();
        UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);
        String fileUploadLocation = applicationConfig.get(Constants.FILE_UPLOAD_LOCATION);

        Folder folder = new Folder();
        String sUserFolderName = folder.getFolderForUser(loggedInUserBean, fileUploadLocation);

        String sFolderPath = fileUploadLocation + "/" + sUserFolderName ;
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            if(items!=null){
                appLogging.debug(" file itesm are going to be listed : " + items + " - " + items.size());
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        String sRandomFilename = Utility.getNewGuid() + "_" + item.getName();
                        File file = new File(sFolderPath, sRandomFilename);
                        item.write(file);

                        UploadRequestBean uploadRequestBean = new UploadRequestBean();
                        uploadRequestBean.setFilename( sRandomFilename );
                        uploadRequestBean.setPath( sUserFolderName );

                        folder.createS3FolderForUser( sFolderPath, sRandomFilename, sUserFolderName );

                        UploadFile uploadFile = new UploadFile();
                        UploadResponseBean uploadResponseBean = uploadFile.saveUploadFileInfo(uploadRequestBean);
                        if(uploadResponseBean!=null && !Utility.isNullOrEmpty(uploadResponseBean.getUploadId())){

                            jsonResponseObj.put("upload_id", uploadResponseBean.getUploadId() );
                            jsonResponseObj.put("name", item.getName());
                            jsonResponseObj.put("size", item.getSize());

                            Text okText = new OkText("The guest groups was successfully saved","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;

                            JSONObject jsono = new JSONObject();
                            jsono.put("name", sRandomFilename );
                            jsono.put("foldername", sUserFolderName );
                            jsono.put("fileuploadhost", fileUploadHost );
                            jsono.put("bucket", s3Bucket );
                            jsono.put("upload_id", uploadResponseBean.getUploadId() );
                            jsono.put("success", true );
                            jsono.put("url", "UploadServlet?getfile=" + item.getName());
                            jsono.put("thumbnail_url", "UploadServlet?getthumb=" + item.getName());
                            jsono.put("delete_url", "UploadServlet?delfile=" + item.getName());
                            jsono.put("delete_type", "GET");
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
                    } else {
                        JSONObject jsono = new JSONObject();
                        jsono.put("success", false );
                        json.put(jsono);
                        appLogging.info("Invalid form field used." );

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
