package com.events.common.comments;

import com.events.bean.common.comments.CommentsBean;
import com.events.bean.common.comments.CommentsRequestBean;
import com.events.bean.common.comments.CommentsResponseBean;
import com.events.bean.common.conversation.ConversationResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.comments.AccessCommentsData;
import com.events.users.AccessUsers;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 8/26/14
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessComments {

    public CommentsResponseBean loadComments( CommentsRequestBean commentsRequestBean) {
        CommentsResponseBean commentsResponseBean = new CommentsResponseBean();
        if(commentsRequestBean!=null){
            AccessCommentsData accessCommentsData = new AccessCommentsData();
            HashMap<Long,CommentsBean> hmCommentsBean = accessCommentsData.getCommentsByParent(commentsRequestBean);

            if(hmCommentsBean!=null){

                AccessUsers accessUsers = new AccessUsers();
                BuildComments buildComments = new BuildComments();
                for(Map.Entry<Long,CommentsBean> mapCommentsBean : hmCommentsBean.entrySet() ) {
                    CommentsBean commentsBean = mapCommentsBean.getValue();

                    String sUserId = commentsBean.getUserId();

                    UserRequestBean userRequestBean = new UserRequestBean();
                    userRequestBean.setUserId( sUserId );

                    UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId(userRequestBean) ;


                    if( userInfoBean!=null ) {
                        if( !Utility.isNullOrEmpty(userInfoBean.getEmail())) {
                            commentsBean.setUserName( userInfoBean.getEmail() );
                        }
                        if( !Utility.isNullOrEmpty(userInfoBean.getTimezone())) {
                            String sTimeZone = Constants.TIME_ZONE.valueOf(ParseUtil.checkNull( userInfoBean.getTimezone() )).getJavaTimeZone();

                            CommentsRequestBean userCommentRequestBean = new CommentsRequestBean();
                            userCommentRequestBean.setTimeZone(sTimeZone);

                            commentsBean.setFormattedHumanCreateDate(  buildComments.getFormattedInputDate(commentsBean.getCreateDate(), userCommentRequestBean )  );
                        }

                    }


                }
                commentsResponseBean.setHmCommentsBean( hmCommentsBean );
                commentsResponseBean.setlNumberOfComments(ParseUtil.IToL(hmCommentsBean.size() ));
            }
        }
        return commentsResponseBean;
    }

    public JSONObject getJsonComments( HashMap<Long,CommentsBean> hmCommentsBean ){
        JSONObject jsonComments = new JSONObject();
        Long lNumOfComments = 0L;
        if( hmCommentsBean!=null && !hmCommentsBean.isEmpty() ){
            for(Map.Entry<Long,CommentsBean> mapCommentsBean : hmCommentsBean.entrySet() ){
                jsonComments.put(ParseUtil.LToS(lNumOfComments) , mapCommentsBean.getValue().toJson() );
                lNumOfComments++;
            }
        }
        jsonComments.put("num_of_comments", lNumOfComments );
        return jsonComments;
    }
}
