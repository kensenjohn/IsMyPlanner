package com.events.bean.users.permissions;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/30/14
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserRolePermissionResponseBean {
    private boolean isSuccess = false;
    private String message = Constants.EMPTY;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ArrayList<EveryRoleDetailBean> arrEveryRoleDetailBean = new ArrayList<EveryRoleDetailBean>();

    public ArrayList<EveryRoleDetailBean> getArrEveryRoleDetailBean() {
        return arrEveryRoleDetailBean;
    }

    public void setArrEveryRoleDetailBean(ArrayList<EveryRoleDetailBean> arrEveryRoleDetailBean) {
        this.arrEveryRoleDetailBean = arrEveryRoleDetailBean;
    }
}
