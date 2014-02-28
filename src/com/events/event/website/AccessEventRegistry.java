package com.events.event.website;

import com.events.bean.event.website.EventRegistryBean;
import com.events.bean.event.website.EventRegistryRequest;
import com.events.data.event.website.AccessEventRegistryData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventRegistry {
    public EventRegistryBean getEventRegistry(EventRegistryRequest eventHotelRequest){
        EventRegistryBean eventRegistryBean = new EventRegistryBean();
        if(eventHotelRequest!=null){
            AccessEventRegistryData accessEventPartyData = new AccessEventRegistryData();
            eventRegistryBean = accessEventPartyData.getEventRegistry( eventHotelRequest );
        }
        return eventRegistryBean;
    }

    public ArrayList<EventRegistryBean> getEventRegistryByWebsite(EventRegistryRequest eventHotelRequest){
        ArrayList<EventRegistryBean> arrEventRegistryBean =  new ArrayList<EventRegistryBean>();
        if(eventHotelRequest!=null){
            AccessEventRegistryData accessEventPartyData = new AccessEventRegistryData();
            arrEventRegistryBean = accessEventPartyData.getEventRegistryByWebsite(eventHotelRequest);
        }
        return arrEventRegistryBean;
    }

    public JSONObject getEventRegistryJson(ArrayList<EventRegistryBean> arrEventRegistryBean ){
        JSONObject jsonObject = new JSONObject();
        if(arrEventRegistryBean!=null && !arrEventRegistryBean.isEmpty()){


            Integer iNumOfEventParty = 0;
            for(EventRegistryBean eventRegistryBean : arrEventRegistryBean ){
                JSONObject jsonEventParty = eventRegistryBean.toJson();


                jsonObject.put(iNumOfEventParty.toString(), jsonEventParty) ;
                iNumOfEventParty++;
            }
            jsonObject.put("num_of_event_registry" ,iNumOfEventParty);
        }
        return jsonObject;
    }
}
