package com.events.common.invoice;

import com.events.bean.invoice.InvoiceBean;
import com.events.bean.invoice.InvoiceRequestBean;
import com.events.bean.invoice.InvoiceResponseBean;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.invoice.AccessInvoiceData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessInvoice {
    public InvoiceResponseBean getVendorInvoices(InvoiceRequestBean invoiceRequestBean) {
        InvoiceResponseBean invoiceResponseBean = new InvoiceResponseBean();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getVendorId()) ) {
            AccessInvoiceData accessInvoiceData = new AccessInvoiceData();
            ArrayList<InvoiceBean> arrInvoiceBean = accessInvoiceData.getVendorInvoices( invoiceRequestBean );
            if(arrInvoiceBean!=null && !arrInvoiceBean.isEmpty()) {
                invoiceResponseBean.setArrInvoiceBean( arrInvoiceBean );
            }
        }
        return invoiceResponseBean;
    }

    public InvoiceResponseBean getClientInvoices(InvoiceRequestBean invoiceRequestBean) {
        InvoiceResponseBean invoiceResponseBean = new InvoiceResponseBean();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getClientId()) ) {
            AccessInvoiceData accessInvoiceData = new AccessInvoiceData();
            ArrayList<InvoiceBean> arrInvoiceBean = accessInvoiceData.getClientInvoices(invoiceRequestBean);
            if(arrInvoiceBean!=null && !arrInvoiceBean.isEmpty()) {
                invoiceResponseBean.setArrInvoiceBean( arrInvoiceBean );
            }
        }
        return invoiceResponseBean;
    }

    public InvoiceResponseBean getInvoice(InvoiceRequestBean invoiceRequestBean) {
        InvoiceResponseBean invoiceResponseBean = new InvoiceResponseBean();
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getInvoiceId()) ) {
            AccessInvoiceData accessInvoiceData = new AccessInvoiceData();
            InvoiceBean invoiceBean = accessInvoiceData.getInvoice(invoiceRequestBean);
            if(invoiceBean!=null) {
                invoiceResponseBean.setInvoiceBean( invoiceBean );
            }
        }
        return invoiceResponseBean;
    }

    public JSONObject getInvoicesBeanJson(ArrayList<InvoiceBean> arrInvoiceBean) {
        JSONObject jsonInvoices = new JSONObject();
        if(arrInvoiceBean!=null && !arrInvoiceBean.isEmpty()) {
            Long iNumOfInvoices = 0L;
            for(InvoiceBean invoiceBean : arrInvoiceBean) {
                jsonInvoices.put(ParseUtil.LToS(iNumOfInvoices), invoiceBean.toJson());
                iNumOfInvoices++;
            }
            jsonInvoices.put("num_of_invoices",iNumOfInvoices);
        }
        return jsonInvoices;
    }
}
