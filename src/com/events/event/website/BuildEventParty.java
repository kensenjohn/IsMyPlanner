package com.events.event.website;

import com.events.bean.event.website.EventPartyBean;
import com.events.bean.event.website.EventPartyRequest;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.BuildEventPartyData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventParty {
    public EventPartyBean saveEventParty(EventPartyRequest eventPartyRequest) {
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null){
            if(Utility.isNullOrEmpty(eventPartyRequest.getEventPartyId())) {
                eventPartyBean = createEventParty(eventPartyRequest);
            } else {
                eventPartyBean = updateEventParty(eventPartyRequest);
            }
        }

        if(eventPartyBean!=null && !Utility.isNullOrEmpty(eventPartyBean.getEventPartyId())) {
            eventPartyRequest.setEventPartyId( eventPartyBean.getEventPartyId() );
            BuildSocialMedia buildSocialMedia = new BuildSocialMedia();
            buildSocialMedia.saveSocialMedia(eventPartyRequest);
        }
        return eventPartyBean;
    }

    public EventPartyBean createEventParty(EventPartyRequest eventPartyRequest) {
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null){
            eventPartyBean = generateEventPartyBean(eventPartyRequest);
            eventPartyBean.setEventPartyId( Utility.getNewGuid());
            if(eventPartyBean!=null && !Utility.isNullOrEmpty(eventPartyBean.getEventPartyId()) && !Utility.isNullOrEmpty(eventPartyBean.getEventWebsiteId())) {
                BuildEventPartyData buildEventPartyData = new BuildEventPartyData();
                Integer numOfRowsInserted = buildEventPartyData.insertEventParty( eventPartyBean );
                if(numOfRowsInserted<=0){
                    eventPartyBean = new EventPartyBean();
                }
            }
        }
        return eventPartyBean;
    }

    public EventPartyBean updateEventParty(EventPartyRequest eventPartyRequest) {
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null){
            eventPartyBean = generateEventPartyBean(eventPartyRequest);
            if(eventPartyBean!=null && !Utility.isNullOrEmpty(eventPartyBean.getEventPartyId()) ) {
                BuildEventPartyData buildEventPartyData = new BuildEventPartyData();
                Integer numOfRowsUpdated = buildEventPartyData.updateEventParty( eventPartyBean );
                if(numOfRowsUpdated<=0){
                    eventPartyBean = new EventPartyBean();
                }
            }
        }
        return eventPartyBean;
    }

    public boolean  deleteEventParty(EventPartyRequest eventPartyRequest) {
        boolean isSuccess = false;
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null){
            eventPartyBean = generateEventPartyBean(eventPartyRequest);
            eventPartyBean.setEventPartyId( Utility.getNewGuid());
        }
        return isSuccess;
    }

    private EventPartyBean generateEventPartyBean(EventPartyRequest eventPartyRequest) {
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null){
            eventPartyBean.setEventPartyId(ParseUtil.checkNull(eventPartyRequest.getEventPartyId()));
            eventPartyBean.setEventWebsiteId(ParseUtil.checkNull(eventPartyRequest.getEventWebsiteId()));
            eventPartyBean.setEventPartyType(Constants.EVENT_PARTY_TYPE.valueOf(ParseUtil.checkNull(eventPartyRequest.getEventPartyTypeName())));
            eventPartyBean.setName( ParseUtil.checkNull(eventPartyRequest.getName())  );
            eventPartyBean.setDescription(ParseUtil.checkNull(eventPartyRequest.getDescription()));
        }
        return eventPartyBean;
    }
}
