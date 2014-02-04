package com.events.common.service;

import com.events.common.Constants;
import com.events.common.email.imap.GmailReader;
import com.events.common.email.imap.ImapMailReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/3/14
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class IMAPThread implements Runnable {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    @Override
    public void run() {
        appLogging.info("GMAIL Readerthread invoked.");
        ImapMailReader imapMailReader = new GmailReader();
        imapMailReader.read();
        appLogging.info("GMAIL Readerthread complete.");

    }
}
