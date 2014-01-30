package com.events.users.permissions;

import com.events.bean.users.UserBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Perm;
import com.events.common.Utility;
import com.events.data.users.permissions.CheckPermissionData;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/30/14
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckPermission {
    private UserBean userBean = new UserBean();
    public CheckPermission(UserBean userBean) {
        this.userBean = userBean;
    }

    public boolean can(Perm perm){
        boolean hasPermission = false;
        if(perm!=null && userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId()) ) {
            UserRolePermissionRequestBean userRolePermissionRequestBean = new UserRolePermissionRequestBean();
            userRolePermissionRequestBean.setUserId( this.userBean.getUserId()   );
            CheckPermissionData checkPermissionData = new CheckPermissionData();
            if ( checkPermissionData.getUserPermission( userBean.getUserId() , perm ) > 0 ) {
                hasPermission = true;
            }
        }
        return hasPermission;
    }
}
