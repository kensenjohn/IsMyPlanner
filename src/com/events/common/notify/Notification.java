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
        if(notifyBean!=null && !Utility.isNullOrEmpty(notifyBean.getFrom()) && !Utility.isNullOrEmpty(notifyBean.getTo() )
                && !Utility.isNullOrEmpty(sResource) ) {
            String unReadNotifyUserKey = DISPLAY_UNREAD_NOTIFY + "." + notifyBean.getTo();

            Long lId = RedisDAO.getId(sResource,  unReadNotifyUserKey + ".id"); // display.unread.notify.{{userid}}.id

            if(lId>0){
                StringBuilder strKey = new StringBuilder(unReadNotifyUserKey).append(".").append(lId); // queue.new.notify.1

                HashMap<String,String> hmRecords = new HashMap<String, String>();
                hmRecords.put( "from", notifyBean.getFrom() );
                hmRecords.put( "from_name", notifyBean.getFromName() );
                hmRecords.put( "to", notifyBean.getTo() );
                hmRecords.put( "message", notifyBean.getMessage() );
                int iRowsAffected = RedisDAO.putInHash(EVENTADMIN_DB, strKey.toString(), hmRecords) ;

                if(iRowsAffected>0) {
                    Long iIndex = RedisDAO.pushId(EVENTADMIN_DB, unReadNotifyUserKey , ParseUtil.LToS(lId));
                    if(iIndex>0){
                        RedisDAO.incrementCounter(EVENTADMIN_DB , unReadNotifyUserKey + ".counter", 1 );
                    }
                }
            }
        }
    }
    public HashMap<String,NotifyBean> getUnreadNotifyRecords(NotifyBean notifyBean){
        return getUnreadNotifyRecords(notifyBean , EVENTADMIN_DB );
    }
    public HashMap<String,NotifyBean> getUnreadNotifyRecords(NotifyBean notifyBean, String sResource){
        HashMap<String,NotifyBean> hmUnReadRecords = new HashMap<String,NotifyBean>();
        if(notifyBean!=null && !Utility.isNullOrEmpty(notifyBean.getTo())) {
            Long iNumOfUnreadNotifications = getNumberOfUnreadNotifyRecords(notifyBean , sResource );
            if(iNumOfUnreadNotifications>0){
                String unReadNotifyUserKey = DISPLAY_UNREAD_NOTIFY + "." + notifyBean.getTo();
                appLogging.info("unReadNotifyUserKey : " + unReadNotifyUserKey );
                ArrayList<String> arrId = RedisDAO.getIdList(EVENTADMIN_DB ,  unReadNotifyUserKey);
                if(arrId!=null && !arrId.isEmpty()) {
                    for(String id : arrId ) {
                        StringBuilder strHashKey = new StringBuilder(unReadNotifyUserKey).append(".").append(id);
                        HashMap<String,String> hmResult = RedisDAO.getFromHash(EVENTADMIN_DB , strHashKey.toString() ) ;

                        NotifyBean newNotifyBean = new NotifyBean(hmResult);

                        String topIdFromQueue = removeUnReadNotificationId(newNotifyBean.getTo());

                        hmUnReadRecords.put(id , newNotifyBean);

                        RedisDAO.decrementCounter(EVENTADMIN_DB , unReadNotifyUserKey + ".counter", 1 );
                    }
                }
/*
                Integer iNumOfUnReadRecords = 0;
                if(hmUnReadRecords!=null && !hmUnReadRecords.isEmpty()){
                    for(int i = 0; i<hmUnReadRecords.size(); i++){
                        String topIdFromQueue = removeUnReadNotificationId(notifyBean.getTo());

                        NotifyBean topUnReadNotifyBean = hmUnReadRecords.get( topIdFromQueue );

                        if(topUnReadNotifyBean!=null){

                        }
                    }
                }*/
            }
        }
        return hmUnReadRecords;
    }

    public Long getNumberOfUnreadNotifyRecords(NotifyBean notifyBean){
        return getNumberOfUnreadNotifyRecords(notifyBean, EVENTADMIN_DB);
    }

    public Long getNumberOfUnreadNotifyRecords(NotifyBean notifyBean, String sResource ){
        Long iNumOfUnreadNotifications = 0L;
        if(notifyBean!=null && !Utility.isNullOrEmpty(notifyBean.getTo())) {
            iNumOfUnreadNotifications = ParseUtil.sToL( RedisDAO.get(sResource, DISPLAY_UNREAD_NOTIFY+"."+notifyBean.getTo()+ ".counter") );
        }
        return iNumOfUnreadNotifications;
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


    public String removeUnReadNotificationId(String sUserId) {
        return removeUnReadNotificationId(EVENTADMIN_DB, sUserId);
    }
    public String removeUnReadNotificationId(String sResource, String sUserId) {
        String poppedId = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(sResource) && !Utility.isNullOrEmpty(sResource) )  {
            String unReadNotifyUserKey = DISPLAY_UNREAD_NOTIFY + "." + sUserId ;

            poppedId = RedisDAO.popFromList( sResource , unReadNotifyUserKey );
        }
        return poppedId;
    }
}
