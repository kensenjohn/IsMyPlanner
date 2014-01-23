package com.events.proc.event;

import com.events.bean.event.EveryEventRequestBean;
import com.events.bean.event.EveryEventResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.AccessEveryEvent;
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
 * User: root
 * Date: 1/23/14
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadClientEvents  extends HttpServlet {
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

                if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {

                    String sClientId = ParseUtil.checkNull( request.getParameter("client_id") );
                    if(Utility.isNullOrEmpty(sClientId)) {
                        appLogging.info("An invalid client ID was used");
                        Text errorText = new ErrorText("We were unable to load this client's events.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        EveryEventRequestBean everyEventRequestBean = new EveryEventRequestBean();
                        everyEventRequestBean.setClientId( sClientId );
                        everyEventRequestBean.setDeletedEvent( false );

                        AccessEveryEvent accessClientEveryEvent = new AccessEveryEvent();
                        EveryEventResponseBean everyEventResponseBean = accessClientEveryEvent.getEveryClientEvent( everyEventRequestBean );

                        if(everyEventResponseBean!=null) {
                            if( everyEventResponseBean.getArrEveryEventBean()!=null && !everyEventResponseBean.getArrEveryEventBean().isEmpty() ) {
                                JSONObject jsonClientEveryEvent = accessClientEveryEvent.getEveryEventJson(everyEventResponseBean);

                                jsonResponseObj.put("client_every_event",jsonClientEveryEvent);
                                jsonResponseObj.put("num_of_events",everyEventResponseBean.getArrEveryEventBean().size());

                                Text okText = new OkText("Loading of Every Event completed","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            } else {

                                jsonResponseObj.put("num_of_events",0);
                                Text okText = new OkText("No event present","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;
                            }
                        } else {
                            appLogging.info("Error while loading every event (everyEventResponseBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadEvent - 002)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
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