package com.events.data.users;

import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import com.events.common.nosql.redis.RedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildUserData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertUser(UserBean userBean) {
        String sQuery = "INSERT INTO GTUSER ( USERID,USERTYPE,FK_PARENTID,  FK_USERINFOID,CREATEDATE,HUMANCREATEDATE)"
                + " VALUES ( ?,?,?, ?,?,?)";
        ArrayList<Object> aParams = DBDAO.createConstraint(
                userBean.getUserId(),userBean.getUserType().getType(),userBean.getParentId(),
                userBean.getUserInfoId(), DateSupport.getEpochMillis(),DateSupport.getUTCDateTime());

        int numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildUserData.java", "insertUser() ");

        return numOfRowsInserted;
    }
    public Integer deleteUser(UserBean userBean) {
        int numOfRowsInserted = 0;
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId() )){
            String sQuery = "DELETE FROM GTUSER WHERE USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( userBean.getUserId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildUserData.java", "deleteUser() ");

        }

        return numOfRowsInserted;
    }
    public Integer insertUserInfo(UserInfoBean userInfoBean) {
        String sQuery = "INSERT INTO GTUSERINFO (USERINFOID,FIRST_NAME,LAST_NAME,   ADDRESS_1,ADDRESS_2,CITY,  STATE,COUNTRY,IP_ADDRESS,  " +
                "CELL_PHONE,PHONE_NUM,EMAIL,    DEL_ROW,CREATEDATE,HUMANCREATEDATE,   COMPANY,ZIPCODE )"
                + " VALUES ( ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,   ?,?)";

        ArrayList<Object> aParams = DBDAO.createConstraint( userInfoBean.getUserInfoId(), userInfoBean.getFirstName(), userInfoBean.getLastName(),
                userInfoBean.getAddress1(), userInfoBean.getAddress2(), userInfoBean.getCity(), userInfoBean.getState(), userInfoBean.getCountry(),
                userInfoBean.getIpAddress(), userInfoBean.getCellPhone(), userInfoBean.getPhoneNum(), userInfoBean.getEmail(),
                userInfoBean.getDeleteRow(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), userInfoBean.getCompany(),userInfoBean.getZipcode());
        int numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildUserData.java", "insertUserInfo() ");

        return numOfRowsInserted;
    }

    public Integer updateUser(UserBean userBean) {
        String sQuery = "UPDATE GTUSER SET USERID = ?,USERTYPE = ?,FK_PARENTID = ?,  FK_USERINFOID = ?,MODIFIEDDATE = ?,  DEL_ROW = ?,HUMANMODIFIEDDATE = ?"
                + " WHERE  USERID = ?";
        ArrayList<Object> aParams = DBDAO.createConstraint(
                userBean.getUserId(),userBean.getUserType().getType(),userBean.getParentId(),
                userBean.getUserInfoId(), DateSupport.getEpochMillis() ,
                userBean.getDeleteRow(), DateSupport.getUTCDateTime(),userBean.getUserId());

        int numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildUserData.java", "updateUser() ");

        return numOfRowsInserted;
    }

    public Integer updateUserInfo(UserInfoBean userInfoBean) {
        String sQuery = "UPDATE GTUSERINFO SET USERINFOID = ?,FIRST_NAME = ?,LAST_NAME = ?,   ADDRESS_1 = ?,ADDRESS_2 = ?,CITY = ?,  " +
                "STATE = ? ,COUNTRY = ?,IP_ADDRESS = ?,  " +
                "CELL_PHONE = ?,PHONE_NUM=?,EMAIL = ?,   DEL_ROW = ?,MODIFIEDDATE = ?,    HUMANMODIFIEDDATE = ?,COMPANY = ?,ZIPCODE = ? "
                + "  WHERE USERINFOID = ?";

        ArrayList<Object> aParams = DBDAO.createConstraint( userInfoBean.getUserInfoId(), userInfoBean.getFirstName(), userInfoBean.getLastName(),
                userInfoBean.getAddress1(), userInfoBean.getAddress2(), userInfoBean.getCity(), userInfoBean.getState(), userInfoBean.getCountry(),
                userInfoBean.getIpAddress(), userInfoBean.getCellPhone(), userInfoBean.getPhoneNum(), userInfoBean.getEmail(),
                userInfoBean.getDeleteRow(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), userInfoBean.getCompany(),userInfoBean.getZipcode(),
                userInfoBean.getUserInfoId());
        int numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildUserData.java", "updateUserInfo() ");
        return numOfRowsInserted;
    }
}
