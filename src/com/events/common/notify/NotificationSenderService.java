package com.events.common.notify;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.common.notify.NotifyBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.users.AccessUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/8/14
 * Time: 7:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationSenderService {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public void invokeNotificationSender() {
        Notification notification = new Notification();
        HashMap<String, NotifyBean> hmNotifyBean = getAllNewNotificationFromQueue();
        if(hmNotifyBean!=null && !hmNotifyBean.isEmpty()){
            for(int i = 0; i<hmNotifyBean.size(); i++){
                String topIdFromQueue = notification.removeNotificationIdFromQueue();

                NotifyBean topNotifyBean = hmNotifyBean.get( topIdFromQueue );

                if(topNotifyBean!=null){
                    createVerboseNotification(topNotifyBean);
                }
            }
        }

    }


    private HashMap<String, NotifyBean> getAllNewNotificationFromQueue() {
        Notification notification = new Notification();
        HashMap<String, NotifyBean> hmNotifyBean = notification.getNewNotificationFromQueue();
        return hmNotifyBean;
    }

    private void createVerboseNotification(NotifyBean topNotifyBean) {
        if(topNotifyBean!=null && !Utility.isNullOrEmpty(topNotifyBean.getFrom()) && !Utility.isNullOrEmpty(topNotifyBean.getTo())
                && !Utility.isNullOrEmpty(topNotifyBean.getMessage())) {

            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setUserId( topNotifyBean.getFrom() );

            AccessUsers accessUsers = new AccessUsers();
            UserBean fromUserBean = accessUsers.getUserById( userRequestBean );
            UserInfoBean fromUserInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );



            String sTo = topNotifyBean.getTo();

            ArrayList<NotifyBean> arrRecepientNotifyBean = new ArrayList<NotifyBean>();
            if(Constants.NOTIFICATION_RECEPIENTS.ALL_PLANNERS.toString().equalsIgnoreCase(sTo)) {
                ArrayList<UserBean> arrAllPlannersUserBean = getAllPlanners(fromUserBean ,fromUserInfoBean );
                if(arrAllPlannersUserBean!=null && !arrAllPlannersUserBean.isEmpty()) {
                    arrRecepientNotifyBean = generateRecipientNotificationBeans(fromUserBean ,fromUserInfoBean , arrAllPlannersUserBean ,topNotifyBean );
                }
            } else if(Constants.NOTIFICATION_RECEPIENTS.ALL_CLIENTS.toString().equalsIgnoreCase(sTo)) {

            } else {

            }
            if(arrRecepientNotifyBean!=null && !arrRecepientNotifyBean.isEmpty()) {
                createNotifications(arrRecepientNotifyBean);
            }
        }
    }

    private ArrayList<UserBean> getAllPlanners(UserBean userBean, UserInfoBean userInfoBean)  {
        String vendorId = Constants.EMPTY;
        ArrayList<UserBean> arrAllPlannersUserBean = new ArrayList<UserBean>();
        if(Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase(userBean.getUserType().getType())) {
            ClientRequestBean clientRequestBean = new ClientRequestBean();
            clientRequestBean.setClientId( userBean.getParentId() );

            AccessClients accessClients = new AccessClients();
            ClientBean clientBean = accessClients.getClient(clientRequestBean);

            vendorId = clientBean.getVendorId();
        } else if(Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase(userBean.getUserType().getType())) {
            vendorId = userBean.getParentId();
        }

        if(!Utility.isNullOrEmpty(vendorId)) {
            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setParentId(vendorId);
            userRequestBean.setUserType(Constants.USER_TYPE.VENDOR);

            AccessUsers accessUsers = new AccessUsers();
            arrAllPlannersUserBean = accessUsers.getAllUsersByParentId( userRequestBean );
        }

        return arrAllPlannersUserBean;
    }

    private void getAllClients(UserBean userBean, UserInfoBean userInfoBean)  {

    }

    private ArrayList<NotifyBean> generateRecipientNotificationBeans(UserBean fromUserBean , UserInfoBean fromUserInfoBean,
                                                                ArrayList<UserBean> arrRecepientUserBean, NotifyBean sourceNotifyBean) {
        ArrayList<NotifyBean> arrNotifyBean = new ArrayList<NotifyBean>();
        if(fromUserBean!=null && fromUserInfoBean!=null && arrRecepientUserBean!=null && !arrRecepientUserBean.isEmpty()
                && sourceNotifyBean!=null) {
            String sFromId = ParseUtil.checkNull(fromUserBean.getUserId());
            String sFromName = ParseUtil.checkNull(ParseUtil.checkNull(fromUserInfoBean.getFirstName()) + " " + ParseUtil.checkNull(fromUserInfoBean.getLastName()));
            if(Utility.isNullOrEmpty(sFromName)){
                if(Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase(fromUserBean.getUserType().getType())) {
                    sFromName = "A Client";
                } else if(Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase(fromUserBean.getUserType().getType())) {
                    sFromName = "The Planner";
                }
            }
            for(UserBean userBean : arrRecepientUserBean ) {
                NotifyBean newNotifyBean = new NotifyBean();
                newNotifyBean.setFrom( sFromId );
                newNotifyBean.setFromName(sFromName);
                newNotifyBean.setMessage( sourceNotifyBean.getMessage() );
                newNotifyBean.setTo(userBean.getUserId());
                arrNotifyBean.add( newNotifyBean );
            }
        }
        return arrNotifyBean;
    }

    private void createNotifications(ArrayList<NotifyBean> arrRecepientNotifyBean) {
        if(arrRecepientNotifyBean!=null && !arrRecepientNotifyBean.isEmpty()){
            for(NotifyBean notifyBean : arrRecepientNotifyBean ){
                Notification.createUnReadNotifyRecord( notifyBean );
            }
        }
    }
}
