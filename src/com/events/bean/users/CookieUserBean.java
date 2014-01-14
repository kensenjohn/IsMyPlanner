package com.events.bean.users;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/8/14
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CookieUserBean {
    private String cookieUserId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public CookieUserBean(){ }
    public CookieUserBean(HashMap<String,String> hmResult){
        this.cookieUserId = ParseUtil.checkNull( hmResult.get("COOKIEUSERID") );
        this.userId = ParseUtil.checkNull( hmResult.get("FK_USERID") );
        this.createDate = ParseUtil.sToL( hmResult.get("CREATEDATE") );
        this.humanCreateDate = ParseUtil.checkNull( hmResult.get("HUMANCREATEDATE") );
    }

    public String getCookieUserId() {
        return cookieUserId;
    }

    public void setCookieUserId(String cookieUserId) {
        this.cookieUserId = cookieUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        final StringBuilder sb = new StringBuilder("CookieUserBean{");
        sb.append("cookieUserId='").append(cookieUserId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cookieuser_id", this.cookieUserId);
            jsonObject.put("user_id", this.userId);
            jsonObject.put("create_date", this.createDate);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}
