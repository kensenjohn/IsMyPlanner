package com.events.bean.event.guest.response;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebRespResponse {
    private GuestWebResponseBean guestWebResponseBean = new GuestWebResponseBean();

    public GuestWebResponseBean getGuestWebResponseBean() {
        return guestWebResponseBean;
    }

    public void setGuestWebResponseBean(GuestWebResponseBean guestWebResponseBean) {
        this.guestWebResponseBean = guestWebResponseBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebRespResponse{");
        sb.append("guestWebResponseBean=").append(guestWebResponseBean);
        sb.append('}');
        return sb.toString();
    }
}
