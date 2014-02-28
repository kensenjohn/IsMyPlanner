package com.events.event.website;

import com.events.bean.event.website.EventContactUsBean;
import com.events.bean.event.website.EventContactUsRequest;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.BuildEventContactUsData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/28/14
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventContactUs {
    public EventContactUsBean saveEventContactUs(EventContactUsRequest eventContactUsRequest) {
        EventContactUsBean eventContactUsBean = new EventContactUsBean();
        if(eventContactUsRequest!=null){
            if(Utility.isNullOrEmpty(eventContactUsRequest.getEventContactUsId())) {
                eventContactUsBean = createEventContactUs(eventContactUsRequest);
            } else {
                eventContactUsBean = updateEventContactUs(eventContactUsRequest);
            }
        }
        return eventContactUsBean;
    }

    public EventContactUsBean createEventContactUs(EventContactUsRequest eventContactUsRequest) {
        EventContactUsBean eventContactUsBean = new EventContactUsBean();
        if(eventContactUsRequest!=null){
            eventContactUsBean = generateEventContactUsBean(eventContactUsRequest);
            eventContactUsBean.setEventContactUsId( Utility.getNewGuid());
            if(eventContactUsBean!=null && !Utility.isNullOrEmpty(eventContactUsBean.getEventContactUsId()) && !Utility.isNullOrEmpty(eventContactUsBean.getEventWebsiteId())) {
                BuildEventContactUsData buildEventContactUsData = new BuildEventContactUsData();
                Integer numOfRowsInserted = buildEventContactUsData.insertEventContactUs( eventContactUsBean );
                if(numOfRowsInserted<=0){
                    eventContactUsBean = new EventContactUsBean();
                }
            }
        }
        return eventContactUsBean;
    }

    public EventContactUsBean updateEventContactUs(EventContactUsRequest eventContactUsRequest) {
        EventContactUsBean eventContactUsBean = new EventContactUsBean();
        if(eventContactUsRequest!=null){
            eventContactUsBean = generateEventContactUsBean(eventContactUsRequest);
            if(eventContactUsBean!=null && !Utility.isNullOrEmpty(eventContactUsBean.getEventContactUsId()) ) {
                BuildEventContactUsData buildEventContactUsData = new BuildEventContactUsData();
                Integer numOfRowsUpdated = buildEventContactUsData.updateEventContactUs( eventContactUsBean );
                if(numOfRowsUpdated<=0){
                    eventContactUsBean = new EventContactUsBean();
                }
            }
        }
        return eventContactUsBean;
    }

    public boolean  deleteEventContactUs(EventContactUsRequest eventContactUsRequest) {
        boolean isSuccess = false;
        if(eventContactUsRequest!=null && !Utility.isNullOrEmpty(eventContactUsRequest.getEventContactUsId())){
            BuildEventContactUsData buildEventContactUsData = new BuildEventContactUsData();
            Integer numOfRowsDeleted = buildEventContactUsData.deleteEventContactUs(eventContactUsRequest);
            if(numOfRowsDeleted > 0 ){
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private EventContactUsBean generateEventContactUsBean(EventContactUsRequest eventContactUsRequest) {
        EventContactUsBean eventContactUsBean = new EventContactUsBean();
        if(eventContactUsRequest!=null){
            eventContactUsBean.setEventContactUsId(ParseUtil.checkNull(eventContactUsRequest.getEventContactUsId()));
            eventContactUsBean.setEventWebsiteId(ParseUtil.checkNull(eventContactUsRequest.getEventWebsiteId()));
            eventContactUsBean.setPhone(ParseUtil.checkNull(eventContactUsRequest.getPhone()) );
            eventContactUsBean.setName( ParseUtil.checkNull(eventContactUsRequest.getName())  );
            eventContactUsBean.setEmail(ParseUtil.checkNull(eventContactUsRequest.getEmail()));
        }
        return eventContactUsBean;
    }
}
