package com.events.common.exception;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyFileException extends Exception {
    private static final long serialVersionUID = -5588421068077476571L;
    private String message = "";
    public PropertyFileException() {
        super();
        this.message = "Something went wrong while processing property file located at " + Constants.PROP_FILE_PATH;
    }

    public PropertyFileException(String message) {
        super(message);
        this.message = message;
    }

    public PropertyFileException(Throwable cause) {
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
