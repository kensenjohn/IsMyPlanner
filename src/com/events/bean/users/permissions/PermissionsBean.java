package com.events.bean.users.permissions;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PermissionsBean {

    private String permissionId = Constants.EMPTY;
    private String permissionGroupId = Constants.EMPTY;
    private String shortName = Constants.EMPTY;
    private String displayText = Constants.EMPTY;
    private String description = Constants.EMPTY;
    private String parentId = Constants.EMPTY;

    public PermissionsBean(){}
    public PermissionsBean(HashMap<String,String> hmResult) {
        this.permissionId = ParseUtil.checkNull(hmResult.get("PERMISSIONID"));
        this.permissionGroupId = ParseUtil.checkNull(hmResult.get("PERMISSIONGROUPID"));
        this.shortName = ParseUtil.checkNull(hmResult.get("SHORT_NAME"));
        this.displayText = ParseUtil.checkNull(hmResult.get("DISPLAY_TEXT"));
        this.description = ParseUtil.checkNull(hmResult.get("DESCRIPTION"));
        this.parentId = ParseUtil.checkNull(hmResult.get("FK_PARENTID"));
    }
    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(String permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PermissionsBean{");
        sb.append("permissionID='").append(permissionId).append('\'');
        sb.append(", permissionGroupId='").append(permissionGroupId).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", displayText='").append(displayText).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("permission_id", this.permissionId);
            jsonObject.put("permission_group_id", this.permissionGroupId);
            jsonObject.put("short_name", this.shortName);
            jsonObject.put("display_text", this.displayText);
            jsonObject.put("description", this.description);
            jsonObject.put("parent_id", this.parentId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
