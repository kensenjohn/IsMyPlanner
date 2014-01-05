package com.events.bean.job;

import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/3/14
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestCreateJobResponseBean {
    private String guestCreateJobId = Constants.EMPTY;
    private GuestCreateJobBean guestCreateJobBean = new GuestCreateJobBean();

    public String getGuestCreateJobId() {
        return guestCreateJobId;
    }

    public void setGuestCreateJobId(String guestCreateJobId) {
        this.guestCreateJobId = guestCreateJobId;
    }

    public GuestCreateJobBean getGuestCreateJobBean() {
        return guestCreateJobBean;
    }

    public void setGuestCreateJobBean(GuestCreateJobBean guestCreateJobBean) {
        this.guestCreateJobBean = guestCreateJobBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestCreateJobResponseBean{");
        sb.append("guestCreateJobBean=").append(guestCreateJobBean);
        sb.append('}');
        return sb.toString();
    }
}
