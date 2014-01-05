package com.events.json;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
import org.json.JSONObject;

public class Payload
{
    private JSONObject jsonPayload = new JSONObject();

    public Payload(JSONObject jsonPayload)
    {
        this.jsonPayload = jsonPayload;
        this.isPayLoadExists = true;

    }

    private boolean isPayLoadExists = false;

    public boolean isPayLoadExists()
    {
        return isPayLoadExists;
    }

    public void setPayLoadExists(boolean isPayLoadExists)
    {
        this.isPayLoadExists = isPayLoadExists;
    }

    public JSONObject toJson()
    {
        return jsonPayload;
    }

}
