package com.events.bean.invoice;

import com.events.bean.clients.ClientResponseBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/22/14
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceEmailRequestBean {
    private String invoiceId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private InvoiceBean invoiceBean = new InvoiceBean();
    private VendorResponseBean vendorResponseBean = new VendorResponseBean();
    private ClientResponseBean clientResponseBean = new ClientResponseBean();

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public InvoiceBean getInvoiceBean() {
        return invoiceBean;
    }

    public void setInvoiceBean(InvoiceBean invoiceBean) {
        this.invoiceBean = invoiceBean;
    }

    public VendorResponseBean getVendorResponseBean() {
        return vendorResponseBean;
    }

    public void setVendorResponseBean(VendorResponseBean vendorResponseBean) {
        this.vendorResponseBean = vendorResponseBean;
    }

    public ClientResponseBean getClientResponseBean() {
        return clientResponseBean;
    }

    public void setClientResponseBean(ClientResponseBean clientResponseBean) {
        this.clientResponseBean = clientResponseBean;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceEmailRequestBean{");
        sb.append("invoiceId='").append(invoiceId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", invoiceBean=").append(invoiceBean);
        sb.append(", vendorResponseBean=").append(vendorResponseBean);
        sb.append(", clientResponseBean=").append(clientResponseBean);
        sb.append('}');
        return sb.toString();
    }
}
