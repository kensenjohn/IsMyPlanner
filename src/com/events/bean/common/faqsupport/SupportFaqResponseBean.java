package com.events.bean.common.faqsupport;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/24/14
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class SupportFaqResponseBean {
    private ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean = new ArrayList<SupportFaqCategoriesBean>();
    private HashMap<String, ArrayList<SupportFaqQuesAndAnsBean> > hmSupportFaqQuesAndAnsBeans = new HashMap<String, ArrayList<SupportFaqQuesAndAnsBean>>();

    public ArrayList<SupportFaqCategoriesBean> getArrSupportFaqCategoriesBean() {
        return arrSupportFaqCategoriesBean;
    }

    public void setArrSupportFaqCategoriesBean(ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean) {
        this.arrSupportFaqCategoriesBean = arrSupportFaqCategoriesBean;
    }

    public HashMap<String, ArrayList<SupportFaqQuesAndAnsBean>> getHmSupportFaqQuesAndAnsBeans() {
        return hmSupportFaqQuesAndAnsBeans;
    }

    public void setHmSupportFaqQuesAndAnsBeans(HashMap<String, ArrayList<SupportFaqQuesAndAnsBean>> hmSupportFaqQuesAndAnsBeans) {
        this.hmSupportFaqQuesAndAnsBeans = hmSupportFaqQuesAndAnsBeans;
    }
}
