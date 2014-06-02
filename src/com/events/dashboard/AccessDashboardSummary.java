package com.events.dashboard;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.common.notify.NotifyBean;
import com.events.bean.dashboard.DashboardSummaryBean;
import com.events.bean.dashboard.SummaryRequest;
import com.events.bean.event.EveryEventBean;
import com.events.bean.event.EveryEventRequestBean;
import com.events.bean.event.EveryEventResponseBean;
import com.events.bean.users.ParentTypeBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.vendors.VendorBean;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.notify.Notification;
import com.events.data.event.AccessEveryEventData;
import com.events.event.AccessEveryEvent;
import com.events.users.AccessUsers;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/8/14
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessDashboardSummary {
    public DashboardSummaryBean getCompleteDashBoardSummary(SummaryRequest summaryRequest) {
        DashboardSummaryBean dashboardSummaryBean = new DashboardSummaryBean();
        if(summaryRequest!=null){

            UserBean userBean = summaryRequest.getUserBean();
            // Un Read  Notification by User
            {
                if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
                    NotifyBean notifyBean = new NotifyBean();
                    notifyBean.setTo(userBean.getUserId());

                    Notification notification = new Notification();
                    Long iNumOfNotifyRecords = notification.getNumberOfUnreadNotifyRecords( notifyBean );

                    dashboardSummaryBean.setNumberOfUnreadNotifications( iNumOfNotifyRecords );
                }
            }

            AccessUsers accessUsers = new AccessUsers();
            ParentTypeBean parentTypeBean = summaryRequest.getParentTypeBean();

            if(parentTypeBean!=null && !parentTypeBean.isUserAClient() && parentTypeBean.getVendorBean()!=null ) {

            }

            if(parentTypeBean!=null){
                VendorBean vendorBean = parentTypeBean.getVendorBean();
                if(!parentTypeBean.isUserAClient() && parentTypeBean.getVendorBean()!=null ) {
                    // Clients for this User
                    {
                        ClientRequestBean clientRequestBean = new ClientRequestBean();
                        clientRequestBean.setVendorId( vendorBean.getVendorId() );

                        AccessClients accessClients = new AccessClients();
                        HashMap<Integer,ClientBean> hmClientBean =accessClients.getAllClientsSummary( clientRequestBean );
                        if(hmClientBean!=null && !hmClientBean.isEmpty()){
                            dashboardSummaryBean.setNumberOfClients(ParseUtil.sToL(ParseUtil.iToS(hmClientBean.size())));
                        }

                    }


                    // Team Members for this User
                    {
                        UserRequestBean userRequestBean = new UserRequestBean();
                        userRequestBean.setParentId(vendorBean.getVendorId());
                        userRequestBean.setUserType(Constants.USER_TYPE.VENDOR);
                        userRequestBean.setUserId(userBean.getUserId());

                        ArrayList<UserBean> arrUserBean =  accessUsers.getAllUsersByParentId( userRequestBean );
                        if(arrUserBean!=null && !arrUserBean.isEmpty() ) {
                            dashboardSummaryBean.setNumberOfTeamMembers( ParseUtil.sToL(ParseUtil.iToS(arrUserBean.size())) );
                        }
                    }
                }

                // Events for this User
                {
                    EveryEventRequestBean everyEventRequestBean = new EveryEventRequestBean();
                    everyEventRequestBean.setVendorId(vendorBean.getVendorId());
                    everyEventRequestBean.setClientId( ParseUtil.checkNull( parentTypeBean.getClientdId() ));
                    everyEventRequestBean.setDeletedEvent(false);
                    everyEventRequestBean.setLoadEventsByClient( parentTypeBean.isUserAClient() );

                    AccessEveryEventData accessEveryEventData = new AccessEveryEventData();
                    ArrayList<EveryEventBean> arrEveryEventBean  = new ArrayList<EveryEventBean>();
                    if(everyEventRequestBean.isLoadEventsByClient()) {
                        arrEveryEventBean  = accessEveryEventData.getEveryEventByClient(everyEventRequestBean);
                    } else {
                        arrEveryEventBean  = accessEveryEventData.getEveryEventByVendor(everyEventRequestBean);
                    }
                    if(arrEveryEventBean!=null && !arrEveryEventBean.isEmpty() ) {
                        dashboardSummaryBean.setNumberOfEvents(  ParseUtil.sToL(ParseUtil.iToS(arrEveryEventBean.size())) );
                    }
                }
            }




        }
        return dashboardSummaryBean;
    }
}
