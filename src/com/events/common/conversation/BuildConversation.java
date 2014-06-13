package com.events.common.conversation;

import com.events.bean.common.conversation.*;
import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.bean.common.notify.NotifyBean;
import com.events.bean.upload.UploadBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.*;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.MailCreator;
import com.events.common.email.send.QuickMailSendThread;
import com.events.common.exception.ExceptionHandler;
import com.events.common.notify.Notification;
import com.events.data.conversation.BuildConversationData;
import com.events.data.email.EmailServiceData;
import com.events.users.AccessUsers;
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
 * Date: 6/2/14
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildConversation {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ConversationResponseBean saveConversation(ConversationRequestBean conversationRequestBean){
        ConversationResponseBean conversationResponseBean = new ConversationResponseBean();
        if( conversationRequestBean!=null ) {
            ConversationBean conversationBean = new ConversationBean();
            String notificationMessage = "Started a new conversation : " + conversationRequestBean.getConversationBody();
            if(Utility.isNullOrEmpty(conversationRequestBean.getConversationId())) {
                conversationBean = createConversation(conversationRequestBean);
                notificationMessage = "Started a new conversation: " + conversationRequestBean.getConversationName();
            } else {
                conversationBean = updateConversation(conversationRequestBean);
                notificationMessage = "Added a new new message to conversation: " + conversationRequestBean.getConversationName();
            }

            if(conversationBean!=null && !Utility.isNullOrEmpty(conversationBean.getConversationId())) {
                ArrayList<UserConversationBean> arrUserConversationBean = generateArrUserConversationBean( conversationRequestBean , conversationBean );
                deleteAllUserConversation(conversationRequestBean);

                BuildConversationData buildConversationData = new BuildConversationData();
                Integer numOfUserConversations = buildConversationData.insertUserConversation(  arrUserConversationBean  );

                Integer iNumOfConvoMessage = 0;
                Integer iNumOfConvoMessageUser = 0;

                ConversationMessageBean conversationMessageBean = generateConversationMessageBean( conversationRequestBean,conversationBean );
                if(numOfUserConversations>0){
                    iNumOfConvoMessage = buildConversationData.insertConversationMessage( conversationMessageBean );
                }

                ConversationMessageUserBean conversationMessageUserBean = generateConversationMessageUserBean( conversationRequestBean,conversationMessageBean );
                if(iNumOfConvoMessage>0){
                    iNumOfConvoMessageUser = buildConversationData.insertConversationMessageUser( conversationMessageUserBean );
                }

                ArrayList<ConversationMessageAttachment> arrConversationMessageAttachment = generateConversationMessageAttachment(conversationRequestBean , conversationMessageBean );
                Integer iNumOfAttachmentRowsInserted = buildConversationData.insertConversationMessageAttachment(arrConversationMessageAttachment);

                if(iNumOfConvoMessage>0 && iNumOfConvoMessage>0 && iNumOfConvoMessageUser>0 ) {

                    AccessConversation accessConversation = new AccessConversation();
                    conversationMessageUserBean.setFormattedHumanCreateDate( accessConversation.getFormattedInputDate( conversationMessageUserBean.getCreateDate() , conversationRequestBean ) );
                    conversationMessageUserBean.setUserGivenName( accessConversation.getGivenNameOfUser(conversationMessageUserBean.getUserId()) );

                    if(iNumOfAttachmentRowsInserted>0){
                        UploadFile uploadFile = new UploadFile();
                        ArrayList<UploadBean> arrUploadBean = uploadFile.getUploadFileList( conversationRequestBean.getArrUploadId() );
                        if(arrUploadBean!=null && !arrUploadBean.isEmpty()){
                            conversationResponseBean.setArrUploadBean( arrUploadBean);
                        }
                    }

                    conversationResponseBean.setConversationBean( conversationBean );
                    conversationResponseBean.setArrUserConversationBean( arrUserConversationBean );
                    conversationResponseBean.setConversationMessageBean( conversationMessageBean );
                    conversationResponseBean.setConversationMessageUserBean( conversationMessageUserBean );

                    createNotifications(conversationRequestBean, notificationMessage);
                    createEmailNotification(conversationRequestBean);

                }
            }
        }
        return conversationResponseBean;
    }
    public void createNotifications(ConversationRequestBean conversationRequestBean, String sMessage){
        if(conversationRequestBean!=null && !Utility.isNullOrEmpty(conversationRequestBean.getCurrentUserId()) &&
                conversationRequestBean.getArrConversationUserId()!=null && conversationRequestBean.getArrConversationUserId().size()>1) {

            ArrayList<String> arrConversationUserId = conversationRequestBean.getArrConversationUserId();
            String sFromUserId = conversationRequestBean.getCurrentUserId();
            if(arrConversationUserId!=null) {
                for(String sToUserId : arrConversationUserId ){
                    if(sFromUserId.equalsIgnoreCase(sToUserId)) {
                        continue; // do not set To Do notification to self
                    }
                    NotifyBean notifyBean = new NotifyBean();
                    notifyBean.setFrom( sFromUserId );
                    notifyBean.setTo( sToUserId );
                    notifyBean.setMessage( sMessage );

                    Notification.createNewNotifyRecord(notifyBean);
                }
            }
        }
    }

    public boolean createEmailNotification(ConversationRequestBean conversationRequestBean){
        boolean isSuccess = false;
        if(conversationRequestBean!=null && !Utility.isNullOrEmpty(conversationRequestBean.getConversationId())  ) {
            String sUserId = conversationRequestBean.getCurrentUserId();
            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setUserId( sUserId);
            AccessUsers accessUsers = new AccessUsers();
            UserBean fromUserBean = accessUsers.getUserById(userRequestBean);
            UserInfoBean fromUserInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );

            EmailServiceData emailServiceData = new EmailServiceData();
            EmailTemplateBean emailTemplateBean = emailServiceData.getEmailTemplate(Constants.EMAIL_TEMPLATE.CONVERSATION_MESSAGE);

            String sHtmlTemplate = emailTemplateBean.getHtmlBody();
            String sTxtTemplate = emailTemplateBean.getTextBody();

            ArrayList<String> arrConversationUserId = conversationRequestBean.getArrConversationUserId();
            if(arrConversationUserId!=null && !arrConversationUserId.isEmpty() ) {
                for(String sToUserId : arrConversationUserId ){
                    userRequestBean.setUserId( sToUserId);

                    UserInfoBean toUserInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );

                    EmailQueueBean emailQueueBean = new EmailQueueBean();
                    emailQueueBean.setEmailSubject(emailTemplateBean.getEmailSubject());
                    emailQueueBean.setFromAddress(emailTemplateBean.getFromAddress());
                    emailQueueBean.setFromAddressName(emailTemplateBean.getFromAddressName());

                    String sFromFirstName = ParseUtil.checkNull(fromUserInfoBean.getFirstName());
                    String sFromLastName = ParseUtil.checkNull(fromUserInfoBean.getLastName());
                    String sFromGivenName = ParseUtil.checkNull( sFromFirstName + " " + sFromLastName);

                    String sToFirstName = ParseUtil.checkNull(toUserInfoBean.getFirstName());
                    String sToLastName = ParseUtil.checkNull(toUserInfoBean.getLastName());
                    String sToGivenName = ParseUtil.checkNull( sToFirstName + " " + sToLastName);

                    emailQueueBean.setToAddress( toUserInfoBean.getEmail() );
                    emailQueueBean.setToAddressName(toUserInfoBean.getEmail() );
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
                    mapTextEmailValues.put("USER_GIVEN_NAME",sToGivenName);
                    mapHtmlEmailValues.put("USER_GIVEN_NAME", sToGivenName);
                    mapTextEmailValues.put("CONVERSATION_FROM",sFromGivenName);
                    mapHtmlEmailValues.put("CONVERSATION_FROM", sFromGivenName);
                    mapTextEmailValues.put("CONVERSATION_TITLE",conversationRequestBean.getConversationName());
                    mapHtmlEmailValues.put("CONVERSATION_TITLE", conversationRequestBean.getConversationName());

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
        }
        return isSuccess;
    }

    public Integer addUserToConversation(ConversationRequestBean conversationRequestBean){
        Integer numOfUserConversations = 0;
        if(conversationRequestBean!=null && !Utility.isNullOrEmpty(conversationRequestBean.getConversationId())) {
            BuildConversationData buildConversationData = new BuildConversationData();

            ConversationBean conversationBean = new ConversationBean();
            conversationBean.setConversationId( conversationRequestBean.getConversationId() );
            if(conversationBean!=null && !Utility.isNullOrEmpty(conversationBean.getConversationId())) {
                ArrayList<UserConversationBean> arrUserConversationBean = generateArrUserConversationBean( conversationRequestBean , conversationBean );

                numOfUserConversations = buildConversationData.insertUserConversation(  arrUserConversationBean  );
            }
        }
        return numOfUserConversations;
    }

    public Integer deleteUserFromConversation(ConversationRequestBean conversationRequestBean){
        Integer numOfUserConversations = 0;
        if(conversationRequestBean!=null && !Utility.isNullOrEmpty(conversationRequestBean.getConversationId())) {
            BuildConversationData buildConversationData = new BuildConversationData();

            ConversationBean conversationBean = new ConversationBean();
            conversationBean.setConversationId( conversationRequestBean.getConversationId() );
            if(conversationBean!=null && !Utility.isNullOrEmpty(conversationBean.getConversationId())) {
                ArrayList<UserConversationBean> arrUserConversationBean = generateArrUserConversationBean( conversationRequestBean , conversationBean );

                numOfUserConversations = buildConversationData.deleteUserFromConversation(conversationRequestBean);
            }
        }
        return numOfUserConversations;
    }

    public ConversationBean createConversation(ConversationRequestBean conversationRequestBean){
        conversationRequestBean.setConversationId( Utility.getNewGuid() );

        ConversationBean conversationBean = generateConversationBean(conversationRequestBean);
        BuildConversationData buildConversationData = new BuildConversationData();
        Integer numOfRowsInserted = buildConversationData.insertConversation( conversationBean );
        if(numOfRowsInserted<=0){
            conversationBean = new ConversationBean();
        }
        return conversationBean;
    }

    public ConversationBean updateConversation(ConversationRequestBean conversationRequestBean){
        ConversationBean conversationBean = generateConversationBean(conversationRequestBean);
        BuildConversationData buildConversationData = new BuildConversationData();
        Integer numOfRowsInserted = buildConversationData.updateConversation(conversationBean);
        if(numOfRowsInserted<=0){
            conversationBean = new ConversationBean();
        }
        return conversationBean;
    }

    public boolean deleteAllUserConversation(  ConversationRequestBean conversationRequestBean  ){
        boolean isDeletedSuccess = false;
        if(conversationRequestBean!=null){
            BuildConversationData buildConversationData = new BuildConversationData();
            buildConversationData.deleteAllUserConversations(conversationRequestBean);
        }
        return isDeletedSuccess;
    }

    private ConversationBean generateConversationBean(ConversationRequestBean conversationRequestBean){
        ConversationBean conversationBean = new ConversationBean();
        if(conversationRequestBean!=null){
            conversationBean.setConversationId( conversationRequestBean.getConversationId() );
            conversationBean.setName( conversationRequestBean.getConversationName() );
        }
        return conversationBean;
    }

    private ArrayList<UserConversationBean> generateArrUserConversationBean(ConversationRequestBean conversationRequestBean, ConversationBean conversationBean){
        ArrayList<UserConversationBean> arrUserConversationBean = new ArrayList<UserConversationBean>();
        if(conversationRequestBean!=null && conversationBean!=null){
            ArrayList<String> arrConversationUserId = conversationRequestBean.getArrConversationUserId();
            if(arrConversationUserId!=null && !arrConversationUserId.isEmpty()){
                for(String sUserId : arrConversationUserId) {
                    UserConversationBean userConversationBean = new UserConversationBean();
                    userConversationBean.setConversationId( conversationBean.getConversationId() );
                    userConversationBean.setUserConversationId( Utility.getNewGuid() );
                    userConversationBean.setUserId( sUserId );
                    if(conversationRequestBean.getCurrentUserId().equalsIgnoreCase( sUserId) ) {
                        userConversationBean.setRead(  true );
                    } else {
                        userConversationBean.setRead( false  );
                    }
                    arrUserConversationBean.add( userConversationBean );
                }
            }
        }
        return arrUserConversationBean;
    }

    private ConversationMessageBean generateConversationMessageBean(  ConversationRequestBean conversationRequestBean , ConversationBean conversationBean ){
        ConversationMessageBean conversationMessageBean = new ConversationMessageBean();
        if(conversationRequestBean!=null && conversationBean!=null){
            conversationMessageBean.setConversationMessageId(Utility.getNewGuid());
            conversationMessageBean.setConversationId(conversationBean.getConversationId());
            conversationMessageBean.setMessage( conversationRequestBean.getConversationBody() );
        }
        return conversationMessageBean;
    }

    private ConversationMessageUserBean generateConversationMessageUserBean( ConversationRequestBean conversationRequestBean , ConversationMessageBean conversationMessageBean ) {
        ConversationMessageUserBean conversationMessageUserBean = new ConversationMessageUserBean();
        if(conversationRequestBean!=null && conversationMessageBean!=null){
            conversationMessageUserBean.setUserId(conversationRequestBean.getCurrentUserId());
            conversationMessageUserBean.setConversationMessageUserId(Utility.getNewGuid());
            conversationMessageUserBean.setConversationMessageId( conversationMessageBean.getConversationMessageId() );
            conversationMessageUserBean.setCreateDate(DateSupport.getEpochMillis());
            conversationMessageUserBean.setHumanCreateDate( DateSupport.getUTCDateTime() );
        }
        return conversationMessageUserBean;
    }

    private ArrayList<ConversationMessageAttachment> generateConversationMessageAttachment( ConversationRequestBean conversationRequestBean, ConversationMessageBean conversationMessageBean ) {
        ArrayList<ConversationMessageAttachment> arrConversationMessageAttachment = new ArrayList<ConversationMessageAttachment>();

        if(conversationRequestBean!=null && conversationMessageBean!=null){
            ArrayList<String> arrUploadId = conversationRequestBean.getArrUploadId();
            if(arrUploadId!=null && !arrUploadId.isEmpty()){
                for(String sUploadId : arrUploadId) {
                    ConversationMessageAttachment conversationMessageAttachment = new ConversationMessageAttachment();
                    conversationMessageAttachment.setConversationMessageAttachementId( Utility.getNewGuid() );
                    conversationMessageAttachment.setConversationMessageId( conversationMessageBean.getConversationMessageId() );
                    conversationMessageAttachment.setConversationId( conversationMessageBean.getConversationId() );
                    conversationMessageAttachment.setUploadId( sUploadId );

                    arrConversationMessageAttachment.add( conversationMessageAttachment ) ;
                }
            }
        }
        return arrConversationMessageAttachment;
    }
}
