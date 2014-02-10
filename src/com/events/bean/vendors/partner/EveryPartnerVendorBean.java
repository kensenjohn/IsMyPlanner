package com.events.bean.vendors.partner;

import com.events.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/4/14
 * Time: 8:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class EveryPartnerVendorBean {
    private String partnerVendorId = Constants.EMPTY;
    private String name = Constants.EMPTY;
    private String email = Constants.EMPTY;
    private String phone = Constants.EMPTY;
    private String type = Constants.EMPTY;
    private String website = Constants.EMPTY;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartnerVendorId() {
        return partnerVendorId;
    }

    public void setPartnerVendorId(String partnerVendorId) {
        this.partnerVendorId = partnerVendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryPartnerVendorBean{");
        sb.append("partnerVendorId='").append(partnerVendorId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", type='").append(type).append('\'');;
        sb.append(", website='").append(website).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("partner_vendor_id", this.partnerVendorId );
            jsonObject.put("name", this.name );
            jsonObject.put("email", this.email );
            jsonObject.put("phone", this.phone );
            jsonObject.put("type", this.type );
            jsonObject.put("website", this.website );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
