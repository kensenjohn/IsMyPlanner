package com.events.data.event.website;

import com.events.bean.event.website.EventPartyRequest;
import com.events.bean.event.website.SocialMediaBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildSocialMediaData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTSOCIALMEDIA(SOCIALMEDIAID VARCHAR(45) NOT NULL, FK_EVENTPARTYID VARCHAR(45) NOT NULL,SOCIALMEDIATYPE  VARCHAR(250) NOT NULL, URL  VARCHAR(1000) NOT NULL ,
    public Integer insertSocialMedia(ArrayList<SocialMediaBean> arrSocialMediaBean){
        Integer numOfRowsInserted = 0;
        if( arrSocialMediaBean!=null && !arrSocialMediaBean.isEmpty() ) {
            String sQuery = "INSERT INTO GTSOCIALMEDIA ( SOCIALMEDIAID,FK_EVENTPARTYID,SOCIALMEDIATYPE,    URL)  VALUES ( ?,?,?,   ?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(SocialMediaBean socialMediaBean : arrSocialMediaBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(
                        socialMediaBean.getSocialMediaId(), socialMediaBean.getEventPartyId(), socialMediaBean.getSocialMediaType().toString(),
                        socialMediaBean.getUrl());
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildSocialMediaData.java", "insertSocialMedia() " );

            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }

    public Integer deleteSocialMedia(EventPartyRequest eventPartyRequest){
        Integer numOfRowsInserted = 0;
        if( eventPartyRequest!=null&& !Utility.isNullOrEmpty(eventPartyRequest.getEventPartyId())) {
            String sQuery = "DELETE FROM GTSOCIALMEDIA WHERE  FK_EVENTPARTYID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventPartyRequest.getEventPartyId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildSocialMediaData.java", "deleteSocialMedia()");
        }
        return numOfRowsInserted;
    }
}
