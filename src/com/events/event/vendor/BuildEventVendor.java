package com.events.event.vendor;

import com.events.bean.event.vendor.EventVendorBean;
import com.events.bean.event.vendor.EventVendorFeatureBean;
import com.events.bean.event.vendor.EventVendorRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.vendor.BuildEventVendorData;


/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventVendor {
    public EventVendorBean saveEventVendorAssignment(EventVendorRequestBean eventVendorRequestBean){
        EventVendorBean eventVendorBean = new EventVendorBean();
        if(eventVendorRequestBean!=null && !Utility.isNullOrEmpty(eventVendorRequestBean.getEventId())
                && !Utility.isNullOrEmpty(eventVendorRequestBean.getVendorId()) )   {

            eventVendorBean = saveEventVendor(eventVendorRequestBean );
            if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId())) {
                Integer iNumOfRows = saveEventVendorUserAction(eventVendorBean , Constants.EVENT_VENDOR_USER_ACTION.is_assigned, ParseUtil.checkNull(eventVendorRequestBean.getUserId()) );
                if(iNumOfRows<=0){
                    eventVendorBean = new EventVendorBean();
                }
            }
        }
        return eventVendorBean;
    }

    public boolean deleteEventVendorAssignment(EventVendorRequestBean eventVendorRequestBean){
        boolean isSuccess = false;
        if(eventVendorRequestBean!=null && !Utility.isNullOrEmpty(eventVendorRequestBean.getEventId())
                && !Utility.isNullOrEmpty(eventVendorRequestBean.getVendorId()) )   {

            EventVendorBean eventVendorBean = deleteEventVendor(eventVendorRequestBean);
            if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId())) {
                isSuccess = true;
                deleteEventVendorUserAction( eventVendorBean , Constants.EVENT_VENDOR_USER_ACTION.is_assigned );
            }
        }
        return isSuccess;
    }

    public EventVendorBean saveEventVendorRecommendation(EventVendorRequestBean eventVendorRequestBean){
        EventVendorBean eventVendorBean = new EventVendorBean();
        if(eventVendorRequestBean!=null && !Utility.isNullOrEmpty(eventVendorRequestBean.getEventId())
                && !Utility.isNullOrEmpty(eventVendorRequestBean.getVendorId()) )   {

            eventVendorBean = saveEventVendor(eventVendorRequestBean );
            if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId())) {
                Integer iNumOfRows = saveEventVendorUserAction(eventVendorBean , Constants.EVENT_VENDOR_USER_ACTION.is_recommended, ParseUtil.checkNull(eventVendorRequestBean.getUserId())  );
                if(iNumOfRows<=0){
                    eventVendorBean = new EventVendorBean();
                }
            }
        }
        return eventVendorBean;
    }

    public boolean deleteEventVendorRecommendation(EventVendorRequestBean eventVendorRequestBean){
        boolean isSuccess = false;
        if(eventVendorRequestBean!=null && !Utility.isNullOrEmpty(eventVendorRequestBean.getEventId())
                && !Utility.isNullOrEmpty(eventVendorRequestBean.getVendorId()) )   {

            EventVendorBean eventVendorBean = deleteEventVendor(eventVendorRequestBean);
            if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId())) {
                isSuccess = true;
                deleteEventVendorUserAction( eventVendorBean , Constants.EVENT_VENDOR_USER_ACTION.is_recommended );
            }
        }
        return isSuccess;
    }

    private EventVendorBean deleteEventVendor( EventVendorRequestBean eventVendorRequestBean ) {
        EventVendorBean eventVendorBean = new EventVendorBean();
        if(eventVendorRequestBean!=null && !Utility.isNullOrEmpty(eventVendorRequestBean.getEventId())
                && !Utility.isNullOrEmpty(eventVendorRequestBean.getVendorId()) )   {
            AccessEventVendor accessEventVendor = new AccessEventVendor();
            eventVendorBean = accessEventVendor.getEventVendor( eventVendorRequestBean );
            if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId())){
                eventVendorRequestBean.setEventVendorId( eventVendorBean.getEventVendorId() );

                BuildEventVendorData buildEventVendorDat = new BuildEventVendorData();
                Integer iNumOfRows =  buildEventVendorDat.deleteEventVendor( eventVendorRequestBean);
                if(iNumOfRows<=0){
                    eventVendorBean = new EventVendorBean();
                }

            }
        }
        return eventVendorBean;
    }

    private EventVendorBean saveEventVendor( EventVendorRequestBean eventVendorRequestBean ) {
        EventVendorBean eventVendorBean = new EventVendorBean();
        if(eventVendorRequestBean!=null && !Utility.isNullOrEmpty(eventVendorRequestBean.getEventId())
                && !Utility.isNullOrEmpty(eventVendorRequestBean.getVendorId()) )   {
            AccessEventVendor accessEventVendor = new AccessEventVendor();
            eventVendorBean = accessEventVendor.getEventVendor( eventVendorRequestBean );
            if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId())){
                eventVendorRequestBean.setEventVendorId( eventVendorBean.getEventVendorId() );
            } else {
                eventVendorRequestBean.setEventVendorId( Utility.getNewGuid());
                BuildEventVendorData buildEventVendorDat = new BuildEventVendorData();
                Integer iNumOfRows = buildEventVendorDat.insertEventVendor( eventVendorRequestBean ) ;
                if(iNumOfRows>0){
                    eventVendorBean.setEventVendorId( eventVendorRequestBean.getEventVendorId() );
                }
            }
        }
        return eventVendorBean;
    }

    private Integer saveEventVendorUserAction(EventVendorBean eventVendorBean ,  Constants.EVENT_VENDOR_USER_ACTION eventVendorUserAction, String sUserId) {
        Integer iNumOfRows = 0;
        if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId()) &&
                eventVendorUserAction!=null ) {
            EventVendorFeatureBean eventVendorFeatureBean = new EventVendorFeatureBean();
            eventVendorFeatureBean.setEventVendorId( eventVendorBean.getEventVendorId() );
            eventVendorFeatureBean.setFeatureType(Constants.EVENT_VENDOR_FEATURETYPE.current_user_action);
            eventVendorFeatureBean.setValue( eventVendorUserAction.toString() );
            eventVendorFeatureBean.setUserId( ParseUtil.checkNull(sUserId) );

            EventVendorFeature eventVendorFeature = new EventVendorFeature();
            iNumOfRows = eventVendorFeature.setFeatureValue(eventVendorFeatureBean );
        }
        return iNumOfRows;
    }

    private Integer deleteEventVendorUserAction(EventVendorBean eventVendorBean ,  Constants.EVENT_VENDOR_USER_ACTION eventVendorUserAction ) {

        Integer iNumOfRows = 0;
        if(eventVendorBean!=null && !Utility.isNullOrEmpty(eventVendorBean.getEventVendorId()) &&
                eventVendorUserAction!=null ) {

            EventVendorFeatureBean eventVendorFeatureBean = new EventVendorFeatureBean();
            eventVendorFeatureBean.setEventVendorId( eventVendorBean.getEventVendorId() );
            eventVendorFeatureBean.setFeatureType(Constants.EVENT_VENDOR_FEATURETYPE.current_user_action);

            EventVendorFeature eventVendorFeature = new EventVendorFeature();
            iNumOfRows = eventVendorFeature.deleteFeatureValue(eventVendorFeatureBean);


        }
        return iNumOfRows;
    }
}
