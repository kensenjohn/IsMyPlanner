package com.events.bean.common.files;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/29/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharedFilesBean {
    //GTSHAREDFILES(  SHAREDFILESID VARCHAR(45) PRIMARY KEY NOT NULL,  FILENAME VARCHAR(250) NOT NULL,
    // FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL,
    // CREATEDATE BIGINT(20) DEFAULT 0 NOT NULL,  HUMANCREATEDATE VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String sharedFilesId = Constants.EMPTY;
    private String fileName = Constants.EMPTY;
    private String sharedFilesGroupId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String formattedCreateDate = Constants.EMPTY;
    private String uploadedBy = Constants.EMPTY;

    public SharedFilesBean() {
    }

    public SharedFilesBean(HashMap<String,String> hmResult) {
        this.sharedFilesId = ParseUtil.checkNull( hmResult.get("SHAREDFILESID") );
        this.fileName = ParseUtil.checkNull( hmResult.get("FILENAME") );
        this.sharedFilesGroupId = ParseUtil.checkNull( hmResult.get("FK_SHAREDFILESGROUPID") );
        this.vendorId = ParseUtil.checkNull( hmResult.get("FK_VENDORID") );
        this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") );
        this.createDate = ParseUtil.sToL( hmResult.get("CREATEDATE") );
        this.humanCreateDate = ParseUtil.checkNull( hmResult.get("HUMANCREATEDATE") );
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getFormattedCreateDate() {
        return formattedCreateDate;
    }

    public void setFormattedCreateDate(String formattedCreateDate) {
        this.formattedCreateDate = formattedCreateDate;
    }

    public String getSharedFilesId() {
        return sharedFilesId;
    }

    public void setSharedFilesId(String sharedFilesId) {
        this.sharedFilesId = sharedFilesId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSharedFilesGroupId() {
        return sharedFilesGroupId;
    }

    public void setSharedFilesGroupId(String sharedFilesGroupId) {
        this.sharedFilesGroupId = sharedFilesGroupId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SharedFilesBean{");
        sb.append("sharedFilesId='").append(sharedFilesId).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", sharedFilesGroupId='").append(sharedFilesGroupId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("shared_files_id", this.sharedFilesId);
            jsonObject.put("file_name", this.fileName);
            jsonObject.put("shared_files_group_id", this.sharedFilesGroupId);
            jsonObject.put("vendor_id", this.vendorId);
            jsonObject.put("user_id", this.userId);
            jsonObject.put("create_date", this.createDate);
            jsonObject.put("formatted_create_date", this.formattedCreateDate);
            jsonObject.put("uploaded_by", this.uploadedBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
