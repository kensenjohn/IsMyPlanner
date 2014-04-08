package com.events.common.invoice;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.invoice.*;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.clients.AccessClients;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.users.AccessUsers;
import com.events.vendors.AccessVendors;
import com.events.vendors.website.AccessVendorWebsite;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.xmlgraphics.util.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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

    public void generateInvoicePdf(InvoicePdfRequestBean invoicePdfRequestBean) throws Exception{
        if(invoicePdfRequestBean!=null && !Utility.isNullOrEmpty(invoicePdfRequestBean.getInvoiceId())) {

            InvoiceRequestBean invoiceRequestBean = new InvoiceRequestBean();
            invoiceRequestBean.setInvoiceId(invoicePdfRequestBean.getInvoiceId());


            AccessInvoice accessInvoice = new AccessInvoice();
            InvoiceResponseBean invoiceResponseBean = accessInvoice.getInvoice(invoiceRequestBean);

            if(invoiceResponseBean!=null){
                InvoiceBean invoiceBean = invoiceResponseBean.getInvoiceBean();
                if(invoiceBean!=null){
                    invoicePdfRequestBean.setInvoiceBean( invoiceBean );
                    InvoicePdfBean invoicePdfBean = new InvoicePdfBean();
                    invoicePdfBean.setInvoiceNumber(  invoiceBean.getInvoiceNumber() );
                    invoicePdfBean.setLogo( ParseUtil.checkNull(invoicePdfRequestBean.getLogo()) );
                    invoicePdfBean.setInvoiceDate( ParseUtil.checkNull(invoiceBean.getHumanInvoiceDate()) );
                    invoicePdfBean.setDueDate( ParseUtil.checkNull(invoiceBean.getHumanDueDate()) );
                    invoicePdfBean.setBalanceDue( ParseUtil.checkNull(invoiceBean.getInvoiceId()) );
                    invoicePdfBean.setTerms(  ParseUtil.checkNull(invoiceBean.getTermsAndConditions())  );
                    invoicePdfBean.setNote(   ParseUtil.checkNull(invoiceBean.getNote())  );
                    invoicePdfBean.setPoNumber(  ParseUtil.checkNull(invoiceBean.getContractPONumber()) );

                    UserBean userBean = invoicePdfRequestBean.getUserBean();

                    AccessUsers accessUsers = new AccessUsers();
                    ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser( userBean );

                    if(parentTypeBean!=null && parentTypeBean.getClientBean()!=null && Utility.isNullOrEmpty(parentTypeBean.getClientdId() )) {

                        ClientRequestBean clientRequestBean = new ClientRequestBean();
                        clientRequestBean.setClientId( invoiceBean.getClientId() );
                        clientRequestBean.setVendorId( invoiceBean.getVendorId() );

                        AccessClients accessClients = new AccessClients();
                        ClientBean clientBean = accessClients.getClient( clientRequestBean );
                        parentTypeBean.setClientBean(clientBean );

                    }
                    invoicePdfRequestBean.setParentTypeBean( parentTypeBean );

                    generateClientContact(invoicePdfBean, invoicePdfRequestBean);
                    generateVendorContact(invoicePdfBean, invoicePdfRequestBean );
                    generateInvoiceItems( invoicePdfBean, invoicePdfRequestBean );

                    JAXBContext jaxbInvoicePdfContext = JAXBContext.newInstance(InvoicePdfBean.class);
                    Marshaller jaxbInvoicePdfMarshaller = jaxbInvoicePdfContext.createMarshaller();

                    StringWriter stringInvoicePdfWriter = new StringWriter();
                    jaxbInvoicePdfMarshaller.marshal(invoicePdfBean , stringInvoicePdfWriter );


                    StreamSource invoiceXmlSource = new StreamSource();
                    if(stringInvoicePdfWriter!=null){
                        appLogging.info(  " Xml String - " + stringInvoicePdfWriter.toString());
                        InputStream inpStreamInvoicePdf = new ByteArrayInputStream(stringInvoicePdfWriter.toString().getBytes("UTF-8"));
                        invoiceXmlSource.setInputStream( inpStreamInvoicePdf );
                    }


                    // the XSL FO file
                    File invoiceCslFile = new File("/usr/uploadloc/invoices_basic.xsl");
                    FileInputStream fis = null;

                    try {
                        fis = new FileInputStream(invoiceCslFile);
                        appLogging.info("Total file size to read (in bytes) : "
                                + fis.available());

                        int content;
                        String fileText = Constants.EMPTY;
                        while ((content = fis.read()) != -1) {
                            // convert to char and display it
                            fileText = fileText +(char) content;
                        }
                        appLogging.info("fileText : " + fileText );

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // creation of transform source
                    StreamSource transformInvoicePdfSource = new StreamSource(invoiceCslFile);
                    // create an instance of fop factory
                    FopFactory fopFactory = FopFactory.newInstance();
                     // a user agent is needed for transformation
                    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
                    // to store output
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();


                    Transformer xslfoTransformer;
                    try
                    {
                        xslfoTransformer = getTransformer(transformInvoicePdfSource);
                        // Construct fop with desired output format
                        Fop fop;
                        try
                        {
                            fop = fopFactory.newFop (MimeConstants.MIME_PDF, foUserAgent, outStream);
                            // Resulting SAX events (the generated FO)
                            // must be piped through to FOP
                            Result res = new SAXResult(fop.getDefaultHandler());

                            // Start XSLT transformation and FOP processing
                            try
                            {
                                appLogging.info(ParseUtil.checkNullObject(xslfoTransformer) + " - " + res);
                                // everything will happen here..
                                xslfoTransformer.transform(invoiceXmlSource, res);
                                // if you want to get the PDF bytes, use the following code
                                //return outStream.toByteArray();
                                // if you want to save PDF file use the following code
                                AccessInvoicePdf accessInvoicePdf = new AccessInvoicePdf();
			File pdffile = new File(accessInvoicePdf.getInvoicePdfLocation( invoicePdfRequestBean ));
			OutputStream out = new java.io.FileOutputStream(pdffile);
                        out = new java.io.BufferedOutputStream(out);
                        FileOutputStream str = new FileOutputStream(pdffile);
                        str.write(outStream.toByteArray());
                        str.close();
                        out.close();

                                appLogging.info("location : " + accessInvoicePdf.getInvoicePdfLocation( invoicePdfRequestBean ) );
                                // to write the content to out put stream
                                //byte[] pdfBytes = outStream.toByteArray();
                                /*response.setContentLength(pdfBytes.length);
                                response.setContentType("application/pdf");
                                response.addHeader("Content-Disposition",
                                        "attachment;filename=pdffile.pdf");
                                response.getOutputStream().write(pdfBytes);
                                response.getOutputStream().flush();*/
                            }
                            catch (TransformerException e) {
                                throw e;
                            }
                        }
                        catch (FOPException e) {
                            throw e;
                        }
                    }
                    catch (TransformerConfigurationException e)
                    {
                        throw e;
                    }
                    catch (TransformerFactoryConfigurationError e)
                    {
                        throw e;
                    }

                }
            }
        }
    }

    private Transformer getTransformer(StreamSource streamSource) {

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(streamSource);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return transformer;
    }

    public void buildInvoicePdf(InvoicePdfRequestBean invoicePdfRequestBean){
        if(invoicePdfRequestBean!=null && !Utility.isNullOrEmpty(invoicePdfRequestBean.getInvoiceId())) {

            InvoiceRequestBean invoiceRequestBean = new InvoiceRequestBean();
            invoiceRequestBean.setInvoiceId(invoicePdfRequestBean.getInvoiceId());


            AccessInvoice accessInvoice = new AccessInvoice();
            InvoiceResponseBean invoiceResponseBean = accessInvoice.getInvoice(invoiceRequestBean);
            if(invoiceResponseBean!=null){
                InvoiceBean invoiceBean = invoiceResponseBean.getInvoiceBean();
                if(invoiceBean!=null){

                    UserBean userBean = invoicePdfRequestBean.getUserBean();

                    AccessUsers accessUsers = new AccessUsers();
                    ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser( userBean );

                    if(parentTypeBean!=null && parentTypeBean.getClientBean()!=null && Utility.isNullOrEmpty(parentTypeBean.getClientdId() )) {

                        ClientRequestBean clientRequestBean = new ClientRequestBean();
                        clientRequestBean.setClientId( invoiceBean.getClientId() );
                        clientRequestBean.setVendorId( invoiceBean.getVendorId() );

                        AccessClients accessClients = new AccessClients();
                        ClientResponseBean clientResponseBean = accessClients.getClientContactInfo( clientRequestBean );
                        if(clientResponseBean!=null && clientResponseBean.getClientBean()!=null ) {
                            parentTypeBean.setClientBean( clientResponseBean.getClientBean() );
                        }
                    }
                    invoicePdfRequestBean.setParentTypeBean( parentTypeBean );



                    invoicePdfRequestBean.setInvoiceBean( invoiceBean );
                    AccessInvoiceItems accessInvoiceItems = new AccessInvoiceItems();
                    invoiceResponseBean = accessInvoiceItems.getInvoiceItems(invoiceRequestBean  ) ;
                    if(invoiceResponseBean!=null){
                        ArrayList<InvoiceItemBean> arrInvoiceItemsBean = invoiceResponseBean.getArrInvoiceItemsBean();

                        if(arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()) {

                            invoicePdfRequestBean.setArrInvoiceItemsBean(  arrInvoiceItemsBean );
                            appLogging.info("Going to invoke create PDF ");
                            createPdf(invoicePdfRequestBean);

                        }
                    }
                }
            }
        }
    }
    private static final BigDecimal ONEHUNDERT = new BigDecimal(100);

    private void generateInvoiceItems(InvoicePdfBean invoicePdfBean , InvoicePdfRequestBean invoicePdfRequestBean){

        if(invoicePdfRequestBean!=null && invoicePdfRequestBean.getInvoiceBean()!=null){
            InvoiceBean invoiceBean = invoicePdfRequestBean.getInvoiceBean();

            if(invoiceBean!=null && !Utility.isNullOrEmpty(invoiceBean.getInvoiceId())) {
                InvoiceRequestBean invoiceRequestBean = new InvoiceRequestBean();
                invoiceRequestBean.setInvoiceId( invoiceBean.getInvoiceId() );

                AccessInvoiceItems accessInvoiceItems = new AccessInvoiceItems();
                InvoiceResponseBean invoiceResponseBean = accessInvoiceItems.getInvoiceItems(invoiceRequestBean  ) ;
                if(invoiceResponseBean!=null) {

                    ArrayList<InvoiceItemBean> arrUpdatedInvoiceItemsBean = new ArrayList<InvoiceItemBean>();

                    ArrayList<InvoiceItemBean> arrInvoiceItemsBean = invoiceResponseBean.getArrInvoiceItemsBean();
                    if(arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()) {

                        BigDecimal bigDecimalSubTotal = new BigDecimal( "0.00");
                        BigDecimal bigDecimalDiscountPercentage = new BigDecimal( invoiceBean.getDiscountPercentage() );
                        BigDecimal bigDecimalTaxPercentage = new BigDecimal( invoiceBean.getTaxPercentage()  );

                        BigDecimal bigDecimalTax = new BigDecimal(  "0.00" );
                        BigDecimal bigDecimalDiscount = new BigDecimal(  "0.00" );
                        BigDecimal bigDecimalBalanceDue = new BigDecimal(  "0.00" );

                        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(Locale.US);
                        for(InvoiceItemBean invoiceItemBean : arrInvoiceItemsBean ) {
                            InvoiceItemBean newInvoiceItemBean = new InvoiceItemBean();
                            newInvoiceItemBean.setInvoiceId( invoiceItemBean.getInvoiceId() );
                            newInvoiceItemBean.setDiscountPercentage( invoiceItemBean.getDiscountPercentage() );
                            newInvoiceItemBean.setInvoiceItemId( invoiceItemBean.getInvoiceItemId() );
                            newInvoiceItemBean.setItemDescription( invoiceItemBean.getItemDescription() );
                            newInvoiceItemBean.setItemName( invoiceItemBean.getItemName() );
                            newInvoiceItemBean.setQuantity( invoiceItemBean.getQuantity() );
                            newInvoiceItemBean.setTaxPercentage( invoiceItemBean.getTaxPercentage() );
                            newInvoiceItemBean.setUnitCost( invoiceItemBean.getUnitCost() );

                            DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
                            BigDecimal bigDecimalItemUnitCost = new BigDecimal( decimalFormat.format(newInvoiceItemBean.getUnitCost()) );
                            BigDecimal bigDecimalItemTaxPercentage = new BigDecimal( decimalFormat.format(newInvoiceItemBean.getTaxPercentage()) );
                            BigDecimal bigDecimalItemDiscountPercentage = new BigDecimal( decimalFormat.format(newInvoiceItemBean.getDiscountPercentage()) );
                            BigDecimal bigDecimalItemQuantity = new BigDecimal( ParseUtil.LToS(newInvoiceItemBean.getQuantity())  );

                            BigDecimal bigDecimalAfterDiscount = bigDecimalItemUnitCost.multiply( bigDecimalItemQuantity ).subtract(bigDecimalItemDiscountPercentage);
                            BigDecimal bigDecimalTaxValue = bigDecimalAfterDiscount.multiply( bigDecimalItemTaxPercentage );

                            BigDecimal bigDecimalItemTotal = bigDecimalAfterDiscount.add( bigDecimalTaxValue );

                            bigDecimalSubTotal = bigDecimalSubTotal.add(bigDecimalItemTotal);

                            newInvoiceItemBean.setTotal( bigDecimalItemTotal.doubleValue() );
                            newInvoiceItemBean.setTotalDisplay( dollarFormat.format( bigDecimalItemTotal.doubleValue()) );
                            newInvoiceItemBean.setUnitCostDisplay( dollarFormat.format( bigDecimalItemUnitCost.doubleValue()) );
                            newInvoiceItemBean.setQuantityDisplay( ParseUtil.LToS(newInvoiceItemBean.getQuantity()) );


                            arrUpdatedInvoiceItemsBean.add( newInvoiceItemBean );
                        }
                        appLogging.info("Sub  Totla : " +bigDecimalSubTotal.toString()  );
                        appLogging.info("Discount Percentage : " +bigDecimalDiscountPercentage.toString()  );
                        appLogging.info("Tax Percentage : " +bigDecimalTaxPercentage.toString()  );
                        bigDecimalDiscount = bigDecimalSubTotal.multiply( bigDecimalDiscountPercentage ).divide(ONEHUNDERT) ;
                        appLogging.info("Discount Value : " +bigDecimalDiscount.toString()  );
                        bigDecimalTax = (bigDecimalSubTotal.subtract( bigDecimalDiscount )).multiply( bigDecimalTaxPercentage.divide(ONEHUNDERT) ) ;
                        appLogging.info("Tax Value : " +bigDecimalTax.toString()  );

                        bigDecimalBalanceDue = bigDecimalSubTotal.subtract( bigDecimalDiscount ).add( bigDecimalTax );
                        appLogging.info("Decimal Balance Due : " +bigDecimalBalanceDue.toString()  );

                        invoicePdfBean.setBalanceDue( dollarFormat.format( bigDecimalBalanceDue.doubleValue()) );
                        invoicePdfBean.setDiscount( dollarFormat.format( bigDecimalDiscount.doubleValue())  );
                        invoicePdfBean.setTax( dollarFormat.format( bigDecimalTax.doubleValue())  );
                        invoicePdfBean.setSubTotal(  dollarFormat.format( bigDecimalSubTotal.doubleValue())  );
                    }
                    invoicePdfBean.setArrInvoiceItemsBean( arrUpdatedInvoiceItemsBean );
                }
            }
        }
    }

    private void generateClientContact(InvoicePdfBean invoicePdfBean , InvoicePdfRequestBean invoicePdfRequestBean){
        appLogging.info("Going to set Client Contacts ");
        if(invoicePdfRequestBean!=null && invoicePdfRequestBean.getParentTypeBean()!=null){
            ClientBean clientBean = invoicePdfRequestBean.getParentTypeBean().getClientBean();
            appLogging.info("for PDF clientBean : " + ParseUtil.checkNullObject(clientBean));
            if(clientBean!=null){
                AccessClients accessClients = new AccessClients();
                ClientResponseBean clientResponseBean =accessClients.getClientContactInfo( clientBean );
                if(clientResponseBean!=null && !Utility.isNullOrEmpty(clientResponseBean.getClientId()) && !Utility.isNullOrEmpty(clientResponseBean.getUserInfoId())
                        && !Utility.isNullOrEmpty(clientResponseBean.getUserId()) ) {

                    UserInfoBean userInfoBean = clientResponseBean.getUserInfoBean();
                    invoicePdfBean.setClientPhone( userInfoBean.getCellPhone() );
                    invoicePdfBean.setClientAddress1( userInfoBean.getAddress1() );
                    invoicePdfBean.setClientAddress2( userInfoBean.getAddress2() );
                    invoicePdfBean.setClientCity( userInfoBean.getCity()  );
                    invoicePdfBean.setClientState( userInfoBean.getState() );
                    invoicePdfBean.setClientZip( userInfoBean.getZipcode() );
                    invoicePdfBean.setClientFirstName( userInfoBean.getFirstName() );
                    invoicePdfBean.setClientLastName( userInfoBean.getLastName() );

                }
            }
        }
        appLogging.info("After setting vendor contacts" + invoicePdfBean );
    }

    private void generateVendorContact(InvoicePdfBean invoicePdfBean , InvoicePdfRequestBean invoicePdfRequestBean){
        appLogging.info("Going to set Vendor Contacts ");
        if(invoicePdfRequestBean!=null && invoicePdfRequestBean.getParentTypeBean()!=null){
            VendorBean vendorBean = invoicePdfRequestBean.getParentTypeBean().getVendorBean();
            appLogging.info("for PDF vendorBean : " + ParseUtil.checkNullObject(vendorBean));
            if(vendorBean!=null){
                AccessVendors accessVendors = new AccessVendors();
                VendorResponseBean vendorResponseBean =accessVendors.getVendorContactInfo( vendorBean );
                if(vendorResponseBean!=null && !Utility.isNullOrEmpty(vendorResponseBean.getVendorId()) && !Utility.isNullOrEmpty(vendorResponseBean.getUserInfoId())
                        && !Utility.isNullOrEmpty(vendorResponseBean.getUserId()) ) {

                    UserInfoBean userInfoBean = vendorResponseBean.getUserInfoBean();
                    invoicePdfBean.setVendorPhone( userInfoBean.getCellPhone() );
                    invoicePdfBean.setVendorAddress1( userInfoBean.getAddress1() );
                    invoicePdfBean.setVendorAddress2( userInfoBean.getAddress2() );
                    invoicePdfBean.setVendorCity( userInfoBean.getCity()  );
                    invoicePdfBean.setVendorState( userInfoBean.getState() );
                    invoicePdfBean.setVendorZip( userInfoBean.getZipcode() );
                    invoicePdfBean.setVendorName( vendorBean.getVendorName() );

                }
            }
        }
        appLogging.info("After setting vendor contacts" + invoicePdfBean );
    }

    private void createPdf(InvoicePdfRequestBean invoicePdfRequestBean){
        if(invoicePdfRequestBean!=null && invoicePdfRequestBean.getInvoiceBean() !=null && invoicePdfRequestBean.getArrInvoiceItemsBean()!=null && !invoicePdfRequestBean.getArrInvoiceItemsBean().isEmpty()) {
            AccessInvoicePdf accessInvoicePdf = new AccessInvoicePdf();
            try{
                PDDocument pdfDocument = new PDDocument();
                PDPage pdgPage = new PDPage();
                pdfDocument.addPage(pdgPage);
                generateInvoiceContent(invoicePdfRequestBean, pdfDocument, pdgPage);
                generateClientAddress(invoicePdfRequestBean, pdfDocument, pdgPage);
                pdfDocument.save( accessInvoicePdf.getInvoicePdfLocation(invoicePdfRequestBean) ) ;
                pdfDocument.close();
            } catch (IOException ie){
                appLogging.info("IOException : " + ExceptionHandler.getStackTrace(ie));
            } catch (Exception io){
                appLogging.info("Exception : " + ExceptionHandler.getStackTrace(io));
            }
        }
    }

    private void generateInvoiceContent(InvoicePdfRequestBean invoicePdfRequestBean ,  PDDocument pdfDocument,PDPage pdgPage) throws IOException {
        if(invoicePdfRequestBean!=null) {
            InvoiceBean invoiceBean = invoicePdfRequestBean.getInvoiceBean();
            ArrayList<InvoiceItemBean> arrInvoiceItemsBean =  invoicePdfRequestBean.getArrInvoiceItemsBean();
            if(invoiceBean!=null && arrInvoiceItemsBean!=null && !arrInvoiceItemsBean.isEmpty()){
                PDFont font = PDType1Font.HELVETICA_BOLD;

                PDPageContentStream content = new PDPageContentStream(pdfDocument, pdgPage);
                content.beginText();
                content.setFont( font, 12 );
                content.moveTextPositionByAmount( 100, 700 );
                content.drawString(DateSupport.getUTCDateTime() + " Invoice Number : " + invoiceBean.getInvoiceNumber());
                appLogging.info("invoiceBean.getInvoiceNumber() : " + invoiceBean.getInvoiceNumber());
                content.endText();
                content.close();
            }

        }
        appLogging.info("generateInvoiceContent invoked ");

    }

    private void generateLogo(InvoicePdfRequestBean invoicePdfRequestBean,  PDDocument pdfDocument,PDPage pdgPage) throws IOException{
        if(invoicePdfRequestBean!=null) {
            String sLogo = ParseUtil.checkNull( invoicePdfRequestBean.getLogo());
            if(!Utility.isNullOrEmpty(sLogo))  {
                PDXObjectImage image =  new PDJpeg(pdfDocument, new FileInputStream(sLogo));

                PDPageContentStream content = new PDPageContentStream(pdfDocument, pdgPage);

                content.drawImage(image,250,50);
                content.close();
            }
        }
    }

    private void generateClientAddress(InvoicePdfRequestBean invoicePdfRequestBean ,  PDDocument pdfDocument,PDPage pdgPage)throws IOException {
        appLogging.info("Startihg generateClientAddress ");
        if(invoicePdfRequestBean!=null) {
            ParentTypeBean parentTypeBean = invoicePdfRequestBean.getParentTypeBean();
            if(parentTypeBean!=null && parentTypeBean.getClientBean()!=null){
                ClientBean clientBean = parentTypeBean.getClientBean();

                ClientRequestBean clientRequestBean = new  ClientRequestBean();
                clientRequestBean.setClientId( clientBean.getClientId() );
                clientRequestBean.setVendorId( clientBean.getVendorId() );

                AccessClients accessClients = new AccessClients();
                ClientResponseBean clientResponseBean = accessClients.getClientContactInfo(clientRequestBean);

                if(clientResponseBean!=null){
                    UserInfoBean userInfoBean = clientResponseBean.getUserInfoBean();
                    appLogging.info("SClient userInfoBean : " + userInfoBean );
                    if(userInfoBean!=null && !Utility.isNullOrEmpty(userInfoBean.getUserInfoId())){

                        PDFont helveticaFont = PDType1Font.HELVETICA;

                        PDPageContentStream nameContent = new PDPageContentStream(pdfDocument, pdgPage);
                        nameContent.beginText();
                        nameContent.setFont( helveticaFont, 11 );
                        nameContent.moveTextPositionByAmount( 100, 720 );
                        nameContent.drawString(userInfoBean.getFirstName() + " " + userInfoBean.getLastName()  + "\n" +
                                ParseUtil.checkNull(userInfoBean.getAddress1()) + "\n" + ParseUtil.checkNull(userInfoBean.getAddress2()) + "\n" +
                                ParseUtil.checkNull(userInfoBean.getCity()) + "\n" + ParseUtil.checkNull(userInfoBean.getState())  +
                                ParseUtil.checkNull(userInfoBean.getZipcode()));
                        appLogging.info("Drawing the Client String ");
                        nameContent.endText();
                        nameContent.close();

                    }
                }
            }

        }
        appLogging.info("generateClientAddress invoked ");
    }

    private void generateVendorAddress(InvoicePdfRequestBean invoicePdfRequestBean ,  PDDocument pdfDocument,PDPage pdgPage){
        if(invoicePdfRequestBean!=null) {

        }
    }
}
