package com.events.proc.event.budget;

import com.events.bean.event.budget.EventBudgetCategoryBean;
import com.events.bean.event.budget.EventBudgetCategoryItemBean;
import com.events.bean.event.budget.EventBudgetRequestBean;
import com.events.bean.event.budget.EventBudgetResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.event.budget.AccessEventBudget;
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
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/18/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadEventBudgetCategoriesAndItems extends HttpServlet {
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
                    String sEventId = ParseUtil.checkNull( request.getParameter("event_id") );
                    String sEventBudgetId = ParseUtil.checkNull( request.getParameter("eventbudget_id") );

                    if(Utility.isNullOrEmpty(sEventId)){
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please select a valid event.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        EventBudgetRequestBean eventBudgetRequestBean = new EventBudgetRequestBean();
                        eventBudgetRequestBean.setEventId( sEventId );

                        AccessEventBudget accessEventBudget = new AccessEventBudget();
                        EventBudgetResponseBean eventBudgetResponseBean = accessEventBudget.loadEventBudgetCategoriesAndItems( eventBudgetRequestBean );

                        Long lNumOfCategories = 0L;
                        if(eventBudgetResponseBean!=null){
                            HashMap<String,ArrayList<EventBudgetCategoryItemBean>> hmEventBudgetCategoryItemBean = eventBudgetResponseBean.getHmEventBudgetCategoryItemBean();
                            ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean= eventBudgetResponseBean.getArrEventBudgetCategoryBean();

                            if(arrEventBudgetCategoryBean!=null && !arrEventBudgetCategoryBean.isEmpty() ) {
                                JSONObject jsonCategories  = accessEventBudget.getJsonEventBudgetCategoriesAndItems(arrEventBudgetCategoryBean , hmEventBudgetCategoryItemBean );

                                if(jsonCategories!=null){
                                    lNumOfCategories = jsonCategories.optLong("num_of_categories");
                                    if(lNumOfCategories>0) {
                                        jsonResponseObj.put("budget_categories_and_items" , jsonCategories);
                                    }
                                }

                            }
                        }
                        jsonResponseObj.put("num_of_budget_categories" , lNumOfCategories );

                        Text okText = new OkText("Your changes were loaded successfully.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
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

