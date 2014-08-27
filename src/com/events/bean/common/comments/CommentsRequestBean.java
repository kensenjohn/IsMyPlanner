package com.events.bean.common.comments;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 8/26/14
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommentsRequestBean {

    private String commentsId = Constants.EMPTY;
    private String parentId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String comment = Constants.EMPTY;
    private String timeZone = Constants.EMPTY;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(String commentsId) {
        this.commentsId = commentsId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getHumanCreateDate() {
        return humanCreateDate;
    }

    public void setHumanCreateDate(String humanCreateDate) {
        this.humanCreateDate = humanCreateDate;
    }
}
