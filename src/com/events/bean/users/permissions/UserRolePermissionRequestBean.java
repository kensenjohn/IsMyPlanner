package com.events.bean.users.permissions;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRolePermissionRequestBean {
    private String roleId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private  String parentId= Constants.EMPTY;
    private  Constants.USER_TYPE userType = Constants.USER_TYPE.NONE;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Constants.USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(Constants.USER_TYPE userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRolePermissionRequestBean{");
        sb.append("roleId='").append(roleId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", userType=").append(userType);
        sb.append('}');
        return sb.toString();
    }
}
