package com.events.data.users.permissions;

import com.events.bean.users.UserBean;
import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolesBean;
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
 * User: root
 * Date: 1/29/14
 * Time: 11:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessUserRolesData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTUSERROLES(  USERROLEID VARCHAR(45) NOT NULL,  FK_ROLEID VARCHAR(45) NOT NULL,  FK_USERID VARCHAR(45)
    public ArrayList<UserRolesBean> getUserRoles( RolesBean rolesBean  ) {
        ArrayList<UserRolesBean> arrUserRolesBean = new ArrayList<UserRolesBean>();
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId() )) {
            String sQuery = "SELECT * FROM GTUSERROLES WHERE FK_ROLEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( rolesBean.getRoleId() );
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserRolesData.java", "getUserRoles()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    UserRolesBean userRolesBean = new UserRolesBean( hmResult );
                    arrUserRolesBean.add(userRolesBean);
                }
            }
        }
        return arrUserRolesBean;
    }

    public ArrayList<UserRolesBean> getUserRolesByUser( UserBean userBean  ) {
        ArrayList<UserRolesBean> arrUserRolesBean = new ArrayList<UserRolesBean>();
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId() )) {
            String sQuery = "SELECT * FROM GTUSERROLES WHERE FK_USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( userBean.getUserId() );
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserRolesData.java", "getUserRolesByUser()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    UserRolesBean userRolesBean = new UserRolesBean( hmResult );
                    arrUserRolesBean.add(userRolesBean);
                }
            }
        }
        return arrUserRolesBean;
    }
}
