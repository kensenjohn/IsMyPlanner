package com.events.common.invoice;

import com.events.bean.invoice.InvoiceItemBean;
import com.events.bean.invoice.InvoiceRequestBean;
import com.events.common.Utility;
import com.events.data.invoice.BuildInvoiceItemsData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildInvoiceItems {
    public Integer saveInvoiceItems(InvoiceRequestBean invoiceRequestBean) {
        Integer iNumOfItemsSaved = 0;
        if(invoiceRequestBean!=null && invoiceRequestBean.getArrInvoiceItemBean()!=null &&
                !invoiceRequestBean.getArrInvoiceItemBean().isEmpty() && !Utility.isNullOrEmpty(invoiceRequestBean.getInvoiceId())) {
            String sInvoiceId = invoiceRequestBean.getInvoiceId();
            ArrayList<InvoiceItemBean> arrInvoiceItemBean = invoiceRequestBean.getArrInvoiceItemBean();

            ArrayList<InvoiceItemBean> arrNewInvoiceItemBean = new ArrayList<InvoiceItemBean>();
            for(InvoiceItemBean newInvoiceItemBean : arrInvoiceItemBean ){
                newInvoiceItemBean.setInvoiceId( sInvoiceId );
                newInvoiceItemBean.setInvoiceItemId( Utility.getNewGuid() );
                arrNewInvoiceItemBean.add( newInvoiceItemBean );
            }

            if(arrNewInvoiceItemBean!=null && arrNewInvoiceItemBean.size() == arrInvoiceItemBean.size() ) {
                BuildInvoiceItemsData buildInvoiceItemsData = new BuildInvoiceItemsData();
                buildInvoiceItemsData.deleteInvoiceItems( invoiceRequestBean );

                iNumOfItemsSaved = buildInvoiceItemsData.insertInvoiceItemsBatch( arrNewInvoiceItemBean );
            }
        }
        return iNumOfItemsSaved;
    }
}
