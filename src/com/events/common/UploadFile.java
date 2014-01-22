package com.events.common;

import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.data.upload.UploadFileData;

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
}
