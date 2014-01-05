package com.events.bean.event.guest;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/1/14
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestBean {
    /*
    create TABLE GTGUEST( GUESTID  VARCHAR(45) NOT NULL, FK_GUESTGROUPID VARCHAR(45) NOT NULL, FIRST_NAME  TEXT NOT NULL,
    MIDDLE_NAME  TEXT NOT NULL, LAST_NAME  TEXT NOT NULL, COMPANY VARCHAR(45), PRIMARY KEY (GUESTID) ) ENGINE = MyISAM  DEFAULT CHARSET = utf8;

     */
    private String guestId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String firstName = Constants.EMPTY;
    private String lastName = Constants.EMPTY;
    private String company = Constants.EMPTY;
    private String deleteRow = "0";
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public GuestBean(){}
    public GuestBean(HashMap<String,String> hmResult) {

        this.guestId = ParseUtil.checkNull(hmResult.get("GUESTID"));
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        this.firstName = ParseUtil.checkNull(hmResult.get("FIRST_NAME"));
        this.lastName = ParseUtil.checkNull(hmResult.get("LAST_NAME"));
        this.deleteRow = ParseUtil.checkNull(hmResult.get("DEL_ROW"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMAN_CREATEDATE"));
        this.company = ParseUtil.checkNull(hmResult.get("COMPANY"));
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
        final StringBuilder sb = new StringBuilder("GuestBean{");
        sb.append("guestId='").append(guestId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", company='").append(company).append('\'');
        sb.append(", deleteRow='").append(deleteRow).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guest_id", this.guestId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("first_name", this.firstName);
            jsonObject.put("last_name", this.lastName);
            jsonObject.put("company", this.company);
            jsonObject.put("del_row", this.deleteRow);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
