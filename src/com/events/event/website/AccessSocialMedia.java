package com.events.event.website;

import com.events.bean.event.website.EventPartyBean;
import com.events.bean.event.website.EventPartyRequest;
import com.events.bean.event.website.SocialMediaBean;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.event.website.AccessSocialMediaData;
import com.events.data.event.website.BuildSocialMediaData;

import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<Constants.SOCIAL_MEDIA_TYPE, SocialMediaBean> getHashSocialMedia(EventPartyRequest eventPartyRequest){
        HashMap<Constants.SOCIAL_MEDIA_TYPE, SocialMediaBean> hashSocialMediaBean =  new HashMap<Constants.SOCIAL_MEDIA_TYPE, SocialMediaBean>();
        if(eventPartyRequest!=null && !Utility.isNullOrEmpty(eventPartyRequest.getEventPartyId()) ){

            AccessSocialMediaData accessSocialMediaData = new AccessSocialMediaData();
            ArrayList<SocialMediaBean> arrSocialMediaBean = accessSocialMediaData.getSocialMedia( eventPartyRequest );
            if(arrSocialMediaBean!=null && !arrSocialMediaBean.isEmpty()) {
                for(SocialMediaBean socialMediaBean : arrSocialMediaBean ) {
                    hashSocialMediaBean.put(socialMediaBean.getSocialMediaType() , socialMediaBean );
                }
            }
        }
        return hashSocialMediaBean;
    }

    public ArrayList<SocialMediaBean> getSocialMedia(ArrayList<EventPartyBean> arrEventPartyBean ){
        ArrayList<SocialMediaBean> arrSocialMediaBean =  new ArrayList<SocialMediaBean>();
        if(arrEventPartyBean!=null && !arrEventPartyBean.isEmpty() ){

            AccessSocialMediaData accessSocialMediaData = new AccessSocialMediaData();
            arrSocialMediaBean = accessSocialMediaData.getSocialMedia( arrEventPartyBean );
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

    public ArrayList<SocialMediaBean> getSocialMediaByTypeWebsite(EventPartyRequest eventPartyRequest){
        ArrayList<SocialMediaBean> arrSocialMediaBean =  eventPartyRequest.getArrSocialMediaBean();
        if(eventPartyRequest!=null && !Utility.isNullOrEmpty(eventPartyRequest.getEventWebsiteId()) ){
            AccessSocialMediaData accessSocialMediaData = new AccessSocialMediaData();
            arrSocialMediaBean = accessSocialMediaData.getSocialMediaByWebsite( eventPartyRequest );
        }
        return arrSocialMediaBean;
    }
}
