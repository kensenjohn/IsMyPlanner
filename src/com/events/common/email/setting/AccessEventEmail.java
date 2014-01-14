package com.events.common.email.setting;

import com.events.bean.common.email.EventEmailBean;
import com.events.bean.common.email.EventEmailFeatureBean;
import com.events.bean.common.email.EventEmailRequestBean;
import com.events.bean.common.email.EventEmailResponseBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.email.EventEmailData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 6:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventEmail {
    public EventEmailResponseBean getEventEmail( EventEmailRequestBean eventEmailRequestBean ) {
        EventEmailResponseBean eventEmailResponseBean = new EventEmailResponseBean();
        if(eventEmailResponseBean!=null && !Utility.isNullOrEmpty(eventEmailRequestBean.getEventEmailId())) {
            EventEmailData eventEmailData = new EventEmailData();
            EventEmailBean eventEmailBean = eventEmailData.getEventEmail(eventEmailRequestBean);
            eventEmailResponseBean.setEventEmailBean(eventEmailBean);
        }
        return eventEmailResponseBean;
    }


    public EventEmailFeatureBean getEventEmailFeatureTypeBean( Constants.EventEmailFeatureType eventEmailFeatureType) {
        EventEmailFeatureBean eventEmailFeatureBean = new EventEmailFeatureBean();
        eventEmailFeatureBean.setFeatureType(eventEmailFeatureType);
        return eventEmailFeatureBean;
    }
}
