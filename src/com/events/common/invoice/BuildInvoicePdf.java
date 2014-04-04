package com.events.common.invoice;

import com.events.bean.invoice.InvoiceBean;
import com.events.bean.invoice.InvoiceItemBean;
import com.events.bean.invoice.InvoiceRequestBean;
import com.events.bean.invoice.InvoiceResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Folder;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/3/14
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildInvoicePdf {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void buildInvoicePdf(InvoiceRequestBean invoiceRequestBean, UserBean userBean){
        if(invoiceRequestBean!=null && !Utility.isNullOrEmpty(invoiceRequestBean.getInvoiceId())) {
            AccessInvoice accessInvoice = new AccessInvoice();
            InvoiceResponseBean invoiceResponseBean = accessInvoice.getInvoice(invoiceRequestBean);
            if(invoiceResponseBean!=null){
                InvoiceBean invoiceBean = invoiceResponseBean.getInvoiceBean();
                if(invoiceBean!=null){
                    AccessInvoiceItems accessInvoiceItems = new AccessInvoiceItems();
                    invoiceResponseBean = accessInvoiceItems.getInvoiceItems(invoiceRequestBean  ) ;
                    if(invoiceResponseBean!=null){
                        ArrayList<InvoiceItemBean> arrInvoiceItemsBean = invoiceResponseBean.getArrInvoiceItemsBean();

                        if(arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()) {
                            appLogging.info("Going to invoke create PDF ");
                            createPdf(invoiceBean, arrInvoiceItemsBean, userBean);

                        }
                    }
                }
            }
        }
    }

    private void createPdf(InvoiceBean invoiceBean , ArrayList<InvoiceItemBean> arrInvoiceItemsBean,UserBean userBean){
        if(invoiceBean!=null && arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()) {
            AccessInvoicePdf accessInvoicePdf = new AccessInvoicePdf();
            try{
                PDDocument pdfDocument = new PDDocument();
                PDPage pdgPage = new PDPage();
                pdfDocument.addPage(pdgPage);
                generateInvoiceContent(invoiceBean,arrInvoiceItemsBean, userBean, pdfDocument, pdgPage );
                pdfDocument.save( accessInvoicePdf.getInvoicePdfLocation(userBean, invoiceBean.getInvoiceId()) ) ;
                pdfDocument.close();
            } catch (IOException ie){
                appLogging.info("IOException : " + ExceptionHandler.getStackTrace(ie));
            } catch (Exception io){
                appLogging.info("Exception : " + ExceptionHandler.getStackTrace(io));
            }
        }
    }

    private void generateInvoiceContent(InvoiceBean invoiceBean , ArrayList<InvoiceItemBean> arrInvoiceItemsBean,UserBean userBean,
                                        PDDocument pdfDocument,PDPage pdgPage) throws IOException {
        if(invoiceBean!=null && arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()) {
            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream content = new PDPageContentStream(pdfDocument, pdgPage);
            content.beginText();
            content.setFont( font, 12 );
            content.moveTextPositionByAmount( 100, 700 );
            content.drawString("Invoice Number : " + invoiceBean.getInvoiceNumber());

            content.endText();
            content.close();
        }

    }
}
