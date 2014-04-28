package com.events.bean.event.guest;

import com.events.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/1/14
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestResponseBean {

    private String guestGroupId = Constants.EMPTY;
    private String eventId = Constants.EMPTY;
    private String eventGuestGroupId = Constants.EMPTY;
    private boolean isGuestDeleted = false;

    private HashMap<String,GuestGroupBean> hmGuestGroupBean = new HashMap<String, GuestGroupBean>();
    private ArrayList<EventGuestGroupBean> arrEventGuestGroupBean = new ArrayList<EventGuestGroupBean>();
    private ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup = new ArrayList<EveryEventGuestGroupBean>();

    /*

            EventGuestGroupBean eventGuestGroupBean = accessGuestData.getEventGuestGroup(guestRequestBean);
            GuestGroupBean guestGroupBean = accessGuestData.getGuestGroup(guestRequestBean);
            GuestBean guestBean = accessGuestData.getGuest(guestRequestBean);
            ArrayList<GuestGroupPhoneBean>  arrGuestGroupPhoneBean = accessGuestData.getGuestGroupPhone(guestRequestBean);
            ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = accessGuestData.getGuestGroupEmail(guestRequestBean);
            ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = accessGuestData.getGuestGroupAddress(guestRequestBean);
     */
    private EventGuestGroupBean eventGuestGroupBean = new EventGuestGroupBean();
    private GuestGroupBean guestGroupBean = new GuestGroupBean();
    private GuestBean guestBean = new GuestBean();
    private ArrayList<GuestGroupPhoneBean>  arrGuestGroupPhoneBean = new ArrayList<GuestGroupPhoneBean>();
    private ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = new ArrayList<GuestGroupEmailBean>();
    private ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = new ArrayList<GuestGroupAddressBean>();
    private ArrayList<GuestGroupCommentsBean> arrGuestGroupCommentsBean = new ArrayList<GuestGroupCommentsBean>();
    private GuestGroupFoodRestrictionAllergyBean guestGroupFoodRestrictionAllergyBean = new GuestGroupFoodRestrictionAllergyBean();

    public ArrayList<GuestGroupCommentsBean> getArrGuestGroupCommentsBean() {
        return arrGuestGroupCommentsBean;
    }

    public void setArrGuestGroupCommentsBean(ArrayList<GuestGroupCommentsBean> arrGuestGroupCommentsBean) {
        this.arrGuestGroupCommentsBean = arrGuestGroupCommentsBean;
    }

    public GuestGroupFoodRestrictionAllergyBean getGuestGroupFoodRestrictionAllergyBean() {
        return guestGroupFoodRestrictionAllergyBean;
    }

    public void setGuestGroupFoodRestrictionAllergyBean(GuestGroupFoodRestrictionAllergyBean guestGroupFoodRestrictionAllergyBean) {
        this.guestGroupFoodRestrictionAllergyBean = guestGroupFoodRestrictionAllergyBean;
    }

    public boolean isGuestDeleted() {
        return isGuestDeleted;
    }

    public void setGuestDeleted(boolean guestDeleted) {
        isGuestDeleted = guestDeleted;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventGuestGroupId() {
        return eventGuestGroupId;
    }

    public void setEventGuestGroupId(String eventGuestGroupId) {
        this.eventGuestGroupId = eventGuestGroupId;
    }

    public HashMap<String, GuestGroupBean> getHmGuestGroupBean() {
        return hmGuestGroupBean;
    }

    public void setHmGuestGroupBean(HashMap<String, GuestGroupBean> hmGuestGroupBean) {
        this.hmGuestGroupBean = hmGuestGroupBean;
    }

    public ArrayList<EventGuestGroupBean> getArrEventGuestGroupBean() {
        return arrEventGuestGroupBean;
    }

    public void setArrEventGuestGroupBean(ArrayList<EventGuestGroupBean> arrEventGuestGroupBean) {
        this.arrEventGuestGroupBean = arrEventGuestGroupBean;
    }

    public ArrayList<EveryEventGuestGroupBean> getArrEveryEventGuestGroup() {
        return arrEveryEventGuestGroup;
    }

    public void setArrEveryEventGuestGroup(ArrayList<EveryEventGuestGroupBean> arrEveryEventGuestGroup) {
        this.arrEveryEventGuestGroup = arrEveryEventGuestGroup;
    }

    public EventGuestGroupBean getEventGuestGroupBean() {
        return eventGuestGroupBean;
    }

    public void setEventGuestGroupBean(EventGuestGroupBean eventGuestGroupBean) {
        this.eventGuestGroupBean = eventGuestGroupBean;
    }

    public GuestGroupBean getGuestGroupBean() {
        return guestGroupBean;
    }

    public void setGuestGroupBean(GuestGroupBean guestGroupBean) {
        this.guestGroupBean = guestGroupBean;
    }

    public GuestBean getGuestBean() {
        return guestBean;
    }

    public void setGuestBean(GuestBean guestBean) {
        this.guestBean = guestBean;
    }

    public ArrayList<GuestGroupPhoneBean> getArrGuestGroupPhoneBean() {
        return arrGuestGroupPhoneBean;
    }

    public void setArrGuestGroupPhoneBean(ArrayList<GuestGroupPhoneBean> arrGuestGroupPhoneBean) {
        this.arrGuestGroupPhoneBean = arrGuestGroupPhoneBean;
    }

    public ArrayList<GuestGroupEmailBean> getArrGuestGroupEmailBean() {
        return arrGuestGroupEmailBean;
    }

    public void setArrGuestGroupEmailBean(ArrayList<GuestGroupEmailBean> arrGuestGroupEmailBean) {
        this.arrGuestGroupEmailBean = arrGuestGroupEmailBean;
    }

    public ArrayList<GuestGroupAddressBean> getArrGuestGroupAddressBean() {
        return arrGuestGroupAddressBean;
    }

    public void setArrGuestGroupAddressBean(ArrayList<GuestGroupAddressBean> arrGuestGroupAddressBean) {
        this.arrGuestGroupAddressBean = arrGuestGroupAddressBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestResponseBean{");
        sb.append("guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", eventGuestGroupId='").append(eventGuestGroupId).append('\'');
        sb.append(", hmGuestGroupBean=").append(hmGuestGroupBean);
        sb.append(", arrEventGuestGroupBean=").append(arrEventGuestGroupBean);
        sb.append(", arrEveryEventGuestGroup=").append(arrEveryEventGuestGroup);
        sb.append(", eventGuestGroupBean=").append(eventGuestGroupBean);
        sb.append(", guestGroupBean=").append(guestGroupBean);
        sb.append(", guestBean=").append(guestBean);
        sb.append(", arrGuestGroupPhoneBean=").append(arrGuestGroupPhoneBean);
        sb.append(", arrGuestGroupEmailBean=").append(arrGuestGroupEmailBean);
        sb.append(", arrGuestGroupAddressBean=").append(arrGuestGroupAddressBean);
        sb.append('}');
        return sb.toString();
    }
}
