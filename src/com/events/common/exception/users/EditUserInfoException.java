package com.events.common.exception.users;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditUserInfoException extends UserException {
    private String message = "";
    public EditUserInfoException() {
        super();
        this.message = "A User Info Data edit was not successful";
    }

    public EditUserInfoException(String message) {
        super(message);
        this.message = message;
    }

    public EditUserInfoException(Throwable cause) {
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