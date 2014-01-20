package com.events.common.email.send;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.events.bean.common.email.EmailAddress;
import com.events.bean.common.email.EmailObject;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 8:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonEmailSend implements MailSender {
    private Logger emailerLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);
    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

    private final String AMAZON_ACCESS_KEY = applicationConfig.get(Constants.AMAZON.ACCESS_KEY.getPropName());
    private final String AMAZON_ACCESS_SECRET = applicationConfig.get(Constants.AMAZON.SECRET_KEY.getPropName());

    public AmazonEmailSend() {
    }

    @Override
    public boolean send(EmailObject emailObject) {
        boolean isSuccess = true;
        if (emailObject != null) {
            emailerLogging.info("Send invoked ");
            emailerLogging.debug("The email Object to send : " + emailObject);
            SendEmailRequest emailSendRequest = new SendEmailRequest();
            emailSendRequest.withSource(emailObject.getFromAddress());

            emailSendRequest.setDestination( createDestinationEmailAddress(emailObject) );

            Content subjContent = new Content().withData(emailObject.getEmailSubject());
            Message msg = new Message().withSubject(subjContent);

            // Include a body in both text and HTML formats
            Content textContent = new Content().withData(emailObject.getTextBody());
            Content htmlContent = new Content().withData(emailObject.getHtmlBody());
            Body body = new Body().withHtml(htmlContent).withText(textContent);
            msg.setBody(body);

            emailSendRequest.setMessage(msg);

            // Set AWS access credentials
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(new BasicAWSCredentials(AMAZON_ACCESS_KEY,AMAZON_ACCESS_SECRET));

            // Call Amazon SES to send the message
            try {
                SendEmailResult sendEmailResult = client.sendEmail( emailSendRequest );
                emailerLogging.info("After send result : " + sendEmailResult.getMessageId() + " - " + sendEmailResult);
            } catch (AmazonClientException e) {
                isSuccess = false;
                emailerLogging.error("AmazonClientException "+ emailObject.getToAddress() + " " + emailObject.getFromAddress() + " "
                        + emailObject.getEmailSubject() + "\n" + ExceptionHandler.getStackTrace(e));
            } catch (Exception e) {
                isSuccess = false;
                emailerLogging.error("Exception " + emailObject.getToAddress() + " " + emailObject.getEmailSubject() + "\n"
                        + ExceptionHandler.getStackTrace(e));
            }
        }  else {
            emailerLogging.error("Email Object was null ");
        }
        return isSuccess;
    }

    private Destination createDestinationEmailAddress(EmailObject emailObject) {
        Destination destinationEmailAddress = new Destination();

        if(emailObject!=null) {
            destinationEmailAddress.withToAddresses(getEmailAddress(emailObject.getToAddress()));
            if(!Utility.isNullOrEmpty(emailObject.getCcAddress())) {
                destinationEmailAddress.withCcAddresses(getEmailAddress(emailObject.getCcAddress()));
            }
            if(!Utility.isNullOrEmpty(emailObject.getBccAddress())) {
                destinationEmailAddress.withBccAddresses(getEmailAddress(emailObject.getBccAddress()));
            }
        }

        return destinationEmailAddress;
    }

    protected ArrayList<String> getEmailAddress(String email) {
        ArrayList<String> arrEmails = new ArrayList<String>();
        if(!Utility.isNullOrEmpty(email)) {
                arrEmails.add(email);
        }
        return arrEmails;
    }
}
