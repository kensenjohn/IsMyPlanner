package com.events.bean.common.email;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/13/14
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventEmailResponseBean {
    private Integer numOfEventEmails = 0;
    private ArrayList<EventEmailBean> arrEventEmailBean =  new ArrayList<EventEmailBean>();
    private HashMap<String,ArrayList<EventEmailFeatureBean> > hmEveryEventEmailFeatureBean = new HashMap<String, ArrayList<EventEmailFeatureBean>>();
    private HashMap<String,EmailSchedulerBean> hmEveryEmailSchdeulerBean = new HashMap<String, EmailSchedulerBean>();


    public ArrayList<EventEmailBean> getArrEventEmailBean() {
        return arrEventEmailBean;
    }

    public void setArrEventEmailBean(ArrayList<EventEmailBean> arrEventEmailBean) {
        this.arrEventEmailBean = arrEventEmailBean;
    }

    public HashMap<String, ArrayList<EventEmailFeatureBean>> getHmEveryEventEmailFeatureBean() {
        return hmEveryEventEmailFeatureBean;
    }

    public void setHmEveryEventEmailFeatureBean(HashMap<String, ArrayList<EventEmailFeatureBean>> hmEveryEventEmailFeatureBean) {
        this.hmEveryEventEmailFeatureBean = hmEveryEventEmailFeatureBean;
    }

    public Integer getNumOfEventEmails() {
        return numOfEventEmails;
    }

    public void setNumOfEventEmails(Integer numOfEventEmails) {
        this.numOfEventEmails = numOfEventEmails;
    }

    public HashMap<String, EmailSchedulerBean> getHmEveryEmailSchdeulerBean() {
        return hmEveryEmailSchdeulerBean;
    }

    public void setHmEveryEmailSchdeulerBean(HashMap<String, EmailSchedulerBean> hmEveryEmailSchdeulerBean) {
        this.hmEveryEmailSchdeulerBean = hmEveryEmailSchdeulerBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryEventEmailResponseBean{");
        sb.append("numOfEventEmails=").append(numOfEventEmails);
        sb.append(", arrEventEmailBean=").append(arrEventEmailBean);
        sb.append(", hmEveryEventEmailFeatureBean=").append(hmEveryEventEmailFeatureBean);
        sb.append(", hmEveryEmailSchdeulerBean=").append(hmEveryEmailSchdeulerBean);
        sb.append('}');
        return sb.toString();
    }
}
