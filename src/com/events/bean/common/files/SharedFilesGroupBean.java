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
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharedFilesGroupBean {
    //GTSHAREDFILESGROUP(  SHAREDFILESGROUPID VARCHAR(45) PRIMARY KEY NOT NULL,  FILESGROUPNAME VARCHAR(250) NOT NULL,
    // FK_VENDORID VARCHAR(45) NOT NULL , FK_CLIENTID VARCHAR(45) NOT NULL , FK_USERID VARCHAR(45) NOT NULL )
    // ENGINE = MyISAM DEFAULT CHARSET = utf8;
    private String sharedFilesGroupId = Constants.EMPTY;
    private String filesGroupName = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientId  = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    public SharedFilesGroupBean() {
    }

    public SharedFilesGroupBean(HashMap<String,String> hmResult) {
        this.sharedFilesGroupId = ParseUtil.checkNull( hmResult.get("SHAREDFILESGROUPID") );
        this.filesGroupName = ParseUtil.checkNull( hmResult.get("FILESGROUPNAME") );
        this.vendorId = ParseUtil.checkNull( hmResult.get("FK_VENDORID") );
        this.clientId = ParseUtil.checkNull( hmResult.get("FK_CLIENTID") );
        this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") );
    }

    public String getSharedFilesGroupId() {
        return sharedFilesGroupId;
    }

    public void setSharedFilesGroupId(String sharedFilesGroupId) {
        this.sharedFilesGroupId = sharedFilesGroupId;
    }

    public String getFilesGroupName() {
        return filesGroupName;
    }

    public void setFilesGroupName(String filesGroupName) {
        this.filesGroupName = filesGroupName;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SharedFilesGroupBean{");
        sb.append("sharedFilesGroupId='").append(sharedFilesGroupId).append('\'');
        sb.append(", filesGroupName='").append(filesGroupName).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("shared_files_group_id", this.sharedFilesGroupId);
            jsonObject.put("files_group_name", this.filesGroupName);
            jsonObject.put("vendor_id", this.vendorId);
            jsonObject.put("client_id", this.clientId);
            jsonObject.put("user_id", this.userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
