package com.events.bean.common.faqsupport;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/24/14
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class SupportFaqRequestBean {
    private ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean = new ArrayList<SupportFaqCategoriesBean>();
    private String supportFaqQuestionId = Constants.EMPTY;

    public ArrayList<SupportFaqCategoriesBean> getArrSupportFaqCategoriesBean() {
        return arrSupportFaqCategoriesBean;
    }

    public void setArrSupportFaqCategoriesBean(ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean) {
        this.arrSupportFaqCategoriesBean = arrSupportFaqCategoriesBean;
    }

    public String getSupportFaqQuestionId() {
        return supportFaqQuestionId;
    }

    public void setSupportFaqQuestionId(String supportFaqQuestionId) {
        this.supportFaqQuestionId = supportFaqQuestionId;
    }
}
