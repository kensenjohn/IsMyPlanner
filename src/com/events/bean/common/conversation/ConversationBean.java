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
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConversationBean {
    //GTCONVERSATION( CONVERSATIONID VARCHAR(45) NOT NULL, NAME TEXT NOT NULL , CREATEDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANCREATEDATE VARCHAR(45), PRIMARY KEY (CONVERSATIONID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8;
    private String conversationId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String formattedHumanCreateDate = Constants.EMPTY;
    private Long modifiedDate = 0L;
    private String humanModifiedDate = Constants.EMPTY;
    private String formattedHumanModifiedDate = Constants.EMPTY;
    private boolean hasReadMessage = false;


    public ConversationBean() {
    }

    public ConversationBean(HashMap<String,String> hmResult) {
        this.conversationId =  ParseUtil.checkNull(hmResult.get("CONVERSATIONID"));
        this.name =  ParseUtil.checkNull(hmResult.get("NAME"));
        this.createDate =  ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate =  ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
        this.modifiedDate =  ParseUtil.sToL(hmResult.get("MODIFIEDDATE"));
        this.humanModifiedDate =  ParseUtil.checkNull(hmResult.get("HUMANMODIFIEDDATE"));
    }

    public boolean isHasReadMessage() {
        return hasReadMessage;
    }

    public void setHasReadMessage(boolean hasUnReadMessage) {
        this.hasReadMessage = hasUnReadMessage;
    }

    public String getFormattedHumanModifiedDate() {
        return formattedHumanModifiedDate;
    }

    public void setFormattedHumanModifiedDate(String formattedHumanModifiedDate) {
        this.formattedHumanModifiedDate = formattedHumanModifiedDate;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getHumanModifiedDate() {
        return humanModifiedDate;
    }

    public void setHumanModifiedDate(String humanModifiedDate) {
        this.humanModifiedDate = humanModifiedDate;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFormattedHumanCreateDate() {
        return formattedHumanCreateDate;
    }

    public void setFormattedHumanCreateDate(String formattedHumanCreateDate) {
        this.formattedHumanCreateDate = formattedHumanCreateDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConversationBean{");
        sb.append("conversationId='").append(conversationId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("conversation_id", this.conversationId );
            jsonObject.put("name", this.name );
            jsonObject.put("formatted_human_create_date", this.formattedHumanCreateDate );
            jsonObject.put("formatted_human_modified_date", this.formattedHumanModifiedDate );
            jsonObject.put("has_read_message", this.hasReadMessage );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
