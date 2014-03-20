package com.events.event.guest;

import com.events.bean.event.EveryEventResponseBean;
import com.events.bean.event.guest.*;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.guest.AccessGuestData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/2/14
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessGuest {

    public GuestResponseBean loadEveryEventsGuests(GuestRequestBean guestRequestBean){
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getEventId())) {
            AccessGuestData accessGuestData = new AccessGuestData();
            ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup = accessGuestData.getEventGuestGroupAndGuestGroup(guestRequestBean);

            if(arrEveryEventGuestGroup!=null && !arrEveryEventGuestGroup.isEmpty()) {
                guestResponseBean.setArrEveryEventGuestGroup( arrEveryEventGuestGroup );
            }
        }
        return guestResponseBean;
    }
    public GuestResponseBean loadGuest( GuestRequestBean guestRequestBean ) {
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getEventId()) && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId()) ) {
            AccessGuestData accessGuestData = new AccessGuestData();
            EventGuestGroupBean eventGuestGroupBean = accessGuestData.getEventGuestGroup(guestRequestBean);
            GuestGroupBean guestGroupBean = accessGuestData.getGuestGroup(guestRequestBean);
            GuestBean guestBean = accessGuestData.getGuest(guestRequestBean);
            ArrayList<GuestGroupPhoneBean>  arrGuestGroupPhoneBean = accessGuestData.getGuestGroupPhone(guestRequestBean);
            ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = accessGuestData.getGuestGroupEmail(guestRequestBean);
            ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = accessGuestData.getGuestGroupAddress(guestRequestBean);

            guestResponseBean.setEventGuestGroupBean(eventGuestGroupBean);
            guestResponseBean.setGuestGroupBean(guestGroupBean);
            guestResponseBean.setGuestBean(guestBean);
            guestResponseBean.setArrGuestGroupPhoneBean(arrGuestGroupPhoneBean);
            guestResponseBean.setArrGuestGroupEmailBean(arrGuestGroupEmailBean);
            guestResponseBean.setArrGuestGroupAddressBean(arrGuestGroupAddressBean);
        }
        return guestResponseBean;
    }

    public GuestGroupEmailBean getGuestEmail(GuestRequestBean guestRequestBean) {
        GuestGroupEmailBean guestGroupEmailBean = new GuestGroupEmailBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestId()) ) {
            AccessGuestData accessGuestData = new AccessGuestData();
            guestGroupEmailBean = accessGuestData.getGuestEmail( guestRequestBean );
        }
        return guestGroupEmailBean;
    }
    public GuestResponseBean loadAllGuests(GuestRequestBean guestRequestBean){
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getEventId())) {
            AccessGuestData accessGuestData = new AccessGuestData();
            ArrayList<EventGuestGroupBean> arrEventGuestGroupBean = accessGuestData.getAllEventGuestGroup(guestRequestBean);
            if(arrEventGuestGroupBean!=null && !arrEventGuestGroupBean.isEmpty()){

                ArrayList<GuestGroupBean> arrGuestGroupBeanId = new ArrayList<GuestGroupBean>();
                for(EventGuestGroupBean eventGuestGroupBean : arrEventGuestGroupBean) {
                    GuestGroupBean guestGroupBean = new GuestGroupBean();
                    guestGroupBean.setGuestGroupId( eventGuestGroupBean.getGuestGroupId() );

                    arrGuestGroupBeanId.add( guestGroupBean );
                }

                HashMap<String,GuestGroupBean> hmGuestGroupBean = accessGuestData.getAllGuestGroup(guestRequestBean);

                if(hmGuestGroupBean!=null && !hmGuestGroupBean.isEmpty()) {
                    guestRequestBean.setArrGuestGroupBean(arrGuestGroupBeanId);
                    guestResponseBean.setHmGuestGroupBean(hmGuestGroupBean);
                }
            }
        }
        return guestResponseBean;
    }

    public JSONObject getEveryEventsGuestsJson(GuestResponseBean guestResponseBean) {
        JSONObject jsonAllGuestJson = new JSONObject();
        if(guestResponseBean!=null) {
            ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup = guestResponseBean.getArrEveryEventGuestGroup();

            Integer iNumberOfGuests = 0;
            for(EveryEventGuestGroupBean eventGuestGroupBean : arrEveryEventGuestGroup ) {
                jsonAllGuestJson.put( ParseUtil.iToS(iNumberOfGuests), eventGuestGroupBean.toJson());
                iNumberOfGuests++;
            }
        }
        return jsonAllGuestJson;
    }
}
