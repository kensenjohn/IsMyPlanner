package com.events.bean.users;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/9/14
 * Time: 7:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ForgotPasswordBean {

    private String forgotPasswordId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String secureTokenId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private boolean isUsable = false;

    public ForgotPasswordBean(){}
    public ForgotPasswordBean(HashMap<String,String> hmForgotPasswordRes){
        this.forgotPasswordId = ParseUtil.checkNull(hmForgotPasswordRes.get("SECURITYFORGOTINFOID"));
        this.userId = ParseUtil.checkNull(hmForgotPasswordRes.get("FK_USERID"));
        this.secureTokenId = ParseUtil.checkNull(hmForgotPasswordRes.get("SECURE_TOKEN_ID"));
        this.createDate = ParseUtil.sToL(hmForgotPasswordRes.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmForgotPasswordRes.get("HUMANCREATEDATE"));
        this.isUsable = ParseUtil.sTob(hmForgotPasswordRes.get("IS_USABLE"));
    }
    
    public String getForgotPasswordId() {
        return forgotPasswordId;
    }

    public void setForgotPasswordId(String forgotPasswordId) {
        this.forgotPasswordId = forgotPasswordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSecureTokenId() {
        return secureTokenId;
    }

    public void setSecureTokenId(String secureTokenId) {
        this.secureTokenId = secureTokenId;
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

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ForgotPasswordBean{");
        sb.append("forgotPasswordId='").append(forgotPasswordId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", secureTokenId='").append(secureTokenId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", isUsable=").append(isUsable);
        sb.append('}');
        return sb.toString();
    }
}
