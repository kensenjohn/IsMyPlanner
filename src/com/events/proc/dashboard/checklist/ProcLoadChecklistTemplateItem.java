package com.events.proc.dashboard.checklist;

import com.events.bean.dashboard.checklist.*;
import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.security.DataSecurityChecker;
import com.events.dashboard.checklist.AccessChecklistTemplate;
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
 * Date: 6/14/14
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadChecklistTemplateItem extends HttpServlet {
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
                    String sChecklistTemplateItemId = ParseUtil.checkNull(request.getParameter("checklist_template_item_id"));
                    String sChecklistTemplateId = ParseUtil.checkNull(request.getParameter("checklist_template_id"));

                    ChecklistTemplateRequestBean checklistTemplateRequestBean = new ChecklistTemplateRequestBean();
                    checklistTemplateRequestBean.setChecklistTemplateItemId( sChecklistTemplateItemId );
                    checklistTemplateRequestBean.setChecklistTemplateId( sChecklistTemplateId );

                    AccessChecklistTemplate accessChecklistTemplate = new AccessChecklistTemplate();
                    ChecklistTemplateResponseBean checklistTemplateResponseBean = accessChecklistTemplate.loadChecklistTemplateItemDetails( checklistTemplateRequestBean );

                    if(checklistTemplateResponseBean!=null){
                        ChecklistTemplateBean checklistTemplateBean = checklistTemplateResponseBean.getChecklistTemplateBean();
                        if(checklistTemplateBean!=null){
                            jsonResponseObj.put( "checklist_template_bean", checklistTemplateBean.toJson() );
                        }


                        ChecklistTemplateItemBean checklistTemplateItemBean = checklistTemplateResponseBean.getChecklistTemplateItemBean();

                        if(checklistTemplateItemBean!=null && !Utility.isNullOrEmpty(checklistTemplateItemBean.getChecklistTemplateItemId()  )) {
                            jsonResponseObj.put( "checklist_template_item_bean", checklistTemplateItemBean.toJson() );

                            ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = checklistTemplateResponseBean.getArrChecklistTemplateTodoBean();
                            Long lNumOfTodos = 0L;
                            if(arrChecklistTemplateTodoBean!=null && !arrChecklistTemplateTodoBean.isEmpty()) {
                                JSONObject jsonAllChecklistTemplateTodos = accessChecklistTemplate.getJsonAllChecklistTemplateTodos(arrChecklistTemplateTodoBean);

                                if( jsonAllChecklistTemplateTodos!=null ){
                                    lNumOfTodos = jsonAllChecklistTemplateTodos.optLong( "num_of_checklist_template_todos" );
                                    if(lNumOfTodos>0){
                                        jsonResponseObj.put( "all_checklist_template_todo" , jsonAllChecklistTemplateTodos );
                                    }
                                }
                            }
                            jsonResponseObj.put( "num_of_checklist_template_todos" , lNumOfTodos );

                            Text okText = new OkText("Load of Checklist Template Item Complete.","status_mssg") ;
                            arrOkText.add(okText);
                            responseStatus = RespConstants.Status.OK;
                        } else {
                            Text errorText = new ErrorText("Oops!! We were unable to save the checklist template. Please try again later.(loadChecklistTemplateItem - 004)","err_mssg") ;
                            arrErrorText.add(errorText);

                            responseStatus = RespConstants.Status.ERROR;
                        }
                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to save the checklist template. Please try again later.(loadChecklistTemplateItem - 003)","err_mssg") ;
                        arrErrorText.add(errorText);

                        responseStatus = RespConstants.Status.ERROR;
                    }

                } else {
                    appLogging.info("Invalid request in Proc Page (loggedInUserBean)" + ParseUtil.checkNullObject(loggedInUserBean) );
                    Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadChecklistTemplateItem - 002)","err_mssg") ;
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
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadChecklistTemplateItem - 001)","err_mssg") ;
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
