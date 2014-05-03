package com.events.bean.common.files;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/30/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharedFilesRequestBean {
    private String sharedFilesGroupId = Constants.EMPTY;
    private String fileGroupName = Constants.EMPTY;
    private String comment = Constants.EMPTY;

    private String vendorId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String uploadId = Constants.EMPTY;
    private String sharedFileId = Constants.EMPTY;
    private ArrayList<String> arrViewerId = new ArrayList<String>();

    private boolean isLoggedInUserAClient = false;
    private boolean isUploadFileInvoked = false;
    //FILESGROUPNAME,FK_VENDORID,    FK_CLIENTID,FK_USERID


    public boolean isUploadFileInvoked() {
        return isUploadFileInvoked;
    }

    public void setUploadFileInvoked(boolean uploadFileInvoked) {
        isUploadFileInvoked = uploadFileInvoked;
    }

    public boolean isLoggedInUserAClient() {
        return isLoggedInUserAClient;
    }

    public void setLoggedInUserAClient(boolean loggedInUserAClient) {
        isLoggedInUserAClient = loggedInUserAClient;
    }

    public String getSharedFileId() {
        return sharedFileId;
    }

    public void setSharedFileId(String sharedFileId) {
        this.sharedFileId = sharedFileId;
    }

    public ArrayList<String> getArrViewerId() {
        return arrViewerId;
    }

    public void setArrViewerId(ArrayList<String> arrViewerId) {
        this.arrViewerId = arrViewerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSharedFilesGroupId() {
        return sharedFilesGroupId;
    }

    public void setSharedFilesGroupId(String sharedFilesGroupId) {
        this.sharedFilesGroupId = sharedFilesGroupId;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getFileGroupName() {
        return fileGroupName;
    }

    public void setFileGroupName(String fileGroupName) {
        this.fileGroupName = fileGroupName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
