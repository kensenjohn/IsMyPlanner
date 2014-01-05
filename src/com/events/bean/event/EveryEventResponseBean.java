package com.events.bean.event;

import com.events.bean.clients.ClientBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/29/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class EveryEventResponseBean {
    private ArrayList<EveryEventBean>  arrEveryEventBean = new ArrayList<EveryEventBean>();
    private HashMap<String,ClientBean> hmClientBean = new HashMap<String, ClientBean>();
    private HashMap<String,EventDisplayDateBean> hmEventDisplayDateBean = new HashMap<String, EventDisplayDateBean>();

    public ArrayList<EveryEventBean> getArrEveryEventBean() {
        return arrEveryEventBean;
    }

    public void setArrEveryEventBean(ArrayList<EveryEventBean> arrEveryEventBean) {
        this.arrEveryEventBean = arrEveryEventBean;
    }

    public HashMap<String, ClientBean> getHmClientBean() {
        return hmClientBean;
    }

    public void setHmClientBean(HashMap<String, ClientBean> hmClientBean) {
        this.hmClientBean = hmClientBean;
    }

    public HashMap<String, EventDisplayDateBean> getHmEventDisplayDateBean() {
        return hmEventDisplayDateBean;
    }

    public void setHmEventDisplayDateBean(HashMap<String, EventDisplayDateBean> hmEventDisplayDateBean) {
        this.hmEventDisplayDateBean = hmEventDisplayDateBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EveryEventResponseBean{");
        sb.append("arrEveryEventBean=").append(arrEveryEventBean);
        sb.append(", hmClientBean=").append(hmClientBean);
        sb.append(", hmEventDisplayDateBean=").append(hmEventDisplayDateBean);
        sb.append('}');
        return sb.toString();
    }
}
