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
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChecklistTemplateBean {

    //GTCHECKLISTTEMPLATE( CHECKLISTTEMPLATEID VARCHAR(45) NOT NULL, NAME VARCHAR(500) NOT NULL, FK_VENDORID  VARCHAR(45) NOT NULL,
    // IS_DEFAULT INT(1) NOT NULL DEFAULT 0 , CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    private String checklistTemplateId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private boolean isDefault = false;

    public ChecklistTemplateBean() {
    }

    public ChecklistTemplateBean(HashMap<String,String> hmResult) {
        this.checklistTemplateId = ParseUtil.checkNull( hmResult.get("CHECKLISTTEMPLATEID"));
        this.name = ParseUtil.checkNull( hmResult.get("NAME"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull( hmResult.get("HUMANCREATEDATE"));
        this.vendorId = ParseUtil.checkNull( hmResult.get("FK_VENDORID"));
        isDefault = ParseUtil.sTob(hmResult.get("IS_DEFAULT"));
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
        final StringBuilder sb = new StringBuilder("ChecklistTemplateBean{");
        sb.append("checklistTemplateId='").append(checklistTemplateId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", isDefault=").append(isDefault);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("checklist_template_id", this.checklistTemplateId );
            jsonObject.put("name", this.name );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("is_default", this.isDefault );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
