package com.events.bean.job;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestCreateJobRequestBean {
    private String guestCreateJobId = Constants.EMPTY;
    private String uploadId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Constants.JOB_STATUS jobStatus = Constants.JOB_STATUS.PRELIM_STATE;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestCreateJobRequestBean{");
        sb.append("guestCreateJobId='").append(guestCreateJobId).append('\'');
        sb.append(", uploadId='").append(uploadId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", jobStatus='").append(jobStatus.getStatus()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
