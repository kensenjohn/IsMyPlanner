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
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class RolesBean {
    //GTROLES( ROLEID, FK_PARENTID, NAME,      CREATEDATE, HUMANCREATEDATE, IS_SITEADMIN)
    private String roleId = Constants.EMPTY;
    private String parentId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate =  Constants.EMPTY;
    private boolean isSiteAdmin = false;

    public RolesBean() {}
    public RolesBean(HashMap<String,String> hmResult) {
        this.roleId = ParseUtil.checkNull(hmResult.get("ROLEID"));
        this.parentId = ParseUtil.checkNull(hmResult.get("FK_PARENTID"));
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
        this.isSiteAdmin = ParseUtil.sTob(hmResult.get("IS_SITEADMIN"));
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isSiteAdmin() {
        return isSiteAdmin;
    }

    public void setSiteAdmin(boolean siteAdmin) {
        isSiteAdmin = siteAdmin;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RolesBean{");
        sb.append("roleId='").append(roleId).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", isSiteAdmin=").append(isSiteAdmin);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("role_id", this.roleId);
            jsonObject.put("parent_id", this.parentId);
            jsonObject.put("name", this.name);
            jsonObject.put("create_date", this.createDate);
            jsonObject.put("human_create_date", this.humanCreateDate);
            jsonObject.put("is_siteadmin", this.isSiteAdmin);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}
