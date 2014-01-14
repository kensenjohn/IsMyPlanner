package com.events.bean.common.email;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailSchedulerResponseBean {
    private EmailSchedulerBean emailSchedulerBean = new EmailSchedulerBean();

    public EmailSchedulerBean getEmailSchedulerBean() {
        return emailSchedulerBean;
    }

    public void setEmailSchedulerBean(EmailSchedulerBean emailSchedulerBean) {
        this.emailSchedulerBean = emailSchedulerBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmailSchedulerResponseBean{");
        sb.append("emailSchedulerBean=").append(emailSchedulerBean);
        sb.append('}');
        return sb.toString();
    }
}
