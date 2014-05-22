package com.events.event.budget;

import com.events.bean.event.budget.*;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.budget.BuildEventBudgetData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/16/14
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventBudget {
    public EventBudgetResponseBean saveEventBudgetEstimate(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetResponseBean eventBudgetResponseBean = new EventBudgetResponseBean();
        if(eventBudgetRequestBean!=null) {
            EventBudgetBean eventBudgetBean = new EventBudgetBean();
            if(Utility.isNullOrEmpty(eventBudgetRequestBean.getEventBudgetId())) {
                eventBudgetBean = createEventBudget( eventBudgetRequestBean);
                if(eventBudgetBean!=null){
                    eventBudgetRequestBean.setEventBudgetId( ParseUtil.checkNull(eventBudgetBean.getEventBudgetId()) );
                    createDefaultBudgetCategory(eventBudgetRequestBean);
                }
            } else {
                eventBudgetBean = updateEventBudget( eventBudgetRequestBean  );
            }

            if(eventBudgetBean!=null) {
                eventBudgetResponseBean.setEventBudgetBean( eventBudgetBean );
                eventBudgetResponseBean.setEventBudgetId(ParseUtil.checkNull( eventBudgetBean.getEventBudgetId() ));
            }
        }
        return eventBudgetResponseBean;
    }

    public EventBudgetBean createEventBudget(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetBean eventBudgetBean = new EventBudgetBean();
        if(eventBudgetRequestBean!=null){
            eventBudgetRequestBean.setEventBudgetId( Utility.getNewGuid() );
            eventBudgetBean = generateEventBudgetBean( eventBudgetRequestBean );
            BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
            Integer iNumOfRecords = buildEventBudgetData.insertEventBudget(eventBudgetBean);
            if(iNumOfRecords<=0){
                eventBudgetBean = new EventBudgetBean();
            }
        }
        return eventBudgetBean;
    }

    public EventBudgetBean updateEventBudget(EventBudgetRequestBean eventBudgetRequestBean) {
        EventBudgetBean eventBudgetBean = new EventBudgetBean();
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getEventBudgetId())){
            eventBudgetBean = generateEventBudgetBean( eventBudgetRequestBean );
            BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
            Integer iNumOfRecords = buildEventBudgetData.updateEventBudget(eventBudgetBean);
            if(iNumOfRecords<=0){
                eventBudgetBean = new EventBudgetBean();
            }
        }
        return eventBudgetBean;
    }

    public EventBudgetResponseBean saveEventBudgetCategory(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetResponseBean eventBudgetResponseBean = new EventBudgetResponseBean();
        if(eventBudgetRequestBean!=null) {
            EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
            if(Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId())) {
                eventBudgetCategoryBean = createEventBudgetCategory(eventBudgetRequestBean);
            } else {
                eventBudgetCategoryBean = updateEventBudgetCategory(eventBudgetRequestBean);
            }

            if(eventBudgetCategoryBean!=null) {
                eventBudgetRequestBean.setBudgetCategoryId( eventBudgetCategoryBean.getBudgetCategoryId() );
                BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
                buildEventBudgetData.deleteEventBudgetCategoryItems( eventBudgetRequestBean ) ;
                ArrayList<EventBudgetCategoryItemBean> arrEventBudgetCategoryItemBeans = eventBudgetRequestBean.getArrEventBudgetCategoryItemBeans();
                if(arrEventBudgetCategoryItemBeans!=null && !arrEventBudgetCategoryItemBeans.isEmpty()){
                    buildEventBudgetData.insertEventBudgetCategoryItems( eventBudgetRequestBean );
                }
                eventBudgetResponseBean.setEventBudgetCategoryBean(eventBudgetCategoryBean);
            }
        }
        return eventBudgetResponseBean;
    }
    public boolean deleteEventBudgetCategoryItems(EventBudgetRequestBean eventBudgetRequestBean){
        boolean isSuccess = false;
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId() )) {
            BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
            Integer iNumOfRowsDeleted = buildEventBudgetData.deleteEventBudgetCategoryItems( eventBudgetRequestBean ) ;
            isSuccess = true;
        }
        return isSuccess;
    }
    public void createDefaultBudgetCategory(EventBudgetRequestBean eventBudgetRequestBean){
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty( eventBudgetRequestBean.getEventBudgetId())) {
            EventBudgetRequestBean cakeEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Cake", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean cateringEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Catering", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean musicEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Music", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean dressEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Dress", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean flowerEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Flowers", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean photographyEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Photography", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean planningEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Planning", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean rentalsEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Rentals", eventBudgetRequestBean.getUserId() );
            EventBudgetRequestBean venueEventBudgetRequestBean = new EventBudgetRequestBean(  eventBudgetRequestBean.getEventBudgetId() ,
                    Utility.getNewGuid(), eventBudgetRequestBean.getEventId(), "Venue", eventBudgetRequestBean.getUserId() );

            ArrayList<EventBudgetRequestBean> arrEventBudgetRequestBean = new ArrayList<EventBudgetRequestBean>();
            arrEventBudgetRequestBean.add( cakeEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( cateringEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( musicEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( dressEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( flowerEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( photographyEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( planningEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( rentalsEventBudgetRequestBean );
            arrEventBudgetRequestBean.add( venueEventBudgetRequestBean );

            createEventBudgetCategory( arrEventBudgetRequestBean );
        }
    }
    public void createEventBudgetCategory( ArrayList<EventBudgetRequestBean> arrEventBudgetRequestBean){
        if(arrEventBudgetRequestBean!=null && !arrEventBudgetRequestBean.isEmpty() ){
            BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
            Integer iNumOfRecords = buildEventBudgetData.insertEventBudgetCategory(arrEventBudgetRequestBean);
        }
    }
    public EventBudgetCategoryBean createEventBudgetCategory(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
        if(eventBudgetRequestBean!=null){
            eventBudgetRequestBean.setBudgetCategoryId(Utility.getNewGuid());
            eventBudgetCategoryBean = generateEventBudgetCategoryBean(eventBudgetRequestBean);
            BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
            Integer iNumOfRecords = buildEventBudgetData.insertEventBudgetCategory(eventBudgetCategoryBean);
            if(iNumOfRecords<=0){
                eventBudgetCategoryBean = new EventBudgetCategoryBean();
            }
        }
        return eventBudgetCategoryBean;
    }
    public EventBudgetCategoryBean updateEventBudgetCategory(EventBudgetRequestBean eventBudgetRequestBean) {
        EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId())){
            eventBudgetCategoryBean = generateEventBudgetCategoryBean(eventBudgetRequestBean);
            BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
            Integer iNumOfRecords = buildEventBudgetData.updateEventBudgetCategory(eventBudgetCategoryBean);
            if(iNumOfRecords<=0){
                eventBudgetCategoryBean = new EventBudgetCategoryBean();
            }
        }
        return eventBudgetCategoryBean;
    }
    public boolean deleteEventBudgetCategory(EventBudgetRequestBean eventBudgetRequestBean) {
        boolean isSuccess = false;
        if(eventBudgetRequestBean!=null && !Utility.isNullOrEmpty(eventBudgetRequestBean.getBudgetCategoryId())
                && !Utility.isNullOrEmpty(eventBudgetRequestBean.getEventId()) ){
            BuildEventBudgetData buildEventBudgetData = new BuildEventBudgetData();
            Integer iNumOfRecords = buildEventBudgetData.deleteEventBudgetCategory(eventBudgetRequestBean);
            if(iNumOfRecords>0){
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private EventBudgetBean generateEventBudgetBean(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetBean eventBudgetBean = new EventBudgetBean();
        if(  eventBudgetRequestBean!=null ) {
            eventBudgetBean.setEventBudgetId( eventBudgetRequestBean.getEventBudgetId() );
            eventBudgetBean.setEventId( eventBudgetRequestBean.getEventId() );
            eventBudgetBean.setEstimateBudget(eventBudgetRequestBean.getEstimatedBudget());
            eventBudgetBean.setUserId( eventBudgetRequestBean.getUserId() );
        }
        return eventBudgetBean;
    }

    private EventBudgetCategoryBean generateEventBudgetCategoryBean(EventBudgetRequestBean eventBudgetRequestBean){
        EventBudgetCategoryBean eventBudgetCategoryBean = new EventBudgetCategoryBean();
        if(  eventBudgetRequestBean!=null ) {
            eventBudgetCategoryBean.setBudgetCategoryId(eventBudgetRequestBean.getBudgetCategoryId());
            eventBudgetCategoryBean.setEventBudgetId(eventBudgetRequestBean.getEventBudgetId());
            eventBudgetCategoryBean.setEventId(eventBudgetRequestBean.getEventId());
            eventBudgetCategoryBean.setCategoryName(eventBudgetRequestBean.getCategoryName());
            eventBudgetCategoryBean.setUserId( eventBudgetRequestBean.getUserId() );
        }
        return eventBudgetCategoryBean;
    }
}
