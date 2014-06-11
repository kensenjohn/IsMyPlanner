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
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConversationMessageAttachment {
    // GTCONVOMESSAGEATTACHMENT( CONVOMESSAGEATTACHMENTID VARCHAR(45) NOT NULL, FK_CONVERSATIONID VARCHAR(45) NOT NULL,
    // FK_CONVOMESSAGEID VARCHAR(45) NOT NULL, FK_SHAREDFILESID VARCHAR(45) NOT NULL, PRIMARY KEY (CONVOMESSAGEATTACHMENTID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    private String conversationMessageAttachementId = Constants.EMPTY;
    private String conversationId = Constants.EMPTY;
    private String conversationMessageId = Constants.EMPTY;
    private String uploadId = Constants.EMPTY;


    public ConversationMessageAttachment() {
    }

    public ConversationMessageAttachment(HashMap<String,String> hmResult) {
        this.conversationMessageAttachementId = ParseUtil.checkNull( hmResult.get("CONVOMESSAGEATTACHMENTID") );
        this.conversationId = ParseUtil.checkNull( hmResult.get("FK_CONVERSATIONID") );
        this.conversationMessageId = ParseUtil.checkNull( hmResult.get("FK_CONVOMESSAGEID") );
        this.uploadId = ParseUtil.checkNull( hmResult.get("FK_UPLOADID") );
    }

    public String getConversationMessageAttachementId() {
        return conversationMessageAttachementId;
    }

    public void setConversationMessageAttachementId(String conversationMessageAttachementId) {
        this.conversationMessageAttachementId = conversationMessageAttachementId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationMessageId() {
        return conversationMessageId;
    }

    public void setConversationMessageId(String conversationMessageId) {
        this.conversationMessageId = conversationMessageId;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConversationMessageAttachment{");
        sb.append("conversationMessageAttachementId='").append(conversationMessageAttachementId).append('\'');
        sb.append(", conversationId='").append(conversationId).append('\'');
        sb.append(", conversationMessageId='").append(conversationMessageId).append('\'');
        sb.append(", uploadId='").append(uploadId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("conversation_mesage_id", this.conversationMessageId );
            jsonObject.put("conversation_id", this.conversationId );
            jsonObject.put("conversation_mesage_attachment_id", this.conversationMessageAttachementId );
            jsonObject.put("upload_id", this.uploadId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
