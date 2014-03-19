package com.events.data.email;

import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EventEmailBean;
import com.events.bean.common.email.EventEmailRequestBean;
import com.events.bean.common.email.EveryEventEmailRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/10/14
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventEmailData {
    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private final String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static final Logger emailLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);
    private static final String sourceFile = "EventEmailData.java";

    public Integer insertEventEmail(EventEmailBean eventEmailBean){
        Integer iNumOfRows = 0;
        if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId()) && !Utility.isNullOrEmpty(eventEmailBean.getEventId())
                && !Utility.isNullOrEmpty(eventEmailBean.getSubject() )  && !Utility.isNullOrEmpty(eventEmailBean.getFromAddressEmail())
                && !Utility.isNullOrEmpty(eventEmailBean.getHtmlBody())) {
            String sQuery = "INSERT INTO GTEVENTEMAIL (EVENTEMAILID,FK_EVENTID,FK_USERID,   FROM_ADDRESS_NAME,FROM_EMAIL_ADDRESS,EMAIL_SUBJECT,    " +
                    " HTML_BODY,TEXT_BODY,CREATEDATE,     HUMANCREATEDATE) VALUES (?,?,?,    ?,?,?,    ?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventEmailBean.getEventEmailId(),eventEmailBean.getEventId(),eventEmailBean.getUserId(),
                    eventEmailBean.getFromAddressName(),eventEmailBean.getFromAddressEmail(),eventEmailBean.getSubject(),
                    eventEmailBean.getHtmlBody(),eventEmailBean.getTextBody(), DateSupport.getEpochMillis(),
                    DateSupport.getUTCDateTime());
            iNumOfRows = DBDAO.putRowsQuery( sQuery,aParams,EVENTADMIN_DB,sourceFile,"insertEventEmail()" );
        }
        return iNumOfRows;
    }

    public Integer updateEventEmail(EventEmailBean eventEmailBean){
        Integer iNumOfRows = 0;
        if(eventEmailBean!=null && !Utility.isNullOrEmpty(eventEmailBean.getEventEmailId()) && !Utility.isNullOrEmpty(eventEmailBean.getEventId())
                && !Utility.isNullOrEmpty(eventEmailBean.getSubject() )  && !Utility.isNullOrEmpty(eventEmailBean.getFromAddressEmail())
                && !Utility.isNullOrEmpty(eventEmailBean.getHtmlBody())) {
            String sQuery = "UPDATE GTEVENTEMAIL SET FROM_ADDRESS_NAME = ?,FROM_EMAIL_ADDRESS = ?,EMAIL_SUBJECT = ?,     " +
                    " HTML_BODY = ?,TEXT_BODY = ? WHERE EVENTEMAILID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventEmailBean.getFromAddressName(),eventEmailBean.getFromAddressEmail(),eventEmailBean.getSubject(),
                    eventEmailBean.getHtmlBody(),eventEmailBean.getTextBody(),eventEmailBean.getEventEmailId());
            iNumOfRows = DBDAO.putRowsQuery( sQuery,aParams,EVENTADMIN_DB,sourceFile,"updateEventEmail()" );
        }
        return iNumOfRows;
    }

    public EventEmailBean getEventEmail(EventEmailRequestBean eventEmailRequestBean) {
        EventEmailBean eventEmailBean = new EventEmailBean();
        if(eventEmailRequestBean!=null && !Utility.isNullOrEmpty(eventEmailRequestBean.getEventEmailId())){
            String sQuery = "SELECT * FROM GTEVENTEMAIL WHERE EVENTEMAILID = ? AND DEL_ROW = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(eventEmailRequestBean.getEventEmailId(), "0");
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(  EVENTADMIN_DB , sQuery, aParams, true, sourceFile, "getEventEmail()");
            if(arrResult!=null && !arrResult.isEmpty() ) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    eventEmailBean = new EventEmailBean(hmResult);
                }
            }
        }
        return eventEmailBean;
    }

    public ArrayList<EventEmailBean> getAllEventEmails(EveryEventEmailRequestBean everyEventEmailRequestBean) {
        ArrayList<EventEmailBean> arrEventEmailBean = new ArrayList<EventEmailBean>();
        if(everyEventEmailRequestBean!=null && !Utility.isNullOrEmpty(everyEventEmailRequestBean.getEventId())){
            String sQuery = "SELECT * FROM GTEVENTEMAIL WHERE FK_EVENTID = ? AND DEL_ROW = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(everyEventEmailRequestBean.getEventId(), "0");
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(  EVENTADMIN_DB , sQuery, aParams, true, sourceFile, "getAllEventEmails()");
            if(arrResult!=null && !arrResult.isEmpty() ) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventEmailBean eventEmailBean = new EventEmailBean(hmResult);
                    arrEventEmailBean.add( eventEmailBean );
                }
            }
        }
        return arrEventEmailBean;
    }
}
