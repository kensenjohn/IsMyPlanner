package com.events.proc.todo;

import com.events.bean.DateObject;
import com.events.bean.common.todo.ToDoBean;
import com.events.bean.common.todo.ToDoRequestBean;
import com.events.bean.common.todo.ToDoResponseBean;
import com.events.bean.event.EventBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.common.todo.AccessToDo;
import com.events.event.AccessEvent;
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
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadTodoSummary extends HttpServlet {
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
                    /*
                    todo_filter_start_date:
todo_filter_end_date:
todo_filter_status:ACTIVE
todo_filter_users:a73d39fa-e638-4cac-bf21-dfbe2edce5b2
                     */
                    String filterStartDate = ParseUtil.checkNull( request.getParameter("todo_filter_start_date"));
                    String filterEndDate = ParseUtil.checkNull( request.getParameter("todo_filter_end_date") );
                    String filterStatus = ParseUtil.checkNull(  request.getParameter("todo_filter_status") );
                    String filterTimeZone = ParseUtil.checkNull(  request.getParameter("todo_filter_time_zone") );
                    String[] filterUsers = request.getParameterValues("todo_filter_users") ;
                    String searchType = ParseUtil.checkNull(  request.getParameter("search_type") );


                    /*if( !"filter".equalsIgnoreCase(searchType)){
                        filterStartDate = DateSupport.getTimeByZone( DateSupport.getEpochMillis(), Constants.DEFAULT_TIMEZONE, "dd MMMMM, yyyy");
                        filterEndDate = filterStartDate;
                    }*/

                    boolean isError = false;
                    ToDoRequestBean toDoRequestBean = new ToDoRequestBean();
                    if( !Utility.isNullOrEmpty( filterStartDate )  && !Utility.isNullOrEmpty( filterEndDate ))  {
                        DateObject todoStartDate = DateSupport.convertTime(filterStartDate + " 00:00 AM",
                                DateSupport.getTimeZone(filterTimeZone), "dd MMMMM, yyyy" + " " + "hh:mm a",
                                DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);

                        Long lFilterStartDate = todoStartDate.getMillis();
                        toDoRequestBean.setlStartDate( lFilterStartDate );

                        DateObject todoEndDate = DateSupport.convertTime(filterEndDate + " 11:59 PM",
                                DateSupport.getTimeZone(filterTimeZone), "dd MMMMM, yyyy" + " " + "hh:mm a",
                                DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);

                        Long lFilterEndDate = todoEndDate.getMillis();

                        toDoRequestBean.setlEndDate( lFilterEndDate );
                        if(lFilterStartDate>lFilterEndDate)  {
                            isError = true;
                            Text errorText = new ErrorText("End date should be greater than start date.","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    }


                    if(!isError){

                        toDoRequestBean.setUserId( loggedInUserBean.getUserId() );

                        AccessUsers accessUsers = new AccessUsers();
                        ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser( loggedInUserBean );
                        Long numOfTodos = 0L;
                        if(parentTypeBean!=null) {

                            ArrayList<String> arrAssignedUserId = new ArrayList<String>();
                            if(filterUsers!=null && filterUsers.length > 0 ) {
                                for(String sAssignedUserId : filterUsers ) {
                                    arrAssignedUserId.add(  sAssignedUserId  );
                                }
                            }
                            if( !"filter".equalsIgnoreCase(searchType)) {
                                arrAssignedUserId.add( loggedInUserBean.getUserId() );
                            }
                            toDoRequestBean.setArrAssignedUserId( arrAssignedUserId );

                            if(Utility.isNullOrEmpty(filterStatus)){
                                filterStatus = Constants.TODO_STATUS.ACTIVE.toString();
                            }
                            if(  !Utility.isNullOrEmpty(filterStatus) ) {
                                toDoRequestBean.setTodoStatus( Constants.TODO_STATUS.valueOf(filterStatus) );
                            }
                            toDoRequestBean.setStartDate( filterStartDate );
                            toDoRequestBean.setEndDate( filterEndDate );
                            toDoRequestBean.setTodoTimeZone( filterTimeZone );


                            toDoRequestBean.setVendorId( ParseUtil.checkNull(parentTypeBean.getVendorId()) );
                            toDoRequestBean.setClientId( ParseUtil.checkNull(parentTypeBean.getClientdId()) );

                            if(parentTypeBean.isUserAVendor() ) {
                                toDoRequestBean.setUserType( Constants.USER_TYPE.VENDOR );
                            } else if(  parentTypeBean.isUserAClient() ) {
                                toDoRequestBean.setUserType( Constants.USER_TYPE.CLIENT );
                            }
                            ToDoResponseBean toDoResponseBean = new ToDoResponseBean();
                            AccessToDo accessToDo = new AccessToDo();
                            if( "filter".equalsIgnoreCase(searchType)) {
                                toDoResponseBean = accessToDo.getFilteredTodo( toDoRequestBean );
                            } else {
                                toDoResponseBean = accessToDo.getFilteredTodo( toDoRequestBean );
                            }


                            if(toDoResponseBean!=null){
                                ArrayList<ToDoBean> arrTodoBean = toDoResponseBean.getArrTodoBean();
                                if(arrTodoBean!=null && !arrTodoBean.isEmpty() ) {
                                    JSONObject jsonTodos = accessToDo.getJsonAllTodo( arrTodoBean );
                                    if( jsonTodos!=null ){
                                        numOfTodos = jsonTodos.optLong("num_of_todos");
                                        if( numOfTodos>0 ) {
                                            jsonResponseObj.put("all_todos" , jsonTodos );
                                        }
                                    }
                                }

                                HashMap<String,EventBean> hmEventBean = toDoResponseBean.getHmEventBean();
                                if(hmEventBean!=null && !hmEventBean.isEmpty()) {
                                    AccessEvent accessEvent = new AccessEvent();
                                    JSONObject jsonHmEventBean = accessEvent.getJsonHmEventBean(hmEventBean);
                                    if(jsonHmEventBean!=null) {
                                        jsonResponseObj.put("todo_event_bean" , jsonHmEventBean );
                                    }
                                }


                                HashMap<String,Long> hmNumOfAssignedUsers = toDoResponseBean.getHmNumOfAssignedUsers();
                                if(hmNumOfAssignedUsers!=null && !hmNumOfAssignedUsers.isEmpty()) {
                                    JSONObject jsonHmAssignedUsers = Utility.getJsonHmLong( hmNumOfAssignedUsers );
                                    if( jsonHmAssignedUsers!=null ) {
                                        jsonResponseObj.put("todo_num_of_assigned_users" , jsonHmAssignedUsers );
                                    }
                                }
                            }

                        }

                        jsonResponseObj.put("num_of_todos" , numOfTodos );
                        Text okText = new OkText("Load of Todo Summary Complete.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadTodoSummary - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadTodoSummary - 001)","err_mssg") ;
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
