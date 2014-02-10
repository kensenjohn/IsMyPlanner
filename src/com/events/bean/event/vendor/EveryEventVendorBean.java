package com.events.bean.event.vendor;

import com.events.bean.vendors.partner.EveryPartnerVendorBean;
import com.events.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/10/14
 * Time: 5:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventVendorBean {
    private EventVendorBean eventVendorBean = new EventVendorBean();
    private EveryPartnerVendorBean everyPartnerVendorBean = new EveryPartnerVendorBean();
    private String eventId = Constants.EMPTY;
    private boolean isAssignedToEvent = false;
    private boolean isRecommendedForEvent = false;
    private boolean isShortlistedForEvent = false;

    public EventVendorBean getEventVendorBean() {
        return eventVendorBean;
    }

    public void setEventVendorBean(EventVendorBean eventVendorBean) {
        this.eventVendorBean = eventVendorBean;
    }

    public EveryPartnerVendorBean getEveryPartnerVendorBean() {
        return everyPartnerVendorBean;
    }

    public void setEveryPartnerVendorBean(EveryPartnerVendorBean everyPartnerVendorBean) {
        this.everyPartnerVendorBean = everyPartnerVendorBean;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isAssignedToEvent() {
        return isAssignedToEvent;
    }

    public void setAssignedToEvent(boolean assignedToEvent) {
        isAssignedToEvent = assignedToEvent;
    }

    public boolean isRecommendedForEvent() {
        return isRecommendedForEvent;
    }

    public void setRecommendedForEvent(boolean recommendedForEvent) {
        isRecommendedForEvent = recommendedForEvent;
    }

    public boolean isShortlistedForEvent() {
        return isShortlistedForEvent;
    }

    public void setShortlistedForEvent(boolean shortlistedForEvent) {
        isShortlistedForEvent = shortlistedForEvent;
    }

    @Override
    public String toString() {
        return "EveryEventVendorBean{" +
                "eventVendorBean=" + eventVendorBean +
                ", everyPartnerVendorBean=" + everyPartnerVendorBean +
                ", eventId='" + eventId + '\'' +
                ", isAssignedToEvent=" + isAssignedToEvent +
                ", isRecommendedForEvent=" + isRecommendedForEvent +
                ", isShortlistedForEvent=" + isShortlistedForEvent +
                '}';
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("is_assigned_to_event", this.isAssignedToEvent );
            jsonObject.put("is_recommended_for_event", this.isRecommendedForEvent );
            jsonObject.put("is_shortlisted_for_event", this.isShortlistedForEvent );
            jsonObject.put("event_vendor_bean", this.eventVendorBean.toJson() );
            jsonObject.put("every_partner_vendor_bean", this.everyPartnerVendorBean.toJson() );
         } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
