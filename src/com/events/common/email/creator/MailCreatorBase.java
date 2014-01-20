package com.events.common.email.creator;

import com.events.bean.common.email.EventEmailBean;
import com.events.bean.common.email.EventEmailRequestBean;
import com.events.bean.common.email.EventEmailResponseBean;
import com.events.common.Utility;
import com.events.common.email.setting.AccessEventEmail;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 7:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailCreatorBase {
    protected EventEmailBean getEventEmailBean(String sEventEmailId){
        EventEmailBean eventEmailBean = new EventEmailBean();
        if( !Utility.isNullOrEmpty(sEventEmailId) ) {
            EventEmailRequestBean eventEmailRequestBean = new EventEmailRequestBean();
            eventEmailRequestBean.setEventEmailId(sEventEmailId);

            AccessEventEmail accessEventEmail = new AccessEventEmail();
            EventEmailResponseBean eventEmailResponseBean = accessEventEmail.getEventEmail( eventEmailRequestBean );

            if(eventEmailResponseBean!=null && eventEmailResponseBean.getEventEmailBean()!=null) {
                eventEmailBean = eventEmailResponseBean.getEventEmailBean();
            }
        }
        return eventEmailBean;
    }
}
