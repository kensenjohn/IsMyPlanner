package com.events.data.event.guest;

import com.events.bean.event.EventBean;
import com.events.bean.event.guest.*;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/2/14
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessGuestData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);


    public ArrayList<EveryEventGuestGroupBean> getEventGuestGroupAndGuestGroup(GuestRequestBean guestRequestBean) {
        ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup = new ArrayList<EveryEventGuestGroupBean>();
        if(guestRequestBean!=null){
            String sQuery = "SELECT * FROM GTEVENTGUESTGROUP EG, GTGUESTGROUP G WHERE EG.FK_EVENTID = ? AND EG.DEL_ROW=0 AND EG.FK_GUESTGROUPID = G.GUESTGROUPID";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getEventId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getEventGuestGroupAndGuestGroup()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EveryEventGuestGroupBean everyEventGuestGroupBean = new EveryEventGuestGroupBean(hmResult);
                    arrEveryEventGuestGroup.add( everyEventGuestGroupBean );
                }
            }
        }
        return arrEveryEventGuestGroup;
    }
    public EventGuestGroupBean getEventGuestGroup(GuestRequestBean guestRequestBean) {
        EventGuestGroupBean eventGuestGroupBean = new EventGuestGroupBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getEventId()) && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId()) ){
            String sQuery = "SELECT * FROM GTEVENTGUESTGROUP WHERE FK_EVENTID = ? AND FK_GUESTGROUPID = ? AND DEL_ROW=0";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getEventId(), guestRequestBean.getGuestGroupId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getEventGuestGroup()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    eventGuestGroupBean = new EventGuestGroupBean(hmResult);
                }
            }
        }
        return eventGuestGroupBean;
    }
    public ArrayList<EventGuestGroupBean> getAllEventGuestGroup(GuestRequestBean guestRequestBean) {
        ArrayList<EventGuestGroupBean> arrEventGuestGroupBean = new ArrayList<EventGuestGroupBean>();
        if(guestRequestBean!=null){
            String sQuery = "SELECT * FROM GTEVENTGUESTGROUP WHERE FK_EVENTID = ? AND DEL_ROW=0";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getEventId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getAllEventGuestGroup()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventGuestGroupBean eventGuestGroupBean = new EventGuestGroupBean(hmResult);
                    arrEventGuestGroupBean.add( eventGuestGroupBean );
                }
            }
        }
        return arrEventGuestGroupBean;
    }
    public HashMap<String,GuestGroupBean> getAllGuestGroup(GuestRequestBean guestRequestBean) {
        HashMap<String,GuestGroupBean> hmGuestGroupBean = new HashMap<String,GuestGroupBean>();
        if(guestRequestBean!=null){
            ArrayList<GuestGroupBean> arrTmpGuestGroupBean = guestRequestBean.getArrGuestGroupBean();
            if( arrTmpGuestGroupBean!=null && !arrTmpGuestGroupBean.isEmpty() ) {
                String sQuery = "SELECT * FROM GTGUESTGROUP WHERE GUESTGROUPID IN ( " + DBDAO.createParamQuestionMarks(arrTmpGuestGroupBean.size()) + ")";
                ArrayList<Object> aParams = new ArrayList<Object>();
                for(GuestGroupBean guestGroupBean : arrTmpGuestGroupBean ) {
                    aParams.add( guestGroupBean.getGuestGroupId() );
                }

                ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getAllGuestGroup()");

                if(arrResult!=null && !arrResult.isEmpty()) {
                    for( HashMap<String, String> hmResult : arrResult ) {
                        GuestGroupBean guestGroupBean = new GuestGroupBean(hmResult);
                        hmGuestGroupBean.put(guestGroupBean.getGuestGroupId(), guestGroupBean);
                    }
                }
            }
        }
        return hmGuestGroupBean;
    }
    public GuestGroupBean getGuestGroup(GuestRequestBean guestRequestBean) {
        GuestGroupBean guestGroupBean = new GuestGroupBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())){
            String sQuery = "SELECT * FROM GTGUESTGROUP WHERE GUESTGROUPID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getGuestGroupId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getGuestGroup()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    guestGroupBean = new GuestGroupBean(hmResult);
                }
            }
        }
        return guestGroupBean;
    }

    public GuestBean getGuest(GuestRequestBean guestRequestBean) {
        GuestBean guestBean = new GuestBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())){
            String sQuery = "SELECT * FROM GTGUEST WHERE FK_GUESTGROUPID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getGuestGroupId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getGuest()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    guestBean = new GuestBean(hmResult);
                }
            }

        }
        return guestBean;
    }

    public ArrayList<GuestGroupPhoneBean> getGuestGroupPhone(GuestRequestBean guestRequestBean) {
        ArrayList<GuestGroupPhoneBean>  arrGuestGroupPhoneBean = new ArrayList<GuestGroupPhoneBean>();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())){
            String sQuery = "SELECT * FROM GTGUESTGROUPPHONE WHERE FK_GUESTGROUPID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getGuestGroupId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getGuestGroupPhone()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    GuestGroupPhoneBean guestGroupPhoneBean = new GuestGroupPhoneBean(hmResult);
                    arrGuestGroupPhoneBean.add(guestGroupPhoneBean);
                }
            }
        }
        return arrGuestGroupPhoneBean;
    }

    public ArrayList<GuestGroupEmailBean> getGuestGroupEmail(GuestRequestBean guestRequestBean) {
        ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = new ArrayList<GuestGroupEmailBean>();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())){
            String sQuery = "SELECT * FROM GTGUESTGROUPEMAIL WHERE FK_GUESTGROUPID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getGuestGroupId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getGuestGroupEmail()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    GuestGroupEmailBean guestGroupEmailBean = new GuestGroupEmailBean(hmResult);
                    arrGuestGroupEmailBean.add(guestGroupEmailBean);
                }
            }
        }
        return arrGuestGroupEmailBean;
    }
    public GuestResponseBean getGuestDetailsFromEmail(GuestRequestBean guestRequestBean) {
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getEventId())&& !Utility.isNullOrEmpty(guestRequestBean.getEmail())){
            String sQuery = "SELECT * FROM  GTEVENTGUESTGROUP GEVENT, GTGUESTGROUPEMAIL GEMAIL, GTGUEST GG, GTGUESTGROUP GGRP  WHERE " +
                    " GEVENT.FK_EVENTID=? AND GEMAIL.EMAIL_ID= ? AND GEVENT.FK_GUESTGROUPID= GEMAIL.FK_GUESTGROUPID AND " +
                    " GG.FK_GUESTGROUPID=GEVENT.FK_GUESTGROUPID  AND GGRP.GUESTGROUPID=GG.FK_GUESTGROUPID ";

            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getEventId() , guestRequestBean.getEmail());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getGuestDetailsFromEmail()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    GuestGroupBean guestGroupBean = new GuestGroupBean(hmResult);
                    EventGuestGroupBean eventGuestGroupBean = new EventGuestGroupBean(hmResult);
                    GuestBean guestBean = new GuestBean(hmResult);

                    guestResponseBean.setGuestBean( guestBean );
                    guestResponseBean.setEventGuestGroupBean( eventGuestGroupBean );
                    guestResponseBean.setGuestGroupBean( guestGroupBean );
                }
            }
        }
        return guestResponseBean;
    }

    public ArrayList<GuestGroupAddressBean> getGuestGroupAddress(GuestRequestBean guestRequestBean) {
        ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = new ArrayList<GuestGroupAddressBean>();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId())){
            String sQuery = "SELECT * FROM GTGUESTGROUPADDRESS WHERE FK_GUESTGROUPID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getGuestGroupId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getGuestGroupAddress()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    GuestGroupAddressBean guestGroupAddressBean = new GuestGroupAddressBean(hmResult);
                    arrGuestGroupAddressBean.add(guestGroupAddressBean);
                }
            }
        }
        return arrGuestGroupAddressBean;
    }

    public GuestGroupEmailBean getGuestEmail(GuestRequestBean guestRequestBean) {
        GuestGroupEmailBean guestGroupEmailBean = new GuestGroupEmailBean();
        if(guestRequestBean!=null && !Utility.isNullOrEmpty(guestRequestBean.getGuestId())){
            String sQuery = "SELECT * FROM GTGUESTGROUPEMAIL WHERE FK_GUESTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestRequestBean.getGuestId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessGuestData.java", "getGuestEmail()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    guestGroupEmailBean = new GuestGroupEmailBean(hmResult);
                }
            }
        }
        return guestGroupEmailBean;
    }
}
