package com.events.bean.clients;

import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/17/13
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientResponseBean {
    private String clientId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String userInfoId = Constants.EMPTY;
    private ClientBean clientBean = new ClientBean();
    private UserBean userBean = new UserBean();
    private UserInfoBean userInfoBean = new UserInfoBean();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
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

    public ClientBean getClientBean() {
        return clientBean;
    }

    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_id", this.clientId);
            jsonObject.put("user_id", this.userId);
            jsonObject.put("userinfo_id", this.userInfoId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
