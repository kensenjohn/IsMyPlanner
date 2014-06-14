package com.events.bean.dashboard.checklist;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChecklistTemplateTodoBean  {
// GTCHECKLISTTEMPLATETODO( CHECKLISTTEMPLATETODOID VARCHAR(45) NOT NULL, FK_CHECKLISTTEMPLATEITEMID VARCHAR(45) NOT NULL,
// FK_CHECKLISTTEMPLATEID VARCHAR(45) NOT NULL, NAME VARCHAR(500) NOT NULL, CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45)
    private String checklistTemplateTodoId = Constants.EMPTY;
    private String checklistTemplateItemId = Constants.EMPTY;
    private String checklistTemplateId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public ChecklistTemplateTodoBean() {
    }

    public ChecklistTemplateTodoBean(HashMap<String,String> hmResult) {
        this.checklistTemplateTodoId = ParseUtil.checkNull(hmResult.get("CHECKLISTTEMPLATETODOID"));
        this.checklistTemplateItemId = ParseUtil.checkNull( hmResult.get("FK_CHECKLISTTEMPLATEITEMID"));
        this.checklistTemplateId = ParseUtil.checkNull( hmResult.get("FK_CHECKLISTTEMPLATEID"));
        this.name = ParseUtil.checkNull( hmResult.get("NAME"));
        this.createDate = ParseUtil.sToL( hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull( hmResult.get("HUMANCREATEDATE"));
    }

    public String getChecklistTemplateTodoId() {
        return checklistTemplateTodoId;
    }

    public void setChecklistTemplateTodoId(String checklistTemplateTodoId) {
        this.checklistTemplateTodoId = checklistTemplateTodoId;
    }

    public String getChecklistTemplateItemId() {
        return checklistTemplateItemId;
    }

    public void setChecklistTemplateItemId(String checklistTemplateItemId) {
        this.checklistTemplateItemId = checklistTemplateItemId;
    }

    public String getChecklistTemplateId() {
        return checklistTemplateId;
    }

    public void setChecklistTemplateId(String checklistTemplateId) {
        this.checklistTemplateId = checklistTemplateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChecklistTemplateTodoBean{");
        sb.append("checklistTemplateTodoId='").append(checklistTemplateTodoId).append('\'');
        sb.append(", checklistTemplateItemId='").append(checklistTemplateItemId).append('\'');
        sb.append(", checklistTemplateId='").append(checklistTemplateId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("checklist_template_todo_id", this.checklistTemplateTodoId );
            jsonObject.put("checklist_template_item_id", this.checklistTemplateItemId );
            jsonObject.put("checklist_template_id", this.checklistTemplateId );
            jsonObject.put("name", this.name );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
