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

    public ArrayList<PermissionsBean> getDefaultPermissionsByRole(  RolesBean rolesBean , ArrayList<PermissionsBean> arrVendorAllPermissionsBean ) {
        ArrayList<PermissionsBean> arrDefaultPermissionsBean = new ArrayList<PermissionsBean>();
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getName()) && !Utility.isNullOrEmpty(rolesBean.getRoleId()) ){
                if("Lead Coordinator".equalsIgnoreCase(rolesBean.getName()))  {
                    arrDefaultPermissionsBean = getLeadCoordinatorPermission(arrVendorAllPermissionsBean);
                } else if("Intern".equalsIgnoreCase(rolesBean.getName()))  {
                    arrDefaultPermissionsBean = getInternPermission(arrVendorAllPermissionsBean);
                }  else if("Client".equalsIgnoreCase(rolesBean.getName()))  {
                    arrDefaultPermissionsBean = getClientPermission(arrVendorAllPermissionsBean);
                }
        }
        return arrDefaultPermissionsBean;
    }

    private void getDefaultRolesDefaultPermissions(ArrayList<PermissionsBean> arrVendorAllPermissionsBean){
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
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.CREATE_NEW_CLIENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
            }  else  if(Perm.SEE_CLIENT_LIST.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.VIEW_ROLE_PERMMISIONS.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.EDIT_ROLE_PERMISSION.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.DELETE_ROLE.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrSiteAdminPermission.add( permissionsBean );
            }  else  if(Perm.ACCESS_DASHBOARD_TAB.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.MANAGE_VENDOR_WEBSITE.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.MANAGE_TEAM_MEMBERS.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.MANAGE_ROLE_PERMISSION.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
            } else  if(Perm.MANAGE_PARTNER_VENDORS.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
            }
            else  if(Perm.ACCESS_EVENTS_TAB.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
                arrClientPermission.add(permissionsBean);
            } else  if(Perm.CREATE_NEW_EVENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
                arrClientPermission.add(permissionsBean);
            } else  if(Perm.DELETE_EVENT.toString().equalsIgnoreCase(permissionsBean.getShortName())) {
                arrLeadCoordinatorPermission.add( permissionsBean );
                arrInternPermission.add( permissionsBean );
                arrSiteAdminPermission.add( permissionsBean );
                arrClientPermission.add(permissionsBean);
            }
        }
    }

    private ArrayList<PermissionsBean> getLeadCoordinatorPermission(ArrayList<PermissionsBean> arrAllPermissionsBean){
        ArrayList<PermissionsBean> arrPermissionsBean = new ArrayList<PermissionsBean>();
        if(arrAllPermissionsBean!=null && !arrAllPermissionsBean.isEmpty()) {
            for(PermissionsBean permissionsBean : arrAllPermissionsBean ) {

            }
        }
        return arrPermissionsBean;
    }
    private ArrayList<PermissionsBean> getInternPermission(ArrayList<PermissionsBean> arrAllPermissionsBean){
        ArrayList<PermissionsBean> arrPermissionsBean = new ArrayList<PermissionsBean>();
        if(arrAllPermissionsBean!=null && !arrAllPermissionsBean.isEmpty()) {
            for(PermissionsBean permissionsBean : arrAllPermissionsBean ) {
            }
        }
        return arrPermissionsBean;
    }
    private ArrayList<PermissionsBean> getClientPermission(ArrayList<PermissionsBean> arrAllPermissionsBean){
        ArrayList<PermissionsBean> arrPermissionsBean = new ArrayList<PermissionsBean>();
        if(arrAllPermissionsBean!=null && !arrAllPermissionsBean.isEmpty()) {
            for(PermissionsBean permissionsBean : arrAllPermissionsBean ) {

            }
        }
        return arrPermissionsBean;
    }
}
