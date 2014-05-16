package com.events.common.todo;

import com.events.bean.common.todo.*;
import com.events.bean.event.EventBean;
import com.events.bean.event.EventRequestBean;
import com.events.bean.event.EventResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import com.events.data.todo.AccessToDoData;
import com.events.event.AccessEvent;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessToDo {
    public ToDoResponseBean getAllTodo( ToDoRequestBean toDoRequestBean ){
        ToDoResponseBean toDoResponseBean = new ToDoResponseBean();

        if(toDoRequestBean!=null){
            AccessToDoData accessTodoData = new AccessToDoData();

            ArrayList<ToDoBean> arrTodoBean = new ArrayList<ToDoBean>();
            if(Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase( toDoRequestBean.getUserType().getType() )) {
                arrTodoBean = accessTodoData.getVendorTodo( toDoRequestBean );
            } else if(Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase( toDoRequestBean.getUserType().getType() )) {
                arrTodoBean = accessTodoData.getClientTodo( toDoRequestBean );
            }


            if(arrTodoBean!=null && !arrTodoBean.isEmpty() ) {
                HashMap<String, EventBean> hmEventBean = new HashMap<String, EventBean>();
                HashMap<String,Long> hmNumOfAssignedUsers = new HashMap<String,Long>();
                for(ToDoBean toDoBean : arrTodoBean){
                    toDoRequestBean.setTodoId( toDoBean.getToDoId() );
                    AssignedToDoEventsBean assignedToDoEventsBean = accessTodoData.getAssignedTodoEvent( toDoRequestBean );
                    if(assignedToDoEventsBean!=null && !Utility.isNullOrEmpty(assignedToDoEventsBean.getAssignedToDoEventsId())){
                        EventRequestBean eventRequestBean = new EventRequestBean();
                        eventRequestBean.setEventId( assignedToDoEventsBean.getEventId() );

                        AccessEvent accessEvent = new AccessEvent();
                        EventResponseBean eventResponseBean = accessEvent.getEventInfo( eventRequestBean );
                        if(eventResponseBean!=null && eventResponseBean.getEventBean()!=null){
                            hmEventBean.put( toDoBean.getToDoId() , eventResponseBean.getEventBean());
                        }
                    }

                    ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = accessTodoData.getAssignedTodoUsers(  toDoRequestBean  );
                    if( arrAssignedToDoUsersBean!=null && !arrAssignedToDoUsersBean.isEmpty() ) {
                        hmNumOfAssignedUsers.put( toDoBean.getToDoId() , ParseUtil.IToL( arrAssignedToDoUsersBean.size() ));
                    }
                }
                toDoResponseBean.setHmEventBean( hmEventBean );
                toDoResponseBean.setHmNumOfAssignedUsers( hmNumOfAssignedUsers );
            }
            toDoResponseBean.setArrTodoBean( arrTodoBean );
        }
        return toDoResponseBean;
    }
    public ToDoResponseBean getFilteredTodo( ToDoRequestBean toDoRequestBean ){
        ToDoResponseBean toDoResponseBean = new ToDoResponseBean();

        if(toDoRequestBean!=null){
            AccessToDoData accessTodoData = new AccessToDoData();

            boolean isDateFilterExists = false;
            boolean isStatusFilterExists = false;
            boolean isUserFilterExists = false;
            ArrayList<ToDoBean> arrTodoBean = new ArrayList<ToDoBean>();
            if(toDoRequestBean.getlStartDate()>0 && toDoRequestBean.getlEndDate() > 0
                    && toDoRequestBean.getlEndDate() >= toDoRequestBean.getlStartDate() ){
                toDoRequestBean.setDateFilterExists( true );
            }

            if( Constants.TODO_STATUS.ALL.toString().equalsIgnoreCase(toDoRequestBean.getTodoStatus().toString()) ) {
                toDoRequestBean.setStatusFilterExists( true );
            }

            if( toDoRequestBean.getArrAssignedUserId()!=null && !toDoRequestBean.getArrAssignedUserId().isEmpty() ){
                toDoRequestBean.setUserFilterExists( true );
            }

            if(Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase( toDoRequestBean.getUserType().getType() )) {
                arrTodoBean = accessTodoData.getVendorTodoByFilter( toDoRequestBean );
            } else if(Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase( toDoRequestBean.getUserType().getType() )) {
                arrTodoBean = accessTodoData.getClientTodoByFilter( toDoRequestBean );
            }

            if(arrTodoBean!=null && !arrTodoBean.isEmpty() ) {
                HashMap<String, EventBean> hmEventBean = new HashMap<String, EventBean>();
                HashMap<String,Long> hmNumOfAssignedUsers = new HashMap<String,Long>();
                for(ToDoBean toDoBean : arrTodoBean){
                    toDoRequestBean.setTodoId( toDoBean.getToDoId() );
                    AssignedToDoEventsBean assignedToDoEventsBean = accessTodoData.getAssignedTodoEvent( toDoRequestBean );
                    if(assignedToDoEventsBean!=null && !Utility.isNullOrEmpty(assignedToDoEventsBean.getAssignedToDoEventsId())){
                        EventRequestBean eventRequestBean = new EventRequestBean();
                        eventRequestBean.setEventId( assignedToDoEventsBean.getEventId() );

                        AccessEvent accessEvent = new AccessEvent();
                        EventResponseBean eventResponseBean = accessEvent.getEventInfo( eventRequestBean );
                        if(eventResponseBean!=null && eventResponseBean.getEventBean()!=null){
                            hmEventBean.put( toDoBean.getToDoId() , eventResponseBean.getEventBean());
                        }
                    }

                    ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = accessTodoData.getAssignedTodoUsers(  toDoRequestBean  );
                    if( arrAssignedToDoUsersBean!=null && !arrAssignedToDoUsersBean.isEmpty() ) {
                        hmNumOfAssignedUsers.put( toDoBean.getToDoId() , ParseUtil.IToL( arrAssignedToDoUsersBean.size() ));
                    }
                }
                toDoResponseBean.setHmEventBean( hmEventBean );
                toDoResponseBean.setHmNumOfAssignedUsers( hmNumOfAssignedUsers );
            }
            toDoResponseBean.setArrTodoBean( arrTodoBean );

        }
        return toDoResponseBean;
    }

    public ToDoBean getTodo( ToDoRequestBean toDoRequestBean ){
        ToDoBean toDoBean = new ToDoBean();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId())) {
            AccessToDoData accessTodoData = new AccessToDoData();
            toDoBean = accessTodoData.getTodo( toDoRequestBean );
        }
        return toDoBean;
    }

    public JSONObject getJsonAllTodo( ArrayList<ToDoBean> arrTodoBean ) {
        JSONObject jsonTodos = new JSONObject();
        if(arrTodoBean!=null && !arrTodoBean.isEmpty() ) {
            Long iNumOfTodos = 0L;
            for(ToDoBean toDoBean  : arrTodoBean ) {
                jsonTodos.put(ParseUtil.LToS(iNumOfTodos), toDoBean.toJson());
                iNumOfTodos++;
            }
            jsonTodos.put("num_of_todos",iNumOfTodos);
        }
        return jsonTodos;
    }

    public AssignedToDoEventsBean getAssignedTodoEvent(   ToDoRequestBean toDoRequestBean  ){
        AssignedToDoEventsBean assignedToDoEventsBean = new AssignedToDoEventsBean();
        if( toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )){
            AccessToDoData accessTodoData = new AccessToDoData();
            assignedToDoEventsBean = accessTodoData.getAssignedTodoEvent( toDoRequestBean );
        }
        return assignedToDoEventsBean;
    }

    public ArrayList<AssignedToDoUsersBean> getAssignedTodoUsers ( ToDoRequestBean toDoRequestBean ) {
        ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = new ArrayList<AssignedToDoUsersBean>();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )   ){
            AccessToDoData accessTodoData = new AccessToDoData();
            arrAssignedToDoUsersBean = accessTodoData.getAssignedTodoUsers( toDoRequestBean );
        }
        return arrAssignedToDoUsersBean;
    }

    public JSONObject getJsonAssignedTodoUsers( ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean ) {
        JSONObject jsonAssignedTodoUsers = new JSONObject();
        Long lNumOfAssignedUsers = 0L;
        if(arrAssignedToDoUsersBean!=null && !arrAssignedToDoUsersBean.isEmpty()) {

            for(AssignedToDoUsersBean assignedToDoUsersBean : arrAssignedToDoUsersBean ){
                jsonAssignedTodoUsers.put(ParseUtil.LToS(lNumOfAssignedUsers) , assignedToDoUsersBean.toJson() );
                lNumOfAssignedUsers++;
            }
        }
        jsonAssignedTodoUsers.put("num_of_assigned_todo_users", lNumOfAssignedUsers );
        return jsonAssignedTodoUsers;
    }

    public TodoReminderScheduleBean getTodoReminderScheduleByTodoId( ToDoRequestBean toDoRequestBean){
        TodoReminderScheduleBean todoReminderScheduleBean = new TodoReminderScheduleBean();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId())) {
            AccessToDoData accessToDoData = new AccessToDoData();
            todoReminderScheduleBean = accessToDoData.getTodoReminderScheduleByTodoId( toDoRequestBean );
        }
        return todoReminderScheduleBean;
    }
}
