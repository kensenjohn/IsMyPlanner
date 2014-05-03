package com.events.bean.upload;

import com.events.common.Constants;
import org.json.JSONObject;

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
    private String originalFileName = Constants.EMPTY;
    private String path = Constants.EMPTY;

    private String folderName = Constants.EMPTY;
    private String imageHost = Constants.EMPTY;
    private String s3Bucket = Constants.EMPTY;
    private Long imageSize = 0L;
    private String mimeType = Constants.EMPTY;
    private String sharedFileHost = Constants.EMPTY;


    private JSONObject jsonResponseObj = new JSONObject();

    public JSONObject getJsonResponseObj() {
        return jsonResponseObj;
    }

    public void setJsonResponseObj(JSONObject jsonResponseObj) {
        this.jsonResponseObj = jsonResponseObj;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getSharedFileHost() {
        return sharedFileHost;
    }

    public void setSharedFileHost(String sharedFileHost) {
        this.sharedFileHost = sharedFileHost;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    public String getUploadId() {
        return uploadId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Long getImageSize() {
        return imageSize;
    }

    public void setImageSize(Long imageSize) {
        this.imageSize = imageSize;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadRequestBean{");
        sb.append("uploadId='").append(uploadId).append('\'');
        sb.append(", filename='").append(filename).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", folderName='").append(folderName).append('\'');
        sb.append(", imageHost='").append(imageHost).append('\'');
        sb.append(", imageSize=").append(imageSize);
        sb.append('}');
        return sb.toString();
    }
}
