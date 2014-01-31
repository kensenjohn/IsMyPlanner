package com.events.users.permissions;

import com.events.bean.users.permissions.RolePermissionsBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.users.permissions.AccessRolePermissionsData;
import com.events.data.users.permissions.BuildRolePermissionsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/30/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessRolePermissions {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public ArrayList<RolePermissionsBean> getRolePermissions(UserRolePermissionRequestBean userRolePermRequest) {
        ArrayList<RolePermissionsBean> arrRolePermissionsBean = new ArrayList<RolePermissionsBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleId() )) {
            AccessRolePermissionsData accessRolePermissionsData = new AccessRolePermissionsData();
            arrRolePermissionsBean = accessRolePermissionsData.getRolePermissions( userRolePermRequest ) ;
        }
        return arrRolePermissionsBean;
    }

    public boolean doesRoleHavePerssmissions( UserRolePermissionRequestBean userRolePermRequest ) {
        boolean isRoleHavePermissions = false;
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleId() )) {
            ArrayList<RolePermissionsBean> arrRolePermissionsBean = getRolePermissions(userRolePermRequest);
            if(arrRolePermissionsBean!=null && !arrRolePermissionsBean.isEmpty()) {
                isRoleHavePermissions = true;
            }
        }
        return isRoleHavePermissions;
    }

    public boolean saveRolePermissions( UserRolePermissionRequestBean userRolePermRequest ) {
        boolean isSuccess = false;
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleId()) && userRolePermRequest.getArrPermissionId()!=null
                && !userRolePermRequest.getArrPermissionId().isEmpty() ) {
            ArrayList<RolePermissionsBean> arrExistingOldRolePermissionsBean = getRolePermissions(userRolePermRequest);
            ArrayList<RolePermissionsBean> arrNewRolePermissionsBean = generateRolePermissions(userRolePermRequest);

            ArrayList<RolePermissionsBean> arrDeleteRolePermissions = new ArrayList<RolePermissionsBean>();

            BuildRolePermissionsData buildRolePermissionsData = new BuildRolePermissionsData();
            Integer iNumOfPermissionsInserted =  buildRolePermissionsData.insertRolePermissionBatch( arrNewRolePermissionsBean );
            if(iNumOfPermissionsInserted ==  arrNewRolePermissionsBean.size() ) {
                arrDeleteRolePermissions = arrExistingOldRolePermissionsBean;
                isSuccess = true;
            } else {
                arrDeleteRolePermissions = arrNewRolePermissionsBean;
            }

            if(arrDeleteRolePermissions!=null && !arrDeleteRolePermissions.isEmpty()) {
                buildRolePermissionsData.deleteRolePermissionBatch( arrDeleteRolePermissions );
            }

        }
        return isSuccess;
    }

    public ArrayList<RolePermissionsBean> generateRolePermissions( UserRolePermissionRequestBean userRolePermRequest ) {
        ArrayList<RolePermissionsBean> arrNewRolePermissionsBean = new ArrayList<RolePermissionsBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleId()) && userRolePermRequest.getArrPermissionId()!=null
                && !userRolePermRequest.getArrPermissionId().isEmpty() ) {
            ArrayList<String> arrPermissionId = userRolePermRequest.getArrPermissionId();
            String sRoleId = ParseUtil.checkNull(userRolePermRequest.getRoleId());

            for(String permissionId : arrPermissionId ) {
                RolePermissionsBean rolePermissionsBean = new RolePermissionsBean();
                rolePermissionsBean.setRolePermissionId( Utility.getNewGuid() );
                rolePermissionsBean.setRoleId( sRoleId );
                rolePermissionsBean.setPermissionId(permissionId);
                arrNewRolePermissionsBean.add( rolePermissionsBean );
            }
        }
        return arrNewRolePermissionsBean;
    }

    public void deleteRolePermissions( ArrayList<RolePermissionsBean> arrRolePermissionsBean ) {
        if(arrRolePermissionsBean!=null && !arrRolePermissionsBean.isEmpty() ) {

        }
    }
}
