package com.events.data.faqsupport;

import com.events.bean.common.faqsupport.SupportFaqCategoriesBean;
import com.events.bean.common.faqsupport.SupportFaqQuesAndAnsBean;
import com.events.bean.common.faqsupport.SupportFaqRequestBean;
import com.events.bean.common.todo.ToDoBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/24/14
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessSupportFaqData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<SupportFaqCategoriesBean> getFaqActiveCategories(){
        ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean = new ArrayList<SupportFaqCategoriesBean>();
        String sQuery  = "SELECT * FROM GTSUPPORTFAQCATEGORIES WHERE IS_SHOWN = 1";
        ArrayList<Object> aParams = new ArrayList<Object>();
        ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSupportFaqData.java", "getActiveCategories()");

        if(arrResult!=null && !arrResult.isEmpty()) {
            for( HashMap<String, String> hmResult : arrResult ) {
                SupportFaqCategoriesBean supportFaqCategoriesBean = new SupportFaqCategoriesBean(hmResult);
                arrSupportFaqCategoriesBean.add( supportFaqCategoriesBean );
            }
        }
        return arrSupportFaqCategoriesBean;
    }

    public HashMap<String, ArrayList<SupportFaqQuesAndAnsBean> > getActiveFaqQuestionAnsByCategory( SupportFaqRequestBean supportFaqRequestBean ) {
        HashMap<String, ArrayList<SupportFaqQuesAndAnsBean> > hmSupportFaqQuesAndAnsBeans = new HashMap<String, ArrayList<SupportFaqQuesAndAnsBean>>();
        if(supportFaqRequestBean!=null && supportFaqRequestBean.getArrSupportFaqCategoriesBean()!=null && !supportFaqRequestBean.getArrSupportFaqCategoriesBean().isEmpty()) {
            ArrayList<SupportFaqCategoriesBean> arrSupportFaqCategoriesBean =  supportFaqRequestBean.getArrSupportFaqCategoriesBean();
            String sQuery  = "SELECT * FROM GTSUPPORTFAQQUESANDANS WHERE IS_SHOWN = 1 AND FK_SUPPORTFAQCATEGORIESID IN ("+ DBDAO.createParamQuestionMarks( arrSupportFaqCategoriesBean.size() ) + ")";

            ArrayList<Object> aParams = new ArrayList<Object>();
            for(SupportFaqCategoriesBean supportFaqCategoriesBean : arrSupportFaqCategoriesBean ) {
                aParams.add( supportFaqCategoriesBean.getSupportFaqCategoryId() );
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSupportFaqData.java", "getActiveFaqQuestionAns()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    SupportFaqQuesAndAnsBean supportFaqQuesAndAnsBean = new SupportFaqQuesAndAnsBean(hmResult);
                    String sCategoryId = supportFaqQuesAndAnsBean.getSupportFaqCategoryId();
                    ArrayList<SupportFaqQuesAndAnsBean> arrSupportFaqQuesAndAnsBeans = hmSupportFaqQuesAndAnsBeans.get( sCategoryId );
                    if(arrSupportFaqQuesAndAnsBeans == null ){
                        arrSupportFaqQuesAndAnsBeans = new ArrayList<SupportFaqQuesAndAnsBean>();
                    }
                    arrSupportFaqQuesAndAnsBeans.add( supportFaqQuesAndAnsBean );

                    hmSupportFaqQuesAndAnsBeans.put(sCategoryId , arrSupportFaqQuesAndAnsBeans ) ;
                }
            }
        }
        return hmSupportFaqQuesAndAnsBeans;
    }

    public SupportFaqQuesAndAnsBean getFaqQuestionAnswer( SupportFaqRequestBean supportFaqRequestBean ) {
        SupportFaqQuesAndAnsBean supportFaqQuesAndAnsBean = new SupportFaqQuesAndAnsBean();
        if(supportFaqRequestBean!=null &&!Utility.isNullOrEmpty( supportFaqRequestBean.getSupportFaqQuestionId() )) {
            String sQuery  = "SELECT * FROM GTSUPPORTFAQQUESANDANS WHERE SUPPORTFAQQUESANDANSID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( supportFaqRequestBean.getSupportFaqQuestionId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessSupportFaqData.java", "getFaqQuestionAnswers()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    supportFaqQuesAndAnsBean = new SupportFaqQuesAndAnsBean(hmResult);
                }
            }
        }
        return supportFaqQuesAndAnsBean;
    }
}
