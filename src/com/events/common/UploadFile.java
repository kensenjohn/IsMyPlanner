package com.events.common;

import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.common.exception.ExceptionHandler;
import com.events.data.upload.UploadFileData;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class UploadFile {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public UploadResponseBean saveUploadFileInfo(UploadRequestBean uploadRequestBean) {
        UploadResponseBean uploadResponseBean = new UploadResponseBean();
        if(uploadRequestBean!=null) {
            uploadRequestBean.setUploadId(Utility.getNewGuid());

            UploadFileData uploadFileData = new UploadFileData();
            Integer iNumOfRows = uploadFileData.insertUpload(uploadRequestBean);
            if(iNumOfRows>0){
                uploadResponseBean.setUploadId( uploadRequestBean.getUploadId() );
            }
        }
        return uploadResponseBean;
    }

    public UploadResponseBean getUploadFileInfo(UploadRequestBean uploadRequestBean) {
        UploadResponseBean uploadResponseBean = new UploadResponseBean();
        if(uploadRequestBean!=null) {
            UploadFileData uploadFileData = new UploadFileData();
            UploadBean uploadBean = uploadFileData.getUploadBean(uploadRequestBean);
            if(uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId()) ) {
                uploadResponseBean.setUploadBean(uploadBean);
                uploadResponseBean.setUploadId( uploadBean.getUploadId() );
            }

        }
        return uploadResponseBean;
    }

    public static JSONObject generateSuccessUploadJson(UploadRequestBean uploadRequestBean){
        JSONObject jsono = new JSONObject();
        if(uploadRequestBean!=null && !Utility.isNullOrEmpty(uploadRequestBean.getFilename())) {
            jsono.put("name", ParseUtil.checkNull( uploadRequestBean.getFilename() ) );
            jsono.put("original_name", ParseUtil.checkNull( uploadRequestBean.getOriginalFileName() ) );
            jsono.put("upload_id", ParseUtil.checkNull( uploadRequestBean.getUploadId() ) );
            jsono.put("foldername", ParseUtil.checkNull( uploadRequestBean.getFolderName()) );
            jsono.put("imagehost", ParseUtil.checkNull( uploadRequestBean.getImageHost()) );
            jsono.put("shared_file_host", ParseUtil.checkNull( uploadRequestBean.getSharedFileHost()) );
            jsono.put("mime_type", ParseUtil.checkNull( uploadRequestBean.getMimeType()) );
            jsono.put("bucket", ParseUtil.checkNull( uploadRequestBean.getS3Bucket()) );
            jsono.put("size", ParseUtil.LToS( uploadRequestBean.getImageSize()) );
            jsono.put("success", true );
            jsono.put("upload_image", uploadRequestBean.getJsonResponseObj() );
            jsono.put("url", "UploadServlet?getfile=" );
            jsono.put("thumbnail_url", "UploadServlet?getthumb=");
            jsono.put("delete_url", "UploadServlet?delfile=");
            jsono.put("delete_type", "GET");
        } else {
            jsono = generateErrorUploadJson();
        }
        return jsono;
    }

    public static JSONObject generateErrorUploadJson(){
        JSONObject jsono = new JSONObject();
        jsono.put("success", false );
        return jsono;
    }
    public static String geenerateRandomeFileName(String sFileName){
        return Utility.getNewGuid() + "_" + ParseUtil.checkNull(sFileName);
    }

    public static String getFileMimeType(File file){
        String sMimeType = Constants.EMPTY;
        if(file!=null){
            try {
                // default tika configuration can detect a lot of different file types
                TikaConfig tika = new TikaConfig();

                // meta data collected about the source file
                Metadata metadata = new Metadata();
                metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());

                // determine mime type from file contents
                MediaType mimetype = tika.getDetector().detect(TikaInputStream.get(file, metadata), metadata);
                if(mimetype!=null){
                    sMimeType = mimetype.toString();
                }
            }  catch (FileNotFoundException e) {
                appLogging.error(ExceptionHandler.getStackTrace(e));
            } catch (TikaException e) {
                appLogging.error(ExceptionHandler.getStackTrace(e));
            } catch (IOException e) {
                appLogging.error(ExceptionHandler.getStackTrace(e));
            }
        }
        return sMimeType;
    }
}
