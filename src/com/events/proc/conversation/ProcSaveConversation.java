package com.events.proc.conversation;

import com.events.bean.common.conversation.*;
import com.events.bean.upload.UploadBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.conversation.BuildConversation;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
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
 * Date: 6/2/14
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveConversation extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

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
                    String sConversationId = ParseUtil.checkNull(request.getParameter("conversation_id"));
                    String sConversationName = ParseUtil.checkNull(request.getParameter("conversation_name"));
                    String sConversationBody = ParseUtil.checkNull(request.getParameter("conversation_body"));
                    String[] strConversationUsers = request.getParameterValues("conversation_users");
                    String sCurrentConversationUserId = ParseUtil.checkNull(request.getParameter("current_conversation_user"));
                    String[] strUploadId = request.getParameterValues("upload_id");

                    ArrayList<String> arrConversationUserId = new ArrayList<String>();
                    if(strConversationUsers!=null && strConversationUsers.length > 0 ) {

                        for(String sConversationUserId : strConversationUsers ) {
                            arrConversationUserId.add(  sConversationUserId  );
                        }
                    }

                    ArrayList<String> arrUploadId = new ArrayList<String>();
                    if(strUploadId!=null && strUploadId.length > 0 ) {

                        for(String sUploadId : strUploadId ) {
                            arrUploadId.add(  sUploadId  );
                        }
                    }

                    if(arrConversationUserId!=null && arrConversationUserId.isEmpty()){
                        Text errorText = new ErrorText("Please select at least one user.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if( Utility.isNullOrEmpty(sConversationName)){
                        Text errorText = new ErrorText("Please select a valid Conversation Name","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if( Utility.isNullOrEmpty(sConversationBody)){
                        Text errorText = new ErrorText("Please enter a valid message.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        arrConversationUserId.add( sCurrentConversationUserId );

                        UserRequestBean userRequestBean = new UserRequestBean();
                        userRequestBean.setUserId( loggedInUserBean.getUserId() );

                        String sTimeZone  = Constants.TIME_ZONE.central.getJavaTimeZone();
                        AccessUsers accessUsers = new AccessUsers();
                        UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );
                        if(userInfoBean!=null && !Utility.isNullOrEmpty(userInfoBean.getTimezone())) {
                            sTimeZone = Constants.TIME_ZONE.valueOf(ParseUtil.checkNull( userInfoBean.getTimezone() )).getJavaTimeZone();
                        }

                        ConversationRequestBean conversationRequestBean = new ConversationRequestBean();
                        conversationRequestBean.setConversationId( sConversationId );
                        conversationRequestBean.setConversationName( sConversationName );
                        conversationRequestBean.setConversationBody( sConversationBody );
                        conversationRequestBean.setCurrentUserId( sCurrentConversationUserId );
                        conversationRequestBean.setArrConversationUserId( arrConversationUserId );
                        conversationRequestBean.setTimeZone( sTimeZone );
                        conversationRequestBean.setArrUploadId( arrUploadId );

                        BuildConversation buildConversation = new BuildConversation();
                        ConversationResponseBean conversationResponseBean = buildConversation.saveConversation( conversationRequestBean );

                        if(conversationResponseBean!=null){
                            ConversationBean conversationBean = conversationResponseBean.getConversationBean();
                            ArrayList<UserConversationBean> arrUserConversationBean = conversationResponseBean.getArrUserConversationBean();
                            ConversationMessageBean conversationMessageBean = conversationResponseBean.getConversationMessageBean();
                            ConversationMessageUserBean conversationMessageUserBean = conversationResponseBean.getConversationMessageUserBean();

                            if(conversationBean!=null && !Utility.isNullOrEmpty(conversationBean.getConversationId())){
                                jsonResponseObj.put("conversation_bean" , conversationBean.toJson() );
                                jsonResponseObj.put("conversation_message_bean" , conversationMessageBean.toJson() );
                                jsonResponseObj.put("conversation_message_user_bean" , conversationMessageUserBean.toJson() );
                                if(arrUserConversationBean!=null && !arrUserConversationBean.isEmpty() ){
                                    Integer iNumOfUsers = arrUserConversationBean.size();
                                    JSONObject jsonUserConversation = new JSONObject();
                                    Integer iTrackNumOfUsers = 0;
                                    for(UserConversationBean userConversationBean : arrUserConversationBean ){
                                        jsonUserConversation.put( ParseUtil.iToI(iTrackNumOfUsers).toString() , userConversationBean.toJson() );
                                        iTrackNumOfUsers++;
                                    }
                                    jsonResponseObj.put("user_conversations" , jsonUserConversation );
                                }

                                ArrayList<UploadBean> arrUploadBean = conversationResponseBean.getArrUploadBean();
                                if(arrUploadBean!=null && !arrUploadBean.isEmpty()){
                                    JSONObject jsonUploadBean = new JSONObject();
                                    Long lNumOfUploads = 0L;
                                    for(UploadBean uploadBean : arrUploadBean ){
                                        jsonUploadBean.put( ParseUtil.LToS(lNumOfUploads) ,uploadBean.toJson() );
                                        lNumOfUploads++;
                                    }
                                    jsonResponseObj.put("message_attachments" , jsonUploadBean );
                                    jsonResponseObj.put("num_of_attachments" , lNumOfUploads );

                                    jsonResponseObj.put("shared_file_host", Utility.getSharedFileHost() );
                                    jsonResponseObj.put("s3_bucket", Utility.getS3Bucket() );
                                }

                                Text okText = new OkText("Load of Conversation Complete.","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else{
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveConversation - 003)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }


                        }
                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveConversation - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveConversation - 001)","err_mssg") ;
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