package com.events.bean.event.guest;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/25/14
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestGroupCommentsBean {
    //GTGUESTGROUPCOMMENTS( GUESTGROUPCOMMENTSID VARCHAR(45) PRIMARY KEY NOT NULL, FK_GUESTGROUPID VARCHAR(45) NOT NULL,
    //  CREATEDATE BIGINT(20) NOT NULL DEFAULT 0 , HUMANCREATEDATE VARCHAR(45)
    // COMMENTS TEXT NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8;

    private String guestGroupCommentId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String comments = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String formattedDate = Constants.EMPTY;

    public GuestGroupCommentsBean(){}
    public GuestGroupCommentsBean(HashMap<String, String> hmResult) {
        this.guestGroupCommentId =  ParseUtil.checkNull(hmResult.get("GUESTGROUPCOMMENTSID"));
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        this.comments = ParseUtil.checkNull(hmResult.get("COMMENTS"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
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

    public String getGuestGroupCommentId() {
        return guestGroupCommentId;
    }

    public void setGuestGroupCommentId(String guestGroupCommentId) {
        this.guestGroupCommentId = guestGroupCommentId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestGroupCommentsBean{");
        sb.append("guestGroupCommentId='").append(guestGroupCommentId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", comments='").append(comments).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", formattedDate='").append(formattedDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guestgroup_comment_id", this.guestGroupCommentId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("comments", this.comments);
            jsonObject.put("formattedDate", this.formattedDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
