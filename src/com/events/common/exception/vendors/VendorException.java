package com.events.common.exception.vendors;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/20/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorException extends Exception{
    private String message = Constants.EMPTY;
    public VendorException() {
        super();
        this.message = "An exception occurred when working with Vendors";
    }

    public VendorException(String message) {
        super(message);
        this.message = message;
    }

    public VendorException(Throwable cause) {
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
