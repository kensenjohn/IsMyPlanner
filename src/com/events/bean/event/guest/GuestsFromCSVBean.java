package com.events.bean.event.guest;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/22/14
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestsFromCSVBean {
    private Integer rowNumber = 0;
    private GuestGroupBean guestGroupBean = new GuestGroupBean();
    private EventGuestGroupBean eventGuestGroupBean = new EventGuestGroupBean();
    private ArrayList<GuestBean> arrGuestBean = new ArrayList<GuestBean>();
    private ArrayList<GuestGroupPhoneBean> arrGuestGroupPhoneBean = new ArrayList<GuestGroupPhoneBean>();
    private ArrayList<GuestGroupEmailBean>  arrGuestGroupEmailBean = new ArrayList<GuestGroupEmailBean>();
    private ArrayList<GuestGroupAddressBean>  arrGuestGroupAddressBean = new ArrayList<GuestGroupAddressBean>();

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public GuestGroupBean getGuestGroupBean() {
        return guestGroupBean;
    }

    public void setGuestGroupBean(GuestGroupBean guestGroupBean) {
        this.guestGroupBean = guestGroupBean;
    }

    public EventGuestGroupBean getEventGuestGroupBean() {
        return eventGuestGroupBean;
    }

    public void setEventGuestGroupBean(EventGuestGroupBean eventGuestGroupBean) {
        this.eventGuestGroupBean = eventGuestGroupBean;
    }

    public ArrayList<GuestBean> getArrGuestBean() {
        return arrGuestBean;
    }

    public void setArrGuestBean(ArrayList<GuestBean> arrGuestBean) {
        this.arrGuestBean = arrGuestBean;
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
        final StringBuilder sb = new StringBuilder("GuestsFromCSVBean{");
        sb.append("rowNumber=").append(rowNumber);
        sb.append(", guestGroupBean=").append(guestGroupBean);
        sb.append(", eventGuestGroupBean=").append(eventGuestGroupBean);
        sb.append(", arrGuestBean=").append(arrGuestBean);
        sb.append(", arrGuestGroupPhoneBean=").append(arrGuestGroupPhoneBean);
        sb.append(", arrGuestGroupEmailBean=").append(arrGuestGroupEmailBean);
        sb.append(", arrGuestGroupAddressBean=").append(arrGuestGroupAddressBean);
        sb.append('}');
        return sb.toString();
    }
}
