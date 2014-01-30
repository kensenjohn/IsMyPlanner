package com.events.bean.users.permissions;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/30/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class PermissionGroupBean {
    //
    private String permissionGroupId = Constants.EMPTY;
    private String groupName = Constants.EMPTY;
    private String parentId = Constants.EMPTY;


    public PermissionGroupBean(){}
    public PermissionGroupBean(HashMap<String,String> hmResult) {
        this.permissionGroupId = ParseUtil.checkNull(hmResult.get("PERMISSIONGROUPID"));
        this.groupName = ParseUtil.checkNull(hmResult.get("GROUP_NAME"));
        this.parentId = ParseUtil.checkNull(hmResult.get("FK_PARENTID"));
    }
    public String getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(String permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PermissionGroupBean{");
        sb.append("permissionGroupId='").append(permissionGroupId).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append('}');
        return sb.toString();
    }
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("permission_group_id", this.permissionGroupId);
            jsonObject.put("group_name", this.groupName);
            jsonObject.put("parent_id", this.parentId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
