package com.events.bean.event.budget;

import com.events.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/17/14
 * Time: 5:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventBudgetResponseBean {
    private EventBudgetBean eventBudgetBean = new EventBudgetBean();
    private String eventBudgetId = Constants.EMPTY;
    private EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
    private HashMap<String,ArrayList<EventBudgetCategoryItemBean>> hmEventBudgetCategoryItemBean = new HashMap<String, ArrayList<EventBudgetCategoryItemBean>>();
    private ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean= new ArrayList<EventBudgetCategoryBean>();


    public HashMap<String, ArrayList<EventBudgetCategoryItemBean>> getHmEventBudgetCategoryItemBean() {
        return hmEventBudgetCategoryItemBean;
    }

    public void setHmEventBudgetCategoryItemBean(HashMap<String, ArrayList<EventBudgetCategoryItemBean>> hmEventBudgetCategoryItemBean) {
        this.hmEventBudgetCategoryItemBean = hmEventBudgetCategoryItemBean;
    }

    public ArrayList<EventBudgetCategoryBean> getArrEventBudgetCategoryBean() {
        return arrEventBudgetCategoryBean;
    }

    public void setArrEventBudgetCategoryBean(ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean) {
        this.arrEventBudgetCategoryBean = arrEventBudgetCategoryBean;
    }

    public EventBudgetCategoryBean getEventBudgetCategoryBean() {
        return eventBudgetCategoryBean;
    }

    public void setEventBudgetCategoryBean(EventBudgetCategoryBean eventBudgetCategoryBean) {
        this.eventBudgetCategoryBean = eventBudgetCategoryBean;
    }

    public EventBudgetBean getEventBudgetBean() {
        return eventBudgetBean;
    }

    public void setEventBudgetBean(EventBudgetBean eventBudgetBean) {
        this.eventBudgetBean = eventBudgetBean;
    }

    public String getEventBudgetId() {
        return eventBudgetId;
    }

    public void setEventBudgetId(String eventBudgetId) {
        this.eventBudgetId = eventBudgetId;
    }


}
