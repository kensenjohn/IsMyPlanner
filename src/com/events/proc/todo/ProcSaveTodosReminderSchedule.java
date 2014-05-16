package com.events.proc.todo;

import com.events.bean.DateObject;
import com.events.bean.common.todo.ToDoBean;
import com.events.bean.common.todo.ToDoRequestBean;
import com.events.bean.common.todo.TodoReminderScheduleBean;
import com.events.bean.users.UserBean;
import com.events.common.*;
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
 * Date: 5/12/14
 * Time: 1:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveTodosReminderSchedule extends HttpServlet {
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
                    String todoReminderId = ParseUtil.checkNull(request.getParameter("todo_reminder_id"));
                    String todoId = ParseUtil.checkNull(request.getParameter("todo_id"));
                    String todoReminderDay = ParseUtil.checkNull(request.getParameter("todo_reminder_day"));
                    String todoReminderTime = ParseUtil.checkNull(request.getParameter("todo_reminder_time"));
                    String todoReminderTimeZone = ParseUtil.checkNull(request.getParameter("todo_reminder_timezone"));
                    /*
                    todo_reminder_day:13 May, 2014
                    todo_reminder_time:8:00 AM
                    todo_reminder_timezone:eastern
                    todo_id:d76c408e-c2a8-47c7-8a3e-7b4ebf359f94
                    current_todo_user:a16d319c-7e4b-42b0-ab8c-bc4562c3b297

                     */

                    ToDoRequestBean toDoRequestBean = new ToDoRequestBean();
                    toDoRequestBean.setTodoId( todoId );
                    toDoRequestBean.setSelectedScheduledSendDay(todoReminderDay);
                    toDoRequestBean.setSelectedScheduledSendTime(todoReminderTime);
                    toDoRequestBean.setSelectedScheduledSendTimeZone(todoReminderTimeZone);
                    toDoRequestBean.setTodoReminderScheduleId( todoReminderId );
                    toDoRequestBean.setUserId( loggedInUserBean.getUserId() );
                    toDoRequestBean.setScheduledStatus( Constants.SCHEDULER_STATUS.NEW_SCHEDULE.getStatus() );

                    AccessToDo accessToDo = new AccessToDo();
                    ToDoBean toDoBean = accessToDo.getTodo( toDoRequestBean );

                    if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId()) ) {
                        if( Constants.TODO_STATUS.ACTIVE.toString().equalsIgnoreCase( toDoBean.getTodoStatus().toString() ) ) {
                            BuildToDo buildToDo = new BuildToDo();
                            TodoReminderScheduleBean todoReminderScheduleBean = buildToDo.saveToDoReminderSchedule( toDoRequestBean );
                            if(todoReminderScheduleBean!=null){
                                jsonResponseObj.put("todo_reminder_schedule" , todoReminderScheduleBean.toJson() );
                            }

                            Text okText = new OkText("The schedule was saved successfully.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            Text errorText = new ErrorText("Please change the To Do item status to \"Active\".","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                    } else {
                        Text errorText = new ErrorText("Please create the To Do item before scheduling a reminder.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
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
