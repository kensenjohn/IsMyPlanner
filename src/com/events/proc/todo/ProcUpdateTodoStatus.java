package com.events.proc.todo;

import com.events.bean.common.todo.ToDoBean;
import com.events.bean.common.todo.ToDoRequestBean;
import com.events.bean.common.todo.TodoReminderScheduleBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.common.todo.AccessToDo;
import com.events.common.todo.BuildToDo;
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
 * Date: 5/13/14
 * Time: 11:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcUpdateTodoStatus extends HttpServlet {
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
                    String todoStatus = ParseUtil.checkNull(request.getParameter("todo_status"));
                    boolean isToDoStatus = ParseUtil.sTob( request.getParameter("todo_status") );

                    ToDoRequestBean toDoRequestBean = new ToDoRequestBean();
                    toDoRequestBean.setTodoId( todoId );

                    AccessToDo accessToDo = new AccessToDo();
                    ToDoBean toDoBean = accessToDo.getTodo( toDoRequestBean );
                    if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId()) ) {
                        toDoRequestBean.setToDo( toDoBean.getToDo() );
                        toDoRequestBean.setClientId( ParseUtil.checkNull(toDoBean.getClientId()) );
                        toDoRequestBean.setUserId( toDoBean.getUserId() );
                        toDoRequestBean.setVendorId( toDoBean.getVendorId() );
                        toDoRequestBean.setTodoTimeZone(  toDoBean.getTodoTimeZone() );
                        toDoRequestBean.setHumanTodoDate(  toDoBean.getHumanToDoDate() );
                        toDoRequestBean.setTodoDate(  toDoBean.getToDoDate() );
                        toDoRequestBean.setUserType( toDoBean.getUserType() );
                        if(isToDoStatus){
                            toDoRequestBean.setTodoStatus( Constants.TODO_STATUS.COMPLETE );
                        } else {
                            toDoRequestBean.setTodoStatus( Constants.TODO_STATUS.ACTIVE );
                        }


                        BuildToDo buildToDo = new BuildToDo();
                        buildToDo.updateTodo( toDoRequestBean );
                    }

                    Text okText = new OkText("The schedule was saved successfully.","status_mssg") ;
                    arrOkText.add(okText);
                    responseStatus = RespConstants.Status.OK;

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