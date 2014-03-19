package com.events.data.email;

import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailSchedulerRequestBean;
import com.events.common.*;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailSchedulerData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private final String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static final Logger emailLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);
    private static final String sourceFile = "EmailSchedulerData.java";


    public Integer insertEmailSchedule( EmailSchedulerBean emailRequestSchedulerBean ) {
        Integer iNumberOfRows = 0;
        if(emailRequestSchedulerBean!=null) {
            String sQuery = "INSERT INTO GTEMAILSCHEDULE (EMAILSCHEDULEID,FK_EVENTEMAILID,FK_EVENTID, " +
                    "      FK_USERID, CREATEDATE, HUMANCREATEDATE,       SCHEDULEDSENDDATE,HUMANSCHEDULEDSENDDATE,SCHEDULE_STATUS )  " +
                    "VALUES(?,?,?,    ?,?,?,    ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(emailRequestSchedulerBean.getEmailScheduleId(), emailRequestSchedulerBean.getEventEmailId(),
                    emailRequestSchedulerBean.getEventId(), emailRequestSchedulerBean.getUserId(),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), emailRequestSchedulerBean.getScheduledSendDate(),
                    emailRequestSchedulerBean.getHumanScheduledSendDate(), emailRequestSchedulerBean.getScheduleStatus());
            emailLogging.info(" Creating schedule for email : Query : " + sQuery + " Params : " + aParams );
            iNumberOfRows = DBDAO.putRowsQuery( sQuery,aParams,EVENTADMIN_DB,sourceFile,"createSchedule()" );
        }
        return iNumberOfRows;
    }

    public Integer removeEmailSchedule( EmailSchedulerRequestBean emailRequestSchedulerBean ) {
        Integer iNumberOfRows = 0;
        if(emailRequestSchedulerBean!=null && !Utility.isNullOrEmpty(emailRequestSchedulerBean.getEventEmailId()) && !Utility.isNullOrEmpty(emailRequestSchedulerBean.getEventId())) {
            String sQuery = "DELETE FROM GTEMAILSCHEDULE WHERE FK_EVENTEMAILID = ? AND FK_EVENTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint( emailRequestSchedulerBean.getEventEmailId(), emailRequestSchedulerBean.getEventId() );
            emailLogging.info(" Creating schedule for email : Query : " + sQuery + " Params : " + aParams );
            iNumberOfRows = DBDAO.putRowsQuery( sQuery,aParams,EVENTADMIN_DB,sourceFile,"createSchedule()" );
        }
        return iNumberOfRows;
    }

    public EmailSchedulerBean getEventEmailSchedule (EmailSchedulerBean emailRequestSchedulerBean){
        EmailSchedulerBean emailSchedulerBean = new EmailSchedulerBean();
        if(emailRequestSchedulerBean!=null && !Utility.isNullOrEmpty(emailRequestSchedulerBean.getEventEmailId())  && !Utility.isNullOrEmpty(emailRequestSchedulerBean.getEventId()))  {
            String sQuery = "SELECT * FROM GTEMAILSCHEDULE WHERE FK_EVENTEMAILID =? AND FK_EVENTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(emailRequestSchedulerBean.getEventEmailId() , emailRequestSchedulerBean.getEventId() );
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB,sQuery,aParams, true,sourceFile,"getEventEmailSchedule()");
            if( arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    emailSchedulerBean = new EmailSchedulerBean(hmResult);
                }
            }
        }
        return emailSchedulerBean;
    }

    public ArrayList<EmailSchedulerBean> getArrEmailSchedule(Long lStartTime, Long lEndTime, Constants.SCHEDULER_STATUS scheduleStatus,
                                                       Constants.SCHEDULE_PICKUP_TYPE schedulePickupType ) {

        ArrayList<EmailSchedulerBean> arrSchedulerBean = new ArrayList<EmailSchedulerBean>();
        if(schedulePickupType!=null && scheduleStatus!=null && lStartTime>0) {
            String sQuery = "SELECT * FROM GTEMAILSCHEDULE WHERE ";
            ArrayList<Object> aParams = new ArrayList<Object>();

            sQuery = sQuery + " SCHEDULE_STATUS = ? ";
            aParams.add( scheduleStatus.getStatus() );

            if(Constants.SCHEDULE_PICKUP_TYPE.NEW_RECORDS.equals( schedulePickupType )) {
                sQuery = sQuery + " AND SCHEDULEDSENDDATE >= ? AND SCHEDULEDSENDDATE <= ? ";
                aParams.add(lStartTime);
                aParams.add(lEndTime);
            }  else if(Constants.SCHEDULE_PICKUP_TYPE.OLD_RECORDS.equals( schedulePickupType )) {
                sQuery = sQuery + " AND SCHEDULEDSENDDATE < ? ";
                aParams.add(lStartTime);
            } else if(Constants.SCHEDULE_PICKUP_TYPE.CURRENT_RECORD.equals( schedulePickupType )) {
                sQuery = sQuery + " AND SCHEDULEDSENDDATE = ? ";
                aParams.add(lStartTime);
            }
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, true, sourceFile, "getArrEmailSchedule()");
            if(arrResult!=null && !arrResult.isEmpty() ) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EmailSchedulerBean  emailScheduleBean = new EmailSchedulerBean(hmResult);
                    arrSchedulerBean.add( emailScheduleBean );
                }
            }
        }
        return arrSchedulerBean;
    }


    public Integer updateEmailSchedule( EmailSchedulerBean emailRequestSchedulerBean )
    {
        Integer iNumberOfRows = 0;
        if(emailRequestSchedulerBean!=null) {
            String sQuery = "UPDATE GTEMAILSCHEDULE SET SCHEDULEDSENDDATE = ? , HUMANSCHEDULEDSENDDATE = ? , SCHEDULE_STATUS = ? , " +
                    "  FK_EVENTEMAILID = ? WHERE EMAILSCHEDULEID = ? AND FK_EVENTID = ? AND FK_USERID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( emailRequestSchedulerBean.getScheduledSendDate(), emailRequestSchedulerBean.getHumanScheduledSendDate() ,
                    emailRequestSchedulerBean.getScheduleStatus(), emailRequestSchedulerBean.getEventEmailId(), emailRequestSchedulerBean.getEmailScheduleId(),
                    emailRequestSchedulerBean.getEventId() , emailRequestSchedulerBean.getUserId() );

            iNumberOfRows = DBDAO.putRowsQuery( sQuery,aParams,EVENTADMIN_DB,sourceFile,"updateEmailSchedule()" );
        }
        return iNumberOfRows;
    }
}
