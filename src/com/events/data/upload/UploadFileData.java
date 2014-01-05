package com.events.data.upload;

import com.events.bean.event.EventBean;
import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.common.*;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class UploadFileData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertUpload(UploadRequestBean uploadRequestBean){
        Integer numOfRowsInserted = 0;
        if(uploadRequestBean!=null && !Utility.isNullOrEmpty(uploadRequestBean.getUploadId())) {
            //UPLOADID   VARCHAR(45) NOT NULL, FILENAME TEXT NOT NULL, PATH TEXT NOT NULL,CREATEDATE BIGINT(20) NOT NULL DEFAULT 0,HUMANCREATEDATE VARCHAR(45) NOT NULL
            String sQuery = "INSERT INTO GTUPLOADS (UPLOADID,FILENAME,PATH,   CREATEDATE,HUMANCREATEDATE) VALUES (?,?,?,    ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(uploadRequestBean.getUploadId(), ParseUtil.checkNull(uploadRequestBean.getFilename())
                    , ParseUtil.checkNull(uploadRequestBean.getPath()), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "UploadFileData.java", "insertUpload() ");
        }
        return numOfRowsInserted;
    }

    public UploadBean getUploadBean(UploadRequestBean uploadRequestBean) {
        UploadBean uploadBean = new UploadBean();
        if(uploadRequestBean!=null && !Utility.isNullOrEmpty(uploadRequestBean.getUploadId())) {
            String sQuery = "SELECT * FROM GTUPLOADS WHERE UPLOADID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(uploadRequestBean.getUploadId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventData.java", "getEvent()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    uploadBean = new UploadBean(hmResult);
                }
            }

        }
        return uploadBean;
    }
}
