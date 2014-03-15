package com.events.common;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.common.ParentSiteEnabledBean;
import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.bean.users.*;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.clients.AccessClients;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.MailCreator;
import com.events.common.email.send.QuickMailSendThread;
import com.events.common.exception.users.ManagePasswordException;
import com.events.data.ParentSiteEnabledData;
import com.events.data.email.EmailServiceData;
import com.events.users.AccessUsers;
import com.events.users.ForgotPassword;
import com.events.users.ManageUserPassword;
import com.events.vendors.AccessVendors;
import com.events.vendors.website.AccessVendorWebsite;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/2/14
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentSiteEnabled {
    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public ParentSiteEnabledBean getParentSiteEnabledStatusForUser(UserRequestBean userRequestBean){
        ParentSiteEnabledBean parentSiteEnabledBean = new ParentSiteEnabledBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId())) {
            ParentSiteEnabledData parentSiteEnabledData = new ParentSiteEnabledData();
            parentSiteEnabledBean = parentSiteEnabledData.getParentSiteEnabledStatusByUser( userRequestBean );
        }
        return parentSiteEnabledBean;
    }

    public ParentSiteEnabledBean toggleParentSiteEnableStatus(UserRequestBean userRequestBean){
        ParentSiteEnabledBean newParentSiteEnabledBean = new ParentSiteEnabledBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId() )) {

            int numOfRowsToggled = 0;
            boolean currentWebsiteStatus = false;
            ParentSiteEnabledBean parentSiteEnabledBean = getParentSiteEnabledStatusForUser(userRequestBean);

            ParentSiteEnabledData parentSiteEnabledData = new ParentSiteEnabledData();
            if(parentSiteEnabledBean!=null && !Utility.isNullOrEmpty(parentSiteEnabledBean.getParentSiteEnabledId())) {
                newParentSiteEnabledBean.setParentSiteEnabledId(parentSiteEnabledBean.getParentSiteEnabledId());
                newParentSiteEnabledBean.setUserId( parentSiteEnabledBean.getUserId() );
                newParentSiteEnabledBean.setAllowed( userRequestBean.isWebsiteAccessEnabled()  );

                numOfRowsToggled = parentSiteEnabledData.updateParentSiteEnabledStatus(newParentSiteEnabledBean);
            } else {
                // Record doesnt exist. So create record with "Enabled" access.
                newParentSiteEnabledBean.setUserId(userRequestBean.getUserId());
                newParentSiteEnabledBean.setParentSiteEnabledId( Utility.getNewGuid() );
                newParentSiteEnabledBean.setAllowed( userRequestBean.isWebsiteAccessEnabled() );

                numOfRowsToggled = parentSiteEnabledData.insertParentSiteEnabledStatus(newParentSiteEnabledBean) ;
            }

            if(numOfRowsToggled<=0){
                //The toggle action failed.
                appLogging.error("Toggle of Client parent website access failed : " + parentSiteEnabledBean );
                newParentSiteEnabledBean = new ParentSiteEnabledBean();
            }
        }
        return newParentSiteEnabledBean;
    }

    public boolean resetParentSitePassword(UserRequestBean userRequestBean){
        boolean isSuccess = false;
        if( userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId())) {
            AccessUsers accessUsers = new AccessUsers();
            UserBean userBean = accessUsers.getUserById(userRequestBean);
            if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
                userRequestBean.setUserInfoId( userBean.getUserInfoId() );
                UserInfoBean userInfoBean = accessUsers.getUserInfoFromInfoId( userRequestBean );

                PasswordRequestBean passwordRequest = new PasswordRequestBean();
                passwordRequest.setUserId( userBean.getUserId() );

                try{
                    ManageUserPassword manageUserPassword = new ManageUserPassword();
                    isSuccess = manageUserPassword.resetUserPassword(passwordRequest);
                }catch (ManagePasswordException me) {
                    appLogging.error("Password for Parent Site was not reset for Client - " + userInfoBean );
                }

            } else {
                appLogging.error("Password nhot reset. Invalid User Id used - " + ParseUtil.checkNullObject(userRequestBean) );
            }

        }
        return isSuccess;

    }

    public boolean resetParentSitePassword(ParentSiteEnabledBean parentSiteEnabledBean){
        boolean isSuccess = false;
        if(parentSiteEnabledBean!=null && !Utility.isNullOrEmpty(parentSiteEnabledBean.getUserId())) {
            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setUserId( parentSiteEnabledBean.getUserId() );

            isSuccess = resetParentSitePassword(userRequestBean) ;
        } else {
            appLogging.error("Password not reset. Invalid User Id in ParentSiteEnabledBean used - " + ParseUtil.checkNullObject(parentSiteEnabledBean) );
        }
        return isSuccess;
    }


    public ForgotPasswordBean createParentSiteNewPasswordUserRequest(ClientBean clientBean , UserRequestBean userRequestBean) {

        ForgotPasswordBean forgotPasswordBean = new ForgotPasswordBean();
        appLogging.info("Password Reset request for "+ ParseUtil.checkNull(userRequestBean.getEmail()) + " recieved.");
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId())) {

            //
            AccessUsers accessUsers = new AccessUsers();
            UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId(userRequestBean);
            UserBean userBean = accessUsers.getUserById(userRequestBean);

            if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId()) && userInfoBean!=null && !Utility.isNullOrEmpty(userInfoBean.getEmail()) ) {


                userBean.setUserInfoId(userInfoBean.getUserInfoId() );
                userBean.setUserInfoBean( userInfoBean );

                String sEmailAddress = userInfoBean.getEmail();

                forgotPasswordBean.setForgotPasswordId(Utility.getNewGuid());
                forgotPasswordBean.setUserId(userBean.getUserId());

                String sSecureTokenId = Utility.getNewGuid();
                forgotPasswordBean.setSecureTokenId(sSecureTokenId);

                forgotPasswordBean.setCreateDate(DateSupport.getEpochMillis());
                forgotPasswordBean.setHumanCreateDate(DateSupport.getUTCDateTime());

                forgotPasswordBean.setUsable(true);

                ForgotPassword forgotPassword = new ForgotPassword();
                boolean isSuccess = forgotPassword.createChangePasswordRecord(forgotPasswordBean);

                if(isSuccess) {
                    appLogging.info("Password Reset request : The request was created successfully "  + sEmailAddress);
                    boolean isSendEmailSuccess = sendParentSitePasswordResetEmail(forgotPasswordBean, userBean, clientBean );

                    if(isSendEmailSuccess) {
                        appLogging.info("Password Reset request : Success sending email : " + sEmailAddress);
                    }  else  {

                        forgotPasswordBean.setForgotPasswordId(Constants.EMPTY);
                        appLogging.info("Password Reset request : Sending email failed : " + sEmailAddress);
                    }
                } else {
                    forgotPasswordBean.setForgotPasswordId(Constants.EMPTY);
                    appLogging.info("Password Reset request : The request failed for " + sEmailAddress);
                }
            } else {
                appLogging.info("Password Reset request : Admin account does not exist for "  + ParseUtil.checkNullObject(userRequestBean) );
            }
        } else  {
            appLogging.info("Password Reset request : Invalid email address ");
        }
        return forgotPasswordBean;
    }

    private boolean sendParentSitePasswordResetEmail(ForgotPasswordBean forgotPasswordBean, UserBean userBean , ClientBean clientBean) {
        boolean isSuccess = false;
        if(forgotPasswordBean!=null && !Utility.isNullOrEmpty(forgotPasswordBean.getForgotPasswordId()) &&
                userBean!=null && userBean.getUserInfoBean() !=null && !Utility.isNullOrEmpty(userBean.getUserInfoBean().getUserInfoId()) ) {

            if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getVendorId())){
                VendorRequestBean vendorRequestBean = new VendorRequestBean();
                vendorRequestBean.setVendorId( clientBean.getVendorId() );

                AccessVendors accessVendors = new AccessVendors();
                VendorBean vendorBean = accessVendors.getVendor( vendorRequestBean );

                if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())){
                    EmailServiceData emailServiceData = new EmailServiceData();
                    EmailTemplateBean emailTemplateBean = emailServiceData.getEmailTemplate(Constants.EMAIL_TEMPLATE.CLIENT_PARENTSITE_ACCESS);


                    String sHtmlTemplate = emailTemplateBean.getHtmlBody();
                    String sTxtTemplate = emailTemplateBean.getTextBody();
                    String sSubjectTemplate = emailTemplateBean.getEmailSubject();

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


                    Map<String, Object> mapTextEmailValues = new HashMap<String, Object>();
                    Map<String, Object> mapHtmlEmailValues = new HashMap<String, Object>();
                    mapTextEmailValues.put("VENDOR_NAME",vendorBean.getVendorName());
                    mapHtmlEmailValues.put("VENDOR_NAME", vendorBean.getVendorName());
                    mapTextEmailValues.put("FIRST_NAME",sFirstName);
                    mapHtmlEmailValues.put("FIRST_NAME", sFirstName);
                    mapTextEmailValues.put("LAST_NAME",sLastName);
                    mapHtmlEmailValues.put("LAST_NAME", sLastName);
                    mapTextEmailValues.put("GIVEN_NAME",sGivenName);
                    mapHtmlEmailValues.put("GIVEN_NAME", sGivenName);

                    String sResetDomain = ParseUtil.checkNull(applicationConfig.get(Constants.DOMAIN));
                    if(sResetDomain!=null && !"".equalsIgnoreCase(sResetDomain)) {


                        AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
                        VendorWebsiteFeatureBean vendorWebsiteFeatureBean = accessVendorWebsite.getSubDomain( vendorBean );
                        if(vendorWebsiteFeatureBean!=null && !Utility.isNullOrEmpty(vendorWebsiteFeatureBean.getValue())){
                            sResetDomain = vendorWebsiteFeatureBean.getValue() + "." + sResetDomain;
                        }

                        String sProtocol = applicationConfig.get(Constants.PROP_LINK_PROTOCOL,"http");

                        String sPortalLink = ParseUtil.checkNull(sProtocol + "://" + sResetDomain + "/com/events/common/set_password.jsp?lotophagi="+forgotPasswordBean.getSecureTokenId());

                        mapTextEmailValues.put("PORTAL_LINK",sPortalLink);
                        mapHtmlEmailValues.put("PORTAL_LINK","<a href=\""+sPortalLink+"\" target=\"_blank\">Set New Password</a>");

                        String sProductName = ParseUtil.checkNull(applicationConfig.get(Constants.PRODUCT_NAME));

                        mapTextEmailValues.put("PRODUCT_NAME",sProductName);
                        mapHtmlEmailValues.put("PRODUCT_NAME",sProductName);


                        MustacheFactory mf = new DefaultMustacheFactory();
                        Mustache mustacheText =  mf.compile(new StringReader(sTxtTemplate), Constants.EMAIL_TEMPLATE.NEWPASSWORD.toString()+"_text");
                        Mustache mustacheHtml = mf.compile(new StringReader(sHtmlTemplate), Constants.EMAIL_TEMPLATE.NEWPASSWORD.toString()+"_html");
                        Mustache mustacheSubject = mf.compile( new StringReader(sSubjectTemplate), Constants.EMAIL_TEMPLATE.NEWPASSWORD.toString()+"_subject" );

                        StringWriter txtWriter = new StringWriter();
                        StringWriter htmlWriter = new StringWriter();
                        StringWriter subjectWriter = new StringWriter();
                        try {
                            mustacheText.execute(txtWriter, mapTextEmailValues).flush();
                            mustacheHtml.execute(htmlWriter, mapHtmlEmailValues).flush();
                            mustacheSubject.execute(subjectWriter, mapHtmlEmailValues ).flush();
                        } catch (IOException e) {
                            txtWriter = new StringWriter();
                            htmlWriter = new StringWriter();
                            subjectWriter = new StringWriter();
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                        emailQueueBean.setHtmlBody(htmlWriter.toString());
                        emailQueueBean.setTextBody(txtWriter.toString());
                        emailQueueBean.setEmailSubject(subjectWriter.toString() );
                        emailQueueBean.setCcAddress("kensenjohn@gmail.com");

                        {
                            // We are just creating a record in the database with this action.
                            // The new password will be sent separately.
                            // This must be changed so that user will have to click link to
                            // generate the new password.
                            // mark it as sent so that it wont get picked up by email service. The email gets sent below
                            emailQueueBean.setStatus(Constants.EMAIL_STATUS.SENT.getStatus());
                            MailCreator dummyEailCreator = new EmailCreator();
                            dummyEailCreator.create(emailQueueBean , new EmailSchedulerBean());
                            appLogging.error("Text body of email. : " + emailQueueBean.getTextBody());
                        }

                        emailQueueBean.setStatus(Constants.EMAIL_STATUS.NEW.getStatus());

                        appLogging.error("Using the Mustache API to generate Email Querue Bean : " + emailQueueBean);
                        // This will actually send the email. Spawning a thread and continue
                        // execution.
                        Thread quickEmail = new Thread(new QuickMailSendThread( emailQueueBean), "Quick Email Password Reset");
                        quickEmail.start();
                        isSuccess = true;
                    }

                }

            }

        }
        return isSuccess;

    }
}
