package com.events.bean.event.budget;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/17/14
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventBudgetCategoryBean {


    private String budgetCategoryId = Constants.EMPTY;
    private String eventBudgetId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String categoryName = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Double estimatedCost = 0.00D;
    private Double actualCost = 0.00D;

    public EventBudgetCategoryBean() {
    }

    public EventBudgetCategoryBean(HashMap<String, String> hmResult) {
        this.budgetCategoryId = ParseUtil.checkNull(hmResult.get("BUDGETCATEGORYID"));
        this.eventBudgetId = ParseUtil.checkNull(hmResult.get("FK_EVENTBUDGETID"));
        this.eventId = ParseUtil.checkNull(hmResult.get("FK_EVENTID"));
        this.categoryName = ParseUtil.checkNull(hmResult.get("CATEGORYNAME"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Double getActualCost() {
        return actualCost;
    }

    public void setActualCost(Double actualCost) {
        this.actualCost = actualCost;
    }

    public String getBudgetCategoryId() {
        return budgetCategoryId;
    }

    public void setBudgetCategoryId(String budgetCategoryId) {
        this.budgetCategoryId = budgetCategoryId;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventBudgetCategoryBean{");
        sb.append("budgetCategoryId='").append(budgetCategoryId).append('\'');
        sb.append(", eventBudgetId='").append(eventBudgetId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", categoryName='").append(categoryName).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("budget_category_id", this.budgetCategoryId );
            jsonObject.put("event_budget_id", this.eventBudgetId );
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("category_name", this.categoryName );
            jsonObject.put("estimated_cost", this.estimatedCost );
            jsonObject.put("actual_cost", this.actualCost );
            jsonObject.put("user_id", this.userId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
