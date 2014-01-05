package com.events.json;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Messages
{
    private ArrayList<Text> arrOkText = new ArrayList<Text>();
    private ArrayList<Text> arrErrorText = new ArrayList<Text>();

    private boolean isMessageExist = false;

    public ArrayList<Text> getArrOkText()
    {
        return arrOkText;
    }

    public void setArrOkText(ArrayList<Text> arrOkText)
    {
        this.arrOkText = arrOkText;
    }

    public ArrayList<Text> getArrErrorText()
    {
        return arrErrorText;
    }

    public void setArrErrorText(ArrayList<Text> arrErrorText)
    {
        this.arrErrorText = arrErrorText;
    }

    public boolean isMessageExist()
    {
        return isMessageExist;
    }

    public void setMessageExist(boolean isMessageExist)
    {
        this.isMessageExist = isMessageExist;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject jsonMessageObject = new JSONObject();

        JSONArray jsonOkArray = new JSONArray();

        if (arrOkText != null && !arrOkText.isEmpty())
        {
            for (Text text : arrOkText)
            {
                if (text != null)
                {
                    jsonOkArray.put(text.toJson());
                    isMessageExist = true;
                }

            }
        }

        jsonMessageObject.put(RespConstants.Key.OK_MSSG.getKey(), jsonOkArray);

        JSONArray jsonErrorArray = new JSONArray();

        if (arrErrorText != null && !arrErrorText.isEmpty())
        {
            for (Text text : arrErrorText)
            {
                if (text != null)
                {
                    jsonErrorArray.put(text.toJson());

                    isMessageExist = true;
                }

            }
        }

        jsonMessageObject.put(RespConstants.Key.ERROR_MSSG.getKey(), jsonErrorArray);

        return jsonMessageObject;

    }

}
