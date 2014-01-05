package com.events.bean.upload;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class UploadRequestBean {

    private String uploadId = Constants.EMPTY;
    private String filename = Constants.EMPTY;
    private String path = Constants.EMPTY;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadRequestBean{");
        sb.append("uploadId='").append(uploadId).append('\'');
        sb.append(", filename='").append(filename).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
