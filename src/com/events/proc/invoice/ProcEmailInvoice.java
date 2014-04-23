package com.events.proc.invoice;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.invoice.InvoiceBean;
import com.events.bean.invoice.InvoiceEmailRequestBean;
import com.events.bean.invoice.InvoiceRequestBean;
import com.events.bean.invoice.InvoiceResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.clients.AccessClients;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.invoice.AccessInvoice;
import com.events.common.invoice.BuildInvoice;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import com.events.vendors.AccessVendors;
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
 * User: root
 * Date: 4/22/14
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcEmailInvoice   extends HttpServlet {
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
                    String sClientId = ParseUtil.checkNull(request.getParameter("client_id") );

                    if(Utility.isNullOrEmpty(sClientId)) {
                        Text errorText = new ErrorText("Please select a valid client.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if(Utility.isNullOrEmpty(sInvoiceId)) {
                        Text errorText = new ErrorText("Please select a Invoice.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        InvoiceRequestBean invoiceRequestBean = new InvoiceRequestBean();
                        invoiceRequestBean.setInvoiceId(sInvoiceId);

                        AccessInvoice accessInvoice = new AccessInvoice();
                        boolean isSendingEmailSuccess = false;
                        InvoiceResponseBean invoiceResponseBean = accessInvoice.getInvoice(invoiceRequestBean);
                        if(invoiceResponseBean!=null && !Utility.isNullOrEmpty(invoiceResponseBean.getInvoiceId()) ) {
                            InvoiceBean invoiceBean = invoiceResponseBean.getInvoiceBean();
                            if(invoiceBean!=null){
                                ClientRequestBean clientRequestBean = new ClientRequestBean();
                                clientRequestBean.setClientId( invoiceBean.getClientId() );
                                clientRequestBean.setVendorId( invoiceBean.getVendorId() );

                                AccessClients accessClients = new AccessClients();
                                ClientResponseBean clientResponseBean = accessClients.getClientContactInfo(clientRequestBean);

                                VendorRequestBean vendorRequestBean = new VendorRequestBean();
                                vendorRequestBean.setVendorId( invoiceBean.getVendorId() );
                                AccessVendors accessVendors = new AccessVendors();
                                VendorResponseBean vendorResponseBean =accessVendors.getVendorContactInfo(vendorRequestBean);


                                InvoiceEmailRequestBean invoiceEmailRequestBean = new InvoiceEmailRequestBean();
                                invoiceEmailRequestBean.setVendorId( invoiceBean.getVendorId() );
                                invoiceEmailRequestBean.setVendorResponseBean(vendorResponseBean);

                                invoiceEmailRequestBean.setClientId( invoiceBean.getClientId() );
                                invoiceEmailRequestBean.setClientResponseBean( clientResponseBean );

                                invoiceEmailRequestBean.setInvoiceId( sInvoiceId );
                                invoiceEmailRequestBean.setInvoiceBean( invoiceBean );

                                BuildInvoice buildInvoice = new BuildInvoice();
                                isSendingEmailSuccess = buildInvoice.sendInvoiceInEmail(invoiceEmailRequestBean );

                            } else {
                                appLogging.info("Invalid InvoiceBean. Unable to send " + !Utility.isNullOrEmpty(invoiceResponseBean.getInvoiceId()) );
                            }
                        } else {
                            appLogging.info("Invalid request " + ParseUtil.checkNull(sInvoiceId) );
                        }



                        if(isSendingEmailSuccess){
                            jsonResponseObj.put("email_send_success", isSendingEmailSuccess);

                            Text okText = new OkText("The invoice was emailed successfully","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to email this invoice. Please try again later.","err_mssg") ;
                            arrErrorText.add(errorText);
                            responseStatus = RespConstants.Status.ERROR;
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
