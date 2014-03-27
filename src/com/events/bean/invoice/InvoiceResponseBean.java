package com.events.bean.invoice;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceResponseBean {
    private String invoiceId = Constants.EMPTY;
    private ArrayList<InvoiceBean> arrInvoiceBean = new ArrayList<InvoiceBean>();
    private InvoiceBean invoiceBean = new InvoiceBean();
    private ArrayList<InvoiceItemBean> arrInvoiceItemsBean = new ArrayList<InvoiceItemBean>();

    public ArrayList<InvoiceItemBean> getArrInvoiceItemsBean() {
        return arrInvoiceItemsBean;
    }

    public void setArrInvoiceItemsBean(ArrayList<InvoiceItemBean> arrInvoiceItemsBean) {
        this.arrInvoiceItemsBean = arrInvoiceItemsBean;
    }

    public ArrayList<InvoiceBean> getArrInvoiceBean() {
        return arrInvoiceBean;
    }

    public void setArrInvoiceBean(ArrayList<InvoiceBean> arrInvoiceBean) {
        this.arrInvoiceBean = arrInvoiceBean;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }



    public InvoiceBean getInvoiceBean() {
        return invoiceBean;
    }

    public void setInvoiceBean(InvoiceBean invoiceBean) {
        this.invoiceBean = invoiceBean;
    }
}
