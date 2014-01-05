package com.events.proc.event.guest;

import com.events.bean.event.guest.GuestRequestBean;
import com.events.bean.event.guest.GuestResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.guest.BuildGuest;
import com.events.json.*;
import com.events.vendors.AccessVendors;
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
 * Date: 1/1/14
 * Time: 12:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveGuest   extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        try{

            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {

                    VendorRequestBean vendorRequestBean = new VendorRequestBean();
                    vendorRequestBean.setUserId( loggedInUserBean.getUserId() );
                    AccessVendors accessVendor = new AccessVendors();
                    VendorBean vendorBean = accessVendor.getVendorByUserId( vendorRequestBean ) ;  // get  vendor from user id

                    String sEventId = ParseUtil.checkNull(request.getParameter("eventId"));
                    String sGuestGroupId = ParseUtil.checkNull(request.getParameter("guestGroupId"));
                    String sEventGuestGroupId = ParseUtil.checkNull(request.getParameter("eventGuestGroupId"));

                    String sGuestGroupName = ParseUtil.checkNull(request.getParameter("groupName"));
                    String sGuestFirstName = ParseUtil.checkNull(request.getParameter("guestFirstName"));
                    String sGuestLastName = ParseUtil.checkNull(request.getParameter("guestLastName"));
                    String sGuestEmail = ParseUtil.checkNull(request.getParameter("guestEmail"));
                    String sGuestCompanyName = ParseUtil.checkNull(request.getParameter("guestCompanyName"));
                    String sGuestPhone1 = ParseUtil.checkNull(request.getParameter("guestPhone1"));
                    String sGuestPhone2 = ParseUtil.checkNull(request.getParameter("guestPhone2"));
                    String sGuestAddress1 = ParseUtil.checkNull(request.getParameter("guestAddress1"));
                    String sGuestAddress2 = ParseUtil.checkNull(request.getParameter("guestAddress2"));
                    String sGuestCity = ParseUtil.checkNull(request.getParameter("guestCity"));
                    String sGuestState = ParseUtil.checkNull(request.getParameter("guestState"));
                    String sGuestCountry = ParseUtil.checkNull(request.getParameter("guestCountry"));
                    String sGuestPostalCode = ParseUtil.checkNull(request.getParameter("guestPostalCode"));

                    int sGuestInvitedSeats = ParseUtil.sToI(request.getParameter("guestInvitedSeats"));
                    int sGuestRSVPSeats = ParseUtil.sToI(request.getParameter("guestRSVP"));
                    boolean willNotAttend = ParseUtil.sTob(request.getParameter("guestInvitedSeats"));

                    if( Utility.isNullOrEmpty(sEventId) ) {
                        appLogging.info("Invalid Event ID request in Proc Page sEventId " + sEventId );
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEvent - 002)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if( Utility.isNullOrEmpty(sGuestFirstName) || sGuestInvitedSeats<=0) {
                        appLogging.info("Please fill in all required fields");
                        Text errorText = new ErrorText("Please fill in all required fields","status_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else if(sGuestInvitedSeats<sGuestRSVPSeats || sGuestRSVPSeats<=0 ) {
                        appLogging.info("Invalid RSVP number used");
                        Text errorText = new ErrorText("Please fill in all required fields","status_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    }
                    else {
                        if(Utility.isNullOrEmpty(sGuestGroupName)){
                            sGuestGroupName = ParseUtil.checkNull(ParseUtil.checkNull(sGuestFirstName) + " " + ParseUtil.checkNull(sGuestLastName));
                        }
                        GuestRequestBean guestRequestBean = new GuestRequestBean();
                        guestRequestBean.setEventId(sEventId);
                        guestRequestBean.setGuestGroupId(sGuestGroupId);
                        guestRequestBean.setGuestGroupName(sGuestGroupName);

                        guestRequestBean.setEventGuestGroupId(sEventGuestGroupId);

                        guestRequestBean.setFirstName(sGuestFirstName);
                        guestRequestBean.setLastName(sGuestLastName);
                        guestRequestBean.setCompany(sGuestCompanyName);
                        guestRequestBean.setAddress1(sGuestAddress1);
                        guestRequestBean.setAddress2(sGuestAddress2);
                        guestRequestBean.setCity(sGuestCity);
                        guestRequestBean.setState(sGuestState);
                        guestRequestBean.setCountry(sGuestCountry);
                        guestRequestBean.setZipCode(sGuestPostalCode);

                        guestRequestBean.setEmail(sGuestEmail);
                        guestRequestBean.setPhone1(sGuestPhone1);
                        guestRequestBean.setPhone2(sGuestPhone2);

                        guestRequestBean.setInvitedSeats(sGuestInvitedSeats);
                        guestRequestBean.setRsvpSeats(sGuestRSVPSeats);

                        guestRequestBean.setNotAttending(willNotAttend);


                        BuildGuest buildGuest = new BuildGuest();
                        GuestResponseBean guestResponseBean = buildGuest.saveGuestGroup( guestRequestBean );

                        if(guestResponseBean!=null && !Utility.isNullOrEmpty(guestResponseBean.getGuestGroupId())) {

                            jsonResponseObj.put("event_id",guestResponseBean.getEventId());
                            jsonResponseObj.put("guestgroup_id",guestResponseBean.getGuestGroupId());
                            jsonResponseObj.put("eventguestgroup_id",guestResponseBean.getEventGuestGroupId());

                            Text okText = new OkText("The guest groups was successfully saved","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            appLogging.info("There was error while trying to save Guest" + ParseUtil.checkNullObject(loggedInUserBean) );
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEvent - 002)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    }



                }   else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEvent - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            }  else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString() );
                responseObject = DataSecurityChecker.getInsecureInputResponse( this.getClass().getName() );
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
