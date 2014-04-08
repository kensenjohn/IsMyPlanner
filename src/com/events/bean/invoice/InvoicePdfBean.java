package com.events.bean.invoice;

import com.events.common.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/4/14
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class InvoicePdfBean {


    private String invoiceNumber = Constants.EMPTY;
    private String imageHost = Constants.EMPTY;
    private String imagePath = Constants.EMPTY;
    private String logo = Constants.EMPTY;

    private String invoiceDate = Constants.EMPTY;
    private String dueDate = Constants.EMPTY;

    private String vendorName = Constants.EMPTY;
    private String vendorPhone = Constants.EMPTY;
    private String vendorAddress1 = Constants.EMPTY;
    private String vendorAddress2 = Constants.EMPTY;
    private String vendorCity = Constants.EMPTY;
    private String vendorState = Constants.EMPTY;
    private String vendorZip = Constants.EMPTY;


    private String clientFirstName = Constants.EMPTY;
    private String clientLastName = Constants.EMPTY;
    private String clientPhone = Constants.EMPTY;
    private String clientAddress1 = Constants.EMPTY;
    private String clientAddress2 = Constants.EMPTY;
    private String clientCity = Constants.EMPTY;
    private String clientState = Constants.EMPTY;
    private String clientZip = Constants.EMPTY;


    private String subTotal = Constants.EMPTY;
    private String discount = Constants.EMPTY;
    private String tax = Constants.EMPTY;
    private String balanceDue = Constants.EMPTY;


    private String terms = Constants.EMPTY;
    private String instructions = Constants.EMPTY;
    private String note = Constants.EMPTY;
    private String poNumber = Constants.EMPTY;

    @XmlElement
    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @XmlElement
    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    @XmlElement
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private ArrayList<InvoiceItemBean> arrInvoiceItemsBean = new ArrayList<InvoiceItemBean>();

    @XmlElement(name = "items")
    public ArrayList<InvoiceItemBean> getArrInvoiceItemsBean() {
        return arrInvoiceItemsBean;
    }

    public void setArrInvoiceItemsBean(ArrayList<InvoiceItemBean> arrInvoiceItemsBean) {
        this.arrInvoiceItemsBean = arrInvoiceItemsBean;
    }

    @XmlElement
    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @XmlElement
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @XmlElement
    public String getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(String balanceDue) {
        this.balanceDue = balanceDue;
    }

    @XmlElement
    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    @XmlElement
    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    @XmlElement
    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @XmlElement
    public String getClientAddress1() {
        return clientAddress1;
    }

    public void setClientAddress1(String clientAddress1) {
        this.clientAddress1 = clientAddress1;
    }

    @XmlElement
    public String getClientAddress2() {
        return clientAddress2;
    }

    public void setClientAddress2(String clientAddress2) {
        this.clientAddress2 = clientAddress2;
    }

    @XmlElement
    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    @XmlElement
    public String getClientState() {
        return clientState;
    }

    public void setClientState(String clientState) {
        this.clientState = clientState;
    }

    @XmlElement
    public String getClientZip() {
        return clientZip;
    }

    public void setClientZip(String clientZip) {
        this.clientZip = clientZip;
    }

    @XmlElement
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    @XmlElement
    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }
    @XmlElement
    public String getVendorAddress1() {
        return vendorAddress1;
    }

    public void setVendorAddress1(String vendorAddress1) {
        this.vendorAddress1 = vendorAddress1;
    }
    @XmlElement
    public String getVendorAddress2() {
        return vendorAddress2;
    }

    public void setVendorAddress2(String vendorAddress2) {
        this.vendorAddress2 = vendorAddress2;
    }
    @XmlElement
    public String getVendorCity() {
        return vendorCity;
    }

    public void setVendorCity(String vendorCity) {
        this.vendorCity = vendorCity;
    }
    @XmlElement
    public String getVendorState() {
        return vendorState;
    }

    public void setVendorState(String vendorState) {
        this.vendorState = vendorState;
    }
    @XmlElement
    public String getVendorZip() {
        return vendorZip;
    }

    public void setVendorZip(String vendorZip) {
        this.vendorZip = vendorZip;
    }

    @XmlElement
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @XmlElement
    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    @XmlElement
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @XmlElement
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @XmlElement
    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    @XmlElement
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @XmlElement
    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoicePdfBean{");
        sb.append("invoiceNumber='").append(invoiceNumber).append('\'');
        sb.append(", imageHost='").append(imageHost).append('\'');
        sb.append(", imagePath='").append(imagePath).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", vendorName='").append(vendorName).append('\'');
        sb.append(", vendorPhone='").append(vendorPhone).append('\'');
        sb.append(", vendorAddress1='").append(vendorAddress1).append('\'');
        sb.append(", vendorAddress2='").append(vendorAddress2).append('\'');
        sb.append(", vendorCity='").append(vendorCity).append('\'');
        sb.append(", vendorState='").append(vendorState).append('\'');
        sb.append(", vendorZip='").append(vendorZip).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
