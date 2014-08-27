package com.events.bean.common.comments;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 8/26/14
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommentsBean {

    //COMMENTSID  VARCHAR(45) NOT NULL, FK_PARENTID  VARCHAR(45) NOT NULL, FK_USERID   VARCHAR(45) NOT NULL, CREATEDATE  BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE
    private String commentsId = Constants.EMPTY;
    private String parentId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String formattedHumanCreateDate = Constants.EMPTY;
    private String comment = Constants.EMPTY;
    private String userName = Constants.EMPTY;

    public CommentsBean() {
    }

    public CommentsBean(HashMap<String,String> hmResult) {
        this.commentsId =  ParseUtil.checkNull(hmResult.get("COMMENTSID"));
        this.parentId =  ParseUtil.checkNull(hmResult.get("FK_PARENTID"));
        this.userId =  ParseUtil.checkNull(hmResult.get("FK_USERID"));
        this.createDate =  ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate =  ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
        this.comment =  ParseUtil.checkNull(hmResult.get("COMMENT"));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getFormattedHumanCreateDate() {
        return formattedHumanCreateDate;
    }

    public void setFormattedHumanCreateDate(String formattedHumanCreateDate) {
        this.formattedHumanCreateDate = formattedHumanCreateDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentsBean{");
        sb.append("commentsId='").append(commentsId).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", formattedHumanCreateDate='").append(formattedHumanCreateDate).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comments_id", this.commentsId );
            jsonObject.put("parent_id", this.parentId );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("comment", this.comment );
            jsonObject.put("formatted_human_create_date", this.formattedHumanCreateDate );
            jsonObject.put("user_name", this.userName );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
