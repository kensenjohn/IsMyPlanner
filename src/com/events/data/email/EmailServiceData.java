package com.events.data.email;

import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.common.*;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/7/14
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class EmailServiceData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static final Logger emailLogging = LoggerFactory.getLogger(Constants.EMAILER_LOGS);


    private final static String queryEmailsFromQueue = "SELECT * FROM GTEMAILQUEUE WHERE STATUS = ?";
    private final static String queryUpdateEmailStatus = "UPDATE GTEMAILQUEUE SET STATUS = ?, MODIFIEDDATE = ?, HUMANMODIFYDATE = ?  WHERE EMAILQUEUEID = ?";
    private final static String queryInsertEmailQueue = "insert into GTEMAILQUEUE (EMAILQUEUEID,FROM_ADDRESS,FROM_ADDRESS_NAME,    " +
            " TO_ADDRESS,TO_ADDRESS_NAME,CC_ADDRESS,    CC_ADDRESSNAME,BCC_ADDRESS,BCC_ADDRESSNAME,     EMAIL_SUBJECT, HTML_BODY,TEXT_BODY," +
            " STATUS,CREATEDATE,MODIFIEDDATE,       HUMANCREATEDATE,HUMANMODIFYDATE ) "
            + " VALUES (?,?,?,    ?,?,?,    ?,?,?,    ?,?,?,    ?,?,?, ?,? )";

    private final static String queryEmailsTemplateById = "SELECT * FROM GTEMAILTEMPLATE WHERE EMAILTEMPLATEID = ?";
    private final static String queryEmailsTemplateByName = "SELECT * FROM GTEMAILTEMPLATE WHERE EMAILTEMPLATENAME = ?";

    public ArrayList<EmailQueueBean> getEmailsFromQueue( Constants.EMAIL_STATUS emailStatus) {
        ArrayList<EmailQueueBean> arrEmailQueueBean = new ArrayList<EmailQueueBean>();
        if (emailStatus != null) {
            ArrayList<Object> aParams = DBDAO.createConstraint(emailStatus.getStatus());

            ArrayList<HashMap<String, String>> arrHmEmailQueue = DBDAO.getDBData(EVENTADMIN_DB, queryEmailsFromQueue, aParams, false,"EmailServiceData.java", "getEmailsFromQueue()");

            if (arrHmEmailQueue != null && !arrHmEmailQueue.isEmpty()) {
                for (HashMap<String, String> hmEmailQueue : arrHmEmailQueue) {
                    EmailQueueBean emailQueueBean = new EmailQueueBean(
                            hmEmailQueue);
                    arrEmailQueueBean.add(emailQueueBean);

                }
            }
        }
        return arrEmailQueueBean;
    }

    public Integer updateEmailStatus(EmailQueueBean emailQueueBean, Constants.EMAIL_STATUS newEmailStatus) {
        Integer iNumOfRows = 0;
        if (emailQueueBean != null && !Utility.isNullOrEmpty(emailQueueBean.getEmailQueueId())  && newEmailStatus != null ) {

            ArrayList<Object> aParams = DBDAO.createConstraint( newEmailStatus.getStatus(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                    emailQueueBean.getEmailQueueId());

            iNumOfRows = DBDAO.putRowsQuery(queryUpdateEmailStatus, aParams,EVENTADMIN_DB, "EmailServiceData.java", "updateEmailStatus()");
        } else {
            emailLogging.debug("Email Queue bean is null of empty " + ParseUtil.checkNullObject(emailQueueBean));
        }

        return iNumOfRows;
    }


    public Integer insertEmailQueue(EmailQueueBean emailQueueBean) {
        Integer iNumOfRows = 0;
        if (emailQueueBean != null && emailQueueBean.getEmailQueueId() != null
                && !"".equalsIgnoreCase(emailQueueBean.getEmailQueueId())) {

            Long lCurrentDate = DateSupport.getEpochMillis();
            String sHumanCurrentDate = DateSupport.getUTCDateTime();

            ArrayList<Object> aParams = DBDAO.createConstraint(
                    emailQueueBean.getEmailQueueId(),
                    emailQueueBean.getFromAddress(),
                    emailQueueBean.getFromAddressName(),
                    emailQueueBean.getToAddress(),
                    emailQueueBean.getToAddressName(),
                    emailQueueBean.getCcAddress(),
                    emailQueueBean.getCcAddressName(),
                    emailQueueBean.getBccAddress(),
                    emailQueueBean.getBccAddressName(),
                    emailQueueBean.getEmailSubject(),
                    emailQueueBean.getHtmlBody(), emailQueueBean.getTextBody(),
                    emailQueueBean.getStatus(), lCurrentDate, lCurrentDate,
                    sHumanCurrentDate, sHumanCurrentDate);

            iNumOfRows = DBDAO.putRowsQuery(queryInsertEmailQueue, aParams,
                    EVENTADMIN_DB, "EmailServiceData.java", "insertEmailQueue()");
        } else {
            appLogging.error("EmailQueueBean is null or has no ID : " + ParseUtil.checkNullObject(emailQueueBean));
            emailLogging.error("EmailQueueBean is null or has no ID : " +  ParseUtil.checkNullObject(emailQueueBean));
        }
        return iNumOfRows;
    }

    public EmailTemplateBean getEmailTemplate( Constants.EMAIL_TEMPLATE emailTemplate ) {
        EmailTemplateBean emailTemplateBean = new EmailTemplateBean();
        if (emailTemplate != null) {
            ArrayList<Object> aParams = DBDAO.createConstraint(emailTemplate.getEmailTemplate());

            ArrayList<HashMap<String, String>> arrHmEmailTemplate = DBDAO .getDBData(EVENTADMIN_DB, queryEmailsTemplateByName, aParams, false,
                            "MailingServiceData.java", "getEmailTemplate()");
            if (arrHmEmailTemplate != null && !arrHmEmailTemplate.isEmpty()) {
                for (HashMap<String, String> hmEmailTemplate : arrHmEmailTemplate) {
                    emailTemplateBean = new EmailTemplateBean(hmEmailTemplate);
                }
            }
        }
        return emailTemplateBean;
    }
    public EmailTemplateBean getEmailTemplateById( String sTemplateId  ) {
        EmailTemplateBean emailTemplateBean = new EmailTemplateBean();
        if( !Utility.isNullOrEmpty(sTemplateId) ) {
            ArrayList<Object> aParams = DBDAO.createConstraint( sTemplateId );
            ArrayList<HashMap<String, String>> arrHmEmailTemplate = DBDAO.getDBData( EVENTADMIN_DB, queryEmailsTemplateById, aParams, false,
                    "EmailServiceData.java", "getEmailTemplateById()");
            if (arrHmEmailTemplate != null && !arrHmEmailTemplate.isEmpty()) {
                for (HashMap<String, String> hmEmailTemplate : arrHmEmailTemplate) {
                    emailTemplateBean = new EmailTemplateBean(hmEmailTemplate);
                }
            }

        }
        return emailTemplateBean;
    }
}
