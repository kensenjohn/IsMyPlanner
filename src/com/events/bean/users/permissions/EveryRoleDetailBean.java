package com.events.bean.users.permissions;

import com.events.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/29/14
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class EveryRoleDetailBean {
    private String name = Constants.EMPTY;
    private Integer assignedToNumOfUsers = 0;
    private String roleId = Constants.EMPTY;
    private boolean isSiteAdmin = false;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAssignedToNumOfUsers() {
        return assignedToNumOfUsers;
    }

    public void setAssignedToNumOfUsers(Integer assignedToNumOfUsers) {
        this.assignedToNumOfUsers = assignedToNumOfUsers;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isSiteAdmin() {
        return isSiteAdmin;
    }

    public void setSiteAdmin(boolean siteAdmin) {
        isSiteAdmin = siteAdmin;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryRoleDetailBean{");
        sb.append("name='").append(name).append('\'');
        sb.append(", assignedToNumOfUsers=").append(assignedToNumOfUsers);
        sb.append(", roleId='").append(roleId).append('\'');
        sb.append(", isSiteAdmin=").append(isSiteAdmin);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", this.name);
            jsonObject.put("assigned_to_num_of_users", this.assignedToNumOfUsers);
            jsonObject.put("role_id", this.roleId);
            jsonObject.put("is_site_admin", this.isSiteAdmin);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
