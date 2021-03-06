package com.events.bean.invoice;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlElement;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceItemBean {
    // CREATE TABLE GTINVOICEITEMS( INVOICEITEMID VARCHAR(45) NOT NULL , FK_INVOICEID  VARCHAR(45) NOT NULL,
    // ITEM_NAME TEXT NOT NULL,ITEM_DESCRIPTION TEXT, UNIT_COST  VARCHAR(45) NOT NULL, QUANTITY VARCHAR(45) NOT NULL,
    // DISCOUNT_PERCENTAGE VARCHAR(45), TAX_PERCENTAGE VARCHAR(45),  PRIMARY KEY (INVOICEITEMID) )
    // ENGINE = MyISAM DEFAULT CHARSET = utf8;


    public InvoiceItemBean() {
    }

    public InvoiceItemBean( HashMap<String,String> hmInvoiceItemResult) {
        this.invoiceItemId =  ParseUtil.checkNull(hmInvoiceItemResult.get("INVOICEITEMID"));
        this.invoiceId = ParseUtil.checkNull(hmInvoiceItemResult.get("FK_INVOICEID"));
        this.itemName = ParseUtil.checkNull(hmInvoiceItemResult.get("ITEM_NAME"));
        this.itemDescription = ParseUtil.checkNull(hmInvoiceItemResult.get("ITEM_DESCRIPTION"));
        this.unitCost = ParseUtil.sToD(hmInvoiceItemResult.get("UNIT_COST"));
        this.quantity = ParseUtil.sToL(hmInvoiceItemResult.get("QUANTITY"));
        this.discountPercentage = ParseUtil.sToD(hmInvoiceItemResult.get("DISCOUNT_PERCENTAGE"));
        this.taxPercentage = ParseUtil.sToD(hmInvoiceItemResult.get("TAX_PERCENTAGE"));
    }

    private String invoiceItemId = Constants.EMPTY;
    private String invoiceId = Constants.EMPTY;
    private String itemName = Constants.EMPTY;
    private String itemDescription = Constants.EMPTY;
    private Double unitCost = 0.00;
    private Long quantity = 0L;
    private Double discountPercentage = 0.00;
    private Double taxPercentage = 0.00;
    private Double total = 0.00;

    private String quantityDisplay = Constants.EMPTY;
    private String unitCostDisplay = Constants.EMPTY;
    private String discountPercentageDisplay = Constants.EMPTY;
    private String taxPercentageDisplay = Constants.EMPTY;
    private String totalDisplay = Constants.EMPTY;

    @XmlElement
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @XmlElement
    public String getQuantityDisplay() {
        return quantityDisplay;
    }

    public void setQuantityDisplay(String quantityDisplay) {
        this.quantityDisplay = quantityDisplay;
    }

    @XmlElement
    public String getUnitCostDisplay() {
        return unitCostDisplay;
    }

    public void setUnitCostDisplay(String unitCostDisplay) {
        this.unitCostDisplay = unitCostDisplay;
    }

    @XmlElement
    public String getDiscountPercentageDisplay() {
        return discountPercentageDisplay;
    }

    public void setDiscountPercentageDisplay(String discountPercentageDisplay) {
        this.discountPercentageDisplay = discountPercentageDisplay;
    }

    @XmlElement
    public String getTaxPercentageDisplay() {
        return taxPercentageDisplay;
    }

    public void setTaxPercentageDisplay(String taxPercentageDisplay) {
        this.taxPercentageDisplay = taxPercentageDisplay;
    }

    @XmlElement
    public String getTotalDisplay() {
        return totalDisplay;
    }

    public void setTotalDisplay(String totalDisplay) {
        this.totalDisplay = totalDisplay;
    }

    @XmlElement
    public String getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(String invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    @XmlElement
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    @XmlElement
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @XmlElement
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @XmlElement
    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    @XmlElement
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @XmlElement
    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @XmlElement
    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceItemBean{");
        sb.append("invoiceItemId='").append(invoiceItemId).append('\'');
        sb.append(", invoiceId='").append(invoiceId).append('\'');
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", itemDescription='").append(itemDescription).append('\'');
        sb.append(", unitCost=").append(unitCost);
        sb.append(", quantity=").append(quantity);
        sb.append(", discountPercentage=").append(discountPercentage);
        sb.append(", taxPercentage=").append(taxPercentage);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("invoice_item_id", this.invoiceItemId );
            jsonObject.put("invoice_id", this.invoiceId );
            jsonObject.put("item_name", this.itemName );
            jsonObject.put("item_description", this.itemDescription );
            jsonObject.put("unit_cost", this.unitCost );
            jsonObject.put("quantity", this.quantity );
            jsonObject.put("discount_percentage", this.discountPercentage );
            jsonObject.put("tax_percentage", this.taxPercentage );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
