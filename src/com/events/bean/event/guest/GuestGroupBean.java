package com.events.bean.event.guest;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/2/14
 * Time: 6:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestGroupBean {
    private String guestGroupId = Constants.EMPTY;
    private String groupName = Constants.EMPTY;
    private String deleteRow = "0";
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public GuestGroupBean() {
    }

    public GuestGroupBean(HashMap<String,String> hmResult) {
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("GUESTGROUPID"));
        this.groupName = ParseUtil.checkNull(hmResult.get("GROUPNAME"));
        this.deleteRow = ParseUtil.checkNull(hmResult.get("DEL_ROW"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDeleteRow() {
        return deleteRow;
    }

    public void setDeleteRow(String deleteRow) {
        this.deleteRow = deleteRow;
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
        final StringBuilder sb = new StringBuilder("GuestGroupBean{");
        sb.append("guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", deleteRow='").append(deleteRow).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }



    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("group_name", this.groupName);
            jsonObject.put("del_row", this.deleteRow);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
