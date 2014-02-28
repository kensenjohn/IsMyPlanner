package com.events.event.website;

import com.events.bean.event.website.EventContactUsBean;
import com.events.bean.event.website.EventContactUsRequest;
import com.events.data.event.website.AccessEventContactUsData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventContactUs {

    public EventContactUsBean getEventContactUs(EventContactUsRequest eventHotelRequest){
        EventContactUsBean eventContactUsBean = new EventContactUsBean();
        if(eventHotelRequest!=null){
            AccessEventContactUsData accessEventContactUsData = new AccessEventContactUsData();
            eventContactUsBean = accessEventContactUsData.getEventContactUs( eventHotelRequest );
        }
        return eventContactUsBean;
    }

    public ArrayList<EventContactUsBean> getEventContactUsByWebsite(EventContactUsRequest eventHotelRequest){
        ArrayList<EventContactUsBean> arrEventContactUsBean =  new ArrayList<EventContactUsBean>();
        if(eventHotelRequest!=null){
            AccessEventContactUsData accessEventContactUsData = new AccessEventContactUsData();
            arrEventContactUsBean = accessEventContactUsData.getEventContactUsByWebsite(eventHotelRequest);
        }
        return arrEventContactUsBean;
    }

    public JSONObject getEventContactUsJson(ArrayList<EventContactUsBean> arrEventContactUsBean ){
        JSONObject jsonObject = new JSONObject();
        if(arrEventContactUsBean!=null && !arrEventContactUsBean.isEmpty()){


            Integer iNumOfEventParty = 0;
            for(EventContactUsBean eventContactUsBean : arrEventContactUsBean ){
                JSONObject jsonEventParty = eventContactUsBean.toJson();


                jsonObject.put(iNumOfEventParty.toString(), jsonEventParty) ;
                iNumOfEventParty++;
            }
            jsonObject.put("num_of_event_contactus" ,iNumOfEventParty);
        }
        return jsonObject;
    }
}
