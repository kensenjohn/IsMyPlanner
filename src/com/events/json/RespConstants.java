package com.events.json;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class RespConstants
{
    public static enum Key
    {
        TEXT_LOC_ID("txt_loc_id"), TEXT("text"), OK_MSSG("ok_mssg"), ERROR_MSSG("error_mssg"), PAYLOAD_EXISTS(
            "is_payload_exist"), MESSAGES_EXIST("is_message_exist"), PAYLOAD("payload"), MESSAGES(
            "messages"), STATUS("status"), RESPONSE("response");

        private String key = "";

        Key(String key)
        {
            this.key = key;
        }

        public String getKey()
        {
            return this.key;
        }

    }

    public static enum Status
    {
        OK("ok"), ERROR("error");
        private String status = "";

        Status(String status)
        {
            this.status = status;
        }

        public String getStatus()
        {
            return this.status;
        }
    }
}
