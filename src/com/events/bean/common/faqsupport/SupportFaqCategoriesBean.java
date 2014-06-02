package com.events.bean.common.faqsupport;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/24/14
 * Time: 6:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class SupportFaqCategoriesBean {
    // SUPPORTFAQCATEGORIESID VARCHAR(45) NOT NULL, CATEGORYNAME VARCHAR(500) NOT NULL , SEQUENCE INT(100) NOT NULL,  IS_SHOWN INT(1) NOT NULL DEFAULT 0
    private String supportFaqCategoryId = Constants.EMPTY;
    private String categoryName = Constants.EMPTY;
    private Long sequence = 0L;
    private boolean isShown = false;

    public SupportFaqCategoriesBean() {
    }

    public SupportFaqCategoriesBean(HashMap<String, String> hmResult) {
        if(hmResult!=null && !hmResult.isEmpty()) {
            this.supportFaqCategoryId =  ParseUtil.checkNull(hmResult.get("SUPPORTFAQCATEGORIESID"));
            this.categoryName =  ParseUtil.checkNull(hmResult.get("CATEGORYNAME"));
            this.sequence = ParseUtil.sToL(hmResult.get("SEQUENCE"));;
            isShown = ParseUtil.sTob( hmResult.get("IS_SHOWN") );;
        }
    }

    public String getSupportFaqCategoryId() {
        return supportFaqCategoryId;
    }

    public void setSupportFaqCategoryId(String supportFaqCategoryId) {
        this.supportFaqCategoryId = supportFaqCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SupportFaqCategoriesBean{");
        sb.append("supportFaqCategoryId='").append(supportFaqCategoryId).append('\'');
        sb.append(", categoryName='").append(categoryName).append('\'');
        sb.append(", sequence=").append(sequence);
        sb.append(", isShown=").append(isShown);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("support_faq_category_id", this.supportFaqCategoryId );
            jsonObject.put("category_name", this.categoryName );
            jsonObject.put("sequence", this.sequence );
            jsonObject.put("is_shown", this.isShown );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
