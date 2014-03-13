package com.events.users.permissions;

import com.events.bean.users.permissions.PermissionsBean;
import com.events.bean.users.permissions.RolePermissionsBean;
import com.events.bean.users.permissions.RolesBean;
import com.events.common.Utility;
import com.events.data.users.permissions.BuildRolePermissionsData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildRolePermissions {
    public ArrayList<RolesBean> createRolePermissions(ArrayList<RolesBean> arrRolesBean , ArrayList<PermissionsBean> arrPermissionsBean) {
        ArrayList<RolesBean> arrRolesBeanWithPermissions = new ArrayList<RolesBean>();
        if(arrPermissionsBean!=null && !arrPermissionsBean.isEmpty() && arrRolesBean!=null && !arrRolesBean.isEmpty()) {

            for(RolesBean rolesBean : arrRolesBean ) {
                Integer iNumOfRows = createRolePermission(rolesBean , arrPermissionsBean ) ;

                if(iNumOfRows ==  arrPermissionsBean.size() ) {
                    arrRolesBeanWithPermissions.add( rolesBean );
                }
            }
        }
        return arrRolesBeanWithPermissions;
    }

    public Integer createRolePermission(RolesBean rolesBean , ArrayList<PermissionsBean> arrPermissionsBean ){
        Integer iNumOfPermsCreated = 0;
        if(arrPermissionsBean!=null && !arrPermissionsBean.isEmpty() && rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId())) {
            BuildRolePermissionsData buildRolePermissionsData = new BuildRolePermissionsData();
            for(PermissionsBean permissionsBean : arrPermissionsBean ) {
                RolePermissionsBean rolePermissionsBean = new RolePermissionsBean();
                rolePermissionsBean.setRolePermissionId(Utility.getNewGuid() );
                rolePermissionsBean.setRoleId( rolesBean.getRoleId() );
                rolePermissionsBean.setPermissionId( permissionsBean.getPermissionId() );

                iNumOfPermsCreated = iNumOfPermsCreated + buildRolePermissionsData.insertRolePermission( rolePermissionsBean );
            }
        }
        return iNumOfPermsCreated;
    }

    public boolean deleteRolePermissions(RolesBean rolesBean){
        boolean isRolePermissionDeleted = false;
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId())) {
            BuildRolePermissionsData buildRolePermissionsData = new BuildRolePermissionsData();
            if( buildRolePermissionsData.deleteRolePermissionByRoleId(rolesBean) > 0 ) {
                isRolePermissionDeleted = true;
            }
        }
        return isRolePermissionDeleted;
    }
}
