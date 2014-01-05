package com.events.common.exception.clients;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/17/13
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientException extends Exception {
    private String message = "";
    public ClientException() {
        super();
        this.message = "An exception occurred when working with Clients";
    }

    public ClientException(String message) {
        super(message);
        this.message = message;
    }

    public ClientException(Throwable cause) {
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
