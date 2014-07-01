package com.events.data.todo;

import com.events.bean.common.todo.*;
import com.events.common.*;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildToDoData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertTodo( ToDoBean toDoBean ){
        Integer numOfRowsInserted = 0;
        if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId())  && !Utility.isNullOrEmpty(toDoBean.getToDo())   ){
            String sQuery = "INSERT INTO GTTODO (TODOID,TODO,FK_USERID,    FK_VENDORID,FK_CLIENTID,USERTYPE,   TODOSTATUS,TODODATE,HUMANTODODATE,     " +
                    "   CREATEDATE, HUMANCREATEDATE,TODOTIMEZONE ) VALUES (?,?,?,   ?,?,?,   ?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoBean.getToDoId(),toDoBean.getToDo(),toDoBean.getUserId(),
                    toDoBean.getVendorId(),ParseUtil.checkNull(toDoBean.getClientId()),toDoBean.getUserType().getType(),
                    toDoBean.getTodoStatus().toString(),toDoBean.getToDoDate(),toDoBean.getHumanToDoDate(),
                    DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),toDoBean.getTodoTimeZone() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "insertTodo() ");
        }
        return numOfRowsInserted;
    }

    public Integer insertTodoList( ArrayList<ToDoBean> arrToDoBean ){
        Integer numOfRowsInserted = 0;
        if(arrToDoBean!=null && !arrToDoBean.isEmpty()   ){
            String sQuery = "INSERT INTO GTTODO (TODOID,TODO,FK_USERID,    FK_VENDORID,FK_CLIENTID,USERTYPE,   TODOSTATUS,TODODATE,HUMANTODODATE,     " +
                    "   CREATEDATE, HUMANCREATEDATE,TODOTIMEZONE ) VALUES (?,?,?,   ?,?,?,   ?,?,?,   ?,?,?)";

            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(ToDoBean toDoBean : arrToDoBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(toDoBean.getToDoId(),toDoBean.getToDo(),toDoBean.getUserId(),
                        toDoBean.getVendorId(),ParseUtil.checkNull(toDoBean.getClientId()),toDoBean.getUserType().getType(),
                        toDoBean.getTodoStatus().toString(),toDoBean.getToDoDate(),toDoBean.getHumanToDoDate(),
                        DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),toDoBean.getTodoTimeZone() );
                aBatchParams.add( aParams );
            }


            int[] aNumOfRowsInserted= DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildChecklistTemplateData.java", "insertTodoList() " );

            if(aNumOfRowsInserted!=null && aNumOfRowsInserted.length > 0 ) {
                for(int iCount : aNumOfRowsInserted ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }

    public Integer updateTodo( ToDoBean toDoBean ){
        Integer numOfRowsUpdated = 0;
        if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId())  && !Utility.isNullOrEmpty(toDoBean.getToDo())    ){
            String sQuery = "UPDATE GTTODO SET TODO =?,FK_VENDORID=?,FK_CLIENTID=?,       TODOSTATUS=?,TODODATE=?,HUMANTODODATE=?,    TODOTIMEZONE=?    WHERE    TODOID=?";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoBean.getToDo(),toDoBean.getVendorId(),ParseUtil.checkNull(toDoBean.getClientId()),
                    toDoBean.getTodoStatus().toString(),toDoBean.getToDoDate(),toDoBean.getHumanToDoDate(),
                    toDoBean.getTodoTimeZone(),
                    toDoBean.getToDoId() );

            numOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "updateTodo() ");
        }
        return numOfRowsUpdated;
    }

    public Integer deleteTodo( ToDoBean toDoBean ){
        Integer numOfRowsUpdated = 0;
        if(toDoBean!=null && !Utility.isNullOrEmpty(toDoBean.getToDoId())     ){
            String sQuery = "DELETE FROM GTTODO WHERE TODOID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(toDoBean.getToDoId() );

            numOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "deleteTodo() ");
        }
        return numOfRowsUpdated;
    }

    public Integer insertTodoReminderSchedule( TodoReminderScheduleBean todoReminderScheduleBean ){
        Integer numOfRowsInserted = 0;
        if(todoReminderScheduleBean!=null && !Utility.isNullOrEmpty(todoReminderScheduleBean.getTodoReminderScheduleId())) {
            String sQuery = "INSERT INTO GTTODOREMINDERSCHEDULE(TODOREMINDERSCHEDULEID,FK_TODOID,FK_USERID," +
                    " CREATEDATE,HUMANCREATEDATE,SCHEDULEDSENDDATE,      HUMANSCHEDULEDSENDDATE,SELECTED_SCHEDULEDSENDDAY,SELECTED_SCHEDULEDSENDTIME," +
                    " SELECTED_SCHEDULEDSENDTIMEZONE,SCHEDULE_STATUS) VALUES(?,?,?,   ?,?,?,   ?,?,?,   ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint( todoReminderScheduleBean.getTodoReminderScheduleId(), todoReminderScheduleBean.getToDoId(),todoReminderScheduleBean.getUserId(),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), todoReminderScheduleBean.getScheduledSendDate(),
                    todoReminderScheduleBean.getHumanScheduledSendDate(),todoReminderScheduleBean.getSelectedScheduledSendDay(),todoReminderScheduleBean.getSelectedScheduledSendTime(),
                    todoReminderScheduleBean.getSelectedScheduledSendTimeZone(),todoReminderScheduleBean.getScheduleStatus());
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "insertTodoReminderSchedule() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateTodoReminderSchedule( TodoReminderScheduleBean todoReminderScheduleBean ){
        Integer numOfRowsUpdated = 0;
        if(todoReminderScheduleBean!=null && !Utility.isNullOrEmpty(todoReminderScheduleBean.getTodoReminderScheduleId())     ){
            String sQuery = "UPDATE GTTODOREMINDERSCHEDULE SET FK_USERID = ?,CREATEDATE=?,HUMANCREATEDATE=?,     " +
                    "  SCHEDULEDSENDDATE=?,HUMANSCHEDULEDSENDDATE=?,SELECTED_SCHEDULEDSENDDAY=?,     " +
                    "  SELECTED_SCHEDULEDSENDTIME=?,SELECTED_SCHEDULEDSENDTIMEZONE=?,SCHEDULE_STATUS=?      WHERE  " +
                    "  TODOREMINDERSCHEDULEID = ? AND FK_TODOID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(todoReminderScheduleBean.getUserId(),DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),
                    todoReminderScheduleBean.getScheduledSendDate(),todoReminderScheduleBean.getHumanScheduledSendDate(),todoReminderScheduleBean.getSelectedScheduledSendDay(),
                    todoReminderScheduleBean.getSelectedScheduledSendTime(),todoReminderScheduleBean.getSelectedScheduledSendTimeZone(),todoReminderScheduleBean.getScheduleStatus(),
                    todoReminderScheduleBean.getTodoReminderScheduleId(), todoReminderScheduleBean.getToDoId() );

            numOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "updateTodoReminderSchedule() ");
        }
        return numOfRowsUpdated;
    }

    public Integer updateTodoReminderScheduleStatus( TodoReminderScheduleBean todoReminderScheduleBean ){
        Integer numOfRowsUpdated = 0;
        if(todoReminderScheduleBean!=null && !Utility.isNullOrEmpty(todoReminderScheduleBean.getTodoReminderScheduleId())     ){
            String sQuery = "UPDATE GTTODOREMINDERSCHEDULE SET SCHEDULE_STATUS=?      WHERE  " +
                    "  TODOREMINDERSCHEDULEID = ? AND FK_TODOID=? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(todoReminderScheduleBean.getScheduleStatus(),
                    todoReminderScheduleBean.getTodoReminderScheduleId(), todoReminderScheduleBean.getToDoId() );

            numOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "updateTodoReminderScheduleStatus() ");
        }
        return numOfRowsUpdated;
    }
    public Integer deleteTodoReminderSchedule(   ToDoRequestBean toDoRequestBean  ){
        Integer numOfRowsInserted = 0;
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )   ){
            String sQuery = "DELETE FROM GTTODOREMINDERSCHEDULE WHERE FK_TODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( toDoRequestBean.getTodoId()  );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "deleteTodoReminderSchedule() ");
        }
        return numOfRowsInserted;
    }


    public Integer insertTodoUsers(ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean){
        Integer numOfRowsInserted = 0;
        if(arrAssignedToDoUsersBean!=null && !arrAssignedToDoUsersBean.isEmpty()) {

            String sQuery = "INSERT INTO GTASSIGNEDTODOUSERS(ASSIGNEDTODOUSERSID,FK_TODOID,FK_USERID) VALUES(?,?,?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(AssignedToDoUsersBean assignedToDoUsersBean : arrAssignedToDoUsersBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint( assignedToDoUsersBean.getAssignedTodoUserId(), assignedToDoUsersBean.getToDoId(),assignedToDoUsersBean.getUserId() );
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildToDoData.java", "insertTodoUsers() " );
            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }
    public Integer deleteTodoUsers( ToDoRequestBean toDoRequestBean){
        Integer numOfRowsInserted = 0;
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )   ){
            String sQuery = "DELETE FROM GTASSIGNEDTODOUSERS WHERE FK_TODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( toDoRequestBean.getTodoId()  );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "deleteTodoUsers() ");
        }
        return numOfRowsInserted;
    }

    public Integer insertTodoEvent( AssignedToDoEventsBean assignedToDoEventsBean){
        Integer numOfRowsInserted = 0;
        if(assignedToDoEventsBean!=null && !Utility.isNullOrEmpty(assignedToDoEventsBean.getAssignedToDoEventsId())   ){
            String sQuery = "INSERT INTO GTASSIGNEDTODOEVENTS (ASSIGNEDTODOEVENTSID,FK_TODOID,FK_EVENTID) VALUES (?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(assignedToDoEventsBean.getAssignedToDoEventsId(),assignedToDoEventsBean.getToDoId(),assignedToDoEventsBean.getEventId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "insertTodoEvent() ");
        }
        return numOfRowsInserted;
    }

    public Integer deleteTodoEvent(   ToDoRequestBean toDoRequestBean  ){
        Integer numOfRowsInserted = 0;
        if(toDoRequestBean!=null && !Utility.isNullOrEmpty(toDoRequestBean.getTodoId() )   ){
            String sQuery = "DELETE FROM GTASSIGNEDTODOEVENTS WHERE FK_TODOID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( toDoRequestBean.getTodoId()  );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildToDoData.java", "deleteTodoEvent() ");
        }
        return numOfRowsInserted;
    }
}
