package com.events.data.users;

import com.events.bean.users.CookieRequestBean;
import com.events.bean.users.CookieUserBean;
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
 * Date: 1/8/14
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CookieUserData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public CookieUserBean getCookieUser( CookieRequestBean cookieRequestBean ) {
        CookieUserBean cookieUserBean = new CookieUserBean();

        if(cookieRequestBean!=null && !Utility.isNullOrEmpty(cookieRequestBean.getCookieUserId())) {
            String sQuery = "SELECT * FROM GTCOOKIEUSER WHERE COOKIEUSERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(cookieRequestBean.getCookieUserId());

            ArrayList<HashMap<String,String>> aResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false,"BuildUserCookieData.java", "getCookieUser()");
            if(aResult!=null && !aResult.isEmpty()) {
                for(HashMap<String,String> hmResult :aResult ) {
                    cookieUserBean = new CookieUserBean( hmResult );
                }
            }
        }
        return cookieUserBean;
    }

    public Integer insertCookieUser( CookieRequestBean cookieRequestBean ) {
        Integer iNumOfRows = 0;
        if(cookieRequestBean!=null && !Utility.isNullOrEmpty(cookieRequestBean.getCookieUserId())) {
            String sQuery = "INSERT INTO GTCOOKIEUSER (COOKIEUSERID,FK_USERID,CREATEDATE,    HUMANCREATEDATE) VALUES(?,?,?,    ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(cookieRequestBean.getCookieUserId(),cookieRequestBean.getUserId(), DateSupport.getEpochMillis(),
                    DateSupport.getUTCDateTime());
            iNumOfRows = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "CookieUserData.java", "insertCookieUser() ");
        }
        return iNumOfRows;
    }
}
