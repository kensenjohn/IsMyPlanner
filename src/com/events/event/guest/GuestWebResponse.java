package com.events.event.guest;

import com.events.bean.event.EventBean;
import com.events.bean.event.EventDisplayDateBean;
import com.events.bean.event.EventRequestBean;
import com.events.bean.event.EventResponseBean;
import com.events.bean.event.guest.GuestGroupBean;
import com.events.bean.event.guest.GuestRequestBean;
import com.events.bean.event.guest.GuestResponseBean;
import com.events.bean.event.guest.response.GuestWebResponseBean;
import com.events.bean.event.guest.response.WebRespRequest;
import com.events.bean.event.guest.response.WebRespResponse;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.data.event.guest.response.GuestWebResponseData;
import com.events.event.AccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestWebResponse{
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public WebRespResponse createGuestWebResponse(WebRespRequest webRespRequest ) {
        WebRespResponse webRespResponse = new WebRespResponse();
        if(webRespRequest!=null && !Utility.isNullOrEmpty(webRespRequest.getEventId())) {
            appLogging.info("Creating link for : " + webRespRequest.getEventId() );
            GuestWebResponseBean guestWebResponseBean = new GuestWebResponseBean();
            guestWebResponseBean.setGuestWebResponseId( Utility.getNewGuid() );
            guestWebResponseBean.setWebResponseType( webRespRequest.getGuestWebResponseType() );
            guestWebResponseBean.setLinkId( Utility.getNewGuid() );
            guestWebResponseBean.setLinkDomain( applicationConfig.get(Constants.APPLICATION_DOMAIN) ); //TODO : Create the domain for RSVP emails
            guestWebResponseBean.setGuestGroupId( webRespRequest.getGuestGroupId() );
            guestWebResponseBean.setEventId( webRespRequest.getEventId() );
            guestWebResponseBean.setResponseStatus( Constants.GUEST_WEB_RESPONSE_STATUS.NONE);

            GuestWebResponseData guestWebResponseData = new GuestWebResponseData();
            Integer iNumOfRows = guestWebResponseData.insertGuestWebResponse(guestWebResponseBean);
            if(iNumOfRows>0) {
                webRespResponse.setGuestWebResponseBean( guestWebResponseBean );
            }
            appLogging.info("num of rows inserted : " + iNumOfRows );
        }
        return webRespResponse;
    }

    public GuestWebResponseBean isValidLinkId(WebRespRequest webRsvpRequestBean ) {
        GuestWebResponseBean guestWebResponseBean = new GuestWebResponseBean();
        if(webRsvpRequestBean!=null && !Utility.isNullOrEmpty(webRsvpRequestBean.getLinkId()) ) {
            GuestWebResponseData webResponseData = new GuestWebResponseData();
            guestWebResponseBean = webResponseData.getGuestWebResponse(webRsvpRequestBean);
        }
        return guestWebResponseBean;
    }

    public GuestResponseBean getGuestGroupFromLink(WebRespRequest webRsvpRequestBean ) {
        GuestResponseBean guestResponseBean = new GuestResponseBean();
        if(webRsvpRequestBean!=null && !Utility.isNullOrEmpty(webRsvpRequestBean.getGuestGroupId())
                && !Utility.isNullOrEmpty(webRsvpRequestBean.getEventId()) ) {
            GuestRequestBean guestRequestBean = new GuestRequestBean();
            guestRequestBean.setEventId(webRsvpRequestBean.getEventId());
            guestRequestBean.setGuestGroupId(webRsvpRequestBean.getGuestGroupId());

            AccessGuest accessGuest = new AccessGuest();
            guestResponseBean = accessGuest.loadGuest(guestRequestBean);
        }
        return guestResponseBean;
    }

    public EventResponseBean getEventFromLink(WebRespRequest webRsvpRequestBean ) {
        EventResponseBean eventResponseBean = new EventResponseBean();
        if(webRsvpRequestBean!=null && !Utility.isNullOrEmpty(webRsvpRequestBean.getEventId()) ) {
            EventRequestBean eventRequestBean = new EventRequestBean();
            eventRequestBean.setEventId( webRsvpRequestBean.getEventId() );
            AccessEvent accessEvent = new AccessEvent();
            eventResponseBean = accessEvent.getEventInfo(eventRequestBean);
            if(eventResponseBean!=null && eventResponseBean.getEventBean()!=null && !Utility.isNullOrEmpty(eventResponseBean.getEventBean().getEventId()) ) {
                EventBean eventBean = eventResponseBean.getEventBean();
                EventDisplayDateBean eventDisplayDateBean = accessEvent.getEventSelectedDisplayDate(eventRequestBean);
                if(eventDisplayDateBean!=null){
                    eventBean.setEventDisplayDateBean(eventDisplayDateBean);
                    eventResponseBean.setEventBean( eventBean );
                }
            }
        }
        return eventResponseBean;
    }
}
