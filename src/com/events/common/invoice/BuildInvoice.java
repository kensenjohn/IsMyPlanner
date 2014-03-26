package com.events.common.invoice;

import com.events.bean.DateObject;
import com.events.bean.invoice.InvoiceBean;
import com.events.bean.invoice.InvoiceItemBean;
import com.events.bean.invoice.InvoiceRequestBean;
import com.events.bean.invoice.InvoiceResponseBean;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.invoice.BuildInvoiceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildInvoice {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public InvoiceResponseBean saveInvoice(InvoiceRequestBean invoiceRequestBean){
        InvoiceResponseBean invoiceResponseBean = new InvoiceResponseBean();
        if(invoiceRequestBean!=null && invoiceRequestBean.getInvoiceBean()!=null && invoiceRequestBean.getArrInvoiceItemBean()!=null &&
                !invoiceRequestBean.getArrInvoiceItemBean().isEmpty()) {
            InvoiceBean invoiceBean = invoiceRequestBean.getInvoiceBean();
            if(invoiceBean!=null){
                String sInvoiceDate = invoiceBean.getHumanInvoiceDate();
                DateObject invoiceDate = DateSupport.convertTime( sInvoiceDate + " 00:00 AM",
                        DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), "dd MMMMM, yyyy hh:mm aaa",
                        DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);
                invoiceBean.setInvoiceDate(invoiceDate.getMillis() );

                String sInvoiceDueDate = invoiceBean.getHumanDueDate();
                DateObject dueDate = DateSupport.convertTime( sInvoiceDueDate + " 00:00 AM",
                        DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), "dd MMMMM, yyyy hh:mm aaa",
                        DateSupport.getTimeZone(Constants.DEFAULT_TIMEZONE), Constants.DATE_PATTERN_TZ);
                invoiceBean.setDueDate(dueDate.getMillis() );


                if(Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
                    invoiceBean.setInvoiceId( Utility.getNewGuid());
                    invoiceBean = createInvoice( invoiceBean );
                }else {
                    invoiceBean = updateInvoice( invoiceBean );
                }

                if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())){
                    invoiceRequestBean.setInvoiceId(invoiceBean.getInvoiceId() );
                    BuildInvoiceItems buildInvoiceItems = new BuildInvoiceItems();
                    buildInvoiceItems.saveInvoiceItems( invoiceRequestBean );
                    invoiceResponseBean.setInvoiceBean( invoiceBean );
                    invoiceResponseBean.setInvoiceId( invoiceBean.getInvoiceId() );
                }

            }
        }
        return invoiceResponseBean;
    }
    public InvoiceBean createInvoice(InvoiceBean invoiceBean) {
        if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
            invoiceBean.setStatus("New");
            BuildInvoiceData buildInvoiceData = new BuildInvoiceData();
            Integer numOfRowsInserted = buildInvoiceData.insertInvoice( invoiceBean );
            if(numOfRowsInserted<=0){
                appLogging.error("Invoice was not created : " + invoiceBean );
                invoiceBean = new InvoiceBean();
            }
        } else {
            appLogging.error("Invalid Invoice Request Used for inserts: " + ParseUtil.checkNullObject(invoiceBean));
        }
        return invoiceBean;
    }
    public InvoiceBean updateInvoice(InvoiceBean invoiceBean){
        if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
            BuildInvoiceData buildInvoiceData = new BuildInvoiceData();
            Integer numOfRowsInserted = buildInvoiceData.updateInvoice(invoiceBean);
            if(numOfRowsInserted<=0){
                appLogging.error("Invoice was not Update : " + invoiceBean );
                invoiceBean = new InvoiceBean();
            }
        } else {
            appLogging.error("Invalid Invoice Request Used for update : " + ParseUtil.checkNullObject(invoiceBean));
        }
        return invoiceBean;
    }
}
