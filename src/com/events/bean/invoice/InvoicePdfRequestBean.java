package com.events.bean.invoice;

import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/4/14
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class InvoicePdfRequestBean {
    private String logo = Constants.EMPTY;
    private InvoiceBean invoiceBean = new InvoiceBean();
    private UserBean userBean = new UserBean();
    private String invoiceId = Constants.EMPTY;
    private ArrayList<InvoiceItemBean> arrInvoiceItemsBean = new ArrayList<InvoiceItemBean>();
    private ParentTypeBean parentTypeBean = new ParentTypeBean();


    public ParentTypeBean getParentTypeBean() {
        return parentTypeBean;
    }

    public void setParentTypeBean(ParentTypeBean parentTypeBean) {
        this.parentTypeBean = parentTypeBean;
    }

    public ArrayList<InvoiceItemBean> getArrInvoiceItemsBean() {
        return arrInvoiceItemsBean;
    }

    public void setArrInvoiceItemsBean(ArrayList<InvoiceItemBean> arrInvoiceItemsBean) {
        this.arrInvoiceItemsBean = arrInvoiceItemsBean;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public InvoiceBean getInvoiceBean() {
        return invoiceBean;
    }

    public void setInvoiceBean(InvoiceBean invoiceBean) {
        this.invoiceBean = invoiceBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
