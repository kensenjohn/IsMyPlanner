package com.events.event.website;

import com.events.bean.event.website.EventPartyBean;
import com.events.bean.event.website.EventPartyRequest;
import com.events.bean.event.website.SocialMediaBean;
import com.events.data.event.website.AccessEventPartyData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventParty {
    public EventPartyBean getEventParty(EventPartyRequest eventPartyRequest){
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null){
            AccessEventPartyData accessEventPartyData = new AccessEventPartyData();
            eventPartyBean = accessEventPartyData.getEventParty( eventPartyRequest );
        }
        return eventPartyBean;
    }

    public ArrayList<EventPartyBean> getEventPartyByWebsite(EventPartyRequest eventPartyRequest){
        ArrayList<EventPartyBean> arrEventPartyBean =  new ArrayList<EventPartyBean>();
        if(eventPartyRequest!=null){
            AccessEventPartyData accessEventPartyData = new AccessEventPartyData();
            arrEventPartyBean = accessEventPartyData.getEventPartyByWebsite(eventPartyRequest);
        }
        return arrEventPartyBean;
    }

    public JSONObject getEventPartyJson(ArrayList<EventPartyBean> arrEventPartyBean , ArrayList<SocialMediaBean> arrSocialMediaBean){
        JSONObject jsonObject = new JSONObject();
        if(arrEventPartyBean!=null && !arrEventPartyBean.isEmpty()){
            Integer iNumOfEventParty = 0;
            for(EventPartyBean eventPartyBean : arrEventPartyBean ){
                jsonObject.put(iNumOfEventParty.toString(), eventPartyBean.toJson()) ;
            }
        }
        return jsonObject;
    }
}
