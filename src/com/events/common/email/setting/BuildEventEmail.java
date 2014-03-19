package com.events.common.email.setting;

import com.events.bean.common.email.EventEmailBean;
import com.events.bean.common.email.EventEmailFeatureBean;
import com.events.bean.common.email.EventEmailRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
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
    private Configuration emailerConfig = Configuration.getInstance(Constants.EMAILER_PROP);
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

                EventEmailFeature eventEmailFeature = new EventEmailFeature();
                Integer iNumOfSendRuleRow = eventEmailFeature.setFeatureValue(sendEmailRuleFeatureBean);

                if(eventEmailRequestBean!=null) {
                    EventEmailFeatureBean userActionFeatureBean = new EventEmailFeatureBean();
                    userActionFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                    userActionFeatureBean.setFeatureType(Constants.EventEmailFeatureType.action );
                    userActionFeatureBean.setValue( eventEmailRequestBean.getUserAction().toString() );
                    eventEmailFeature.setFeatureValue( userActionFeatureBean );
                }

                EventEmailFeatureBean emailSendDayFeatureBean = new EventEmailFeatureBean();
                emailSendDayFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                emailSendDayFeatureBean.setFeatureType(Constants.EventEmailFeatureType.email_send_day );
                emailSendDayFeatureBean.setValue( eventEmailRequestBean.getEmailSendDay());
                if(!Utility.isNullOrEmpty(eventEmailRequestBean.getEmailSendDay()) && eventEmailRequestBean.isEmailScheduleEnabled() ) {
                    eventEmailFeature.setFeatureValue(emailSendDayFeatureBean);
                } else {
                    eventEmailFeature.removeFeatureValue(emailSendDayFeatureBean);
                }

                EventEmailFeatureBean emailSendTimeFeatureBean = new EventEmailFeatureBean();
                emailSendTimeFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                emailSendTimeFeatureBean.setFeatureType(Constants.EventEmailFeatureType.email_send_time );
                emailSendTimeFeatureBean.setValue( eventEmailRequestBean.getEmailSendTime());
                if(!Utility.isNullOrEmpty(eventEmailRequestBean.getEmailSendTime())  && eventEmailRequestBean.isEmailScheduleEnabled() ) {
                    eventEmailFeature.setFeatureValue(emailSendTimeFeatureBean);
                } else {
                    eventEmailFeature.removeFeatureValue(emailSendTimeFeatureBean);
                }

                EventEmailFeatureBean emailSendTimeZoneFeatureBean = new EventEmailFeatureBean();
                emailSendTimeZoneFeatureBean.setEventEmailId(eventEmailBean.getEventEmailId());
                emailSendTimeZoneFeatureBean.setFeatureType(Constants.EventEmailFeatureType.email_send_timezone );
                emailSendTimeZoneFeatureBean.setValue( eventEmailRequestBean.getEmailSendTimeZone());
                if(!Utility.isNullOrEmpty(eventEmailRequestBean.getEmailSendTimeZone())  && eventEmailRequestBean.isEmailScheduleEnabled()  ) {
                    eventEmailFeature.setFeatureValue(emailSendTimeZoneFeatureBean);
                } else {
                    eventEmailFeature.removeFeatureValue(emailSendTimeZoneFeatureBean);
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
            setFromAddress(tmpEventEmailBean);
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
            setFromAddress(tmpEventEmailBean);
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

    private void setFromAddress(EventEmailBean eventEmailBean){
        if(eventEmailBean!=null && Utility.isNullOrEmpty(eventEmailBean.getFromAddressEmail() )) {
            String sFromAddress = ParseUtil.checkNull(emailerConfig.get(Constants.PROP_FROM_EMAIL_ADDRESS));
            eventEmailBean.setFromAddressEmail( sFromAddress );
            eventEmailBean.setFromAddressName( sFromAddress );
        }
    }
}
