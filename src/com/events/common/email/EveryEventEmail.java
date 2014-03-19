package com.events.common.email;

import com.events.bean.common.email.*;
import com.events.bean.event.EveryEventResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.email.setting.AccessEventEmail;
import com.events.common.email.setting.EventEmailFeature;
import com.events.data.email.EventEmailData;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventEmail {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EveryEventEmailResponseBean getEveryEventEmail(EveryEventEmailRequestBean everyEventEmailRequestBean) {
        EveryEventEmailResponseBean everyEventEmailResponseBean = new EveryEventEmailResponseBean();
        if(everyEventEmailRequestBean!=null && !Utility.isNullOrEmpty(everyEventEmailRequestBean.getEventId())) {
            EventEmailData eventEmailData = new EventEmailData();
            ArrayList<EventEmailBean> arrEventEmailBean = eventEmailData.getAllEventEmails(everyEventEmailRequestBean);
            if(arrEventEmailBean!=null && !arrEventEmailBean.isEmpty()) {
                HashMap<String,ArrayList<EventEmailFeatureBean> > hmEveryEventEmailFeatureBean = getFeatures( arrEventEmailBean );
                HashMap<String,EmailSchedulerBean> hmEveryEmailSchdeulerBean =  getEmailSchedulerBean(arrEventEmailBean);

                everyEventEmailResponseBean.setArrEventEmailBean(arrEventEmailBean);
                everyEventEmailResponseBean.setNumOfEventEmails(arrEventEmailBean.size());
                everyEventEmailResponseBean.setHmEveryEventEmailFeatureBean(hmEveryEventEmailFeatureBean);
                everyEventEmailResponseBean.setHmEveryEmailSchdeulerBean(hmEveryEmailSchdeulerBean);
            }
        }
        return everyEventEmailResponseBean;
    }

    public HashMap<String,ArrayList<EventEmailFeatureBean> >  getFeatures(ArrayList<EventEmailBean> arrEventEmailBean) {
        HashMap<String,ArrayList<EventEmailFeatureBean> > hmEveryEventEmailFeatureBean = new HashMap<String, ArrayList<EventEmailFeatureBean>>();
        if(arrEventEmailBean!=null && !arrEventEmailBean.isEmpty()) {

            AccessEventEmail accessEventEmail = new AccessEventEmail();

            ArrayList<EventEmailFeatureBean> arrEventEmailFeatureBean = new ArrayList<EventEmailFeatureBean>();
            arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.send_email_rule) );
            arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.email_send_day) );
            arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.email_send_time) );
            arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.email_send_timezone) );
            arrEventEmailFeatureBean.add( accessEventEmail.getEventEmailFeatureTypeBean(Constants.EventEmailFeatureType.action) );

            for(EventEmailBean eventEmailBean : arrEventEmailBean) {
                String sEventEmailId = ParseUtil.checkNull(eventEmailBean.getEventEmailId());
                EventEmailFeature eventEmailFeature = new EventEmailFeature();
                ArrayList<EventEmailFeatureBean> arrMultipleFeatureBean = eventEmailFeature.getMultipleFeatures(arrEventEmailFeatureBean, sEventEmailId );

                if(arrMultipleFeatureBean!=null && !arrMultipleFeatureBean.isEmpty()) {
                    hmEveryEventEmailFeatureBean.put(sEventEmailId,arrMultipleFeatureBean );
                }
            }
        }
        return hmEveryEventEmailFeatureBean;
    }

    public HashMap<String,EmailSchedulerBean>  getEmailSchedulerBean(ArrayList<EventEmailBean> arrEventEmailBean) {
        HashMap<String,EmailSchedulerBean> hmEveryEmailSchdeulerBean = new HashMap<String, EmailSchedulerBean>();
        if(arrEventEmailBean!=null && !arrEventEmailBean.isEmpty()) {
            AccessEventEmail accessEventEmail = new AccessEventEmail();
            for(EventEmailBean eventEmailBean : arrEventEmailBean) {
                EmailSchedulerBean emailSchedulerBean = accessEventEmail.getEventEmailSchedule(eventEmailBean);

                hmEveryEmailSchdeulerBean.put(eventEmailBean.getEventEmailId(),emailSchedulerBean );
            }
        }
        return hmEveryEmailSchdeulerBean;
    }

    public JSONObject getEveryEventEmailJson(EveryEventEmailResponseBean everyEventEmailResponseBean) {
        JSONObject jsonEveryEventEmailObject = new JSONObject();
        if(everyEventEmailResponseBean!=null && everyEventEmailResponseBean.getNumOfEventEmails() >0 ){

            ArrayList<EventEmailBean> arrEventEmailBean = everyEventEmailResponseBean.getArrEventEmailBean();
            if(arrEventEmailBean!=null && !arrEventEmailBean.isEmpty()) {
                HashMap<String,ArrayList<EventEmailFeatureBean> > hmEveryEventEmailFeatureBean = everyEventEmailResponseBean.getHmEveryEventEmailFeatureBean();
                HashMap<String,EmailSchedulerBean> hmEveryEmailSchdeulerBean = everyEventEmailResponseBean.getHmEveryEmailSchdeulerBean();
                Integer iTrackNumOfEventEmails = 0;
                for(EventEmailBean eventEmailBean : arrEventEmailBean) {
                    EveryEventEmailBean everyEventEmailBean = new EveryEventEmailBean();
                    everyEventEmailBean.setEventEmailId( eventEmailBean.getEventEmailId() );
                    everyEventEmailBean.setEventId( eventEmailBean.getEventId() );
                    everyEventEmailBean.setName( eventEmailBean.getSubject() );
                    if(hmEveryEventEmailFeatureBean!=null && !hmEveryEventEmailFeatureBean.isEmpty()) {
                        ArrayList<EventEmailFeatureBean> arrEventEmailFeatures = hmEveryEventEmailFeatureBean.get(eventEmailBean.getEventEmailId());

                        if( arrEventEmailFeatures!=null && !arrEventEmailFeatures.isEmpty() ) {
                            String sSendDay =  Constants.EMPTY;
                            String sSendTime =  Constants.EMPTY;
                            String sSendTimeZone =  Constants.EMPTY;
                            String sSendRule =  Constants.EMPTY;
                            for( EventEmailFeatureBean eventEmailFeatureBean : arrEventEmailFeatures )  {

                                String sValue = ParseUtil.checkNull(eventEmailFeatureBean.getValue());
                                String sFeatureName = ParseUtil.checkNull(eventEmailFeatureBean.getFeatureName());
                                if(Constants.EventEmailFeatureType.email_send_day.toString().equalsIgnoreCase(sFeatureName)){
                                    sSendDay = sValue;
                                } else if(Constants.EventEmailFeatureType.email_send_time.toString().equalsIgnoreCase(sFeatureName)) {
                                    sSendTime = sValue;
                                } else if(Constants.EventEmailFeatureType.email_send_timezone.toString().equalsIgnoreCase(sFeatureName)) {
                                    sSendTimeZone = Constants.TIME_ZONE.valueOf(sValue).getTimeZoneDisplay();
                                } else if(Constants.EventEmailFeatureType.send_email_rule.toString().equalsIgnoreCase(sFeatureName)) {
                                    sSendRule = Constants.SEND_EMAIL_RULES.valueOf(sValue).getDescription();
                                }
                            }
                            if( !Utility.isNullOrEmpty(sSendDay) &&  !Utility.isNullOrEmpty(sSendTime)){
                                everyEventEmailBean.setSendDate(sSendDay + " " + sSendTime + " " + sSendTimeZone);
                             } else {
                                everyEventEmailBean.setSendDate("Not Scheduled");
                            }


                            everyEventEmailBean.setSendRule(  sSendRule);
                        }
                    }
                    EmailSchedulerBean emailSchedulerBean = hmEveryEmailSchdeulerBean.get(eventEmailBean.getEventEmailId());
                    if(emailSchedulerBean!=null && !Utility.isNullOrEmpty(emailSchedulerBean.getEmailScheduleId()))  {
                        everyEventEmailBean.setStatus( Constants.SCHEDULER_STATUS.valueOf( emailSchedulerBean.getScheduleStatus()).getDescription());
                    }

                    jsonEveryEventEmailObject.put(iTrackNumOfEventEmails.toString(),everyEventEmailBean.toJson());
                    iTrackNumOfEventEmails++;
                }
                jsonEveryEventEmailObject.put("num_of_eventemails" , iTrackNumOfEventEmails );
            }

        }
        return jsonEveryEventEmailObject;
    }
}
