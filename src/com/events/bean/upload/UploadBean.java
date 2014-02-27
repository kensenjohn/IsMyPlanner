package com.events.bean.upload;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class UploadBean {
    // create TABLE GTUPLOADS (UPLOADID   VARCHAR(45) NOT NULL, FILENAME TEXT NOT NULL, PATH TEXT NOT NULL,
    //  CREATEDATE BIGINT(20) NOT NULL DEFAULT 0,HUMANCREATEDATE VARCHAR(45) NOT NULL, PRIMARY KEY (FEATUREID)

    private String uploadId = Constants.EMPTY;
    private String filename = Constants.EMPTY;
    private String path = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public UploadBean(){}
    public UploadBean(HashMap<String,String> hmResult){
        this.uploadId = ParseUtil.checkNull(hmResult.get("UPLOADID"));
        this.filename = ParseUtil.checkNull(hmResult.get("FILENAME"));
        this.path = ParseUtil.checkNull(hmResult.get("PATH"));
    }
    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        final StringBuilder sb = new StringBuilder("UploadBean{");
        sb.append("uploadId='").append(uploadId).append('\'');
        sb.append(", filename='").append(filename).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("upload_id", this.uploadId );
            jsonObject.put("filename", this.filename );
            jsonObject.put("path", this.path );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
