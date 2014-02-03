package com.events.users;

import com.events.bean.users.*;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.users.EditUserException;
import com.events.data.users.AccessUserData;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessUsers {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public UserBean getUserByEmail(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null && !"".equalsIgnoreCase(userRequestBean.getEmail()) ) {
            AccessUserData accessUserData = new AccessUserData();
            userBean = accessUserData.getUserDataByEmail(userRequestBean);
        }
        return userBean;
    }

    public UserBean getUserById(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null && !"".equalsIgnoreCase(userRequestBean.getUserId()) ) {
            AccessUserData accessUserData = new AccessUserData();
            userBean = accessUserData.getUserDataByID(userRequestBean);
        }
        return userBean;
    }
    public ArrayList<UserInfoBean> getAllUsersInfo(ArrayList<UserBean> arrUserBean) {
        ArrayList<UserInfoBean> arrUserInfoBeans =  new ArrayList<UserInfoBean>();
        if(arrUserBean!=null && !arrUserBean.isEmpty() ) {
            AccessUserData accessUserData = new AccessUserData();
             arrUserInfoBeans = accessUserData.getUserInfoFromAllUserBean( arrUserBean );
        }
        return arrUserInfoBeans;
    }

    public ArrayList<UserBean> getAllUsersByParentId(UserRequestBean userRequestBean) {
        ArrayList<UserBean> arrUserBean =  new ArrayList<UserBean>();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getParentId()) ) {
            AccessUserData accessUserData = new AccessUserData();
            arrUserBean = accessUserData.getUserDataByParentID(userRequestBean);
        }
        return arrUserBean;
    }
    public UserBean getUserByParentId(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getParentId()) ) {
            AccessUserData accessUserData = new AccessUserData();
            ArrayList<UserBean> arrUserBean = accessUserData.getUserDataByParentID(userRequestBean);
            if(arrUserBean!=null && !arrUserBean.isEmpty() ){
                for(UserBean tmpUserBean : arrUserBean) {
                    userBean = tmpUserBean;
                }
            }
        }
        return userBean;
    }

    public UserInfoBean getUserInfoFromInfoId(UserRequestBean userRequestBean)  {
        UserInfoBean userInfoBean = new UserInfoBean();
        if(userRequestBean!=null && !"".equalsIgnoreCase(userRequestBean.getUserInfoId()) ) {
            AccessUserData accessUserData = new AccessUserData();
            userInfoBean = accessUserData.getUserInfoFromInfoId(userRequestBean);
        }
        return userInfoBean;
    }

    public UserInfoBean getUserInfoFromUserId(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = new UserInfoBean();
        if(userRequestBean!=null && !"".equalsIgnoreCase(userRequestBean.getUserId()) ) {
            AccessUserData accessUserData = new AccessUserData();
            userInfoBean = accessUserData.getUserInfoFromUserId(userRequestBean);
        }
        return userInfoBean;
    }

    public boolean authenticateUser(UserRequestBean userRequestBean) {
        boolean isUserAuthenticated = false;
        if(userRequestBean!=null && !"".equalsIgnoreCase(userRequestBean.getUserId()) && userRequestBean.getPasswordRequestBean()!=null ) {
            PasswordRequestBean passwordRequestBean = userRequestBean.getPasswordRequestBean();
            ManageUserPassword manageUserPassword = new ManageUserPassword();
            isUserAuthenticated = manageUserPassword.isUserPasswordAuthenticated( passwordRequestBean );
        }
        return isUserAuthenticated;
    }

    public UserBean getLoggedInUserBean(Cookie[] cookies) {
        UserBean userBean = new UserBean();
        if(cookies!=null) {
            for(int cookieCount = 0; cookieCount < cookies.length; cookieCount++)  {
                Cookie cookie1 = cookies[cookieCount];
                if (Constants.COOKIEUSER_ID.equals(cookie1.getName())) {
                    String sCookieUserId = ParseUtil.checkNull(cookie1.getValue());

                    CookieRequestBean cookieRequestBean = new CookieRequestBean();
                    cookieRequestBean.setCookieUserId(sCookieUserId);

                    CookieUser cookieUser = new CookieUser();
                    CookieUserResponseBean cookieUserResponseBean = cookieUser.getCookieUser(cookieRequestBean);

                    if(cookieUserResponseBean!=null && !Utility.isNullOrEmpty(cookieUserResponseBean.getCookieUserId())) {
                        CookieUserBean cookieUserBean =  cookieUserResponseBean.getCookieUserBean();

                        if( (DateSupport.getEpochMillis() - cookieUserBean.getCreateDate()) < (12*60*60*1000)  ) {
                            UserRequestBean userRequestBean = new UserRequestBean();
                            userRequestBean.setUserId(cookieUserBean.getUserId() );

                            AccessUsers accessUsers = new AccessUsers();
                            userBean = accessUsers.getUserById(userRequestBean);

                            userRequestBean.setUserInfoId( userBean.getUserInfoId() );
                            UserInfoBean userInfoBean = accessUsers.getUserInfoFromInfoId(userRequestBean);
                            userBean.setUserInfoBean( userInfoBean );
                        }
                    }
                }
            }
        }
        return userBean;
    }

    public JSONObject getJsonUserBeanAndInfo(ArrayList<UserBean> arrUserBean, ArrayList<UserInfoBean> arrUserInfoBean){
        JSONObject jsonObject = new JSONObject();
        if(arrUserBean!=null && !arrUserBean.isEmpty() && arrUserInfoBean!=null && !arrUserInfoBean.isEmpty()) {
            Integer iNumOfUserBeanObjs = 0;
            for(UserBean userBean : arrUserBean) {

                for(UserInfoBean userInfoBean : arrUserInfoBean){
                    if(userBean.getUserInfoId().equalsIgnoreCase(userInfoBean.getUserInfoId()) ) {
                        userBean.setUserInfoBean( userInfoBean );
                        jsonObject.put( iNumOfUserBeanObjs.toString(), userBean.toJson() );
                        iNumOfUserBeanObjs++;
                    }
                }
            }
            jsonObject.put("num_of_userbean", iNumOfUserBeanObjs);
        }
        return jsonObject;
    }
}
