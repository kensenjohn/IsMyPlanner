package com.events.json;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
import org.json.JSONException;
import org.json.JSONObject;

public class RespJsonObject
{
    private RespConstants.Status status = null;
    private Response response = new Response();

    public void setStatus(RespConstants.Status status)
    {
        this.status = status;
    }

    public void setResponse(Response response)
    {
        this.response = response;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject customRespJsonObject = new JSONObject();

        if (this.response == null)
        {
            this.response = new Response();
        }
        customRespJsonObject.put(RespConstants.Key.RESPONSE.getKey(), this.response.toJson());

        if (this.status == null)
        {
            this.status = RespConstants.Status.ERROR;
        }
        customRespJsonObject.put(RespConstants.Key.STATUS.getKey(), this.status.getStatus());

        return customRespJsonObject;

    }
}
