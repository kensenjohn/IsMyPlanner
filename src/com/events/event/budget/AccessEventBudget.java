package com.events.event.budget;

import com.events.bean.event.budget.*;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.budget.AccessEventBudgetData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/16/14
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventBudget {
    public EventBudgetResponseBean loadEventBudget(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetResponseBean eventBudgetResponseBean = new EventBudgetResponseBean();
        if(eventBudgetRequestBean!=null) {
            AccessEventBudgetData accessEventBudgetData = new AccessEventBudgetData();
            EventBudgetBean eventBudgetBean = accessEventBudgetData.getEventBudgetByEvent( eventBudgetRequestBean );
            if(eventBudgetBean!=null && !Utility.isNullOrEmpty(eventBudgetBean.getEventBudgetId())) {
                eventBudgetResponseBean.setEventBudgetBean( eventBudgetBean );
                eventBudgetResponseBean.setEventBudgetId( eventBudgetBean.getEventBudgetId() );
            }
        }
        return  eventBudgetResponseBean;
    }

    public EventBudgetResponseBean loadEventBudgetCategoriesAndItems(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetResponseBean eventBudgetResponseBean = new EventBudgetResponseBean();
        if(eventBudgetRequestBean!=null) {
            AccessEventBudgetData accessEventBudgetData = new AccessEventBudgetData();
            EventBudgetBean eventBudgetBean = accessEventBudgetData.getEventBudgetByEvent( eventBudgetRequestBean );
            if(eventBudgetBean!=null && !Utility.isNullOrEmpty(eventBudgetBean.getEventBudgetId())) {

                ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean= accessEventBudgetData.getEventBudgetCategoryByEvent( eventBudgetRequestBean );
                ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBean = accessEventBudgetData.getEventBudgetItemsByEvent( eventBudgetRequestBean );

                HashMap<String,ArrayList<EventBudgetCategoryItemBean>> hmEventBudgetCategoryItemBean = new HashMap<String, ArrayList<EventBudgetCategoryItemBean>>();
                if(arrEventBudgetCategoryItemBean!=null && !arrEventBudgetCategoryItemBean.isEmpty()) {
                    for(EventBudgetCategoryItemBean eventBudgetCategoryItemBean : arrEventBudgetCategoryItemBean){
                        ArrayList<EventBudgetCategoryItemBean> arrTmpEventBudgetCategoryItemBean = hmEventBudgetCategoryItemBean.get( eventBudgetCategoryItemBean.getBudgetCategoryId() );
                        if(arrTmpEventBudgetCategoryItemBean==null){
                            arrTmpEventBudgetCategoryItemBean = new ArrayList<EventBudgetCategoryItemBean>();
                        }
                        arrTmpEventBudgetCategoryItemBean.add( eventBudgetCategoryItemBean );

                        hmEventBudgetCategoryItemBean.put( eventBudgetCategoryItemBean.getBudgetCategoryId(),arrTmpEventBudgetCategoryItemBean );
                    }
                }
                eventBudgetResponseBean.setArrEventBudgetCategoryBean( arrEventBudgetCategoryBean );
                eventBudgetResponseBean.setHmEventBudgetCategoryItemBean( hmEventBudgetCategoryItemBean );
                eventBudgetResponseBean.setEventBudgetBean( eventBudgetBean );
                eventBudgetResponseBean.setEventBudgetId( eventBudgetBean.getEventBudgetId() );
            }
        }
        return  eventBudgetResponseBean;
    }

    public  EventBudgetCategoryBean getBudgetCategoryByCategory(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId())) {
            AccessEventBudgetData accessEventBudgetData = new AccessEventBudgetData();
            eventBudgetCategoryBean = accessEventBudgetData.getEventBudgetCategoryByCategory( eventBudgetRequestBean );
        }
        return eventBudgetCategoryBean;
    }

    public  ArrayList<EventBudgetCategoryItemBean> getBudgetCategoryItemsByCategory(EventBudgetRequestBean eventBudgetRequestBean){
        ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBean = new ArrayList<EventBudgetCategoryItemBean>();
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId())) {
            AccessEventBudgetData accessEventBudgetData = new AccessEventBudgetData();
            arrEventBudgetCategoryItemBean = accessEventBudgetData.getEventBudgetItemsByCategory( eventBudgetRequestBean );
        }
        return arrEventBudgetCategoryItemBean;
    }

    public JSONObject getJsonEventBudgetCategoriesAndItems(ArrayList<EventBudgetCategoryBean> arrEventBudgetCategoryBean ,  HashMap<String,ArrayList<EventBudgetCategoryItemBean>> hmEventBudgetCategoryItemBean ){
        JSONObject jsonCategories  = new JSONObject();
        Double totalEstmatedCost = 0.0;
        Double totalActualCost = 0.0;
        if(arrEventBudgetCategoryBean!=null && !arrEventBudgetCategoryBean.isEmpty() ) {
            Long lNumberOfCategories = 0L;
            for(EventBudgetCategoryBean eventBudgetCategoryBean : arrEventBudgetCategoryBean){

                Double estmatedCost = 0.0;
                Double actualCost = 0.0;
                Long lNumberOfItems = 0L;
                if(hmEventBudgetCategoryItemBean!=null && !hmEventBudgetCategoryItemBean.isEmpty() ){
                    ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBean = hmEventBudgetCategoryItemBean.get( eventBudgetCategoryBean.getBudgetCategoryId() );
                    if(arrEventBudgetCategoryItemBean!=null && !arrEventBudgetCategoryItemBean.isEmpty() ) {

                        JSONObject jsonItems = new JSONObject();

                        for(EventBudgetCategoryItemBean eventBudgetCategoryItemBean : arrEventBudgetCategoryItemBean ){
                            estmatedCost = estmatedCost + eventBudgetCategoryItemBean.getEstimatedAmount();
                            actualCost = actualCost + eventBudgetCategoryItemBean.getActualAmount();
                            jsonItems.put(ParseUtil.LToS(lNumberOfItems) , eventBudgetCategoryItemBean.toJson() );

                            lNumberOfItems++;
                        }

                        jsonCategories.put(eventBudgetCategoryBean.getBudgetCategoryId()+"_items",jsonItems);
                    }
                }
                totalEstmatedCost = totalEstmatedCost + estmatedCost;
                totalActualCost = totalActualCost + actualCost;
                eventBudgetCategoryBean.setEstimatedCost( estmatedCost );
                eventBudgetCategoryBean.setActualCost( actualCost  );
                jsonCategories.put(ParseUtil.LToS(lNumberOfCategories), eventBudgetCategoryBean.toJson() );
                jsonCategories.put(eventBudgetCategoryBean.getBudgetCategoryId()+"_num_of_items",lNumberOfItems);
                lNumberOfCategories++;
            }
            jsonCategories.put("num_of_categories" , lNumberOfCategories );
        }
        jsonCategories.put("total_estimate_cost", totalEstmatedCost );
        jsonCategories.put("total_actual_cost", totalActualCost );
        return jsonCategories;
    }

    public JSONObject getJsonBudgetItems(ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBean){
        JSONObject jsonItems  = new JSONObject();
        if(arrEventBudgetCategoryItemBean!=null && !arrEventBudgetCategoryItemBean.isEmpty() ) {
            Long lNumberOfItems = 0L;
            for(EventBudgetCategoryItemBean eventBudgetCategoryItemBean : arrEventBudgetCategoryItemBean){
                jsonItems.put(ParseUtil.LToS(lNumberOfItems), eventBudgetCategoryItemBean.toJson() );
                lNumberOfItems++;
            }
            jsonItems.put("num_of_items" , lNumberOfItems );
        }
        return jsonItems;
    }
}
