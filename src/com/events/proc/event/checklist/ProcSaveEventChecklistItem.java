package com.events.proc.event.checklist;

import com.events.bean.event.checklist.EventChecklistItemBean;
import com.events.bean.event.checklist.EventChecklistRequestBean;
import com.events.bean.event.checklist.EventChecklistTodoBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.checklist.BuildEventChecklist;
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
 * Date: 7/4/14
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveEventChecklistItem extends HttpServlet {
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

                    String sEventChecklistItemId = ParseUtil.checkNull( request.getParameter("event_checklist_item_id"));
                    String sEventChecklistItemName = ParseUtil.checkNull(request.getParameter("event_checklist_item_name"));
                    String sEventChecklistItemAction = ParseUtil.checkNull(request.getParameter("update_item_status_"+sEventChecklistItemId));

                    boolean isItemActionComplete = false;
                    if( "done".equalsIgnoreCase(sEventChecklistItemAction) || "on".equalsIgnoreCase(sEventChecklistItemAction) ) {
                        isItemActionComplete = true;
                    } else if(  "active".equalsIgnoreCase(sEventChecklistItemAction) ) {
                        isItemActionComplete = false;
                    }

                    String sEventChecklistId = ParseUtil.checkNull(request.getParameter("event_checklist_id"));


                    String[] aEventChecklistTodoId = request.getParameterValues("event_checklist_todo_id[]");
                    ArrayList<String> arrEventChecklistTodoId = new ArrayList<String>();
                    if(aEventChecklistTodoId!=null && aEventChecklistTodoId.length>0){
                        for(String eventChecklistTodoId : aEventChecklistTodoId )  {
                            arrEventChecklistTodoId.add(eventChecklistTodoId);
                        }
                    }

                    ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean = new ArrayList<EventChecklistTodoBean>();
                    if(arrEventChecklistTodoId!=null && !arrEventChecklistTodoId.isEmpty() ) {
                        for(String sEventChecklistTodoId : arrEventChecklistTodoId )  {
                            EventChecklistTodoBean eventChecklistTodoBean = new EventChecklistTodoBean();
                            eventChecklistTodoBean.setEventChecklistTodoId(sEventChecklistTodoId);
                            eventChecklistTodoBean.setEventChecklistItemId(sEventChecklistItemId);
                            eventChecklistTodoBean.setEventChecklistId(sEventChecklistId);
                            String todoAction =  ParseUtil.checkNull( request.getParameter("todo_action_"+sEventChecklistTodoId) );
                            boolean isTodoActionComplete = ParseUtil.sTob(request.getParameter("todo_action_" + sEventChecklistTodoId));
                            /*boolean isTodoActionComplete = false;
                            if( "done".equalsIgnoreCase(todoAction) ) {
                                isTodoActionComplete = true;
                            } else if(  "active".equalsIgnoreCase(todoAction) ) {
                                isTodoActionComplete = false;
                            }*/
                            eventChecklistTodoBean.setComplete( isTodoActionComplete );

                            String sTodo = ParseUtil.checkNull( request.getParameter("checklist_item_todo_"+sEventChecklistTodoId) );
                            if(!Utility.isNullOrEmpty(sTodo)){
                                eventChecklistTodoBean.setName( sTodo );
                                arrEventChecklistTodoBean.add( eventChecklistTodoBean );
                            }

                        }
                    }
                    if( Utility.isNullOrEmpty(sEventChecklistItemName)) {
                        Text errorText = new ErrorText("Please select a valid checklist item name","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else {

                        EventChecklistRequestBean eventChecklistRequestBean = new EventChecklistRequestBean();
                        eventChecklistRequestBean.setChecklistItemName( sEventChecklistItemName );
                        eventChecklistRequestBean.setChecklistItemId( sEventChecklistItemId );
                        eventChecklistRequestBean.setArrEventChecklistTodoBean(arrEventChecklistTodoBean);
                        eventChecklistRequestBean.setComplete( isItemActionComplete );
                        eventChecklistRequestBean.setChecklistId( sEventChecklistId );

                        BuildEventChecklist buildEventChecklist = new BuildEventChecklist();
                        EventChecklistItemBean eventChecklistItemBean = buildEventChecklist.saveEventChecklistItem( eventChecklistRequestBean );
                        jsonResponseObj.put("event_checklist_item_bean", eventChecklistItemBean);

                        Text okText = new OkText("Your changes were saved successfully.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;

                    }



                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveChecklistTemplate - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveChecklistTemplate - 001)","err_mssg") ;
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
