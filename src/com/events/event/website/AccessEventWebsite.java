package com.events.event.website;

import com.events.bean.event.website.EventWebsiteBean;
import com.events.bean.event.website.EventWebsiteRequestBean;
import com.events.common.Utility;
import com.events.data.event.website.AccessEventWebsiteData;

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
}
