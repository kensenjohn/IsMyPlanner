package com.events.data.users.permissions;

import com.events.bean.users.permissions.RolePermissionsBean;
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
 * Date: 1/30/14
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessRolePermissionsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<RolePermissionsBean> getRolePermissions(UserRolePermissionRequestBean userRolePermRequest) {
        //GTROLEPERMISSIONS(  ROLEPERMISSIONID VARCHAR(45)  NOT NULL,  FK_ROLEID VARCHAR(45) NOT NULL,  FK_PERMISSIONID VARCHAR(45)
        ArrayList<RolePermissionsBean> arrRolePermissionsBean = new ArrayList<RolePermissionsBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getRoleId())) {
            String sQuery = "SELECT * FROM GTROLEPERMISSIONS WHERE FK_ROLEID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRolePermRequest.getRoleId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessRolePermissionsData.java", "getRolePermissions()");
            if(arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    RolePermissionsBean permissionsBean = new RolePermissionsBean( hmResult );
                    arrRolePermissionsBean.add(permissionsBean);
                }
            }
        }
        return arrRolePermissionsBean;
    }
}
