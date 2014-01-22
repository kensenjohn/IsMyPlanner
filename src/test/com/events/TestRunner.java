package com.events;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/10/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestJavaTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
