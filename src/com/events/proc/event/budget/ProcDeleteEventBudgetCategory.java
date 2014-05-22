package com.events.proc.event.budget;

import com.events.bean.event.budget.EventBudgetCategoryItemBean;
import com.events.bean.event.budget.EventBudgetRequestBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.budget.AccessEventBudget;
import com.events.event.budget.BuildEventBudget;
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
 * Date: 5/20/14
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcDeleteEventBudgetCategory extends HttpServlet {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

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
                    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
                    String sEventBudgetCategoryId = ParseUtil.checkNull( request.getParameter("category_id") );
                    if(Utility.isNullOrEmpty(sEventBudgetCategoryId) ) {
                        Text errorText = new ErrorText("Please select a valid category and try again.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else if(Utility.isNullOrEmpty(sEventBudgetCategoryId) ) {
                        Text errorText = new ErrorText("Please select a valid event and try again.","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        EventBudgetRequestBean eventBudgetRequestBean = new EventBudgetRequestBean();
                        eventBudgetRequestBean.setBudgetCategoryId( sEventBudgetCategoryId );
                        eventBudgetRequestBean.setEventId( sEventId );


                        BuildEventBudget buildEventBudget = new BuildEventBudget();
                        boolean isSuccess = buildEventBudget.deleteEventBudgetCategory(eventBudgetRequestBean);
                        if(isSuccess){

                            AccessEventBudget accessEventBudget = new AccessEventBudget();
                            ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBean = accessEventBudget.getBudgetCategoryItemsByCategory(eventBudgetRequestBean);

                            if(arrEventBudgetCategoryItemBean!=null && !arrEventBudgetCategoryItemBean.isEmpty() ) {
                                buildEventBudget.deleteEventBudgetCategoryItems(eventBudgetRequestBean);
                                JSONObject jsonItems = accessEventBudget.getJsonBudgetItems(arrEventBudgetCategoryItemBean);
                                Long lNumOfItems = 0L;
                                if(jsonItems!=null){
                                    lNumOfItems = jsonItems.optLong("num_of_items");
                                    if(lNumOfItems>0){
                                        jsonResponseObj.put("deleted_items",jsonItems);
                                    }
                                    jsonResponseObj.put("num_of_items",lNumOfItems );
                                }

                            }

                            jsonResponseObj.put("deleted_category_id" , sEventBudgetCategoryId);
                            Text okText = new OkText("Successfully Deleted..","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(delCategory - 003)","err_mssg") ;
                            arrErrorText.add(errorText);
                        }
                        jsonResponseObj.put("is_deleted" , isSuccess);

                    }
                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEventBudgetCategory - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEventBudgetCategory - 001)","err_mssg") ;
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
