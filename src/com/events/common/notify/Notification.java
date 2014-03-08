package com.events.common.notify;

import com.events.bean.common.notify.NotifyBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.nosql.redis.RedisDAO;

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

    public static void createNewNotifyRecord( NotifyBean notifyBean ) {
        if(notifyBean!=null && !Utility.isNullOrEmpty(notifyBean.getFrom()) && !Utility.isNullOrEmpty(notifyBean.getTo() ) ) {
            Long lId = RedisDAO.getId(EVENTADMIN_DB, "disp.notify.id");
            if(lId>0){
                StringBuilder strKey = new StringBuilder("disp.notify.");
                strKey.append(lId).append(".");

                HashMap<String,String> hmRecords = new HashMap<String, String>();
                hmRecords.put( strKey.toString()+"from", notifyBean.getFrom() );
                hmRecords.put( strKey.toString()+"to", notifyBean.getTo() );
                hmRecords.put( strKey.toString()+"message", notifyBean.getMessage() );
                int iRowsAffected = RedisDAO.put(EVENTADMIN_DB , hmRecords) ;

                if(iRowsAffected>0) {
                    RedisDAO.pushId(EVENTADMIN_DB, "disp.new.notify", ParseUtil.LToS(lId) );
                }
            }
        }
    }
}
