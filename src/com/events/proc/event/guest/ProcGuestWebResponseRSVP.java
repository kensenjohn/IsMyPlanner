package com.events.proc.event.guest;

import com.events.bean.event.guest.GuestRequestBean;
import com.events.bean.event.guest.GuestResponseBean;
import com.events.bean.event.guest.response.GuestWebResponseBean;
import com.events.bean.event.guest.response.WebRespRequest;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.guest.BuildGuest;
import com.events.event.guest.GuestWebResponse;
import com.events.json.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/21/14
 * Time: 7:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcGuestWebResponseRSVP  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {

                String sLinkId = ParseUtil.checkNull(request.getParameter("link_id"));
                String sEventGuestGroupId = ParseUtil.checkNull(request.getParameter("event_guestgroup_id"));
                String sNumOfGuests = ParseUtil.checkNull(request.getParameter("num_of_guests"));
                String sWillAttend = ParseUtil.checkNull(request.getParameter("will_you_attend"));
                Integer iNumOfGuests = ParseUtil.sToI(request.getParameter("num_of_guests"));
                boolean isAttending = ParseUtil.sTob(request.getParameter("will_you_attend"));

                WebRespRequest  webRsvpRequestBean = new WebRespRequest();
                webRsvpRequestBean.setLinkId(sLinkId);
                webRsvpRequestBean.setGuestWebResponseType(Constants.GUEST_WEBRESPONSE_TYPE.RSVP);

                GuestWebResponse guestWebResponse = new GuestWebResponse();
                GuestWebResponseBean guestWebResponseBean = guestWebResponse.isValidLinkId(webRsvpRequestBean);

                if( guestWebResponseBean == null || (guestWebResponseBean!=null && Utility.isNullOrEmpty(guestWebResponseBean.getGuestWebResponseId()))
                        ||  Utility.isNullOrEmpty(sWillAttend) ||  ("yes".equalsIgnoreCase(sWillAttend) && Utility.isNullOrEmpty(sNumOfGuests)) ) {
                    Text errorText = new ErrorText("Please fill in all required fields. We were unable to process your RSVP.","account_num") ;
                    arrErrorText.add(errorText);
                    responseStatus = RespConstants.Status.ERROR;
                } else {
                    boolean isWillNotAttend = true;
                    if(isAttending){
                        iNumOfGuests = iNumOfGuests +1; // include the main invitee.
                        isWillNotAttend = false;
                    } else {
                        iNumOfGuests = 0;
                    }

                    GuestRequestBean guestRequestBean = new GuestRequestBean();
                    guestRequestBean.setGuestGroupId(guestWebResponseBean.getGuestGroupId());
                    guestRequestBean.setEventId( guestWebResponseBean.getEventId());
                    guestRequestBean.setEventGuestGroupId(sEventGuestGroupId);
                    guestRequestBean.setRsvpSeats(iNumOfGuests);
                    guestRequestBean.setNotAttending(isWillNotAttend);
                    guestRequestBean.setHasResponded( true );

                    BuildGuest buildGuest = new BuildGuest();
                    GuestResponseBean guestResponseBean = buildGuest.updateEventGuestGroupRSVP(guestRequestBean);
                    if(guestResponseBean!=null && !Utility.isNullOrEmpty(guestResponseBean.getGuestGroupId())) {
                        Text okText = new OkText("Thank You! Your RSVP was successfully update.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to update your RSVP. Please try again later.","account_num") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    }
                }

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(001)","err_mssg") ;
            arrErrorText.add(errorText);

            responseStatus = RespConstants.Status.ERROR;
        }


        responseObject.setErrorMessages(arrErrorText);
        responseObject.setOkMessages(arrOkText);
        responseObject.setResponseStatus(responseStatus);
        responseObject.setJsonResponseObj(jsonResponseObj);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write( responseObject.getJson().toString() );
    }
}