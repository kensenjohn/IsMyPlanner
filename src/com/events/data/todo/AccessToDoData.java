package com.events.data.todo;

import com.events.bean.common.todo.*;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessToDoData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<ToDoBean> getClientTodo( ToDoRequestBean toDoRequestBean){
        ArrayList<ToDoBean> arrTodoBean = new ArrayList<ToDoBean>();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getClientId())) {
            String sQuery  = "SELECT * FROM GTTODO WHERE FK_CLIENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoRequestBean.getClientId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getClientTodo()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ToDoBean toDoBean = new ToDoBean(hmResult);
                    arrTodoBean.add( toDoBean );
                }
            }
        }
        return arrTodoBean;
    }

    public ArrayList<ToDoBean> getClientTodoByFilter( ToDoRequestBean toDoRequestBean){
        ArrayList<ToDoBean> arrTodoBean = new ArrayList<ToDoBean>();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getVendorId())) {

            ArrayList<String> arrUserId = toDoRequestBean.getArrAssignedUserId();

            ArrayList<Object> aParams = new ArrayList<Object>();
            String sQuery  = "SELECT * FROM GTTODO T ";


            if( toDoRequestBean.isUserFilterExists() && arrUserId!=null && !arrUserId.isEmpty() ){
                sQuery = sQuery + " , GTASSIGNEDTODOUSERS U ";
            }

            sQuery = sQuery + " WHERE T.FK_CLIENTID = ?  ";
            aParams.add( toDoRequestBean.getClientId() );

            if( toDoRequestBean.isDateFilterExists() ) {
                sQuery = sQuery + " AND T.TODODATE >= ? AND T.TODODATE <= ? ";
                aParams.add( toDoRequestBean.getlStartDate() );
                aParams.add( toDoRequestBean.getlEndDate() );
            }

            if( toDoRequestBean.isStatusFilterExists() ) {
                sQuery = sQuery + " AND T.TODOSTATUS IN ('"+Constants.TODO_STATUS.ACTIVE.toString()+"','"+Constants.TODO_STATUS.COMPLETE.toString()+"') ";
            } else {
                sQuery = sQuery + " AND T.TODOSTATUS IN ('"+toDoRequestBean.getTodoStatus().toString()+"') ";
            }

            if( toDoRequestBean.isUserFilterExists() && arrUserId!=null && !arrUserId.isEmpty() ){
                sQuery = sQuery + " AND U.FK_TODOID = T.TODOID AND U.FK_USERID IN ( " + DBDAO.createParamQuestionMarks( arrUserId.size() ) + " )";

                for(String sTodoUserId : arrUserId )  {
                    aParams.add( sTodoUserId );
                }
            }
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getVendorTodoByFilter()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ToDoBean toDoBean = new ToDoBean(hmResult);
                    arrTodoBean.add( toDoBean );
                }
            }
        }
        return arrTodoBean;
    }

    public ArrayList<ToDoBean> getVendorTodo( ToDoRequestBean toDoRequestBean){
        ArrayList<ToDoBean> arrTodoBean = new ArrayList<ToDoBean>();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getVendorId())) {
            String sQuery  = "SELECT * FROM GTTODO WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoRequestBean.getVendorId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getVendorTodo()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ToDoBean toDoBean = new ToDoBean(hmResult);
                    arrTodoBean.add( toDoBean );
                }
            }
        }
        return arrTodoBean;
    }

    public ArrayList<ToDoBean> getVendorTodoByFilter( ToDoRequestBean toDoRequestBean){
        ArrayList<ToDoBean> arrTodoBean = new ArrayList<ToDoBean>();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getVendorId())) {

            ArrayList<String> arrUserId = toDoRequestBean.getArrAssignedUserId();

            ArrayList<Object> aParams = new ArrayList<Object>();
            String sQuery  = "SELECT * FROM GTTODO T ";


            if( toDoRequestBean.isUserFilterExists() && arrUserId!=null && !arrUserId.isEmpty() ){
                sQuery = sQuery + " , GTASSIGNEDTODOUSERS U ";
            }

            sQuery = sQuery + " WHERE T.FK_VENDORID = ?  ";
            aParams.add( toDoRequestBean.getVendorId() );

            if( toDoRequestBean.isDateFilterExists() ) {
                sQuery = sQuery + " AND T.TODODATE >= ? AND T.TODODATE <= ? ";
                aParams.add( toDoRequestBean.getlStartDate() );
                aParams.add( toDoRequestBean.getlEndDate() );
            }

            if( toDoRequestBean.isStatusFilterExists() ) {
                sQuery = sQuery + " AND T.TODOSTATUS IN ('"+Constants.TODO_STATUS.ACTIVE.toString()+"','"+Constants.TODO_STATUS.COMPLETE.toString()+"') ";
            } else {
                sQuery = sQuery + " AND T.TODOSTATUS IN ('"+toDoRequestBean.getTodoStatus().toString()+"') ";
            }

            if( toDoRequestBean.isUserFilterExists() && arrUserId!=null && !arrUserId.isEmpty() ){
                sQuery = sQuery + " AND U.FK_TODOID = T.TODOID AND U.FK_USERID IN ( " + DBDAO.createParamQuestionMarks( arrUserId.size() ) + " )";

                for(String sTodoUserId : arrUserId )  {
                    aParams.add( sTodoUserId );
                }
            }
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getVendorTodoByFilter()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ToDoBean toDoBean = new ToDoBean(hmResult);
                    arrTodoBean.add( toDoBean );
                }
            }
        }
        return arrTodoBean;
    }


    public ToDoBean getTodo( ToDoRequestBean toDoRequestBean){
        ToDoBean toDoBean = new ToDoBean();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId())) {
            String sQuery  = "SELECT * FROM GTTODO WHERE TODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoRequestBean.getTodoId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getTodo()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    toDoBean = new ToDoBean(hmResult);
                }
            }
        }
        return toDoBean;
    }


    public AssignedToDoEventsBean getAssignedTodoEvent(   ToDoRequestBean toDoRequestBean  ){
        AssignedToDoEventsBean assignedToDoEventsBean = new AssignedToDoEventsBean();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )   ){
            String sQuery = "SELECT * FROM GTASSIGNEDTODOEVENTS WHERE FK_TODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoRequestBean.getTodoId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getTodoEvent()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    assignedToDoEventsBean = new AssignedToDoEventsBean(hmResult);
                }
            }
        }
        return assignedToDoEventsBean;
    }

    public ArrayList<AssignedToDoUsersBean> getAssignedTodoUsers ( ToDoRequestBean toDoRequestBean ) {
        ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = new ArrayList<AssignedToDoUsersBean>();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )   ){
            String sQuery = "SELECT * FROM GTASSIGNEDTODOUSERS WHERE FK_TODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoRequestBean.getTodoId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getAssignedTodoUsers()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    AssignedToDoUsersBean assignedToDoUsersBean = new AssignedToDoUsersBean(hmResult);
                    arrAssignedToDoUsersBean.add( assignedToDoUsersBean );
                }
            }
        }
        return arrAssignedToDoUsersBean;
    }

    public TodoReminderScheduleBean getTodoReminderScheduleByTodoId(   ToDoRequestBean toDoRequestBean  ){
        TodoReminderScheduleBean todoReminderScheduleBean = new TodoReminderScheduleBean();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )   ){
            String sQuery = "SELECT * FROM GTTODOREMINDERSCHEDULE WHERE FK_TODOID=?";
            ArrayList<Object> aParams = DBDAO.createConstraint( toDoRequestBean.getTodoId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessToDoData.java", "getTodoReminderSchedule()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    todoReminderScheduleBean = new TodoReminderScheduleBean(hmResult);
                }
            }
        }
        return todoReminderScheduleBean;
    }

    public ArrayList<TodoReminderScheduleBean> getArrTodoReminderSchedule(Long lStartTime, Long lEndTime, Constants.SCHEDULER_STATUS scheduleStatus,
                                                             Constants.SCHEDULE_PICKUP_TYPE schedulePickupType ) {

        ArrayList<TodoReminderScheduleBean> arrToDoReminderSchedulerBean = new ArrayList<TodoReminderScheduleBean>();
        if(schedulePickupType!=null && scheduleStatus!=null && lStartTime>0) {
            String sQuery = "SELECT * FROM GTTODOREMINDERSCHEDULE WHERE ";
            ArrayList<Object> aParams = new ArrayList<Object>();

            sQuery = sQuery + " SCHEDULE_STATUS = ? ";
            aParams.add( scheduleStatus.getStatus() );

            if(Constants.SCHEDULE_PICKUP_TYPE.NEW_RECORDS.equals( schedulePickupType )) {
                sQuery = sQuery + " AND SCHEDULEDSENDDATE >= ? AND SCHEDULEDSENDDATE <= ? ";
                aParams.add(lStartTime);
                aParams.add(lEndTime);
            }  else if(Constants.SCHEDULE_PICKUP_TYPE.OLD_RECORDS.equals( schedulePickupType )) {
                sQuery = sQuery + " AND SCHEDULEDSENDDATE < ? ";
                aParams.add(lStartTime);
            } else if(Constants.SCHEDULE_PICKUP_TYPE.CURRENT_RECORD.equals( schedulePickupType )) {
                sQuery = sQuery + " AND SCHEDULEDSENDDATE = ? ";
                aParams.add(lStartTime);
            }
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, true, "AccessToDoData.java", "getArrTodoReminderSchedule()");
            if(arrResult!=null && !arrResult.isEmpty() ) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    TodoReminderScheduleBean  todoReminderScheduleBean = new TodoReminderScheduleBean(hmResult);
                    arrToDoReminderSchedulerBean.add( todoReminderScheduleBean );
                }
            }
        }
        return arrToDoReminderSchedulerBean;
    }
}
