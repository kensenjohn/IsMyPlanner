package com.events.users.permissions;

import com.events.bean.users.permissions.PermissionGroupBean;
import com.events.bean.users.permissions.PermissionsBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.users.permissions.AccessPermissionsData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessPermissions {
    public ArrayList<PermissionsBean> getDefaultPermissions( UserRolePermissionRequestBean userRolePermRequest) {
        ArrayList<PermissionsBean> arrDefaultPermissionsBean = new ArrayList<PermissionsBean>();
        if(userRolePermRequest!=null && userRolePermRequest.getUserType()!=null && Constants.USER_TYPE.NONE!=userRolePermRequest.getUserType() ) {
            UserRolePermissionRequestBean tmpUserRolePermRequest = new UserRolePermissionRequestBean();
            tmpUserRolePermRequest.setParentId( userRolePermRequest.getUserType().getType() );

            AccessPermissionsData accessPermissionsData = new AccessPermissionsData();
            arrDefaultPermissionsBean = accessPermissionsData.getPermissions( tmpUserRolePermRequest );
        }

        return arrDefaultPermissionsBean;
    }

    public ArrayList<PermissionGroupBean> getDefaultPermissionsGroups( UserRolePermissionRequestBean userRolePermRequest) {
        ArrayList<PermissionGroupBean> arrPermissionGroupBean = new ArrayList<PermissionGroupBean>();
        if(userRolePermRequest!=null && userRolePermRequest.getUserType()!=null && Constants.USER_TYPE.NONE!=userRolePermRequest.getUserType() ) {
            UserRolePermissionRequestBean tmpUserRolePermRequest = new UserRolePermissionRequestBean();
            tmpUserRolePermRequest.setParentId( userRolePermRequest.getUserType().getType() );

            AccessPermissionsData accessPermissionsData = new AccessPermissionsData();
            arrPermissionGroupBean = accessPermissionsData.getPermissionGroups( tmpUserRolePermRequest );

        }
        return arrPermissionGroupBean;
    }
}
