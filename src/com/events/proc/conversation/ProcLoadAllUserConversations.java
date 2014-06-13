package com.events.proc.conversation;

import com.events.bean.common.conversation.ConversationBean;
import com.events.bean.common.conversation.ConversationRequestBean;
import com.events.bean.common.conversation.ConversationResponseBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.*;
import com.events.common.conversation.AccessConversation;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import com.events.users.permissions.CheckPermission;
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
 * Date: 6/3/14
 * Time: 5:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadAllUserConversations extends HttpServlet {
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

                    boolean isHiddenConversation = ParseUtil.sTob( request.getParameter("hidden_conversations") );
                    boolean isDeletedConversation = ParseUtil.sTob( request.getParameter("deleted_conversations") );

                    ConversationRequestBean conversationRequestBean = new ConversationRequestBean();
                    conversationRequestBean.setCurrentUserId( loggedInUserBean.getUserId() );
                    conversationRequestBean.setHiddenConversation( isHiddenConversation );
                    conversationRequestBean.setDeletedConversation( isDeletedConversation );

                    UserRequestBean userRequestBean = new UserRequestBean();
                    userRequestBean.setUserId( loggedInUserBean.getUserId() );

                    String sTimeZone  = Constants.TIME_ZONE.central.getJavaTimeZone();
                    AccessUsers accessUsers = new AccessUsers();
                    UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );
                    if(userInfoBean!=null && !Utility.isNullOrEmpty(userInfoBean.getTimezone())) {
                        sTimeZone = Constants.TIME_ZONE.valueOf(ParseUtil.checkNull( userInfoBean.getTimezone() )).getJavaTimeZone();
                    }

                    conversationRequestBean.setTimeZone( sTimeZone );

                    AccessConversation accessConversation = new AccessConversation();
                    ConversationResponseBean conversationResponseBean = new ConversationResponseBean();

                    ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser(loggedInUserBean);
                    conversationRequestBean.setVendorId( parentTypeBean.getVendorId() );

                    conversationResponseBean = accessConversation.loadAllUserConversations(conversationRequestBean);

                    Long lNumOfConversations = 0L;
                    if(conversationResponseBean!=null){
                        ArrayList<ConversationBean> arrConversationBean = conversationResponseBean.getArrConversationBean();


                        if(arrConversationBean!=null && !arrConversationBean.isEmpty() ){
                            JSONObject jsonConversation = accessConversation.getJsonConversations(arrConversationBean);
                            if(jsonConversation!=null){
                                lNumOfConversations = jsonConversation.optLong("num_of_conversations");
                                if(lNumOfConversations>0){
                                    jsonResponseObj.put("all_conversations", jsonConversation );
                                }
                            }
                        }


                        HashMap<String, ArrayList<String>> hmUserNames =  conversationResponseBean.getHmUserNames();
                        if( hmUserNames!=null && !hmUserNames.isEmpty() ) {
                            JSONObject jsonAllConversationUsers = accessConversation.getJsonUserConversations(hmUserNames);
                            if(jsonAllConversationUsers!=null){
                                jsonResponseObj.put("conversation_users", jsonAllConversationUsers );
                            }
                        }
                    }
                    jsonResponseObj.put("num_of_conversations", lNumOfConversations );
                    jsonResponseObj.put("current_user_id", loggedInUserBean.getUserId());


                    Text okText = new OkText("Load of Conversation Complete.","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllConversation - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadAllConversation - 001)","err_mssg") ;
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