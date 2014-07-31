package com.events.event.checklist;

import com.events.bean.common.todo.ToDoBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateItemBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateRequestBean;
import com.events.bean.dashboard.checklist.ChecklistTemplateTodoBean;
import com.events.bean.event.checklist.*;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.todo.BuildToDo;
import com.events.dashboard.checklist.AccessChecklistTemplate;
import com.events.data.event.checklist.BuildEventChecklistData;
import com.events.data.todo.BuildToDoData;

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
public class BuildEventChecklist {
    public EventChecklistResponseBean saveEventChecklist(EventChecklistRequestBean eventChecklistRequestBean){
        EventChecklistResponseBean eventChecklistResponseBean = new EventChecklistResponseBean();
        if( eventChecklistRequestBean!=null ) {
            EventChecklistBean eventChecklistBean = new EventChecklistBean();
            if(Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())) {
                eventChecklistBean = createEventChecklist( eventChecklistRequestBean );
            } else {
                eventChecklistBean = updateEventChecklist( eventChecklistRequestBean );
            }

            if(eventChecklistBean!=null && !Utility.isNullOrEmpty(eventChecklistBean.getEventChecklistId()) ) {
                if( !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistTemplateId())  ) {
                    HashMap<String, EventChecklistItemBean> hmEventChecklistItemBean = createEventChecklistItems( eventChecklistRequestBean , eventChecklistBean );
                }
            }

