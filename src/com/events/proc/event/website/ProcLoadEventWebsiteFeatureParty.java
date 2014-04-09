package com.events.proc.event.website;

import com.events.bean.common.FeatureBean;
import com.events.bean.event.EventRequestBean;
import com.events.bean.event.website.*;
import com.events.bean.upload.UploadBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.feature.FeatureType;
import com.events.common.security.DataSecurityChecker;
import com.events.event.AccessEvent;
import com.events.event.website.AccessEventParty;
import com.events.event.website.AccessEventWebsite;
import com.events.event.website.AccessEventWebsitePage;
import com.events.event.website.AccessSocialMedia;
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
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/27/14
 * Time: 5:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadEventWebsiteFeatureParty extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) ) {
                    String sUserId = ParseUtil.checkNull(loggedInUserBean.getUserId());
                    String sEventId =  ParseUtil.checkNull(request.getParameter("event_id"));
                    String sPageType =  ParseUtil.checkNull(request.getParameter("page_type"));
                    String[] strEventPartyTypeArray =  request.getParameterValues("event_party_type[]");
                    /*
                        <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.GROOMSMAN%>"/>
    <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.BESTMAN%>"/>
                     */
                    if(!Utility.isNullOrEmpty(sEventId) && !Utility.isNullOrEmpty(sPageType) && strEventPartyTypeArray!=null && strEventPartyTypeArray.length>0) {
                        EventWebsiteRequestBean eventWebsiteRequestBean = new EventWebsiteRequestBean();
                        eventWebsiteRequestBean.setEventId( sEventId );

                        AccessEventWebsite accessEventWebsite = new AccessEventWebsite();
                        EventWebsiteBean eventWebsiteBean = accessEventWebsite.getEventWebsite(eventWebsiteRequestBean);
                        if(eventWebsiteBean!=null && !Utility.isNullOrEmpty(eventWebsiteBean.getEventWebsiteId())) {

                            EventWebsitePageBean eventWebsitePageBeanReg = new EventWebsitePageBean();
                            eventWebsitePageBeanReg.setEventWebsiteId( eventWebsiteBean.getEventWebsiteId() );
                            eventWebsitePageBeanReg.setWebsiteThemeId( eventWebsiteBean.getWebsiteThemeId() );
                            eventWebsitePageBeanReg.setType( sPageType );


                            AccessEventWebsitePage accessEventWebsitePage = new AccessEventWebsitePage();
                            EventWebsitePageBean eventWebsitePageBean = accessEventWebsitePage.getEventWebsitePageByType(eventWebsitePageBeanReg);

                            if(eventWebsitePageBean!=null && !Utility.isNullOrEmpty(eventWebsitePageBean.getEventWebsitePageId() )) {

                                ArrayList<Constants.EVENT_PARTY_TYPE> arrEventPartType = new ArrayList<Constants.EVENT_PARTY_TYPE>();
                                for(String sEventPartyType : strEventPartyTypeArray ){
                                    arrEventPartType.add( Constants.EVENT_PARTY_TYPE.valueOf(sEventPartyType));
                                }
                                EventPartyRequest eventPartyRequest = new EventPartyRequest();
                                eventPartyRequest.setEventWebsiteId(eventWebsiteBean.getEventWebsiteId());
                                eventPartyRequest.setArrEventPartyType(arrEventPartType);

                                AccessEventParty accessEventParty = new AccessEventParty();
                                ArrayList<EventPartyBean> arrEventPartyBean = accessEventParty.getEventPartyListByTypeAndWebsite(eventPartyRequest);

                                HashMap<String,UploadBean> hmUploadBean = accessEventParty.getEventPartyImage( arrEventPartyBean );

                                AccessSocialMedia accessSocialMedia = new AccessSocialMedia();
                                ArrayList<SocialMediaBean> arrSocialMediaBean =  accessSocialMedia.getSocialMedia(arrEventPartyBean);

                                JSONObject eventPartyJson = accessEventParty.getEventPartyJson( arrEventPartyBean , arrSocialMediaBean,hmUploadBean );

                                EventRequestBean eventRequestBean = new EventRequestBean();
                                eventRequestBean.setEventId( sEventId );

                                AccessEvent accessEvent = new AccessEvent();
                                FeatureBean featureBean = accessEvent.getFeatureValue( eventRequestBean, FeatureType.image_location);

                                Integer iNumOFEventParty = 0;
                                if(eventPartyJson!=null){
                                    iNumOFEventParty = eventPartyJson.optInt("num_of_event_party");
                                    if(iNumOFEventParty>0){
                                        jsonResponseObj.put("event_party" , eventPartyJson);
                                    }
                                }
                                jsonResponseObj.put("num_of_event_party" , iNumOFEventParty);
                                jsonResponseObj.put("event_website" , eventWebsiteBean.toJson());
                                jsonResponseObj.put("event_website_page" , eventWebsitePageBean.toJson());
                                jsonResponseObj.put("page_type" , sPageType );
                                jsonResponseObj.put("image_host", Utility.getImageUploadHost() );
                                jsonResponseObj.put("bucket", Utility.getS3Bucket() );
                                jsonResponseObj.put("image_folder_location", featureBean.getValue() );

                                Text okText = new OkText("Website Themes loaded","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                Text errorText = new ErrorText("Please select a theme for this website. We were unable to load the the requested information.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }

                        }  else {
                            Text errorText = new ErrorText("Please select a theme for this website. We were unable to load page information.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEventParty - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEventParty - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEventParty - 001)","err_mssg") ;
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