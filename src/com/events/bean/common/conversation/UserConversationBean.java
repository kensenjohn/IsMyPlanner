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
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserConversationBean {
    // GTUSERCONVERSATION( USERCONVERSATIONID VARCHAR(45) NOT NULL, FK_CONVERSATIONID VARCHAR(45) NOT NULL ,
    // FK_USERID VARCHAR(45) NOT NULL, PRIMARY KEY (USERCONVERSATIONID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    // IS_CONVERSATION_HIDDEN INT(1) NOT NULL DEFAULT 0,  IS_CONVERSATION_DELETED INT(1) NOT NULL DEFAULT 0, IS_READ

    private String userConversationId = Constants.EMPTY;
    private String conversationId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    boolean isConversationHidden = false;
    boolean isConversationDeleted = false;
    boolean isRead = false;

    public UserConversationBean() {
    }

    public UserConversationBean(HashMap<String,String> hmResult) {
        this.userConversationId = ParseUtil.checkNull(hmResult.get("USERCONVERSATIONID"));
        this.conversationId = ParseUtil.checkNull(hmResult.get("FK_CONVERSATIONID"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
        this.isConversationHidden = ParseUtil.sTob(hmResult.get("IS_CONVERSATION_HIDDEN"));
        this.isConversationDeleted = ParseUtil.sTob(hmResult.get("IS_CONVERSATION_DELETED"));
        this.isRead = ParseUtil.sTob(hmResult.get("IS_READ"));
    }

    public boolean isConversationHidden() {
        return isConversationHidden;
    }

    public void setConversationHidden(boolean conversationHidden) {
        isConversationHidden = conversationHidden;
    }

    public boolean isConversationDeleted() {
        return isConversationDeleted;
    }

    public void setConversationDeleted(boolean conversationDeleted) {
        isConversationDeleted = conversationDeleted;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getUserConversationId() {
        return userConversationId;
    }

    public void setUserConversationId(String userConversationId) {
        this.userConversationId = userConversationId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserConversationBean{");
        sb.append("userConversationId='").append(userConversationId).append('\'');
        sb.append(", conversationId='").append(conversationId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_conversation_id", this.userConversationId );
            jsonObject.put("conversation_id", this.conversationId );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("is_conversation_hidden", this.isConversationHidden );
            jsonObject.put("is_conversation_deleted", this.isConversationDeleted );
            jsonObject.put("is_read", this.isRead );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