            eventChecklistResponseBean.setEventChecklistBean( eventChecklistBean );
        }
        return eventChecklistResponseBean;
    }

    public EventChecklistItemBean saveEventChecklistItem(EventChecklistRequestBean eventChecklistRequestBean) {
        EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean();
        if(eventChecklistRequestBean!=null){
            String sEventChecklistItemId = eventChecklistRequestBean.getChecklistItemId();
            if(Utility.isNullOrEmpty(sEventChecklistItemId)){
                eventChecklistItemBean = createEventChecklistItem( eventChecklistRequestBean );
            } else {
                eventChecklistItemBean = updateEventChecklistItem( eventChecklistRequestBean );
            }

            if(eventChecklistItemBean!=null && !Utility.isNullOrEmpty(eventChecklistItemBean.getEventChecklistItemId())
                    && !Utility.isNullOrEmpty(eventChecklistItemBean.getEventChecklistId())   ){
                eventChecklistRequestBean.setChecklistItemId( eventChecklistItemBean.getEventChecklistItemId() );
                ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean = eventChecklistRequestBean.getArrEventChecklistTodoBean();

                BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
                buildEventChecklistData.createChecklistItemTodo(arrEventChecklistTodoBean , eventChecklistRequestBean) ;
            }
        }
        return eventChecklistItemBean;
    }

    public EventChecklistItemBean createEventChecklistItem(EventChecklistRequestBean eventChecklistRequestBean){
        EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean();
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())) {

            AccessEventChecklist accessEventChecklist = new AccessEventChecklist();
            Long lNumOfChecklistItems = accessEventChecklist.getNumberOfEventChecklistItems( eventChecklistRequestBean );

            eventChecklistRequestBean.setChecklistItemId( Utility.getNewGuid());
            eventChecklistRequestBean.setSortOrder( lNumOfChecklistItems+1 );
            eventChecklistItemBean = generateEventChecklistItemBean( eventChecklistRequestBean );

            ArrayList<EventChecklistItemBean> arrEventChecklistItemBean = new ArrayList<EventChecklistItemBean>();
            arrEventChecklistItemBean.add(eventChecklistItemBean);

            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            Integer numOfRowsInserted = buildEventChecklistData.createEventChecklistItem( arrEventChecklistItemBean );
            if(numOfRowsInserted<=0){
                eventChecklistItemBean = new EventChecklistItemBean();
            }
        }
        return eventChecklistItemBean;
    }

    public EventChecklistItemBean updateEventChecklistItem(EventChecklistRequestBean eventChecklistRequestBean){
        EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean();
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistItemId())) {
            eventChecklistItemBean = generateEventChecklistItemBean( eventChecklistRequestBean );

            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            Integer numOfRowsInserted = buildEventChecklistData.updateEventChecklistItem(eventChecklistItemBean);
            if(numOfRowsInserted<=0){
                eventChecklistItemBean = new EventChecklistItemBean();
            }
        }
        return eventChecklistItemBean;
    }

    public EventChecklistBean createEventChecklist(EventChecklistRequestBean eventChecklistRequestBean){
        EventChecklistBean eventChecklistBean = new EventChecklistBean();
        if( eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getEventId()) ) {
            eventChecklistRequestBean.setChecklistId( Utility.getNewGuid() );
            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            eventChecklistBean = generateEventChecklistBean(eventChecklistRequestBean);
            Integer numOfRowsInserted = buildEventChecklistData.insertEventChecklist( eventChecklistBean );
            if(numOfRowsInserted<=0){
                eventChecklistBean = new EventChecklistBean();
            }
        }
        return eventChecklistBean;
    }

    public EventChecklistBean updateEventChecklist(EventChecklistRequestBean eventChecklistRequestBean){
        EventChecklistBean eventChecklistBean = new EventChecklistBean();
        if( eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getEventId())  ) {
            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            eventChecklistBean = generateEventChecklistBean(eventChecklistRequestBean );

            Integer numOfRowsUpdated = buildEventChecklistData.updateEventChecklist( eventChecklistBean );
            if(numOfRowsUpdated<=0){
                eventChecklistBean = new EventChecklistBean();
            }
        }
        return eventChecklistBean;
    }

    public HashMap<String, EventChecklistItemBean> createEventChecklistItems( EventChecklistRequestBean eventChecklistRequestBean , EventChecklistBean eventChecklistBean  ){

        HashMap<String, EventChecklistItemBean> hmEventChecklistItemBean = new HashMap<String, EventChecklistItemBean>();
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistTemplateId())  &&
                eventChecklistBean!=null && !Utility.isNullOrEmpty(eventChecklistBean.getEventChecklistId()) ) {

            ChecklistTemplateRequestBean checklistTemplateRequestBean = new ChecklistTemplateRequestBean();
            checklistTemplateRequestBean.setChecklistTemplateId( eventChecklistRequestBean.getChecklistTemplateId() );

            AccessChecklistTemplate accessChecklistTemplate = new AccessChecklistTemplate();
            HashMap<Long, ChecklistTemplateItemBean> hmChecklistTemplateItemBean = accessChecklistTemplate.getAllChecklistTemplateItems( checklistTemplateRequestBean );

            ArrayList<EventChecklistItemBean> arrEventChecklistItemBean = new ArrayList<EventChecklistItemBean>();
            ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean = new ArrayList<EventChecklistTodoBean>();
            ArrayList<ToDoBean> arrTodoBeans = new ArrayList<ToDoBean>();

            if(hmChecklistTemplateItemBean!=null && !hmChecklistTemplateItemBean.isEmpty()) {
                HashMap<String, ArrayList<ChecklistTemplateTodoBean>> hmChecklistTemplateTodoBean = accessChecklistTemplate.getAllChecklistTemplateTodos( hmChecklistTemplateItemBean );
                for(Map.Entry<Long,ChecklistTemplateItemBean> mapChecklistTemplateItemBean : hmChecklistTemplateItemBean.entrySet() ) {
                    ChecklistTemplateItemBean checklistTemplateItemBean = mapChecklistTemplateItemBean.getValue();
                    Long lSortOrder = mapChecklistTemplateItemBean.getKey();

                    EventChecklistItemBean tmpEventChecklistItemBean = generateEventChecklistItemBean(checklistTemplateItemBean , eventChecklistBean , lSortOrder);
                    hmEventChecklistItemBean.put(checklistTemplateItemBean.getChecklistTemplateItemId() , tmpEventChecklistItemBean );
                    arrEventChecklistItemBean.add(  tmpEventChecklistItemBean  );

                    ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = hmChecklistTemplateTodoBean.get( checklistTemplateItemBean.getChecklistTemplateItemId() );
                    if(arrChecklistTemplateTodoBean!=null && !arrChecklistTemplateTodoBean.isEmpty()) {
                        for(ChecklistTemplateTodoBean checklistTemplateTodoBean : arrChecklistTemplateTodoBean ){
                            EventChecklistTodoBean eventChecklistTodoBean = generateEventChecklistTodoBean( checklistTemplateTodoBean,eventChecklistBean,tmpEventChecklistItemBean );
                            arrEventChecklistTodoBean.add( eventChecklistTodoBean ) ;
                            ToDoBean toDoBean = generateTodoBean( eventChecklistTodoBean , eventChecklistRequestBean );
                            arrTodoBeans.add( toDoBean );
                        }
                    }
                }
            }

            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            Integer numOfRowsInserted = buildEventChecklistData.createEventChecklistItem( arrEventChecklistItemBean );
            buildEventChecklistData.createChecklistItemTodo( arrEventChecklistTodoBean ) ;

            BuildToDoData buildToDoData = new BuildToDoData();
            buildToDoData.insertTodoList( arrTodoBeans );

        }
        return hmEventChecklistItemBean;
    }

    public void sortEventChecklistItem(  EventChecklistRequestBean eventChecklistRequestBean  ){
        if(eventChecklistRequestBean!=null){
            HashMap<String,Long> hmSortEventChecklistItemId = eventChecklistRequestBean.getHmEventChecklistItemId();

            if(hmSortEventChecklistItemId!=null && !hmSortEventChecklistItemId.isEmpty()) {
                BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
                for( Map.Entry<String,Long> mapSortChecklistItem : hmSortEventChecklistItemId.entrySet() ){
                    EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean();
                    eventChecklistItemBean.setSortOrder( mapSortChecklistItem.getValue() );
                    eventChecklistItemBean.setEventChecklistItemId( mapSortChecklistItem.getKey());

                    buildEventChecklistData.updateEventChecklistItemSort( eventChecklistItemBean );
                }
            }
        }
    }

    public Integer deleteEventChecklist( EventChecklistRequestBean eventChecklistRequestBean ){
        Integer numOfRowsDeleted = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            numOfRowsDeleted = buildEventChecklistData.deleteEventChecklist( eventChecklistRequestBean );
        }
        return numOfRowsDeleted;
    }

    public Integer deleteEventChecklistItem(  EventChecklistRequestBean eventChecklistRequestBean  ){
        Integer numOfRowsDeleted = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistItemId())){
            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            numOfRowsDeleted = buildEventChecklistData.deleteEventChecklistItem(eventChecklistRequestBean);
        }
        return numOfRowsDeleted;
    }
    public Integer deleteEventChecklistTodo(  EventChecklistRequestBean eventChecklistRequestBean  ){
        Integer numOfRowsDeleted = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistTodoId())){
            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            numOfRowsDeleted = buildEventChecklistData.deleteEventChecklistTodo(eventChecklistRequestBean);
        }
        return numOfRowsDeleted;
    }

    public Integer updateEventChecklistItemAction(   EventChecklistRequestBean eventChecklistRequestBean ){
        Integer numOfRowsUpdated = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistItemId())){
            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            numOfRowsUpdated = buildEventChecklistData.updateEventChecklistItemAction(eventChecklistRequestBean);
        }
        return numOfRowsUpdated;
    }

    public Integer updateEventChecklistTodoAction(   EventChecklistRequestBean eventChecklistRequestBean ){
        Integer numOfRowsUpdated = 0;
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistTodoId())){
            BuildEventChecklistData buildEventChecklistData = new BuildEventChecklistData();
            numOfRowsUpdated = buildEventChecklistData.updateEventChecklistTodoAction(eventChecklistRequestBean);
        }
        return numOfRowsUpdated;
    }

    private ToDoBean generateTodoBean (EventChecklistTodoBean eventChecklistTodoBean, EventChecklistRequestBean eventChecklistRequestBean ){
        ToDoBean toDoBean = new ToDoBean();
        if(eventChecklistTodoBean!=null){
            toDoBean.setToDoId(ParseUtil.checkNull(eventChecklistTodoBean.getTodoId()));
            toDoBean.setToDo(ParseUtil.checkNull(eventChecklistTodoBean.getName()));
            toDoBean.setTodoStatus(Constants.TODO_STATUS.ACTIVE);
            toDoBean.setVendorId(eventChecklistRequestBean.getVendorId());
            toDoBean.setClientId(eventChecklistRequestBean.getClientId());
        }
        return toDoBean;
    }

    private EventChecklistTodoBean generateEventChecklistTodoBean(ChecklistTemplateTodoBean checklistTemplateTodoBean , EventChecklistBean eventChecklistBean, EventChecklistItemBean eventChecklistItemBean){
        EventChecklistTodoBean eventChecklistTodoBean = new EventChecklistTodoBean();

        if(checklistTemplateTodoBean!=null && !Utility.isNullOrEmpty(checklistTemplateTodoBean.getName()) &&
                eventChecklistBean!=null && eventChecklistItemBean!=null ) {
            eventChecklistTodoBean.setEventChecklistId( eventChecklistBean.getEventChecklistId() );
            eventChecklistTodoBean.setEventChecklistItemId( eventChecklistItemBean.getEventChecklistItemId() );
            eventChecklistTodoBean.setEventChecklistTodoId( Utility.getNewGuid() );
            eventChecklistTodoBean.setName( checklistTemplateTodoBean.getName() );
            eventChecklistTodoBean.setComplete( false );
            eventChecklistTodoBean.setTodoId( Utility.getNewGuid()  );
        }

        return eventChecklistTodoBean;
    }

    private EventChecklistBean generateEventChecklistBean(EventChecklistRequestBean eventChecklistRequestBean){
        EventChecklistBean eventChecklistBean = new EventChecklistBean();
        if( eventChecklistRequestBean!=null ) {
            eventChecklistBean.setEventChecklistId(eventChecklistRequestBean.getChecklistId());
            eventChecklistBean.setClientId(eventChecklistRequestBean.getClientId());
            eventChecklistBean.setVendorId(eventChecklistRequestBean.getVendorId());
            eventChecklistBean.setName(eventChecklistRequestBean.getChecklistName());
            eventChecklistBean.setEventId( eventChecklistRequestBean.getEventId() );
        }
        return eventChecklistBean;
    }

    private EventChecklistItemBean generateEventChecklistItemBean(ChecklistTemplateItemBean checklistTemplateItemBean, EventChecklistBean eventChecklistBean, Long lSortOrder ){
        EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean();
        if(checklistTemplateItemBean!=null && !Utility.isNullOrEmpty(checklistTemplateItemBean.getChecklistTemplateItemId())
                && eventChecklistBean!=null && !Utility.isNullOrEmpty(eventChecklistBean.getEventChecklistId())  )  {
            eventChecklistItemBean.setEventChecklistId( eventChecklistBean.getEventChecklistId() );
            eventChecklistItemBean.setEventChecklistItemId( Utility.getNewGuid() );
            eventChecklistItemBean.setComplete( false );
            eventChecklistItemBean.setName( checklistTemplateItemBean.getName() );
            eventChecklistItemBean.setSortOrder( lSortOrder );
        }
        return eventChecklistItemBean;
    }

    private EventChecklistItemBean generateEventChecklistItemBean(  EventChecklistRequestBean eventChecklistRequestBean  ){
        EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean();
        if(eventChecklistRequestBean!=null){
            eventChecklistItemBean.setEventChecklistId( eventChecklistRequestBean.getChecklistId() );
            eventChecklistItemBean.setEventChecklistItemId(eventChecklistRequestBean.getChecklistItemId());
            eventChecklistItemBean.setName(  eventChecklistRequestBean.getChecklistItemName() );
            eventChecklistItemBean.setComplete(  eventChecklistRequestBean.isComplete()  );
            eventChecklistItemBean.setSortOrder(eventChecklistRequestBean.getSortOrder());
        }
        return eventChecklistItemBean;
    }
}
