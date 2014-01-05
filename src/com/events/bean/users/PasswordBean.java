package com.events.bean.users;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/15/13
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordBean {
    private String passwordId = Constants.EMPTY;
    private String hashedPassword = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate =  Constants.EMPTY;
    private String sPasswordStatus =  Constants.EMPTY;
    private Constants.PASSWORD_STATUS passwordStatus = Constants.PASSWORD_STATUS.ACTIVE;

    public PasswordBean() {

    }

    public PasswordBean(HashMap<String, String> hmPasswordRes) {
        this.passwordId = ParseUtil.checkNull(hmPasswordRes.get("PASSWORDID"));
        this.hashedPassword = ParseUtil.checkNull(hmPasswordRes.get("PASSWORD"));
        this.userId = ParseUtil.checkNull(hmPasswordRes.get("FK_USERID"));
        this.createDate = ParseUtil.sToL(hmPasswordRes.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmPasswordRes.get("HUMANCREATEDATE"));
        this.sPasswordStatus = ParseUtil.checkNull(hmPasswordRes.get("PASSWORD_STATUS"));

        if(Constants.PASSWORD_STATUS.ACTIVE.getStatus().equalsIgnoreCase(sPasswordStatus)) {
            this.passwordStatus = Constants.PASSWORD_STATUS.ACTIVE;
        } else if ( Constants.PASSWORD_STATUS.DELETED.getStatus().equalsIgnoreCase(sPasswordStatus) ) {
            this.passwordStatus = Constants.PASSWORD_STATUS.DELETED;
        }  else if ( Constants.PASSWORD_STATUS.OLD.getStatus().equalsIgnoreCase(sPasswordStatus) ) {
            this.passwordStatus = Constants.PASSWORD_STATUS.OLD;
        }
    }

    public String getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(String passwordId) {
        this.passwordId = passwordId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

    public Constants.PASSWORD_STATUS getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(Constants.PASSWORD_STATUS passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    @Override
    public String toString() {
        return "PasswordBean{" +
                "passwordId='" + passwordId + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", userId='" + userId + '\'' +
                ", createDate=" + createDate +
                ", humanCreateDate='" + humanCreateDate + '\'' +
                ", passwordStatus=" + passwordStatus +
                '}';
    }
}
