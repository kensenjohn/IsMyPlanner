package com.events.bean.vendors;

import com.events.common.Constants;
import com.events.common.ParseUtil;

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

    @Override
    public String toString() {
        return "VendorBean{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", createDate=" + createDate +
                ", humanCreateDate='" + humanCreateDate + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", humanModifiedDate='" + humanModifiedDate + '\'' +
                '}';
    }
}
