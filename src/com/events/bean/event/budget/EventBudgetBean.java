package com.events.bean.event.budget;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/16/14
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventBudgetBean {
    //CREATE TABLE GTEVENTBUDGET ( EVENTBUDGETID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, ESTIMATE_BUDGET VARCHAR(45),
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45), FK_USERID VARCHAR(45), PRIMARY KEY (EVENTBUDGETID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String eventBudgetId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private Double estimateBudget = 0.0;
    private Long modifiedDate = 0L;
    private String humanModifiedDate = Constants.EMPTY;
    private String userId = Constants.EMPTY;

    public EventBudgetBean() {
    }

    public EventBudgetBean(HashMap<String, String> hmResult) {
        this.eventBudgetId = ParseUtil.checkNull(hmResult.get("EVENTBUDGETID"));
        this.eventId = ParseUtil.checkNull(hmResult.get("FK_EVENTID"));
        this.estimateBudget = ParseUtil.sToD(hmResult.get("ESTIMATE_BUDGET"));
        this.modifiedDate = ParseUtil.sToL(hmResult.get("MODIFIEDDATE"));
        this.humanModifiedDate = ParseUtil.checkNull(hmResult.get("HUMANMODIFIEDDATE"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
    }

    public String getEventBudgetId() {
        return eventBudgetId;
    }

    public void setEventBudgetId(String eventBudgetId) {
        this.eventBudgetId = eventBudgetId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Double getEstimateBudget() {
        return estimateBudget;
    }

    public void setEstimateBudget(Double estimateBudget) {
        this.estimateBudget = estimateBudget;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getHumanModifiedDate() {
        return humanModifiedDate;
    }

    public void setHumanModifiedDate(String humanModifiedDate) {
        this.humanModifiedDate = humanModifiedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventBudgetBean{");
        sb.append("eventBudgetId='").append(eventBudgetId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", estimateBudget=").append(estimateBudget);
        sb.append(", modifiedDate=").append(modifiedDate);
        sb.append(", humanModifiedDate='").append(humanModifiedDate).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_budget_id", this.eventBudgetId );
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("estimate_budget", this.estimateBudget );
            jsonObject.put("user_id", this.userId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
