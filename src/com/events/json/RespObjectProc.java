package com.events.json;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class RespObjectProc {
    ArrayList<Text> arrOkText = new ArrayList<Text>();
    ArrayList<Text> arrErrorText = new ArrayList<Text>();
    JSONObject jsonResponseObj = new JSONObject();
    RespConstants.Status responseStatus = RespConstants.Status.ERROR;

    public JSONObject getJson() throws JSONException {
        Messages messages = new Messages();
        messages.setArrOkText(this.arrOkText);
        messages.setArrErrorText(this.arrErrorText);

        Payload payload = new Payload(this.jsonResponseObj);
        Response response = new Response();
        response.setPayload(payload);
        response.setMessages(messages);

        RespJsonObject respJsonObj = new RespJsonObject();
        respJsonObj.setStatus(this.responseStatus);
        respJsonObj.setResponse(response);

        return respJsonObj.toJson();
    }

    public void setErrorMessages(ArrayList<Text> arrErrorText) {
        this.arrErrorText = arrErrorText;
    }

    public void setOkMessages(ArrayList<Text> arrOkText) {
        this.arrOkText = arrOkText;
    }

    public void setJsonResponseObj(JSONObject jsonResponseObj) {
        this.jsonResponseObj = jsonResponseObj;
    }

    public void setResponseStatus(RespConstants.Status responseStatus) {
        this.responseStatus = responseStatus;
    }
}