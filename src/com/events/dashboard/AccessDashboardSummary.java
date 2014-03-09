package com.events.dashboard;

import com.events.bean.common.notify.NotifyBean;
import com.events.bean.dashboard.DashboardSummaryBean;
import com.events.bean.dashboard.SummaryRequest;
import com.events.bean.users.UserBean;
import com.events.common.Utility;
import com.events.common.notify.Notification;

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

        }
        return dashboardSummaryBean;
    }
}
