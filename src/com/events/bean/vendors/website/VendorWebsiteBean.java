package com.events.bean.vendors.website;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/26/14
 * Time: 10:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsiteBean {
    //VENDORWEBSITEID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL,FK_USERID VARCHAR(45) NOT NULL, CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE
    private String vendorWebsiteId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;

    public VendorWebsiteBean (){}

    public VendorWebsiteBean(HashMap<String,String> hmResult) {
        this.vendorWebsiteId = ParseUtil.checkNull(hmResult.get("VENDORWEBSITEID"));
        this.vendorId = ParseUtil.checkNull(hmResult.get("FK_VENDORID"));
        this.userId = ParseUtil.checkNull(hmResult.get("FK_USERID"));
        this.createDate = ParseUtil.sToL(hmResult.get("CREATEDATE"));
        this.humanCreateDate = ParseUtil.checkNull(hmResult.get("HUMANCREATEDATE"));
    }

    public String getVendorWebsiteId() {
        return vendorWebsiteId;
    }

    public void setVendorWebsiteId(String vendorWebsiteId) {
        this.vendorWebsiteId = vendorWebsiteId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorWebsiteBean{");
        sb.append("vendorWebsiteId='").append(vendorWebsiteId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", humanCreateDate='").append(humanCreateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vendorwebsite_id", this.vendorWebsiteId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("user_id", this.userId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
