package com.events.data.users.permissions;

import com.events.bean.users.permissions.RolePermissionsBean;
import com.events.bean.users.permissions.RolesBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/29/14
 * Time: 7:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildRolePermissionsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    //GTROLEPERMISSIONS(  ROLEPERMISSIONID VARCHAR(45)  NOT NULL,  FK_ROLEID VARCHAR(45) NOT NULL,  FK_PERMISSIONID VARCHAR(45) NOT NULL

    public Integer insertRolePermission(RolePermissionsBean rolesPermissionsBean) {
        int numOfRowsInserted = 0;
        if(rolesPermissionsBean!=null && !Utility.isNullOrEmpty(rolesPermissionsBean.getRoleId())  && !Utility.isNullOrEmpty(rolesPermissionsBean.getPermissionId()) ) {
            String sQuery = "INSERT INTO GTROLEPERMISSIONS ( ROLEPERMISSIONID,FK_ROLEID,FK_PERMISSIONID)  VALUES ( ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(
                    rolesPermissionsBean.getRolePermissionId(), rolesPermissionsBean.getRoleId(),rolesPermissionsBean.getPermissionId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildRolePermissionsData.java", "insertRolePermission() ");
        }


        return numOfRowsInserted;
    }

    public Integer deleteRolePermissionByRoleId(RolesBean rolesBean) {
        int numOfRowsInserted = 0;
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId()) ) {
            String sQuery = "DELETE FROM GTROLEPERMISSIONS WHERE FK_ROLEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(  rolesBean.getRoleId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildRolePermissionsData.java", "deleteRolePermissionByRoleId() ");
        }


        return numOfRowsInserted;
    }

}
