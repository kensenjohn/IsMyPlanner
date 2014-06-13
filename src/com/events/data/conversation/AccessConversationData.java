package com.events.data.conversation;

import com.events.bean.common.conversation.*;
import com.events.bean.common.todo.ToDoBean;
import com.events.bean.upload.UploadBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/3/14
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessConversationData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ConversationBean getConversation( ConversationRequestBean conversationRequestBean){
        ConversationBean conversationBean = new ConversationBean();
        if(conversationRequestBean!=null){
            String sQuery = "SELECT * FROM GTCONVERSATION GC WHERE GC.CONVERSATIONID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( conversationRequestBean.getConversationId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessConversationData.java", "getConversation()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    conversationBean = new ConversationBean( hmResult );
                }
            }
        }
        return conversationBean;
    }
    public ArrayList<ConversationBean> getConversationByVendor(ConversationRequestBean conversationRequestBean){
        ArrayList<ConversationBean> arrConversationBean = new ArrayList<ConversationBean>();
        if(conversationRequestBean!=null){
            String sQuery = "SELECT * FROM GTCONVERSATION GC WHERE GC.FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationRequestBean.getVendorId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessConversationData.java", "getConversationByVendor()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ConversationBean conversationBean = new ConversationBean( hmResult );

                    arrConversationBean.add( conversationBean );
                }
            }
        }
        return arrConversationBean;
    }
    public ArrayList<ConversationBean> getConversationByUser( ConversationRequestBean conversationRequestBean){
        ArrayList<ConversationBean> arrConversationBean = new ArrayList<ConversationBean>();
        if(conversationRequestBean!=null){
            String sQuery = "SELECT * FROM GTCONVERSATION GC, GTUSERCONVERSATION GUC WHERE GUC.FK_USERID=? AND GUC.IS_CONVERSATION_HIDDEN = ? AND " +
                    " GUC.IS_CONVERSATION_DELETED = ? AND GC.CONVERSATIONID = GUC.FK_CONVERSATIONID ORDER BY MODIFIEDDATE DESC";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationRequestBean.getCurrentUserId(), (conversationRequestBean.isHiddenConversation()?"1":"0"),
                    (conversationRequestBean.isDeletedConversation()?"1":"0") );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessConversationData.java", "getConversationByUser()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ConversationBean conversationBean = new ConversationBean( hmResult );
                    UserConversationBean userConversationBean = new UserConversationBean ( hmResult );
                    conversationBean.setHasReadMessage( userConversationBean.isRead() );

                    arrConversationBean.add( conversationBean );
                }
            }
        }
        return arrConversationBean;
    }

    public HashMap<String, ArrayList<UserConversationBean> > getConversationUsers( ArrayList<ConversationBean> arrConversationBean ){
        HashMap<String, ArrayList<UserConversationBean> >  hmUserConversationBean = new HashMap<String, ArrayList<UserConversationBean> >();
        if(arrConversationBean!=null && !arrConversationBean.isEmpty()){
            String sQuery = "SELECT * FROM GTUSERCONVERSATION GUC WHERE GUC.FK_CONVERSATIONID IN ("+DBDAO.createParamQuestionMarks(arrConversationBean.size())+")";

            ArrayList<Object> aParams = new ArrayList<Object>();
            for(ConversationBean conversationBean : arrConversationBean) {
                aParams.add( conversationBean.getConversationId() );
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessConversationData.java", "getUserConversation()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    UserConversationBean userConversationBean = new UserConversationBean( hmResult );

                    if(userConversationBean!=null && !Utility.isNullOrEmpty( userConversationBean.getUserId() )){
                        ArrayList<UserConversationBean> arrUserConversationBean = hmUserConversationBean.get( userConversationBean.getConversationId() );

                        if(arrUserConversationBean == null ){
                            arrUserConversationBean = new ArrayList<UserConversationBean>();
                        }

                        arrUserConversationBean.add( userConversationBean );

                        hmUserConversationBean.put(userConversationBean.getConversationId() , arrUserConversationBean );
                    }


                }
            }
        }
        return hmUserConversationBean;
    }

    //GTCONVOMESSAGE( CONVOMESSAGEID VARCHAR(45) NOT NULL, FK_CONVERSATIONID VARCHAR(45) NOT NULL, MESSAGE TEXT NOT NULL,CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    public HashMap<Long, ConversationMessageBean > getConversationMessages( ConversationRequestBean conversationRequestBean){
        HashMap<Long, ConversationMessageBean > hmConversationMessageBean = new HashMap<Long, ConversationMessageBean>();
        ArrayList<ConversationMessageBean> arrConversationMessageBean = new ArrayList<ConversationMessageBean>();
        if(conversationRequestBean!=null && !Utility.isNullOrEmpty(conversationRequestBean.getConversationId())){
            String sQuery = "SELECT * FROM GTCONVOMESSAGE WHERE FK_CONVERSATIONID = ?  ORDER BY CREATEDATE ASC";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationRequestBean.getConversationId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessConversationData.java", "getConversationMessages()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                Long lMessageCount = 0L;
                for(Integer lTmpTrack = 0; lTmpTrack<arrResult.size(); lTmpTrack++ ){
                    ConversationMessageBean conversationMessageBean = new ConversationMessageBean(  arrResult.get(lTmpTrack) );
                    hmConversationMessageBean.put(lMessageCount,conversationMessageBean);
                    lMessageCount++;
                }
            }
        }

        return hmConversationMessageBean;
    }

    //GTCONVOMESSAGEUSER( CONVOMESSAGEUSERID VARCHAR(45) NOT NULL, FK_CONVOMESSAGEID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL, CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    public HashMap<String, ConversationMessageUserBean > getConversationMessageUser( HashMap<Long, ConversationMessageBean > hmConversationMessageBean ){
        HashMap<String, ConversationMessageUserBean >  hmConversationMessageUserBean = new HashMap<String, ConversationMessageUserBean >();
        if(hmConversationMessageBean!=null && !hmConversationMessageBean.isEmpty()){
            String sQuery = "SELECT * FROM GTCONVOMESSAGEUSER WHERE FK_CONVOMESSAGEID IN ("+DBDAO.createParamQuestionMarks(hmConversationMessageBean.size())+")";

            ArrayList<Object> aParams = new ArrayList<Object>();
            for(Map.Entry<Long,ConversationMessageBean > mapConversationMessageBean : hmConversationMessageBean.entrySet() ){
                aParams.add(  mapConversationMessageBean.getValue().getConversationMessageId() );
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessConversationData.java", "getConversationMessageUser()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ConversationMessageUserBean conversationMessageUserBean = new ConversationMessageUserBean( hmResult );
                    hmConversationMessageUserBean.put(conversationMessageUserBean.getConversationMessageId() , conversationMessageUserBean );
                }
            }
        }
        return hmConversationMessageUserBean;
    }

    public HashMap<String, ArrayList<UploadBean> > getArrMessageAttachmentsUploadBeans ( HashMap<Long, ConversationMessageBean > hmConversationMessageBean  ) {
        HashMap<String, ArrayList<UploadBean> > hmConversationMessageAttachment = new HashMap<String, ArrayList<UploadBean>>();
        if(hmConversationMessageBean!=null && !hmConversationMessageBean.isEmpty()){
            String sQuery = "SELECT * FROM GTCONVOMESSAGEATTACHMENT GA, GTUPLOADS GU WHERE GA.FK_CONVOMESSAGEID IN ("+DBDAO.createParamQuestionMarks(hmConversationMessageBean.size())+") AND " +
                    " GA.FK_UPLOADID = GU.UPLOADID";
            ArrayList<Object> aParams = new ArrayList<Object>();
            for(Map.Entry<Long,ConversationMessageBean > mapConversationMessageBean : hmConversationMessageBean.entrySet() ){
                aParams.add(  mapConversationMessageBean.getValue().getConversationMessageId() );
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessConversationData.java", "getArrMessageAttachmentsUploadBeans()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    ConversationMessageAttachment conversationMessageAttachment = new ConversationMessageAttachment(hmResult);
                    if(conversationMessageAttachment!=null && !Utility.isNullOrEmpty(conversationMessageAttachment.getConversationMessageAttachementId())) {
                        String sConversationMessageId = conversationMessageAttachment.getConversationMessageId();
                        UploadBean uploadBean = new UploadBean( hmResult );

                        ArrayList<UploadBean> arrUploadBean = hmConversationMessageAttachment.get( sConversationMessageId );
                        if(arrUploadBean==null){
                            arrUploadBean = new ArrayList<UploadBean>();
                        }
                        arrUploadBean.add(uploadBean);

                        hmConversationMessageAttachment.put( sConversationMessageId , arrUploadBean  );

                    }
                }
            }
        }
        return hmConversationMessageAttachment;
    }
}
