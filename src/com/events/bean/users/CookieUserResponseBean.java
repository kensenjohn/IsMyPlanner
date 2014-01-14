package com.events.bean.users;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/8/14
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CookieUserResponseBean {
    private String cookieUserId = Constants.EMPTY;
    private CookieUserBean cookieUserBean = new CookieUserBean();

    public CookieUserBean getCookieUserBean() {
        return cookieUserBean;
    }

    public void setCookieUserBean(CookieUserBean cookieUserBean) {
        this.cookieUserBean = cookieUserBean;
    }

    public String getCookieUserId() {
        return cookieUserId;
    }

    public void setCookieUserId(String cookieUserId) {
        this.cookieUserId = cookieUserId;
    }
}
