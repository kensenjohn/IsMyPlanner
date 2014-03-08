package com.events.bean.common.notify;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/7/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotifyBean {
    private String from = Constants.EMPTY;
    private String fromName = Constants.EMPTY;
    private String to  = Constants.EMPTY;
    private String toName  = Constants.EMPTY;
    private String message = Constants.EMPTY;

    public NotifyBean(){}
    public NotifyBean(HashMap<String,String> hmResult) {
        if(hmResult!=null && !hmResult.isEmpty()){
            this.from = ParseUtil.checkNull(hmResult.get("from"));
            this.fromName = ParseUtil.checkNull(hmResult.get("from_name"));
            this.to = ParseUtil.checkNull(hmResult.get("to"));
            this.toName = ParseUtil.checkNull(hmResult.get("to_name"));
            this.message = ParseUtil.checkNull(hmResult.get("message"));
        }
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NotifyBean{");
        sb.append("from='").append(from).append('\'');
        sb.append(", fromName='").append(fromName).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", toName='").append(toName).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
