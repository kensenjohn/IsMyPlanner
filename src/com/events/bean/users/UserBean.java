package com.events.bean.users;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserBean {
    private String userId = Constants.EMPTY;
    private Constants.USER_TYPE userType = Constants.USER_TYPE.VENDOR;
    private String parentId = Constants.EMPTY;
    private String userInfoId = Constants.EMPTY;
    private Long createDate = 0L;
    private String deleteRow = "0";
    private String humanCreateDate = Constants.EMPTY;
    private UserInfoBean userInfoBean = new UserInfoBean();
    private boolean isUserExists = false;
    private boolean isLoggedInUser = false;

    public UserBean(HashMap<String, String> hmAdminRes) {

        this.userId = ParseUtil.checkNull(hmAdminRes.get("USERID"));
        this.parentId = ParseUtil.checkNull(hmAdminRes.get("FK_PARENTID"));
        this.userInfoId = ParseUtil.checkNull(hmAdminRes.get("FK_USERINFOID"));
        this.createDate = ParseUtil.sToL(hmAdminRes.get("CREATEDATE"));
        this.deleteRow = ParseUtil.checkNull(hmAdminRes.get("DEL_ROW"));
        this.humanCreateDate = ParseUtil.checkNull(hmAdminRes.get("HUMANCREATEDATE"));


        String sUserType = ParseUtil.checkNull(hmAdminRes.get("USERTYPE"));
        if(Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase(sUserType)) {
            userType = Constants.USER_TYPE.VENDOR;
        } else if(Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase(sUserType)) {
            userType = Constants.USER_TYPE.CLIENT;
        } else if(Constants.USER_TYPE.ADMIN.getType().equalsIgnoreCase(sUserType)) {
            userType = Constants.USER_TYPE.ADMIN;
        }

        if (!Utility.isNullOrEmpty(this.userInfoId)) {
            this.userInfoBean = new UserInfoBean(hmAdminRes);

            if (this.userInfoBean.isUserInfoExists()) {
                isUserExists = true;
            }
        }
    }

    public UserBean() {
        // this.adminId = Utility.getNewGuid();
    }

    public void setUserExists(boolean userExists) {
        isUserExists = userExists;
    }

    public boolean isLoggedInUser() {
        return isLoggedInUser;
    }

    public void setLoggedInUser(boolean isLoggedInUser) {
        isLoggedInUser = isLoggedInUser;
    }

    public String getUserId() {
        return userId;
    }

    public Constants.USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(Constants.USER_TYPE userType) {
        this.userType = userType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getDeleteRow() {
        return deleteRow;
    }

    public void setDeleteRow(String deleteRow) {
        this.deleteRow = deleteRow;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public boolean isUserExists() {
        return isUserExists;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId='" + userId + '\'' +
                ", userType=" + userType +
                ", parentId='" + parentId + '\'' +
                ", userInfoId='" + userInfoId + '\'' +
                ", createDate=" + createDate +
                ", deleteRow='" + deleteRow + '\'' +
                ", humanCreateDate='" + humanCreateDate + '\'' +
                ", isUserExists=" + isUserExists +
                ", isLoggedInUser=" + isLoggedInUser +
                '}';
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", this.userId);
            jsonObject.put("userinfo_id", this.userInfoId);
            jsonObject.put("user_type", this.userType.getType());
            jsonObject.put("parent_id", this.parentId);
            jsonObject.put("create_date", this.createDate);
            jsonObject.put("del_row", this.deleteRow);
            jsonObject.put("user_info_id", this.userInfoId);
            jsonObject.put("is_logged_in_user", this.isLoggedInUser);

            if (userInfoBean != null) {

                jsonObject.put("user_info_bean",
                        this.userInfoBean.toJson());
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}
