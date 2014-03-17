package com.events.event.website;

import com.events.bean.event.website.EventWebsiteBean;
import com.events.bean.event.website.EventWebsiteRequestBean;
import com.events.common.Utility;
import com.events.data.event.website.AccessEventWebsiteData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventWebsite {
    public EventWebsiteBean getEventWebsite(EventWebsiteRequestBean eventWebsiteRequestBean) {
        EventWebsiteBean eventWebsiteBean = new EventWebsiteBean();
        if(eventWebsiteRequestBean!=null && !Utility.isNullOrEmpty(eventWebsiteRequestBean.getEventId())) {
            AccessEventWebsiteData accessEventWebsiteData = new AccessEventWebsiteData();
            eventWebsiteBean = accessEventWebsiteData.getEventWebsite( eventWebsiteRequestBean );
        }
        return eventWebsiteBean;
    }

    public ArrayList<EventWebsiteBean> getEventWebsiteByUrlUniqueName(EventWebsiteRequestBean eventWebsiteRequestBean){
        ArrayList<EventWebsiteBean> arrEventWebsiteBean = new ArrayList<EventWebsiteBean>();
        if(eventWebsiteRequestBean!=null && !Utility.isNullOrEmpty(eventWebsiteRequestBean.getEventId())) {
            AccessEventWebsiteData accessEventWebsiteData = new AccessEventWebsiteData();
            arrEventWebsiteBean = accessEventWebsiteData.getEventWebsiteByUniqueURL(eventWebsiteRequestBean);
        }
        return arrEventWebsiteBean;
    }
}
