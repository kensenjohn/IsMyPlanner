package com.events.proc.event.guest;

import com.events.bean.event.guest.*;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.guest.AccessGuest;
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
 * Date: 1/2/14
 * Time: 11:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadGuest  extends HttpServlet {
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
                    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
                    String sGuestGroupId = ParseUtil.checkNull(request.getParameter("guestGroupId"));
                    if(!Utility.isNullOrEmpty(sEventId) && !Utility.isNullOrEmpty(sGuestGroupId)) {
                        GuestRequestBean guestRequestBean = new GuestRequestBean();
                        guestRequestBean.setEventId( sEventId );
                        guestRequestBean.setGuestGroupId( sGuestGroupId );

                        AccessGuest accessGuest = new AccessGuest();
                        GuestResponseBean guestResponseBean = accessGuest.loadGuest(guestRequestBean);
                        if(guestResponseBean!=null && guestResponseBean.getEventGuestGroupBean()!=null){

                            jsonResponseObj.put(  "event_guest_group",guestResponseBean.getEventGuestGroupBean().toJson() );
                            jsonResponseObj.put(  "guest_group",guestResponseBean.getGuestGroupBean().toJson() );
                            jsonResponseObj.put(  "guest",guestResponseBean.getGuestBean().toJson() );

                            ArrayList<GuestGroupPhoneBean>  arrGuestGroupPhoneBean = guestResponseBean.getArrGuestGroupPhoneBean();
                            if(arrGuestGroupPhoneBean!=null && !arrGuestGroupPhoneBean.isEmpty()) {
                                JSONObject jsonGuestGroupPhone = new JSONObject();
                                Integer iNumOfPhone = 0;
                                for(GuestGroupPhoneBean guestGroupPhoneBean : arrGuestGroupPhoneBean ){
                                    jsonGuestGroupPhone.put( ParseUtil.iToS(iNumOfPhone), guestGroupPhoneBean.toJson());
                                    iNumOfPhone++;
                                }
                                jsonResponseObj.put(  "num_of_guest_group_phone", arrGuestGroupPhoneBean.size() );
                                jsonResponseObj.put(  "guest_group_phone", jsonGuestGroupPhone );
                            }

                            ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = guestResponseBean.getArrGuestGroupEmailBean();
                            if(arrGuestGroupEmailBean!=null && !arrGuestGroupEmailBean.isEmpty()) {
                                JSONObject jsonGuestGroupEmail = new JSONObject();
                                Integer iNumOfEmail = 0;
                                for(GuestGroupEmailBean guestGroupEmailBean : arrGuestGroupEmailBean ){
                                    jsonGuestGroupEmail.put( ParseUtil.iToS(iNumOfEmail), guestGroupEmailBean.toJson());
                                    iNumOfEmail++;
                                }
                                jsonResponseObj.put(  "num_of_guest_group_email", arrGuestGroupEmailBean.size() );
                                jsonResponseObj.put(  "guest_group_email", jsonGuestGroupEmail );
                            }

                            ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = guestResponseBean.getArrGuestGroupAddressBean();
                            if(arrGuestGroupAddressBean!=null && !arrGuestGroupAddressBean.isEmpty()) {
                                JSONObject jsonGuestGroupAddress = new JSONObject();
                                Integer iNumOfEmail = 0;
                                for(GuestGroupAddressBean guestGroupAddressBean : arrGuestGroupAddressBean ){
                                    jsonGuestGroupAddress.put( ParseUtil.iToS(iNumOfEmail), guestGroupAddressBean.toJson());
                                    iNumOfEmail++;
                                }
                                jsonResponseObj.put(  "num_of_guest_group_address", arrGuestGroupAddressBean.size() );
                                jsonResponseObj.put(  "guest_group_address", jsonGuestGroupAddress );
                            }

                            GuestGroupFoodRestrictionAllergyBean guestGroupFoodRestrictionAllergyBean = guestResponseBean.getGuestGroupFoodRestrictionAllergyBean();
                            if(guestGroupFoodRestrictionAllergyBean!=null && !Utility.isNullOrEmpty(guestGroupFoodRestrictionAllergyBean.getGuestGroupFoodRestrictionAllergyId())) {
                                JSONObject jsonGuestGroupFoodRestrictionAllergy = new JSONObject();
                                jsonResponseObj.put(  "guest_group_food_restriction_allergy", guestGroupFoodRestrictionAllergyBean.toJson() );
                            }

                            ArrayList<GuestGroupCommentsBean> arrGuestGroupCommentsBeans = guestResponseBean.getArrGuestGroupCommentsBean();
                            if(arrGuestGroupCommentsBeans!=null && !arrGuestGroupCommentsBeans.isEmpty()){
                                JSONObject jsonGuestGroupComments = new JSONObject();
                                Integer iNumOfComments = 0;
                                for(GuestGroupCommentsBean guestGroupCommentsBean : arrGuestGroupCommentsBeans ) {
                                    jsonGuestGroupComments.put( ParseUtil.iToS(iNumOfComments), guestGroupCommentsBean.toJson());
                                    iNumOfComments++;
                                }
                                jsonResponseObj.put(  "num_of_guest_group_comments", arrGuestGroupCommentsBeans.size() );
                                jsonResponseObj.put(  "guest_group_comments", jsonGuestGroupComments );
                            }


                            Text okText = new OkText("The event was saved successfully","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            appLogging.info("Invalid Guest Response Bean : " + ParseUtil.checkNullObject(guestResponseBean) );
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllGuest - 004)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                    }  else {
                        appLogging.info("Invalid event Id used : " + sEventId + " sGuestGroupId : " + sGuestGroupId );
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllGuest - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadGuest - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
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