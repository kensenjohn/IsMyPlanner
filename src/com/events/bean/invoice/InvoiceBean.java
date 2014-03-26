package com.events.bean.invoice;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceBean {
    //CREATE TABLE GTINVOICES(INVOICEID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL,
    // FK_CLIENTID VARCHAR(45) NOT NULL, INVOICE_NUMBER  VARCHAR(250) NOT NULL,  CONTRACT_PO_NUMBER  VARCHAR(250),
    // INVOICEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANINVOICEDATE VARCHAR(45), DUEDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANDUEDATE VARCHAR(45), MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45),
    // DISCOUNT_PERCENTAGE VARCHAR(45), TAX_PERCENTAGE VARCHAR(45),TERMS_CONDITIONS VARCHAR(100),  NOTE TEXT,
    // STATUS VARCHAR(100) NOT NULL, DEL_ROW INT(1) NOT NULL DEFAULT 0, PRIMARY KEY (INVOICEID) )
    // ENGINE = MyISAM DEFAULT CHARSET = utf8;


    public InvoiceBean() {
    }

    public InvoiceBean(HashMap<String, String> hmInvoiceResult) {
        this.invoiceId = ParseUtil.checkNull(hmInvoiceResult.get("INVOICEID"));
        this.userId = ParseUtil.checkNull(hmInvoiceResult.get("FK_USERID"));
        this.vendorId = ParseUtil.checkNull(hmInvoiceResult.get("FK_VENDORID"));
        this.clientId = ParseUtil.checkNull(hmInvoiceResult.get("FK_CLIENTID"));
        this.invoiceNumber = ParseUtil.checkNull(hmInvoiceResult.get("INVOICE_NUMBER"));
        this.contractPONumber = ParseUtil.checkNull(hmInvoiceResult.get("CONTRACT_PO_NUMBER"));
        this.invoiceDate = ParseUtil.sToL(hmInvoiceResult.get("INVOICEDATE"));
        this.humanInvoiceDate = ParseUtil.checkNull(hmInvoiceResult.get("HUMANINVOICEDATE"));
        this.dueDate = ParseUtil.sToL(hmInvoiceResult.get("DUEDATE"));
        this.humanDueDate = ParseUtil.checkNull(hmInvoiceResult.get("HUMANDUEDATE"));
        this.discountPercentage = ParseUtil.sToD(hmInvoiceResult.get("DISCOUNT_PERCENTAGE"));
        this.taxPercentage = ParseUtil.sToD(hmInvoiceResult.get("TAX_PERCENTAGE"));
        this.termsAndConditions = ParseUtil.checkNull(hmInvoiceResult.get("TERMS_CONDITIONS"));
        this.note = ParseUtil.checkNull(hmInvoiceResult.get("NOTE"));
        this.deleteRow = ParseUtil.sTob(hmInvoiceResult.get("DEL_ROW"));
        this.status = ParseUtil.checkNull(hmInvoiceResult.get("STATUS"));
    }

    private String invoiceId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private String invoiceNumber = Constants.EMPTY;
    private String contractPONumber = Constants.EMPTY;
    private Long invoiceDate = 0L;
    private String humanInvoiceDate = Constants.EMPTY;
    private Long dueDate = 0L;
    private String humanDueDate = Constants.EMPTY;
    private Double discountPercentage = 0.00;
    private Double taxPercentage = 0.00;
    private String termsAndConditions = Constants.EMPTY;
    private String note = Constants.EMPTY;
    private boolean  deleteRow = false;
    private String status = Constants.EMPTY;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeleteRow() {
        return deleteRow;
    }

    public void setDeleteRow(boolean deleteRow) {
        this.deleteRow = deleteRow;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getContractPONumber() {
        return contractPONumber;
    }

    public void setContractPONumber(String contractPONumber) {
        this.contractPONumber = contractPONumber;
    }

    public Long getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Long invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getHumanInvoiceDate() {
        return humanInvoiceDate;
    }

    public void setHumanInvoiceDate(String humanInvoiceDate) {
        this.humanInvoiceDate = humanInvoiceDate;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public String getHumanDueDate() {
        return humanDueDate;
    }

    public void setHumanDueDate(String humanDueDate) {
        this.humanDueDate = humanDueDate;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceBean{");
        sb.append("invoiceId='").append(invoiceId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", vendorId='").append(vendorId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", invoiceNumber='").append(invoiceNumber).append('\'');
        sb.append(", contractPONumber='").append(contractPONumber).append('\'');
        sb.append(", invoiceDate=").append(invoiceDate);
        sb.append(", humanInvoiceDate='").append(humanInvoiceDate).append('\'');
        sb.append(", dueDate=").append(dueDate);
        sb.append(", humanDueDate='").append(humanDueDate).append('\'');
        sb.append(", discountPercentage=").append(discountPercentage);
        sb.append(", taxPercentage=").append(taxPercentage);
        sb.append(", termsAndConditions='").append(termsAndConditions).append('\'');
        sb.append(", note='").append(note).append('\'');
        sb.append(", deleteRow=").append(deleteRow);
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("invoice_id", this.invoiceId );
            jsonObject.put("client_id", this.clientId );
            jsonObject.put("vendor_id", this.vendorId );
            jsonObject.put("user_id", this.userId );
            jsonObject.put("invoice_number", this.invoiceNumber );
            jsonObject.put("contract_po_number", this.contractPONumber );
            jsonObject.put("invoice_date", this.invoiceDate );
            jsonObject.put("human_invoice_date", this.humanInvoiceDate );
            jsonObject.put("due_date", this.dueDate );
            jsonObject.put("human_due_date", this.humanDueDate );
            jsonObject.put("discount_percentage", this.discountPercentage );
            jsonObject.put("tax_percentage", this.taxPercentage );
            jsonObject.put("terms_and_conditions", this.termsAndConditions);
            jsonObject.put("note", this.note );
            jsonObject.put("delete_row", this.deleteRow );
            jsonObject.put("status", this.status );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
