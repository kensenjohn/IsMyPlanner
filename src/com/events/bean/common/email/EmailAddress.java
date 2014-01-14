package com.events.bean.common.email;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class EmailAddress {
    private String emailAddress = Constants.EMPTY;
    private String name = Constants.EMPTY;

    public EmailAddress(){}
    public EmailAddress(String emailAddress, String name){
        this.emailAddress = emailAddress;
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
