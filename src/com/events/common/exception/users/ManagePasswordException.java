package com.events.common.exception.users;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 11:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManagePasswordException  extends UserException {
    private String message = "";
    public ManagePasswordException() {
        super();
        this.message = "A User edit was not successful";
    }

    public ManagePasswordException(String message) {
        super(message);
        this.message = message;
    }

    public ManagePasswordException(Throwable cause) {
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
