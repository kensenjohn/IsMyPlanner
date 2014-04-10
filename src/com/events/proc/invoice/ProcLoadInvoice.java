package com.events.proc.invoice;

import com.events.bean.invoice.*;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.invoice.AccessInvoice;
import com.events.common.invoice.AccessInvoiceItems;
import com.events.common.invoice.AccessInvoicePdf;
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
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadInvoice   extends HttpServlet {
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
                    if(Utility.isNullOrEmpty(sInvoiceId)){
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadInvoice - 004)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        InvoiceRequestBean invoiceRequestBean = new InvoiceRequestBean();
                        invoiceRequestBean.setInvoiceId(sInvoiceId);

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
                                        JSONObject jsonInvoiceItems = accessInvoiceItems.getInvoiceItemsJson( arrInvoiceItemsBean );
                                        Long lNumOfInvoiceItems = 0L;
                                        if(jsonInvoiceItems!=null){
                                            lNumOfInvoiceItems = jsonInvoiceItems.optLong( "num_of_invoice_items" );
                                            if(lNumOfInvoiceItems>0){
                                                jsonResponseObj.put("invoice_items",jsonInvoiceItems );
                                            }
                                        }
                                        jsonResponseObj.put("num_of_invoice_items",lNumOfInvoiceItems );

                                        jsonResponseObj.put("invoice_bean", invoiceBean.toJson() );

                                        invoiceRequestBean.setInvoiceId( sInvoiceId );

                                        String sLogo = Constants.EMPTY;
                                        if( request.getSession().getAttribute("SUBDOMAIN_LOGO") != null ) {
                                            sLogo = ParseUtil.checkNull( (String) request.getSession().getAttribute("SUBDOMAIN_LOGO"));
                                        }
                                        if(Utility.isNullOrEmpty(sLogo)) {
                                            String protocol = ParseUtil.checkNull(applicationConfig.get( Constants.PROP_LINK_PROTOCOL,"https"));
                                            String domain = ParseUtil.checkNull(applicationConfig.get( Constants.APPLICATION_DOMAIN,"https"));
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
                                        jsonResponseObj.put("invoice_id", sInvoiceId);
                                        jsonResponseObj.put("file_host", fileUploadHost);
                                        jsonResponseObj.put("bucket", bucket);
                                        jsonResponseObj.put("folder_path_name", sUserFolderName);

                                        Text okText = new OkText("The invoice was saved successfully","status_mssg") ;
                                        arrOkText.add(okText);
                                        responseStatus = RespConstants.Status.OK;
                                    } else {
                                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadInvoice - 005)","err_mssg") ;
                                        arrErrorText.add(errorText);

                                        responseStatus = RespConstants.Status.ERROR;
                                    }

                                } else {
                                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadInvoice - 006)","err_mssg") ;
                                    arrErrorText.add(errorText);

                                    responseStatus = RespConstants.Status.ERROR;
                                }

                            }  else {
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadInvoice - 007)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }




                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadInvoice - 003)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }

                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadInvoice - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadInvoice - 001)","err_mssg") ;
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
