package com.events.event;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.event.*;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.AccessEveryEventData;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/29/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEveryEvent {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EveryEventResponseBean getEveryEvent(EveryEventRequestBean everyEventRequestBean){

        EveryEventResponseBean everyEventResponseBean = new EveryEventResponseBean();
        if(everyEventRequestBean!=null){
            AccessEveryEventData accessEveryEventData = new AccessEveryEventData();
            ArrayList<EveryEventBean> arrEveryEventBean  = new ArrayList<EveryEventBean>();
            if(everyEventRequestBean.isLoadEventsByClient()) {
                arrEveryEventBean  = accessEveryEventData.getEveryEventByClient(everyEventRequestBean);
            } else {
                arrEveryEventBean  = accessEveryEventData.getEveryEventByVendor(everyEventRequestBean);
            }


            HashMap<String,ClientBean> hmClientBean = getClientDetails(arrEveryEventBean);
            HashMap<String,EventDisplayDateBean> hmEventDisplayDate = getEventDisplayDate(arrEveryEventBean);

            everyEventResponseBean.setArrEveryEventBean(arrEveryEventBean);
            everyEventResponseBean.setHmClientBean(hmClientBean);
            everyEventResponseBean.setHmEventDisplayDateBean(hmEventDisplayDate);
        }
        return everyEventResponseBean;
    }

    public JSONObject getEveryEventJson(EveryEventResponseBean everyEventResponseBean) {
        JSONObject jsonEveryEvent = new JSONObject();
        if(everyEventResponseBean!=null && everyEventResponseBean.getArrEveryEventBean()!=null ) {
            ArrayList<EveryEventBean> arrEveryEventBean  = everyEventResponseBean.getArrEveryEventBean();
            HashMap<String,ClientBean> hmClientBean = everyEventResponseBean.getHmClientBean();
            HashMap<String,EventDisplayDateBean> hmEventDisplayDateBean = everyEventResponseBean.getHmEventDisplayDateBean();
            if(arrEveryEventBean!=null && !arrEveryEventBean.isEmpty() &&
                    hmEventDisplayDateBean!=null && !hmEventDisplayDateBean.isEmpty()) {
                int iNumOfEvents = 0;
                for(EveryEventBean everyEventBean : arrEveryEventBean ){

                    EveryEventBean newEveryEventBean = new EveryEventBean();

                    String sEventID = ParseUtil.checkNull(everyEventBean.getEventId());
                    newEveryEventBean.setEventId(sEventID);
                    newEveryEventBean.setEventName( ParseUtil.checkNull(everyEventBean.getEventName()) );

                    EventDisplayDateBean eventDisplayDateBean = hmEventDisplayDateBean.get(sEventID);
                    newEveryEventBean.setEventDay( eventDisplayDateBean.getSelectedDay() );
                    newEveryEventBean.setEventTime( eventDisplayDateBean.getSelectedTime() );
                    if( !"".equalsIgnoreCase(ParseUtil.checkNull(eventDisplayDateBean.getSelectedTimeZone())) && Constants.TIME_ZONE.valueOf(eventDisplayDateBean.getSelectedTimeZone()) !=null ){
                        newEveryEventBean.setEventTimeZone( Constants.TIME_ZONE.valueOf(eventDisplayDateBean.getSelectedTimeZone()).getTimeZoneDisplay() );
                    }

                    String sClientID = ParseUtil.checkNull(everyEventBean.getClientId());
                    newEveryEventBean.setClientId( sClientID );

                    if(hmClientBean!=null && !hmClientBean.isEmpty()) {
                        ClientBean clientBean = hmClientBean.get(sClientID);
                        newEveryEventBean.setClientName( clientBean.getClientName() );
                        newEveryEventBean.setVendorId( ParseUtil.checkNull(clientBean.getVendorId()));
                    }



                    jsonEveryEvent.put( ParseUtil.iToS(iNumOfEvents) , newEveryEventBean.toJson() );
                    iNumOfEvents++;
                }
            }
        }
        return jsonEveryEvent;
    }

    public HashMap<String,ClientBean> getClientDetails(ArrayList<EveryEventBean> arrEveryEventBean){
        HashMap<String,ClientBean> hmClientBean = new HashMap<String, ClientBean>();
        if(arrEveryEventBean!=null && !arrEveryEventBean.isEmpty()) {
            AccessClients accessClients = new AccessClients();
            for( EveryEventBean everyEventBean : arrEveryEventBean ) {

                String sClientId = ParseUtil.checkNull(everyEventBean.getClientId());
                if(sClientId!=null && !"".equalsIgnoreCase(sClientId)) {

                    ClientRequestBean clientRequestBean = new ClientRequestBean();
                    clientRequestBean.setVendorId(everyEventBean.getVendorId());
                    clientRequestBean.setClientId(sClientId);

                    ClientBean clientBean = accessClients.getClientDataByVendorAndClient(clientRequestBean);

                    hmClientBean.put(sClientId,clientBean);
                }

            }
        }
        return hmClientBean;
    }

    public HashMap<String,EventDisplayDateBean> getEventDisplayDate(ArrayList<EveryEventBean> arrEveryEventBean) {
        HashMap<String,EventDisplayDateBean> hmEventDisplayDate = new HashMap<String, EventDisplayDateBean>();
        if(arrEveryEventBean!=null && !arrEveryEventBean.isEmpty()) {
            AccessEvent accessEvent = new AccessEvent();

            for( EveryEventBean everyEventBean : arrEveryEventBean ) {
                String sEventId =  ParseUtil.checkNull(everyEventBean.getEventId());
                if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
                    EventRequestBean eventRequestBean = new EventRequestBean();
                    eventRequestBean.setEventId(sEventId);

                    EventDisplayDateBean eventDisplayDateBean = accessEvent.getEventSelectedDisplayDate(eventRequestBean);

                    hmEventDisplayDate.put(sEventId,eventDisplayDateBean);

                }
            }

        }
        return hmEventDisplayDate;
    }


    public EveryEventResponseBean getEveryClientEvent(EveryEventRequestBean everyEventRequestBean) {
        EveryEventResponseBean everyEventResponseBean = new EveryEventResponseBean();
        if(everyEventRequestBean!=null && !Utility.isNullOrEmpty(everyEventRequestBean.getClientId()) ) {
            AccessEveryEventData accessEveryEventData = new AccessEveryEventData();
            ArrayList<EveryEventBean> arrEveryEventBean  = accessEveryEventData.getEveryEventByClient(everyEventRequestBean);
            HashMap<String,EventDisplayDateBean> hmEventDisplayDate = getEventDisplayDate(arrEveryEventBean);
            everyEventResponseBean.setArrEveryEventBean(arrEveryEventBean);
            everyEventResponseBean.setHmEventDisplayDateBean(hmEventDisplayDate);
        }
        return everyEventResponseBean;
    }
}
