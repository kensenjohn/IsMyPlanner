package com.events.data.invoice;

import com.events.bean.invoice.InvoiceItemBean;
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
 * User: root
 * Date: 3/26/14
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessInvoiceItemsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTINVOICEITEMS( INVOICEITEMID VARCHAR(45) NOT NULL , FK_INVOICEID  VARCHAR(45) NOT NULL, ITEM_NAME TEXT NOT NULL,
    // ITEM_DESCRIPTION TEXT, UNIT_COST  VARCHAR(45) NOT NULL, QUANTITY VARCHAR(45) NOT NULL,
    // DISCOUNT_PERCENTAGE VARCHAR(45), TAX_PERCENTAGE VARCHAR(45)

    public ArrayList<InvoiceItemBean> getInvoiceItems( InvoiceRequestBean invoiceRequestBean) {
        ArrayList<InvoiceItemBean> arrInvoiceItemsBean = new ArrayList<InvoiceItemBean>();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getInvoiceId())) {
            String sQuery  = "SELECT * FROM GTINVOICEITEMS WHERE FK_INVOICEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(invoiceRequestBean.getInvoiceId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessInvoiceItemsData.java", "getInvoiceItems()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    InvoiceItemBean invoiceItemBeanBean = new InvoiceItemBean(hmResult);
                    arrInvoiceItemsBean.add( invoiceItemBeanBean );
                }
            }
        }
        return arrInvoiceItemsBean;
    }
}
