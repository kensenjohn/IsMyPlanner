package com.events.bean.event.budget;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/16/14
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventBudgetRequestBean {

    public EventBudgetRequestBean() {
    }

    public EventBudgetRequestBean(  String eventBudgetId, String budgetCategoryId,String eventId, String categoryName, String userId ) {
        this.eventBudgetId = eventBudgetId;
        this.eventId = eventId;
        this.userId = userId;
        this.budgetCategoryId = budgetCategoryId;
        this.categoryName = categoryName;
    }

    private String eventBudgetId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private Double estimatedBudget = 0.0;
    private String humanModifiedDate = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String budgetCategoryId = Constants.EMPTY;
    private String categoryName = Constants.EMPTY;
    private EventBudgetCategoryItemBean eventBudgetCategoryItemBean = new EventBudgetCategoryItemBean();
    private ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBeans = new ArrayList<EventBudgetCategoryItemBean>();
    private ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean = new ArrayList<EventBudgetCategoryBean>();


    public ArrayList<EventBudgetCategoryBean> getArrEventBudgetCategoryBean() {
        return arrEventBudgetCategoryBean;
    }

    public void setArrEventBudgetCategoryBean(ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean) {
        this.arrEventBudgetCategoryBean = arrEventBudgetCategoryBean;
    }

    public ArrayList<EventBudgetCategoryItemBean> getArrEventBudgetCategoryItemBeans() {
        return arrEventBudgetCategoryItemBeans;
    }

    public void setArrEventBudgetCategoryItemBeans(ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBeans) {
        this.arrEventBudgetCategoryItemBeans = arrEventBudgetCategoryItemBeans;
    }

    public EventBudgetCategoryItemBean getEventBudgetCategoryItemBean() {
        return eventBudgetCategoryItemBean;
    }

    public void setEventBudgetCategoryItemBean(EventBudgetCategoryItemBean eventBudgetCategoryItemBean) {
        this.eventBudgetCategoryItemBean = eventBudgetCategoryItemBean;
    }

    public String getBudgetCategoryId() {
        return budgetCategoryId;
    }

    public void setBudgetCategoryId(String budgetCategoryId) {
        this.budgetCategoryId = budgetCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(Double estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    public String getEventBudgetId() {
        return eventBudgetId;
    }

    public void setEventBudgetId(String eventBudgetId) {
        this.eventBudgetId = eventBudgetId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getHumanModifiedDate() {
        return humanModifiedDate;
    }

    public void setHumanModifiedDate(String humanModifiedDate) {
        this.humanModifiedDate = humanModifiedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
