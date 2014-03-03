package com.events.bean.common;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/2/14
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentSiteEnabledBean {
    //GTPARENTSITEENABLED(PARENTSITEENABLEDID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL, IS_ALLOWED_ACCESS  INT(1) NOT NULL DEFAULT 0,
    private String parentSiteEnabledId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private boolean  isAllowed = false;

    public ParentSiteEnabledBean() { }
    public ParentSiteEnabledBean(HashMap<String, String> hmResult){
        this.parentSiteEnabledId = ParseUtil.checkNull(hmResult.get("PARENTSITEENABLEDID")) ;
        this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") ) ;
        this.isAllowed = ParseUtil.sTob(hmResult.get("IS_ALLOWED_ACCESS")) ;
    }

    public String getParentSiteEnabledId() {
        return parentSiteEnabledId;
    }

    public void setParentSiteEnabledId(String parentSiteEnabledId) {
        this.parentSiteEnabledId = parentSiteEnabledId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ParentSiteEnabledBean{");
        sb.append("parentSiteEnabledId='").append(parentSiteEnabledId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", isAllowed=").append(isAllowed);
        sb.append('}');
        return sb.toString();
    }


    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("parent_site_enabled_id", this.parentSiteEnabledId);
            jsonObject.put("user_id", this.userId);
            jsonObject.put("is_allowed_access", this.isAllowed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
