package com.events.event.guest;

import com.events.bean.event.guest.GuestRequestBean;
import com.events.bean.event.guest.GuestResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.guest.BuildGuestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/2/14
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildGuest {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public GuestResponseBean saveGuestGroup(GuestRequestBean guestRequestBean){
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())) {
            guestRequestBean.setGuestGroupId( Utility.getNewGuid());
            guestResponseBean = createGuestGroup(guestRequestBean);
        } else {
            guestResponseBean = updateGuestGroup(guestRequestBean);
        }

        if(guestResponseBean!=null && !Utility.isNullOrEmpty(guestResponseBean.getGuestGroupId())) {

            deleteGuestContacts(guestRequestBean);

            BuildGuestData buildGuestData = new BuildGuestData();
            buildGuestData.deleteGuest(guestRequestBean);

            guestRequestBean.setGuestId( Utility.getNewGuid());
            Integer iNumOfGuests = buildGuestData.insertGuest(guestRequestBean);

            if(iNumOfGuests>0){
                if(!Utility.isNullOrEmpty(guestRequestBean.getPhone1())) {
                    guestRequestBean.setGuestGroupPhoneId( Utility.getNewGuid());
                    buildGuestData.insertGuestPhone(guestRequestBean, guestRequestBean.getPhone1());
                }

                if(!Utility.isNullOrEmpty(guestRequestBean.getPhone2())) {
                    guestRequestBean.setGuestGroupPhoneId( Utility.getNewGuid());
                    buildGuestData.insertGuestPhone(guestRequestBean, guestRequestBean.getPhone2());
                }

                if(!Utility.isNullOrEmpty(guestRequestBean.getEmail())) {
                    guestRequestBean.setGuestGroupEmailId(  Utility.getNewGuid());
                    buildGuestData.insertGuestEmail(guestRequestBean);
                }

                if(!Utility.isNullOrEmpty(guestRequestBean.getAddress1()) || !Utility.isNullOrEmpty(guestRequestBean.getCity())
                        || !Utility.isNullOrEmpty(guestRequestBean.getState()) || !Utility.isNullOrEmpty(guestRequestBean.getZipCode())
                        || !Utility.isNullOrEmpty(guestRequestBean.getCountry()) ) {
                    guestRequestBean.setGuestGroupAddressId(  Utility.getNewGuid());
                    buildGuestData.insertGuestAddress(guestRequestBean);
                }
                if(guestRequestBean.isNotAttending() ||guestRequestBean.getRsvpSeats()>0){
                    guestRequestBean.setHasResponded(true);
                }

                Integer iNumOfEventGuests = 0;
                if(Utility.isNullOrEmpty(guestRequestBean.getEventGuestGroupId())) {
                    guestRequestBean.setEventGuestGroupId( Utility.getNewGuid());
                    iNumOfEventGuests = buildGuestData.insertEventGuestGroup(guestRequestBean);
                } else {
                    iNumOfEventGuests = buildGuestData.updateEventGuestGroup(guestRequestBean);
                }

                guestResponseBean.setEventId( guestRequestBean.getEventId() );
                guestResponseBean.setEventGuestGroupId( guestRequestBean.getEventGuestGroupId() );
            } else {
                guestResponseBean.setGuestGroupId(Constants.EMPTY);
                appLogging.error("Error creating Guest");
            }

        } else {
            guestResponseBean.setGuestGroupId(Constants.EMPTY);
            appLogging.error("Error creating Guest Group");
        }
        return guestResponseBean;
    }

    public GuestResponseBean createGuestGroup(GuestRequestBean guestRequestBean) {
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId() )){
            BuildGuestData buildGuestData = new BuildGuestData();
            Integer iNumOfRows = buildGuestData.insertGuestGroup(guestRequestBean);
            if(iNumOfRows<=0){
                appLogging.info("Guest Group was not created. " + guestRequestBean);
            } else{
                guestResponseBean.setGuestGroupId( guestRequestBean.getGuestGroupId() );
            }
        } else {
            appLogging.info("Invalid Guest Group was used. " + ParseUtil.checkNullObject(guestRequestBean));
        }
        return guestResponseBean;
    }

    public void deleteGuestContacts(GuestRequestBean guestRequestBean){
        if(guestRequestBean!=null) {
            BuildGuestData buildGuestData = new BuildGuestData();
            buildGuestData.deleteGuestPhone(guestRequestBean);
            buildGuestData.deleteGuestEmails(guestRequestBean);
            buildGuestData.deleteGuestAddress(guestRequestBean);
        }
    }

    public GuestResponseBean updateGuestGroup(GuestRequestBean guestRequestBean) {
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())){
            BuildGuestData buildGuestData = new BuildGuestData();
            int iNumOfRows = buildGuestData.updateGuestGroup(guestRequestBean);
            if(iNumOfRows>0){
                guestResponseBean.setGuestGroupId( guestRequestBean.getGuestGroupId() );
            }
        }
        return guestResponseBean;
    }

    public GuestResponseBean updateEventGuestGroupRSVP(GuestRequestBean guestRequestBean) {
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId()) && !Utility.isNullOrEmpty(guestRequestBean.getEventId())
                && !Utility.isNullOrEmpty(guestRequestBean.getEventGuestGroupId()) ){
            appLogging.info("Invoked this here");
            BuildGuestData buildGuestData = new BuildGuestData();
            Integer iNumOfRows = buildGuestData.updateEventGuestGroupRSVP(guestRequestBean);
            if(iNumOfRows>0){
                guestResponseBean.setGuestGroupId( guestRequestBean.getGuestGroupId() );
            }
        }
        return guestResponseBean;
    }
    public GuestResponseBean deleteEventGuestGroup(GuestRequestBean guestRequestBean){
        GuestResponseBean guestResponseBean =  new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())){
            BuildGuestData buildGuestData = new BuildGuestData();
            Integer numOfRowsDeleted = buildGuestData.deleteEventGuestGroup(guestRequestBean);
            if(numOfRowsDeleted>0){
                guestResponseBean.setGuestDeleted(true);
                appLogging.info("Event Guest Group was deleted successfully : " + guestRequestBean );
            } else {
                appLogging.error("Event Guest Group could not be deleted : " + guestRequestBean );
            }
        }
        return guestResponseBean;
    }
}
