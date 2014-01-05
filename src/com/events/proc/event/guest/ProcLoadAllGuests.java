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
import com.events.event.guest.AccessGuest;
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
 * Date: 12/31/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadAllGuests  extends HttpServlet {
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

                    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));

                    if(!Utility.isNullOrEmpty(sEventId)) {
                        GuestRequestBean guestRequestBean = new GuestRequestBean();
                        guestRequestBean.setEventId(sEventId);

                        AccessGuest accessGuest = new AccessGuest();
                        GuestResponseBean guestResponseBean = accessGuest.loadEveryEventsGuests(guestRequestBean);

                        if(guestResponseBean!=null && guestResponseBean.getArrEveryEventGuestGroup()!=null ){
                            Integer iNumOfEveryEventsGuests = guestResponseBean.getArrEveryEventGuestGroup().size();
                            if( iNumOfEveryEventsGuests > 0 ) {
                                JSONObject jsonEveryEventsGuests = accessGuest.getEveryEventsGuestsJson( guestResponseBean );
                                jsonResponseObj.put("every_event_guests", jsonEveryEventsGuests );
                            }
                            jsonResponseObj.put("num_of_guests", iNumOfEveryEventsGuests );

                            Text okText = new OkText("Loading of Every Guest completed","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;


                        } else {
                            appLogging.info("Invalid  response after trying to load guest response: " + ParseUtil.checkNullObject(guestResponseBean) );
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllGuest - 004)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    } else {
                        appLogging.info("Invalid event Id used : " + sEventId );
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllGuest - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }


                }  else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllGuest - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }
            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString() );
                responseObject = DataSecurityChecker.getInsecureInputResponse( this.getClass().getName() );
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