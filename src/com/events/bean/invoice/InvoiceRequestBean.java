package com.events.bean.invoice;

import com.events.common.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceRequestBean {
    private String invoiceId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private InvoiceBean invoiceBean = new InvoiceBean();
    private ArrayList<InvoiceItemBean> arrInvoiceItemBean = new ArrayList<InvoiceItemBean>();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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

    public ArrayList<InvoiceItemBean> getArrInvoiceItemBean() {
        return arrInvoiceItemBean;
    }

    public void setArrInvoiceItemBean(ArrayList<InvoiceItemBean> arrInvoiceItemBean) {
        this.arrInvoiceItemBean = arrInvoiceItemBean;
    }
}
