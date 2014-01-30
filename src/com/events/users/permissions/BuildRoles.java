package com.events.users.permissions;

import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Utility;
import com.events.data.users.permissions.BuildRolesData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildRoles {
    public ArrayList<RolesBean> createRolesFromDefault(ArrayList<RolesBean> arrDefaultRolesBean, UserRolePermissionRequestBean userRolePermRequest) {

        ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
        if(arrDefaultRolesBean!=null && !arrDefaultRolesBean.isEmpty() && userRolePermRequest !=null
                && !Utility.isNullOrEmpty(userRolePermRequest.getParentId() ) ) {
            BuildRolesData buildRolesData = new BuildRolesData();
            Integer iNumOfRows = 0;
            for(RolesBean tmpRolesBean : arrDefaultRolesBean ) {
                RolesBean rolesBean = new RolesBean();
                rolesBean.setRoleId( Utility.getNewGuid() );
                rolesBean.setParentId( userRolePermRequest.getParentId() );
                rolesBean.setName( tmpRolesBean.getName() );
                rolesBean.setSiteAdmin( tmpRolesBean.isSiteAdmin() );

                iNumOfRows = iNumOfRows + buildRolesData.insertRole( rolesBean );
                if(iNumOfRows>0) {
                    arrRolesBean.add( rolesBean );
                }
            }
        }
        return arrRolesBean;
    }

    public boolean deleteRole(RolesBean rolesBean){
        boolean isRoleDeleted = false;
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId()))  {
            BuildRolesData buildRolesData = new BuildRolesData();
            if( buildRolesData.deletetRole(rolesBean) > 0 ) {
                isRoleDeleted = true;
            }
        }
        return isRoleDeleted;
    }
}
