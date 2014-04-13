package com.events.users;

import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.bean.users.*;
import com.events.bean.users.permissions.RolesBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.bean.users.permissions.UserRolesBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteURLBean;
import com.events.common.*;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.MailCreator;
import com.events.common.email.send.QuickMailSendThread;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.users.EditUserException;
import com.events.common.exception.users.EditUserInfoException;
import com.events.common.exception.users.ManagePasswordException;
import com.events.common.exception.vendors.EditVendorException;
import com.events.data.email.EmailServiceData;
import com.events.data.users.BuildUserData;
import com.events.users.permissions.AccessUserRoles;
import com.events.users.permissions.BuildUserRoles;
import com.events.users.permissions.CheckPermission;
import com.events.users.permissions.UserRolePermission;
import com.events.vendors.AccessVendors;
import com.events.vendors.BuildVendors;
import com.events.vendors.website.AccessVendorWebsite;
import com.events.vendors.website.VendorWebsiteFeature;
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
 * Date: 12/13/13
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildUsers {

    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer createUser(UserBean userBean) throws EditUserException {
        Integer iNumOfRecords = 0;
        if(userBean!=null && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserId()))
                && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserInfoId())) ) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.insertUser(userBean);
            if(iNumOfRecords<=0) {
                appLogging.error("User was not be created : " + userBean);
                throw new EditUserException();
            }
        } else {
            appLogging.error("User not created because some data is missing userid : " + ParseUtil.checkNull(userBean.getUserId()) +
                    "  userinfoid : " + ParseUtil.checkNull(userBean.getUserInfoId()) );
            throw new EditUserException();
        }
        return iNumOfRecords;
    }
    public Integer deleteUser(UserBean userBean) {
        Integer iNumOfRecords = 0;
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())){
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.deleteUser(userBean);
        }
        return iNumOfRecords;
    }
    public Integer updateUser(UserBean userBean) throws EditUserException {
        Integer iNumOfRecords = 0;
        if(userBean!=null && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserId()))
                && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserInfoId())) ) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.updateUser(userBean);
        } else {
            appLogging.error("User was not updated because some data is missing userid : " + ParseUtil.checkNull(userBean.getUserId()) +
                    "  userinfoid : " + ParseUtil.checkNull(userBean.getUserInfoId()) );
            throw new EditUserException();
        }
        return iNumOfRecords;
    }
    public UserBean updateTeamMemberUserDetails( UserRequestBean userRequestBean )    throws EditUserException, EditUserInfoException, ManagePasswordException {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId()) ) {
            if( !Utility.isNullOrEmpty(userRequestBean.getParentId()) ) {
                UserInfoBean userInfoBean = generateExistingUserInfoBean(userRequestBean);
                Integer iNumOfUserInfoIdRows = updateUserInfo(userInfoBean);

                if(iNumOfUserInfoIdRows > 0 ) {
                    ArrayList<String> arrRoleId = userRequestBean.getArrRoleId();

                    ArrayList<RolesBean> arrRoleBean = new ArrayList<RolesBean>();
                    for(String roleId : arrRoleId) {
                        RolesBean rolesBean = new RolesBean();
                        rolesBean.setRoleId( roleId );
                        arrRoleBean.add(rolesBean);
                    }
                    userBean.setUserId( userRequestBean.getUserId() );
                    AccessUserRoles accessUserRoles = new AccessUserRoles();
                    ArrayList<UserRolesBean> arrOldUserRolesBean = accessUserRoles.getUserRolesByUserId( userBean);

                    BuildUserRoles buildUserRoles = new BuildUserRoles();
                    ArrayList<UserRolesBean> arrNewUserRolesBean = buildUserRoles.createUserRole( userRequestBean.getUserId() , arrRoleBean );
                    if(arrNewUserRolesBean!=null && !arrNewUserRolesBean.isEmpty() ) {
                        if ( !buildUserRoles.deleteUserRole( arrOldUserRolesBean ) ){
                            // if the old roles could not be deleted. then delete the newest user roles that were created just above.

                            appLogging.error("There was error while updating team member user Role data " + userRequestBean);
                            buildUserRoles.deleteUserRole( arrNewUserRolesBean );
                            userBean = new UserBean();
                        }
                    }
                } else {
                    appLogging.error("There was error while updating team member user info data " + userRequestBean);
                }

            }else {
                appLogging.error("Invalid request while trying to update Team Member " + ParseUtil.checkNullObject(userRequestBean));
            }
        }
        return userBean;
    }
    public boolean  deleteTeamMember( UserRequestBean userRequestBean )  {
        boolean isSuccessfullyDeleted = false;
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId()) ) {
            UserBean userBean  = new UserBean();
            userBean.setUserId( userRequestBean.getUserId());

            Integer iNumOfTeamMembersDeleted = deleteUser(userBean );
            if(iNumOfTeamMembersDeleted>0){

                AccessUserRoles accessUserRoles = new AccessUserRoles();
                ArrayList<UserRolesBean> arrUserRolesBean =  accessUserRoles.getUserRolesByUserId(userBean);
                if(arrUserRolesBean!=null && !arrUserRolesBean.isEmpty() ) {
                    BuildUserRoles buildUserRoles = new BuildUserRoles();
                    buildUserRoles.deleteUserRole( arrUserRolesBean );
                }
                isSuccessfullyDeleted = true;
            }
        }
        return isSuccessfullyDeleted;
    }
    public UserBean createTeamMember( UserRequestBean userRequestBean )    throws EditUserException, EditUserInfoException, ManagePasswordException {
        UserBean userBean = new UserBean();
        boolean isError = false;
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getEmail())
                && userRequestBean.getPasswordRequestBean() !=null ) {

            if( !Utility.isNullOrEmpty(userRequestBean.getParentId()) ) {
                UserInfoBean userInfoBean = generateNewUserInfoBean(userRequestBean);
                Integer iNumOfUserInfoIdRows = createUserInfo(userInfoBean);
                if( iNumOfUserInfoIdRows>0 ) {
                    userRequestBean.setUserInfoId( userInfoBean.getUserInfoId() );
                    userBean = generateNewUserBean(userRequestBean);

                    ManageUserPassword managePasswords = new ManageUserPassword();
                    PasswordRequestBean passwordRequest = userRequestBean.getPasswordRequestBean();
                    passwordRequest.setUserId( userBean.getUserId() );
                    Integer iNumOfPasswordRows = managePasswords.createPassword(passwordRequest);

                    if(iNumOfPasswordRows > 0 ) {
                        ArrayList<String> arrRoleId = userRequestBean.getArrRoleId();

                        ArrayList<RolesBean> arrRoleBean = new ArrayList<RolesBean>();
                        for(String roleId : arrRoleId) {
                            RolesBean rolesBean = new RolesBean();
                            rolesBean.setRoleId( roleId );
                            arrRoleBean.add(rolesBean);
                        }

                        BuildUserRoles buildUserRoles = new BuildUserRoles();
                        ArrayList<UserRolesBean> arrUserRolesBean = buildUserRoles.createUserRole( userBean.getUserId() ,arrRoleBean);
                        if( arrUserRolesBean!=null && !arrUserRolesBean.isEmpty()) {
                            Integer iNumOfUsersRows = createUser( userBean );

                            if(iNumOfUsersRows<=0) {
                                appLogging.info("Error while creating a new user (team member)" +  userBean.getUserId()  );
                                userBean = new UserBean();
                            }
                        } else {
                            isError = true;
                            appLogging.info("Unable to set the permission for user (team member)" +  userBean.getUserId()  );
                        }
                    } else {
                        isError = true;
                        appLogging.info("Unable to create password for user " +  userBean.getUserId()  );
                    }
                }
            }
        }
        return userBean;
    }
    public ForgotPasswordBean createNewTeamMemberPasswordUserRequest(VendorBean vendorBean , UserRequestBean userRequestBean) {

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
                    boolean isSendEmailSuccess = sendParentSitePasswordResetEmailForTeam(forgotPasswordBean, userBean, vendorBean );

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

    private boolean sendParentSitePasswordResetEmailForTeam(ForgotPasswordBean forgotPasswordBean, UserBean userBean , VendorBean vendorBean) {
        boolean isSuccess = false;
        if(forgotPasswordBean!=null && !Utility.isNullOrEmpty(forgotPasswordBean.getForgotPasswordId()) &&
                userBean!=null && userBean.getUserInfoBean() !=null && !Utility.isNullOrEmpty(userBean.getUserInfoBean().getUserInfoId()) ) {

            if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())){
                EmailServiceData emailServiceData = new EmailServiceData();
                EmailTemplateBean emailTemplateBean = emailServiceData.getEmailTemplate(Constants.EMAIL_TEMPLATE.NEW_TEAM_MEMBER_ACCESS);

                if(emailTemplateBean!=null && !Utility.isNullOrEmpty(emailTemplateBean.getEmailTemplateId())) {
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

                    AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
                    VendorWebsiteURLBean vendorWebsiteURLBean = accessVendorWebsite.getVendorWebsiteUrlBean(vendorBean);

                    if(vendorWebsiteURLBean!=null && !Utility.isNullOrEmpty(vendorWebsiteURLBean.getDomain())) {

                        String sPortalLink = ParseUtil.checkNull(vendorWebsiteURLBean.getUrl() + "/com/events/common/set_password.jsp?lotophagi="+forgotPasswordBean.getSecureTokenId());

                        mapTextEmailValues.put("PORTAL_LINK",sPortalLink);
                        mapHtmlEmailValues.put("PORTAL_LINK","<a href=\""+sPortalLink+"\" target=\"_blank\">Set New Password And Login</a>");

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
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                        emailQueueBean.setHtmlBody(htmlWriter.toString());
                        emailQueueBean.setTextBody(txtWriter.toString());
                        emailQueueBean.setEmailSubject(subjectWriter.toString());

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
                } else {
                    appLogging.error("The email template " + Constants.EMAIL_TEMPLATE.NEW_TEAM_MEMBER_ACCESS.getEmailTemplate() + " was not found.");
                }


            }

        }
        return isSuccess;

    }

    public UserBean registerUser(UserRequestBean userRequestBean) throws EditUserException, EditUserInfoException, ManagePasswordException {
        UserBean userBean = new UserBean();
        boolean isError = false;
        if(userRequestBean!=null && !"".equalsIgnoreCase(userRequestBean.getEmail())
                && !"".equalsIgnoreCase(ParseUtil.checkNullObject(userRequestBean.getPasswordRequestBean()))  ) {

            // Depending on Type of User
            VendorBean vendorBean = new VendorBean();
            if( userRequestBean.isPlanner() ) {
                BuildVendors buildVendors = new BuildVendors();
                try{
                    vendorBean = buildVendors.registerVendor( userRequestBean.getCompanyName() );
                    userRequestBean.setParentId( vendorBean.getVendorId() );
                    userRequestBean.setUserType(Constants.USER_TYPE.VENDOR);
                } catch(EditVendorException e) {
                    isError = true;
                    appLogging.info("Error while creating a vendor : " + ExceptionHandler.getStackTrace(e));
                    throw new EditUserException();
                }
            }

            if( !Utility.isNullOrEmpty(userRequestBean.getParentId()) ) {
                UserInfoBean userInfoBean = generateNewUserInfoBean(userRequestBean);
                Integer iNumOfUserInfoIdRows = createUserInfo(userInfoBean);

                if( iNumOfUserInfoIdRows>0 ) {
                    userRequestBean.setUserInfoId( userInfoBean.getUserInfoId() );
                    userBean = generateNewUserBean(userRequestBean);
                    Integer iNumOfUsersRows = createUser( userBean );

                    if(iNumOfUsersRows>0) {
                        ManageUserPassword managePasswords = new ManageUserPassword();
                        PasswordRequestBean passwordRequest = userRequestBean.getPasswordRequestBean();
                        passwordRequest.setUserId( userBean.getUserId() );
                        Integer iNumOfPasswordRows = managePasswords.createPassword(passwordRequest);
                        if(iNumOfPasswordRows<=0) {
                            isError = true;
                            appLogging.info("Error creating user password during registration : " + userRequestBean);
                        } else {
                            UserRolePermissionRequestBean userRolePermRequest = new UserRolePermissionRequestBean();
                            userRolePermRequest.setUserId( userBean.getUserId() );
                            userRolePermRequest.setParentId( userRequestBean.getParentId() );
                            userRolePermRequest.setUserType( userRequestBean.getUserType() );
                            UserRolePermission userRolePermission = new UserRolePermission();
                            if(!userRolePermission.initiatePermissionBootup( userRolePermRequest ) ) {
                                isError = true;
                                appLogging.info("Unable to set the permission." + isError );
                            } else {
                                if(userRequestBean.isPlanner()){
                                    userBean.setUserInfoBean( userInfoBean  );
                                    sendNewVendorWelcomeEmail(userBean,vendorBean) ;
                                }


                            }
                        }

                    } else {
                        isError = true;
                        appLogging.info("Error occurred while creating user : " + userRequestBean);
                    }
                } else {
                    isError = true;
                    appLogging.info("Error occurred while creating user info: " + userRequestBean);
                }
            }  else {
                isError = true;
                appLogging.info("Error occurred while creating User's Parent : " + userRequestBean);
            }

        } else {
            isError = true;
            appLogging.info("Invalid request used to register user : " + ParseUtil.checkNullObject(userRequestBean));
        }
        if(isError) {
            userBean = new UserBean();
        }
        return userBean;
    }

    public UserBean generateNewUserBean(UserRequestBean userRequestBean) {
        UserBean userBean = generateExistingUserBean(userRequestBean);
        userBean.setUserId( Utility.getNewGuid() );
        return userBean;
    }
    public UserBean generateExistingUserBean(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null){
            userBean.setParentId(userRequestBean.getParentId());
            userBean.setUserInfoId( userRequestBean.getUserInfoId() );
            userBean.setUserType( userRequestBean.getUserType() );
        }
        return userBean;
    }

    public UserInfoBean generateNewUserInfoBean(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = generateExistingUserInfoBean(userRequestBean);
        userInfoBean.setUserInfoId(Utility.getNewGuid());
        return userInfoBean;
    }

    public UserInfoBean generateExistingUserInfoBean(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = new UserInfoBean();
        if(userRequestBean!=null) {
            userInfoBean.setUserInfoId( ParseUtil.checkNull(userRequestBean.getUserInfoId()) );
            userInfoBean.setFirstName( ParseUtil.checkNull(userRequestBean.getFirstName()) );
            userInfoBean.setLastName( ParseUtil.checkNull(userRequestBean.getLastName()) );
            userInfoBean.setEmail( ParseUtil.checkNull(userRequestBean.getEmail())  );
            userInfoBean.setCompany( ParseUtil.checkNull(userRequestBean.getCompanyName()));
            userInfoBean.setCellPhone( ParseUtil.checkNull(userRequestBean.getCellPhone()));
            userInfoBean.setPhoneNum( ParseUtil.checkNull(userRequestBean.getWorkPhone()));
            userInfoBean.setAddress1( ParseUtil.checkNull(userRequestBean.getAddress1()));
            userInfoBean.setAddress2( ParseUtil.checkNull(userRequestBean.getAddress2()));
            userInfoBean.setCity( ParseUtil.checkNull(userRequestBean.getCity()));
            userInfoBean.setState( ParseUtil.checkNull(userRequestBean.getState()));
            userInfoBean.setCountry( ParseUtil.checkNull(userRequestBean.getCountry()));
            userInfoBean.setZipcode( ParseUtil.checkNull(userRequestBean.getPostalCode()));
            userInfoBean.setWebsite( ParseUtil.checkNull(userRequestBean.getWebsite()) );
        }
        return userInfoBean;
    }

    public Integer  createUserInfo(UserInfoBean userInfoBean) throws EditUserInfoException {
        Integer iNumOfRecords = 0;
        if(userInfoBean!=null && !"".equalsIgnoreCase(userInfoBean.getUserInfoId())) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.insertUserInfo(userInfoBean);
            if(iNumOfRecords<=0) {
                appLogging.error("User Info was not created : " + userInfoBean);
                throw new EditUserInfoException();
            }
        } else {
            appLogging.error("User Info was not created because some data is missing : " + ParseUtil.checkNullObject(userInfoBean)  );
            throw new EditUserInfoException();
        }
        return iNumOfRecords;
    }


    public Integer  updateUserInfo(UserInfoBean userInfoBean) throws EditUserInfoException{
        Integer iNumOfRecords = 0;
        if(userInfoBean!=null && !Utility.isNullOrEmpty(userInfoBean.getUserInfoId()) ) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.updateUserInfo(userInfoBean);
        }  else {
            appLogging.error("User Info was not updated because some data is missing userid : " + ParseUtil.checkNullObject(userInfoBean)  );
            throw new EditUserInfoException();
        }
        return iNumOfRecords;
    }

    public static String getPassThroughLink(UserBean userBean) {
        String sLink = Constants.EMPTY;
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
            CheckPermission checkPermission = new CheckPermission(userBean);
            if(checkPermission.can(Perm.ACCESS_DASHBOARD_TAB)) {
                sLink = Constants.DASHBOARD_LINK;
            } else if(checkPermission.can(Perm.ACCESS_CLIENTS_TAB)) {
                sLink = Constants.CLIENTS_LINK;
            } else  if(checkPermission.can(Perm.ACCESS_EVENTS_TAB)) {
                sLink = Constants.EVENTS_LINK;
            }
        }
        return sLink;
    }

    private boolean sendNewVendorWelcomeEmail(UserBean userBean,VendorBean vendorBean){
        boolean isEmailSendtSuccessfully = false;
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
            EmailServiceData emailServiceData = new EmailServiceData();
            EmailTemplateBean emailTemplateBean = emailServiceData.getEmailTemplate(Constants.EMAIL_TEMPLATE.NEW_VENDOR_ACCESS);

            if(emailTemplateBean!=null){
                String sHtmlTemplate = emailTemplateBean.getHtmlBody();
                String sTxtTemplate = emailTemplateBean.getTextBody();
                String sSubject = emailTemplateBean.getEmailSubject();

                EmailQueueBean emailQueueBean = new EmailQueueBean();
                emailQueueBean.setEmailSubject(emailTemplateBean.getEmailSubject());
                emailQueueBean.setFromAddress(emailTemplateBean.getFromAddress());
                emailQueueBean.setFromAddressName(emailTemplateBean.getFromAddressName());

                UserInfoBean userInfoBean = userBean.getUserInfoBean();
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
                Map<String, Object> mapSubjectEmailValues = new HashMap<String, Object>();

                AccessVendorWebsite accessVendorWebsite = new AccessVendorWebsite();
                VendorWebsiteURLBean vendorWebsiteURLBean = accessVendorWebsite.getVendorWebsiteUrlBean(vendorBean);


                if(vendorWebsiteURLBean!=null && !Utility.isNullOrEmpty(vendorWebsiteURLBean.getDomain())) {


                    String sDomain = ParseUtil.checkNull(applicationConfig.get(Constants.APPLICATION_DOMAIN));
                    mapTextEmailValues.put("APPLICATION_NAME",sDomain);
                    mapHtmlEmailValues.put("APPLICATION_NAME", sDomain);
                    mapSubjectEmailValues.put("APPLICATION_NAME", sDomain);

                    String sPortalLink = "<a href=\""+vendorWebsiteURLBean.getUrl()+"\" target=\'_blank\">"+sDomain+"</a>";
                    mapTextEmailValues.put("PORTAL_LINK",sPortalLink);
                    mapHtmlEmailValues.put("PORTAL_LINK", sPortalLink);
                    mapSubjectEmailValues.put("PORTAL_LINK", sPortalLink);

                    String sContactUsUrl = "<a href=\""+vendorWebsiteURLBean.getUrl()+"/com/events/common/contact.jsp\" target=\'_blank\">Contact Us Page</a>";
                    mapTextEmailValues.put("CONTACT_US_PAGE",sContactUsUrl);
                    mapHtmlEmailValues.put("CONTACT_US_PAGE", sContactUsUrl);
                    mapSubjectEmailValues.put("CONTACT_US_PAGE", sContactUsUrl);

                    MustacheFactory mf = new DefaultMustacheFactory();
                    Mustache mustacheText =  mf.compile(new StringReader(sTxtTemplate), Constants.EMAIL_TEMPLATE.NEW_VENDOR_ACCESS.toString()+"_text");
                    Mustache mustacheHtml = mf.compile(new StringReader(sHtmlTemplate), Constants.EMAIL_TEMPLATE.NEW_VENDOR_ACCESS.toString()+"_html");
                    Mustache mustacheSubject = mf.compile(new StringReader(sSubject), Constants.EMAIL_TEMPLATE.NEW_VENDOR_ACCESS.toString()+"_subject");

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
                    isEmailSendtSuccessfully = true;
                }

            }

        }
        return isEmailSendtSuccessfully;
    }

}
