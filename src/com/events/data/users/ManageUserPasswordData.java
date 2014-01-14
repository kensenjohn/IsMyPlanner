package com.events.data.users;

import com.events.bean.users.PasswordBean;
import com.events.bean.users.PasswordRequestBean;
import com.events.bean.users.UserBean;
import com.events.common.*;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageUserPasswordData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertPassword(PasswordRequestBean passwordRequestBean) {
        int numOfRowsInserted = 0;
        if( passwordRequestBean!=null  && !Utility.isNullOrEmpty(passwordRequestBean.getPassword()) && !Utility.isNullOrEmpty(passwordRequestBean.getUserId()) &&
                passwordRequestBean.getPasswordStatus()!=null  ) {
            String sQuery = "INSERT INTO GTPASSWORD ( PASSWORDID,PASSWORD,FK_USERID,   CREATEDATE,HUMANCREATEDATE,PASSWORD_STATUS) VALUES(?,?,?,  ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(passwordRequestBean.getPasswordId(),passwordRequestBean.getHashedPassword(),passwordRequestBean.getUserId(),
                    DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),passwordRequestBean.getPasswordStatus().getStatus());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,"ManageUserPasswordData.java", "insertPassword() ");

        } else {
            appLogging.error("Invalid DB request to create password " + ParseUtil.checkNullObject(passwordRequestBean));
        }
        return numOfRowsInserted;
    }

    public Integer updatePassword(PasswordRequestBean passwordRequestBean) {
        int numOfRowsInserted = 0;
        if( passwordRequestBean!=null  && !Utility.isNullOrEmpty(passwordRequestBean.getPassword()) && !Utility.isNullOrEmpty(passwordRequestBean.getUserId()) &&
                passwordRequestBean.getPasswordStatus()!=null  ) {
            String sQuery = "UPDATE GTPASSWORD SET PASSWORD = ?,PASSWORD_STATUS=?,CREATEDATE =?,    HUMANCREATEDATE=? WHERE FK_USERID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(passwordRequestBean.getHashedPassword(), passwordRequestBean.getPasswordStatus().getStatus(),
                    DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(), passwordRequestBean.getUserId());
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,"ManageUserPasswordData.java", "updatePassword() ");
        } else {
            appLogging.error("Invalid  request to update password " + ParseUtil.checkNullObject(passwordRequestBean));
        }
        return numOfRowsInserted;
    }

    public PasswordBean getPassword(PasswordRequestBean passwordRequestBean) {
        PasswordBean passwordBean = new PasswordBean();
        appLogging.error("PasswordRequestBean ");

        if(passwordRequestBean!=null ) {
            String sQuery = "SELECT * FROM GTPASSWORD WHERE FK_USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(passwordRequestBean.getUserId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "ManageUserPasswordData.java", "getPassword()");

            appLogging.error("sQuery :  " + sQuery + " aParams : " + aParams + " arrResult : " + arrResult);
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    passwordBean = new PasswordBean(hmResult);
                }
            }
        }
        return passwordBean;
    }
}
