package com.events.common.email.imap;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.exception.ExceptionHandler;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.SortTerm;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/3/14
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class GmailReader implements ImapMailReader{
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    @Override
    public void read() {
        appLogging.info("Reading started.");
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "kjohn@smarasoft.com", "Foreversmarasoftisfun@6d7");
            appLogging.info("Connect to GMail complete");

            IMAPFolder imapInboxFolder = (IMAPFolder) store.getFolder("inbox");
            if(imapInboxFolder!=null ) {
                appLogging.info("Default Folder : " + imapInboxFolder.getFullName() );

                if(!imapInboxFolder.isOpen()) {
                    imapInboxFolder.open(Folder.READ_WRITE);
                }


                javax.mail.Message[] defaultFolderMessages = imapInboxFolder.getMessages();


                for( Message message : defaultFolderMessages ) {
                    appLogging.info(message.getSubject() + " - " +  imapInboxFolder.getUID( message ) + " - " +message.getMessageNumber() );
                    appLogging.info(ParseUtil.checkNullObject(message.getContent()));

                    /*Address[]fromAddress= message.getFrom();
                    if(fromAddress!=null && fromAddress.length > 0) {
                        for(Address address : fromAddress )  {
                            appLogging.info(address.getType() + " : " + address.toString() );
                        }
                    }

                    Address[]allRecipients= message.getAllRecipients();
                    if(allRecipients!=null && allRecipients.length > 0) {
                        for(Address recepientAddress : allRecipients )  {
                            appLogging.info(recepientAddress.getType() + " : " + recepientAddress.toString() );
                        }
                    }*/

                }
            }

        } catch (javax.mail.NoSuchProviderException e) {
            e.printStackTrace();
            appLogging.error(ExceptionHandler.getStackTrace(e));
        } catch( javax.mail.MessagingException e) {
            e.printStackTrace();
            appLogging.error(ExceptionHandler.getStackTrace(e));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            appLogging.error(ExceptionHandler.getStackTrace(e));
        }
    }
}
