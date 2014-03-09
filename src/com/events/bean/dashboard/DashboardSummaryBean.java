package com.events.bean.dashboard;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/8/14
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class DashboardSummaryBean {
    private Long numberOfUnreadNotifications = 0L;

    public Long getNumberOfUnreadNotifications() {
        return numberOfUnreadNotifications;
    }

    public void setNumberOfUnreadNotifications(Long numberOfUnreadNotifications) {
        this.numberOfUnreadNotifications = numberOfUnreadNotifications;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DashboardSummaryBean{");
        sb.append("numberOfUnreadNotifications=").append(numberOfUnreadNotifications);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("num_of_unread_notifications", this.numberOfUnreadNotifications );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

