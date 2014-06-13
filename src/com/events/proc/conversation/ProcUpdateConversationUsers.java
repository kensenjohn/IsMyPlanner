package com.events.proc.conversation;

import com.events.bean.common.conversation.ConversationRequestBean;
import com.events.bean.users.UserBean;
import com.events.common.*;
import com.events.common.conversation.BuildConversation;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
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

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/12/14
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcUpdateConversationUsers extends HttpServlet {
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
                    String sAction = ParseUtil.checkNull(request.getParameter("action"));
                    String sUserId = ParseUtil.checkNull(request.getParameter("user_id"));
                    String sConversationId = ParseUtil.checkNull(request.getParameter("conversation_id"));

                    if(!Utility.isNullOrEmpty(sConversationId)) {
                        boolean canAddUsersToConversation = false;
                        boolean isAddUserAction = false;

                        boolean canDeleteUsersFromConversation = false;
                        boolean isDeleteUserAction = false;

                        if( "selected".equalsIgnoreCase(sAction) ) {
                            isAddUserAction = true;
                        } else if( "deselected".equalsIgnoreCase(sAction)) {
                            isDeleteUserAction = true;
                        }
                        CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
                        if(checkPermission!=null ) {
                            if( isAddUserAction && checkPermission.can(Perm.ADD_USERS_TO_OLD_CONVERSATION) ) {
                                canAddUsersToConversation = true;
                            } else if( isDeleteUserAction && checkPermission.can(Perm.ADD_USERS_TO_OLD_CONVERSATION) ) {
                                canDeleteUsersFromConversation = true;
                            }
                        }

                        jsonResponseObj.put("user_id", sUserId);
                        jsonResponseObj.put("action", sAction);
                        if(isAddUserAction && !canAddUsersToConversation)  {

                            Text okText = new OkText("You do not have permission to add a new user to this conversation. Please contact your support representative.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;

                            jsonResponseObj.put("is_success",false);

                        }  else if( isDeleteUserAction && !canDeleteUsersFromConversation) {

                            Text okText = new OkText("You do not have permission to delete users from this conversation. Please contact your support representative.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;

                            jsonResponseObj.put("is_success",false);
                        }  else if ( (isAddUserAction && canAddUsersToConversation) || (isDeleteUserAction && canDeleteUsersFromConversation) ) {
                           ArrayList<String> arrConversationUserId = new ArrayList<String>();
                            arrConversationUserId.add( sUserId );

                            ConversationRequestBean conversationRequestBean = new ConversationRequestBean();
                            conversationRequestBean.setConversationId( sConversationId );
                            conversationRequestBean.setArrConversationUserId( arrConversationUserId );

                            Integer iNumOfRows = 0;

                            BuildConversation buildConversation = new BuildConversation();
                            if( isAddUserAction ){
                                iNumOfRows = buildConversation.addUserToConversation(  conversationRequestBean  );
                            } else if( isDeleteUserAction ) {
                                iNumOfRows = buildConversation.deleteUserFromConversation(  conversationRequestBean  );
                            }

                            if(iNumOfRows>0 && isAddUserAction){
                                Text okText = new OkText("","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                                jsonResponseObj.put("is_success",true);
                            } else if(iNumOfRows>0 && isDeleteUserAction){
                                Text okText = new OkText("","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                                jsonResponseObj.put("is_success",true);
                            } else {
                                Text okText = new OkText("We were unable to process your request. Please try again later.","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                                jsonResponseObj.put("is_success",false);
                            }

                        } else {
                            Text errorText = new ErrorText("We were unable to process your request. Please refresh and try again.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }


                    } else {
                        Text okText = new OkText("","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
                        jsonResponseObj.put("is_success",true);
                    }




                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(updateConversationUser - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(updateConversationUser - 001)","err_mssg") ;
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
