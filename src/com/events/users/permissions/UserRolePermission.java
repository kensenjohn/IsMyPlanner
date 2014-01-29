package com.events.users.permissions;

import com.events.bean.users.permissions.PermissionsBean;
import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Constants;
import com.events.common.Utility;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRolePermission {
    public void initiatePermissionBootup(UserRolePermissionRequestBean userRolePermRequest) {
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getUserId())
                && userRolePermRequest.getUserType() != Constants.USER_TYPE.NONE ) {
            AccessRoles accessRoles = new AccessRoles();
            ArrayList<RolesBean> arrDefaultRolesBean = accessRoles.getDefaultRoles(userRolePermRequest);

            ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
            if( arrDefaultRolesBean!=null && !arrDefaultRolesBean.isEmpty() ) {
                BuildRoles buildRoles = new BuildRoles();
                arrRolesBean = buildRoles.createRolesFromDefault(arrDefaultRolesBean , userRolePermRequest );
            }

            if(arrRolesBean!=null && !arrRolesBean.isEmpty() ) {
                ArrayList<RolesBean> arrSiteAdminRolesBean = new ArrayList<RolesBean>();
                for(RolesBean rolesBean : arrRolesBean ){
                    if(rolesBean.isSiteAdmin()) {
                        arrSiteAdminRolesBean.add( rolesBean );
                    }
                }

                if(arrSiteAdminRolesBean!=null && !arrSiteAdminRolesBean.isEmpty() ) {
                    AccessPermissions accessPermissions = new AccessPermissions();
                    ArrayList<PermissionsBean> arrDefaultPermissionsBean = accessPermissions.getDefaultPermissions(userRolePermRequest);
                }
            }
        }
    }
}
