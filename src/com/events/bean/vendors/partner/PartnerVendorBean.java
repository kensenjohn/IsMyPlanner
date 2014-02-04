package com.events.bean.vendors.partner;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PartnerVendorBean {
    //GTPARTNERVENDORS(PARTNERID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL, FK_VENDORID_PARTNER
    private String partnerId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String partnerVendorId = Constants.EMPTY;

    public PartnerVendorBean() {
    }

    public PartnerVendorBean( HashMap<String,String> hashMap) {
        this.partnerId = ParseUtil.checkNull(hashMap.get("PARTNERID"));
        this.vendorId = ParseUtil.checkNull(hashMap.get("FK_VENDORID"));
        this.partnerVendorId = ParseUtil.checkNull(hashMap.get("FK_VENDORID_PARTNER"));
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getPartnerVendorId() {
        return partnerVendorId;
    }

    public void setPartnerVendorId(String partnerVendorId) {
        this.partnerVendorId = partnerVendorId;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("partner_id", this.partnerId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("partner_vendor_id", this.partnerVendorId );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "PartnerVendorBean{" +
                "partnerId='" + partnerId + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", partnerVendorId='" + partnerVendorId + '\'' +
                '}';
    }
}
