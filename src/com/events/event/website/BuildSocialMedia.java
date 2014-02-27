package com.events.event.website;

import com.events.bean.event.website.EventPartyRequest;
import com.events.bean.event.website.SocialMediaBean;
import com.events.common.Utility;
import com.events.data.event.website.BuildSocialMediaData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildSocialMedia {
    public void saveSocialMedia(EventPartyRequest eventPartyRequest){
        ArrayList<SocialMediaBean> arrSocialMediaBean =  eventPartyRequest.getArrSocialMediaBean();
        if(eventPartyRequest!=null && !Utility.isNullOrEmpty(eventPartyRequest.getEventPartyId()) ){

            final String sEventPartyId = eventPartyRequest.getEventPartyId();
            BuildSocialMediaData buildSocialMediaData = new BuildSocialMediaData();
            Integer numOfRowsInserted = buildSocialMediaData.deleteSocialMedia( eventPartyRequest ) ;
            if(arrSocialMediaBean!=null && !arrSocialMediaBean.isEmpty()){
                ArrayList<SocialMediaBean> arrNewSocialMediaBean = new ArrayList<SocialMediaBean>();
                for( SocialMediaBean socialMediaBean : arrSocialMediaBean ){
                    socialMediaBean.setSocialMediaId( Utility.getNewGuid());
                    socialMediaBean.setEventPartyId( sEventPartyId );

                    arrNewSocialMediaBean.add(socialMediaBean );
                }
                buildSocialMediaData.insertSocialMedia( arrNewSocialMediaBean );
            }
        }
    }
}
