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
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventChecklistBean {
    //GTEVENTCHECKLIST(EVENTCHECKLISTID VARCHAR(45) NOT NULL,  NAME VARCHAR(500) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL,
    // FK_VENDORID  VARCHAR(45) NOT NULL , FK_CLIENTID  VARCHAR(45) NOT NULL , CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANCREATEDATE VARCHAR(45), PRIMARY KEY (EVENTCHECKLISTID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String eventChecklistId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public EventChecklistBean() {
    }

    public EventChecklistBean(HashMap<String,String> hmResult) {
        this.eventChecklistId = ParseUtil.checkNull( hmResult.get("EVENTCHECKLISTID"));
        this.name =  ParseUtil.checkNull( hmResult.get("NAME"));
        this.eventId =  ParseUtil.checkNull( hmResult.get("FK_EVENTID"));
        this.vendorId =  ParseUtil.checkNull( hmResult.get("FK_VENDORID"));
        this.clientId =  ParseUtil.checkNull( hmResult.get("FK_CLIENTID"));
        this.createDate =  ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate =  ParseUtil.checkNull( hmResult.get("HUMANCREATEDATE"));
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    public String getEventChecklistId() {
        return eventChecklistId;
    }

    public void setEventChecklistId(String eventChecklistId) {
        this.eventChecklistId = eventChecklistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventChecklistBean{");
        sb.append("eventChecklistId='").append(eventChecklistId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_checklist_id", this.eventChecklistId );
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("name", this.name );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("client_id", this.clientId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
