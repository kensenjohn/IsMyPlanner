package com.events.data.comments;

import com.events.bean.common.comments.CommentsBean;
import com.events.bean.common.comments.CommentsRequestBean;
import com.events.bean.common.conversation.ConversationBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 8/26/14
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessCommentsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public HashMap<Long,CommentsBean> getCommentsByParent(CommentsRequestBean commentsRequestBean){
        HashMap<Long,CommentsBean> hmCommentsBean = new HashMap<Long, CommentsBean>();
        if(commentsRequestBean!=null && !Utility.isNullOrEmpty(commentsRequestBean.getParentId())){
            String sQuery = "SELECT * FROM GTCOMMENTS GC WHERE GC.FK_PARENTID = ? ORDER BY CREATEDATE";
            ArrayList<Object> aParams = DBDAO.createConstraint(commentsRequestBean.getParentId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessCommentsData.java", "getCommentsByParent()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                Long lNumOfComments = 0L;
                for( HashMap<String, String> hmResult : arrResult ) {
                    CommentsBean commentsBean = new CommentsBean( hmResult );
                    hmCommentsBean.put(lNumOfComments, commentsBean);
                    lNumOfComments++;
                }
            }
        }
        return hmCommentsBean;
    }
}
