package com.events.bean.clients;

import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/17/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientRequestBean {
    private String clientId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientName = Constants.EMPTY;
    private String dataDetail = Constants.EMPTY;
    private boolean isCorporateClient = false;
    private UserRequestBean userRequestBean = new UserRequestBean();

    private UserBean userBean = new UserBean();
    private UserInfoBean userInfoBean = new UserInfoBean();

    public String getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(String dataDetail) {
        this.dataDetail = dataDetail;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isCorporateClient() {
        return isCorporateClient;
    }

    public void setCorporateClient(boolean corporateClient) {
        isCorporateClient = corporateClient;
    }

    public UserRequestBean getUserRequestBean() {
        return userRequestBean;
    }

    public void setUserRequestBean(UserRequestBean userRequestBean) {
        this.userRequestBean = userRequestBean;
    }

    @Override
    public String toString() {
        return "ClientRequestBean{" +
                "clientId='" + clientId + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", isCorporateClient=" + isCorporateClient +
                ", userRequestBean=" + userRequestBean +
                ", userBean=" + userBean +
                ", userInfoBean=" + userInfoBean +
                '}';
    }
}
