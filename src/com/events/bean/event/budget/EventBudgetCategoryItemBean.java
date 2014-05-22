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
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventBudgetCategoryItemBean {
    private String budgetCategoryItemId = Constants.EMPTY;
    private String budgetCategoryId = Constants.EMPTY;
    private String eventBudgetId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String itemName = Constants.EMPTY;
    private Double estimatedAmount = 0.0D;
    private Double actualAmount = 0.0D;
    private boolean isPaid = false;
    private String userId = Constants.EMPTY;
    private Long modifiedDate = 0L;
    private String humanModifiedDate = Constants.EMPTY;
    // CREATE TABLE GTEVENTBUDGETCATEGORYITEM( BUDGETCATEGORYITEMID VARCHAR(45) NOT NULL, FK_BUDGETCATEGORYID VARCHAR(45) NOT NULL,
    // FK_EVENTBUDGETID VARCHAR(45) NOT NULL,FK_EVENTID VARCHAR(45) NOT NULL,  ITEMNAME TEXT NOT NULL, ESTIMATE_AMOUNT VARCHAR(45),
    // ACTUAL_AMOUNT VARCHAR(45), IS_PAID INT(1) NOT NULL DEFAULT 0, MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),
    // FK_USERID VARCHAR(45), PRIMARY KEY (BUDGETCATEGORYITEMID) ) ENGINE = MyISAM DEFAULT CHARSET = utf8


    public EventBudgetCategoryItemBean() {
    }

    public EventBudgetCategoryItemBean( HashMap<String,String> hmResult) {
        this.budgetCategoryItemId = ParseUtil.checkNull(hmResult.get("BUDGETCATEGORYITEMID"));
        this.budgetCategoryId = ParseUtil.checkNull(hmResult.get("FK_BUDGETCATEGORYID"));
        this.eventBudgetId = ParseUtil.checkNull(hmResult.get("FK_EVENTBUDGETID"));
        this.eventId = ParseUtil.checkNull(hmResult.get("FK_EVENTID"));
        this.itemName = ParseUtil.checkNull(hmResult.get("ITEMNAME"));
        this.estimatedAmount = ParseUtil.sToD(hmResult.get("ESTIMATE_AMOUNT"));
        this.actualAmount = ParseUtil.sToD(hmResult.get("ACTUAL_AMOUNT"));
        this.isPaid = ParseUtil.sTob(hmResult.get("IS_PAID"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getHumanModifiedDate() {
        return humanModifiedDate;
    }

    public void setHumanModifiedDate(String humanModifiedDate) {
        this.humanModifiedDate = humanModifiedDate;
    }

    public String getBudgetCategoryItemId() {
        return budgetCategoryItemId;
    }

    public void setBudgetCategoryItemId(String budgetCategoryItemId) {
        this.budgetCategoryItemId = budgetCategoryItemId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventBudgetCategoryItemBean{");
        sb.append("budgetCategoryItemId='").append(budgetCategoryItemId).append('\'');
        sb.append(", budgetCategoryId='").append(budgetCategoryId).append('\'');
        sb.append(", eventBudgetId='").append(eventBudgetId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", estimatedAmount=").append(estimatedAmount);
        sb.append(", actualAmount=").append(actualAmount);
        sb.append(", isPaid=").append(isPaid);
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("budget_category_item_id", this.budgetCategoryItemId );
            jsonObject.put("budget_category_id", this.budgetCategoryId );
            jsonObject.put("event_budget_id", this.eventBudgetId );
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("item_name", this.itemName );
            jsonObject.put("estimated_amount", this.estimatedAmount );
            jsonObject.put("actual_amount", this.actualAmount );
            jsonObject.put("is_paid", this.isPaid );
            jsonObject.put("user_id", this.userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
