package com.events.bean.dashboard;

import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/8/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class SummaryRequest {
    private UserBean userBean = new UserBean();
    private ParentTypeBean parentTypeBean = new ParentTypeBean();

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public ParentTypeBean getParentTypeBean() {
        return parentTypeBean;
    }

    public void setParentTypeBean(ParentTypeBean parentTypeBean) {
        this.parentTypeBean = parentTypeBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SummaryRequest{");
        sb.append("userBean=").append(userBean);
        sb.append('}');
        return sb.toString();
    }
}
