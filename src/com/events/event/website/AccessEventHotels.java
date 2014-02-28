package com.events.event.website;

import com.events.bean.event.website.EventHotelRequest;
import com.events.bean.event.website.EventHotelsBean;
import com.events.common.Utility;
import com.events.data.event.website.AccessEventHotelData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventHotels {

    public EventHotelsBean getEventHotel(EventHotelRequest eventHotelRequest){
        EventHotelsBean eventHotelsBean = new EventHotelsBean();
        if(eventHotelRequest!=null){
            AccessEventHotelData accessEventPartyData = new AccessEventHotelData();
            eventHotelsBean = accessEventPartyData.getEventHotel( eventHotelRequest );
        }
        return eventHotelsBean;
    }

    public ArrayList<EventHotelsBean> getEventHotelByWebsite(EventHotelRequest eventHotelRequest){
        ArrayList<EventHotelsBean> arrEventHotelsBean =  new ArrayList<EventHotelsBean>();
        if(eventHotelRequest!=null){
            AccessEventHotelData accessEventPartyData = new AccessEventHotelData();
            arrEventHotelsBean = accessEventPartyData.getEventHotelByWebsite(eventHotelRequest);
        }
        return arrEventHotelsBean;
    }

    public JSONObject getEventHotelJson(ArrayList<EventHotelsBean> arrEventHotelsBean ){
        JSONObject jsonObject = new JSONObject();
        if(arrEventHotelsBean!=null && !arrEventHotelsBean.isEmpty()){


            Integer iNumOfEventParty = 0;
            for(EventHotelsBean eventHotelsBean : arrEventHotelsBean ){
                JSONObject jsonEventParty = eventHotelsBean.toJson();


                jsonObject.put(iNumOfEventParty.toString(), jsonEventParty) ;
                iNumOfEventParty++;
            }
            jsonObject.put("num_of_event_hotels" ,iNumOfEventParty);
        }
        return jsonObject;
    }
}
