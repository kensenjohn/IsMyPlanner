package com.events.data.users.permissions;

import com.events.bean.users.permissions.RolePermissionsBean;
import com.events.bean.users.permissions.UserRolesBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/29/14
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildUserRolesData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertUserRoles(UserRolesBean userRolesBean) {
        //GTROLES( USERROLEID VARCHAR(45) NOT NULL,  FK_ROLEID VARCHAR(45) NOT NULL,  FK_USERID VARCHAR(45) NOT NULL
        int numOfRowsInserted = 0;
        if(userRolesBean!=null && !Utility.isNullOrEmpty(userRolesBean.getRoleId())  && !Utility.isNullOrEmpty(userRolesBean.getUserId()) ) {
            String sQuery = "INSERT INTO GTUSERROLES ( USERROLEID,FK_ROLEID,FK_USERID)  VALUES ( ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(
                    userRolesBean.getUserRoleId(), userRolesBean.getRoleId(), userRolesBean.getUserId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildUserRolesData.java", "insertUserRoles() ");
        }


        return numOfRowsInserted;
    }
}
