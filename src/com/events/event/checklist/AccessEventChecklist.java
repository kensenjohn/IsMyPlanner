package com.events.event.checklist;

import com.events.bean.event.checklist.*;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.checklist.AccessEventChecklistData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 7/1/14
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventChecklist {
    public ArrayList<EventChecklistBean> getAllEventChecklists( EventChecklistRequestBean eventChecklistRequestBean){
        ArrayList<EventChecklistBean> arrEventChecklistBean = new ArrayList<EventChecklistBean>();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getEventId())){
            AccessEventChecklistData accessEventChecklistData = new AccessEventChecklistData();
            arrEventChecklistBean = accessEventChecklistData.getAllEventChecklists( eventChecklistRequestBean );
        }
        return arrEventChecklistBean;
    }

    public JSONObject getJsonEventChecklistBean(ArrayList<EventChecklistBean> arrEventChecklistBean){
        JSONObject jsonAllEventChecklist = new JSONObject();
        if(arrEventChecklistBean!=null && !arrEventChecklistBean.isEmpty()) {
            Long lNumOfEventChecklists = 0L;
            for(EventChecklistBean eventChecklistBean : arrEventChecklistBean )  {
                jsonAllEventChecklist.put(ParseUtil.LToS(lNumOfEventChecklists),eventChecklistBean.toJson());
                lNumOfEventChecklists++;
            }
            jsonAllEventChecklist.put("num_of_event_checklists", lNumOfEventChecklists );
        }
        return jsonAllEventChecklist;
    }


    public EventChecklistResponseBean getEventChecklistItemDetails( EventChecklistRequestBean eventChecklistRequestBean ){
        EventChecklistResponseBean eventChecklistResponseBean = new EventChecklistResponseBean();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            EventChecklistBean eventChecklistBean = getEventChecklist(eventChecklistRequestBean);
            HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean = getEventChecklistItem(  eventChecklistRequestBean  );
            HashMap<String, ArrayList<EventChecklistTodoBean> > hmEventChecklistTodoBean = getEventChecklistTodo(hmEventChecklistItemBean);

            eventChecklistResponseBean.setEventChecklistBean( eventChecklistBean );
            eventChecklistResponseBean.setHmEventChecklistItemBean( hmEventChecklistItemBean );
            eventChecklistResponseBean.setHmEventChecklistTodoBean( hmEventChecklistTodoBean );
        }
        return eventChecklistResponseBean;
    }

    public HashMap<Long,EventChecklistItemBean> getEventChecklistItem(  EventChecklistRequestBean eventChecklistRequestBean  ){
        HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean = new HashMap<Long, EventChecklistItemBean>();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            AccessEventChecklistData accessEventChecklistData = new AccessEventChecklistData();
            hmEventChecklistItemBean = accessEventChecklistData.getEventChecklistItem( eventChecklistRequestBean );
        }
        return hmEventChecklistItemBean;
    }

    public EventChecklistResponseBean getEventChecklistDetails( EventChecklistRequestBean eventChecklistRequestBean ){
        EventChecklistResponseBean eventChecklistResponseBean = new EventChecklistResponseBean();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            EventChecklistBean eventChecklistBean = getEventChecklist(eventChecklistRequestBean);
            HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean = getAllEventChecklistItems(eventChecklistRequestBean);
            HashMap<String, ArrayList<EventChecklistTodoBean> > hmEventChecklistTodoBean = getEventChecklistTodo(hmEventChecklistItemBean);

            eventChecklistResponseBean.setEventChecklistBean( eventChecklistBean );
            eventChecklistResponseBean.setHmEventChecklistItemBean( hmEventChecklistItemBean );
            eventChecklistResponseBean.setHmEventChecklistTodoBean( hmEventChecklistTodoBean );
        }
        return eventChecklistResponseBean;
    }

    public EventChecklistBean getEventChecklist( EventChecklistRequestBean eventChecklistRequestBean ){
        EventChecklistBean eventChecklistBean = new EventChecklistBean();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            AccessEventChecklistData accessEventChecklistData = new AccessEventChecklistData();
            eventChecklistBean = accessEventChecklistData.getEventChecklist( eventChecklistRequestBean );
        }
        return eventChecklistBean;
    }

    public HashMap<Long,EventChecklistItemBean> getAllEventChecklistItems(  EventChecklistRequestBean eventChecklistRequestBean  ){
        HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean = new HashMap<Long, EventChecklistItemBean>();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            AccessEventChecklistData accessEventChecklistData = new AccessEventChecklistData();
            hmEventChecklistItemBean = accessEventChecklistData.getAllEventChecklistItems( eventChecklistRequestBean );
        }
        return hmEventChecklistItemBean;
    }

    public HashMap<String, ArrayList<EventChecklistTodoBean> > getEventChecklistTodo(  HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean  ){
        HashMap<String, ArrayList<EventChecklistTodoBean> > hmEventChecklistTodoBean = new HashMap<String, ArrayList<EventChecklistTodoBean>>();
        if(hmEventChecklistItemBean!=null  && !hmEventChecklistItemBean.isEmpty() ){
            AccessEventChecklistData accessEventChecklistData = new AccessEventChecklistData();
            hmEventChecklistTodoBean = accessEventChecklistData.getAllEventChecklistTodos( hmEventChecklistItemBean );
        }
        return hmEventChecklistTodoBean;
    }

    public JSONObject getJsonAllChecklistItems(HashMap<Long, EventChecklistItemBean> hmEventChecklistItemBean){
        JSONObject jsonAllEventChecklistItemBean = new JSONObject();
        Long lNumberOfEventChecklistItems = 0L;
        if(hmEventChecklistItemBean!=null && !hmEventChecklistItemBean.isEmpty()){
            for(Map.Entry<Long,EventChecklistItemBean> mapEventChecklistItemBean : hmEventChecklistItemBean.entrySet() ){
                jsonAllEventChecklistItemBean.put(ParseUtil.LToS(mapEventChecklistItemBean.getKey()) , mapEventChecklistItemBean.getValue().toJson() ) ;
                lNumberOfEventChecklistItems++;
            }
        }
        jsonAllEventChecklistItemBean.put("num_of_event_checklist_items",lNumberOfEventChecklistItems);
        return jsonAllEventChecklistItemBean;
    }

    public JSONObject getJsonAllEventChecklistTodos( HashMap<String, ArrayList<EventChecklistTodoBean>> hmEventChecklistTodoBean ){
        JSONObject jsonAllHmEventChecklistTodos = new JSONObject();
        Long lNumOfItems = 0L;
        if(hmEventChecklistTodoBean!=null && !hmEventChecklistTodoBean.isEmpty()){
            for( Map.Entry<String, ArrayList<EventChecklistTodoBean>> mapEventChecklistTodoBean : hmEventChecklistTodoBean.entrySet() ){
                String sItemId = mapEventChecklistTodoBean.getKey();
                ArrayList<EventChecklistTodoBean> arrmapEventChecklistTodoBean = mapEventChecklistTodoBean.getValue();

                JSONObject jsonAllEventChecklistItemTodos =  getJsonEventChecklistTodos( arrmapEventChecklistTodoBean );
                jsonAllHmEventChecklistTodos.put( sItemId , jsonAllEventChecklistItemTodos );
                lNumOfItems++;
            }
        }
        jsonAllHmEventChecklistTodos.put("num_of_event_checklist_items_with_todos" , lNumOfItems);
        return jsonAllHmEventChecklistTodos;
    }

    public JSONObject getJsonEventChecklistTodos(  ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean  ){
        JSONObject jsonEventChecklistTodoBean = new JSONObject();
        Long lNumberOfEventChecklistTodoBean = 0L;
        if(arrEventChecklistTodoBean!=null && !arrEventChecklistTodoBean.isEmpty()){
            for(EventChecklistTodoBean eventChecklistTodoBean : arrEventChecklistTodoBean ){
                jsonEventChecklistTodoBean.put(ParseUtil.LToS(lNumberOfEventChecklistTodoBean) , eventChecklistTodoBean.toJson() ) ;
                lNumberOfEventChecklistTodoBean++;
            }
        }
        jsonEventChecklistTodoBean.put("num_of_event_checklist_todos",lNumberOfEventChecklistTodoBean);
        return jsonEventChecklistTodoBean;
    }
}
