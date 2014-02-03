package com.events.users.permissions;

import com.events.bean.users.UserBean;
import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolesBean;
import com.events.common.Utility;
import com.events.data.users.permissions.AccessUserRolesData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/29/14
 * Time: 11:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessUserRoles {
    public ArrayList<UserRolesBean> getUserRolesByRoles(RolesBean rolesBean) {
        ArrayList<UserRolesBean> arrUserRolesBean = new ArrayList<UserRolesBean>();
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId())) {
            AccessUserRolesData accessUserRolesData = new AccessUserRolesData();
            arrUserRolesBean = accessUserRolesData.getUserRoles(rolesBean) ;
        }
        return arrUserRolesBean;
    }

    public ArrayList<UserRolesBean> getUserRolesByUserId(UserBean userBean) {
        ArrayList<UserRolesBean> arrUserRolesBean = new ArrayList<UserRolesBean>();
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
            AccessUserRolesData accessUserRolesData = new AccessUserRolesData();
            arrUserRolesBean = accessUserRolesData.getUserRolesByUser(userBean) ;
        }
        return arrUserRolesBean;
    }
}
