package com.events.common.todo;

import com.events.bean.DateObject;
import com.events.bean.common.todo.*;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.todo.BuildToDoData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildToDo {

    public ToDoBean saveToDo(ToDoRequestBean toDoRequestBean){
        ToDoBean toDoBean = new ToDoBean();
        if(toDoRequestBean!=null){

            DateObject todoDate = DateSupport.convertTime(toDoRequestBean.getHumanTodoDate() + " 00:00 AM",
                    DateSupport.getTimeZone(toDoRequestBean.getTodoTimeZone()), "dd MMMMM, yyyy" + " " + "hh:mm a",
                    DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);

            toDoRequestBean.setTodoDate(todoDate.getMillis());

            toDoBean = new ToDoBean();
            if(Utility.isNullOrEmpty(toDoRequestBean.getTodoId())) {
                toDoBean = createTodo(toDoRequestBean) ;
            } else {
                toDoBean = updateTodo( toDoRequestBean );
            }

            if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId())) {

                toDoRequestBean.setTodoId( toDoBean.getToDoId() );

                BuildToDoData buildToDoData = new BuildToDoData();

                if(Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase(toDoRequestBean.getUserType().getType()) ){
                    buildToDoData.deleteTodoUsers( toDoRequestBean );
                    ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = generateAssignedTodoUsersBean( toDoRequestBean );
                    buildToDoData.insertTodoUsers( arrAssignedToDoUsersBean );
                } else if(  Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase(toDoRequestBean.getUserType().getType()) ) {
                    AccessToDo accessToDo = new AccessToDo();
                    ArrayList<AssignedToDoUsersBean> currentArrAssignedToDoUsersBean = accessToDo.getAssignedTodoUsers( toDoRequestBean );
                    if(currentArrAssignedToDoUsersBean==null || (currentArrAssignedToDoUsersBean!=null && currentArrAssignedToDoUsersBean.isEmpty()) ){
                        ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = generateAssignedTodoUsersBean( toDoRequestBean );
                        buildToDoData.insertTodoUsers( arrAssignedToDoUsersBean );
                    }
                }

                AssignedToDoEventsBean assignedToDoEventsBean = generateAssignedTodoEventsBean(toDoRequestBean);
                buildToDoData.deleteTodoEvent( toDoRequestBean );
                buildToDoData.insertTodoEvent( assignedToDoEventsBean );


            }
        }
        return toDoBean;
    }

    public TodoReminderScheduleBean saveToDoReminderSchedule(ToDoRequestBean toDoRequestBean){
        TodoReminderScheduleBean todoReminderScheduleBean = new TodoReminderScheduleBean();
        if(toDoRequestBean!=null){
            DateObject todoReminderScheduleDate = DateSupport.convertTime(toDoRequestBean.getSelectedScheduledSendDay()+ " " + toDoRequestBean.getSelectedScheduledSendTime(),
                    DateSupport.getTimeZone(toDoRequestBean.getSelectedScheduledSendTimeZone()), "dd MMMMM, yyyy hh:mm aaa",
                    DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);

            toDoRequestBean.setTodoScheduleSendDate(todoReminderScheduleDate.getMillis());
            if(Utility.isNullOrEmpty(toDoRequestBean.getTodoReminderScheduleId())) {
                todoReminderScheduleBean = createTodoReminder( toDoRequestBean );
            } else {
                todoReminderScheduleBean = updateTodoReminder( toDoRequestBean );
            }
        }
        return todoReminderScheduleBean;
    }

    public TodoReminderScheduleBean createTodoReminder( ToDoRequestBean toDoRequestBean ){
        TodoReminderScheduleBean todoReminderScheduleBean = new TodoReminderScheduleBean();
        if(toDoRequestBean!=null) {
            toDoRequestBean.setTodoReminderScheduleId( Utility.getNewGuid() );
            todoReminderScheduleBean = generateTodoReminderScheduleBean( toDoRequestBean );
            BuildToDoData buildToDoData = new BuildToDoData();
            Integer numOfRowsInserted = buildToDoData.insertTodoReminderSchedule( todoReminderScheduleBean );
            if(numOfRowsInserted<=0){
                todoReminderScheduleBean = new TodoReminderScheduleBean();
            }
        }
        return todoReminderScheduleBean;
    }
    public TodoReminderScheduleBean updateTodoReminder( ToDoRequestBean toDoRequestBean ){
        TodoReminderScheduleBean todoReminderScheduleBean = new TodoReminderScheduleBean();
        if(toDoRequestBean!=null) {
            todoReminderScheduleBean = generateTodoReminderScheduleBean( toDoRequestBean );
            BuildToDoData buildToDoData = new BuildToDoData();
            Integer numOfRowsUpdated = buildToDoData.updateTodoReminderSchedule(todoReminderScheduleBean);
            if(numOfRowsUpdated<=0){
                todoReminderScheduleBean = new TodoReminderScheduleBean();
            }
        }
        return todoReminderScheduleBean;
    }

    public ToDoBean createTodo(ToDoRequestBean toDoRequestBean){
        ToDoBean toDoBean = new ToDoBean();
        if(toDoRequestBean!=null) {
            toDoRequestBean.setTodoId(Utility.getNewGuid());
            toDoBean = generateToDoBean( toDoRequestBean );
            BuildToDoData buildToDoData = new BuildToDoData();
            Integer iNumOfRows = buildToDoData.insertTodo(toDoBean);
            if(iNumOfRows<=0 ){
                toDoBean = new ToDoBean();
            }
        }
        return toDoBean;
    }
    public ToDoBean updateTodo(ToDoRequestBean toDoRequestBean){
        ToDoBean toDoBean = new ToDoBean();
        if(toDoRequestBean!=null) {
            toDoBean = generateToDoBean( toDoRequestBean );
            BuildToDoData buildToDoData = new BuildToDoData();
            Integer iNumOfRows = buildToDoData.updateTodo(toDoBean);
            if(iNumOfRows<=0 ){
                toDoBean = new ToDoBean();
            }
        }
        return toDoBean;
    }

    public boolean deleteTodo(  ToDoBean toDoBean ){
        boolean isSuccess = false;
        if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId())) {
            BuildToDoData buildToDoData = new BuildToDoData();
            Integer iNumOfRowsDeleted = buildToDoData.deleteTodo(toDoBean);
            if(iNumOfRowsDeleted>0){
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    public Integer deleteAssignedTodoUsers(  ToDoRequestBean toDoRequestBean  ){
        Integer iNumOfRowsDeleted = 0;
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId())) {
            BuildToDoData buildToDoData = new BuildToDoData();
            iNumOfRowsDeleted = buildToDoData.deleteTodoUsers(toDoRequestBean);
        }
        return iNumOfRowsDeleted;
    }

    public Integer deleteAssignedTodoEvents(  ToDoRequestBean toDoRequestBean  ){
        Integer iNumOfRowsDeleted = 0;
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId())) {
            BuildToDoData buildToDoData = new BuildToDoData();
            iNumOfRowsDeleted = buildToDoData.deleteTodoEvent(toDoRequestBean);
        }
        return iNumOfRowsDeleted;
    }

    public Integer deleteTodoReminderSchedule(  ToDoRequestBean toDoRequestBean  ){
        Integer iNumOfRowsDeleted = 0;
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId())) {
            BuildToDoData buildToDoData = new BuildToDoData();
            iNumOfRowsDeleted = buildToDoData.deleteTodoReminderSchedule(  toDoRequestBean  );
        }
        return iNumOfRowsDeleted;
    }

    public TodoReminderScheduleBean generateTodoReminderScheduleBean(ToDoRequestBean toDoRequestBean){
        TodoReminderScheduleBean todoReminderScheduleBean = new TodoReminderScheduleBean();
        if(toDoRequestBean!=null){
            /*
            this.todoReminderScheduleId = ParseUtil.checkNull(hmResult.get("TODOREMINDERSCHEDULEID"));
            this.toDoId = ParseUtil.checkNull(hmResult.get("FK_TODOID"));
            this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") );
            this.humanScheduledSendDate =  ParseUtil.checkNull(hmResult.get("HUMANSCHEDULEDSENDDATE"));
            this.scheduledSendDate = ParseUtil.sToL(hmResult.get("SCHEDULEDSENDDATE"));
            this.scheduleStatus = ParseUtil.checkNull(hmResult.get("SCHEDULE_STATUS"));
            this.selectedScheduledSendDay =  ParseUtil.checkNull(hmResult.get("SELECTED_SCHEDULEDSENDDAY"));
            this.selectedScheduledSendTime =  ParseUtil.checkNull( hmResult.get("SELECTED_SCHEDULEDSENDTIME") );
            this.selectedScheduledSendTimeZone =  ParseUtil.checkNull( hmResult.get("SELECTED_SCHEDULEDSENDTIMEZONE") );
             */
            todoReminderScheduleBean.setTodoReminderScheduleId(ParseUtil.checkNull(toDoRequestBean.getTodoReminderScheduleId()) );
            todoReminderScheduleBean.setUserId( toDoRequestBean.getUserId() );
            todoReminderScheduleBean.setToDoId( toDoRequestBean.getTodoId() );
            todoReminderScheduleBean.setHumanScheduledSendDate( toDoRequestBean.getHumanTodoScheduleSendDate()  );
            todoReminderScheduleBean.setSelectedScheduledSendTimeZone( toDoRequestBean.getSelectedScheduledSendTimeZone() );
            todoReminderScheduleBean.setSelectedScheduledSendTime( toDoRequestBean.getSelectedScheduledSendTime() );
            todoReminderScheduleBean.setSelectedScheduledSendDay( toDoRequestBean.getSelectedScheduledSendDay() );
            todoReminderScheduleBean.setScheduledSendDate( toDoRequestBean.getTodoScheduleSendDate() );
            todoReminderScheduleBean.setScheduleStatus( toDoRequestBean.getScheduledStatus() );
        }
        return todoReminderScheduleBean;
    }

    public ToDoBean generateToDoBean(ToDoRequestBean toDoRequestBean){
        ToDoBean toDoBean = new ToDoBean();
        if(toDoRequestBean!=null){
            toDoBean.setToDoId(  ParseUtil.checkNull(toDoRequestBean.getTodoId()) );
            toDoBean.setToDo( ParseUtil.checkNull(toDoRequestBean.getToDo()) );
            toDoBean.setTodoStatus(toDoRequestBean.getTodoStatus());
            toDoBean.setVendorId( toDoRequestBean.getVendorId() );
            toDoBean.setClientId( toDoRequestBean.getClientId() );
            toDoBean.setUserId( toDoRequestBean.getUserId() );
            toDoBean.setToDoDate( toDoRequestBean.getTodoDate() );
            toDoBean.setHumanToDoDate( ParseUtil.checkNull(toDoRequestBean.getHumanTodoDate())  );
            toDoBean.setTodoTimeZone( ParseUtil.checkNull(toDoRequestBean.getTodoTimeZone()) );
            toDoBean.setUserType( toDoRequestBean.getUserType() );
        }
        return toDoBean;
    }

    private ArrayList<AssignedToDoUsersBean> generateAssignedTodoUsersBean(ToDoRequestBean toDoRequestBean){
        ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = new ArrayList<AssignedToDoUsersBean>();
        if(toDoRequestBean!=null){
            ArrayList<String> arrTodoUserIds = toDoRequestBean.getArrAssignedUserId();
            for(String todoUserId : arrTodoUserIds )  {
                AssignedToDoUsersBean assignedToDoUsersBean = new AssignedToDoUsersBean();
                assignedToDoUsersBean.setAssignedTodoUserId( Utility.getNewGuid() );
                assignedToDoUsersBean.setToDoId( toDoRequestBean.getTodoId() );
                assignedToDoUsersBean.setUserId( todoUserId );
                arrAssignedToDoUsersBean.add( assignedToDoUsersBean );
            }
        }
        return arrAssignedToDoUsersBean;
    }

    private AssignedToDoEventsBean generateAssignedTodoEventsBean(ToDoRequestBean toDoRequestBean){
        AssignedToDoEventsBean assignedToDoEventsBean = new AssignedToDoEventsBean();
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty( toDoRequestBean.getTodoId())  && !Utility.isNullOrEmpty( toDoRequestBean.getTodoEventId() )  ){

            assignedToDoEventsBean.setAssignedToDoEventsId( Utility.getNewGuid() );
            assignedToDoEventsBean.setToDoId( toDoRequestBean.getTodoId()  );
            assignedToDoEventsBean.setEventId( toDoRequestBean.getTodoEventId() );
        }
        return assignedToDoEventsBean;
    }
}
