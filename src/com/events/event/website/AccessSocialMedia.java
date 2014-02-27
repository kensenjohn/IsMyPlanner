package com.events.event.website;

import com.events.bean.event.website.EventPartyRequest;
import com.events.bean.event.website.SocialMediaBean;
import com.events.common.Utility;
import com.events.data.event.website.AccessSocialMediaData;
import com.events.data.event.website.BuildSocialMediaData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessSocialMedia {
    public ArrayList<SocialMediaBean> getSocialMedia(EventPartyRequest eventPartyRequest){
        ArrayList<SocialMediaBean> arrSocialMediaBean =  new ArrayList<SocialMediaBean>();
        if(eventPartyRequest!=null && !Utility.isNullOrEmpty(eventPartyRequest.getEventPartyId()) ){

            AccessSocialMediaData accessSocialMediaData = new AccessSocialMediaData();
            arrSocialMediaBean = accessSocialMediaData.getSocialMedia( eventPartyRequest );
        }
        return arrSocialMediaBean;
    }

    public ArrayList<SocialMediaBean> getSocialMediaByWebsite(EventPartyRequest eventPartyRequest){
        ArrayList<SocialMediaBean> arrSocialMediaBean =  eventPartyRequest.getArrSocialMediaBean();
        if(eventPartyRequest!=null && !Utility.isNullOrEmpty(eventPartyRequest.getEventWebsiteId()) ){
            AccessSocialMediaData accessSocialMediaData = new AccessSocialMediaData();
            arrSocialMediaBean = accessSocialMediaData.getSocialMediaByWebsite( eventPartyRequest );
        }
        return arrSocialMediaBean;
    }
}
