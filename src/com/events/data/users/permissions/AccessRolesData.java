package com.events.data.users.permissions;

import com.events.bean.users.permissions.RolesBean;
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
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessRolesData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);


    private ArrayList<RolesBean> getRoles(String sQuery, ArrayList<Object> aParams ) {
        ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
        if(!Utility.isNullOrEmpty(sQuery) && aParams!=null && !aParams.isEmpty()) {
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessRolesData.java", "getRoles()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    RolesBean rolesBean = new RolesBean( hmResult );
                    arrRolesBean.add(rolesBean);
                }
            }
        }
        return arrRolesBean;
    }
    public ArrayList<RolesBean> getRolesByParent(UserRolePermissionRequestBean userRolePermRequest) {
        ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getParentId())) {
            String sQuery = "SELECT * FROM GTROLES WHERE FK_PARENTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( userRolePermRequest.getParentId() );

            arrRolesBean = getRoles(sQuery , aParams );
        }
        return arrRolesBean;
    }

    public ArrayList<RolesBean> getSiteAdminRoleByParent( UserRolePermissionRequestBean userRolePermRequest ) {
        ArrayList<RolesBean> arrRolesBean = new ArrayList<RolesBean>();
        if(userRolePermRequest!=null && !Utility.isNullOrEmpty(userRolePermRequest.getParentId())) {
            String sQuery = "SELECT * FROM GTROLES WHERE FK_PARENTID = ? AND IS_SITEADMIN = 1";

            ArrayList<Object> aParams = DBDAO.createConstraint( userRolePermRequest.getParentId() );

            arrRolesBean = getRoles(sQuery , aParams );
        }
        return arrRolesBean;
    }
}
