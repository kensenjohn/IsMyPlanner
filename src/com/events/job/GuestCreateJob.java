package com.events.job;

import com.events.bean.job.GuestCreateJobBean;
import com.events.bean.job.GuestCreateJobRequestBean;
import com.events.bean.job.GuestCreateJobResponseBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.job.GuestCreateJobData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestCreateJob {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public GuestCreateJobResponseBean saveGuestCreationJob(GuestCreateJobRequestBean guestCreateJobRequestBean) {
        GuestCreateJobResponseBean guestCreateJobResponseBean = new GuestCreateJobResponseBean();
        Integer iNumOfRows = 0;
        if(guestCreateJobRequestBean!=null) {
            if( Utility.isNullOrEmpty(guestCreateJobRequestBean.getGuestCreateJobId())) {
                guestCreateJobRequestBean.setGuestCreateJobId(Utility.getNewGuid());
                guestCreateJobResponseBean = createGuestCreationJob(guestCreateJobRequestBean);
            } else {
                guestCreateJobResponseBean = updateGuestCreationJob(guestCreateJobRequestBean);
            }
        }
        appLogging.info(" Save guestCreateJobResponseBean : " + guestCreateJobResponseBean);
        return guestCreateJobResponseBean;
    }

    public GuestCreateJobResponseBean createGuestCreationJob(GuestCreateJobRequestBean guestCreateJobRequestBean) {
        GuestCreateJobResponseBean guestCreateJobResponseBean = new GuestCreateJobResponseBean();
        if(guestCreateJobRequestBean!=null) {
            GuestCreateJobData guestCreateJobData = new GuestCreateJobData();
            Integer iNumOfRows = guestCreateJobData.insertGuestCreationJob(guestCreateJobRequestBean);
            appLogging.info(" Create guestCreateJobResponseBean iNumOfRows : " + iNumOfRows + " - " + guestCreateJobRequestBean.getGuestCreateJobId());
            if(iNumOfRows>0){
                guestCreateJobResponseBean.setGuestCreateJobId( guestCreateJobRequestBean.getGuestCreateJobId() );
            }
        }
        return guestCreateJobResponseBean;
    }

    public GuestCreateJobResponseBean updateGuestCreationJob(GuestCreateJobRequestBean guestCreateJobRequestBean) {
        GuestCreateJobResponseBean guestCreateJobResponseBean = new GuestCreateJobResponseBean();
        if(guestCreateJobRequestBean!=null) {
            GuestCreateJobData guestCreateJobData = new GuestCreateJobData();
            Integer iNumOfRows = guestCreateJobData.updateGuestCreationJob(guestCreateJobRequestBean);
            if(iNumOfRows>0){
                guestCreateJobResponseBean.setGuestCreateJobId( guestCreateJobRequestBean.getGuestCreateJobId() );
            }
        }
        return guestCreateJobResponseBean;
    }

    public GuestCreateJobResponseBean getGuestCreationJobByEvent(GuestCreateJobRequestBean guestCreateJobRequestBean) {
        GuestCreateJobResponseBean guestCreateJobResponseBean = new GuestCreateJobResponseBean();
        if(guestCreateJobRequestBean!=null) {
            GuestCreateJobData guestCreateJobData = new GuestCreateJobData();
            GuestCreateJobBean guestCreateJobBean = guestCreateJobData.getGuestCreateJobBeanByEvent(guestCreateJobRequestBean);
            guestCreateJobResponseBean.setGuestCreateJobBean( guestCreateJobBean );
        }
        return guestCreateJobResponseBean;
    }
}
