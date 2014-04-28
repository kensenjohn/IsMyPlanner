package com.events.bean.event.guest;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/1/14
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestRequestBean {
    private String guestGroupId = Constants.EMPTY;
    private String guestId = Constants.EMPTY;
    private String guestGroupName = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;

    private String firstName =  Constants.EMPTY;
    private String lastName =  Constants.EMPTY;
    private String company =  Constants.EMPTY;

    private Integer invitedSeats = 0;
    private Integer rsvpSeats = 0;

    private boolean isNotAttending = false;
    private boolean hasResponded = false;


    private String guestGroupPhoneId =   Constants.EMPTY;
    private String phone1 =  Constants.EMPTY;
    private String phone2 =  Constants.EMPTY;


    private String guestGroupEmailId =   Constants.EMPTY;
    private String email =  Constants.EMPTY;

    private boolean isPrimaryContactInfo = false;

    private String guestGroupAddressId =   Constants.EMPTY;
    private String address1 =  Constants.EMPTY;
    private String address2 =  Constants.EMPTY;
    private String city =  Constants.EMPTY;
    private String state =  Constants.EMPTY;
    private String country =  Constants.EMPTY;
    private String zipCode =  Constants.EMPTY;


    private String eventGuestGroupId =  Constants.EMPTY;

    private String guestGroupCommentId =  Constants.EMPTY;
    private String comments =  Constants.EMPTY;

    private String guestGroupFoodRestrictionAllergyId =  Constants.EMPTY;
    private boolean isFoodRestrictionAllergyExists =  false;
    private String foodRestrictionAllergyDetails =  Constants.EMPTY;

    private ArrayList<GuestGroupBean> arrGuestGroupBean = new ArrayList<GuestGroupBean>();

/*
                        String sGuestEmail = ParseUtil.checkNull(request.getParameter("guestEmail"));
                    String sGuestCompanyName = ParseUtil.checkNull(request.getParameter("guestCompanyName"));
                    String sGuestCellPhone = ParseUtil.checkNull(request.getParameter("guestCellPhone"));
                    String sGuestWorkPhone = ParseUtil.checkNull(request.getParameter("guestWorkPhone"));
                    String sGuestAddress1 = ParseUtil.checkNull(request.getParameter("guestAddress1"));
                    String sGuestAddress2 = ParseUtil.checkNull(request.getParameter("guestAddress2"));
                    String sGuestCity = ParseUtil.checkNull(request.getParameter("guestCity"));
                    String sGuestState = ParseUtil.checkNull(request.getParameter("guestState"));
                    String sGuestCountry = ParseUtil.checkNull(request.getParameter("guestCountry"));
                    String sGuestPostalCode = ParseUtil.checkNull(request.getParameter("guestPostalCode"));
                    String sGuestInvitedSeats = ParseUtil.checkNull(request.getParameter("guestInvitedSeats"));
                    String sGuestRSVP = ParseUtil.checkNull(request.getParameter("guestRSVP"));
     */

    public String getGuestGroupCommentId() {
        return guestGroupCommentId;
    }

    public void setGuestGroupCommentId(String guestGroupCommentId) {
        this.guestGroupCommentId = guestGroupCommentId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGuestGroupFoodRestrictionAllergyId() {
        return guestGroupFoodRestrictionAllergyId;
    }

    public void setGuestGroupFoodRestrictionAllergyId(String guestGroupFoodRestrictionAllergyId) {
        this.guestGroupFoodRestrictionAllergyId = guestGroupFoodRestrictionAllergyId;
    }

    public boolean isFoodRestrictionAllergyExists() {
        return isFoodRestrictionAllergyExists;
    }

    public void setFoodRestrictionAllergyExists(boolean foodRestrictionAllergyExists) {
        isFoodRestrictionAllergyExists = foodRestrictionAllergyExists;
    }

    public String getFoodRestrictionAllergyDetails() {
        return foodRestrictionAllergyDetails;
    }

    public void setFoodRestrictionAllergyDetails(String foodRestrictionAllergyDetails) {
        this.foodRestrictionAllergyDetails = foodRestrictionAllergyDetails;
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

    public String getGuestGroupName() {
        return guestGroupName;
    }

    public void setGuestGroupName(String guestGroupName) {
        this.guestGroupName = guestGroupName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getInvitedSeats() {
        return invitedSeats;
    }

    public void setInvitedSeats(Integer invitedSeats) {
        this.invitedSeats = invitedSeats;
    }

    public Integer getRsvpSeats() {
        return rsvpSeats;
    }

    public void setRsvpSeats(Integer rsvpSeats) {
        this.rsvpSeats = rsvpSeats;
    }

    public boolean isNotAttending() {
        return isNotAttending;
    }

    public void setNotAttending(boolean notAttending) {
        this.isNotAttending = notAttending;
    }

    public String getGuestGroupPhoneId() {
        return guestGroupPhoneId;
    }

    public void setGuestGroupPhoneId(String guestGroupPhoneId) {
        this.guestGroupPhoneId = guestGroupPhoneId;
    }

    public String getGuestGroupEmailId() {
        return guestGroupEmailId;
    }

    public void setGuestGroupEmailId(String guestGroupEmailId) {
        this.guestGroupEmailId = guestGroupEmailId;
    }

    public boolean isPrimaryContactInfo() {
        return isPrimaryContactInfo;
    }

    public void setPrimaryContactInfo(boolean primaryContactInfo) {
        isPrimaryContactInfo = primaryContactInfo;
    }

    public String getGuestGroupAddressId() {
        return guestGroupAddressId;
    }

    public void setGuestGroupAddressId(String guestGroupAddressId) {
        this.guestGroupAddressId = guestGroupAddressId;
    }

    public String getEventGuestGroupId() {
        return eventGuestGroupId;
    }

    public void setEventGuestGroupId(String eventGuestGroupId) {
        this.eventGuestGroupId = eventGuestGroupId;
    }

    public boolean isHasResponded() {
        return hasResponded;
    }

    public void setHasResponded(boolean hasResponded) {
        this.hasResponded = hasResponded;
    }

    public ArrayList<GuestGroupBean> getArrGuestGroupBean() {
        return arrGuestGroupBean;
    }

    public void setArrGuestGroupBean(ArrayList<GuestGroupBean> arrGuestGroupBean) {
        this.arrGuestGroupBean = arrGuestGroupBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestRequestBean{");
        sb.append("guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", guestId='").append(guestId).append('\'');
        sb.append(", guestGroupName='").append(guestGroupName).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", company='").append(company).append('\'');
        sb.append(", invitedSeats=").append(invitedSeats);
        sb.append(", rsvpSeats=").append(rsvpSeats);
        sb.append(", isNotAttending=").append(isNotAttending);
        sb.append(", hasResponded=").append(hasResponded);
        sb.append(", guestGroupPhoneId='").append(guestGroupPhoneId).append('\'');
        sb.append(", phone1='").append(phone1).append('\'');
        sb.append(", phone2='").append(phone2).append('\'');
        sb.append(", guestGroupEmailId='").append(guestGroupEmailId).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", isPrimaryContactInfo=").append(isPrimaryContactInfo);
        sb.append(", guestGroupAddressId='").append(guestGroupAddressId).append('\'');
        sb.append(", address1='").append(address1).append('\'');
        sb.append(", address2='").append(address2).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append(", eventGuestGroupId='").append(eventGuestGroupId).append('\'');
        sb.append(", arrGuestGroupId=").append(arrGuestGroupBean);
        sb.append('}');
        return sb.toString();
    }
}
