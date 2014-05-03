package com.events.data.files;

import com.events.bean.common.files.SharedFilesBean;
import com.events.bean.common.files.SharedFilesGroupBean;
import com.events.bean.common.files.SharedFilesRequestBean;
import com.events.bean.common.files.SharedFilesViewersBean;
import com.events.bean.event.EventBean;
import com.events.bean.event.EventRequestBean;
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
 * Date: 4/30/14
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessSharedFilesData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<SharedFilesGroupBean>  getVendorSharedFileGroup(SharedFilesRequestBean sharedFilesRequestBean) {
        ArrayList<SharedFilesGroupBean>  arrSharedFilesGroupBean = new ArrayList<SharedFilesGroupBean>();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getVendorId())) {
            //GTSHAREDFILESGROUP(  SHAREDFILESGROUPID VARCHAR(45) PRIMARY KEY NOT NULL,  FILESGROUPNAME VARCHAR(250) NOT NULL,
            // FK_VENDORID VARCHAR(45) NOT NULL , FK_CLIENTID VARCHAR(45) NOT NULL , FK_USERID VARCHAR(45) NOT NULL ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "SELECT * FROM GTSHAREDFILESGROUP WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getVendorId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSharedFilesData.java", "getVendorSharedFileGroup()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    SharedFilesGroupBean sharedFilesGroupBean = new SharedFilesGroupBean(hmResult);
                    arrSharedFilesGroupBean.add( sharedFilesGroupBean );
                }
            }
        }
        return arrSharedFilesGroupBean;
    }

    public ArrayList<SharedFilesGroupBean> getClientSharedFileGroup(SharedFilesRequestBean sharedFilesRequestBean) {
        ArrayList<SharedFilesGroupBean>  arrSharedFilesGroupBean = new ArrayList<SharedFilesGroupBean>();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getClientId())) {
            //GTSHAREDFILESGROUP(  SHAREDFILESGROUPID VARCHAR(45) PRIMARY KEY NOT NULL,  FILESGROUPNAME VARCHAR(250) NOT NULL,
            // FK_VENDORID VARCHAR(45) NOT NULL , FK_CLIENTID VARCHAR(45) NOT NULL , FK_USERID VARCHAR(45) NOT NULL ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "SELECT FG.* FROM GTSHAREDFILESGROUP FG , GTSHAREDFILESVIEWERS FV WHERE FV.FK_SHAREDFILESGROUPID = FG.SHAREDFILESGROUPID AND FV.FK_PARENTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getClientId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSharedFilesData.java", "getClientSharedFileGroup()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    SharedFilesGroupBean sharedFilesGroupBean = new SharedFilesGroupBean(hmResult);
                    arrSharedFilesGroupBean.add(sharedFilesGroupBean);
                }
            }
        }
        return arrSharedFilesGroupBean;
    }

    public SharedFilesGroupBean getSharedFileGroup(SharedFilesRequestBean sharedFilesRequestBean) {
        SharedFilesGroupBean sharedFilesGroupBean = new SharedFilesGroupBean();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId())) {
            //GTSHAREDFILESGROUP(  SHAREDFILESGROUPID VARCHAR(45) PRIMARY KEY NOT NULL,  FILESGROUPNAME VARCHAR(250) NOT NULL,
            // FK_VENDORID VARCHAR(45) NOT NULL , FK_CLIENTID VARCHAR(45) NOT NULL , FK_USERID VARCHAR(45) NOT NULL ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "SELECT * FROM GTSHAREDFILESGROUP WHERE SHAREDFILESGROUPID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getSharedFilesGroupId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSharedFilesData.java", "getSharedFileGroup()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    sharedFilesGroupBean = new SharedFilesGroupBean(hmResult);
                }
            }
        }
        return sharedFilesGroupBean;
    }

    public ArrayList<SharedFilesBean> getSharedFiles(SharedFilesRequestBean sharedFilesRequestBean) {
        ArrayList<SharedFilesBean> arrSharedFilesBean = new ArrayList<SharedFilesBean>();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId())) {
            // GTSHAREDFILES(  SHAREDFILESID VARCHAR(45) PRIMARY KEY NOT NULL,  FILENAME VARCHAR(250) NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
            // FK_VENDORID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL,  CREATEDATE BIGINT(20) DEFAULT 0 NOT NULL,  HUMANCREATEDATE VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "SELECT * FROM GTSHAREDFILES WHERE FK_SHAREDFILESGROUPID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getSharedFilesGroupId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSharedFilesData.java", "getSharedFiles()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    SharedFilesBean sharedFilesBean = new SharedFilesBean(hmResult);
                    arrSharedFilesBean.add( sharedFilesBean );
                }
            }
        }
        return arrSharedFilesBean;
    }

    public SharedFilesBean getSharedFilesFromUploadId(SharedFilesRequestBean sharedFilesRequestBean) {
        SharedFilesBean sharedFilesBean = new SharedFilesBean();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getUploadId() )) {
            // GTSHAREDFILES(  SHAREDFILESID VARCHAR(45) PRIMARY KEY NOT NULL,  FILENAME VARCHAR(250) NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
            // FK_VENDORID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL,  CREATEDATE BIGINT(20) DEFAULT 0 NOT NULL,  HUMANCREATEDATE VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "SELECT * FROM GTSHAREDFILES WHERE FILENAME = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getUploadId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSharedFilesData.java", "getSharedFilesFromUploadId()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    sharedFilesBean = new SharedFilesBean(hmResult);
                }
            }
        }
        return sharedFilesBean;
    }

    public ArrayList<SharedFilesViewersBean> getSharedFilesViewer(SharedFilesRequestBean sharedFilesRequestBean) {
        ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean = new ArrayList<SharedFilesViewersBean>();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId())) {
            // GTSHAREDFILES(  SHAREDFILESID VARCHAR(45) PRIMARY KEY NOT NULL,  FILENAME VARCHAR(250) NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
            // FK_VENDORID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL,  CREATEDATE BIGINT(20) DEFAULT 0 NOT NULL,  HUMANCREATEDATE VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "SELECT * FROM GTSHAREDFILESVIEWERS WHERE FK_SHAREDFILESGROUPID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getSharedFilesGroupId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSharedFilesData.java", "getSharedFilesViewer()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    SharedFilesViewersBean sharedFilesViewersBean = new SharedFilesViewersBean(hmResult);
                    arrSharedFilesViewersBean.add( sharedFilesViewersBean );
                }
            }
        }
        return arrSharedFilesViewersBean;
    }
}
