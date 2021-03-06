package com.events.users;

import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.bean.users.*;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.website.*;
import com.events.common.*;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.MailCreator;
import com.events.common.email.send.QuickMailSendThread;
import com.events.common.exception.ExceptionHandler;
import com.events.data.email.EmailServiceData;
import com.events.data.users.ForgotPasswordData;
import com.events.vendors.website.AccessVendorWebsite;
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
 * User: root
 * Date: 1/9/14
 * Time: 7:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ForgotPassword {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private String sEmailAddress = Constants.EMPTY;

    public ForgotPassword(){}
    public ForgotPassword(String sEmailAddress) {
        this.sEmailAddress = sEmailAddress;
    }

    public ForgotPasswordBean createUserRequest() {

        ForgotPasswordBean forgotPasswordBean = new ForgotPasswordBean();
        appLogging.info("Password Reset request for "+ ParseUtil.checkNull(this.sEmailAddress) + " recieved.");
        if(!Utility.isNullOrEmpty(this.sEmailAddress)) {

            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setEmail( this.sEmailAddress );

            AccessUsers accessUsers = new AccessUsers();
            UserBean userBean = accessUsers.getUserByEmail(userRequestBean);

            if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId()))
            {

                forgotPasswordBean.setForgotPasswordId(Utility.getNewGuid());
                forgotPasswordBean.setUserId(userBean.getUserId());

                String sSecureTokenId = Utility.getNewGuid();
                forgotPasswordBean.setSecureTokenId(sSecureTokenId);

                forgotPasswordBean.setCreateDate(DateSupport.getEpochMillis());
                forgotPasswordBean.setHumanCreateDate(DateSupport.getUTCDateTime());

                forgotPasswordBean.setUsable(true);

                boolean isSuccess = createChangePasswordRecord(forgotPasswordBean);

                if(isSuccess) {
                    appLogging.info("Password Reset request : The request was created successfully "  + this.sEmailAddress);
                    boolean isSendEmailSuccess = sendPasswordResetEmail(forgotPasswordBean, userBean);

                    if(isSendEmailSuccess) {
                        appLogging.info("Password Reset request : Success sending email : " + this.sEmailAddress);
                    }  else  {

                        forgotPasswordBean.setForgotPasswordId(Constants.EMPTY);
                        appLogging.info("Password Reset request : Sending email failed : " + this.sEmailAddress);
                    }
                } else {
                    forgotPasswordBean.setForgotPasswordId(Constants.EMPTY);
                    appLogging.info("Password Reset request : The request failed for " + this.sEmailAddress);
                }
            } else {
                appLogging.info("Password Reset request : Admin account does not exist for "  + this.sEmailAddress);
            }
        } else  {
            appLogging.info("Password Reset request : Invalid email address ");
        }
        return forgotPasswordBean;
    }

    public boolean createChangePasswordRecord(ForgotPasswordBean forgotPasswordBean) {
        boolean isSuccess = false;
        if(forgotPasswordBean!=null && !Utility.isNullOrEmpty(forgotPasswordBean.getForgotPasswordId())) {

            ForgotPasswordData forgotpasswordData = new ForgotPasswordData();
            forgotpasswordData.deactivateForgotPassword(forgotPasswordBean);  // deactivating the old request for forgot password

            Integer iNumOfRecords = forgotpasswordData.createForgotPassword(forgotPasswordBean);
            if(iNumOfRecords > 0 ) {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private boolean sendPasswordResetEmail(ForgotPasswordBean forgotPasswordBean, UserBean userBean) {
        boolean isSuccess = false;
        appLogging.error("userBean : " + userBean + " " + forgotPasswordBean + " " + ParseUtil.checkNullObject(userBean.getUserInfoBean()));
        if(forgotPasswordBean!=null && !Utility.isNullOrEmpty(forgotPasswordBean.getForgotPasswordId()) &&
                userBean!=null && userBean.getUserInfoBean() !=null && !Utility.isNullOrEmpty(userBean.getUserInfoBean().getUserInfoId()) ) {

            EmailServiceData emailServiceData = new EmailServiceData();
            EmailTemplateBean emailTemplateBean = emailServiceData.getEmailTemplate(Constants.EMAIL_TEMPLATE.NEWPASSWORD);

            String sHtmlTemplate = emailTemplateBean.getHtmlBody();
            String sTxtTemplate = emailTemplateBean.getTextBody();

            EmailQueueBean emailQueueBean = new EmailQueueBean();
            emailQueueBean.setEmailSubject(emailTemplateBean.getEmailSubject());
            emailQueueBean.setFromAddress(emailTemplateBean.getFromAddress());
            emailQueueBean.setFromAddressName(emailTemplateBean.getFromAddressName());

            UserInfoBean userInfoBean = userBean.getUserInfoBean();
            String sFirstName = ParseUtil.checkNull(userInfoBean.getFirstName());
            String sLastName = ParseUtil.checkNull(userInfoBean.getLastName());
            String sGivenName = sFirstName + " " + sLastName;

            emailQueueBean.setToAddress( userInfoBean.getEmail() );
            emailQueueBean.setToAddressName(userInfoBean.getEmail() );
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
            mapTextEmailValues.put("FIRST_NAME",sFirstName);
            mapHtmlEmailValues.put("FIRST_NAME", sFirstName);
            mapTextEmailValues.put("LAST_NAME",sLastName);
            mapHtmlEmailValues.put("LAST_NAME", sLastName);
            mapTextEmailValues.put("GIVEN_NAME",sGivenName);
            mapHtmlEmailValues.put("GIVEN_NAME", sGivenName);

            String sBusinessName =  Constants.EMPTY;
            VendorWebsiteURLBean vendorWebsiteURLBean = new VendorWebsiteURLBean();
            AccessUsers accessUsers = new AccessUsers();
            ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser(userBean);
            if(parentTypeBean!=null && parentTypeBean.getVendorBean()!=null){
                VendorBean vendorBean = parentTypeBean.getVendorBean();
                sBusinessName = ParseUtil.checkNull(vendorBean.getVendorName());

                AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
                vendorWebsiteURLBean = accessVendorWebsite.getVendorWebsiteUrlBean(vendorBean);
            }
            if(vendorWebsiteURLBean!=null && !Utility.isNullOrEmpty(vendorWebsiteURLBean.getDomain())) {

                String sResetLink = ParseUtil.checkNull(vendorWebsiteURLBean.getUrl() + "/com/events/common/reset_password.jsp?lotophagi="+forgotPasswordBean.getSecureTokenId());

                mapTextEmailValues.put("NEW_PASSWORD_RESET_LINK",sResetLink);
                mapHtmlEmailValues.put("NEW_PASSWORD_RESET_LINK",sResetLink);


                mapTextEmailValues.put("BUSINESS_NAME",sBusinessName);
                mapHtmlEmailValues.put("BUSINESS_NAME",sBusinessName);


                MustacheFactory mf = new DefaultMustacheFactory();
                Mustache mustacheText =  mf.compile(new StringReader(sTxtTemplate), Constants.EMAIL_TEMPLATE.NEWPASSWORD.toString()+"_text");
                Mustache mustacheHtml = mf.compile(new StringReader(sHtmlTemplate), Constants.EMAIL_TEMPLATE.NEWPASSWORD.toString()+"_html");

                StringWriter txtWriter = new StringWriter();
                StringWriter htmlWriter = new StringWriter();
                try {
                    mustacheText.execute(txtWriter, mapTextEmailValues).flush();
                    mustacheHtml.execute(htmlWriter, mapHtmlEmailValues).flush();
                } catch (IOException e) {
                    appLogging.error("reset Password mustache exception: " + ExceptionHandler.getStackTrace(e));
                    txtWriter = new StringWriter();
                    htmlWriter = new StringWriter();
                }



                emailQueueBean.setHtmlBody(htmlWriter.toString());
                emailQueueBean.setTextBody(txtWriter.toString());

                emailQueueBean.setStatus(Constants.EMAIL_STATUS.NEW.getStatus());

                // This will actually send the email. Spawning a thread and continue
                // execution.
                Thread quickEmail = new Thread(new QuickMailSendThread( emailQueueBean), "Quick Email Password Reset");
                quickEmail.start();
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    public ForgotPasswordBean getForgotPasswordBean(String sSecureTokenId , String sUserId ) {
        ForgotPasswordBean forgotPasswordBean = new ForgotPasswordBean();
        if(!Utility.isNullOrEmpty(sSecureTokenId) && !Utility.isNullOrEmpty(sUserId)) {
            ForgotPasswordData forgotPasswordData = new ForgotPasswordData();
            forgotPasswordBean = forgotPasswordData.getForgotPassswordBean(sSecureTokenId, sUserId);
        }
        return forgotPasswordBean;
    }
}
