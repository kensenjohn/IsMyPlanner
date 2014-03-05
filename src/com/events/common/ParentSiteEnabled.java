package com.events.common;

import com.events.bean.common.ParentSiteEnabledBean;
import com.events.bean.users.PasswordRequestBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.exception.users.ManagePasswordException;
import com.events.data.ParentSiteEnabledData;
import com.events.users.AccessUsers;
import com.events.users.ManageUserPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/2/14
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentSiteEnabled {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public ParentSiteEnabledBean getParentSiteEnabledStatusForUser(UserRequestBean userRequestBean){
        ParentSiteEnabledBean parentSiteEnabledBean = new ParentSiteEnabledBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId())) {
            ParentSiteEnabledData parentSiteEnabledData = new ParentSiteEnabledData();
            parentSiteEnabledBean = parentSiteEnabledData.getParentSiteEnabledStatusByUser( userRequestBean );
        }
        return parentSiteEnabledBean;
    }

    public ParentSiteEnabledBean toggleParentSiteEnableStatus(UserRequestBean userRequestBean){
        ParentSiteEnabledBean newParentSiteEnabledBean = new ParentSiteEnabledBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId() )) {

            int numOfRowsToggled = 0;
            boolean currentWebsiteStatus = false;
            ParentSiteEnabledBean parentSiteEnabledBean = getParentSiteEnabledStatusForUser(userRequestBean);

            ParentSiteEnabledData parentSiteEnabledData = new ParentSiteEnabledData();
            if(parentSiteEnabledBean!=null && !Utility.isNullOrEmpty(parentSiteEnabledBean.getParentSiteEnabledId())) {
                newParentSiteEnabledBean.setParentSiteEnabledId(parentSiteEnabledBean.getParentSiteEnabledId());
                newParentSiteEnabledBean.setUserId( parentSiteEnabledBean.getUserId() );
                currentWebsiteStatus = !parentSiteEnabledBean.isAllowed(); // Here we toggle (flip) the boolean flag.
                newParentSiteEnabledBean.setAllowed( currentWebsiteStatus);

                numOfRowsToggled = parentSiteEnabledData.updateParentSiteEnabledStatus(newParentSiteEnabledBean);
            } else {
                // Record doesnt exist. So create record with "Enabled" access.
                newParentSiteEnabledBean.setUserId(userRequestBean.getUserId());
                newParentSiteEnabledBean.setParentSiteEnabledId( Utility.getNewGuid() );
                newParentSiteEnabledBean.setAllowed( true);

                numOfRowsToggled = parentSiteEnabledData.insertParentSiteEnabledStatus(newParentSiteEnabledBean) ;
            }

            if(numOfRowsToggled<=0){
                //The toggle action failed.
                appLogging.error("Toggle of Client parent website access failed : " + parentSiteEnabledBean );
                newParentSiteEnabledBean = new ParentSiteEnabledBean();
            }
        }
        return newParentSiteEnabledBean;
    }

    public boolean resetParentSitePassword(UserRequestBean userRequestBean){
        boolean isSuccess = false;
        if( userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId())) {
            AccessUsers accessUsers = new AccessUsers();
            UserBean userBean = accessUsers.getUserById(userRequestBean);
            if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
                userRequestBean.setUserInfoId( userBean.getUserInfoId() );
                UserInfoBean userInfoBean = accessUsers.getUserInfoFromInfoId( userRequestBean );

                PasswordRequestBean passwordRequest = new PasswordRequestBean();
                passwordRequest.setUserId( userBean.getUserId() );

                try{
                    ManageUserPassword manageUserPassword = new ManageUserPassword();
                    isSuccess = manageUserPassword.resetUserPassword(passwordRequest);
                }catch (ManagePasswordException me) {
                    appLogging.error("Password for Parent Site was not reset for Client - " + userInfoBean );
                }

            } else {
                appLogging.error("Password nhot reset. Invalid User Id used - " + ParseUtil.checkNullObject(userRequestBean) );
            }

        }
        return isSuccess;

    }

    public boolean resetParentSitePassword(ParentSiteEnabledBean parentSiteEnabledBean){
        boolean isSuccess = false;
        if(parentSiteEnabledBean!=null && !Utility.isNullOrEmpty(parentSiteEnabledBean.getUserId())) {
            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setUserId( parentSiteEnabledBean.getUserId() );

            isSuccess = resetParentSitePassword(userRequestBean) ;
        } else {
            appLogging.error("Password not reset. Invalid User Id in ParentSiteEnabledBean used - " + ParseUtil.checkNullObject(parentSiteEnabledBean) );
        }
        return isSuccess;
    }
}
