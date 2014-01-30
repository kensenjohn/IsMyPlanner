package com.events.data.users.permissions;

import com.events.bean.users.permissions.PermissionGroupBean;
import com.events.bean.users.permissions.PermissionsBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/29/14
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessPermissionsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<PermissionsBean> getPermissions(UserRolePermissionRequestBean userRolePermRequest) {
        //GTPERMISSIONS(PERMISSIONID,PERMISSIONGROUPID, SHORT_NAME,DISPLAY_TEXT,      DESCRIPTION,FK_PARENTID
        ArrayList<PermissionsBean> arrPermissionsBean = new ArrayList<PermissionsBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getParentId())) {
            String sQuery = "SELECT * FROM GTPERMISSIONS WHERE FK_PARENTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRolePermRequest.getParentId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessPermissionsData.java", "getPermissions()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    PermissionsBean permissionsBean = new PermissionsBean( hmResult );
                    arrPermissionsBean.add(permissionsBean);
                }
            }
        }
        return arrPermissionsBean;
    }
    //GTPERMISSIONGROUP(  PERMISSIONGROUPID VARCHAR(45)  NOT NULL,  GROUP_NAME VARCHAR(100) NOT NULL,  FK_PARENTID
    public ArrayList<PermissionGroupBean> getPermissionGroups(UserRolePermissionRequestBean userRolePermRequest) {
        ArrayList<PermissionGroupBean> arrPermissionGroupBean = new ArrayList<PermissionGroupBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getParentId())) {
            String sQuery = "SELECT * FROM GTPERMISSIONGROUP WHERE FK_PARENTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRolePermRequest.getParentId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessPermissionsData.java", "getPermissionGroups()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    PermissionGroupBean permissionGroupBean = new PermissionGroupBean( hmResult );
                    arrPermissionGroupBean.add(permissionGroupBean);
                }
            }
        }
        return arrPermissionGroupBean;
    }
}
