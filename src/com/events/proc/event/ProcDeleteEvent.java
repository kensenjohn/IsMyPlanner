package com.events.proc.event;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.event.EventRequestBean;
import com.events.bean.event.EventResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Perm;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.BuildEvent;
import com.events.json.*;
import com.events.users.permissions.CheckPermission;
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
 * Date: 12/30/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcDeleteEvent  extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

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


                    CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
                    if(checkPermission!=null && checkPermission.can(Perm.DELETE_EVENT)) {
                        boolean isLoggedInUserAClient = false;
                        ClientRequestBean clientRequestBean = new ClientRequestBean();
                        clientRequestBean.setClientId( loggedInUserBean.getParentId());

                        AccessClients accessClients = new AccessClients();
                        ClientBean clientBean = accessClients.getClient( clientRequestBean );
                        if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getClientId())) {
                            isLoggedInUserAClient = true;
                        }

                        VendorBean vendorBean = new VendorBean();

                        VendorRequestBean vendorRequestBean = new VendorRequestBean();
                        AccessVendors accessVendor = new AccessVendors();
                        if(isLoggedInUserAClient) {
                            vendorRequestBean.setVendorId(  clientBean.getVendorId() );
                            vendorBean = accessVendor.getVendor( vendorRequestBean );
                        } else {
                            vendorRequestBean.setUserId( loggedInUserBean.getUserId() );
                            vendorBean = accessVendor.getVendorByUserId( vendorRequestBean ) ;  // get  vendor from user id
                        }

                        String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));

                        EventRequestBean eventRequestBean = new EventRequestBean();
                        eventRequestBean.setEventId(sEventId);
                        eventRequestBean.setEventVendorId(ParseUtil.checkNull(vendorBean.getVendorId()));
                        eventRequestBean.setEventDelete(true);

                        BuildEvent buildEvent = new BuildEvent();
                        EventResponseBean eventResponseBean = buildEvent.toggleEventDelete(eventRequestBean);
                        if(eventResponseBean!=null){
                            if(eventResponseBean.isDeleteEvent()){
                                Text okText = new OkText("The event was deleted successfully. ","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {
                                Text errorText = new ErrorText("Oops!! We were unable to delete the event. Please try again later.","err_mssg") ;
                                arrErrorText.add(errorText);
                                responseStatus = RespConstants.Status.ERROR;
                            }
                            jsonResponseObj.put("is_deleted",eventResponseBean.isDeleteEvent());
                            jsonResponseObj.put("deleted_event_id",sEventId);
                        }
                    } else {
                        Text errorText = new ErrorText("Oops!! You are not authorized to perform this action.(delEvent - 004)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEvent - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(001)","err_mssg") ;
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
