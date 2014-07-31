package com.events.bean.event.checklist;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 7/1/14
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventChecklistTodoBean {
    // EVENTCHECKLISTTODOID VARCHAR(45) NOT NULL, FK_EVENTCHECKLISTITEMID VARCHAR(45) NOT NULL, FK_EVENTCHECKLISTID VARCHAR(45) NOT NULL,
    // FK_TODOID VARCHAR(45) NOT NULL, NAME VARCHAR(500) NOT NULL, CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    // IS_COMPLETE INT(1) NOT NULL DEFAULT 0 , PRIMARY KEY (EVENTCHECKLISTTODOID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String eventChecklistTodoId = Constants.EMPTY;
    private String eventChecklistItemId = Constants.EMPTY;
    private String eventChecklistId = Constants.EMPTY;
    private String todoId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private boolean isComplete = false;

    public EventChecklistTodoBean() {
    }

    public EventChecklistTodoBean( HashMap<String,String> hmResult) {
        this.eventChecklistTodoId = ParseUtil.checkNull(hmResult.get("EVENTCHECKLISTTODOID"));
        this.eventChecklistItemId = ParseUtil.checkNull(hmResult.get("FK_EVENTCHECKLISTITEMID"));
        this.eventChecklistId = ParseUtil.checkNull(hmResult.get("FK_EVENTCHECKLISTID"));
        this.todoId = ParseUtil.checkNull(hmResult.get("FK_TODOID"));
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
        this.isComplete = ParseUtil.sTob(hmResult.get("IS_COMPLETE"));
    }

    public String getEventChecklistTodoId() {
        return eventChecklistTodoId;
    }

    public void setEventChecklistTodoId(String eventChecklistTodoId) {
        this.eventChecklistTodoId = eventChecklistTodoId;
    }

    public String getEventChecklistItemId() {
        return eventChecklistItemId;
    }

    public void setEventChecklistItemId(String eventChecklistItemId) {
        this.eventChecklistItemId = eventChecklistItemId;
    }

    public String getEventChecklistId() {
        return eventChecklistId;
    }

    public void setEventChecklistId(String eventChecklistId) {
        this.eventChecklistId = eventChecklistId;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventChecklistTodoBean{");
        sb.append("eventChecklistTodoId='").append(eventChecklistTodoId).append('\'');
        sb.append(", eventChecklistItemId='").append(eventChecklistItemId).append('\'');
        sb.append(", eventChecklistId='").append(eventChecklistId).append('\'');
        sb.append(", todoId='").append(todoId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", isComplete=").append(isComplete);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_checklist_todo_id", this.eventChecklistTodoId );
            jsonObject.put("event_checklist_item_id", this.eventChecklistItemId );
            jsonObject.put("event_checklist_id", this.eventChecklistId );
            jsonObject.put("todo_id", this.todoId );
            jsonObject.put("name", this.name );
            jsonObject.put("is_complete", this.isComplete );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
