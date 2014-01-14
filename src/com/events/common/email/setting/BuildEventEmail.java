package com.events.common.email.setting;

import com.events.bean.common.email.EventEmailBean;
import com.events.bean.common.email.EventEmailFeatureBean;
import com.events.bean.common.email.EventEmailRequestBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.feature.FeatureType;
import com.events.data.email.EventEmailData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/10/14
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventEmail {
    public EventEmailBean saveEventEmail(EventEmailRequestBean eventEmailRequestBean) {
        EventEmailBean eventEmailBean = new EventEmailBean();
        if(eventEmailRequestBean!=null && eventEmailRequestBean.getEventEmailBean()!=null) {
            eventEmailBean = eventEmailRequestBean.getEventEmailBean();
            if(eventEmailBean!=null) {
                if(Utility.isNullOrEmpty(eventEmailBean.getEventEmailId())) {
                    eventEmailBean = createEventEmail(eventEmailRequestBean);
                } else {
                    eventEmailBean = updateEventEmail(eventEmailRequestBean);
                }
            }

            if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId())) {
                EventEmailFeatureBean sendEmailRuleFeatureBean = new EventEmailFeatureBean();
                sendEmailRuleFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                sendEmailRuleFeatureBean.setFeatureType(Constants.EventEmailFeatureType.send_email_rule );
                sendEmailRuleFeatureBean.setValue( eventEmailRequestBean.getSendEmailRules().getRule());

                EventEmailFeatureBean emailStatusFeatureBean = new EventEmailFeatureBean();
                emailStatusFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                emailStatusFeatureBean.setFeatureType(Constants.EventEmailFeatureType.email_send_status );
                emailStatusFeatureBean.setValue( "Scheduled");

                EventEmailFeature eventEmailFeature = new EventEmailFeature();
                Integer iNumOfSendRuleRow = eventEmailFeature.setFeatureValue(sendEmailRuleFeatureBean);
                eventEmailFeature.setFeatureValue(emailStatusFeatureBean);


                if(!Utility.isNullOrEmpty(eventEmailRequestBean.getEmailSendDay())) {
                    EventEmailFeatureBean emailSendDayFeatureBean = new EventEmailFeatureBean();
                    emailSendDayFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                    emailSendDayFeatureBean.setFeatureType(Constants.EventEmailFeatureType.email_send_day );
                    emailSendDayFeatureBean.setValue( eventEmailRequestBean.getEmailSendDay());
                    eventEmailFeature.setFeatureValue(emailSendDayFeatureBean);
                }

                if(!Utility.isNullOrEmpty(eventEmailRequestBean.getEmailSendTime())) {
                    EventEmailFeatureBean emailSendTimeFeatureBean = new EventEmailFeatureBean();
                    emailSendTimeFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                    emailSendTimeFeatureBean.setFeatureType(Constants.EventEmailFeatureType.email_send_time );
                    emailSendTimeFeatureBean.setValue( eventEmailRequestBean.getEmailSendTime());
                    eventEmailFeature.setFeatureValue(emailSendTimeFeatureBean);
                }

                if(!Utility.isNullOrEmpty(eventEmailRequestBean.getEmailSendTimeZone())) {
                    EventEmailFeatureBean emailSendTimeZoneFeatureBean = new EventEmailFeatureBean();
                    emailSendTimeZoneFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                    emailSendTimeZoneFeatureBean.setFeatureType(Constants.EventEmailFeatureType.email_send_timezone );
                    emailSendTimeZoneFeatureBean.setValue( eventEmailRequestBean.getEmailSendTimeZone());
                    eventEmailFeature.setFeatureValue(emailSendTimeZoneFeatureBean);
                }
            }
        }
        return eventEmailBean;
    }

    public EventEmailBean createEventEmail(EventEmailRequestBean eventEmailRequestBean) {
        EventEmailBean eventEmailBean = new EventEmailBean();
        if(eventEmailRequestBean!=null && eventEmailRequestBean.getEventEmailBean()!=null) {
            EventEmailBean tmpEventEmailBean = eventEmailRequestBean.getEventEmailBean();
            tmpEventEmailBean.setEventEmailId(Utility.getNewGuid());
            EventEmailData eventEmailData = new EventEmailData();
            Integer iNumOfRows = eventEmailData.insertEventEmail(tmpEventEmailBean);
            if(iNumOfRows>0){
                eventEmailBean = tmpEventEmailBean;
            }
        }
        return eventEmailBean;
    }

    public EventEmailBean updateEventEmail(EventEmailRequestBean eventEmailRequestBean) {

        EventEmailBean eventEmailBean = new EventEmailBean();
        if(eventEmailRequestBean!=null && eventEmailRequestBean.getEventEmailBean()!=null) {
            EventEmailBean tmpEventEmailBean = eventEmailRequestBean.getEventEmailBean();
            if(!Utility.isNullOrEmpty(tmpEventEmailBean.getEventEmailId())) {
                EventEmailData eventEmailData = new EventEmailData();
                Integer iNumOfRows = eventEmailData.updateEventEmail(tmpEventEmailBean);
                if(iNumOfRows>0){
                    eventEmailBean = tmpEventEmailBean;
                }
            }
        }
        return eventEmailBean;
    }
}
