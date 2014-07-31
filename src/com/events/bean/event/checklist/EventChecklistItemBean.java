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
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventChecklistItemBean {
    // GTEVENTCHECKLISTITEM( EVENTCHECKLISTITEMID VARCHAR(45) NOT NULL, FK_EVENTCHECKLISTID VARCHAR(45) NOT NULL,
    // NAME VARCHAR(500) NOT NULL, CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    // SORT_ORDER  BIGINT(20) NOT NULL DEFAULT -1, IS_COMPLETE INT(1) NOT NULL DEFAULT 0 , PRIMARY KEY (EVENTCHECKLISTITEMID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String eventChecklistItemId = Constants.EMPTY;
    private String eventChecklistId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private boolean isComplete = false;
    private Long sortOrder = 0L;

    public EventChecklistItemBean() {
    }

    public EventChecklistItemBean( HashMap<String,String> hmResult ) {
        this.eventChecklistItemId = ParseUtil.checkNull(hmResult.get("EVENTCHECKLISTITEMID"));
        this.eventChecklistId = ParseUtil.checkNull(hmResult.get("FK_EVENTCHECKLISTID"));
        this.name = ParseUtil.checkNull(hmResult.get("NAME"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
        this.isComplete = ParseUtil.sTob(hmResult.get("IS_COMPLETE"));
        this.sortOrder = ParseUtil.sToL(hmResult.get("SORT_ORDER"));
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

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventChecklistItemBean{");
        sb.append("eventChecklistItemId='").append(eventChecklistItemId).append('\'');
        sb.append(", eventChecklistId='").append(eventChecklistId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", isComplete=").append(isComplete);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_checklist_item_id", this.eventChecklistItemId );
            jsonObject.put("event_checklist_id", this.eventChecklistId );
            jsonObject.put("name", this.name );
            jsonObject.put("sort_order", this.sortOrder );
            jsonObject.put("is_complete", this.isComplete );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
