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
            String sQuery = "INSERT INTO GTUPLOADS (UPLOADID,FILENAME,PATH,   CREATEDATE,HUMANCREATEDATE,ORIGINAL_FILENAME) VALUES (?,?,?,    ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(uploadRequestBean.getUploadId(), ParseUtil.checkNull(uploadRequestBean.getFilename())
                    , ParseUtil.checkNull(uploadRequestBean.getPath()), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), ParseUtil.checkNull(uploadRequestBean.getOriginalFileName()) );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "UploadFileData.java", "insertUpload() ");
        }
        return numOfRowsInserted;
    }

    public UploadBean getUploadBean(UploadRequestBean uploadRequestBean) {
        UploadBean uploadBean = new UploadBean();
        if(uploadRequestBean!=null && !Utility.isNullOrEmpty(uploadRequestBean.getUploadId())) {
            String sQuery = "SELECT * FROM GTUPLOADS WHERE UPLOADID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(uploadRequestBean.getUploadId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "UploadFileData.java", "getUploadBean()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    uploadBean = new UploadBean(hmResult);
                }
            }

        }
        return uploadBean;
    }

    public ArrayList<UploadBean> getUploadBeanList(UploadRequestBean uploadRequestBean) {
        ArrayList<UploadBean> arrUploadBean = new ArrayList<UploadBean>();
        if(uploadRequestBean!=null && uploadRequestBean.getArrUploadId()!=null && !uploadRequestBean.getArrUploadId().isEmpty() ) {
            ArrayList<String> arrUploadId = uploadRequestBean.getArrUploadId();
            String sQuery = "SELECT * FROM GTUPLOADS WHERE UPLOADID in("+ DBDAO.createParamQuestionMarks(arrUploadId.size())+")";

            ArrayList<Object> aParams = new ArrayList<Object>();
            for(String uploadId : arrUploadId ){
                aParams.add( uploadId  );
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "UploadFileData.java", "getUploadBean()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    UploadBean uploadBean = new UploadBean(hmResult);
                    arrUploadBean.add( uploadBean );
                }
            }
        }
        return arrUploadBean;
    }
}
