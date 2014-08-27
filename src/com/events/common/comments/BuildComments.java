package com.events.common.comments;

import com.events.bean.common.comments.CommentsBean;
import com.events.bean.common.comments.CommentsRequestBean;
import com.events.bean.common.comments.CommentsResponseBean;
import com.events.common.Utility;
import com.events.data.comments.BuildCommentsData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 8/26/14
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildComments {
    public CommentsResponseBean saveComment( CommentsRequestBean commentsRequestBean){
        CommentsResponseBean commentsResponseBean = new CommentsResponseBean();
        if(commentsRequestBean!=null){
            CommentsBean commentsBean = createComment( commentsRequestBean );
            if(commentsBean!=null && !Utility.isNullOrEmpty(commentsBean.getCommentsId())) {
                commentsResponseBean.setCommentsBean( commentsBean );
            }
        }
        return commentsResponseBean;
    }


    public CommentsBean createComment(CommentsRequestBean commentsRequestBean){
        commentsRequestBean.setCommentsId(Utility.getNewGuid());

        CommentsBean commentsBean = generateCommentBean(commentsRequestBean);
        BuildCommentsData buildCommentsData = new BuildCommentsData();
        Integer numOfRowsInserted = buildCommentsData.insertComment( commentsBean );
        if(numOfRowsInserted<=0){
            commentsBean = new CommentsBean();
        }
        return commentsBean;
    }

    /*public ConversationBean updateConversation(ConversationRequestBean conversationRequestBean){
        ConversationBean conversationBean = generateConversationBean(conversationRequestBean);
        BuildConversationData buildConversationData = new BuildConversationData();
        Integer numOfRowsInserted = buildConversationData.updateConversation(conversationBean);
        if(numOfRowsInserted<=0){
            conversationBean = new ConversationBean();
        }
        return conversationBean;
    }*/

    private CommentsBean generateCommentBean(CommentsRequestBean commentsRequestBean){
        CommentsBean commentsBean = new CommentsBean();
        if(commentsRequestBean!=null){
            commentsBean.setCommentsId( commentsRequestBean.getCommentsId() );
            commentsBean.setParentId( commentsRequestBean.getParentId() );
            commentsBean.setUserId( commentsRequestBean.getUserId() );
            commentsBean.setComment( commentsRequestBean.getComment() );
            commentsBean.setCreateDate( commentsRequestBean.getCreateDate() );
        }
        return commentsBean;
    }
}
