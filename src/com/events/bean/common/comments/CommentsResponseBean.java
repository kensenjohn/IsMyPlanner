package com.events.bean.common.comments;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 8/26/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommentsResponseBean {

    private CommentsBean commentsBean = new CommentsBean();
    private HashMap<Long,CommentsBean> hmCommentsBean = new HashMap<Long, CommentsBean>();
    private Long lNumberOfComments = 0L;

    public CommentsBean getCommentsBean() {
        return commentsBean;
    }

    public void setCommentsBean(CommentsBean commentsBean) {
        this.commentsBean = commentsBean;
    }

    public HashMap<Long, CommentsBean> getHmCommentsBean() {
        return hmCommentsBean;
    }

    public void setHmCommentsBean(HashMap<Long, CommentsBean> hmCommentsBean) {
        this.hmCommentsBean = hmCommentsBean;
    }

    public Long getlNumberOfComments() {
        return lNumberOfComments;
    }

    public void setlNumberOfComments(Long lNumberOfComments) {
        this.lNumberOfComments = lNumberOfComments;
    }
}
