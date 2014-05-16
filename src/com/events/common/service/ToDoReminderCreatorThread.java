package com.events.common.service;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.EmailCreatorBaseService;
import com.events.common.email.creator.MailCreator;
import com.events.common.exception.ExceptionHandler;
import com.events.common.todo.ToDoReminderCreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/12/14
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToDoReminderCreatorThread implements Runnable {
    private static final Logger schedulerLogging = LoggerFactory.getLogger(Constants.SCHEDULER_LOGS);

    @Override
    public void run() {

        try {
            ToDoReminderCreatorService toDoReminderCreatorService = new ToDoReminderCreatorService() ;
            toDoReminderCreatorService.invokeToDoReminderSender();
        } catch (Exception e) {
            schedulerLogging.error("Error while invoking the ToDoReminderCreatorThread service "
                    + ExceptionHandler.getStackTrace(e));
        }
    }
}
