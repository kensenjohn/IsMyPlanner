package com.events.bean.common.conversation;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/2/14
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConversationMessageBean {
    // GTCONVOMESSAGE( CONVOMESSAGEID VARCHAR(45) NOT NULL, FK_CONVERSATIONID VARCHAR(45) NOT NULL, MESSAGE TEXT NOT NULL,
    // CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),

    private String conversationMessageId = Constants.EMPTY;
    private String conversationId = Constants.EMPTY;
    private String message = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String formattedHumanCreateDate = Constants.EMPTY;

    public ConversationMessageBean() {
    }

    public ConversationMessageBean(HashMap<String,String> hmResult) {
        this.conversationMessageId = ParseUtil.checkNull(hmResult.get("CONVOMESSAGEID"));
        this.conversationId = ParseUtil.checkNull(hmResult.get("FK_CONVERSATIONID"));
        this.message = ParseUtil.checkNull(hmResult.get("MESSAGE"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
    }

    public String getFormattedHumanCreateDate() {
        return formattedHumanCreateDate;
    }

    public void setFormattedHumanCreateDate(String formattedHumanCreateDate) {
        this.formattedHumanCreateDate = formattedHumanCreateDate;
    }

    public String getConversationMessageId() {
        return conversationMessageId;
    }

    public void setConversationMessageId(String conversationMessageId) {
        this.conversationMessageId = conversationMessageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConversationMessageBean{");
        sb.append("conversationMessageId='").append(conversationMessageId).append('\'');
        sb.append(", conversationId='").append(conversationId).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("conversation_message_id", this.conversationMessageId );
            jsonObject.put("conversation_id", this.conversationId );
            jsonObject.put("message", this.message );
            jsonObject.put("formatted_human_create_date", this.formattedHumanCreateDate );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
