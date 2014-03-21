package com.events.data.tracker;

import com.events.bean.common.TrackerEmailBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.nosql.redis.RedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/20/14
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessTrackerEmailOpenedData {
    private static Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Long getTotalNumOfTimesEmailViewed(TrackerEmailBean trackerEmailBean){
        Long lNumOfViews = 0L;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())){
            lNumOfViews = ParseUtil.sToL(RedisDAO.get(EVENTADMIN_DB , "views." + trackerEmailBean.getEventEmailId() + ".counter" )) ;
        }
        return lNumOfViews;
    }
    public String getGuestIdOfUserWhoOpenedEmail(TrackerEmailBean trackerEmailBean) {
        String sGuestId = Constants.EMPTY;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestEmailAddress())  && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())){
            sGuestId = RedisDAO.getValueFromHash(EVENTADMIN_DB , "hash." + trackerEmailBean.getEventEmailId() + ".emails", trackerEmailBean.getGuestEmailAddress());
        }
        return sGuestId;
    }
    public ArrayList<TrackerEmailBean> getAllGuestsWhoViewedEmail(TrackerEmailBean trackerEmailBean){
        ArrayList<TrackerEmailBean> arrTrackerEmailBean = new ArrayList<TrackerEmailBean>();
        if(trackerEmailBean!=null  && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())){
            HashMap<String,String> hmResult = RedisDAO.getAllFromHash( EVENTADMIN_DB , "hash." + trackerEmailBean.getEventEmailId() + ".emails" );
            if(hmResult!=null && !hmResult.isEmpty()) {
                for(Map.Entry<String,String> mapGuests : hmResult.entrySet() ) {

                    TrackerEmailBean guestTrackerEmailBean = new TrackerEmailBean();
                    Long iNumOfViews = ParseUtil.sToL(RedisDAO.get(EVENTADMIN_DB, "views." + trackerEmailBean.getEventEmailId() + "." + mapGuests.getValue()));
                    guestTrackerEmailBean.setNumberOfViews( iNumOfViews );
                    guestTrackerEmailBean.setGuestEmailAddress( mapGuests.getKey() );
                    guestTrackerEmailBean.setGuestId( mapGuests.getValue() );
                    guestTrackerEmailBean.setEventEmailId( trackerEmailBean.getEventEmailId() );

                    arrTrackerEmailBean.add( guestTrackerEmailBean );
                }
            }
        }
        return arrTrackerEmailBean;
    }

    public Long getNumOfTimesEmailViewedByUser(TrackerEmailBean trackerEmailBean){
        Long lNumOfTimesViewed = 0L;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestId())  && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())) {
            lNumOfTimesViewed = ParseUtil.sToL(RedisDAO.get(EVENTADMIN_DB , "views."+ trackerEmailBean.getEventEmailId() + "."+trackerEmailBean.getGuestId()));
        }
        return lNumOfTimesViewed;
    }
}
