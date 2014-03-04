package com.events.data;

import com.events.bean.common.ParentSiteEnabledBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserRequestBean;
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
 * Date: 3/2/14
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentSiteEnabledData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);


    //GTPARENTSITEENABLED(PARENTSITEENABLEDID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL, IS_ALLOWED_ACCESS  INT(1) NOT NULL DEFAULT 0,
    public ParentSiteEnabledBean getParentSiteEnabledStatusByUser(UserRequestBean userRequestBean) {
        ParentSiteEnabledBean parentSiteEnabledBean = new ParentSiteEnabledBean();
        if(userRequestBean!=null) {
            String sQuery  = "SELECT * FROM GTPARENTSITEENABLED WHERE FK_USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(userRequestBean.getUserId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "ParentSiteEnabledData.java", "getParentSiteEnabledStatusByUser()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    parentSiteEnabledBean = new ParentSiteEnabledBean(hmResult);
                }
            }
        }
        return parentSiteEnabledBean;
    }

    public Integer insertParentSiteEnabledStatus(ParentSiteEnabledBean parentSiteEnabledBean){
        int numOfRowsInserted = 0 ;
        if(parentSiteEnabledBean!=null && !Utility.isNullOrEmpty(parentSiteEnabledBean.getParentSiteEnabledId())){
            String sQuery  = "INSERT INTO GTPARENTSITEENABLED (PARENTSITEENABLEDID,FK_USERID,IS_ALLOWED_ACCESS ) VALUES (?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(parentSiteEnabledBean.getParentSiteEnabledId(), parentSiteEnabledBean.getUserId(), parentSiteEnabledBean.isAllowed()?"1":"0");

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "ParentSiteEnabledData.java", "insertParentSiteEnabledStatus() ");

        }

        return numOfRowsInserted;
    }

    public Integer updateParentSiteEnabledStatus(ParentSiteEnabledBean parentSiteEnabledBean){
        int numOfRowsInserted = 0 ;
        if(parentSiteEnabledBean!=null && !Utility.isNullOrEmpty(parentSiteEnabledBean.getParentSiteEnabledId())){
            String sQuery  = "UPDATE GTPARENTSITEENABLED SET IS_ALLOWED_ACCESS = ? WHERE  PARENTSITEENABLEDID = ?  AND FK_USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( parentSiteEnabledBean.isAllowed()?"1":"0", parentSiteEnabledBean.getParentSiteEnabledId(), parentSiteEnabledBean.getUserId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "ParentSiteEnabledData.java", "insertParentSiteEnabledStatus() ");

        }
        return numOfRowsInserted;
    }
}
