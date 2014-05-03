package com.events.data.files;

import com.events.bean.common.files.*;
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
 * Date: 4/30/14
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildSharedFilesData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertSharedFileGroup(SharedFilesGroupBean sharedFilesGroupBean) {
        Integer numOfRowsInserted = 0;
        if(sharedFilesGroupBean!=null && !Utility.isNullOrEmpty(sharedFilesGroupBean.getSharedFilesGroupId())) {
            String sQuery  = "INSERT INTO GTSHAREDFILESGROUP(SHAREDFILESGROUPID,FILESGROUPNAME,FK_VENDORID,    FK_CLIENTID,FK_USERID  ) VALUES(?,?,?,   ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesGroupBean.getSharedFilesGroupId(),sharedFilesGroupBean.getFilesGroupName(), sharedFilesGroupBean.getVendorId(),
                    sharedFilesGroupBean.getClientId(),sharedFilesGroupBean.getUserId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildSharedFilesData.java", "insertSharedFileGroup()");
        }
        return numOfRowsInserted;
    }

    public Integer updateSharedFileGroup(SharedFilesGroupBean sharedFilesGroupBean) {
        Integer numOfRowsInserted = 0;
        if(sharedFilesGroupBean!=null && !Utility.isNullOrEmpty(sharedFilesGroupBean.getSharedFilesGroupId())) {
            String sQuery  = "UPDATE GTSHAREDFILESGROUP SET FILESGROUPNAME = ? WHERE SHAREDFILESGROUPID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesGroupBean.getFilesGroupName(), sharedFilesGroupBean.getSharedFilesGroupId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildSharedFilesData.java", "updateSharedFileGroup()");
        }
        return numOfRowsInserted;
    }

    public Integer insertSharedFiles(SharedFilesBean sharedFilesBean) {
        Integer numOfRowsInserted = 0;
        if(sharedFilesBean!=null && !Utility.isNullOrEmpty(sharedFilesBean.getSharedFilesId())) {
            // GTSHAREDFILES(  SHAREDFILESID VARCHAR(45) PRIMARY KEY NOT NULL,  FILENAME VARCHAR(250) NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
            //  FK_VENDORID VARCHAR(45) NOT NULL,
            // FK_USERID VARCHAR(45) NOT NULL,  CREATEDATE BIGINT(20) DEFAULT 0 NOT NULL,  HUMANCREATEDATE VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "INSERT INTO GTSHAREDFILES(SHAREDFILESID,FILENAME,FK_SHAREDFILESGROUPID,    FK_VENDORID, FK_USERID,CREATEDATE,  HUMANCREATEDATE ) VALUES(?,?,?,   ?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesBean.getSharedFilesId(),sharedFilesBean.getFileName(),sharedFilesBean.getSharedFilesGroupId(),
                    sharedFilesBean.getVendorId(),sharedFilesBean.getUserId(), DateSupport.getEpochMillis(),
                    DateSupport.getUTCDateTime() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildSharedFilesData.java", "insertSharedFiles()");
        }
        return numOfRowsInserted;
    }

    public Integer insertSharedFilesViewer(SharedFilesViewersBean sharedFilesViewersBean){
        Integer numOfRowsInserted = 0;
        if(sharedFilesViewersBean!=null && !Utility.isNullOrEmpty(sharedFilesViewersBean.getSharedFilesViewersId()) ) {
            // GTSHAREDFILESVIEWERS (  SHAREDFILESVIEWERSID VARCHAR(45) PRIMARY KEY NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
            // FK_USERID VARCHAR(45) NOT NULL,  VIEWER_TYPE VARCHAR(45) NOT NULL,  FK_PARENTID VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "INSERT INTO GTSHAREDFILESVIEWERS(SHAREDFILESVIEWERSID,FK_SHAREDFILESGROUPID,FK_USERID,    VIEWER_TYPE,FK_PARENTID ) VALUES(?,?,?,   ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesViewersBean.getSharedFilesViewersId(),sharedFilesViewersBean.getSharedFilesGroupId(),sharedFilesViewersBean.getUserId(),
                    sharedFilesViewersBean.getViewerType().getType(),sharedFilesViewersBean.getParentId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildSharedFilesData.java", "insertSharedFilesViewer()");
        }
        return numOfRowsInserted;
    }

    public Integer deleteSharedFiles(SharedFilesRequestBean sharedFilesRequestBean){
        Integer numOfRowsInserted = 0;
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFileId()) ) {
            // GTSHAREDFILESVIEWERS (  SHAREDFILESVIEWERSID VARCHAR(45) PRIMARY KEY NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
            // FK_USERID VARCHAR(45) NOT NULL,  VIEWER_TYPE VARCHAR(45) NOT NULL,  FK_PARENTID VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "DELETE FROM GTSHAREDFILES WHERE SHAREDFILESID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getSharedFileId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildSharedFilesData.java", "deleteSharedFiles()");
        }
        return numOfRowsInserted;
    }

    public Integer deleteSharedFilesViewer(SharedFilesRequestBean sharedFilesRequestBean){
        Integer numOfRowsInserted = 0;
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId()) ) {
            // GTSHAREDFILESVIEWERS (  SHAREDFILESVIEWERSID VARCHAR(45) PRIMARY KEY NOT NULL, FK_SHAREDFILESGROUPID VARCHAR(250) NOT NULL,
            // FK_USERID VARCHAR(45) NOT NULL,  VIEWER_TYPE VARCHAR(45) NOT NULL,  FK_PARENTID VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "DELETE FROM GTSHAREDFILESVIEWERS WHERE FK_SHAREDFILESGROUPID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesRequestBean.getSharedFilesGroupId() );
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildSharedFilesData.java", "deleteSharedFilesViewer()");
        }
        return numOfRowsInserted;
    }

    public Integer insertSharedFilesComments(SharedFilesCommentsBean sharedFilesCommentsBean){
        Integer numOfRowsInserted = 0;
        if(sharedFilesCommentsBean!=null && !Utility.isNullOrEmpty(sharedFilesCommentsBean.getSharedFilesCommentsId())) {
            // GTSHAREDFILESCOMMENTS(  SHAREDFILESCOMMENTSID VARCHAR(45) PRIMARY KEY NOT NULL,  COMMENT TEXT NOT NULL,
            // FROM_FK_USERID VARCHAR(45) NOT NULL,  FK_SHAREDFILESGROUPID VARCHAR(45) NOT NULL,
            // CREATEDATE BIGINT(20) DEFAULT 0 NOT NULL,  HUMANCREATEDATE VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;
            String sQuery  = "INSERT INTO GTSHAREDFILESCOMMENTS(SHAREDFILESCOMMENTSID,COMMENT,FROM_FK_USERID,    FK_SHAREDFILESGROUPID,CREATEDATE,HUMANCREATEDATE ) VALUES(?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(sharedFilesCommentsBean.getSharedFilesCommentsId(),sharedFilesCommentsBean.getComment(),sharedFilesCommentsBean.getFromUserId(),
                    sharedFilesCommentsBean.getSharedFilesGroupId(),DateSupport.getEpochMillis(), DateSupport.getUTCDateTime());
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB,  "BuildSharedFilesData.java", "deleteSharedFilesViewer()");
        }
        return numOfRowsInserted;
    }
    //public void

}
