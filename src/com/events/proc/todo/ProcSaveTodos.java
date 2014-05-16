package com.events.proc.todo;

import com.events.bean.common.todo.ToDoBean;
import com.events.bean.common.todo.ToDoRequestBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.common.todo.BuildToDo;
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
 * Date: 5/9/14
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveTodos extends HttpServlet {
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
                    String todoId = ParseUtil.checkNull(request.getParameter("todo_id"));
                    String todo = ParseUtil.checkNull(request.getParameter("todo"));
                    String todoDate = ParseUtil.checkNull(request.getParameter("todoDate"));
                    String todoTimeZone = ParseUtil.checkNull(request.getParameter("todoTimeZone"));
                    String todoStatus = ParseUtil.checkNull(request.getParameter("todoStatus"));
                    String currentTodoUser = ParseUtil.checkNull(request.getParameter("current_todo_user"));
                    String[] sArrAssignedUserId = request.getParameterValues("todoUsers");
                    String todoEventId = ParseUtil.checkNull(request.getParameter("todoEvents"));

                    if( Utility.isNullOrEmpty(todo) ) {
                        Text errorText = new ErrorText("Please make sure to enter a valid To Do item.","account_num") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else if( Utility.isNullOrEmpty( todoStatus ) ) {
                        Text errorText = new ErrorText("Please make sure to enter a valid To Do status.","account_num") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        ToDoRequestBean toDoRequestBean = new ToDoRequestBean();
                        toDoRequestBean.setTodoId( todoId );
                        toDoRequestBean.setToDo( todo );
                        toDoRequestBean.setHumanTodoDate( todoDate );
                        toDoRequestBean.setTodoTimeZone( todoTimeZone );
                        toDoRequestBean.setTodoStatus( Constants.TODO_STATUS.valueOf( todoStatus ) );

                        AccessUsers accessUsers = new AccessUsers();
                        ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser( loggedInUserBean );

                        if(parentTypeBean!=null){
                            toDoRequestBean.setVendorId( ParseUtil.checkNull(parentTypeBean.getVendorId()) );
                            toDoRequestBean.setClientId(  ParseUtil.checkNull(parentTypeBean.getClientdId()) );
                            toDoRequestBean.setUserId(  ParseUtil.checkNull(loggedInUserBean.getUserId() )  );

                            if( parentTypeBean.isUserAVendor() ){
                                toDoRequestBean.setUserType( Constants.USER_TYPE.VENDOR);
                            } else if(parentTypeBean.isUserAClient()) {
                                toDoRequestBean.setUserType( Constants.USER_TYPE.CLIENT);
                            }
                        }
                        ArrayList<String> arrAssignedUserId = new ArrayList<String>();
                        if(sArrAssignedUserId!=null && sArrAssignedUserId.length > 0 ) {

                            for(String sAssignedUserId : sArrAssignedUserId ) {
                                arrAssignedUserId.add(  sAssignedUserId  );
                            }
                        }
                        arrAssignedUserId.add( currentTodoUser );
                        toDoRequestBean.setArrAssignedUserId( arrAssignedUserId );
                        toDoRequestBean.setTodoEventId( todoEventId );

                        BuildToDo buildToDo = new BuildToDo();
                        ToDoBean toDoBean = buildToDo.saveToDo( toDoRequestBean );
                        if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId() )){
                            jsonResponseObj.put("todo_bean",toDoBean.toJson());
                            Text okText = new OkText("Your changes were saved successfully.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveTodo - 003)","account_num") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
                        }

                    }


                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveTodo - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveTodo - 001)","err_mssg") ;
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