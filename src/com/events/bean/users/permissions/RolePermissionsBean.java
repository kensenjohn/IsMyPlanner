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
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class RolePermissionsBean {
    //ROLEPERMISSIONID VARCHAR(45) NOT NULL, FK_ROLEID VARCHAR(45) NOT NULL , FK_PERMISSIONID
    private String rolePermissionId = Constants.EMPTY;
    private String roleId = Constants.EMPTY;
    private String permissionId = Constants.EMPTY;

    public RolePermissionsBean(){}
    public RolePermissionsBean(HashMap<String,String> hmResult) {
        this.rolePermissionId = ParseUtil.checkNull(hmResult.get("ROLEPERMISSIONID"));
        this.roleId = ParseUtil.checkNull(hmResult.get("FK_ROLEID"));
        this.permissionId = ParseUtil.checkNull(hmResult.get("FK_PERMISSIONID"));
    }
    public String getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(String rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RolePermissionsBean{");
        sb.append("rolePermissionId='").append(rolePermissionId).append('\'');
        sb.append(", roleId='").append(roleId).append('\'');
        sb.append(", permissionsId='").append(permissionId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("role_permission_id", this.rolePermissionId);
            jsonObject.put("role_id", this.roleId);
            jsonObject.put("permission_id", this.permissionId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
