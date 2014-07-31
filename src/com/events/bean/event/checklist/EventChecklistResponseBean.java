package com.events.bean.event.checklist;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 7/1/14
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventChecklistResponseBean {

    private EventChecklistBean eventChecklistBean = new EventChecklistBean();
    private HashMap<String, ArrayList<EventChecklistTodoBean> > hmEventChecklistTodoBean = new HashMap<String, ArrayList<EventChecklistTodoBean>>();
    private HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean = new HashMap<Long, EventChecklistItemBean>();


    public HashMap<String, ArrayList<EventChecklistTodoBean>> getHmEventChecklistTodoBean() {
        return hmEventChecklistTodoBean;
    }

    public void setHmEventChecklistTodoBean(HashMap<String, ArrayList<EventChecklistTodoBean>> hmEventChecklistTodoBean) {
        this.hmEventChecklistTodoBean = hmEventChecklistTodoBean;
    }

    public HashMap<Long, EventChecklistItemBean> getHmEventChecklistItemBean() {
        return hmEventChecklistItemBean;
    }

    public void setHmEventChecklistItemBean(HashMap<Long, EventChecklistItemBean> hmEventChecklistItemBean) {
        this.hmEventChecklistItemBean = hmEventChecklistItemBean;
    }

    public EventChecklistBean getEventChecklistBean() {
        return eventChecklistBean;
    }

    public void setEventChecklistBean(EventChecklistBean eventChecklistBean) {
        this.eventChecklistBean = eventChecklistBean;
    }
}
