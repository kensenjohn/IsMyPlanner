package com.events.common;

import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.data.upload.UploadFileData;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class UploadFile {
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
            jsono.put("foldername", ParseUtil.checkNull( uploadRequestBean.getFolderName()) );
            jsono.put("imagehost", ParseUtil.checkNull( uploadRequestBean.getImageHost()) );
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


}
