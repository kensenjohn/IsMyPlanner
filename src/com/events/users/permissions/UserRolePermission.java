package com.events.users.permissions;

import com.events.bean.users.permissions.*;
import com.events.common.Constants;
import com.events.common.Utility;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRolePermission {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public boolean initiatePermissionBootup(UserRolePermissionRequestBean userRolePermRequest) {
        boolean isSuccess = false;
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getUserId())
                && userRolePermRequest.getUserType() != Constants.USER_TYPE.NONE ) {
            appLogging.info("bootstrapping permission for user : " + userRolePermRequest.getUserId() + " userType :" + userRolePermRequest.getUserType().getType() );
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

                    BuildRolePermissions buildRolePermissions = new BuildRolePermissions();
                    ArrayList<RolesBean> arrCompletedRolesBeanWithPermissions =  buildRolePermissions.createRolePermissions(  arrSiteAdminRolesBean , arrDefaultPermissionsBean );

                    if(arrCompletedRolesBeanWithPermissions!=null && arrCompletedRolesBeanWithPermissions.size() == arrSiteAdminRolesBean.size() ) {
                        BuildUserRoles buildUserRoles = new BuildUserRoles();
                        ArrayList<UserRolesBean> arrUserRolesBean = buildUserRoles.createUserRole( userRolePermRequest.getUserId(), arrCompletedRolesBeanWithPermissions );
                        if(arrUserRolesBean!=null && !arrUserRolesBean.isEmpty() ) {
                            isSuccess = true;
                        }
                    }
                }
            }
        } else {
            appLogging.error("Error while bootstrapping permission for user : " + userRolePermRequest.getUserId() + " userType :" + userRolePermRequest.getUserType().getType() );

        }
        return isSuccess;
    }

    public UserRolePermissionResponseBean loadRoleDetails( UserRolePermissionRequestBean userRolePermissionRequestBean) {
        UserRolePermissionResponseBean userRolePermissionResponseBean = new UserRolePermissionResponseBean();

        ArrayList<EveryRoleDetailBean>  arrEveryRoleDetailBean = new ArrayList<EveryRoleDetailBean>();

        ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
        if(userRolePermissionRequestBean!=null && !Utility.isNullOrEmpty(userRolePermissionRequestBean.getParentId() )) {
            AccessRoles accessRoles = new AccessRoles();
            arrRolesBean = accessRoles.getRolesByParent( userRolePermissionRequestBean );
        }

        if( arrRolesBean!=null && !arrRolesBean.isEmpty() ) {
            AccessUserRoles accessUserRoles = new AccessUserRoles();
            for(RolesBean rolesBean : arrRolesBean  )  {

                EveryRoleDetailBean everyRoleDetailBean = new EveryRoleDetailBean();
                ArrayList<UserRolesBean> arrUserRolesBean = accessUserRoles.getUserRolesByRoles( rolesBean );


                everyRoleDetailBean.setRoleId( rolesBean.getRoleId() );
                everyRoleDetailBean.setName( rolesBean.getName() );
                if(arrUserRolesBean!=null){
                    everyRoleDetailBean.setAssignedToNumOfUsers( arrUserRolesBean.size() );
                }
                everyRoleDetailBean.setSiteAdmin( rolesBean.isSiteAdmin() );
                arrEveryRoleDetailBean.add(everyRoleDetailBean);

            }
        }
        userRolePermissionResponseBean.setArrEveryRoleDetailBean( arrEveryRoleDetailBean );
        return userRolePermissionResponseBean;

    }

    public JSONObject loadRoleDetailsJson (ArrayList<EveryRoleDetailBean>  arrEveryRoleDetailBean) {
        JSONObject jsonEveryRoleDetail = new JSONObject();
        if(arrEveryRoleDetailBean!=null && !arrEveryRoleDetailBean.isEmpty() ) {
            Integer iTrackNumOfRoles = 0;
            for(EveryRoleDetailBean everyRoleDetailBean : arrEveryRoleDetailBean ) {
                jsonEveryRoleDetail.put(iTrackNumOfRoles.toString(),everyRoleDetailBean.toJson());
                iTrackNumOfRoles++;
            }

            jsonEveryRoleDetail.put("num_of_roles" , iTrackNumOfRoles);
        }
        return jsonEveryRoleDetail;
    }
}
