package com.events.dashboard.checklist;

import com.events.bean.dashboard.checklist.*;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.dashboard.checklist.AccessChecklistTemplateData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessChecklistTemplate {

    public ChecklistTemplateResponseBean loadChecklistTemplateDetails( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();
        ChecklistTemplateBean checklistTemplateBean = getChecklistTemplateBean(checklistTemplateRequestBean);
        checklistTemplateResponseBean.setChecklistTemplateBean( checklistTemplateBean );

        HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean = getAllChecklistTemplateItems( checklistTemplateRequestBean ) ;
        checklistTemplateResponseBean.setHmChecklistTemplateItemBean(hmChecklistTemplateItemBean);

        HashMap<String, ArrayList<ChecklistTemplateTodoBean>> hmChecklistTemplateTodoBean = getAllChecklistTemplateTodos( hmChecklistTemplateItemBean );
        checklistTemplateResponseBean.setHmChecklistTemplateTodoBean(hmChecklistTemplateTodoBean);
        return checklistTemplateResponseBean;
    }

    public ChecklistTemplateBean getChecklistTemplateBean( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            checklistTemplateBean = accessChecklistTemplateData.getChecklistTemplateBean( checklistTemplateRequestBean );
        }
        return checklistTemplateBean;
    }

    public ChecklistTemplateResponseBean loadChecklistTemplateItemDetails( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();
        ChecklistTemplateBean checklistTemplateBean = getChecklistTemplateBean(checklistTemplateRequestBean);
        checklistTemplateResponseBean.setChecklistTemplateBean( checklistTemplateBean );

        ChecklistTemplateItemBean checklistTemplateItemBean = getChecklistTemplateItem(checklistTemplateRequestBean) ;
        checklistTemplateResponseBean.setChecklistTemplateItemBean( checklistTemplateItemBean );

        ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean =  getChecklistTemplateTodo(checklistTemplateRequestBean);
        checklistTemplateResponseBean.setArrChecklistTemplateTodoBean( arrChecklistTemplateTodoBean );
        return checklistTemplateResponseBean;
    }

    public HashMap<Long, ChecklistTemplateItemBean>  getAllChecklistTemplateItems(  ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean = new HashMap<Long, ChecklistTemplateItemBean>();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())  ) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            hmChecklistTemplateItemBean =  accessChecklistTemplateData.getAllChecklistTemplateItemBean(checklistTemplateRequestBean);
        }
        return hmChecklistTemplateItemBean;
    }

    public HashMap<String, ArrayList<ChecklistTemplateTodoBean>> getAllChecklistTemplateTodos(HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean){
        HashMap<String, ArrayList<ChecklistTemplateTodoBean>> hmChecklistTemplateTodoBean = new HashMap<String, ArrayList<ChecklistTemplateTodoBean>>();
        if(hmChecklistTemplateItemBean!=null &&  !hmChecklistTemplateItemBean.isEmpty() ) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            hmChecklistTemplateTodoBean = accessChecklistTemplateData.getAllChecklistTemplateTodoBean(hmChecklistTemplateItemBean);
        }
        return hmChecklistTemplateTodoBean;
    }

    public ChecklistTemplateItemBean getChecklistTemplateItem( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())
                && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId())  ) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            checklistTemplateItemBean = accessChecklistTemplateData.getChecklistTemplateItemBean(checklistTemplateRequestBean);
        }
        return  checklistTemplateItemBean;
    }

    public ArrayList<ChecklistTemplateTodoBean> getChecklistTemplateTodo( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = new ArrayList<ChecklistTemplateTodoBean>();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())
                && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId())  ) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            arrChecklistTemplateTodoBean = accessChecklistTemplateData.getAllChecklistTemplateItemTodo(checklistTemplateRequestBean);
        }
        return  arrChecklistTemplateTodoBean;
    }

    public ArrayList<ChecklistTemplateBean> getAllChecklistTemplatesByVendor( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ArrayList<ChecklistTemplateBean> arrChecklistTemplateBean = new ArrayList<ChecklistTemplateBean>();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getVendorId())) {
            AccessChecklistTemplateData accessChecklistTemplateData = new AccessChecklistTemplateData();
            arrChecklistTemplateBean = accessChecklistTemplateData.getAllChecklistTemplatesByVendor(checklistTemplateRequestBean);
        }
        return arrChecklistTemplateBean;
    }

    public JSONObject getJsonAllChecklistTemplates(ArrayList<ChecklistTemplateBean> arrChecklistTemplateBean){
        JSONObject jsonAllChecklistTemplates = new JSONObject();
        Long lNumberOfChecklistTemplates = 0L;
        if(arrChecklistTemplateBean!=null && !arrChecklistTemplateBean.isEmpty()){
            for(ChecklistTemplateBean checklistTemplateBean : arrChecklistTemplateBean ){
                jsonAllChecklistTemplates.put(ParseUtil.LToS(lNumberOfChecklistTemplates) , checklistTemplateBean.toJson() ) ;
                lNumberOfChecklistTemplates++;
            }
        }
        jsonAllChecklistTemplates.put("num_of_checklist_templates",lNumberOfChecklistTemplates);
        return jsonAllChecklistTemplates;
    }

    public JSONObject getJsonAllChecklistTemplateItems(HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean){
        JSONObject jsonAllChecklistTemplateItems = new JSONObject();
        Long lNumberOfChecklistTemplateItems = 0L;
        if(hmChecklistTemplateItemBean!=null && !hmChecklistTemplateItemBean.isEmpty()){
            for(Map.Entry<Long,ChecklistTemplateItemBean> mapChecklistTemplateItemBean : hmChecklistTemplateItemBean.entrySet() ){
                jsonAllChecklistTemplateItems.put(ParseUtil.LToS(mapChecklistTemplateItemBean.getKey()) , mapChecklistTemplateItemBean.getValue().toJson() ) ;
                lNumberOfChecklistTemplateItems++;
            }
        }
        jsonAllChecklistTemplateItems.put("num_of_checklist_template_items",lNumberOfChecklistTemplateItems);
        return jsonAllChecklistTemplateItems;
    }

    public JSONObject getJsonAllChecklistTemplateTodos( HashMap<String, ArrayList<ChecklistTemplateTodoBean>> hmChecklistTemplateTodoBean ){
        JSONObject jsonHmChecklistTemplateTodos = new JSONObject();
        Long lNumOfItems = 0L;
        if(hmChecklistTemplateTodoBean!=null && !hmChecklistTemplateTodoBean.isEmpty()){
            for( Map.Entry<String, ArrayList<ChecklistTemplateTodoBean>> mapChecklistTemplateTodoBean : hmChecklistTemplateTodoBean.entrySet() ){
                String sItemId = mapChecklistTemplateTodoBean.getKey();
                ArrayList<ChecklistTemplateTodoBean> arrmapChecklistTemplateTodoBean = mapChecklistTemplateTodoBean.getValue();

                JSONObject jsonAllChecklistTemplateTodos =  getJsonAllChecklistTemplateTodos( arrmapChecklistTemplateTodoBean );
                jsonHmChecklistTemplateTodos.put( sItemId , jsonAllChecklistTemplateTodos );
                lNumOfItems++;
            }
        }
        jsonHmChecklistTemplateTodos.put("num_of_checklist_template_items_with_todos" , lNumOfItems);
        return jsonHmChecklistTemplateTodos;
    }

    public JSONObject getJsonAllChecklistTemplateTodos(ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean){
        JSONObject jsonAllChecklistTemplateTodos = new JSONObject();
        Long lNumberOfChecklistTemplateTodos = 0L;
        if(arrChecklistTemplateTodoBean!=null && !arrChecklistTemplateTodoBean.isEmpty()){
            for(ChecklistTemplateTodoBean checklistTemplateTodoBean : arrChecklistTemplateTodoBean ){
                jsonAllChecklistTemplateTodos.put(ParseUtil.LToS(lNumberOfChecklistTemplateTodos) , checklistTemplateTodoBean.toJson() ) ;
                lNumberOfChecklistTemplateTodos++;
            }
        }
        jsonAllChecklistTemplateTodos.put("num_of_checklist_template_todos",lNumberOfChecklistTemplateTodos);
        return jsonAllChecklistTemplateTodos;
    }


    public Long getNumOfItems(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        Long lNumberOfItems = 0L;
        if(checklistTemplateRequestBean!=null){
            HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean = getAllChecklistTemplateItems(checklistTemplateRequestBean);
            if(hmChecklistTemplateItemBean!=null && !hmChecklistTemplateItemBean.isEmpty()){
                lNumberOfItems = ParseUtil.iToI(hmChecklistTemplateItemBean.size()).longValue();
            }
        }
        return lNumberOfItems;

    }
}
