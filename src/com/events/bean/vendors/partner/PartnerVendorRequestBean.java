package com.events.bean.vendors.partner;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 2/4/14
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PartnerVendorRequestBean {
    private String partnerId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String partnerVendorId = Constants.EMPTY;

    private String businessName = Constants.EMPTY;
    private String firstName = Constants.EMPTY;
    private String lastName = Constants.EMPTY;
    private String address1 = Constants.EMPTY;
    private String address2 = Constants.EMPTY;
    private String city = Constants.EMPTY;
    private String state = Constants.EMPTY;
    private String country = Constants.EMPTY;
    private String ipAddress = Constants.EMPTY;
    private String email = Constants.EMPTY;
    private String cellPhone = Constants.EMPTY;
    private String phoneNum = Constants.EMPTY;
    private String humanCellPhone = Constants.EMPTY;
    private String humanPhoneNum = Constants.EMPTY;
    private String website = Constants.EMPTY;
    private String zipcode = Constants.EMPTY;

    private String userId =  Constants.EMPTY;
    private String vendorType = Constants.EMPTY;

    private String price = Constants.EMPTY;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getHumanCellPhone() {
        return humanCellPhone;
    }

    public void setHumanCellPhone(String humanCellPhone) {
        this.humanCellPhone = humanCellPhone;
    }

    public String getHumanPhoneNum() {
        return humanPhoneNum;
    }

    public void setHumanPhoneNum(String humanPhoneNum) {
        this.humanPhoneNum = humanPhoneNum;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "PartnerVendorRequestBean{" +
                "partnerId='" + partnerId + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", partnerVendorId='" + partnerVendorId + '\'' +
                ", businessName='" + businessName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", email='" + email + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", humanCellPhone='" + humanCellPhone + '\'' +
                ", humanPhoneNum='" + humanPhoneNum + '\'' +
                ", website='" + website + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
