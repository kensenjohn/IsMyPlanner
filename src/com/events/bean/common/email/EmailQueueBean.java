package com.events.bean.common.email;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class EmailQueueBean extends EmailObject{
    private String emailQueueId = Constants.EMPTY;

    public EmailQueueBean(){}
    public EmailQueueBean(HashMap<String, String> hmEmailQueue) {
        this.emailQueueId = ParseUtil.checkNull(hmEmailQueue.get("EMAILQUEUEID"));
        this.fromAddress = ParseUtil.checkNull(hmEmailQueue.get("FROM_ADDRESS"));
        this.fromAddressName = ParseUtil.checkNull(hmEmailQueue.get("FROM_ADDRESS_NAME"));
        this.toAddress = ParseUtil.checkNull(hmEmailQueue.get("TO_ADDRESS"));
        this.toAddressName = ParseUtil.checkNull(hmEmailQueue.get("TO_ADDRESS_NAME"));
        this.ccAddress = ParseUtil.checkNull(hmEmailQueue.get("CC_ADDRESS"));
        this.ccAddressName = ParseUtil.checkNull(hmEmailQueue.get("CC_ADDRESSNAME"));
        this.bccAddress = ParseUtil.checkNull(hmEmailQueue.get("BCC_ADDRESS"));
        this.bccAddressName = ParseUtil.checkNull(hmEmailQueue.get("BCC_ADDRESSNAME"));
        this.emailSubject = ParseUtil.checkNull(hmEmailQueue.get("EMAIL_SUBJECT"));
        this.htmlBody = ParseUtil.checkNull(hmEmailQueue.get("HTML_BODY"));
        this.textBody = ParseUtil.checkNull(hmEmailQueue.get("TEXT_BODY"));
        this.status = ParseUtil.checkNull(hmEmailQueue.get("STATUS"));
    }

    public String getEmailQueueId() {
        return emailQueueId;
    }

    public void setEmailQueueId(String emailQueueId) {
        this.emailQueueId = emailQueueId;
    }

    @Override
    public String getFromAddress() {
        return this.fromAddress;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    @Override
    public String getFromAddressName() {
        return this.fromAddressName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setFromAddressName(String fromAddressName) {
        this.fromAddressName = fromAddressName;
    }

    @Override
    public String getToAddress() {
        return this.toAddressName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setToAddress(String toAddressName) {
        this.toAddressName = toAddressName;
    }

    @Override
    public String getCcAddress() {
        return this.ccAddress;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    @Override
    public String getBccAddress() {
        return this.bccAddress;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBccAddress(String bccAddress) {
        this.bccAddress = bccAddress;
    }

    @Override
    public String getEmailSubject() {
        return this.emailSubject;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    @Override
    public String getHtmlBody() {
        return this.htmlBody;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    @Override
    public String getTextBody() {
        return this.textBody;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    @Override
    public String getStatus() {
        return this.status;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getToAddressName() {
        return this.toAddressName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setToAddressName(String toAddressName) {
        this.toAddressName = toAddressName;
    }

    @Override
    public String getCcAddressName() {
        return this.ccAddressName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCcAddressName(String ccAddressName) {
        this.ccAddressName = ccAddressName;
    }

    @Override
    public String getBccAddressName() {
        return this.bccAddressName;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBccAddressName(String bccAddressName) {
        this.bccAddressName = bccAddressName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmailQueueBean{");
        sb.append("emailQueueId='").append(emailQueueId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
