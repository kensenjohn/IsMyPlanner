package com.events.common.faqsupport;

import com.events.bean.common.faqsupport.SupportFaqCategoriesBean;
import com.events.bean.common.faqsupport.SupportFaqQuesAndAnsBean;
import com.events.bean.common.faqsupport.SupportFaqRequestBean;
import com.events.bean.common.faqsupport.SupportFaqResponseBean;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.faqsupport.AccessSupportFaqData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/24/14
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessSupportFaq {
    public SupportFaqResponseBean getActiveFaqs(){
        SupportFaqResponseBean supportFaqResponseBean = new SupportFaqResponseBean();
        ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean = getActiveFaqCategories();
        if(arrSupportFaqCategoriesBean!=null && !arrSupportFaqCategoriesBean.isEmpty()) {
            supportFaqResponseBean.setArrSupportFaqCategoriesBean( arrSupportFaqCategoriesBean );

            SupportFaqRequestBean supportFaqRequestBean = new SupportFaqRequestBean();
            supportFaqRequestBean.setArrSupportFaqCategoriesBean(arrSupportFaqCategoriesBean  );
            HashMap<String, ArrayList<SupportFaqQuesAndAnsBean> > hmSupportFaqQuesAndAnsBeans = getActiveFaqQuestions( supportFaqRequestBean );
            supportFaqResponseBean.setHmSupportFaqQuesAndAnsBeans( hmSupportFaqQuesAndAnsBeans );
        }
        return supportFaqResponseBean;
    }

    public ArrayList<SupportFaqCategoriesBean> getActiveFaqCategories(){
        AccessSupportFaqData accessSupportFaqData = new AccessSupportFaqData();
        ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean = accessSupportFaqData.getFaqActiveCategories();
        return arrSupportFaqCategoriesBean;
    }
    public HashMap<String, ArrayList<SupportFaqQuesAndAnsBean> > getActiveFaqQuestions( SupportFaqRequestBean supportFaqRequestBean ){
        HashMap<String, ArrayList<SupportFaqQuesAndAnsBean> > hmSupportFaqQuesAndAnsBeans = new HashMap<String, ArrayList<SupportFaqQuesAndAnsBean>>();
        if(supportFaqRequestBean!=null && supportFaqRequestBean.getArrSupportFaqCategoriesBean()!=null && !supportFaqRequestBean.getArrSupportFaqCategoriesBean().isEmpty()) {
            AccessSupportFaqData accessSupportFaqData = new AccessSupportFaqData();
            hmSupportFaqQuesAndAnsBeans =accessSupportFaqData.getActiveFaqQuestionAnsByCategory( supportFaqRequestBean) ;
        }
        return hmSupportFaqQuesAndAnsBeans;
    }

    public SupportFaqQuesAndAnsBean getFaqAnswer(  SupportFaqRequestBean supportFaqRequestBean ){
        SupportFaqQuesAndAnsBean supportFaqQuesAndAnsBean = new SupportFaqQuesAndAnsBean();
        if(supportFaqRequestBean!=null && !Utility.isNullOrEmpty(supportFaqRequestBean.getSupportFaqQuestionId())){
            AccessSupportFaqData accessSupportFaqData = new AccessSupportFaqData();
            supportFaqQuesAndAnsBean = accessSupportFaqData.getFaqQuestionAnswer( supportFaqRequestBean );
        }
        return supportFaqQuesAndAnsBean;
    }

    public JSONObject getJsonActiveFaq(ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean , HashMap<String, ArrayList<SupportFaqQuesAndAnsBean> > hmSupportFaqQuesAndAnsBeans){
        JSONObject jsonFaqCategory = new JSONObject();
        Long lCategoryCount = 0L;
        if(arrSupportFaqCategoriesBean!=null && !arrSupportFaqCategoriesBean.isEmpty() ) {
            for(SupportFaqCategoriesBean supportFaqCategoriesBean : arrSupportFaqCategoriesBean ){

                ArrayList<SupportFaqQuesAndAnsBean>  arrSupportFaqQuesAndAnsBean = hmSupportFaqQuesAndAnsBeans.get( supportFaqCategoriesBean.getSupportFaqCategoryId() );
                Long lQuestionAnswerCount = 0L;
                if(arrSupportFaqQuesAndAnsBean!=null && !arrSupportFaqQuesAndAnsBean.isEmpty()){

                    JSONObject jsonFaqQuestionAnswer = new JSONObject();
                    for(SupportFaqQuesAndAnsBean supportFaqQuesAndAnsBean : arrSupportFaqQuesAndAnsBean ){
                        jsonFaqQuestionAnswer.put(ParseUtil.LToS(lQuestionAnswerCount),supportFaqQuesAndAnsBean.toJson() );
                        lQuestionAnswerCount++;
                    }
                    jsonFaqCategory.put("faq_question_answers_"+supportFaqCategoriesBean.getSupportFaqCategoryId(), jsonFaqQuestionAnswer );
                }
                jsonFaqCategory.put("num_of_faq_questions_"+supportFaqCategoriesBean.getSupportFaqCategoryId(), lQuestionAnswerCount );
                jsonFaqCategory.put(ParseUtil.LToS(lCategoryCount), supportFaqCategoriesBean.toJson() );
                lCategoryCount++;
            }
        }
        jsonFaqCategory.put("num_of_faq_categories" , lCategoryCount );
        return jsonFaqCategory;
    }
}
