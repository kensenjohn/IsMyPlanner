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

    public ArrayList<TrackerEmailBean> getGuestsWhoViewedEmail(TrackerEmailBean trackerEmailBean){
        ArrayList<TrackerEmailBean> arrAllGuestsWhoViewedEmail = new ArrayList<TrackerEmailBean>();
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())) {
            AccessTrackerEmailOpenedData accessTrackerEmailOpenedData = new AccessTrackerEmailOpenedData();
            arrAllGuestsWhoViewedEmail = accessTrackerEmailOpenedData.getAllGuestsWhoViewedEmail( trackerEmailBean );
        }
        return arrAllGuestsWhoViewedEmail;
    }

    public JSONObject getJsonGuestsWhoViewedEmail(ArrayList<TrackerEmailBean> arrAllGuestsWhoViewedEmail) {
        JSONObject jsonAllGuestsWhoViewedEmail = new JSONObject();
        if(arrAllGuestsWhoViewedEmail!=null && !arrAllGuestsWhoViewedEmail.isEmpty()){
            Long lNumOfGuests = 0L;
            for( TrackerEmailBean userWhoViewedEmail : arrAllGuestsWhoViewedEmail ) {
                jsonAllGuestsWhoViewedEmail.put(ParseUtil.LToS(lNumOfGuests) , userWhoViewedEmail.toJson() );
                lNumOfGuests++;
            }
            jsonAllGuestsWhoViewedEmail.put("total_num_of_guests" , ParseUtil.LToS(lNumOfGuests) );
        }
        return jsonAllGuestsWhoViewedEmail;
    }
}
