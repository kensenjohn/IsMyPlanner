package com.events.bean.users.permissions;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/30/14
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserRolePermissionResponseBean {
    private ArrayList<EveryRoleDetailBean> arrEveryRoleDetailBean = new ArrayList<EveryRoleDetailBean>();

    public ArrayList<EveryRoleDetailBean> getArrEveryRoleDetailBean() {
        return arrEveryRoleDetailBean;
    }

    public void setArrEveryRoleDetailBean(ArrayList<EveryRoleDetailBean> arrEveryRoleDetailBean) {
        this.arrEveryRoleDetailBean = arrEveryRoleDetailBean;
    }
}
