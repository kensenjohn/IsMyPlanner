package com.events.data.conversation;

import com.events.bean.common.conversation.*;
import com.events.bean.common.todo.AssignedToDoUsersBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/2/14
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildConversationData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    // GTCONVERSATION( CONVERSATIONID VARCHAR(45) NOT NULL, NAME TEXT NOT NULL , CREATEDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANCREATEDATE VARCHAR(45), PRIMARY KEY (CONVERSATIONID) )
    public Integer insertConversation( ConversationBean conversationBean ){
        Integer numOfRowsInserted = 0;
        if(conversationBean!=null){
            String sQuery = "INSERT INTO GTCONVERSATION (CONVERSATIONID,NAME,CREATEDATE,     HUMANCREATEDATE,MODIFIEDDATE,HUMANMODIFIEDDATE ) VALUES (?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationBean.getConversationId(), conversationBean.getName(),DateSupport.getEpochMillis(),
                    DateSupport.getUTCDateTime(),DateSupport.getEpochMillis(),DateSupport.getUTCDateTime());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildConversationData.java", "insertConversation() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateConversation( ConversationBean conversationBean ){
        Integer numOfRowsInserted = 0;
        if(conversationBean!=null){
            String sQuery = "UPDATE GTCONVERSATION SET NAME = ?,MODIFIEDDATE =?,HUMANMODIFIEDDATE =? WHERE     CONVERSATIONID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationBean.getName(), DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),
                    conversationBean.getConversationId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildConversationData.java", "updateConversation() ");
        }
        return numOfRowsInserted;
    }
    public Integer updateMarkUserConversationReadStatus(  ArrayList<UserConversationBean> arrUserConversationBean, boolean isRead ){
        Integer numOfRowsUpdated = 0;
        if(arrUserConversationBean!=null && !arrUserConversationBean.isEmpty() ){
            String sQuery = "UPDATE GTUSERCONVERSATION SET IS_READ = ? WHERE USERCONVERSATIONID in ("+DBDAO.createParamQuestionMarks(arrUserConversationBean.size())+") AND IS_READ = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( isRead?"1":"0");
            for(UserConversationBean userConversationBean : arrUserConversationBean ) {
                aParams.add(userConversationBean.getUserConversationId());
            }
            aParams.add( isRead?"0":"1" );
            numOfRowsUpdated = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildConversationData.java", "updateConversation() ");
        }
        return numOfRowsUpdated;
    }

    // GTUSERCONVERSATION( USERCONVERSATIONID VARCHAR(45) NOT NULL, FK_CONVERSATIONID VARCHAR(45) NOT NULL , FK_USERID VARCHAR(45) NOT NULL,
    public Integer insertUserConversation( ArrayList<UserConversationBean> arrUserConversationBean ){
        Integer numOfRowsInserted = 0;
        if(arrUserConversationBean!=null && !arrUserConversationBean.isEmpty()) {

            String sQuery = "INSERT INTO GTUSERCONVERSATION (USERCONVERSATIONID,FK_CONVERSATIONID,FK_USERID,       IS_READ ) VALUES (?,?,?,         ?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(UserConversationBean userConversationBean : arrUserConversationBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(userConversationBean.getUserConversationId(), userConversationBean.getConversationId(), userConversationBean.getUserId(),
                        userConversationBean.isRead());
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildToDoData.java", "insertUserConversation() " );
            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }

    public Integer deleteUserConversation( ConversationRequestBean conversationRequestBean){
        Integer numOfRowsDeleted = 0;
        if(conversationRequestBean!=null){
            String sQuery = "DELETE FROM GTUSERCONVERSATION WHERE FK_CONVERSATIONID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationRequestBean.getConversationId());
            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildConversationData.java", "deleteUserConversation() ");
        }
        return numOfRowsDeleted;
    }
    // GTCONVOMESSAGE( CONVOMESSAGEID VARCHAR(45) NOT NULL, FK_CONVERSATIONID VARCHAR(45) NOT NULL, MESSAGE TEXT NOT NULL,
    // CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    public Integer insertConversationMessage(ConversationMessageBean conversationMessageBean){
        Integer numOfRowsInserted = 0;
        if(conversationMessageBean!=null){
            String sQuery = "INSERT INTO GTCONVOMESSAGE (CONVOMESSAGEID,FK_CONVERSATIONID,MESSAGE,   CREATEDATE,HUMANCREATEDATE) " +
                    " VALUES (?,?,?,   ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationMessageBean.getConversationMessageId(), conversationMessageBean.getConversationId(), conversationMessageBean.getMessage(),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildConversationData.java", "insertConversationMessage() ");

        }
        return numOfRowsInserted;
    }

    // GTCONVOMESSAGEUSER( CONVOMESSAGEUSERID VARCHAR(45) NOT NULL, FK_CONVOMESSAGEID VARCHAR(45) NOT NULL,
    // FK_USERID VARCHAR(45) NOT NULL, CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45),
    public Integer insertConversationMessageUser(  ConversationMessageUserBean conversationMessageUserBean){
        Integer numOfRowsInserted = 0;
        if(conversationMessageUserBean!=null){
            String sQuery = "INSERT INTO GTCONVOMESSAGEUSER (CONVOMESSAGEUSERID,FK_CONVOMESSAGEID,FK_USERID,   CREATEDATE,HUMANCREATEDATE) " +
                    " VALUES (?,?,?,   ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(conversationMessageUserBean.getConversationMessageUserId(), conversationMessageUserBean.getConversationMessageId(), conversationMessageUserBean.getUserId(),
                    conversationMessageUserBean.getCreateDate(), conversationMessageUserBean.getHumanCreateDate());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildConversationData.java", "insertConversationMessageUser() ");
        }
        return numOfRowsInserted;
    }

    //GTCONVOMESSAGEATTACHMENT( CONVOMESSAGEATTACHMENTID VARCHAR(45) NOT NULL, FK_CONVERSATIONID VARCHAR(45) NOT NULL, FK_CONVOMESSAGEID VARCHAR(45) NOT NULL, FK_UPLOADID VARCHAR(45) NOT NULL,
    public Integer insertConversationMessageAttachment(  ArrayList<ConversationMessageAttachment> arrConversationMessageAttachment ){
        Integer numOfRowsInserted = 0;
        if(arrConversationMessageAttachment!=null && !arrConversationMessageAttachment.isEmpty() ) {
            String sQuery = "INSERT INTO GTCONVOMESSAGEATTACHMENT (CONVOMESSAGEATTACHMENTID,FK_CONVERSATIONID,FK_CONVOMESSAGEID,     FK_UPLOADID) VALUES (?,?,?,    ?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(ConversationMessageAttachment conversationMessageAttachment : arrConversationMessageAttachment ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(conversationMessageAttachment.getConversationMessageAttachementId(), conversationMessageAttachment.getConversationId(), conversationMessageAttachment.getConversationMessageId(),
                        conversationMessageAttachment.getUploadId());
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildConversationData.java", "insertConversationMessageAttachment() " );
            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsInserted = numOfRowsInserted + iCount;
                }
            }
        }
        return numOfRowsInserted;
    }

}
