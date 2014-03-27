package com.events.common.invoice;

import com.events.bean.invoice.InvoiceItemBean;
import com.events.bean.invoice.InvoiceRequestBean;
import com.events.bean.invoice.InvoiceResponseBean;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.invoice.AccessInvoiceItemsData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessInvoiceItems {
    public InvoiceResponseBean getInvoiceItems(InvoiceRequestBean invoiceRequestBean){
        InvoiceResponseBean invoiceResponseBean = new InvoiceResponseBean();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getInvoiceId())) {
            AccessInvoiceItemsData accessInvoiceItemsData = new AccessInvoiceItemsData();
            ArrayList<InvoiceItemBean> arrInvoiceItemsBean = accessInvoiceItemsData.getInvoiceItems( invoiceRequestBean );
            if(arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()) {
                invoiceResponseBean.setArrInvoiceItemsBean( arrInvoiceItemsBean );
            }
        }
        return invoiceResponseBean;
    }

    public JSONObject getInvoiceItemsJson(ArrayList<InvoiceItemBean> arrInvoiceItemsBean){
        JSONObject jsonInvoiceItems = new JSONObject();
        if(arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()) {
            Long lNumOfItems = 0L;
            for(InvoiceItemBean invoiceItemBean : arrInvoiceItemsBean ){
                jsonInvoiceItems.put(ParseUtil.LToS(lNumOfItems) , invoiceItemBean.toJson());
                lNumOfItems++;
            }
            jsonInvoiceItems.put( "num_of_invoice_items", lNumOfItems );
        }
        return jsonInvoiceItems;
    }
}
