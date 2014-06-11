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
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConversationMessageUserBean {
    // GTCONVOMESSAGEUSER( CONVOMESSAGEUSERID VARCHAR(45) NOT NULL, FK_CONVOMESSAGEID VARCHAR(45) NOT NULL,
    // FK_USERID VARCHAR(45) NOT NULL, CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),

    private String conversationMessageUserId = Constants.EMPTY;
    private String conversationMessageId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String formattedHumanCreateDate = Constants.EMPTY;
    private String humanCreateDate = Constants.EMPTY;
    private String userGivenName = Constants.EMPTY;

    public ConversationMessageUserBean() {
    }

    public ConversationMessageUserBean( HashMap<String,String> hmResult) {
        this.conversationMessageUserId = ParseUtil.checkNull( hmResult.get("CONVOMESSAGEUSERID") );
        this.conversationMessageId = ParseUtil.checkNull( hmResult.get("FK_CONVOMESSAGEID") );
        this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") );
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }

    public String getUserGivenName() {
        return userGivenName;
    }

    public void setUserGivenName(String userGivenName) {
        this.userGivenName = userGivenName;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getConversationMessageUserId() {
        return conversationMessageUserId;
    }

    public void setConversationMessageUserId(String conversationMessageUserId) {
        this.conversationMessageUserId = conversationMessageUserId;
    }

    public String getConversationMessageId() {
        return conversationMessageId;
    }

    public void setConversationMessageId(String conversationMessageId) {
        this.conversationMessageId = conversationMessageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFormattedHumanCreateDate() {
        return formattedHumanCreateDate;
    }

    public void setFormattedHumanCreateDate(String formattedHumanCreateDate) {
        this.formattedHumanCreateDate = formattedHumanCreateDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConversationMessageUserBean{");
        sb.append("conversationMessageUserId='").append(conversationMessageUserId).append('\'');
        sb.append(", conversationMessageId='").append(conversationMessageId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", formattedHumanCreateDate='").append(formattedHumanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("conversation_mesage_user_id", this.conversationMessageUserId );
            jsonObject.put("conversation_message_id", this.conversationMessageId );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("formatted_human_create_date", this.formattedHumanCreateDate );
            jsonObject.put("user_given_name", this.userGivenName );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
