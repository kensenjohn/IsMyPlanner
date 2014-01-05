package com.events.common.exception.users;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserException extends Exception {
    private String message = "";
    public UserException() {
        super();
        this.message = "An exception occurred when working with User";
    }

    public UserException(String message) {
        super(message);
        this.message = message;
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
