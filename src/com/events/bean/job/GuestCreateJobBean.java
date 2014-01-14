package com.events.bean.job;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 9:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestCreateJobBean {
    // GUESTCREATEJOBID VARCHAR(45) NOT NULL, FK_UPLOADID  VARCHAR(45) NOT NULL, FK_EVENTID  VARCHAR(45) NOT NULL,
    // JOBSTATUS   VARCHAR(100) NOT NULL,  CREATEDATE BIGINT(20) NOT NULL DEFAULT 0 , HUMANCREATEDATE VARCHAR(45)
    private String guestCreateJobId = Constants.EMPTY;
    private String uploadId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Constants.JOB_STATUS jobStatus = Constants.JOB_STATUS.PRELIM_STATE;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public GuestCreateJobBean(){}
    public GuestCreateJobBean(HashMap<String,String> hmResult) {
        this.guestCreateJobId = ParseUtil.checkNull(hmResult.get("GUESTCREATEJOBID"));
        this.uploadId = ParseUtil.checkNull(hmResult.get("FK_UPLOADID"));
        this.eventId = ParseUtil.checkNull(hmResult.get("FK_EVENTID"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
        String sJobStatus = ParseUtil.checkNull(hmResult.get("JOBSTATUS"));
        if(!Utility.isNullOrEmpty(sJobStatus) )  {
            this.jobStatus = Constants.JOB_STATUS.valueOf(sJobStatus);
        }
    }

    public String getGuestCreateJobId() {
        return guestCreateJobId;
    }

    public void setGuestCreateJobId(String guestCreateJobId) {
        this.guestCreateJobId = guestCreateJobId;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Constants.JOB_STATUS getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Constants.JOB_STATUS jobStatus) {
        this.jobStatus = jobStatus;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestCreationJobBean{");
        sb.append("guestCreateJobId='").append(guestCreateJobId).append('\'');
        sb.append(", uploadId='").append(uploadId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", jobStatus='").append(jobStatus.getStatus()).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
    /*
        private String guestCreateJobId = Constants.EMPTY;
    private String uploadId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Constants.JOB_STATUS jobStatus = Constants.JOB_STATUS.PRELIM_STATE;
     */
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guestcreatejob_id", this.guestCreateJobId );
            jsonObject.put("upload_id", this.uploadId );
            jsonObject.put("event_id", this.eventId );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("job_status", jobStatus!=null?jobStatus.getStatus():"" );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
