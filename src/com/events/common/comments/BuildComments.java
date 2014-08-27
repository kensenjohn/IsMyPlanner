package com.events.common.comments;

import com.events.bean.common.comments.CommentsBean;
import com.events.bean.common.comments.CommentsRequestBean;
import com.events.bean.common.comments.CommentsResponseBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.comments.BuildCommentsData;
import com.events.users.AccessUsers;

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


                UserRequestBean userRequestBean = new UserRequestBean();
                userRequestBean.setUserId( commentsRequestBean.getUserId() );

                String sTimeZone  = Constants.TIME_ZONE.central.getJavaTimeZone();
                AccessUsers accessUsers = new AccessUsers();
                UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );
                if(userInfoBean!=null && !Utility.isNullOrEmpty(userInfoBean.getTimezone())) {
                    sTimeZone = Constants.TIME_ZONE.valueOf(ParseUtil.checkNull(userInfoBean.getTimezone())).getJavaTimeZone();
                }

                commentsRequestBean.setTimeZone( sTimeZone );
                commentsBean.setFormattedHumanCreateDate( getFormattedInputDate(  commentsBean.getCreateDate(),commentsRequestBean ) );

            }
        }
        return commentsResponseBean;
    }


    public CommentsBean createComment(CommentsRequestBean commentsRequestBean){
        commentsRequestBean.setCommentsId(Utility.getNewGuid());

        CommentsBean commentsBean = generateCommentBean(commentsRequestBean);
        BuildCommentsData buildCommentsData = new BuildCommentsData();
        Integer numOfRowsInserted = buildCommentsData.insertComment(commentsBean);
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

    public String getFormattedInputDate( Long lInputDate ,CommentsRequestBean commentsRequestBean ){
        String sFormattedInputDate = Constants.EMPTY;
        if(lInputDate>0 && commentsRequestBean!=null) {
            sFormattedInputDate = DateSupport.getTimeByZone(lInputDate, commentsRequestBean.getTimeZone(), Constants.PRETTY_DATE_PATTERN_1);
        }
        return sFormattedInputDate;
    }

    private CommentsBean generateCommentBean(CommentsRequestBean commentsRequestBean){
        CommentsBean commentsBean = new CommentsBean();
        if(commentsRequestBean!=null){
            commentsBean.setCommentsId(commentsRequestBean.getCommentsId());
            commentsBean.setParentId(commentsRequestBean.getParentId());
            commentsBean.setUserId(commentsRequestBean.getUserId());
            commentsBean.setComment(commentsRequestBean.getComment());
            commentsBean.setCreateDate(commentsRequestBean.getCreateDate());
            commentsBean.setHumanCreateDate( commentsRequestBean.getHumanCreateDate() );
        }
        return commentsBean;
    }
}
