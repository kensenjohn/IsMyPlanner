package com.events.proc.event.vendors;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.event.vendor.EventVendorRequestBean;
import com.events.bean.event.vendor.EveryEventVendorBean;
import com.events.bean.users.UserBean;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.vendor.AccessEventVendor;
import com.events.json.*;
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
 * Date: 2/10/14
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadEventVendors  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;
        try{
            if( !DataSecurityChecker.isInsecureInputResponse(request) ) {
                UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) ) {
                    String sUserId = ParseUtil.checkNull(loggedInUserBean.getUserId());
                    String sEventId =  ParseUtil.checkNull(request.getParameter("event_id"));
                    String sVendorId = Constants.EMPTY;

                    if(Constants.USER_TYPE.VENDOR.equals( loggedInUserBean.getUserType() )) {
                        sVendorId = loggedInUserBean.getParentId();

                    } else if(Constants.USER_TYPE.CLIENT.equals( loggedInUserBean.getUserType())) {
                        ClientRequestBean clientRequestBean = new ClientRequestBean();
                        clientRequestBean.setClientId( loggedInUserBean.getParentId() );

                        AccessClients accessClients = new AccessClients();
                        ClientBean clientBean = accessClients.getClientData( clientRequestBean );
                        if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getVendorId())) {
                            sVendorId = ParseUtil.checkNull(clientBean.getVendorId());
                        }
                    }

                    if( !Utility.isNullOrEmpty(sVendorId) && !Utility.isNullOrEmpty(sEventId ) ) {
                        EventVendorRequestBean eventVendorRequestBean = new EventVendorRequestBean();
                        eventVendorRequestBean.setVendorId( sVendorId );
                        eventVendorRequestBean.setEventId( sEventId );

                        AccessEventVendor accessEventVendor = new AccessEventVendor();
                        Integer iNumOfEventVendors = 0;
                        ArrayList<EveryEventVendorBean> arrEveryEventVendorBean = accessEventVendor.getEventVendorsList( eventVendorRequestBean );
                        if(arrEveryEventVendorBean!=null && !arrEveryEventVendorBean.isEmpty()){
                            JSONObject everyEventVendorJSON = accessEventVendor.getEveryEventVendorJSON(arrEveryEventVendorBean);
                            iNumOfEventVendors = everyEventVendorJSON.optInt("num_of_potential_event_vendors");
                            if(iNumOfEventVendors>0 && everyEventVendorJSON!=null){
                                jsonResponseObj.put("event_vendors",everyEventVendorJSON);
                            }
                        }

                        jsonResponseObj.put("num_of_event_vendors",iNumOfEventVendors);

                        Text okText = new OkText("Event  Vendors are successfully loaded.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;

                    }  else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later,.(loadEventVendors - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEventVendors - 002)","err_mssg") ;
                    arrErrorText.add(errorText);

                    responseStatus = RespConstants.Status.ERROR;
                }

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEventVendors - 001)","err_mssg") ;
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
