package com.events.bean.common.files;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/29/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharedFilesViewersBean {
    //GTSHAREDFILESVIEWERS (  SHAREDFILESVIEWERSID VARCHAR(45) PRIMARY KEY NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
    // FK_USERID VARCHAR(45) NOT NULL,  VIEWER_TYPE VARCHAR(45) NOT NULL,  FK_PARENTID VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String sharedFilesViewersId = Constants.EMPTY;
    private String sharedFilesGroupId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Constants.USER_TYPE viewerType = Constants.USER_TYPE.NONE;
    private String parentId = Constants.EMPTY;
    private String viewerTypeName = Constants.EMPTY;

    public SharedFilesViewersBean() {
    }

    public SharedFilesViewersBean(HashMap<String,String> hmResult) {
        this.sharedFilesViewersId = ParseUtil.checkNull( hmResult.get("SHAREDFILESVIEWERSID") );
        this.sharedFilesGroupId = ParseUtil.checkNull( hmResult.get("FK_SHAREDFILESGROUPID") );
        this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") );

        String viewerTypeName =  ParseUtil.checkNull( hmResult.get("VIEWER_TYPE") );
        if(!Utility.isNullOrEmpty( viewerTypeName ) ){
            this.viewerType = Constants.USER_TYPE.valueOf(viewerTypeName);
        }

        this.parentId = ParseUtil.checkNull( hmResult.get("FK_PARENTID") );
    }

    public String getSharedFilesViewersId() {
        return sharedFilesViewersId;
    }

    public void setSharedFilesViewersId(String sharedFilesViewersId) {
        this.sharedFilesViewersId = sharedFilesViewersId;
    }

    public String getSharedFilesGroupId() {
        return sharedFilesGroupId;
    }

    public void setSharedFilesGroupId(String sharedFilesGroupId) {
        this.sharedFilesGroupId = sharedFilesGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Constants.USER_TYPE getViewerType() {
        return viewerType;
    }

    public void setViewerType(Constants.USER_TYPE viewerType) {
        this.viewerType = viewerType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SharedFilesViewersBean{");
        sb.append("sharedFilesViewersId='").append(sharedFilesViewersId).append('\'');
        sb.append(", sharedFilesGroupId='").append(sharedFilesGroupId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", viewerType=").append(viewerType);
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("shared_files_viewers_id", this.sharedFilesViewersId);
            jsonObject.put("shared_files_group_id", this.sharedFilesGroupId);
            jsonObject.put("shared_files_group_id", this.sharedFilesGroupId);
            jsonObject.put("user_id", this.userId);
            jsonObject.put("viewer_type", this.viewerType.getType());
            jsonObject.put("parent_id", this.parentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
