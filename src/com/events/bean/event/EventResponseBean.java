package com.events.bean.event;

import com.events.bean.common.FeatureBean;
import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/22/13
 * Time: 1:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventResponseBean {
    private String eventId = Constants.EMPTY;
    private EventBean eventBean = new EventBean();
    private ArrayList<FeatureBean> arrFeatureBean = new ArrayList<FeatureBean>();
    private boolean deleteEvent = false;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }

    public ArrayList<FeatureBean> getArrFeatureBean() {
        return arrFeatureBean;
    }

    public void setArrFeatureBean(ArrayList<FeatureBean> arrFeatureBean) {
        this.arrFeatureBean = arrFeatureBean;
    }

    public boolean isDeleteEvent() {
        return deleteEvent;
    }

    public void setDeleteEvent(boolean deleteEvent) {
        this.deleteEvent = deleteEvent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventResponseBean{");
        sb.append("eventId='").append(eventId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
