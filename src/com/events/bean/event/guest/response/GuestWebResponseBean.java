package com.events.bean.event.guest.response;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestWebResponseBean {
    private String guestWebResponseId = Constants.EMPTY;
    private Constants.GUEST_WEBRESPONSE_TYPE webResponseType = Constants.GUEST_WEBRESPONSE_TYPE.none;
    private String linkId = Constants.EMPTY;
    private String linkDomain = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private Constants.GUEST_WEB_RESPONSE_STATUS responseStatus = Constants.GUEST_WEB_RESPONSE_STATUS.NONE;
    private String userId = Constants.EMPTY;
    private Long responseDate = 0L;
    private String humanResponseDate = Constants.EMPTY;
    //GTGUESTWEBRESPONSE ( GUESTWEBRESPONSEID VARCHAR(45) NOT NULL,WEB_RESPONSE_TYPE  VARCHAR(45) NOT NULL,
    // LINKID  VARCHAR(45) NOT NULL,  LINK_DOMAIN  TEXT, FK_GUESTGROUPID VARCHAR(45) NOT NULL ,
    // FK_EVENTID VARCHAR(45) NOT NULL, CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    // RESPONSE_STATUS VARCHAR(45) NOT NULL ,FK_USERID VARCHAR(45) NOT NULL, RESPONSEDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANRESPONSEDATE VARCHAR(45),  PRIMARY KEY (GUESTWEBRESPONSEID,WEB_RESPONSE_TYPE,LINKID)
    public GuestWebResponseBean(HashMap<String, String> hmWebResponse) {
        this.guestWebResponseId = ParseUtil.checkNull(hmWebResponse.get("GUESTWEBRESPONSEID"));
        this.webResponseType = Constants.GUEST_WEBRESPONSE_TYPE.valueOf(ParseUtil.checkNull(hmWebResponse.get("WEB_RESPONSE_TYPE")));;
        this.linkId = ParseUtil.checkNull(hmWebResponse.get("LINKID"));;
        this.linkDomain = ParseUtil.checkNull(hmWebResponse.get("LINK_DOMAIN"));;
        this.guestGroupId = ParseUtil.checkNull(hmWebResponse.get("FK_GUESTGROUPID"));;
        this.eventId = ParseUtil.checkNull(hmWebResponse.get("FK_EVENTID"));;
        this.createDate = ParseUtil.sToL(hmWebResponse.get("CREATEDATE"));;
        this.humanCreateDate = ParseUtil.checkNull(hmWebResponse.get("HUMANCREATEDATE"));;
        this.responseStatus = Constants.GUEST_WEB_RESPONSE_STATUS.valueOf(ParseUtil.checkNull(hmWebResponse.get("RESPONSE_STATUS")));;
        this.userId = ParseUtil.checkNull(hmWebResponse.get("FK_USERID"));;
        this.responseDate = ParseUtil.sToL(hmWebResponse.get("RESPONSEDATE"));;
        this.humanResponseDate = ParseUtil.checkNull(hmWebResponse.get("HUMANRESPONSEDATE"));;
    }

    public GuestWebResponseBean( ) {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestWebResponseBean{");
        sb.append("guestWebResponseId='").append(guestWebResponseId).append('\'');
        sb.append(", webResponseType=").append(webResponseType);
        sb.append(", linkId='").append(linkId).append('\'');
        sb.append(", linkDomain='").append(linkDomain).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", responseStatus=").append(responseStatus);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", responseDate=").append(responseDate);
        sb.append(", humanResponseDate='").append(humanResponseDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getGuestWebResponseId() {
        return guestWebResponseId;
    }

    public void setGuestWebResponseId(String guestWebResponseId) {
        this.guestWebResponseId = guestWebResponseId;
    }

    public Constants.GUEST_WEBRESPONSE_TYPE getWebResponseType() {
        return webResponseType;
    }

    public void setWebResponseType(Constants.GUEST_WEBRESPONSE_TYPE webResponseType) {
        this.webResponseType = webResponseType;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getLinkDomain() {
        return linkDomain;
    }

    public void setLinkDomain(String linkDomain) {
        this.linkDomain = linkDomain;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    public Constants.GUEST_WEB_RESPONSE_STATUS getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Constants.GUEST_WEB_RESPONSE_STATUS responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Long responseDate) {
        this.responseDate = responseDate;
    }

    public String getHumanResponseDate() {
        return humanResponseDate;
    }

    public void setHumanResponseDate(String humanResponseDate) {
        this.humanResponseDate = humanResponseDate;
    }


}
