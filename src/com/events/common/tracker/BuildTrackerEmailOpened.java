package com.events.common.tracker;

import com.events.bean.common.TrackerEmailBean;
import com.events.bean.event.guest.GuestGroupEmailBean;
import com.events.bean.event.guest.GuestRequestBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.tracker.AccessTrackerEmailOpenedData;
import com.events.data.tracker.BuildTrackerEmailOpenedData;
import com.events.event.guest.AccessGuest;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/20/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildTrackerEmailOpened {
    public void saveTracks(TrackerEmailBean trackerEmailBean){
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestId())
                && !Utility.isNullOrEmpty(trackerEmailBean.getEventEmailId())) {

            BuildTrackerEmailOpenedData buildTrackerEmailOpenedData = new BuildTrackerEmailOpenedData();
            String sGuestId =  getGuestIdOfUserWhoOpenedEmail( trackerEmailBean );
            boolean isNewEmail = false;
            if(Utility.isNullOrEmpty(sGuestId)) {
                // there is no record. so create record here.
                buildTrackerEmailOpenedData.putEmailWhoOpened( trackerEmailBean );
                isNewEmail = true;
            }

            Long iNumOfTimesGuestViewedEmail = 0L;
            if(!isNewEmail) {
                AccessTrackerEmailOpened accessTrackerEmailOpened = new AccessTrackerEmailOpened();
                iNumOfTimesGuestViewedEmail = accessTrackerEmailOpened.getNumOfTimesEmailViewedByUser( trackerEmailBean );
            }
            iNumOfTimesGuestViewedEmail++; //increment count
            trackerEmailBean.setNumberOfViews( iNumOfTimesGuestViewedEmail );
            buildTrackerEmailOpenedData.putNumOfTimesEmailViewed( trackerEmailBean );

            buildTrackerEmailOpenedData.putEmailViewedTimestamp( trackerEmailBean );

            buildTrackerEmailOpenedData.incrementViewCount( trackerEmailBean );
        }
    }

    public String getGuestIdOfUserWhoOpenedEmail(TrackerEmailBean trackerEmailBean) {
        String sGuestId = Constants.EMPTY;
        if(trackerEmailBean!=null && !Utility.isNullOrEmpty(trackerEmailBean.getGuestId())){
            GuestRequestBean guestRequestBean = new GuestRequestBean();
            guestRequestBean.setGuestId( trackerEmailBean.getGuestId() );

            AccessGuest accessGuest = new AccessGuest();
            GuestGroupEmailBean guestGroupEmailBean = accessGuest.getGuestEmail(guestRequestBean);

            if(guestGroupEmailBean!=null && !Utility.isNullOrEmpty(guestGroupEmailBean.getemailId())){
                trackerEmailBean.setGuestEmailAddress( guestGroupEmailBean.getemailId() );

                AccessTrackerEmailOpened accessTrackerEmailOpened = new AccessTrackerEmailOpened();
                sGuestId = accessTrackerEmailOpened.getGuestIdOfUserWhoOpenedEmail( trackerEmailBean );
            }
        }
        return sGuestId;
    }
}
