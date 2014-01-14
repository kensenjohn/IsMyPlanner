package com.events.bean.common.email;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailTemplateBean {

    private String emailTemplateId = Constants.EMPTY;
    private String fromAddress = Constants.EMPTY;
    private String fromAddressName = Constants.EMPTY;
    private String toAddress = Constants.EMPTY;
    private String toAddressName = Constants.EMPTY;
    private String emailSubject = Constants.EMPTY;
    private String htmlBody = Constants.EMPTY;
    private String textBody = Constants.EMPTY;
    private String templateName = Constants.EMPTY;

    public EmailTemplateBean(HashMap<String, String> hmEmailTemplate) {
        this.emailTemplateId = ParseUtil.checkNull(hmEmailTemplate.get("EMAILTEMPLATEID"));
        this.fromAddress = ParseUtil.checkNull(hmEmailTemplate.get("FROM_EMAIL_ADDRESS"));
        this.fromAddressName = ParseUtil.checkNull(hmEmailTemplate.get("FROM_ADDRESS_NAME"));
        this.toAddress = ParseUtil.checkNull(hmEmailTemplate.get("TO_EMAIL_ADDRESS"));
        this.toAddressName = ParseUtil.checkNull(hmEmailTemplate.get("TO_ADDRESS_NAME"));
        this.emailSubject = ParseUtil.checkNull(hmEmailTemplate.get("EMAIL_SUBJECT"));
        this.htmlBody = ParseUtil.checkNull(hmEmailTemplate.get("HTML_BODY"));
        this.textBody = ParseUtil.checkNull(hmEmailTemplate.get("TEXT_BODY"));
        this.templateName = ParseUtil.checkNull(hmEmailTemplate.get("EMAILTEMPLATENAME"));
    }

    public EmailTemplateBean() {}

    public String getEmailTemplateId() {
        return emailTemplateId;
    }

    public void setEmailTemplateId(String emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromAddressName() {
        return fromAddressName;
    }

    public void setFromAddressName(String fromAddressName) {
        this.fromAddressName = fromAddressName;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToAddressName() {
        return toAddressName;
    }

    public void setToAddressName(String toAddressName) {
        this.toAddressName = toAddressName;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email_template_id", this.emailTemplateId);
            jsonObject.put("from_address", this.fromAddress);
            jsonObject.put("from_address_name", this.fromAddressName);
            jsonObject.put("to_address", this.toAddress);
            jsonObject.put("to_address_name", this.toAddressName);
            jsonObject.put("email_subject", this.emailSubject);
            jsonObject.put("html_body", this.htmlBody);
            jsonObject.put("text_body", this.textBody);
            jsonObject.put("template_name", this.templateName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "EmailTemplateBean{" +
                "emailTemplateId='" + emailTemplateId + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", fromAddressName='" + fromAddressName + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", toAddressName='" + toAddressName + '\'' +
                ", emailSubject='" + emailSubject + '\'' +
                ", htmlBody='" + htmlBody + '\'' +
                ", textBody='" + textBody + '\'' +
                ", templateName='" + templateName + '\'' +
                '}';
    }
}
