package com.events.data.comments;

import com.events.bean.common.comments.CommentsBean;
import com.events.bean.common.conversation.ConversationBean;
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
 * Date: 8/26/14
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildCommentsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertComment( CommentsBean commentsBean ){
        Integer numOfRowsInserted = 0;
        if(commentsBean!=null){
            // COMMENTSID  VARCHAR(45) NOT NULL, FK_PARENTID  VARCHAR(45) NOT NULL, FK_USERID   VARCHAR(45) NOT NULL, COMMENT TEXT NOT NULL , CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE VARCHAR(45)
            String sQuery = "INSERT INTO GTCOMMENTS (COMMENTSID,FK_PARENTID,FK_USERID,   COMMENT,CREATEDATE,HUMANCREATEDATE ) VALUES (?,?,?,   ?,?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(commentsBean.getCommentsId(), commentsBean.getParentId(),commentsBean.getUserId(),
                    commentsBean.getComment(),commentsBean.getCreateDate(), commentsBean.getHumanCreateDate());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildCommentsData.java", "insertComment() ");
        }
        return numOfRowsInserted;
    }
}
