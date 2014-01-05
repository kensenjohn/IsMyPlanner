package com.events.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionHandler {
    public static String getStackTrace(Throwable exception) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        exception.printStackTrace(printWriter);
        return exception.getMessage() + "\n" + result.toString();
    }
}
