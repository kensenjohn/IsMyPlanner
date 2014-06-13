package com.events.common.conversation;

import com.events.bean.common.conversation.*;
import com.events.bean.upload.UploadBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.conversation.AccessConversationData;
import com.events.data.conversation.BuildConversationData;
import com.events.users.AccessUsers;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/2/14
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessConversation {
    public ConversationResponseBean loadConversation(ConversationRequestBean conversationRequestBean){
        ConversationResponseBean conversationResponseBean = new ConversationResponseBean();
        if(conversationRequestBean!=null){
            AccessConversationData accessConversationData = new AccessConversationData();
            ConversationBean conversationBean =  accessConversationData.getConversation(conversationRequestBean);

            conversationBean.setFormattedHumanModifiedDate(  getFormattedInputDate(conversationBean.getModifiedDate(),conversationRequestBean)   );
            conversationResponseBean.setConversationBean( conversationBean );


            ArrayList<ConversationBean> arrConversationBean = new ArrayList<ConversationBean>();
            arrConversationBean.add( conversationBean );

            HashMap<String, ArrayList<UserConversationBean> > hmUserConversationBean = accessConversationData.getConversationUsers( arrConversationBean );

            if(hmUserConversationBean!=null && !hmUserConversationBean.isEmpty()){
                ArrayList<UserConversationBean> arrUserConversationBean = hmUserConversationBean.get( conversationBean.getConversationId() );

                BuildConversationData buildConversationData = new BuildConversationData();
                buildConversationData.updateMarkUserConversationReadStatus(arrUserConversationBean, true);
                conversationResponseBean.setArrUserConversationBean( arrUserConversationBean );
            }

            HashMap<Long, ConversationMessageBean > hmConversationMessageBean = accessConversationData.getConversationMessages( conversationRequestBean );
            if(hmConversationMessageBean!=null && !hmConversationMessageBean.isEmpty() ) {
                HashMap<String, ArrayList<UploadBean> > hmConversationMessageAttachment = accessConversationData.getArrMessageAttachmentsUploadBeans(hmConversationMessageBean);
                conversationResponseBean.setHmConversationMessageAttachment( hmConversationMessageAttachment );
            }
            HashMap<String, ConversationMessageUserBean >  hmConversationMessageUserBean = accessConversationData.getConversationMessageUser( hmConversationMessageBean );

            if(hmConversationMessageUserBean!=null && !hmConversationMessageUserBean.isEmpty() ) {
                for(Map.Entry<String, ConversationMessageUserBean > mapConversationMessageUserBean : hmConversationMessageUserBean.entrySet() ){
                    ConversationMessageUserBean conversationMessageUserBean = mapConversationMessageUserBean.getValue();
                    String sUserId = ParseUtil.checkNull(conversationMessageUserBean.getUserId());
                    Long lMessageCreateDate = conversationMessageUserBean.getCreateDate();

                    conversationMessageUserBean.setUserGivenName( getGivenNameOfUser(sUserId) );
                    conversationMessageUserBean.setFormattedHumanCreateDate( getFormattedInputDate(lMessageCreateDate,conversationRequestBean) );
                }
            }

            conversationResponseBean.setHmConversationMessageBean( hmConversationMessageBean );
            conversationResponseBean.setHmConversationMessageUserBean( hmConversationMessageUserBean );
        }
        return conversationResponseBean;
    }
    public String getFormattedInputDate( Long lInputDate ,ConversationRequestBean conversationRequestBean ){
        String sFormattedModifiedDate = Constants.EMPTY;
        if(lInputDate>0 && conversationRequestBean!=null) {
            sFormattedModifiedDate = DateSupport.getTimeByZone( lInputDate , conversationRequestBean.getTimeZone(), Constants.PRETTY_DATE_PATTERN_1  );
        }
        return sFormattedModifiedDate;
    }

    public String getGivenNameOfUser( String sUserId){
        String sGivenName = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(sUserId)) {
            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setUserId( sUserId );

            AccessUsers accessUsers = new AccessUsers();
            UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );

            sGivenName = ParseUtil.checkNull(  ParseUtil.checkNull(userInfoBean.getFirstName()) + " " + ParseUtil.checkNull(userInfoBean.getLastName())  );
            if(Utility.isNullOrEmpty(sGivenName)){
                sGivenName =  ParseUtil.checkNull(userInfoBean.getEmail());
            }
        }
        return sGivenName;
    }

    public ConversationResponseBean loadAllUserConversations(ConversationRequestBean conversationRequestBean){
        ConversationResponseBean conversationResponseBean = new ConversationResponseBean();

        ArrayList<ConversationBean> arrConversationBean = new ArrayList<ConversationBean>();
        if(conversationRequestBean!=null){
            AccessConversationData accessConversationData = new AccessConversationData();
            /*if(conversationRequestBean!=null && conversationRequestBean.isCanManageEveryConversation() ) {
                arrConversationBean =  accessConversationData.getConversationByVendor(conversationRequestBean);
            } else {
                arrConversationBean =  accessConversationData.getConversationByUser(conversationRequestBean);
            }*/
            arrConversationBean =  accessConversationData.getConversationByUser(conversationRequestBean);
            if(arrConversationBean!=null && !arrConversationBean.isEmpty()){

                conversationResponseBean.setArrConversationBean( arrConversationBean );


                for(ConversationBean tmpConversationBean : arrConversationBean ){

                    tmpConversationBean.setFormattedHumanModifiedDate( getFormattedInputDate(  tmpConversationBean.getModifiedDate(),conversationRequestBean )   );
                }

                HashMap<String, ArrayList<UserConversationBean> > hmUserConversationBean = accessConversationData.getConversationUsers( arrConversationBean );

                if(hmUserConversationBean!=null && !hmUserConversationBean.isEmpty()){
                    HashMap<String, ArrayList<String> > hmUserNames = new HashMap<String, ArrayList<String>>();

                    HashMap<String, String> hmUserNameCache = new HashMap<String, String>();

                    for(Map.Entry<String, ArrayList<UserConversationBean> > mapUserConversationBean : hmUserConversationBean.entrySet() ){
                        ArrayList<String> arrUserNames = new ArrayList<String>();

                        ArrayList<UserConversationBean> arrUserConversationBean = mapUserConversationBean.getValue();

                        if(arrUserConversationBean!=null && !arrUserConversationBean.isEmpty() ) {
                            for( UserConversationBean userConversationBean : arrUserConversationBean ){
                                String sUserId = ParseUtil.checkNull( userConversationBean.getUserId() );
                                String sGivenName = ParseUtil.checkNull(hmUserNameCache.get(sUserId));
                                if( !Utility.isNullOrEmpty(sUserId) && Utility.isNullOrEmpty(sGivenName) ){
                                    sGivenName =  getGivenNameOfUser( sUserId);
                                    hmUserNameCache.put( sUserId, sGivenName );
                                }
                                arrUserNames.add( sGivenName );
                            }
                        }
                        hmUserNames.put( mapUserConversationBean.getKey() , arrUserNames );
                    }
                    conversationResponseBean.setHmUserNames(hmUserNames);

                }
            }
        }
        return conversationResponseBean;
    }

    public JSONObject getJsonConversations( ArrayList<ConversationBean> arrConversationBean ){
        JSONObject jsonUserConversation = new JSONObject();
        Long lNumOfConversations = 0L;
        if( arrConversationBean!=null && !arrConversationBean.isEmpty() ){
            for(ConversationBean conversationBean : arrConversationBean ){
                jsonUserConversation.put(ParseUtil.LToS(lNumOfConversations) , conversationBean.toJson() );
                lNumOfConversations++;
            }
        }
        jsonUserConversation.put("num_of_conversations", lNumOfConversations );
        return jsonUserConversation;
    }

    public JSONObject getJsonConversationUsers( ArrayList<UserConversationBean> arrConversationBean ){
        JSONObject jsonConversationUsers = new JSONObject();
        if(arrConversationBean!=null && !arrConversationBean.isEmpty()) {
            Long lNumOfUserConversation = 0L;
            for( UserConversationBean userConversationBean : arrConversationBean ){
                jsonConversationUsers.put( ParseUtil.LToS(lNumOfUserConversation), userConversationBean.toJson() );
                lNumOfUserConversation++;
            }
            jsonConversationUsers.put("num_of_conversation_users", lNumOfUserConversation );
        }
        return jsonConversationUsers;
    }

    public JSONObject getJsonUserConversations( HashMap<String, ArrayList<String>> hmUserNames ){
        JSONObject jsonAllUserConversation = new JSONObject();
        if( hmUserNames!=null && !hmUserNames.isEmpty() ) {
            for( Map.Entry<String, ArrayList<String>> mapUserNames : hmUserNames.entrySet() ){
                String sConversionId = mapUserNames.getKey();

                ArrayList<String> arrUserNames = mapUserNames.getValue();
                if( arrUserNames!=null && !arrUserNames.isEmpty() ) {
                    Integer iNumOfUserNames = 0;
                    JSONObject jsonUserNames = new JSONObject();
                    for(String userName : arrUserNames ) {
                        jsonUserNames.put( ParseUtil.iToS(iNumOfUserNames) , userName );
                        iNumOfUserNames++;
                    }
                    jsonAllUserConversation.put(sConversionId, jsonUserNames );
                    jsonAllUserConversation.put("users_per_conversation_"+sConversionId, iNumOfUserNames );
                }
            }
        }
        return jsonAllUserConversation;
    }

    /*
    HashMap<Long, ConversationMessageBean > hmConversationMessageBean = accessConversationData.getConversationMessages( conversationRequestBean );
            HashMap<String, ConversationMessageUserBean >  hmConversationMessageUserBean = accessConversationData.getConversationMessageUser( hmConversationMessageBean );
     */
    public JSONObject getJsonConversationMessageBean(HashMap<Long, ConversationMessageBean > hmConversationMessageBean){
        JSONObject jsonConversationMessageBean = new JSONObject();
        Long lNumOfMessages = 0L;
        if(hmConversationMessageBean!=null && !hmConversationMessageBean.isEmpty()){
            for(Map.Entry<Long,ConversationMessageBean> mapConversationMessageBean : hmConversationMessageBean.entrySet() ) {
                jsonConversationMessageBean.put(ParseUtil.LToS(mapConversationMessageBean.getKey()), mapConversationMessageBean.getValue().toJson());
                lNumOfMessages++;
            }
        }
        jsonConversationMessageBean.put("num_of_conversation_messages",lNumOfMessages);
        return jsonConversationMessageBean;
    }

    public JSONObject getJsonConversationMessageUserBean(HashMap<String, ConversationMessageUserBean > hmConversationMessageUserBean){
        JSONObject jsonConversationMessageUserBean = new JSONObject();
        if(hmConversationMessageUserBean!=null && !hmConversationMessageUserBean.isEmpty()){
            for(Map.Entry<String,ConversationMessageUserBean> mapConversationMessageUserBean : hmConversationMessageUserBean.entrySet() ) {
                jsonConversationMessageUserBean.put(mapConversationMessageUserBean.getKey(), mapConversationMessageUserBean.getValue().toJson());
            }
        }
        return jsonConversationMessageUserBean;
    }

    public JSONObject getJsonConversationMessageAttachments(HashMap<String, ArrayList<UploadBean> > hmConversationMessageAttachments){
        JSONObject jsonMessageAttachments = new JSONObject();
        if(hmConversationMessageAttachments!=null && !hmConversationMessageAttachments.isEmpty()){
            for(Map.Entry<String, ArrayList<UploadBean> > mapMessageAttachmentUploadBean : hmConversationMessageAttachments.entrySet() ) {
                String sMessageId  = mapMessageAttachmentUploadBean.getKey();
                ArrayList<UploadBean> arrUploadBean = mapMessageAttachmentUploadBean.getValue();
                Long lNumOfUploads = 0L;
                if(arrUploadBean!=null && !arrUploadBean.isEmpty()){

                    JSONObject jsonUploadBean = new JSONObject();
                    for(UploadBean uploadBean : arrUploadBean ) {
                        jsonUploadBean.put(ParseUtil.LToS(lNumOfUploads) , uploadBean.toJson()) ;
                        lNumOfUploads++;
                    }
                    jsonMessageAttachments.put(sMessageId,jsonUploadBean );
                }
                jsonMessageAttachments.put("num_of_attachments_"+sMessageId, lNumOfUploads );
            }
        }
        return jsonMessageAttachments;
    }
}
