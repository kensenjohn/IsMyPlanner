package com.events.common.conversation;

import com.events.bean.common.conversation.*;
import com.events.bean.upload.UploadBean;
import com.events.common.DateSupport;
import com.events.common.UploadFile;
import com.events.common.Utility;
import com.events.data.conversation.BuildConversationData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/2/14
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildConversation {
    public ConversationResponseBean saveConversation(ConversationRequestBean conversationRequestBean){
        ConversationResponseBean conversationResponseBean = new ConversationResponseBean();
        if( conversationRequestBean!=null ) {
            ConversationBean conversationBean = new ConversationBean();
            if(Utility.isNullOrEmpty(conversationRequestBean.getConversationId())) {
                conversationBean = createConversation(conversationRequestBean);
            } else {
                conversationBean = updateConversation(conversationRequestBean);
            }

            if(conversationBean!=null && !Utility.isNullOrEmpty(conversationBean.getConversationId())) {
                ArrayList<UserConversationBean> arrUserConversationBean = generateArrUserConversationBean( conversationRequestBean , conversationBean );
                deleteUserConversation( conversationRequestBean );

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

                }
            }
        }
        return conversationResponseBean;
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

    public boolean deleteUserConversation(  ConversationRequestBean conversationRequestBean  ){
        boolean isDeletedSuccess = false;
        if(conversationRequestBean!=null){
            BuildConversationData buildConversationData = new BuildConversationData();
            buildConversationData.deleteUserConversation( conversationRequestBean );
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
