package com.events.bean.common.email;

import com.events.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventEmailBean {
    private String eventId = Constants.EMPTY;
    private String eventEmailId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String sendDate = Constants.EMPTY;
    private String sendRule = Constants.EMPTY;
    private String status = Constants.EMPTY;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventEmailId() {
        return eventEmailId;
    }

    public void setEventEmailId(String eventEmailId) {
        this.eventEmailId = eventEmailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendRule() {
        return sendRule;
    }

    public void setSendRule(String sendRule) {
        this.sendRule = sendRule;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryEventEmailBean{");
        sb.append("eventId='").append(eventId).append('\'');
        sb.append(", eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", sendDate='").append(sendDate).append('\'');
        sb.append(", sendRule='").append(sendRule).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("eventemail_id", this.eventEmailId );
            jsonObject.put("eventemail_name", this.name );
            jsonObject.put("send_date", this.sendDate );
            jsonObject.put("send_rule", this.sendRule );
            jsonObject.put("status", this.status );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
