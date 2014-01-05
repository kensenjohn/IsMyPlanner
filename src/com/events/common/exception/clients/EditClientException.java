package com.events.common.exception.clients;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/17/13
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditClientException extends ClientException {
    private String message = "";
    public EditClientException() {
        super();
        this.message = "An exception occurred when trying to edit a Clients";
    }

    public EditClientException(String message) {
        super(message);
        this.message = message;
    }

    public EditClientException(Throwable cause) {
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
