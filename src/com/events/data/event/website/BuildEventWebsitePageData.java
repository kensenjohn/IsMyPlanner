package com.events.data.event.website;

import com.events.bean.event.website.EventWebsitePageBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/21/14
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventWebsitePageData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTEVENTWEBSITEPAGE(EVENTWEBSITEPAGEID  VARCHAR(45) NOT NULL ,   FK_EVENTWEBSITEID  VARCHAR(45) NOT NULL , FK_WEBSITETHEMEID  VARCHAR(45) NOT NULL , TYPE  VARCHAR(45) NOT NULL, IS_SHOW
    public Integer insertEventWebsitePageBatch( ArrayList<EventWebsitePageBean> arrEventWebsitePageBean) {
        Integer numOfRowsInserted = 0;
        if(arrEventWebsitePageBean!=null && !arrEventWebsitePageBean.isEmpty()){
            String sQuery = "INSERT INTO GTEVENTWEBSITEPAGE ( EVENTWEBSITEPAGEID,FK_EVENTWEBSITEID,FK_WEBSITETHEMEID,    TYPE,IS_SHOW)  VALUES ( ?,?,?,   ?,?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(EventWebsitePageBean eventWebsitePageBean : arrEventWebsitePageBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(
                        eventWebsitePageBean.getEventWebsitePageId(), eventWebsitePageBean.getEventWebsiteId(),eventWebsitePageBean.getWebsiteThemeId(),
                        eventWebsitePageBean.getType(), eventWebsitePageBean.isShow() );
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildEventWebsitePageData.java", "insertEventWebsitePageBatch() " );

            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;

    }

    public Integer deleteEventWebsitePageBatch( ArrayList<EventWebsitePageBean> arrEventWebsitePageBean) {
        Integer numOfRowsDeleted = 0;
        if(arrEventWebsitePageBean!=null && !arrEventWebsitePageBean.isEmpty()){
            String sQuery = "DELETE FROM GTEVENTWEBSITEPAGE WHERE EVENTWEBSITEPAGEID = ? AND FK_EVENTWEBSITEID = ?";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(EventWebsitePageBean eventWebsitePageBean : arrEventWebsitePageBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(eventWebsitePageBean.getEventWebsitePageId(), eventWebsitePageBean.getEventWebsiteId());
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildEventWebsitePageData.java", "deleteEventWebsitePageBatch() " );

            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsDeleted = numOfRowsDeleted + iCount;
                }
            }
        }
        return numOfRowsDeleted;

    }
}
