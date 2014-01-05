package com.events.bean.upload;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadResponseBean {
    private String uploadId = Constants.EMPTY;
    private UploadBean uploadBean = new UploadBean();

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public UploadBean getUploadBean() {
        return uploadBean;
    }

    public void setUploadBean(UploadBean uploadBean) {
        this.uploadBean = uploadBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadResponseBean{");
        sb.append("uploadId='").append(uploadId).append('\'');
        sb.append(", uploadBean=").append(uploadBean);
        sb.append('}');
        return sb.toString();
    }
}

