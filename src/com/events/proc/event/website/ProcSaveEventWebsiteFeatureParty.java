package com.events.proc.event.website;

import com.events.bean.event.website.*;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.website.AccessEventWebsite;
import com.events.event.website.AccessEventWebsitePage;
import com.events.event.website.BuildEventParty;
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
 * Date: 2/26/14
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveEventWebsiteFeatureParty extends HttpServlet {
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
                    String sEventWebsiteId =  ParseUtil.checkNull(request.getParameter("event_website_id"));
                    String sEventPartyId =  ParseUtil.checkNull(request.getParameter("event_party_id"));
                    String sEventPartyType =  ParseUtil.checkNull(request.getParameter("event_party_type"));
                    String sUploadId =  ParseUtil.checkNull(request.getParameter("upload_id"));

                    if(Utility.isNullOrEmpty(sEventWebsiteId)){
                        EventWebsiteRequestBean eventWebsiteRequestBean = new EventWebsiteRequestBean();
                        eventWebsiteRequestBean.setEventId( sEventId );

                        AccessEventWebsite accessEventWebsite = new AccessEventWebsite();
                        EventWebsiteBean eventWebsiteBean = accessEventWebsite.getEventWebsite(eventWebsiteRequestBean);

                        if(eventWebsiteBean!=null && !Utility.isNullOrEmpty(eventWebsiteBean.getEventWebsiteId())) {
                            sEventWebsiteId = eventWebsiteBean.getEventWebsiteId();
                        }

                    }



                    String sPartnerNum =  ParseUtil.checkNull(request.getParameter("couple_partner_num"));
                    if(!Utility.isNullOrEmpty(sEventPartyType) ){
                        String sName = ParseUtil.checkNull(request.getParameter("partner"+sPartnerNum+"_name"));
                        String sFacebookUrl = ParseUtil.checkNull(request.getParameter("partner"+sPartnerNum+"_facebook"));
                        String sTwitterUrl = ParseUtil.checkNull(request.getParameter("partner"+sPartnerNum+"_twitter"));
                        String sPinterestUrl = ParseUtil.checkNull(request.getParameter("partner"+sPartnerNum+"_pinterest"));
                        String sDescription = ParseUtil.checkNull(request.getParameter("partner"+sPartnerNum+"_description"));

                        ArrayList<SocialMediaBean> arrSocialMediaBean = new ArrayList<SocialMediaBean>();
                        if(!Utility.isNullOrEmpty(sFacebookUrl)) {
                            SocialMediaBean facebooksocialMediaBean = new SocialMediaBean();
                            facebooksocialMediaBean.setUrl( sFacebookUrl );
                            facebooksocialMediaBean.setSocialMediaType( Constants.SOCIAL_MEDIA_TYPE.FACEBOOK);
                            facebooksocialMediaBean.setEventPartyId( sEventPartyId );
                            arrSocialMediaBean.add( facebooksocialMediaBean );
                        }

                        if(!Utility.isNullOrEmpty(sTwitterUrl)) {
                            SocialMediaBean twitterSocialMediaBean = new SocialMediaBean();
                            twitterSocialMediaBean.setUrl( sTwitterUrl );
                            twitterSocialMediaBean.setSocialMediaType( Constants.SOCIAL_MEDIA_TYPE.TWITTER);
                            twitterSocialMediaBean.setEventPartyId( sEventPartyId );
                            arrSocialMediaBean.add( twitterSocialMediaBean );
                        }

                        if(!Utility.isNullOrEmpty(sPinterestUrl)) {
                            SocialMediaBean pinterestSocialMediaBean = new SocialMediaBean();
                            pinterestSocialMediaBean.setUrl( sPinterestUrl );
                            pinterestSocialMediaBean.setSocialMediaType( Constants.SOCIAL_MEDIA_TYPE.PINTEREST);
                            pinterestSocialMediaBean.setEventPartyId( sEventPartyId );
                            arrSocialMediaBean.add( pinterestSocialMediaBean );
                        }

                        EventPartyRequest eventPartyRequest = new EventPartyRequest();
                        eventPartyRequest.setEventPartyId( sEventPartyId );
                        eventPartyRequest.setEventWebsiteId( sEventWebsiteId );
                        eventPartyRequest.setName( sName );
                        eventPartyRequest.setDescription( sDescription );
                        eventPartyRequest.setArrSocialMediaBean( arrSocialMediaBean );
                        eventPartyRequest.setEventPartyType( Constants.EVENT_PARTY_TYPE.valueOf(sEventPartyType)  );
                        eventPartyRequest.setEventPartyTypeName( sEventPartyType );
                        eventPartyRequest.setUploadId( sUploadId );


                        BuildEventParty buildEventParty = new BuildEventParty();
                        buildEventParty.saveEventParty( eventPartyRequest ) ;

                    }

                    Text okText = new OkText("Your changes were successfully updated.","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEventWebPage - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEventWebPage - 001)","err_mssg") ;
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
