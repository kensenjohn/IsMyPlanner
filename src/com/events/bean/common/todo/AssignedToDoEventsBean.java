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
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssignedToDoEventsBean {
    //GTASSIGNEDTODOEVENTS (ASSIGNEDTODOEVENTSID VARCHAR(45) NOT NULL, FK_TODOID  VARCHAR(45) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL
    private String assignedToDoEventsId = Constants.EMPTY;
    private String toDoId = Constants.EMPTY;
    private String eventId  = Constants.EMPTY;

    public AssignedToDoEventsBean() {
    }

    public AssignedToDoEventsBean(HashMap<String, String> hmResult) {
        this.assignedToDoEventsId = ParseUtil.checkNull( hmResult.get("ASSIGNEDTODOEVENTSID") );
        this.toDoId = ParseUtil.checkNull( hmResult.get("FK_TODOID") );
        this.eventId = ParseUtil.checkNull( hmResult.get("FK_EVENTID") );
    }

    public String getAssignedToDoEventsId() {
        return assignedToDoEventsId;
    }

    public void setAssignedToDoEventsId(String assignedToDoEventsId) {
        this.assignedToDoEventsId = assignedToDoEventsId;
    }

    public String getToDoId() {
        return toDoId;
    }

    public void setToDoId(String toDoId) {
        this.toDoId = toDoId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AssignedToDoEventsBean{");
        sb.append("assignedToDoEventsId='").append(assignedToDoEventsId).append('\'');
        sb.append(", toDoId='").append(toDoId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("assignedToDoEventsId", this.assignedToDoEventsId );
            jsonObject.put("todo_id", this.toDoId );
            jsonObject.put("event_id", this.eventId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
