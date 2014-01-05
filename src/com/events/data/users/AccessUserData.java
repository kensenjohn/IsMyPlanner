package com.events.data.users;

import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessUserData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public UserBean getUserDataByEmail(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null) {
            String sQuery  = "SELECT U.* FROM GTUSER U, GTUSERINFO UI WHERE UI.EMAIL = ? AND U.FK_USERINFOID = UI.USERINFOID";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRequestBean.getEmail());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserData.java", "getUserDataByEmail()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    userBean = new UserBean(hmResult);
                }
            }
        }
        return userBean;
    }

    public UserBean getUserDataByID(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null) {
            String sQuery  = "SELECT * FROM GTUSER WHERE USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRequestBean.getUserId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserData.java", "getUserDataByID()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    userBean = new UserBean(hmResult);
                }
            }
        }
        return userBean;
    }


    public UserBean getUserDataByParentID(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null) {
            String sQuery  = "SELECT * FROM GTUSER WHERE FK_PARENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRequestBean.getParentId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserData.java", "getUserDataByParentID()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    userBean = new UserBean(hmResult);
                }
            }
        }
        return userBean;
    }

    public UserInfoBean getUserInfoFromInfoId(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = new UserInfoBean();
        if(userRequestBean!=null) {
            String sQuery  = "SELECT * FROM GTUSERINFO WHERE USERINFOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRequestBean.getUserInfoId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserData.java", "getUserInfoFromInfoId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    userInfoBean = new UserInfoBean(hmResult);
                }
            }

        }
        return userInfoBean;
    }

    public UserInfoBean getUserInfoFromUserId(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = new UserInfoBean();
        if(userRequestBean!=null) {
            String sQuery  = "SELECT GTUINFO.* FROM GTUSER GTU, GTUSERINFO GTUINFO WHERE GTUINFO.USERINFOID = GTU.FK_USERINFOID AND USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRequestBean.getUserId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserData.java", "getUserInfoFromUserId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    userInfoBean = new UserInfoBean(hmResult);
                }
            }

        }
        return userInfoBean;
    }
}
