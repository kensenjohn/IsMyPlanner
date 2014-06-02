package com.events.bean.common.todo;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/9/14
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToDoBean {
    private String toDoId = Constants.EMPTY;
    private String toDo = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private Constants.USER_TYPE userType = Constants.USER_TYPE.NONE;
    private Constants.TODO_STATUS todoStatus = Constants.TODO_STATUS.ACTIVE;
    private long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private long toDoDate = 0L;
    private String humanToDoDate = Constants.EMPTY;
    private String todoTimeZone = Constants.EMPTY;
    private String todoTimeZoneFormatted = Constants.EMPTY;

    public ToDoBean(){}
    public ToDoBean(HashMap<String, String> hmResult){
        if(hmResult!=null && !hmResult.isEmpty()){
            this.toDoId = ParseUtil.checkNull(hmResult.get("TODOID"));
            this.toDo = ParseUtil.checkNull(hmResult.get("TODO"));
            this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
            this.vendorId = ParseUtil.checkNull(hmResult.get("FK_VENDORID"));
            this.clientId = ParseUtil.checkNull(hmResult.get("FK_CLIENTID"));
            if(hmResult.get("USERTYPE")!=null){
                this.userType = Constants.USER_TYPE.valueOf( hmResult.get("USERTYPE") );
            }
            if(hmResult.get("TODOSTATUS")!=null){
                this.todoStatus = Constants.TODO_STATUS.valueOf( hmResult.get("TODOSTATUS") );
            }
            this.createDate = ParseUtil.sToL( hmResult.get("CREATEDATE") );
            this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
            this.toDoDate = ParseUtil.sToL( hmResult.get("TODODATE") );
            this.humanToDoDate = ParseUtil.checkNull( hmResult.get("HUMANTODODATE") );
            this.todoTimeZone = ParseUtil.checkNull( hmResult.get("TODOTIMEZONE") );
            if(!Utility.isNullOrEmpty(this.todoTimeZone)) {
                this.todoTimeZoneFormatted = Constants.TIME_ZONE.valueOf( this.todoTimeZone ).getTimeZoneDisplay();
            }
        }
    }

    public String getTodoTimeZone() {
        return todoTimeZone;
    }

    public void setTodoTimeZone(String todoTimeZone) {
        this.todoTimeZone = todoTimeZone;
    }

    public String getToDoId() {
        return toDoId;
    }

    public void setToDoId(String toDoId) {
        this.toDoId = toDoId;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
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

    public Constants.USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(Constants.USER_TYPE userType) {
        this.userType = userType;
    }

    public Constants.TODO_STATUS getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(Constants.TODO_STATUS todoStatus) {
        this.todoStatus = todoStatus;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    public long getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(long toDoDate) {
        this.toDoDate = toDoDate;
    }

    public String getHumanToDoDate() {
        return humanToDoDate;
    }

    public void setHumanToDoDate(String humanToDoDate) {
        this.humanToDoDate = humanToDoDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ToDoBean{");
        sb.append("toDoId='").append(toDoId).append('\'');
        sb.append(", toDo='").append(toDo).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", userType=").append(userType);
        sb.append(", todoStatus=").append(todoStatus);
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", toDoDate=").append(toDoDate);
        sb.append(", humanToDoDate='").append(humanToDoDate).append('\'');
        sb.append(", toDoTimeZone='").append(todoTimeZone).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todo_id", this.toDoId );
            jsonObject.put("todo", this.toDo );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("client_id", this.clientId );
            jsonObject.put("user_type", this.userType.getType() );
            jsonObject.put("todo_status", this.todoStatus.toString() );
            jsonObject.put("todo_date", this.humanToDoDate);
            jsonObject.put("todo_timezone", this.todoTimeZone);
            jsonObject.put("todo_timezone_formatted", this.todoTimeZoneFormatted);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
