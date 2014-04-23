package com.events.common.invoice;

import com.events.bean.DateObject;
import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.bean.invoice.*;
import com.events.bean.users.UserInfoBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.common.*;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.MailCreator;
import com.events.common.email.send.QuickMailSendThread;
import com.events.data.email.EmailServiceData;
import com.events.data.invoice.BuildInvoiceData;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildInvoice {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    public InvoiceResponseBean saveInvoice(InvoiceRequestBean invoiceRequestBean){
        InvoiceResponseBean invoiceResponseBean = new InvoiceResponseBean();
        if(invoiceRequestBean!=null && invoiceRequestBean.getInvoiceBean()!=null && invoiceRequestBean.getArrInvoiceItemBean()!=null &&
                !invoiceRequestBean.getArrInvoiceItemBean().isEmpty()) {
            InvoiceBean invoiceBean = invoiceRequestBean.getInvoiceBean();
            if(invoiceBean!=null){
                String sInvoiceDate = invoiceBean.getHumanInvoiceDate();
                if(!Utility.isNullOrEmpty(sInvoiceDate)){
                    DateObject invoiceDate = DateSupport.convertTime( sInvoiceDate + " 00:00 AM",
                            DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), "dd MMMMM, yyyy hh:mm aaa",
                            DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);
                    invoiceBean.setInvoiceDate(invoiceDate.getMillis() );
                }


                String sInvoiceDueDate = invoiceBean.getHumanDueDate();
                if(!Utility.isNullOrEmpty(sInvoiceDueDate)){
                    DateObject dueDate = DateSupport.convertTime( sInvoiceDueDate + " 00:00 AM",
                            DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), "dd MMMMM, yyyy hh:mm aaa",
                            DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);
                    invoiceBean.setDueDate(dueDate.getMillis() );
                }



                if(Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
                    invoiceBean.setInvoiceId( Utility.getNewGuid());
                    invoiceBean = createInvoice( invoiceBean );
                }else {
                    invoiceBean = updateInvoice( invoiceBean );
                }

                if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())){
                    invoiceRequestBean.setInvoiceId(invoiceBean.getInvoiceId() );
                    BuildInvoiceItems buildInvoiceItems = new BuildInvoiceItems();
                    buildInvoiceItems.saveInvoiceItems( invoiceRequestBean );
                    invoiceResponseBean.setInvoiceBean( invoiceBean );
                    invoiceResponseBean.setInvoiceId( invoiceBean.getInvoiceId() );
                }

            }
        }
        return invoiceResponseBean;
    }
    public InvoiceBean createInvoice(InvoiceBean invoiceBean) {
        if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
            invoiceBean.setStatus("New");
            BuildInvoiceData buildInvoiceData = new BuildInvoiceData();
            Integer numOfRowsInserted = buildInvoiceData.insertInvoice( invoiceBean );
            if(numOfRowsInserted<=0){
                appLogging.error("Invoice was not created : " + invoiceBean );
                invoiceBean = new InvoiceBean();
            }
        } else {
            appLogging.error("Invalid Invoice Request Used for inserts: " + ParseUtil.checkNullObject(invoiceBean));
        }
        return invoiceBean;
    }
    public InvoiceBean updateInvoice(InvoiceBean invoiceBean){
        if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
            BuildInvoiceData buildInvoiceData = new BuildInvoiceData();
            Integer numOfRowsInserted = buildInvoiceData.updateInvoice(invoiceBean);
            if(numOfRowsInserted<=0){
                appLogging.error("Invoice was not Update : " + invoiceBean );
                invoiceBean = new InvoiceBean();
            }
        } else {
            appLogging.error("Invalid Invoice Request Used for update : " + ParseUtil.checkNullObject(invoiceBean));
        }
        return invoiceBean;
    }

    public boolean deleteInvoice(InvoiceBean invoiceBean){
        boolean isSuccess = false;
        if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
            BuildInvoiceData buildInvoiceData = new BuildInvoiceData();
            Integer numOfRowsDeleted = buildInvoiceData.deleteInvoice(invoiceBean);
            if(numOfRowsDeleted>0){
                appLogging.error("Invoice was deleted : " + invoiceBean );
                isSuccess = true;
            }
        } else {
            appLogging.error("Invalid Invoice Request Used for update : " + ParseUtil.checkNullObject(invoiceBean));
        }
        return isSuccess;
    }

    public boolean sendInvoiceInEmail(InvoiceEmailRequestBean invoiceEmailRequestBean){
        boolean  isEmailSentSuccessfully = false;
        if(invoiceEmailRequestBean!=null && invoiceEmailRequestBean.getInvoiceBean()!=null && invoiceEmailRequestBean.getVendorResponseBean()!=null &&
                invoiceEmailRequestBean.getClientResponseBean()!=null ) {
            InvoiceBean invoiceBean = invoiceEmailRequestBean.getInvoiceBean();


            AccessInvoicePdf accessInvoicePdf = new AccessInvoicePdf();
            String fileUploadLocation = applicationConfig.get(Constants.FILE_UPLOAD_LOCATION);
            String sUserFolderName = accessInvoicePdf.getUserFolderName(invoiceEmailRequestBean.getVendorResponseBean().getUserBean() , fileUploadLocation) ;
            String fileUploadHost = Utility.getFileUploadHost();
            String bucket = Utility.getS3Bucket();

            /*jsonResponseObj.put("invoice_id", sInvoiceId);
            jsonResponseObj.put("file_host", fileUploadHost);
            jsonResponseObj.put("bucket", bucket);
            jsonResponseObj.put("folder_path_name", sUserFolderName);*/



            EmailServiceData emailServiceData = new EmailServiceData();
            EmailTemplateBean emailTemplateBean = emailServiceData.getEmailTemplate(Constants.EMAIL_TEMPLATE.CLIENT_INVOICE);

            if(emailTemplateBean!=null){
                VendorResponseBean vendorResponseBean = invoiceEmailRequestBean.getVendorResponseBean();
                VendorBean vendorBean = vendorResponseBean.getVendorBean();
                UserInfoBean vendorUserInfoBean = vendorResponseBean.getUserInfoBean();

                ClientResponseBean clientResponseBean = invoiceEmailRequestBean.getClientResponseBean();
                ClientBean clientBean = clientResponseBean.getClientBean();
                UserInfoBean clientUserInfoBean = clientResponseBean.getUserInfoBean();


                String sHtmlTemplate = emailTemplateBean.getHtmlBody();
                String sTxtTemplate = emailTemplateBean.getTextBody();
                String sSubject = emailTemplateBean.getEmailSubject();

                EmailQueueBean emailQueueBean = new EmailQueueBean();
                emailQueueBean.setEmailSubject(emailTemplateBean.getEmailSubject());
                emailQueueBean.setFromAddress(emailTemplateBean.getFromAddress());
                emailQueueBean.setFromAddressName(emailTemplateBean.getFromAddressName());
                if(vendorUserInfoBean!=null && !Utility.isNullOrEmpty(vendorUserInfoBean.getEmail())){
                    emailQueueBean.setReplyToAddress( vendorUserInfoBean.getEmail() );
                }

                emailQueueBean.setToAddress( clientUserInfoBean.getEmail() );
                emailQueueBean.setToAddressName(clientUserInfoBean.getEmail() );
                emailQueueBean.setHtmlBody(sHtmlTemplate);
                emailQueueBean.setTextBody(sTxtTemplate);

                // mark it as sent so that it wont get picked up by email service. The email gets sent below
                emailQueueBean.setStatus(Constants.EMAIL_STATUS.SENT.getStatus());


                // We are just creating a record in the database with this action.
                // The new password will be sent separately.
                // This must be changed so that user will have to click link to
                // generate the new password.
                MailCreator dummyEailCreator = new EmailCreator();
                dummyEailCreator.create(emailQueueBean , new EmailSchedulerBean());

                // Now here we will be putting the correct password in the email
                // text and
                // send it out directly.
                // This needs to be changed. Warning bells are rining.
                // Lots of potential to fail.

                Map<String, Object> mapTextEmailValues = new HashMap<String, Object>();
                Map<String, Object> mapHtmlEmailValues = new HashMap<String, Object>();
                Map<String, Object> mapSubjectEmailValues = new HashMap<String, Object>();

                            /*
                            {{VENDOR_NAME}} : Your Invoice
            Dear {{CLIENT_GIVEN_NAME}},\nPlease click on the link below to download your invoice.\n{{{INVOICE_LINK}}}\n\n
            If you have any questions please contact us at {{VENDOR_EMAIL}}.\n\n Thank You for your business.\n{{VENDOR_NAME}}
             */
                mapTextEmailValues.put("VENDOR_NAME", vendorBean.getVendorName() );
                mapHtmlEmailValues.put("VENDOR_NAME", vendorBean.getVendorName() );
                mapSubjectEmailValues.put("VENDOR_NAME", vendorBean.getVendorName() );

                String sVendorEmailUrl = "<a href=\"mailto:"+vendorUserInfoBean.getEmail()+"\" target=\'_blank\">"+vendorUserInfoBean.getEmail()+"</a>";
                mapTextEmailValues.put("VENDOR_EMAIL",  sVendorEmailUrl );
                mapHtmlEmailValues.put("VENDOR_EMAIL",  sVendorEmailUrl );
                mapSubjectEmailValues.put("VENDOR_EMAIL",  sVendorEmailUrl );

                String sClientGivenName = ParseUtil.checkNull(clientUserInfoBean.getFirstName()+ " " + clientUserInfoBean.getLastName() ) ;


                mapTextEmailValues.put("CLIENT_GIVEN_NAME", sClientGivenName );
                mapHtmlEmailValues.put("CLIENT_GIVEN_NAME", sClientGivenName );
                mapSubjectEmailValues.put("CLIENT_GIVEN_NAME", sClientGivenName );

                String sContactUsUrl = "<a href=\""+fileUploadHost+"/"+bucket+"/"+sUserFolderName+"/"+invoiceBean.getInvoiceId()+".pdf\" target=\'_blank\">Download Invoice</a>";
                mapTextEmailValues.put("INVOICE_LINK",sContactUsUrl);
                mapHtmlEmailValues.put("INVOICE_LINK", sContactUsUrl);
                mapSubjectEmailValues.put("INVOICE_LINK", sContactUsUrl);

                MustacheFactory mf = new DefaultMustacheFactory();
                Mustache mustacheText =  mf.compile(new StringReader(sTxtTemplate), Constants.EMAIL_TEMPLATE.CLIENT_INVOICE.toString()+"_"+invoiceBean.getInvoiceId()+"_text");
                Mustache mustacheHtml = mf.compile(new StringReader(sHtmlTemplate), Constants.EMAIL_TEMPLATE.CLIENT_INVOICE.toString()+"_"+invoiceBean.getInvoiceId()+"_html");
                Mustache mustacheSubject = mf.compile(new StringReader(sSubject), Constants.EMAIL_TEMPLATE.CLIENT_INVOICE.toString()+"_"+invoiceBean.getInvoiceId()+"_subject");

                StringWriter txtWriter = new StringWriter();
                StringWriter htmlWriter = new StringWriter();
                StringWriter subjectWriter = new StringWriter();
                try {
                    mustacheText.execute(txtWriter, mapTextEmailValues).flush();
                    mustacheHtml.execute(htmlWriter, mapHtmlEmailValues).flush();
                    mustacheSubject.execute(subjectWriter, mapSubjectEmailValues).flush();
                } catch (IOException e) {
                    txtWriter = new StringWriter();
                    htmlWriter = new StringWriter();
                    subjectWriter = new StringWriter();
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                appLogging.error("Html Email Writers: " + htmlWriter.toString());

                emailQueueBean.setHtmlBody(htmlWriter.toString());
                emailQueueBean.setTextBody(txtWriter.toString());
                emailQueueBean.setEmailSubject( subjectWriter.toString() );

                emailQueueBean.setStatus(Constants.EMAIL_STATUS.NEW.getStatus());

                appLogging.error("Using the Mustache API to generate Email Querue Bean : " + emailQueueBean);
                // This will actually send the email. Spawning a thread and continue
                // execution.
                Thread quickEmail = new Thread(new QuickMailSendThread( emailQueueBean), "New Vendor Registered Access Email");
                quickEmail.start();
                isEmailSentSuccessfully = true;
            }

        }
        return isEmailSentSuccessfully;
    }
}
