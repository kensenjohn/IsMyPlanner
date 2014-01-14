package com.events.bean.common.email;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/10/14
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventEmailBean {
    // EVENTEMAILID VARCHAR(45) NOT NULL, FK_EVENTID VARCHAR(45) NOT NULL, FROM_ADDRESS_NAME VARCHAR(256) NOT NULL, FROM_EMAIL_ADDRESS VARCHAR(256) NOT NULL,
    // TO_ADDRESS_RULES VARCHAR(45) , EMAIL_SUBJECT TEXT, HTML_BODY TEXT NOT NULL, TEXT_BODY TEXT NOT NULL, CREATEDATE BIGINT(20) NOT NULL DEFAULT 0 , HUMANCREATEDATE

    private String eventEmailId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String fromAddressName = Constants.EMPTY;
    private String fromAddressEmail = Constants.EMPTY;
    private String subject = Constants.EMPTY;
    private String htmlBody = Constants.EMPTY;
    private String textBody = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public EventEmailBean(){}
    public EventEmailBean( HashMap<String,String> hmEventEmailRes) {
        this.eventEmailId = ParseUtil.checkNull( hmEventEmailRes.get("EVENTEMAILID") );
        this.eventId = ParseUtil.checkNull( hmEventEmailRes.get("FK_EVENTID") );
        this.userId = ParseUtil.checkNull( hmEventEmailRes.get("FK_USERID") );
        this.fromAddressName = ParseUtil.checkNull( hmEventEmailRes.get("FROM_ADDRESS_NAME") );
        this.fromAddressEmail = ParseUtil.checkNull( hmEventEmailRes.get("FROM_EMAIL_ADDRESS") );
        this.subject = ParseUtil.checkNull( hmEventEmailRes.get("EMAIL_SUBJECT") );
        this.htmlBody = ParseUtil.checkNull( hmEventEmailRes.get("HTML_BODY") );
        this.textBody = ParseUtil.checkNull( hmEventEmailRes.get("TEXT_BODY") );
        this.createDate = ParseUtil.sToL( hmEventEmailRes.get("CREATEDATE") );
        this.humanCreateDate = ParseUtil.checkNull( hmEventEmailRes.get("HUMANCREATEDATE") );
    }
    public String getEventEmailId() {
        return eventEmailId;
    }

    public void setEventEmailId(String eventEmailId) {
        this.eventEmailId = eventEmailId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromAddressName() {
        return fromAddressName;
    }

    public void setFromAddressName(String fromAddressName) {
        this.fromAddressName = fromAddressName;
    }

    public String getFromAddressEmail() {
        return fromAddressEmail;
    }

    public void setFromAddressEmail(String fromAddressEmail) {
        this.fromAddressEmail = fromAddressEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
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
        final StringBuilder sb = new StringBuilder("EventEmailBean{");
        sb.append("eventEmailId='").append(eventEmailId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", userID='").append(userId).append('\'');
        sb.append(", fromAddressName='").append(fromAddressName).append('\'');
        sb.append(", fromAddressEmail='").append(fromAddressEmail).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", htmlBody='").append(htmlBody).append('\'');
        sb.append(", textBody='").append(textBody).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_email_id", this.eventEmailId);
            jsonObject.put("event_id", this.eventId);
            jsonObject.put("user_id", this.userId);
            jsonObject.put("from_address_name", this.fromAddressName);
            jsonObject.put("from_address_email", this.fromAddressEmail);
            jsonObject.put("email_subject", this.subject);
            jsonObject.put("html_body", this.htmlBody);
            jsonObject.put("text_body", this.textBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
