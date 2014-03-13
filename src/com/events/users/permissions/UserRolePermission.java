package com.events.users.permissions;

import com.events.bean.users.UserBean;
import com.events.bean.users.permissions.*;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Perm;
import com.events.common.Utility;
import com.events.data.users.permissions.AccessPermissionsData;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

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
            ArrayList<RolesBean> arrDefaultRolesBean = accessRoles.getDefaultRoles(userRolePermRequest);  // Get All Possible Roles Allowed for this  Vendor Account

            ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
            if( arrDefaultRolesBean!=null && !arrDefaultRolesBean.isEmpty() ) {
                BuildRoles buildRoles = new BuildRoles();
                arrRolesBean = buildRoles.createRolesFromDefault(arrDefaultRolesBean , userRolePermRequest );  // Assign the Roles to the Vendor. (No Permissions)
            }

            if(arrRolesBean!=null && !arrRolesBean.isEmpty() ) {   // Going to assign Default(All) Permissions to the Site Admin Role. Then assign Role to the User.
                ArrayList<RolesBean> arrSiteAdminRolesBean = new ArrayList<RolesBean>();

                AccessPermissions accessPermissions = new AccessPermissions();
                ArrayList<PermissionsBean> arrDefaultPermissionsBean = accessPermissions.getDefaultPermissions(userRolePermRequest); // Getting All possible permissions

                UserRolePermissionRequestBean userRolePermissionRequestBean = new UserRolePermissionRequestBean();
                userRolePermissionRequestBean.setParentId(Constants.USER_TYPE.VENDOR.getType());

                AccessPermissionsData accessPermissionsData = new AccessPermissionsData();
                ArrayList<PermissionsBean> arrVendorAllPermissionsBean =  accessPermissionsData.getPermissions( userRolePermissionRequestBean );
                HashMap<String,  ArrayList<PermissionsBean>> hmRolePermissions = accessPermissions.getDefaultRolesDefaultPermissions(arrVendorAllPermissionsBean);
                BuildRolePermissions buildRolePermissions = new BuildRolePermissions();
                for(RolesBean rolesBean : arrRolesBean ){

                    ArrayList<PermissionsBean> arrRolePermissions = hmRolePermissions.get(rolesBean.getName());
                    if(arrRolePermissions!=null && !arrRolePermissions.isEmpty()) {
                        Integer iNumOfPermsCreated = buildRolePermissions.createRolePermission(  rolesBean , arrRolePermissions );
                    }

                    if(rolesBean.isSiteAdmin()) {
                        arrSiteAdminRolesBean.add( rolesBean ); // Identified the Site Admin Role.

                        BuildUserRoles buildUserRoles = new BuildUserRoles();
                        ArrayList<UserRolesBean> arrUserRolesBean = buildUserRoles.createUserRole( userRolePermRequest.getUserId(), arrSiteAdminRolesBean );  //Assigning  the Site Admin Role to the User
                        if(arrUserRolesBean!=null && !arrUserRolesBean.isEmpty() ) {
                            isSuccess = true;
                        }
                    }
                }

/*

                ArrayList<RolesBean> arrNotSiteAdminRolesBean = new ArrayList<RolesBean>();
                for(RolesBean rolesBean : arrRolesBean ){
                    if(rolesBean.isSiteAdmin()) {
                        arrSiteAdminRolesBean.add( rolesBean ); // Identified the Site Admin Role.
                    }else {
                        arrNotSiteAdminRolesBean.add(rolesBean);
                    }
                }



                if(arrNotSiteAdminRolesBean!=null && !arrNotSiteAdminRolesBean.isEmpty() ) {

                }

                if(arrSiteAdminRolesBean!=null && !arrSiteAdminRolesBean.isEmpty() ) {

                    ArrayList<RolesBean> arrCompletedRolesBeanWithPermissions = createRolePermission( arrSiteAdminRolesBean , arrDefaultPermissionsBean ) ;

                    if(arrCompletedRolesBeanWithPermissions!=null && arrCompletedRolesBeanWithPermissions.size() == arrSiteAdminRolesBean.size() ) {
                        BuildUserRoles buildUserRoles = new BuildUserRoles();
                        ArrayList<UserRolesBean> arrUserRolesBean = buildUserRoles.createUserRole( userRolePermRequest.getUserId(), arrCompletedRolesBeanWithPermissions );  //Assigning  the Site Admin Role to the User
                        if(arrUserRolesBean!=null && !arrUserRolesBean.isEmpty() ) {
                            isSuccess = true;
                        }
                    }
                }
*/


            }
        } else {
            appLogging.error("Error while bootstrapping permission for user : " + userRolePermRequest.getUserId() + " userType :" + userRolePermRequest.getUserType().getType());

        }
        return isSuccess;
    }

    private ArrayList<RolesBean> createRolePermission(ArrayList<RolesBean> arrRolesBean, ArrayList<PermissionsBean> arrPermissionsBean){
        BuildRolePermissions buildRolePermissions = new BuildRolePermissions();
        ArrayList<RolesBean> arrCompletedRolesBeanWithPermissions =  buildRolePermissions.createRolePermissions(  arrRolesBean , arrPermissionsBean ); //Assigning all permissions to the Site Admin
        return arrCompletedRolesBeanWithPermissions;
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

    public UserRolePermissionResponseBean deleteRole ( UserRolePermissionRequestBean userRolePermissionRequestBean ) {
        UserRolePermissionResponseBean userRolePermissionResponseBean = new UserRolePermissionResponseBean();
        if( userRolePermissionRequestBean!=null && !Utility.isNullOrEmpty(userRolePermissionRequestBean.getRoleId())
                && !Utility.isNullOrEmpty(userRolePermissionRequestBean.getUserId() ) ) {

            AccessRoles accessRoles = new AccessRoles();
            RolesBean rolesBean = accessRoles.getRoleById(userRolePermissionRequestBean);
            if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId() )) {
                if( !rolesBean.isSiteAdmin() && !isDefaultRole(rolesBean) ) {

                    AccessUserRoles accessUserRoles = new AccessUserRoles();
                    ArrayList<UserRolesBean> arrUserRolesBean = accessUserRoles.getUserRolesByRoles( rolesBean ) ;
                    if(arrUserRolesBean == null || (arrUserRolesBean!=null && arrUserRolesBean.isEmpty()) ) {
                        BuildRoles buildRoles = new BuildRoles();
                        boolean isRoleDeleted = buildRoles.deleteRole( rolesBean );
                        if(isRoleDeleted) {
                            userRolePermissionResponseBean.setSuccess(true);

                            BuildRolePermissions buildRolePermissions = new BuildRolePermissions();
                            buildRolePermissions.deleteRolePermissions( rolesBean );
                        } else {
                            appLogging.info("Cannot Delete : Role was not be deleted : " + ParseUtil.checkNullObject(rolesBean) );
                            userRolePermissionResponseBean.setSuccess(false);
                            userRolePermissionResponseBean.setMessage( "We were unable to delete the role your requested.");
                        }

                    } else {
                        appLogging.info("Cannot Delete : The role has users assigned to it- num of users : " + arrUserRolesBean.size() );
                        userRolePermissionResponseBean.setSuccess(false);
                        userRolePermissionResponseBean.setMessage( "Please make sure that no users are assigned to this role.");
                    }
                } else{
                    appLogging.info("Cannot Delete : The role is a site admin : " + rolesBean );
                    userRolePermissionResponseBean.setSuccess(false);
                    userRolePermissionResponseBean.setMessage( "This role may not be deleted. Please contact your support representative.");
                }
            } else {
                appLogging.info("The role Id does not exist : " + userRolePermissionRequestBean.getRoleId() );
                userRolePermissionResponseBean.setSuccess(false);
                userRolePermissionResponseBean.setMessage( "Please select a valid role.");
            }
        } else{
            appLogging.info("Invalid request : " + ParseUtil.checkNullObject(userRolePermissionRequestBean));
            userRolePermissionResponseBean.setSuccess(false);
            userRolePermissionResponseBean.setMessage( "We were unable to process your request at this time.");
        }
        return userRolePermissionResponseBean;
    }

    private boolean isDefaultRole(RolesBean rolesBean ) {
        boolean isDefaultRole = false;
        if(rolesBean!=null && (Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase(rolesBean.getParentId()) ||
                Constants.USER_TYPE.ADMIN.getType().equalsIgnoreCase(rolesBean.getParentId()) ||
                Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase(rolesBean.getParentId())||
                Constants.USER_TYPE.SUPERUSER.getType().equalsIgnoreCase(rolesBean.getParentId()))  )  {
            isDefaultRole = true;
        }
        return isDefaultRole;
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

    public UserRolePermissionResponseBean getRolePermissions( UserRolePermissionRequestBean userRolePermnRequest ) {

        UserRolePermissionResponseBean userRolePermissionResponseBean = new UserRolePermissionResponseBean();

        if(userRolePermnRequest!=null && userRolePermnRequest.getUserType()!=null) {
            AccessPermissions accessPermissions = new AccessPermissions();
            ArrayList<PermissionGroupBean> arrPermissionGroupBean = accessPermissions.getDefaultPermissionsGroups(userRolePermnRequest);
            ArrayList<PermissionsBean> arrDefaultPermissionsBean = accessPermissions.getDefaultPermissions( userRolePermnRequest ) ;
            ArrayList<RolePermissionsBean> arrRolePermissionsBean = new ArrayList<RolePermissionsBean>();
            if(arrDefaultPermissionsBean!=null && !arrDefaultPermissionsBean.isEmpty() && !Utility.isNullOrEmpty(userRolePermnRequest.getRoleId()))  {
                AccessRolePermissions accessRolePermissions = new AccessRolePermissions();
                arrRolePermissionsBean = accessRolePermissions.getRolePermissions( userRolePermnRequest );
            }
            userRolePermissionResponseBean.setArrPermissionGroupBean( arrPermissionGroupBean );
            userRolePermissionResponseBean.setArrDefaultPermissionsBean( arrDefaultPermissionsBean );
            userRolePermissionResponseBean.setArrRolePermissionsBean( arrRolePermissionsBean );


            AccessRoles accessRoles = new AccessRoles();
            RolesBean roleBean  = accessRoles.getRoleById(userRolePermnRequest);
            userRolePermissionResponseBean.setRoleBean(roleBean);

            //hmPermissionTables = createRolePermissionTable(arrPermissionGroupBean ,arrDefaultPermissionsBean , arrRolePermissionsBean );
        }
        return userRolePermissionResponseBean;
    }

    public RolesBean saveRolePersmissions(UserRolePermissionRequestBean userRolePermRequest){
        RolesBean roleBean  = new RolesBean();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleName())) {

            ArrayList<String> arrPermissionId = userRolePermRequest.getArrPermissionId();
            if( arrPermissionId!=null  && !arrPermissionId.isEmpty() ) {
                AccessRoles accessRoles = new AccessRoles();
                if(!Utility.isNullOrEmpty(userRolePermRequest.getRoleId())) {
                    roleBean = accessRoles.getRoleById(userRolePermRequest);
                }

                if(roleBean!=null && Utility.isNullOrEmpty(roleBean.getRoleId())) {
                    roleBean = createRole(userRolePermRequest);
                } else {
                    roleBean = updateRole(userRolePermRequest);
                }

                if(roleBean!=null && !Utility.isNullOrEmpty(roleBean.getRoleId())) {
                    userRolePermRequest.setRoleId( ParseUtil.checkNull(roleBean.getRoleId()) );
                    AccessRolePermissions accessRolePermissions = new AccessRolePermissions();
                    boolean isRolePermissionCreationSuccess = accessRolePermissions.saveRolePermissions( userRolePermRequest );
                    if(!isRolePermissionCreationSuccess) {
                        BuildRoles buildRoles = new BuildRoles();
                        buildRoles.deleteRole( roleBean );
                        roleBean = new RolesBean();
                    }
                } else {
                    appLogging.error("There was error saving the RoleBean");
                }
            }
        }
        return roleBean;
    }

    public RolesBean createRole(UserRolePermissionRequestBean userRolePermRequest) {
        RolesBean newRoleBean = new RolesBean();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleName())
                && !Utility.isNullOrEmpty(userRolePermRequest.getParentId() ) && userRolePermRequest.getArrPermissionId()!=null
                && ! userRolePermRequest.getArrPermissionId().isEmpty()) {

            newRoleBean.setName( userRolePermRequest.getRoleName() );
            newRoleBean.setParentId( userRolePermRequest.getParentId() );
            newRoleBean.setSiteAdmin( userRolePermRequest.isSiteAdmin() );

            BuildRoles buildRoles = new BuildRoles();
            newRoleBean = buildRoles.createRole( newRoleBean );
        }
        return newRoleBean;
    }
    public RolesBean updateRole(UserRolePermissionRequestBean userRolePermRequest) {
        RolesBean updatedRoleBean = new RolesBean();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleName())
                && !Utility.isNullOrEmpty(userRolePermRequest.getParentId())  && !Utility.isNullOrEmpty(userRolePermRequest.getRoleId() )
                && userRolePermRequest.getArrPermissionId()!=null
                && ! userRolePermRequest.getArrPermissionId().isEmpty()) {
            updatedRoleBean.setRoleId( userRolePermRequest.getRoleId() );
            updatedRoleBean.setName( userRolePermRequest.getRoleName() );
            updatedRoleBean.setParentId( userRolePermRequest.getParentId() );
            updatedRoleBean.setSiteAdmin(userRolePermRequest.isSiteAdmin());

            BuildRoles buildRoles = new BuildRoles();
            updatedRoleBean = buildRoles.updateRoleById(updatedRoleBean);

        }
        return updatedRoleBean;
    }
}
