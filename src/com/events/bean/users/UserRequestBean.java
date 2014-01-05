package com.events.bean.users;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRequestBean {
    private String userId = Constants.EMPTY;
    private String userInfoId = Constants.EMPTY;
    private String email = Constants.EMPTY;
    private String firstName = Constants.EMPTY;
    private String lastName = Constants.EMPTY;
    private PasswordRequestBean passwordRequestBean = new PasswordRequestBean();
    private Long createDate = 0L;
    private String humanCreateDate = Constants.EMPTY;
    private String companyName = Constants.EMPTY;
    private String cellPhone = Constants.EMPTY;
    private String workPhone = Constants.EMPTY;
    private String address1 = Constants.EMPTY;
    private String address2 = Constants.EMPTY;
    private String city = Constants.EMPTY;
    private String state = Constants.EMPTY;
    private String country = Constants.EMPTY;
    private String postalCode = Constants.EMPTY;
    private String parentId = Constants.EMPTY;
    private String userTypeId = Constants.EMPTY;
    private  Constants.USER_TYPE userType = Constants.USER_TYPE.VENDOR;
    private boolean isPlanner = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public PasswordRequestBean getPasswordRequestBean() {
        return passwordRequestBean;
    }

    public void setPasswordRequestBean(PasswordRequestBean passwordRequestBean) {
        this.passwordRequestBean = passwordRequestBean;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Constants.USER_TYPE getUserType() {

        return userType;
    }

    public void setUserType(Constants.USER_TYPE userType) {
        this.userType = userType;
    }

    public boolean isPlanner() {
        return isPlanner;
    }

    public void setPlanner(boolean planner) {
        isPlanner = planner;
    }

    @Override
    public String toString() {
        return "UserRequestBean{" +
                "userId='" + userId + '\'' +
                ", userInfoId='" + userInfoId + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passwordRequestBean=" + passwordRequestBean +
                ", createDate=" + createDate +
                ", humanCreateDate='" + humanCreateDate + '\'' +
                ", companyName='" + companyName + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", workPhone='" + workPhone + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", parentId='" + parentId + '\'' +
                ", userTypeId='" + userTypeId + '\'' +
                ", userType='" + userType + '\'' +
                ", isPlanner=" + isPlanner +
                '}';
    }
}
