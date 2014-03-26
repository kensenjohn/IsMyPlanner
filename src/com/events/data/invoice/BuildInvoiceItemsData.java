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

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildInvoiceItemsData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTINVOICEITEMS( INVOICEITEMID VARCHAR(45) NOT NULL , FK_INVOICEID  VARCHAR(45) NOT NULL, ITEM_NAME TEXT NOT NULL,
    // ITEM_DESCRIPTION TEXT, UNIT_COST  VARCHAR(45) NOT NULL, QUANTITY VARCHAR(45) NOT NULL,
    // DISCOUNT_PERCENTAGE VARCHAR(45), TAX_PERCENTAGE VARCHAR(45)

    public Integer insertInvoiceItemsBatch( ArrayList<InvoiceItemBean> arrInvoiceItemBean) {
        Integer numOfRowsDeleted = 0;
        if(arrInvoiceItemBean!=null && !arrInvoiceItemBean.isEmpty()){
            String sQuery = "INSERT INTO GTINVOICEITEMS (INVOICEITEMID,FK_INVOICEID,ITEM_NAME,     " +
                    " ITEM_DESCRIPTION,UNIT_COST,QUANTITY,    DISCOUNT_PERCENTAGE,TAX_PERCENTAGE) VALUES" +
                    " (?,?,?,   ?,?,?,    ?,?)";
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            for(InvoiceItemBean invoiceItemBean : arrInvoiceItemBean ) {
                ArrayList<Object> aParams = DBDAO.createConstraint(invoiceItemBean.getInvoiceItemId(),invoiceItemBean.getInvoiceId(),invoiceItemBean.getItemName(),
                        invoiceItemBean.getItemDescription(),invoiceItemBean.getUnitCost(),invoiceItemBean.getQuantity(),
                        invoiceItemBean.getDiscountPercentage(),invoiceItemBean.getTaxPercentage());
                aBatchParams.add( aParams );
            }
            int[] numOfRowsAffected = DBDAO.putBatchRowsQuery( sQuery, aBatchParams, EVENTADMIN_DB, "BuildInvoiceItemsData.java", "insertInvoiceItemsBatch() " );

            if(numOfRowsAffected!=null && numOfRowsAffected.length > 0 ) {
                for(int iCount : numOfRowsAffected ) {
                    numOfRowsDeleted = numOfRowsDeleted + iCount;
                }
            }
        }
        return numOfRowsDeleted;

    }

    public Integer deleteInvoiceItems( InvoiceRequestBean invoiceRequestBean) {
        Integer numOfRowsDeleted = 0;
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getInvoiceId())){
            String sQuery = "DELETE FROM GTINVOICEITEMS WHERE FK_INVOICEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(invoiceRequestBean.getInvoiceId());
            ArrayList< ArrayList<Object> > aBatchParams = new ArrayList<ArrayList<Object>>();
            numOfRowsDeleted = DBDAO.putRowsQuery( sQuery, aParams, EVENTADMIN_DB, "BuildInvoiceItemsData.java", "deleteInvoiceItems() " );
        }
        return numOfRowsDeleted;

    }
}
