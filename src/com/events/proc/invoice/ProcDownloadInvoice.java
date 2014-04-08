package com.events.proc.invoice;

import com.events.bean.invoice.InvoiceRequestBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.invoice.AccessInvoicePdf;
import com.events.common.invoice.BuildInvoicePdf;
import com.events.common.security.DataSecurityChecker;
import com.events.json.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/4/14
 * Time: 12:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcDownloadInvoice   extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public void doPost(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {

        try{

            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {
                    String sInvoiceId = ParseUtil.checkNull(request.getParameter("invoice_id"));
                    if(Utility.isNullOrEmpty(sInvoiceId)) {

                    } else {
                        InvoiceRequestBean invoiceRequestBean = new InvoiceRequestBean();
                        invoiceRequestBean.setInvoiceId( sInvoiceId );
/*
                        AccessInvoicePdf accessInvoicePdf = new AccessInvoicePdf();
                        String sInvoicePath = ParseUtil.checkNull( accessInvoicePdf.getInvoicePdfLocation(loggedInUserBean, sInvoiceId )  );

                        if(!Utility.isNullOrEmpty(sInvoicePath)){


                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition","attachment; filename="+sInvoiceId+".pdf");

                            OutputStream out = response.getOutputStream();
                            FileInputStream in = new FileInputStream(sInvoicePath);
                            byte[] buffer = new byte[4096];
                            int length;
                            while ((length = in.read(buffer)) > 0){
                                out.write(buffer, 0, length);
                            }
                            in.close();
                            out.flush();
                        } else {

                        }*/

                    }

                }   else {
                }

            }  else {
            }

        }  catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
        }

    }
}
