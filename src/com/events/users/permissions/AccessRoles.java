package com.events.users.permissions;

import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.users.permissions.AccessRolesData;
import com.events.data.users.permissions.BuildRolesData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessRoles {
    public ArrayList<RolesBean> getDefaultRoles( UserRolePermissionRequestBean userRolePermRequest ) {
        ArrayList<RolesBean> arrDefaultRolesBean = new ArrayList<RolesBean>();
        if(userRolePermRequest!=null && userRolePermRequest.getUserType()!=null && Constants.USER_TYPE.NONE!=userRolePermRequest.getUserType() ) {
            UserRolePermissionRequestBean tmpUserRolePermRequest = new UserRolePermissionRequestBean();
            tmpUserRolePermRequest.setParentId( userRolePermRequest.getUserType().getType() );
            AccessRolesData accessRolesData = new AccessRolesData();
            arrDefaultRolesBean = accessRolesData.getRolesByParent( tmpUserRolePermRequest ) ;
        }
        return arrDefaultRolesBean;
    }

    public ArrayList<RolesBean> getRolesByParent(  UserRolePermissionRequestBean userRolePermRequest  ) {
        ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty( userRolePermRequest.getParentId()) ) {
            AccessRolesData accessRolesData = new AccessRolesData();
            arrRolesBean = accessRolesData.getRolesByParent( userRolePermRequest ) ;
        }
        return arrRolesBean;
    }

    public RolesBean getRoleById(  UserRolePermissionRequestBean userRolePermRequest ) {
        RolesBean roleBean  = new RolesBean();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty( userRolePermRequest.getRoleId()) ) {
            AccessRolesData accessRolesData = new AccessRolesData();
            ArrayList<RolesBean> arrRolesBean = accessRolesData.getRoleById(userRolePermRequest);
            if(arrRolesBean!=null && !arrRolesBean.isEmpty()) {
                for(RolesBean tmpRolesBean : arrRolesBean )  {
                    roleBean = tmpRolesBean;
                }
            }
        }
        return roleBean;
    }

    public Integer deleteRole(RolesBean rolesBean) {
        Integer iNumOfRowsDeleted = 0;
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId())) {
            BuildRolesData buildRolesData = new BuildRolesData();
        }
        return iNumOfRowsDeleted;
    }
}
