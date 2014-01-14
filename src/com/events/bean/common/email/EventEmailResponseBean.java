package com.events.bean.common.email;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 6:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventEmailResponseBean {
    private EventEmailBean eventEmailBean = new EventEmailBean();

    public EventEmailBean getEventEmailBean() {
        return eventEmailBean;
    }

    public void setEventEmailBean(EventEmailBean eventEmailBean) {
        this.eventEmailBean = eventEmailBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventEmailResponseBean{");
        sb.append("eventEmailBean=").append(eventEmailBean);
        sb.append('}');
        return sb.toString();
    }
}
