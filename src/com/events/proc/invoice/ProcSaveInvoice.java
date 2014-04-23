package com.events.proc.invoice;

import com.events.bean.invoice.*;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.common.invoice.AccessInvoicePdf;
import com.events.common.invoice.BuildInvoice;
import com.events.common.invoice.BuildInvoicePdf;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.users.AccessUsers;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/26/14
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveInvoice   extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        try{

            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {
                    String sInvoiceId = ParseUtil.checkNull(request.getParameter("invoice_id"));
                    String sClientId = ParseUtil.checkNull(request.getParameter("invoiceClient") );
                    String sTermsAndConditions = ParseUtil.checkNull(request.getParameter("invoiceTerms") );
                    String sNote = ParseUtil.checkNull(request.getParameter("invoiceNote") );
                    Double dInvoiceTax = ParseUtil.sToD(request.getParameter("invoiceTax") );
                    Double dInvoiceDiscount = ParseUtil.sToD(request.getParameter("invoiceDiscount") );
                    String sInvoiceDueDate = ParseUtil.checkNull(request.getParameter("invoiceDueDate") );
                    String sInvoiceDate = ParseUtil.checkNull(request.getParameter("invoiceDate") );
                    String sInvoicePONumber = ParseUtil.checkNull(request.getParameter("invoicePONumber") );
                    String sInvoiceNumber = ParseUtil.checkNull(request.getParameter("invoiceNumber") );
                    String[] strItemIdArray =  request.getParameterValues("item_id[]");

                    if(Utility.isNullOrEmpty(sClientId)) {
                        Text errorText = new ErrorText("Please select a valid client.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if(Utility.isNullOrEmpty(sInvoiceNumber) || Utility.isNullOrEmpty(sInvoiceDate)) {
                        Text errorText = new ErrorText("Please fill in all required fields.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if(strItemIdArray ==null || (strItemIdArray!=null && strItemIdArray.length<=0 )) {
                        Text errorText = new ErrorText("Please add at least one item to the invoice .","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else if(!Utility.isNullOrEmpty(sTermsAndConditions) && sTermsAndConditions.length()>100) {
                        Text errorText = new ErrorText("Please reduce the number of characters in the Terms and Conditions","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else if(!Utility.isNullOrEmpty(sNote) && sNote.length()>100) {
                        Text errorText = new ErrorText("Please reduce the number of characters in the Note.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    }else {

                        boolean isErrorInItems = false;
                        ArrayList<InvoiceItemBean> arrInvoiceItemsBean = new ArrayList<InvoiceItemBean>();
                        for(String sItemId : strItemIdArray ){
                            String sItemName = ParseUtil.checkNull(request.getParameter("item_"+sItemId));
                            String sItemDescription = ParseUtil.checkNull(request.getParameter("item_description_"+sItemId));
                            String sUnitCost = ParseUtil.checkNull(request.getParameter("unit_cost_"+sItemId));
                            String sQuantity = ParseUtil.checkNull(request.getParameter("quantity_"+sItemId));
                            if(Utility.isNullOrEmpty(sItemName) || Utility.isNullOrEmpty(sUnitCost) || Utility.isNullOrEmpty(sQuantity) ) {
                                Text errorText = new ErrorText("Please fill in the name, unit cost and quantity of the invoice item. Delete all empty item rows.","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                                isErrorInItems = true;
                            } else if(!ParseUtil.isValidLong(sQuantity) ){
                                Text errorText = new ErrorText("Please use a valid quantity for item " + sItemName,"err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                                isErrorInItems = true;

                            }  else if(  !ParseUtil.isValidDouble(sUnitCost)){
                                Text errorText = new ErrorText("Please use a valid unit cost for item " + sItemName,"err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                                isErrorInItems = true;
                            }
                            else {
                                Long lQuantity = ParseUtil.sToL(sQuantity);
                                Double dUnitCost = ParseUtil.sToD( sUnitCost );
                                InvoiceItemBean invoiceItemBean = new InvoiceItemBean();

                                invoiceItemBean.setInvoiceId(Utility.getNewGuid());
                                invoiceItemBean.setItemName(sItemName);
                                invoiceItemBean.setItemDescription(sItemDescription);
                                invoiceItemBean.setQuantity(lQuantity);
                                invoiceItemBean.setDiscountPercentage(0.00);
                                invoiceItemBean.setTaxPercentage( 0.00);
                                invoiceItemBean.setUnitCost(dUnitCost);
                                invoiceItemBean.setInvoiceId(sInvoiceId);

                                arrInvoiceItemsBean.add(invoiceItemBean);
                            }
                            if(isErrorInItems)  {
                                break;
                            }
                        }

                        if(!isErrorInItems){
                            InvoiceBean invoiceBean = new InvoiceBean();
                            invoiceBean.setInvoiceId( sInvoiceId);
                            invoiceBean.setUserId( loggedInUserBean.getUserId() );
                            invoiceBean.setClientId( sClientId ) ;
                            invoiceBean.setInvoiceNumber( sInvoiceNumber );
                            invoiceBean.setContractPONumber( sInvoicePONumber );
                            invoiceBean.setHumanInvoiceDate( sInvoiceDate );
                            invoiceBean.setHumanDueDate(sInvoiceDueDate);
                            invoiceBean.setDiscountPercentage( dInvoiceDiscount );
                            invoiceBean.setTaxPercentage( dInvoiceTax );
                            invoiceBean.setTermsAndConditions( sTermsAndConditions );
                            invoiceBean.setNote( sNote );


                            AccessUsers accessUsers = new AccessUsers();
                            ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser(loggedInUserBean);
                            if(parentTypeBean!=null && !Utility.isNullOrEmpty(parentTypeBean.getVendorId())){
                                invoiceBean.setVendorId(parentTypeBean.getVendorId());
                            }

                            InvoiceRequestBean invoiceRequestBean = new InvoiceRequestBean();
                            invoiceRequestBean.setInvoiceId( sInvoiceId );
                            invoiceRequestBean.setInvoiceBean( invoiceBean );
                            invoiceRequestBean.setArrInvoiceItemBean( arrInvoiceItemsBean );

                            BuildInvoice buildInvoice = new BuildInvoice();
                            InvoiceResponseBean invoiceResponseBean = buildInvoice.saveInvoice( invoiceRequestBean );
                            if(invoiceResponseBean!=null && !Utility.isNullOrEmpty(invoiceResponseBean.getInvoiceId())) {
                                sInvoiceId =  invoiceResponseBean.getInvoiceId();
                                invoiceRequestBean.setInvoiceId( sInvoiceId );

                                String sLogo = Constants.EMPTY;
                                if( request.getSession().getAttribute("SUBDOMAIN_LOGO") != null ) {
                                    sLogo = ParseUtil.checkNull( (String) request.getSession().getAttribute("SUBDOMAIN_LOGO"));
                                }
                                if(Utility.isNullOrEmpty(sLogo)) {
                                    String protocol = ParseUtil.checkNull(applicationConfig.get( Constants.PROP_LINK_PROTOCOL,"https"));
                                    String domain = ParseUtil.checkNull(applicationConfig.get( Constants.APPLICATION_DOMAIN,"ismyplanner.com"));
                                    if(!"localhost".equalsIgnoreCase(domain) && !domain.startsWith("www.")) {
                                        domain = "www."+ domain;
                                    }
                                    sLogo = protocol+"://"+domain+"/img/logo.png";
                                }

                                InvoicePdfRequestBean invoicePdfRequestBean = new InvoicePdfRequestBean();
                                invoicePdfRequestBean.setInvoiceId( sInvoiceId );
                                invoicePdfRequestBean.setUserBean( loggedInUserBean );
                                invoicePdfRequestBean.setLogo( sLogo );

                                BuildInvoicePdf buildInvoicePdf = new BuildInvoicePdf();
                                buildInvoicePdf.generateInvoicePdf( invoicePdfRequestBean );

                                AccessInvoicePdf accessInvoicePdf = new AccessInvoicePdf();
                                String fileUploadLocation = applicationConfig.get(Constants.FILE_UPLOAD_LOCATION);
                                String sUserFolderName = accessInvoicePdf.getUserFolderName(loggedInUserBean, fileUploadLocation) ;
                                String fileUploadHost = Utility.getFileUploadHost();
                                String bucket = Utility.getS3Bucket();
                                jsonResponseObj.put("invoice_id",sInvoiceId );
                                jsonResponseObj.put("client_id",sClientId );
                                jsonResponseObj.put("file_host", fileUploadHost);
                                jsonResponseObj.put("bucket", bucket);
                                jsonResponseObj.put("folder_path_name", sUserFolderName);


                                Text okText = new OkText("The invoice was saved successfully","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveInvoice - 003)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        }
                    }
                }   else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveInvoice - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            }  else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }

        }  catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveInvoice - 001)","err_mssg") ;
            arrErrorText.add(errorText);

            responseStatus = RespConstants.Status.ERROR;
        }


        responseObject.setErrorMessages(arrErrorText);
        responseObject.setOkMessages(arrOkText);
        responseObject.setResponseStatus(responseStatus);
        responseObject.setJsonResponseObj(jsonResponseObj);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write( responseObject.getJson().toString() );

    }
}
