package com.events.bean.event;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventBean {
    private String eventId = Constants.EMPTY;
    private String eventName = Constants.EMPTY;
    private String folderId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private Long eventDate = 0L;
    private String humanEventDate = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private Long modifiedDate = 0L;
    private String humanModifiedDate = Constants.EMPTY;
    private boolean isDeleteRow = false;

    private EventDisplayDateBean eventDisplayDateBean = new EventDisplayDateBean();

    public EventBean() {

    }
    public EventBean(HashMap<String,String> hmResult) {
        this.eventId = ParseUtil.checkNull(hmResult.get("EVENTID"));
        this.eventName = ParseUtil.checkNull(hmResult.get("EVENTNAME"));
        this.folderId = ParseUtil.checkNull(hmResult.get("FK_FOLDERID"));
        this.clientId = ParseUtil.checkNull(hmResult.get("FK_CLIENTID"));
        this.vendorId = ParseUtil.checkNull(hmResult.get("FK_VENDORID"));
        this.eventDate = ParseUtil.sToL(hmResult.get("EVENTDATE"));
        this.humanEventDate = ParseUtil.checkNull(hmResult.get("HUMANEVENTDATE"));
        this.isDeleteRow = ParseUtil.sTob( hmResult.get("DEL_ROW") );
    }
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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

    public boolean isDeleteRow() {
        return isDeleteRow;
    }

    public void setDeleteRow(boolean deleteRow) {
        isDeleteRow = deleteRow;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getHumanEventDate() {
        return humanEventDate;
    }

    public void setHumanEventDate(String humanEventDate) {
        this.humanEventDate = humanEventDate;
    }

    public EventDisplayDateBean getEventDisplayDateBean() {
        return eventDisplayDateBean;
    }

    public void setEventDisplayDateBean(EventDisplayDateBean eventDisplayDateBean) {
        this.eventDisplayDateBean = eventDisplayDateBean;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("event_name", this.eventName );
            jsonObject.put("folder_id", this.folderId );
            jsonObject.put("client_id", this.clientId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("event_date", this.eventDate );
            jsonObject.put("event_human_date", this.humanEventDate );
            jsonObject.put("event_display_date", this.eventDisplayDateBean!=null?this.eventDisplayDateBean.toJson():"" );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventBean{");
        sb.append("eventId='").append(eventId).append('\'');
        sb.append(", eventName='").append(eventName).append('\'');
        sb.append(", folderId='").append(folderId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", modifiedDate=").append(modifiedDate);
        sb.append(", humanModifiedDate='").append(humanModifiedDate).append('\'');
        sb.append(", displayDate='").append(eventDisplayDateBean).append('\'');
        sb.append(", isDeleteRow=").append(isDeleteRow);
        sb.append('}');
        return sb.toString();
    }


}
