package com.events.bean.common.todo;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class TodoReminderScheduleBean {
    //SELECTED_SCHEDULEDSENDDAY VARCHAR(45) NOT NULL, SELECTED_SCHEDULEDSENDTIME VARCHAR(45) NOT NULL, SELECTED_SCHEDULEDSENDTIMEZONE VARCHAR(45) NOT NULL

    private String todoReminderScheduleId = Constants.EMPTY;
    private String toDoId = Constants.EMPTY;
    private String userId  = Constants.EMPTY;
    private Long createDate = 0L;
    private String  humanCreateDate  = Constants.EMPTY;
    private Long scheduledSendDate = 0L;
    private String humanScheduledSendDate  = Constants.EMPTY;
    private String scheduleStatus = Constants.EMPTY;


    private String selectedScheduledSendDay  = Constants.EMPTY;
    private String selectedScheduledSendTime  = Constants.EMPTY;
    private String selectedScheduledSendTimeZone  = Constants.EMPTY;

    public TodoReminderScheduleBean() {
    }
    public TodoReminderScheduleBean(HashMap<String,String> hmResult) {
        if(hmResult!=null){
            this.todoReminderScheduleId = ParseUtil.checkNull(hmResult.get("TODOREMINDERSCHEDULEID"));
            this.toDoId = ParseUtil.checkNull(hmResult.get("FK_TODOID"));
            this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") );
            this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
            this.humanCreateDate =  ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
            this.humanScheduledSendDate =  ParseUtil.checkNull(hmResult.get("HUMANSCHEDULEDSENDDATE"));
            this.scheduledSendDate = ParseUtil.sToL(hmResult.get("SCHEDULEDSENDDATE"));
            this.scheduleStatus = ParseUtil.checkNull(hmResult.get("SCHEDULE_STATUS"));
            this.selectedScheduledSendDay =  ParseUtil.checkNull(hmResult.get("SELECTED_SCHEDULEDSENDDAY"));
            this.selectedScheduledSendTime =  ParseUtil.checkNull( hmResult.get("SELECTED_SCHEDULEDSENDTIME") );
            this.selectedScheduledSendTimeZone =  ParseUtil.checkNull( hmResult.get("SELECTED_SCHEDULEDSENDTIMEZONE") );
        }
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

    public String getSelectedScheduledSendTimeZone() {
        return selectedScheduledSendTimeZone;
    }

    public void setSelectedScheduledSendTimeZone(String selectedScheduledSendTimeZone) {
        this.selectedScheduledSendTimeZone = selectedScheduledSendTimeZone;
    }

    public String getTodoReminderScheduleId() {
        return todoReminderScheduleId;
    }

    public void setTodoReminderScheduleId(String todoReminderScheduleId) {
        this.todoReminderScheduleId = todoReminderScheduleId;
    }

    public String getToDoId() {
        return toDoId;
    }

    public void setToDoId(String toDoId) {
        this.toDoId = toDoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    public Long getScheduledSendDate() {
        return scheduledSendDate;
    }

    public void setScheduledSendDate(Long scheduledSendDate) {
        this.scheduledSendDate = scheduledSendDate;
    }

    public String getHumanScheduledSendDate() {
        return humanScheduledSendDate;
    }

    public void setHumanScheduledSendDate(String humanScheduledSendDate) {
        this.humanScheduledSendDate = humanScheduledSendDate;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TodoReminderScheduleBean{");
        sb.append("todoReminderScheduleId='").append(todoReminderScheduleId).append('\'');
        sb.append(", toDoId='").append(toDoId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", scheduledSendDate=").append(scheduledSendDate);
        sb.append(", humanScheduledSendDate='").append(humanScheduledSendDate).append('\'');
        sb.append(", scheduleStatus='").append(scheduleStatus).append('\'');
        sb.append(", selectedScheduledSendDay='").append(selectedScheduledSendDay).append('\'');
        sb.append(", selectedScheduledSendTime='").append(selectedScheduledSendTime).append('\'');
        sb.append(", selectedScheduledSendTimeZone='").append(selectedScheduledSendTimeZone).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("toDoReminderScheduleId", this.todoReminderScheduleId );
            jsonObject.put("todo_id", this.toDoId );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("create_date", this.createDate );
            jsonObject.put("human_create_date", this.humanCreateDate );
            jsonObject.put("schedule_send_date", this.scheduledSendDate );
            jsonObject.put("human_schedule_send_date", this.humanScheduledSendDate );
            jsonObject.put("schedule_status", this.scheduleStatus );
            jsonObject.put("selected_scheduled_send_day", this.selectedScheduledSendDay );
            jsonObject.put("selected_scheduled_send_time", this.selectedScheduledSendTime );
            jsonObject.put("selected_scheduled_send_timezone", this.selectedScheduledSendTimeZone );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
