package com.events.users.permissions;

import com.events.bean.users.permissions.RolePermissionsBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Utility;
import com.events.data.users.permissions.AccessRolePermissionsData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/30/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessRolePermissions {
    public ArrayList<RolePermissionsBean> getRolePermissions(UserRolePermissionRequestBean userRolePermRequest) {
        ArrayList<RolePermissionsBean> arrRolePermissionsBean = new ArrayList<RolePermissionsBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleId() )) {
            AccessRolePermissionsData accessRolePermissionsData = new AccessRolePermissionsData();
            arrRolePermissionsBean = accessRolePermissionsData.getRolePermissions( userRolePermRequest ) ;
        }
        return arrRolePermissionsBean;
    }
}
