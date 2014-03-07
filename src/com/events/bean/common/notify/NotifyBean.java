package com.events.bean.common.notify;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/7/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotifyBean {
    private String from = Constants.EMPTY;
    private String to  = Constants.EMPTY;
    private String message = Constants.EMPTY;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
