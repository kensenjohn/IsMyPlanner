package com.events.proc.event.guest;

import com.events.bean.event.guest.*;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.guest.AccessGuest;
import com.events.event.guest.BuildGuest;
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
 * Date: 4/13/14
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveGuestRSVP   extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        try{

            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
                String sFirstName = ParseUtil.checkNull(request.getParameter("rsvpFirstName"));
                String sLastName = ParseUtil.checkNull(request.getParameter("rsvpLastName"));
                String sEmail = ParseUtil.checkNull(request.getParameter("rsvpEmail"));
                String sWillYouAttend = ParseUtil.checkNull(request.getParameter("rsvpWillYouAttend"));
                String sNumOfGuests = ParseUtil.checkNull(request.getParameter("rsvpNumOfGuests"));


                boolean isWillYouAttend = ParseUtil.sTob(request.getParameter("rsvpWillYouAttend"));
                Integer iNumOfGuests = ParseUtil.sToI(request.getParameter("rsvpNumOfGuests"));

                boolean isFoodRestrictionAllergyExist = ParseUtil.sTob(request.getParameter("rsvpIsFoodRestrictionAllergyExist"));
                String foodRestrictionAllergyDetails = ParseUtil.checkNull(request.getParameter("rsvpFoodRestrictionAllergyDetails"));


                String guestComments = ParseUtil.checkNull(request.getParameter("rsvpComments"));

                if( Utility.isNullOrEmpty(sFirstName) || Utility.isNullOrEmpty(sLastName) || Utility.isNullOrEmpty(sEmail) || Utility.isNullOrEmpty(sWillYouAttend)
                        || Utility.isNullOrEmpty(sNumOfGuests) )  {
                    appLogging.warn("Invalid request parameters " + sEventId );
                    Text errorText = new ErrorText("To RSVP please fill in all required fields.(saveRsvp - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                } else if (  ParseUtil.isValidInteger(request.getParameter("rsvpNumOfGuests")) ) {
                    Text errorText = new ErrorText("Please use a valid numbers for guests who will attend. (E.g. 1, 2, 34 etc.","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                } else {
                    GuestRequestBean guestRequestBean = new GuestRequestBean();
                    guestRequestBean.setEventId(sEventId);
                    guestRequestBean.setFirstName(sFirstName);
                    guestRequestBean.setLastName(sLastName);
                    guestRequestBean.setEmail(sEmail);
                    guestRequestBean.setHasResponded(true);
                    guestRequestBean.setNotAttending(!isWillYouAttend);
                    guestRequestBean.setRsvpSeats( iNumOfGuests );
                    guestRequestBean.setComments( guestComments );
                    guestRequestBean.setFoodRestrictionAllergyDetails( foodRestrictionAllergyDetails );
                    guestRequestBean.setFoodRestrictionAllergyExists( isFoodRestrictionAllergyExist );

                    AccessGuest accessGuest = new AccessGuest();
                    GuestResponseBean guestResponseBean = accessGuest.loadGuestFromEmail( guestRequestBean );


                    if(guestResponseBean!=null){
                        GuestBean guestBean = guestResponseBean.getGuestBean();
                        if(guestBean!=null){
                            guestRequestBean.setGuestId( ParseUtil.checkNull(guestBean.getGuestId()) );
                        }

                        EventGuestGroupBean eventGuestGroupBean = guestResponseBean.getEventGuestGroupBean();
                        if(eventGuestGroupBean!=null){
                            guestRequestBean.setEventGuestGroupId(ParseUtil.checkNull(eventGuestGroupBean.getEventGuestGroupId()));
                        }

                        GuestGroupBean guestGroupBean = guestResponseBean.getGuestGroupBean();
                        if(guestGroupBean!=null){
                            guestRequestBean.setGuestGroupId(ParseUtil.checkNull(guestGroupBean.getGuestGroupId()));
                        }
                    }
                    guestRequestBean.setHasResponded( true );

                    BuildGuest buildGuest = new BuildGuest();

                    if( !Utility.isNullOrEmpty(guestRequestBean.getGuestId())  &&  !Utility.isNullOrEmpty(guestRequestBean.getEventGuestGroupId()) &&  !Utility.isNullOrEmpty(guestRequestBean.getGuestGroupId()) ) {
                        guestResponseBean = buildGuest.updateEventGuestGroupRSVP(guestRequestBean);

                        buildGuest.deleteGuestGroupFoodRestrictionAllergy( guestRequestBean );
                        //if( !Utility.isNullOrEmpty( guestRequestBean.getFoodRestrictionAllergyDetails())) {
                            guestRequestBean.setGuestGroupFoodRestrictionAllergyId( Utility.getNewGuid() );
                            buildGuest.createGuestGroupFoodRestrictionAllergy(  guestRequestBean );
                        //}

                        if(!Utility.isNullOrEmpty(guestRequestBean.getComments())){
                            guestRequestBean.setGuestGroupCommentId ( Utility.getNewGuid() );
                            buildGuest.createGuestGroupComments( guestRequestBean );
                        }

                    } else {
                        guestRequestBean.setGuestGroupName( "(New)" + ParseUtil.checkNull(guestRequestBean.getFirstName()) + " " + ParseUtil.checkNull(guestRequestBean.getLastName()));
                        guestRequestBean.setComments(guestComments);
                        guestRequestBean.setFoodRestrictionAllergyExists(isFoodRestrictionAllergyExist);
                        guestRequestBean.setFoodRestrictionAllergyDetails(foodRestrictionAllergyDetails);
                        guestResponseBean = buildGuest.saveGuestGroup( guestRequestBean );
                    }

                    if(guestResponseBean!=null && !Utility.isNullOrEmpty(guestResponseBean.getGuestGroupId())) {
                        jsonResponseObj.put("event_id",guestResponseBean.getEventId());
                        jsonResponseObj.put("guestgroup_id",guestResponseBean.getGuestGroupId());
                        jsonResponseObj.put("eventguestgroup_id",guestResponseBean.getEventGuestGroupId());

                        Text okText = new OkText("Your RSVP was successfully saved","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveRSVP - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                }

            }  else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }

        }  catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEvent - 001)","err_mssg") ;
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
