package com.events.users;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.users.*;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.users.AccessUserData;
import com.events.vendors.AccessVendors;
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
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserInfoId())  ) {
            AccessUserData accessUserData = new AccessUserData();
            userInfoBean = accessUserData.getUserInfoFromInfoId(userRequestBean);
        }
        return userInfoBean;
    }

    public UserInfoBean getUserInfoFromUserId(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = new UserInfoBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId()) ) {
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

    public ArrayList<UserBean> getAllVendorUsers(UserRequestBean userRequestBean){
        ArrayList<UserBean> arrUserBean = new ArrayList<UserBean>();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getVendorId())) {
            AccessUserData accessUserData = new AccessUserData();
            arrUserBean = accessUserData.getAllVendorUsers( userRequestBean );
        }

        return arrUserBean;
    }

    public ArrayList<UserBean> getAllVendorClientUsers(UserRequestBean userRequestBean){
        ArrayList<UserBean> arrUserBean = new ArrayList<UserBean>();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getVendorId())) {
            AccessUserData accessUserData = new AccessUserData();
            arrUserBean = accessUserData.getAllVendorClientUsers( userRequestBean );
        }
        return arrUserBean;
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
    public JSONObject getJsonUserBean(ArrayList<UserBean> arrUserBean){
        JSONObject jsonObject = new JSONObject();
        if(arrUserBean!=null && !arrUserBean.isEmpty()  ) {
            Integer iNumOfUserBean = 0;
            for(UserBean userBean : arrUserBean) {
                if(userBean!=null){
                    jsonObject.put( iNumOfUserBean.toString(), userBean.toJson() );
                    iNumOfUserBean++;
                }
            }
            jsonObject.put("num_of_userbean", iNumOfUserBean);
        }
        return jsonObject;
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
    public VendorBean getVendorFromUser(UserBean userBean){
        VendorBean vendorBean = new VendorBean();
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId())) {
            String sVendorId = Constants.EMPTY;
            if(Constants.USER_TYPE.VENDOR.equals( userBean.getUserType() )) {
                sVendorId = userBean.getParentId();

            } else if(Constants.USER_TYPE.CLIENT.equals( userBean.getUserType())) {
                ClientRequestBean clientRequestBean = new ClientRequestBean();
                clientRequestBean.setClientId( userBean.getParentId() );

                AccessClients accessClients = new AccessClients();
                ClientBean clientBean = accessClients.getClientDataByVendorAndClient(clientRequestBean);
                if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getVendorId())) {
                    sVendorId = ParseUtil.checkNull(clientBean.getVendorId());
                }
            }

            if(!Utility.isNullOrEmpty(sVendorId)){
                VendorRequestBean vendorRequestBean = new VendorRequestBean();
                vendorRequestBean.setVendorId( sVendorId );

                AccessVendors accessVendors = new AccessVendors();
                vendorBean = accessVendors.getVendor(vendorRequestBean);
            }
        }
        return  vendorBean;
    }

    public ParentTypeBean  getParentTypeBeanFromUser(UserBean userBean){
        ParentTypeBean parentTypeBean = new ParentTypeBean();
        if(userBean!=null && !Utility.isNullOrEmpty(userBean.getUserId()) && !Utility.isNullOrEmpty(userBean.getParentId())) {
            boolean isUserAClient = false;
            ClientRequestBean clientRequestBean = new ClientRequestBean();
            clientRequestBean.setClientId( userBean.getParentId());

            AccessClients accessClients = new AccessClients();
            ClientBean clientBean = accessClients.getClient( clientRequestBean );
            if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getClientId())) {
                isUserAClient = true;
            }

            VendorBean vendorBean = new VendorBean();
            boolean isUserAVendor = false;
            VendorRequestBean vendorRequestBean = new VendorRequestBean();
            AccessVendors accessVendor = new AccessVendors();
            if(isUserAClient) {
                vendorRequestBean.setVendorId(  clientBean.getVendorId() );
                vendorBean = accessVendor.getVendor( vendorRequestBean );
            } else {
                vendorRequestBean.setUserId( userBean.getUserId() );
                vendorBean = accessVendor.getVendorByUserId( vendorRequestBean ) ;  // get  vendor from user id

                if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())) {
                    isUserAVendor = true;
                }
            }

            if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getClientId())){
                parentTypeBean.setClientdId( clientBean.getClientId() );
            }
            if(clientBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId())){
                parentTypeBean.setVendorId( vendorBean.getVendorId() );
            }

            parentTypeBean.setClientBean( clientBean );
            parentTypeBean.setVendorBean( vendorBean );
            parentTypeBean.setUserAClient( isUserAClient );
            parentTypeBean.setUserAVendor( isUserAVendor );
        }
        return parentTypeBean;
    }
}
