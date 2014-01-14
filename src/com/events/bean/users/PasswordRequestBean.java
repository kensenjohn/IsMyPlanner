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
    private String emailAddress = Constants.EMPTY;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PasswordRequestBean{");
        sb.append("emailAddress='").append(emailAddress).append('\'');
        sb.append(", passwordId='").append(passwordId).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", hashedPassword='").append(hashedPassword).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", passwordStatus=").append(passwordStatus);
        sb.append('}');
        return sb.toString();
    }
}
