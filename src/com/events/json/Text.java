package com.events.json;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Text
{
    protected String text = "";
    protected String textLocationId = "";

    public Text(String text, String textLocationId)
    {
        super();
        this.text = text;
        this.textLocationId = textLocationId;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject jsonMessageObject = new JSONObject();

        jsonMessageObject.put(RespConstants.Key.TEXT_LOC_ID.getKey(), textLocationId);
        jsonMessageObject.put(RespConstants.Key.TEXT.getKey(), text);

        return jsonMessageObject;

    }

}