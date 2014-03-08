package com.events.common.notify;

import com.events.bean.common.notify.NotifyBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.nosql.redis.RedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/7/14
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Notification {
    private static Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    private static final String QUE_NEW_NOTIFY = "queue.new.notify";
    private static final String DISPLAY_UNREAD_NOTIFY = "display.unread.notify";

    public static void createUnReadNotifyRecord( NotifyBean notifyBean ) {
        createUnReadNotifyRecord(notifyBean , EVENTADMIN_DB );
    }
    public static void createUnReadNotifyRecord( NotifyBean notifyBean, String sResource ) {

    }

    public static void createNewNotifyRecord( NotifyBean notifyBean ) {
        createNewNotifyRecord(notifyBean , EVENTADMIN_DB );
    }

    public static void createNewNotifyRecord( NotifyBean notifyBean, String sResource ) {
        if(notifyBean!=null && !Utility.isNullOrEmpty(notifyBean.getFrom()) && !Utility.isNullOrEmpty(notifyBean.getTo() )
                && !Utility.isNullOrEmpty(sResource) ) {
            Long lId = RedisDAO.getId(sResource, QUE_NEW_NOTIFY + ".id"); // queue.new.notify.id
            if(lId>0){
                StringBuilder strKey = new StringBuilder(QUE_NEW_NOTIFY).append(".").append(lId); // queue.new.notify.1

                HashMap<String,String> hmRecords = new HashMap<String, String>();
                hmRecords.put( "from", notifyBean.getFrom() );
                hmRecords.put( "to", notifyBean.getTo() );
                hmRecords.put( "message", notifyBean.getMessage() );
                int iRowsAffected = RedisDAO.putInHash(EVENTADMIN_DB, strKey.toString(), hmRecords) ;

                if(iRowsAffected>0) {
                    RedisDAO.pushId(EVENTADMIN_DB, QUE_NEW_NOTIFY , ParseUtil.LToS(lId));
                }
            }
        }
    }
    public HashMap<String, NotifyBean> getNewNotificationFromQueue() {
        return getNewNotificationFromQueue(EVENTADMIN_DB);
    }
    public HashMap<String, NotifyBean> getNewNotificationFromQueue(String sResource) {
        HashMap<String, NotifyBean> hmNotifyBean = new HashMap<String, NotifyBean>();
        if(!Utility.isNullOrEmpty(sResource))  {
            ArrayList<String> arrId = RedisDAO.getIdList( sResource , QUE_NEW_NOTIFY );
            appLogging.info("Ids in Queue : " + arrId);
            if(arrId!=null && !arrId.isEmpty() ) {
                for(String id : arrId ){

                    StringBuilder strHashKey = new StringBuilder(QUE_NEW_NOTIFY).append(".").append(id);
                    HashMap<String,String> hmResult = RedisDAO.getFromHash(EVENTADMIN_DB , strHashKey.toString() ) ;

                    NotifyBean notifyBean = new NotifyBean(hmResult);
                    hmNotifyBean.put(id, notifyBean);
                }
            }
        }
        return hmNotifyBean;
    }
    public String removeNotificationIdFromQueue() {
        return removeNotificationIdFromQueue(EVENTADMIN_DB);
    }
    public String removeNotificationIdFromQueue(String sResource) {
        String poppedId = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(sResource))  {
            poppedId = RedisDAO.popFromList( sResource , QUE_NEW_NOTIFY );
        }
        return poppedId;
    }
}
