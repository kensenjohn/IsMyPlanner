package com.events.bean.event.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class SocialMediaBean {
    //GTSOCIALMEDIA(SOCIALMEDIAID VARCHAR(45) NOT NULL, FK_EVENTPARTYID VARCHAR(45) NOT NULL,SOCIALMEDIATYPE  VARCHAR(250) NOT NULL, URL  VARCHAR(1000) NOT NULL ,
    private String socialMediaId = Constants.EMPTY;
    private String eventPartyId = Constants.EMPTY;
    private String socialMediaName = Constants.EMPTY;
    private Constants.SOCIAL_MEDIA_TYPE socialMediaType = Constants.SOCIAL_MEDIA_TYPE.NONE;
    private String url = Constants.EMPTY;

    public SocialMediaBean(){}
    public SocialMediaBean(HashMap<String, String> hmResult){
        this.socialMediaId = ParseUtil.checkNull(hmResult.get("SOCIALMEDIAID"));
        this.eventPartyId = ParseUtil.checkNull(hmResult.get("EVENTPARTYID"));
        this.socialMediaName = ParseUtil.checkNull(hmResult.get("SOCIALMEDIATYPE"));
        this.url = ParseUtil.checkNull(hmResult.get("URL"));

        if(!Utility.isNullOrEmpty(this.socialMediaName)) {
            socialMediaType = Constants.SOCIAL_MEDIA_TYPE.valueOf( this.socialMediaName );
        }
    }
    public String getSocialMediaId() {
        return socialMediaId;
    }

    public void setSocialMediaId(String socialMediaId) {
        this.socialMediaId = socialMediaId;
    }

    public String getEventPartyId() {
        return eventPartyId;
    }

    public void setEventPartyId(String eventPartyId) {
        this.eventPartyId = eventPartyId;
    }

    public String getSocialMediaName() {
        return socialMediaName;
    }

    public void setSocialMediaName(String socialMediaName) {
        this.socialMediaName = socialMediaName;
    }

    public Constants.SOCIAL_MEDIA_TYPE getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(Constants.SOCIAL_MEDIA_TYPE socialMediaType) {
        this.socialMediaType = socialMediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SocialMediaBean{");
        sb.append("socialMediaId='").append(socialMediaId).append('\'');
        sb.append(", eventPartyId='").append(eventPartyId).append('\'');
        sb.append(", socialMediaName='").append(socialMediaName).append('\'');
        sb.append(", socialMediaType=").append(socialMediaType);
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
