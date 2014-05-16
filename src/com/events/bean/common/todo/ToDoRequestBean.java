package com.events.bean.common.todo;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToDoRequestBean {
    private String todoId = Constants.EMPTY;
    private String toDo = Constants.EMPTY;
    private Long todoDate = 0L;
    private String humanTodoDate = Constants.EMPTY;
    private String todoTimeZone = Constants.EMPTY;
    private Constants.TODO_STATUS todoStatus = Constants.TODO_STATUS.ACTIVE;
    private String userId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private Constants.USER_TYPE userType = Constants.USER_TYPE.NONE;
    private String todoEventId = Constants.EMPTY;
    private ArrayList<String> arrAssignedUserId = new ArrayList<String>();
    private ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = new ArrayList<AssignedToDoUsersBean>();


    private String todoReminderScheduleId = Constants.EMPTY;
    private Long todoScheduleSendDate = 0L;
    private String humanTodoScheduleSendDate = Constants.EMPTY;
    private String selectedScheduledSendDay  = Constants.EMPTY;
    private String selectedScheduledSendTime  = Constants.EMPTY;
    private String selectedScheduledSendTimeZone  = Constants.EMPTY;
    private String scheduledStatus  = Constants.EMPTY;

    private String startDate = Constants.EMPTY;
    private String endDate = Constants.EMPTY;
    private Long lStartDate = 0L;
    private Long lEndDate = 0L;
    private boolean isDateFilterExists = false;
    private boolean isStatusFilterExists = false;
    private boolean isUserFilterExists = false;

    public ArrayList<AssignedToDoUsersBean> getArrAssignedToDoUsersBean() {
        return arrAssignedToDoUsersBean;
    }

    public void setArrAssignedToDoUsersBean(ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean) {
        this.arrAssignedToDoUsersBean = arrAssignedToDoUsersBean;
    }

    public boolean isDateFilterExists() {
        return isDateFilterExists;
    }

    public void setDateFilterExists(boolean dateFilterExists) {
        isDateFilterExists = dateFilterExists;
    }

    public boolean isStatusFilterExists() {
        return isStatusFilterExists;
    }

    public void setStatusFilterExists(boolean statusFilterExists) {
        isStatusFilterExists = statusFilterExists;
    }

    public boolean isUserFilterExists() {
        return isUserFilterExists;
    }

    public void setUserFilterExists(boolean userFilterExists) {
        isUserFilterExists = userFilterExists;
    }

    public Long getlStartDate() {
        return lStartDate;
    }

    public void setlStartDate(Long lStartDate) {
        this.lStartDate = lStartDate;
    }

    public Long getlEndDate() {
        return lEndDate;
    }

    public void setlEndDate(Long lEndDate) {
        this.lEndDate = lEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSelectedScheduledSendTimeZone() {
        return selectedScheduledSendTimeZone;
    }

    public void setSelectedScheduledSendTimeZone(String selectedScheduledSendTimeZone) {
        this.selectedScheduledSendTimeZone = selectedScheduledSendTimeZone;
    }

    public String getScheduledStatus() {
        return scheduledStatus;
    }

    public void setScheduledStatus(String scheduledStatus) {
        this.scheduledStatus = scheduledStatus;
    }

    public String getSelectedScheduledSendDay() {
        return selectedScheduledSendDay;
    }

    public void setSelectedScheduledSendDay(String selectedScheduledSendDay) {
        this.selectedScheduledSendDay = selectedScheduledSendDay;
    }

    public String getSelectedScheduledSendTime() {
        return selectedScheduledSendTime;
    }

    public void setSelectedScheduledSendTime(String selectedScheduledSendTime) {
        this.selectedScheduledSendTime = selectedScheduledSendTime;
    }



    public Long getTodoScheduleSendDate() {
        return todoScheduleSendDate;
    }

    public void setTodoScheduleSendDate(Long todoScheduleSendDate) {
        this.todoScheduleSendDate = todoScheduleSendDate;
    }

    public String getHumanTodoScheduleSendDate() {
        return humanTodoScheduleSendDate;
    }

    public void setHumanTodoScheduleSendDate(String humanTodoScheduleSendDate) {
        this.humanTodoScheduleSendDate = humanTodoScheduleSendDate;
    }

    public String getTodoReminderScheduleId() {
        return todoReminderScheduleId;
    }

    public void setTodoReminderScheduleId(String todoReminderScheduleId) {
        this.todoReminderScheduleId = todoReminderScheduleId;
    }

    public String getTodoEventId() {
        return todoEventId;
    }

    public void setTodoEventId(String todoEventId) {
        this.todoEventId = todoEventId;
    }

    public ArrayList<String> getArrAssignedUserId() {
        return arrAssignedUserId;
    }

    public void setArrAssignedUserId(ArrayList<String> arrAssignedUserId) {
        this.arrAssignedUserId = arrAssignedUserId;
    }

    public Constants.USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(Constants.USER_TYPE userType) {
        this.userType = userType;
    }

    public String getTodoTimeZone() {
        return todoTimeZone;
    }

    public void setTodoTimeZone(String todoTimeZone) {
        this.todoTimeZone = todoTimeZone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public Long getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(Long todoDate) {
        this.todoDate = todoDate;
    }

    public String getHumanTodoDate() {
        return humanTodoDate;
    }

    public void setHumanTodoDate(String humanTodoDate) {
        this.humanTodoDate = humanTodoDate;
    }

    public Constants.TODO_STATUS getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(Constants.TODO_STATUS todoStatus) {
        this.todoStatus = todoStatus;
    }
}
