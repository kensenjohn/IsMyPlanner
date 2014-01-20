package com.events.bean.vendors;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/20/13
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorBean {
    //VENDORID VARCHAR(45) NOT NULL, VENDORNAME VARCHAR(400) NOT NULL, CREATEDATE BIGINT(20) NOT NULL DEFAULT 0 , HUMANCREATEDATE VARCHAR(45),
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45) , DEL_ROW INT(1) NOT NULL DEFAULT 0,
    private String vendorId = Constants.EMPTY;
    private String vendorName = "Hey!! I Am New!!";
    private String folder = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private Long modifiedDate = 0L;
    private String humanModifiedDate = Constants.EMPTY;

    public VendorBean() {}
    public VendorBean(HashMap<String, String> hmVendorRes) {
        this.vendorId = ParseUtil.checkNull(hmVendorRes.get("VENDORID"));
        this.vendorName = ParseUtil.checkNull(hmVendorRes.get("VENDORNAME"));
        this.createDate = ParseUtil.sToL(hmVendorRes.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmVendorRes.get("HUMANCREATEDATE"));
        this.modifiedDate = ParseUtil.sToL(hmVendorRes.get("MODIFIEDDATE"));
        this.humanModifiedDate = ParseUtil.checkNull(hmVendorRes.get("HUMANMODIFIEDDATE"));
        this.folder = ParseUtil.checkNull(hmVendorRes.get("FOLDER"));
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getHumanModifiedDate() {
        return humanModifiedDate;
    }

    public void setHumanModifiedDate(String humanModifiedDate) {
        this.humanModifiedDate = humanModifiedDate;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorBean{");
        sb.append("vendorId='").append(vendorId).append('\'');
        sb.append(", vendorName='").append(vendorName).append('\'');
        sb.append(", folder='").append(folder).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append(", modifiedDate=").append(modifiedDate);
        sb.append(", humanModifiedDate='").append(humanModifiedDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("vendor_name", this.vendorName );
            jsonObject.put("folder", this.folder );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
