package com.events.bean.users;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/8/14
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CookieRequestBean {
    private String cookieUserId = Constants.EMPTY;
    private String userId = Constants.EMPTY;

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
}
