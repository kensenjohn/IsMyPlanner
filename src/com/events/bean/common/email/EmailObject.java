package com.events.bean.common.email;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EmailObject {
    protected String fromAddress = Constants.EMPTY;
    protected String fromAddressName = Constants.EMPTY;
    protected String toAddress = Constants.EMPTY;
    protected String toAddressName = Constants.EMPTY;
    protected String ccAddress = Constants.EMPTY;
    protected String ccAddressName = Constants.EMPTY;
    protected String bccAddress = Constants.EMPTY;
    protected String bccAddressName = Constants.EMPTY;
    protected String emailSubject = Constants.EMPTY;
    protected String htmlBody = Constants.EMPTY;
    protected String textBody = Constants.EMPTY;
    protected String status = Constants.EMPTY;


    public abstract String getFromAddress();

    public abstract void setFromAddress(String fromAddress);

    public abstract String getFromAddressName();

    public abstract void setFromAddressName(String fromAddressName);

    public abstract String getToAddress();

    public abstract void setToAddress(String toAddressName);

    public abstract String getCcAddress();

    public abstract void setCcAddress(String ccAddress);

    public abstract String getBccAddress();

    public abstract void setBccAddress(String bccAddress);

    public abstract String getEmailSubject();

    public abstract void setEmailSubject(String emailSubject);

    public abstract String getHtmlBody();

    public abstract void setHtmlBody(String htmlBody);

    public abstract String getTextBody();

    public abstract void setTextBody(String textBody);

    public abstract String getStatus();

    public abstract void setStatus(String status);

    public abstract  String getToAddressName();

    public abstract  void setToAddressName(String toAddressName);

    public abstract  String getCcAddressName();

    public abstract  void setCcAddressName(String ccAddressName);

    public abstract  String getBccAddressName();

    public abstract  void setBccAddressName(String bccAddressName);
}
