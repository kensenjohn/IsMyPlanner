package com.events.common.tracker;

import com.events.bean.common.TrackerEmailBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.tracker.AccessTrackerEmailOpenedData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/20/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessTrackerEmailOpened {
    public Long getTotalNumOfTimesEmailViewed(TrackerEmailBean trackerEmailBean){
        Long lNumOfTimesEmailViewed = 0L;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())){
            AccessTrackerEmailOpenedData accessTrackerEmailOpenedData = new AccessTrackerEmailOpenedData();
            lNumOfTimesEmailViewed = accessTrackerEmailOpenedData.getTotalNumOfTimesEmailViewed(trackerEmailBean);
        }
        return lNumOfTimesEmailViewed;
    }

    public Long getNumOfTimesEmailViewedByUser(TrackerEmailBean trackerEmailBean){
        Long lNumOfTimesViewed = 0L;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId()) && !Utility.isNullOrEmpty(trackerEmailBean.getGuestId())) {
            AccessTrackerEmailOpenedData accessTrackerEmailOpenedData = new AccessTrackerEmailOpenedData();
            accessTrackerEmailOpenedData.getNumOfTimesEmailViewedByUser( trackerEmailBean );
        }
        return lNumOfTimesViewed;
    }
    public String getGuestIdOfUserWhoOpenedEmail(TrackerEmailBean trackerEmailBean) {
        String sGuestId = Constants.EMPTY;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestEmailAddress())  && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())){
            AccessTrackerEmailOpenedData accessTrackerEmailOpenedData = new AccessTrackerEmailOpenedData();
            sGuestId = accessTrackerEmailOpenedData.getGuestIdOfUserWhoOpenedEmail( trackerEmailBean);
        }
        return sGuestId;
    }

    public ArrayList<TrackerEmailBean> getUsersWhoViewedEmail(TrackerEmailBean trackerEmailBean){
        ArrayList<TrackerEmailBean> arrAllUsersWhoViewedEmail = new ArrayList<TrackerEmailBean>();
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())) {
            AccessTrackerEmailOpenedData accessTrackerEmailOpenedData = new AccessTrackerEmailOpenedData();
            arrAllUsersWhoViewedEmail = accessTrackerEmailOpenedData.getAllUsersWhoViewedEmail( trackerEmailBean );
        }
        return arrAllUsersWhoViewedEmail;
    }

    public JSONObject getJsonUsersWhoViewedEmail(ArrayList<TrackerEmailBean> arrAllUsersWhoViewedEmail) {
        JSONObject jsonAllUsersWhoViewedEmail = new JSONObject();
        if(arrAllUsersWhoViewedEmail!=null && !arrAllUsersWhoViewedEmail.isEmpty()){
            Long lNumOfUsers = 0L;
            for( TrackerEmailBean userWhoViewedEmail : arrAllUsersWhoViewedEmail ) {
                jsonAllUsersWhoViewedEmail.put(ParseUtil.LToS(lNumOfUsers) , userWhoViewedEmail.toJson() );
                lNumOfUsers++;
            }
            jsonAllUsersWhoViewedEmail.put("total_num_of_users" , ParseUtil.LToS(lNumOfUsers) );
        }
        return jsonAllUsersWhoViewedEmail;
    }
}
