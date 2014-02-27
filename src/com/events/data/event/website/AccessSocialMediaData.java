package com.events.data.event.website;

import com.events.bean.event.website.EventPartyBean;
import com.events.bean.event.website.EventPartyRequest;
import com.events.bean.event.website.SocialMediaBean;
import com.events.bean.event.website.ThemePageBean;
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
 * Date: 2/26/14
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessSocialMediaData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    ////GTSOCIALMEDIA(SOCIALMEDIAID VARCHAR(45) NOT NULL, FK_EVENTPARTYID VARCHAR(45) NOT NULL,SOCIALMEDIATYPE  VARCHAR(250) NOT NULL, URL  VARCHAR(1000) NOT NULL ,
    public ArrayList<SocialMediaBean> getSocialMedia(EventPartyRequest eventPartyRequest){
        Integer numOfRowsInserted = 0;
        ArrayList<SocialMediaBean> arrSocialMediaBean = new ArrayList<SocialMediaBean>();
        if( eventPartyRequest!=null  ) {
            String sQuery = "SELECT * FROM GTSOCIALMEDIA WHERE FK_EVENTPARTYID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventPartyRequest.getEventPartyId()  );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSocialMediaData.java", "getSocialMedia()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    SocialMediaBean socialMediaBean = new SocialMediaBean(hmResult);
                    arrSocialMediaBean.add( socialMediaBean );
                }
            }
        }
        return arrSocialMediaBean;
    }

    public ArrayList<SocialMediaBean> getSocialMedia(ArrayList<EventPartyBean> arrEventPartyBean){
        Integer numOfRowsInserted = 0;
        ArrayList<SocialMediaBean> arrSocialMediaBean = new ArrayList<SocialMediaBean>();
        if( arrEventPartyBean!=null && !arrEventPartyBean.isEmpty() ) {
            String sQuery = "SELECT * FROM GTSOCIALMEDIA WHERE FK_EVENTPARTYID IN ( " + DBDAO.createParamQuestionMarks(arrEventPartyBean.size()) + ")";
            ArrayList<Object> aParams = new ArrayList<Object>();
            for(EventPartyBean eventPartyBean : arrEventPartyBean ) {
                aParams.add( eventPartyBean.getEventPartyId() );
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSocialMediaData.java", "getSocialMedia()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    SocialMediaBean socialMediaBean = new SocialMediaBean(hmResult);
                    arrSocialMediaBean.add( socialMediaBean );
                }
            }
        }
        return arrSocialMediaBean;
    }

    public ArrayList<SocialMediaBean> getSocialMediaByWebsite(EventPartyRequest eventPartyRequest){
        ArrayList<SocialMediaBean> arrSocialMediaBean = new ArrayList<SocialMediaBean>();
        if(eventPartyRequest!=null && !Utility.isNullOrEmpty(eventPartyRequest.getEventWebsiteId())) {
            String sQuery = "SELECT GS.* FROM GTSOCIALMEDIA GS, GTEVENTPARTY GEP WHERE GEP.EVENTPARTYID = GS.FK_EVENTPARTYID AND GEP.FK_EVENTWEBSITEID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventPartyRequest.getEventWebsiteId()  );
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSocialMediaData.java", "getSocialMediaByWebsite()");
            if(arrResult!=null && !arrResult.isEmpty()){
                for(HashMap<String, String> hmResult : arrResult ) {
                    SocialMediaBean socialMediaBean = new SocialMediaBean(hmResult);
                    arrSocialMediaBean.add( socialMediaBean );
                }
            }
        }
        return arrSocialMediaBean;
    }
}
