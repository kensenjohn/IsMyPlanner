package com.events.event.website;

import com.events.bean.event.website.EventRegistryBean;
import com.events.bean.event.website.EventRegistryRequest;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.BuildEventRegistryData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventRegistry {
    public EventRegistryBean saveEventRegistry(EventRegistryRequest eventHotelRequest) {
        EventRegistryBean eventRegistryBean = new EventRegistryBean();
        if(eventHotelRequest!=null){
            if(Utility.isNullOrEmpty(eventHotelRequest.getEventRegistryId())) {
                eventRegistryBean = createEventRegistry(eventHotelRequest);
            } else {
                eventRegistryBean = updateEventRegistry(eventHotelRequest);
            }
        }
        return eventRegistryBean;
    }

    public EventRegistryBean createEventRegistry(EventRegistryRequest eventHotelRequest) {
        EventRegistryBean eventRegistryBean = new EventRegistryBean();
        if(eventHotelRequest!=null){
            eventRegistryBean = generateEventRegistryBean(eventHotelRequest);
            eventRegistryBean.setEventRegistryId( Utility.getNewGuid());
            if(eventRegistryBean!=null && !Utility.isNullOrEmpty(eventRegistryBean.getEventRegistryId()) && !Utility.isNullOrEmpty(eventRegistryBean.getEventWebsiteId())) {
                BuildEventRegistryData buildEventRegistrysData = new BuildEventRegistryData();
                Integer numOfRowsInserted = buildEventRegistrysData.insertEventRegistry( eventRegistryBean );
                if(numOfRowsInserted<=0){
                    eventRegistryBean = new EventRegistryBean();
                }
            }
        }
        return eventRegistryBean;
    }

    public EventRegistryBean updateEventRegistry(EventRegistryRequest eventHotelRequest) {
        EventRegistryBean eventRegistryBean = new EventRegistryBean();
        if(eventHotelRequest!=null){
            eventRegistryBean = generateEventRegistryBean(eventHotelRequest);
            if(eventRegistryBean!=null && !Utility.isNullOrEmpty(eventRegistryBean.getEventRegistryId()) ) {
                BuildEventRegistryData buildEventRegistrysData = new BuildEventRegistryData();
                Integer numOfRowsUpdated = buildEventRegistrysData.updateEventRegistry( eventRegistryBean );
                if(numOfRowsUpdated<=0){
                    eventRegistryBean = new EventRegistryBean();
                }
            }
        }
        return eventRegistryBean;
    }

    public boolean  deleteEventRegistry(EventRegistryRequest eventHotelRequest) {
        boolean isSuccess = false;
        if(eventHotelRequest!=null && !Utility.isNullOrEmpty(eventHotelRequest.getEventRegistryId())){
            BuildEventRegistryData buildEventRegistrysData = new BuildEventRegistryData();
            Integer numOfRowsDeleted = buildEventRegistrysData.deleteEventRegistry(eventHotelRequest);
            if(numOfRowsDeleted > 0 ){
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private EventRegistryBean generateEventRegistryBean(EventRegistryRequest eventHotelRequest) {
        EventRegistryBean eventRegistryBean = new EventRegistryBean();
        if(eventHotelRequest!=null){
            eventRegistryBean.setEventRegistryId(ParseUtil.checkNull(eventHotelRequest.getEventRegistryId()));
            eventRegistryBean.setEventWebsiteId(ParseUtil.checkNull(eventHotelRequest.getEventWebsiteId()));
            eventRegistryBean.setName( ParseUtil.checkNull(eventHotelRequest.getName())  );
            eventRegistryBean.setUrl(ParseUtil.checkNull(eventHotelRequest.getUrl()));
            eventRegistryBean.setInstructions(ParseUtil.checkNull(eventHotelRequest.getInstructions()));
        }
        return eventRegistryBean;
    }
}
