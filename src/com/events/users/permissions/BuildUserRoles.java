package com.events.users.permissions;

import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolesBean;
import com.events.common.Utility;
import com.events.data.users.permissions.BuildUserRolesData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildUserRoles {
    public ArrayList<UserRolesBean> createUserRole(String sUserId, ArrayList<RolesBean> arrRoleBean) {
        ArrayList<UserRolesBean> arrUserRolesBean = new ArrayList<UserRolesBean>();
        if(!Utility.isNullOrEmpty(sUserId) && arrRoleBean!=null && !arrRoleBean.isEmpty() ) {
            BuildUserRolesData buildUserRolesData  = new BuildUserRolesData();
            for( RolesBean roleBean : arrRoleBean ) {
                UserRolesBean userRolesBean = new UserRolesBean();
                userRolesBean.setUserRoleId( Utility.getNewGuid() );
                userRolesBean.setUserId( sUserId );
                userRolesBean.setRoleId( roleBean.getRoleId() );

                Integer iNumOfRows = buildUserRolesData.insertUserRoles(  userRolesBean );
                if(iNumOfRows>0){
                    arrUserRolesBean.add( userRolesBean );
                }
            }
        }
        return arrUserRolesBean;
    }
}
