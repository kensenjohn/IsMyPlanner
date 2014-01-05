package com.events.bean.users;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordRequestBean {
    private String passwordId = Constants.EMPTY;
    private String password = Constants.EMPTY;
    private String hashedPassword = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Constants.PASSWORD_STATUS passwordStatus = Constants.PASSWORD_STATUS.ACTIVE;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(String passwordId) {
        this.passwordId = passwordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Constants.PASSWORD_STATUS getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(Constants.PASSWORD_STATUS passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    @Override
    public String toString() {
        return "PasswordRequestBean{" +
                "password=xxxxxxxx'"  + '\'' +
                ", hashedPassword=xxxxxxxx'"  + '\'' +
                ", userid='" + userId + '\'' +
                '}';
    }
}
