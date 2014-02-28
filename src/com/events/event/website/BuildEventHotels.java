package com.events.event.website;

import com.events.bean.event.website.EventHotelRequest;
import com.events.bean.event.website.EventHotelsBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.BuildEventHotelsData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventHotels {
    public EventHotelsBean saveEventHotel(EventHotelRequest eventHotelRequest) {
        EventHotelsBean eventHotelBean = new EventHotelsBean();
        if(eventHotelRequest!=null){
            if(Utility.isNullOrEmpty(eventHotelRequest.getEventHotelId())) {
                eventHotelBean = createEventHotel(eventHotelRequest);
            } else {
                eventHotelBean = updateEventHotel(eventHotelRequest);
            }
        }
        return eventHotelBean;
    }

    public EventHotelsBean createEventHotel(EventHotelRequest eventHotelRequest) {
        EventHotelsBean eventHotelBean = new EventHotelsBean();
        if(eventHotelRequest!=null){
            eventHotelBean = generateEventHotelsBean(eventHotelRequest);
            eventHotelBean.setEventHotelId( Utility.getNewGuid());
            if(eventHotelBean!=null && !Utility.isNullOrEmpty(eventHotelBean.getEventHotelId()) && !Utility.isNullOrEmpty(eventHotelBean.getEventWebsiteId())) {
                BuildEventHotelsData buildEventHotelsData = new BuildEventHotelsData();
                Integer numOfRowsInserted = buildEventHotelsData.insertEventHotel( eventHotelBean );
                if(numOfRowsInserted<=0){
                    eventHotelBean = new EventHotelsBean();
                }
            }
        }
        return eventHotelBean;
    }

    public EventHotelsBean updateEventHotel(EventHotelRequest eventHotelRequest) {
        EventHotelsBean eventHotelBean = new EventHotelsBean();
        if(eventHotelRequest!=null){
            eventHotelBean = generateEventHotelsBean(eventHotelRequest);
            if(eventHotelBean!=null && !Utility.isNullOrEmpty(eventHotelBean.getEventHotelId()) ) {
                BuildEventHotelsData buildEventHotelsData = new BuildEventHotelsData();
                Integer numOfRowsUpdated = buildEventHotelsData.updateEventHotel( eventHotelBean );
                if(numOfRowsUpdated<=0){
                    eventHotelBean = new EventHotelsBean();
                }
            }
        }
        return eventHotelBean;
    }

    public boolean  deleteEventHotel(EventHotelRequest eventHotelRequest) {
        boolean isSuccess = false;
        if(eventHotelRequest!=null && !Utility.isNullOrEmpty(eventHotelRequest.getEventHotelId())){
            BuildEventHotelsData buildEventHotelsData = new BuildEventHotelsData();
            Integer numOfRowsDeleted = buildEventHotelsData.deleteEventHotel(eventHotelRequest);
            if(numOfRowsDeleted > 0 ){
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private EventHotelsBean generateEventHotelsBean(EventHotelRequest eventHotelRequest) {
        EventHotelsBean eventHotelBean = new EventHotelsBean();
        if(eventHotelRequest!=null){
            eventHotelBean.setEventHotelId(ParseUtil.checkNull(eventHotelRequest.getEventHotelId()));
            eventHotelBean.setEventWebsiteId(ParseUtil.checkNull(eventHotelRequest.getEventWebsiteId()));
            eventHotelBean.setPhone(ParseUtil.checkNull(eventHotelRequest.getPhone()) );
            eventHotelBean.setName( ParseUtil.checkNull(eventHotelRequest.getName())  );
            eventHotelBean.setAddress(ParseUtil.checkNull(eventHotelRequest.getAddress()));
            eventHotelBean.setUrl(ParseUtil.checkNull(eventHotelRequest.getUrl()));
            eventHotelBean.setInstructions(ParseUtil.checkNull(eventHotelRequest.getInstructions()));
        }
        return eventHotelBean;
    }
}
