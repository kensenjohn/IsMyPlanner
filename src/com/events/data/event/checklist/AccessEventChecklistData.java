package com.events.data.event.checklist;

import com.events.bean.dashboard.checklist.ChecklistTemplateBean;
import com.events.bean.event.checklist.EventChecklistBean;
import com.events.bean.event.checklist.EventChecklistItemBean;
import com.events.bean.event.checklist.EventChecklistRequestBean;
import com.events.bean.event.checklist.EventChecklistTodoBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 7/1/14
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventChecklistData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ArrayList<EventChecklistBean> getAllEventChecklists( EventChecklistRequestBean eventChecklistRequestBean){
        ArrayList<EventChecklistBean>  arrEventChecklistBean = new ArrayList<EventChecklistBean>();
        if(eventChecklistRequestBean!=null && !Utility.isNullOrEmpty(eventChecklistRequestBean.getEventId())){
            String sQuery = "SELECT * FROM GTEVENTCHECKLIST WHERE FK_EVENTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventChecklistRequestBean.getEventId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventChecklistData.java", "getAllEventChecklists()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventChecklistBean eventChecklistBean = new EventChecklistBean( hmResult );
                    arrEventChecklistBean.add(eventChecklistBean);
                }
            }
        }
        return arrEventChecklistBean;
    }

    public EventChecklistBean getEventChecklist( EventChecklistRequestBean eventChecklistRequestBean ){
        EventChecklistBean eventChecklistBean = new EventChecklistBean();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            String sQuery = "SELECT * FROM GTEVENTCHECKLIST WHERE EVENTCHECKLISTID = ? ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventChecklistRequestBean.getChecklistId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventChecklistData.java", "getEventChecklist()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    eventChecklistBean = new EventChecklistBean( hmResult );
                }
            }
        }
        return eventChecklistBean;
    }

    public HashMap<Long,EventChecklistItemBean> getAllEventChecklistItems( EventChecklistRequestBean eventChecklistRequestBean ){
        HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean = new HashMap<Long, EventChecklistItemBean>();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistId())){
            String sQuery = "SELECT * FROM GTEVENTCHECKLISTITEM WHERE FK_EVENTCHECKLISTID = ? ORDER BY SORT_ORDER ";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventChecklistRequestBean.getChecklistId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventChecklistData.java", "getAllEventChecklistItems()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean( hmResult );
                    hmEventChecklistItemBean.put( eventChecklistItemBean.getSortOrder(), eventChecklistItemBean );
                }
            }
        }
        return hmEventChecklistItemBean;
    }

    public HashMap<Long,EventChecklistItemBean> getEventChecklistItem( EventChecklistRequestBean eventChecklistRequestBean ){
        HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean = new HashMap<Long, EventChecklistItemBean>();
        if(eventChecklistRequestBean!=null  && !Utility.isNullOrEmpty(eventChecklistRequestBean.getChecklistItemId())){
            String sQuery = "SELECT * FROM GTEVENTCHECKLISTITEM WHERE EVENTCHECKLISTITEMID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint( eventChecklistRequestBean.getChecklistItemId() );

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventChecklistData.java", "getEventChecklistItem()");

            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventChecklistItemBean eventChecklistItemBean = new EventChecklistItemBean( hmResult );
                    hmEventChecklistItemBean.put( eventChecklistItemBean.getSortOrder(), eventChecklistItemBean );
                }
            }
        }
        return hmEventChecklistItemBean;
    }

    public  HashMap<String, ArrayList<EventChecklistTodoBean> >  getAllEventChecklistTodos(  HashMap<Long,EventChecklistItemBean> hmEventChecklistItemBean  ){
        HashMap<String, ArrayList<EventChecklistTodoBean> > hmEventChecklistTodoBean = new HashMap<String, ArrayList<EventChecklistTodoBean>>();
        if(hmEventChecklistItemBean!=null  && !hmEventChecklistItemBean.isEmpty()  ){
            String sQuery = "SELECT * FROM GTEVENTCHECKLISTTODO WHERE FK_EVENTCHECKLISTITEMID IN (" + DBDAO.createParamQuestionMarks(hmEventChecklistItemBean.size()) + ") ORDER BY CREATEDATE ASC";
            ArrayList<Object> aParams = new ArrayList<Object>();
            for(Map.Entry<Long, EventChecklistItemBean> mapEventChecklistItemBean : hmEventChecklistItemBean.entrySet() ) {
                EventChecklistItemBean eventChecklistItemBean = mapEventChecklistItemBean.getValue();
                aParams.add( eventChecklistItemBean.getEventChecklistItemId() );
            }

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessEventChecklistData.java", "getAllEventChecklistTodos()");
            if(arrResult!=null && !arrResult.isEmpty()) {
                for( HashMap<String, String> hmResult : arrResult ) {
                    EventChecklistTodoBean eventChecklistTodoBean = new EventChecklistTodoBean( hmResult );
                    ArrayList<EventChecklistTodoBean> arrEventChecklistTodoBean = hmEventChecklistTodoBean.get( eventChecklistTodoBean.getEventChecklistItemId() );
                    if(arrEventChecklistTodoBean == null ){
                        arrEventChecklistTodoBean = new ArrayList<EventChecklistTodoBean>();
                    }
                    arrEventChecklistTodoBean.add(eventChecklistTodoBean);

                    hmEventChecklistTodoBean.put( eventChecklistTodoBean.getEventChecklistItemId(), arrEventChecklistTodoBean );
                }
            }
        }
        return hmEventChecklistTodoBean;
    }
}
