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
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssignedToDoUsersBean {
    //GTASSIGNEDTODOUSERS(ASSIGNEDTODOUSERSID VARCHAR(45) NOT NULL, FK_TODOID  VARCHAR(45) NOT NULL, FK_USERID  VARCHAR(45) NOT NULL ,
    // PRIMARY KEY (ASSIGNEDTODOUSERSID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    private String assignedTodoUserId = Constants.EMPTY;
    private String toDoId  = Constants.EMPTY;
    private String userId  = Constants.EMPTY;

    public AssignedToDoUsersBean() {
    }

    public AssignedToDoUsersBean(HashMap<String, String> hmResult) {
        if(hmResult!=null && !hmResult.isEmpty()){
            this.assignedTodoUserId = ParseUtil.checkNull(hmResult.get("ASSIGNEDTODOUSERSID"));
            this.toDoId = ParseUtil.checkNull(hmResult.get("FK_TODOID"));
            this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
        }
    }

    public String getAssignedTodoUserId() {
        return assignedTodoUserId;
    }

    public void setAssignedTodoUserId(String assignedTodoUserId) {
        this.assignedTodoUserId = assignedTodoUserId;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AssignedToDoUsersBean{");
        sb.append("assignedTodoUserId='").append(assignedTodoUserId).append('\'');
        sb.append(", todoId='").append(toDoId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("assignedtodouserid", this.assignedTodoUserId );
            jsonObject.put("todo_id", this.toDoId );
            jsonObject.put("user_id", this.userId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
