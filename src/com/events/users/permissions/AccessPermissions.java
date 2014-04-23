package com.events.users.permissions;

import com.events.bean.users.permissions.PermissionGroupBean;
import com.events.bean.users.permissions.PermissionsBean;
import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Constants;
import com.events.common.Perm;
import com.events.common.Utility;
import com.events.data.users.permissions.AccessPermissionsData;

import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<String,  ArrayList<PermissionsBean>> getDefaultRolesDefaultPermissions(ArrayList<PermissionsBean> arrVendorAllPermissionsBean){

        HashMap<String,  ArrayList<PermissionsBean>> hmRolePermissions = new HashMap<String, ArrayList<PermissionsBean>>();

        ArrayList<PermissionsBean>  arrSiteAdminPermission = new ArrayList<PermissionsBean>();
        ArrayList<PermissionsBean>  arrLeadCoordinatorPermission = new ArrayList<PermissionsBean>();
        ArrayList<PermissionsBean>  arrInternPermission = new ArrayList<PermissionsBean>();
        ArrayList<PermissionsBean>  arrClientPermission = new ArrayList<PermissionsBean>();
        /*
            ACCESS_CLIENTS_TAB,CREATE_NEW_CLIENT,SEE_CLIENT_LIST,
    VIEW_ROLE_PERMMISIONS,EDIT_ROLE_PERMISSION,DELETE_ROLE,
    ACCESS_DASHBOARD_TAB, MANAGE_VENDOR_WEBSITE, MANAGE_TEAM_MEMBERS, MANAGE_ROLE_PERMISSION, MANAGE_PARTNER_VENDORS;

    ACCESS_EVENTS_TAB,CREATE_NEW_EVENT,DELETE_EVENT,
         */
        for(PermissionsBean permissionsBean : arrVendorAllPermissionsBean ) {
            if(Perm.ACCESS_CLIENTS_TAB.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
            } else  if(Perm.CREATE_NEW_CLIENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );

            }  else  if(Perm.DELETE_CLIENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
            } else  if(Perm.EDIT_CLIENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );

            } else  if(Perm.EDIT_ROLE_PERMISSION.toString().equalsIgnoreCase(permissionsBean.getShortName())) {

            } else  if(Perm.DELETE_ROLE.toString().equalsIgnoreCase(permissionsBean.getShortName())) {

            }  else  if(Perm.ACCESS_DASHBOARD_TAB.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );

                arrClientPermission.add(permissionsBean);
            } else  if(Perm.MANAGE_VENDOR_WEBSITE.toString().equalsIgnoreCase(permissionsBean.getShortName())) {

            } else  if(Perm.MANAGE_TEAM_MEMBERS.toString().equalsIgnoreCase(permissionsBean.getShortName())) {

            } else  if(Perm.MANAGE_ROLE_PERMISSION.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );

            } else  if(Perm.MANAGE_PARTNER_VENDORS.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );

            }
            else  if(Perm.ACCESS_EVENTS_TAB.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );

                arrClientPermission.add(permissionsBean);
            } else  if(Perm.CREATE_NEW_EVENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );

                arrClientPermission.add(permissionsBean);
            } else  if(Perm.DELETE_EVENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrClientPermission.add(permissionsBean);
            }  else  if(Perm.EDIT_INVOICE.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
            }  else  if(Perm.VIEW_INVOICE.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrClientPermission.add(permissionsBean);
            }

            hmRolePermissions.put("Site Admin",arrVendorAllPermissionsBean);
            hmRolePermissions.put("Lead Coordinator",arrLeadCoordinatorPermission);
            hmRolePermissions.put("Intern",arrInternPermission);
            hmRolePermissions.put("Client",arrClientPermission);
        }
        return hmRolePermissions;
    }
}
