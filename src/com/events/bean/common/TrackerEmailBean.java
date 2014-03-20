package com.events.bean.common;

import com.events.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/20/14
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrackerEmailBean {
    private String eventEmailId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private Constants.EMAIL_METRIC_TYPE metricType = Constants.EMAIL_METRIC_TYPE.NONE;
    private String guestId = Constants.EMPTY;
    private String guestEmailAddress = Constants.EMPTY;
    private String url = Constants.EMPTY;
    private Long numberOfViews = 0L;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Constants.EMAIL_METRIC_TYPE getMetricType() {
        return metricType;
    }

    public void setMetricType(Constants.EMAIL_METRIC_TYPE metricType) {
        this.metricType = metricType;
    }

    public Long getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(Long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getGuestEmailAddress() {
        return guestEmailAddress;
    }

    public void setGuestEmailAddress(String guestEmailAddress) {
        this.guestEmailAddress = guestEmailAddress;
    }

    public String getEventEmailId() {
        return eventEmailId;
    }

    public void setEventEmailId(String eventEmailId) {
        this.eventEmailId = eventEmailId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TrackerEmailBean{");
        sb.append("eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", metricType=").append(metricType);
        sb.append(", guestId='").append(guestId).append('\'');
        sb.append(", guestEmailAddress='").append(guestEmailAddress).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", numberOfViews=").append(numberOfViews);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("event_email_id", this.eventEmailId );
            jsonObject.put("guest_id", this.guestId );
            jsonObject.put("email_address", this.guestEmailAddress );
            jsonObject.put("number_of_views", this.numberOfViews );
            jsonObject.put("url", this.url );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
