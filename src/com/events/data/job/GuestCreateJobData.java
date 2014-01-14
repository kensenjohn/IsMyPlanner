package com.events.data.job;

import com.events.bean.job.GuestCreateJobBean;
import com.events.bean.job.GuestCreateJobRequestBean;
import com.events.bean.users.UserBean;
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
 * Date: 1/3/14
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestCreateJobData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    //  GUESTCREATEJOBID VARCHAR(45) NOT NULL, FK_UPLOADID  VARCHAR(45) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL,
    // JOBSTATUS   VARCHAR(100) NOT NULL,  CREATEDATE BIGINT(20) NOT NULL DEFAULT 0 , HUMANCREATEDATE VARCHAR(45)
    public Integer insertGuestCreationJob(GuestCreateJobRequestBean guestCreateJobRequestBean) {
        Integer iNumOfRows = 0;
        if(guestCreateJobRequestBean!=null && !Utility.isNullOrEmpty(guestCreateJobRequestBean.getGuestCreateJobId())) {
            String sQuery = "INSERT INTO GTGUESTCREATEJOB (GUESTCREATEJOBID,FK_UPLOADID,FK_EVENTID," +
                    "FK_USERID, JOBSTATUS, CREATEDATE,    HUMANCREATEDATE ) VALUES (?,?,?,   ?,?,?,    ?)";

            ArrayList<Object> aParams = DBDAO.createConstraint(
                    guestCreateJobRequestBean.getGuestCreateJobId(),guestCreateJobRequestBean.getUploadId(), guestCreateJobRequestBean.getEventId(),
                    guestCreateJobRequestBean.getUserId(), guestCreateJobRequestBean.getJobStatus().getStatus(), DateSupport.getEpochMillis(),
                    DateSupport.getUTCDateTime());
            appLogging.info(" Query : " + sQuery + " aPArams : " + aParams);
            iNumOfRows = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "GuestCreateJobData.java", "insertGuestCreationJob() ");
        }
        return iNumOfRows;
    }

    public Integer updateGuestCreationJob(GuestCreateJobRequestBean guestCreateJobRequestBean) {
        Integer iNumOfRows = 0;
        if(guestCreateJobRequestBean!=null && !Utility.isNullOrEmpty(guestCreateJobRequestBean.getGuestCreateJobId())) {
            String sQuery = "UPDATE GTGUESTCREATEJOB SET JOBSTATUS = ? WHERE GUESTCREATEJOBID = ?";

            ArrayList<Object> aParams = DBDAO.createConstraint( guestCreateJobRequestBean.getJobStatus().getStatus(),
                    guestCreateJobRequestBean.getGuestCreateJobId() );
            iNumOfRows = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "GuestCreateJobData.java", "updateGuestCreationJob() ");
        }
        return iNumOfRows;
    }

    public GuestCreateJobBean getGuestCreateJobBeanByEvent(GuestCreateJobRequestBean guestCreateJobRequestBean) {
        GuestCreateJobBean guestCreateJobBean = new GuestCreateJobBean();
        if(guestCreateJobRequestBean!=null ) {
            String sQuery = "SELECT * FROM GTGUESTCREATEJOB  WHERE FK_EVENTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( guestCreateJobRequestBean.getEventId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "GuestCreateJobData.java", "getGuestCreateJobBeanByEvent()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    guestCreateJobBean = new GuestCreateJobBean(hmResult);
                }
            }

        }
        return guestCreateJobBean;
    }
}
