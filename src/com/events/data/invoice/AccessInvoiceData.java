package com.events.data.invoice;

import com.events.bean.invoice.InvoiceBean;
import com.events.bean.invoice.InvoiceRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessInvoiceData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    // GTINVOICES(INVOICEID VARCHAR(45) NOT NULL, FK_USERID VARCHAR(45) NOT NULL, FK_CLIENTID VARCHAR(45) NOT NULL,
    // INVOICE_NUMBER  VARCHAR(250) NOT NULL,  CONTRACT_PO_NUMBER  VARCHAR(250), INVOICEDATE BIGINT(20) NOT NULL DEFAULT 0,
    // HUMANINVOICEDATE VARCHAR(45), DUEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANDUEDATE VARCHAR(45),
    // MODIFIEDDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANMODIFIEDDATE VARCHAR(45), DISCOUNT_PERCENTAGE VARCHAR(45),
    // TAX_PERCENTAGE VARCHAR(45),TERMS_CONDITIONS VARCHAR(100),  NOTE TEXT, STATUS VARCHAR(100) NOT NULL,
    // DEL_ROW INT(1) NOT NULL DEFAULT 0
    public ArrayList<InvoiceBean> getVendorInvoices(InvoiceRequestBean invoiceRequestBean) {
        ArrayList<InvoiceBean> arrInvoiceBean = new ArrayList<InvoiceBean>();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getVendorId())) {
            String sQuery  = "SELECT * FROM GTINVOICES WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(invoiceRequestBean.getVendorId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessInvoiceData.java", "getVendorInvoices()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    InvoiceBean invoiceBean = new InvoiceBean(hmResult);
                    arrInvoiceBean.add( invoiceBean );
                }
            }
        }
        return arrInvoiceBean;
    }

    public ArrayList<InvoiceBean> getClientInvoices(InvoiceRequestBean invoiceRequestBean) {
        ArrayList<InvoiceBean> arrInvoiceBean = new ArrayList<InvoiceBean>();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getClientId())) {
            String sQuery  = "SELECT * FROM GTINVOICES WHERE FK_CLIENTID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(invoiceRequestBean.getClientId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessInvoiceData.java", "getClientInvoices()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    InvoiceBean invoiceBean = new InvoiceBean(hmResult);
                    arrInvoiceBean.add( invoiceBean );
                }
            }
        }
        return arrInvoiceBean;
    }

    public InvoiceBean getInvoice(InvoiceRequestBean invoiceRequestBean) {
        InvoiceBean invoiceBean = new InvoiceBean();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getInvoiceId())) {
            String sQuery  = "SELECT * FROM GTINVOICES WHERE INVOICEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(invoiceRequestBean.getInvoiceId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessInvoiceData.java", "getInvoice()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    invoiceBean = new InvoiceBean(hmResult);
                }
            }
        }
        return invoiceBean;
    }
}
