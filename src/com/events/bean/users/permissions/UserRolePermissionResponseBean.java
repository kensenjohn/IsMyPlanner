package com.events.bean.users.permissions;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/30/14
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserRolePermissionResponseBean {
    private boolean isSuccess = false;
    private String message = Constants.EMPTY;

    public boolean isSuccess() {
        return isSuccess;
    }

    /*
    ArrayList<PermissionGroupBean> arrPermissionGroupBean = accessPermissions.getDefaultPermissionsGroups(userRolePermnRequest);
            ArrayList<PermissionsBean> arrDefaultPermissionsBean = accessPermissions.getDefaultPermissions( userRolePermnRequest ) ;
            ArrayList<RolePermissionsBean> arrRolePermissionsBean = new ArrayList<RolePermissionsBean>();
     */
    private ArrayList<PermissionGroupBean> arrPermissionGroupBean = new ArrayList<PermissionGroupBean>();
    private ArrayList<PermissionsBean> arrDefaultPermissionsBean = new ArrayList<PermissionsBean>();
    private ArrayList<RolePermissionsBean> arrRolePermissionsBean = new ArrayList<RolePermissionsBean>();
    private RolesBean roleBean  = new RolesBean();

    public RolesBean getRoleBean() {
        return roleBean;
    }

    public void setRoleBean(RolesBean roleBean) {
        this.roleBean = roleBean;
    }

    public ArrayList<PermissionGroupBean> getArrPermissionGroupBean() {
        return arrPermissionGroupBean;
    }

    public void setArrPermissionGroupBean(ArrayList<PermissionGroupBean> arrPermissionGroupBean) {
        this.arrPermissionGroupBean = arrPermissionGroupBean;
    }

    public ArrayList<PermissionsBean> getArrDefaultPermissionsBean() {
        return arrDefaultPermissionsBean;
    }

    public void setArrDefaultPermissionsBean(ArrayList<PermissionsBean> arrDefaultPermissionsBean) {
        this.arrDefaultPermissionsBean = arrDefaultPermissionsBean;
    }

    public ArrayList<RolePermissionsBean> getArrRolePermissionsBean() {
        return arrRolePermissionsBean;
    }

    public void setArrRolePermissionsBean(ArrayList<RolePermissionsBean> arrRolePermissionsBean) {
        this.arrRolePermissionsBean = arrRolePermissionsBean;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ArrayList<EveryRoleDetailBean> arrEveryRoleDetailBean = new ArrayList<EveryRoleDetailBean>();

    public ArrayList<EveryRoleDetailBean> getArrEveryRoleDetailBean() {
        return arrEveryRoleDetailBean;
    }

    public void setArrEveryRoleDetailBean(ArrayList<EveryRoleDetailBean> arrEveryRoleDetailBean) {
        this.arrEveryRoleDetailBean = arrEveryRoleDetailBean;
    }
}
