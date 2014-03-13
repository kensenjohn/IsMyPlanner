package com.events.data.users.permissions;

import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.common.*;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/30/14
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckPermissionData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer getUserPermission( String sUserId , Perm perm ) {
        Integer userPermissionCount = 0;
        if( !Utility.isNullOrEmpty(sUserId) && perm!=null ) {
            String sQuery = "SELECT COUNT(*) AS PERMISSION_COUNT FROM GTUSERROLES UR,GTROLEPERMISSIONS RP,GTPERMISSIONS P " +
                    " WHERE UR.FK_USERID = ?  AND P.SHORT_NAME = ? AND UR.FK_ROLEID = RP.FK_ROLEID AND " +
                    " RP.FK_PERMISSIONID = P.PERMISSIONID";

            ArrayList<Object> aParams = DBDAO.createConstraint( sUserId , perm.toString() );
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "CheckPermissionData.java", "getUserPermission()");
            if( arrResult!=null && !arrResult.isEmpty() ){
                for(HashMap<String, String> hmResult : arrResult) {
                    userPermissionCount = ParseUtil.sToI(hmResult.get("PERMISSION_COUNT"));
                }

            }
        }
        return userPermissionCount;
    }

}
