package com.events.bean.event;

import com.events.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/28/13
 * Time: 6:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventDisplayDateBean {
    private String selectedDay = Constants.EMPTY;
    private String selectedTime = Constants.EMPTY;
    private String selectedTimeZone = Constants.EMPTY;

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDate) {
        this.selectedDay = selectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getSelectedTimeZone() {
        return selectedTimeZone;
    }

    public void setSelectedTimeZone(String selectedTimeZone) {
        this.selectedTimeZone = selectedTimeZone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventDisplayDateBean{");
        sb.append("selectedDay='").append(selectedDay).append('\'');
        sb.append(", selectedTime='").append(selectedTime).append('\'');
        sb.append(", selectedTimeZone='").append(selectedTimeZone).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("selected_day", this.selectedDay );
            jsonObject.put("selected_time", this.selectedTime );
            jsonObject.put("selected_timezone", this.selectedTimeZone );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
