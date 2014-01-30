package com.events.bean.users.permissions;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/29/14
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRolesBean {
    //
    private String userRoleId = Constants.EMPTY;
    private String roleId = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    public UserRolesBean(){}
    public UserRolesBean( HashMap<String,String> hmResult) {
        this.userRoleId = ParseUtil.checkNull(hmResult.get("USERROLEID"));
        this.roleId = ParseUtil.checkNull(hmResult.get("FK_ROLEID"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
    }
    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRolesBean{");
        sb.append("userRoleId='").append(userRoleId).append('\'');
        sb.append(", roleId='").append(roleId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
