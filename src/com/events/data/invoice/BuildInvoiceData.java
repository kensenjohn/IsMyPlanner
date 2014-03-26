package com.events.data.invoice;

import com.events.bean.invoice.InvoiceBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildInvoiceData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    // GTINVOICES(INVOICEID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL, FK_CLIENTID VARCHAR(45) NOT NULL,
    // INVOICE_NUMBER  VARCHAR(250) NOT NULL,  CONTRACT_PO_NUMBER  VARCHAR(250), INVOICEDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANINVOICEDATE VARCHAR(45), DUEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANDUEDATE VARCHAR(45),
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45), DISCOUNT_PERCENTAGE VARCHAR(45),
    // TAX_PERCENTAGE VARCHAR(45),TERMS_CONDITIONS VARCHAR(100),  NOTE TEXT, STATUS VARCHAR(100) NOT NULL,
    // DEL_ROW INT(1) NOT NULL DEFAULT 0
    public Integer insertInvoice(InvoiceBean invoiceBean) {
        Integer numOfRowsInserted = 0;

        if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId()) && !Utility.isNullOrEmpty(invoiceBean.getClientId())
                && !Utility.isNullOrEmpty(invoiceBean.getUserId() )){
            String sQuery = "INSERT INTO GTINVOICES (INVOICEID,FK_USERID,FK_CLIENTID,     INVOICE_NUMBER,CONTRACT_PO_NUMBER,INVOICEDATE, " +
                    "   HUMANINVOICEDATE,DUEDATE,HUMANDUEDATE,     MODIFIEDDATE,HUMANMODIFIEDDATE,DISCOUNT_PERCENTAGE,    " +
                    "   TAX_PERCENTAGE,TERMS_CONDITIONS,NOTE,      STATUS, DEL_ROW,FK_VENDORID ) VALUES (?,?,?,   ?,?,?,   ?,?,?,   ?,?,?,  ?,?,?,  ?,?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(invoiceBean.getInvoiceId(),invoiceBean.getUserId(),invoiceBean.getClientId(),
                    invoiceBean.getInvoiceNumber(),invoiceBean.getContractPONumber(),invoiceBean.getInvoiceDate(),
                    invoiceBean.getHumanInvoiceDate(),invoiceBean.getDueDate(),invoiceBean.getHumanDueDate(),
                    DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),invoiceBean.getDiscountPercentage(),
                    invoiceBean.getTaxPercentage(),invoiceBean.getTermsAndConditions(),invoiceBean.getNote(),
                    invoiceBean.getStatus(),invoiceBean.isDeleteRow(),invoiceBean.getVendorId() );

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildInvoiceData.java", "insertInvoice() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateInvoice(InvoiceBean invoiceBean) {
        Integer numOfRowsInserted = 0;

        if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId()) && !Utility.isNullOrEmpty(invoiceBean.getClientId())
                && !Utility.isNullOrEmpty(invoiceBean.getUserId() )){
            String sQuery = "UPDATE GTINVOICES SET FK_USERID = ?,FK_CLIENTID = ?,     INVOICE_NUMBER = ?,CONTRACT_PO_NUMBER = ?,INVOICEDATE = ?, " +
                    "   HUMANINVOICEDATE = ?,DUEDATE = ?,HUMANDUEDATE = ?,     MODIFIEDDATE = ?,HUMANMODIFIEDDATE = ?,DISCOUNT_PERCENTAGE  = ?,    " +
                    "   TAX_PERCENTAGE = ?,TERMS_CONDITIONS = ?,NOTE = ?,      STATUS = ?, DEL_ROW = ?,FK_VENDORID = ? " +
                    "   WHERE INVOICEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(invoiceBean.getUserId(),invoiceBean.getClientId(),
                    invoiceBean.getInvoiceNumber(),invoiceBean.getContractPONumber(),invoiceBean.getInvoiceDate(),
                    invoiceBean.getHumanInvoiceDate(),invoiceBean.getDueDate(),invoiceBean.getHumanDueDate(),
                    DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),invoiceBean.getDiscountPercentage(),
                    invoiceBean.getTaxPercentage(),invoiceBean.getTermsAndConditions(),invoiceBean.getNote(),
                    invoiceBean.getStatus(),invoiceBean.isDeleteRow(),invoiceBean.getVendorId(),
                    invoiceBean.getInvoiceId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildInvoiceData.java", "updateInvoice() ");
        }
        return  numOfRowsInserted;
    }
}
