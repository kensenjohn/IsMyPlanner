package com.events.data.tracker;

import com.events.bean.common.TrackerEmailBean;
import com.events.common.*;
import com.events.common.nosql.redis.RedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/20/14
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildTrackerEmailOpenedData {
    private static Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer putEmailWhoOpened(TrackerEmailBean trackerEmailBean) {
        int iRowsAffected = 0;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestEmailAddress())  && !Utility.isNullOrEmpty(trackerEmailBean.getGuestId())
                && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())){
            HashMap<String,String> hmTrackEmails = new HashMap<String, String>();
            hmTrackEmails.put(trackerEmailBean.getGuestEmailAddress() , trackerEmailBean.getGuestId() );
            iRowsAffected = RedisDAO.putInHash(EVENTADMIN_DB , "hash." + trackerEmailBean.getEventEmailId() + ".emails", hmTrackEmails );
        }
        return iRowsAffected;
    }

    public String putNumOfTimesEmailViewed(TrackerEmailBean trackerEmailBean){
        String status = Constants.EMPTY;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestId())  && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())) {
            status =  RedisDAO.set(EVENTADMIN_DB,
                    "views." + trackerEmailBean.getEventEmailId() + "." + trackerEmailBean.getGuestId(), ParseUtil.LToS(trackerEmailBean.getNumberOfViews()));
        }
        return status;
    }

    public void putEmailViewedTimestamp(TrackerEmailBean trackerEmailBean) {
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestId())  && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())) {
            RedisDAO.set(EVENTADMIN_DB ,
                    "views.timestamp.utc."+ trackerEmailBean.getEventEmailId() + "."+trackerEmailBean.getGuestId()+"."+trackerEmailBean.getNumberOfViews(),
                    DateSupport.getUTCDateTime());
            RedisDAO.set(EVENTADMIN_DB ,
                    "views.timestamp.unix."+ trackerEmailBean.getEventEmailId() + "."+trackerEmailBean.getGuestId()+"."+trackerEmailBean.getNumberOfViews(),
                    ParseUtil.LToS(DateSupport.getEpochMillis()));
        }
    }

    public void incrementViewCount(TrackerEmailBean trackerEmailBean){
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())){
            RedisDAO.incrementCounter(EVENTADMIN_DB , "views."+ trackerEmailBean.getEventEmailId() + ".counter" , 1L);
        }
    }
}
