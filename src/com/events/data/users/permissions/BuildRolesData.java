package com.events.data.users.permissions;

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
 * User: kensen
 * Date: 1/29/14
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildRolesData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertRole(RolesBean rolesBean) {
        //GTROLES( ROLEID, FK_PARENTID, NAME,      CREATEDATE, HUMANCREATEDATE, IS_SITEADMIN)
        int numOfRowsInserted = 0;
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId())  && !Utility.isNullOrEmpty(rolesBean.getParentId()) ) {
            String sQuery = "INSERT INTO GTROLES ( ROLEID,FK_PARENTID,NAME,  CREATEDATE,HUMANCREATEDATE,IS_SITEADMIN)"
                    + " VALUES ( ?,?,?, ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(
                    rolesBean.getRoleId(), rolesBean.getParentId(),
                    rolesBean.getName(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),rolesBean.isSiteAdmin()?"1":"0");

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildRolesData.java", "insertRole() ");
        }


        return numOfRowsInserted;
    }
    public Integer deletetRole(RolesBean rolesBean) {
        //GTROLES( ROLEID, FK_PARENTID, NAME,      CREATEDATE, HUMANCREATEDATE, IS_SITEADMIN)
        int numOfRowsDeleted = 0;
        if(rolesBean!=null && !Utility.isNullOrEmpty(rolesBean.getRoleId())  && !Utility.isNullOrEmpty(rolesBean.getParentId()) ) {
            String sQuery = "DELETE FROM GTROLES WHERE ROLEID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( rolesBean.getRoleId() );

            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildRolesData.java", "deletetRole() ");
        }
        return numOfRowsDeleted;
    }
}
