package com.events.common.exception.vendors;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/20/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditVendorException extends VendorException {
    private String message = Constants.EMPTY;
    public EditVendorException() {
        super();
        this.message = "An exception occurred when editing Vendors";
    }

    public EditVendorException(String message) {
        super(message);
        this.message = message;
    }

    public EditVendorException(Throwable cause) {
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
