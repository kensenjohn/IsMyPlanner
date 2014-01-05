package com.events.bean.users;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/20/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserTypeBean {
    private String userTypeId = Constants.EMPTY;
    private String type = Constants.EMPTY;

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
