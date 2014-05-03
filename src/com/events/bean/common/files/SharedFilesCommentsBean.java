package com.events.bean.common.files;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/29/14
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharedFilesCommentsBean {
    //GTSHAREDFILESCOMMENTS(  SHAREDFILESCOMMENTSID VARCHAR(45) PRIMARY KEY NOT NULL,  COMMENT TEXT NOT NULL,
    // FROM_FK_USERID VARCHAR(45) NOT NULL,  FK_SHAREDFILESGROUPID VARCHAR(45) NOT NULL,  CREATEDATE BIGINT(20) DEFAULT 0 NOT NULL,
    // HUMANCREATEDATE VARCHAR(45) NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String sharedFilesCommentsId = Constants.EMPTY;
    private String comment = Constants.EMPTY;
    private String fromUserId = Constants.EMPTY;
    private String sharedFilesGroupId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String formattedCreateDate = Constants.EMPTY;

    public SharedFilesCommentsBean() {
    }

    public SharedFilesCommentsBean(HashMap<String,String> hmResult) {
        this.sharedFilesCommentsId = ParseUtil.checkNull(hmResult.get("SHAREDFILESCOMMENTSID"));
        this.comment = ParseUtil.checkNull(hmResult.get("SHAREDFILESCOMMENTSID"));
        this.fromUserId = ParseUtil.checkNull(hmResult.get("SHAREDFILESCOMMENTSID"));
        this.sharedFilesGroupId = ParseUtil.checkNull(hmResult.get("SHAREDFILESCOMMENTSID"));
        this.createDate = ParseUtil.sToL(hmResult.get("SHAREDFILESCOMMENTSID"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("SHAREDFILESCOMMENTSID"));
    }

    public String getSharedFilesCommentsId() {
        return sharedFilesCommentsId;
    }

    public void setSharedFilesCommentsId(String sharedFilesCommentsId) {
        this.sharedFilesCommentsId = sharedFilesCommentsId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getSharedFilesGroupId() {
        return sharedFilesGroupId;
    }

    public void setSharedFilesGroupId(String sharedFilesGroupId) {
        this.sharedFilesGroupId = sharedFilesGroupId;
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

    public String getFormattedCreateDate() {
        return formattedCreateDate;
    }

    public void setFormattedCreateDate(String formattedCreateDate) {
        this.formattedCreateDate = formattedCreateDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SharedFilesCommentsBean{");
        sb.append("sharedFilesCommentsId='").append(sharedFilesCommentsId).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", fromUserId='").append(fromUserId).append('\'');
        sb.append(", sharedFilesGroupId='").append(sharedFilesGroupId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", formattedCreateDate='").append(formattedCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("shared_files_comments_id", this.sharedFilesCommentsId);
            jsonObject.put("comment", this.comment);
            jsonObject.put("shared_files_group_id", this.sharedFilesGroupId);
            jsonObject.put("user_id", this.fromUserId);
            jsonObject.put("viewer_type", this.createDate);
            jsonObject.put("human_create_date", this.humanCreateDate);
            jsonObject.put("formatted_create_date", this.formattedCreateDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
