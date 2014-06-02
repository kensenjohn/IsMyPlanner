package com.events.proc.faqsupport;

import com.events.bean.common.faqsupport.SupportFaqQuesAndAnsBean;
import com.events.bean.common.faqsupport.SupportFaqRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.faqsupport.AccessSupportFaq;
import com.events.common.security.DataSecurityChecker;
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
 * Date: 5/24/14
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcLoadFaqQuestionAnswer extends HttpServlet {
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
                String sQuestionId = ParseUtil.checkNull( request.getParameter("faq_question_id"));
                if( Utility.isNullOrEmpty( sQuestionId )) {
                    Text errorText = new ErrorText("Please select a valid question.","faq_question_id") ;
                    arrErrorText.add(errorText);
                    responseStatus = RespConstants.Status.ERROR;
                } else {

                    SupportFaqRequestBean supportFaqRequestBean = new SupportFaqRequestBean();
                    supportFaqRequestBean.setSupportFaqQuestionId( sQuestionId );

                    AccessSupportFaq accessSupportFaq = new AccessSupportFaq();
                    SupportFaqQuesAndAnsBean supportFaqQuesAndAnsBean =accessSupportFaq.getFaqAnswer(supportFaqRequestBean);
                    if(supportFaqQuesAndAnsBean!=null && !Utility.isNullOrEmpty(supportFaqQuesAndAnsBean.getSupportFaqQuesAnsId())) {

                        jsonResponseObj.put("faq_question_and_answer" , supportFaqQuesAndAnsBean.toJson() );
                        Text okText = new OkText("Load of Faq Question Complete.","status_mssg") ;
                        arrOkText.add(okText);
                        responseStatus = RespConstants.Status.OK;
                    } else {
                        Text errorText = new ErrorText("Oops!! We were unable to process your request. Please try again later.","account_num") ;
                        arrErrorText.add(errorText);
                        responseStatus = RespConstants.Status.ERROR;
                    }


                }

            } else {
                appLogging.info("Insecure Parameters used in this Proc Page " + Utility.dumpRequestParameters(request).toString()  + " --> " + this.getClass().getName());
                Text errorText = new ErrorText("Please use valid parameters. We have identified insecure parameters in your form.","account_num") ;
                arrErrorText.add(errorText);
                responseStatus = RespConstants.Status.ERROR;
            }
        } catch(Exception e) {
            appLogging.info("An exception occurred in the Proc Page " + ExceptionHandler.getStackTrace(e) );
            Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(loadTodo - 001)","err_mssg") ;
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
