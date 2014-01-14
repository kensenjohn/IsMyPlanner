package com.events.data.users;

import com.events.bean.users.ForgotPasswordBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/9/14
 * Time: 7:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class ForgotPasswordData {
    private String sourceFile = "ForgotPasswordData.java";
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer deactivateForgotPassword(ForgotPasswordBean forgotPasswordBean)
    {
        Integer iNumOfRecs = 0;
        if(forgotPasswordBean!=null && !Utility.isNullOrEmpty(forgotPasswordBean.getUserId()))
        {
            iNumOfRecs = deactivateForgotPassword(forgotPasswordBean.getUserId());
        }
        return iNumOfRecs;
    }

    public Integer  deactivateForgotPassword(String sUserId) {
        appLogging.info("Deactivating security forgotinfo record : " + sUserId );
        Integer iNumOfRecs = 0;
        if( !Utility.isNullOrEmpty(sUserId) )
        {
            String sQuery = "UPDATE GTFORGOTPASSWORD SET IS_USABLE = ? WHERE FK_USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint("0", sUserId);

            iNumOfRecs = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, sourceFile, "deactivateForgotPassword()");
        }
        return iNumOfRecs;
    }

    public Integer createForgotPassword(ForgotPasswordBean forgotPasswordBean) {
        Integer iNumOfRows = 0 ;
        if(forgotPasswordBean!=null && !Utility.isNullOrEmpty(forgotPasswordBean.getForgotPasswordId())) {
            //
            String sQuery = "INSERT INTO GTFORGOTPASSWORD (FORGOTPASSWORDID,FK_USERID,SECURE_TOKEN_ID,     CREATEDATE,HUMANCREATEDATE,IS_USABLE) " +
                    "  VALUES (?,?,?,    ?,?,?)";

            ArrayList<Object> aParams = DBDAO.createConstraint(forgotPasswordBean.getForgotPasswordId(),forgotPasswordBean.getUserId(),forgotPasswordBean.getSecureTokenId(),
                    DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),forgotPasswordBean.isUsable());

            iNumOfRows = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, sourceFile, "createForgotPassword()");
        }
        return iNumOfRows;
    }

    public ForgotPasswordBean getForgotPassswordBean(String sSecureTokenId, String sUserId) {
        ForgotPasswordBean forgotPasswordBean = new ForgotPasswordBean();
        if(!Utility.isNullOrEmpty(sSecureTokenId) && !Utility.isNullOrEmpty(sUserId)) {
            String sQuery = "SELECT * FROM GTFORGOTPASSWORD WHERE SECURE_TOKEN_ID = ? AND FK_USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(sSecureTokenId, sUserId);

            ArrayList<HashMap<String,String>> aResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false,sourceFile, "getForgotPassswordBean()");
            if(aResult!=null && !aResult.isEmpty()) {
                for(HashMap<String,String> hmResult :aResult ) {
                    forgotPasswordBean = new ForgotPasswordBean( hmResult );
                }
            }
        }
        return forgotPasswordBean;
    }
}
