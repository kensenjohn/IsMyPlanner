package com.events.json;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
import org.json.JSONException;
import org.json.JSONObject;

public class Response
{
    private Payload payload = new Payload(new JSONObject());
    private Messages messages = new Messages();

    public Payload getPayload()
    {
        return payload;
    }

    public void setPayload(Payload payload)
    {
        this.payload = payload;
    }

    public Messages getMessages()
    {
        return messages;
    }

    public void setMessages(Messages messages)
    {
        this.messages = messages;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject jsonResponseObject = new JSONObject();

        if (payload == null)
        {
            payload = new Payload(new JSONObject());
            payload.setPayLoadExists(false);
        }
        jsonResponseObject.put(RespConstants.Key.PAYLOAD.getKey(), payload.toJson());

        if (messages == null)
        {
            messages = new Messages();
            messages.setMessageExist(false);
        }

        jsonResponseObject.put(RespConstants.Key.MESSAGES.getKey(), messages.toJson());

        jsonResponseObject.put(RespConstants.Key.PAYLOAD_EXISTS.getKey(),
                payload.isPayLoadExists());
        jsonResponseObject.put(RespConstants.Key.MESSAGES_EXIST.getKey(),
                messages.isMessageExist());

        return jsonResponseObject;

    }
}
