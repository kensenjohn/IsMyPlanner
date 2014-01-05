package com.events.bean.event.guest;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/2/14
 * Time: 7:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestGroupAddressBean {
    // ADDRESS_1 VARCHAR(1024) , ADDRESS_2 VARCHAR(1024), CITY VARCHAR(1024), STATE VARCHAR(150), COUNTRY VARCHAR(150), ZIPCODE VARCHAR(45),
    private String guestGroupAddressId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private String guestId = Constants.EMPTY;
    private String address1 = Constants.EMPTY;
    private String address2 = Constants.EMPTY;
    private String city = Constants.EMPTY;
    private String state = Constants.EMPTY;
    private String country = Constants.EMPTY;
    private String zipcode = Constants.EMPTY;
    private boolean isPrimaryContact = false;

    public GuestGroupAddressBean() {
    }

    public GuestGroupAddressBean(HashMap<String,String> hmResult) {
        this.guestGroupAddressId = ParseUtil.checkNull(hmResult.get("GUESTGROUPEMAILID"));
        this.guestGroupId = ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        this.guestId = ParseUtil.checkNull(hmResult.get("FK_GUESTID"));
        this.address1 = ParseUtil.checkNull(hmResult.get("ADDRESS_1"));
        this.address2 = ParseUtil.checkNull(hmResult.get("ADDRESS_2"));
        this.city = ParseUtil.checkNull(hmResult.get("CITY"));
        this.state = ParseUtil.checkNull(hmResult.get("STATE"));
        this.country = ParseUtil.checkNull(hmResult.get("COUNTRY"));
        this.zipcode = ParseUtil.checkNull(hmResult.get("ZIPCODE"));
        isPrimaryContact = ParseUtil.sTob(hmResult.get("PRIMARY_CONTACT"));
    }

    public String getGuestGroupAddressId() {
        return guestGroupAddressId;
    }

    public void setGuestGroupAddressId(String guestGroupAddressId) {
        this.guestGroupAddressId = guestGroupAddressId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean isPrimaryContact() {
        return isPrimaryContact;
    }

    public void setPrimaryContact(boolean primaryContact) {
        isPrimaryContact = primaryContact;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestGroupAddressBean{");
        sb.append("guestGroupAddressId='").append(guestGroupAddressId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", guestId='").append(guestId).append('\'');
        sb.append(", address1='").append(address1).append('\'');
        sb.append(", address2='").append(address2).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", zipcode='").append(zipcode).append('\'');
        sb.append(", isPrimaryContact=").append(isPrimaryContact);
        sb.append('}');
        return sb.toString();
    }



    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guestgroup_address_id", this.guestGroupAddressId);
            jsonObject.put("guest_id", this.guestId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("address_1", this.address1);
            jsonObject.put("address_2", this.address2);
            jsonObject.put("city", this.city);
            jsonObject.put("state", this.state);
            jsonObject.put("country", this.country);
            jsonObject.put("zipcode", this.zipcode);
            jsonObject.put("is_primary_contact", this.isPrimaryContact);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
