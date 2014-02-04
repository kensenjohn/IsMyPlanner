package com.events.common.service;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduledServiceExecutions implements ServletContextListener {
    Configuration configSchedulerProc = Configuration.getInstance(Constants.SCHEDULER_PROCESS_PROP);
    private static final Logger schedulerLogging = LoggerFactory.getLogger(Constants.SCHEDULER_LOGS);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);


    private ScheduledExecutorService emailSenderThread;
    private ScheduledExecutorService emailCreatorThread;
    private ScheduledExecutorService imapReaderThread;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        // Sending Emails to Recepients from the Email Queue
        emailSenderThread = Executors.newSingleThreadScheduledExecutor();
        emailSenderThread.scheduleWithFixedDelay(new EmailSenderThread(),
                ParseUtil.sToL(configSchedulerProc.get(Constants.SCHEDULER.SEND_EMAIL_STARTUP_DELAY.getPropName(), "400")),
                ParseUtil.sToL(configSchedulerProc.get(Constants.SCHEDULER.SEND_EMAIL_DELAY_BETWEEN_CALL.getPropName() , "700" )),
                TimeUnit.SECONDS);
        schedulerLogging.info("emailSender Scheduler started : startup context");


        // Creating EmailObject to be inserted into the queue - from a Schedule that
        emailCreatorThread = Executors.newSingleThreadScheduledExecutor();
        emailCreatorThread.scheduleWithFixedDelay(new EmailCreatorThread(),
                ParseUtil.sToL(configSchedulerProc.get( Constants.SCHEDULER.CREATE_EMAIL_STARTUP_DELAY.getPropName(), "300" ) ),
                ParseUtil.sToL(configSchedulerProc.get( Constants.SCHEDULER.CREATE_EMAIL_DELAY_BETWEEN_CALL.getPropName() , "600" ) ),
                TimeUnit.SECONDS  );
        schedulerLogging.info("emailCreator Scheduler started : startup context");


        /*imapReaderThread = Executors.newSingleThreadScheduledExecutor();
        imapReaderThread.scheduleWithFixedDelay(new IMAPThread(),
                ParseUtil.sToL(configSchedulerProc.get( Constants.SCHEDULER.CREATE_EMAIL_STARTUP_DELAY.getPropName(), "10" ) ),
                ParseUtil.sToL(configSchedulerProc.get( Constants.SCHEDULER.CREATE_EMAIL_DELAY_BETWEEN_CALL.getPropName() , "600" ) ),
                TimeUnit.SECONDS  );
        schedulerLogging.info("imap Reader started : startup context");*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (emailSenderThread != null) {
            emailSenderThread.shutdownNow();
            schedulerLogging.info("emailSenderThread  : shut down ");
        }

        if(emailCreatorThread!=null) {
            emailCreatorThread.shutdownNow();
            schedulerLogging.info("emailCreatorThread  : shut down ");
        }

        /*if(imapReaderThread!=null) {
            imapReaderThread.shutdownNow();
            schedulerLogging.info("imapReaderThread  : shut down ");
        }*/
    }
}
