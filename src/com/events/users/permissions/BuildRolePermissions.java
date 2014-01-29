package com.events.users.permissions;

import com.events.bean.users.permissions.PermissionsBean;
import com.events.bean.users.permissions.RolePermissionsBean;
import com.events.bean.users.permissions.RolesBean;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildRolePermissions {
    public void buildRolePermissions(ArrayList<PermissionsBean> arrPermissionsBean , ArrayList<RolesBean> arrRolesBean) {
        if(arrPermissionsBean!=null && !arrPermissionsBean.isEmpty() && arrRolesBean!=null && !arrRolesBean.isEmpty()) {
            RolePermissionsBean rolePermissionsBean = new RolePermissionsBean();
        }
    }
}
