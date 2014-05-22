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
 * Date: 5/17/14
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcSaveEventBudgetCategory extends HttpServlet {
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
                    String sEventBudgetCategoryId = ParseUtil.checkNull( request.getParameter("eventbudget_category_id") );
                    String sBudgetCategoryName = ParseUtil.checkNull( request.getParameter("budget_category_name") );

                    String[] strBudgetItemIdArray =  request.getParameterValues("budget_item_id[]");

                    if( Utility.isNullOrEmpty( sEventId ) ) {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please select a valid event.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else if( Utility.isNullOrEmpty( sEventBudgetId ) ) {
                        Text errorText = new ErrorText("Please select a valid event and budget","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }  else if( Utility.isNullOrEmpty( sBudgetCategoryName ) ) {
                        Text errorText = new ErrorText("Please select a valid category name.","err_mssg") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    } else {
                        EventBudgetRequestBean eventBudgetRequestBean = new EventBudgetRequestBean();
                        eventBudgetRequestBean.setEventId(sEventId);
                        eventBudgetRequestBean.setEventBudgetId(sEventBudgetId);
                        eventBudgetRequestBean.setBudgetCategoryId(sEventBudgetCategoryId);
                        eventBudgetRequestBean.setUserId(loggedInUserBean.getUserId());
                        eventBudgetRequestBean.setCategoryName(sBudgetCategoryName);

                        boolean isErrorInItems = false;
                        ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBeans = new ArrayList<EventBudgetCategoryItemBean>();
                        if(strBudgetItemIdArray!=null && strBudgetItemIdArray.length>0){
                            for(String sItemId : strBudgetItemIdArray ){
                                String sBudgetItemName = ParseUtil.checkNull(request.getParameter("budget_item_name_"+sItemId));
                                String sBudgetItemEstimateAmount = ParseUtil.checkNull(request.getParameter("budget_item_estimate_"+sItemId));
                                String sBudgetItemActualAmount = ParseUtil.checkNull(request.getParameter("budget_item_actual_"+sItemId));
                                boolean isBudgetItemPaid = ParseUtil.sTob(request.getParameter("budget_item_ispaid_" + sItemId));

                                if(Utility.isNullOrEmpty(sBudgetItemName) ) {
                                    Text errorText = new ErrorText("Please fill in the item name. Delete all empty item rows.","err_mssg") ;
                                    arrErrorText.add(errorText);
                                    responseStatus = RespConstants.Status.ERROR;
                                    isErrorInItems = true;
                                } else if(  !Utility.isNullOrEmpty(sBudgetItemEstimateAmount) && !ParseUtil.isValidDouble( sBudgetItemEstimateAmount)  ){
                                    Text errorText = new ErrorText("Please enter a valid Estimate amount for " + sBudgetItemName,"err_mssg") ;
                                    arrErrorText.add(errorText);

                                    responseStatus = RespConstants.Status.ERROR;
                                    isErrorInItems = true;
                                } else if(  !Utility.isNullOrEmpty(sBudgetItemActualAmount) &&  !ParseUtil.isValidDouble( sBudgetItemActualAmount)  ){
                                    Text errorText = new ErrorText("Please enter a valid Actual amount for " + sBudgetItemActualAmount,"err_mssg") ;
                                    arrErrorText.add(errorText);

                                    responseStatus = RespConstants.Status.ERROR;
                                    isErrorInItems = true;
                                } else {
                                    Double dEstimatedAmount = ParseUtil.sToD( sBudgetItemEstimateAmount );
                                    Double dActualAmount = ParseUtil.sToD( sBudgetItemActualAmount );

                                    EventBudgetCategoryItemBean eventBudgetCategoryItemBean = new EventBudgetCategoryItemBean();
                                    eventBudgetCategoryItemBean.setBudgetCategoryItemId( Utility.getNewGuid() );
                                    eventBudgetCategoryItemBean.setBudgetCategoryId( sEventBudgetCategoryId );
                                    eventBudgetCategoryItemBean.setEventBudgetId( sEventBudgetId );
                                    eventBudgetCategoryItemBean.setEventId( sEventId );
                                    eventBudgetCategoryItemBean.setItemName( sBudgetItemName );
                                    eventBudgetCategoryItemBean.setEstimatedAmount( dEstimatedAmount );
                                    eventBudgetCategoryItemBean.setActualAmount( dActualAmount );
                                    eventBudgetCategoryItemBean.setPaid( isBudgetItemPaid );
                                    eventBudgetCategoryItemBean.setUserId( loggedInUserBean.getUserId() );

                                    arrEventBudgetCategoryItemBeans.add( eventBudgetCategoryItemBean );
                                }
                            }
                        } else {
                            isErrorInItems = false;
                        }


                        if(!isErrorInItems) {
                            eventBudgetRequestBean.setArrEventBudgetCategoryItemBeans(arrEventBudgetCategoryItemBeans );

                            BuildEventBudget buildEventBudget = new BuildEventBudget();
                            EventBudgetResponseBean eventBudgetResponseBean = buildEventBudget.saveEventBudgetCategory(eventBudgetRequestBean);

                            if( eventBudgetResponseBean!=null ) {
                                EventBudgetCategoryBean eventBudgetCategoryBean = eventBudgetResponseBean.getEventBudgetCategoryBean();
                                if(eventBudgetCategoryBean!=null && !Utility.isNullOrEmpty(eventBudgetCategoryBean.getBudgetCategoryId() )) {
                                    Text okText = new OkText("Your changes were saved successfully.","status_mssg") ;
                                    arrOkText.add(okText);
                                    responseStatus = RespConstants.Status.OK;

                                    jsonResponseObj.put( "event_budget_category_bean", eventBudgetCategoryBean.toJson() );
                                } else {
                                    Text errorText = new ErrorText("Oops!! We were unable create/update this Budget Category. Please try again later.(saveEventBudgetCategory - 004)","err_mssg") ;
                                    arrErrorText.add(errorText);

                                    responseStatus = RespConstants.Status.ERROR;
                                }
                            } else {
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(saveEventBudgetCategory - 003)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                            }
                        } else {

                        }


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
