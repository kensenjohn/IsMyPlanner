package com.events.users;

import com.events.bean.users.PasswordRequestBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.users.permissions.UserRolePermissionRequestBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.ExceptionHandler;
import com.events.common.exception.users.EditUserException;
import com.events.common.exception.users.EditUserInfoException;
import com.events.common.exception.users.ManagePasswordException;
import com.events.common.exception.vendors.EditVendorException;
import com.events.data.users.BuildUserData;
import com.events.users.permissions.UserRolePermission;
import com.events.vendors.BuildVendors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/13/13
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildUsers {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer createUser(UserBean userBean) throws EditUserException {
        Integer iNumOfRecords = 0;
        if(userBean!=null && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserId()))
                && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserInfoId())) ) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.insertUser(userBean);
            if(iNumOfRecords<=0) {
                appLogging.error("User was not be created : " + userBean);
                throw new EditUserException();
            }
        } else {
            appLogging.error("User not created because some data is missing userid : " + ParseUtil.checkNull(userBean.getUserId()) +
                    "  userinfoid : " + ParseUtil.checkNull(userBean.getUserInfoId()) );
            throw new EditUserException();
        }
        return iNumOfRecords;
    }
    public Integer updateUser(UserBean userBean) throws EditUserException {
        Integer iNumOfRecords = 0;
        if(userBean!=null && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserId()))
                && !"".equalsIgnoreCase(ParseUtil.checkNull(userBean.getUserInfoId())) ) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.updateUser(userBean);
        } else {
            appLogging.error("User was not updated because some data is missing userid : " + ParseUtil.checkNull(userBean.getUserId()) +
                    "  userinfoid : " + ParseUtil.checkNull(userBean.getUserInfoId()) );
            throw new EditUserException();
        }
        return iNumOfRecords;
    }

    public UserBean registerUser(UserRequestBean userRequestBean) throws EditUserException, EditUserInfoException, ManagePasswordException {
        UserBean userBean = new UserBean();
        boolean isError = false;
        if(userRequestBean!=null && !"".equalsIgnoreCase(userRequestBean.getEmail())
                && !"".equalsIgnoreCase(ParseUtil.checkNullObject(userRequestBean.getPasswordRequestBean()))  ) {

            // Depending on Type of User
            if( userRequestBean.isPlanner() ) {
                BuildVendors buildVendors = new BuildVendors();
                try{
                    VendorBean vendorBean = buildVendors.registerVendor();
                    userRequestBean.setParentId( vendorBean.getVendorId() );
                    userRequestBean.setUserType(Constants.USER_TYPE.VENDOR);
                } catch(EditVendorException e) {
                    isError = true;
                    appLogging.info("Error while creating a vendor : " + ExceptionHandler.getStackTrace(e));
                    throw new EditUserException();
                }
            }

            if( !Utility.isNullOrEmpty(userRequestBean.getParentId()) ) {
                UserInfoBean userInfoBean = generateNewUserInfoBean(userRequestBean);
                Integer iNumOfUserInfoIdRows = createUserInfo(userInfoBean);

                if( iNumOfUserInfoIdRows>0 ) {
                    userRequestBean.setUserInfoId( userInfoBean.getUserInfoId() );
                    userBean = generateNewUserBean(userRequestBean);
                    Integer iNumOfUsersRows = createUser( userBean );

                    if(iNumOfUsersRows>0) {
                        ManageUserPassword managePasswords = new ManageUserPassword();
                        PasswordRequestBean passwordRequest = userRequestBean.getPasswordRequestBean();
                        passwordRequest.setUserId( userBean.getUserId() );
                        Integer iNumOfPasswordRows = managePasswords.createPassword(passwordRequest);
                        if(iNumOfPasswordRows<=0) {
                            isError = true;
                            appLogging.info("Error creating user password during registration : " + userRequestBean);
                        } else {
                            UserRolePermissionRequestBean userRolePermRequest = new UserRolePermissionRequestBean();
                            userRolePermRequest.setUserId( userBean.getUserId() );
                            userRolePermRequest.setParentId( userRequestBean.getParentId() );
                            userRolePermRequest.setUserType( userRequestBean.getUserType() );
                            UserRolePermission userRolePermission = new UserRolePermission();
                            if(!userRolePermission.initiatePermissionBootup( userRolePermRequest ) ) {
                                isError = true;
                                appLogging.info("Unable to set the permission." + isError );
                            }
                        }

                    } else {
                        isError = true;
                        appLogging.info("Error occurred while creating user : " + userRequestBean);
                    }
                } else {
                    isError = true;
                    appLogging.info("Error occurred while creating user info: " + userRequestBean);
                }
            }  else {
                isError = true;
                appLogging.info("Error occurred while creating User's Parent : " + userRequestBean);
            }

        } else {
            isError = true;
            appLogging.info("Invalid request used to register user : " + ParseUtil.checkNullObject(userRequestBean));
        }
        if(isError) {
            userBean = new UserBean();
        }
        return userBean;
    }

    public UserBean generateNewUserBean(UserRequestBean userRequestBean) {
        UserBean userBean = generateExistingUserBean(userRequestBean);
        userBean.setUserId( Utility.getNewGuid() );
        return userBean;
    }
    public UserBean generateExistingUserBean(UserRequestBean userRequestBean) {
        UserBean userBean = new UserBean();
        if(userRequestBean!=null){
            userBean.setParentId(userRequestBean.getParentId());
            userBean.setUserInfoId( userRequestBean.getUserInfoId() );
            userBean.setUserType( userRequestBean.getUserType() );
        }
        return userBean;
    }

    public UserInfoBean generateNewUserInfoBean(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = generateExistingUserInfoBean(userRequestBean);
        userInfoBean.setUserInfoId(Utility.getNewGuid());
        return userInfoBean;
    }

    public UserInfoBean generateExistingUserInfoBean(UserRequestBean userRequestBean) {
        UserInfoBean userInfoBean = new UserInfoBean();
        if(userRequestBean!=null) {
            userInfoBean.setFirstName( ParseUtil.checkNull(userRequestBean.getFirstName()) );
            userInfoBean.setLastName( ParseUtil.checkNull(userRequestBean.getLastName()) );
            userInfoBean.setEmail( ParseUtil.checkNull(userRequestBean.getEmail())  );
            userInfoBean.setCompany( ParseUtil.checkNull(userRequestBean.getCompanyName()));
            userInfoBean.setCellPhone( ParseUtil.checkNull(userRequestBean.getCellPhone()));
            userInfoBean.setPhoneNum( ParseUtil.checkNull(userRequestBean.getWorkPhone()));
            userInfoBean.setAddress1( ParseUtil.checkNull(userRequestBean.getAddress1()));
            userInfoBean.setAddress2( ParseUtil.checkNull(userRequestBean.getAddress2()));
            userInfoBean.setCity( ParseUtil.checkNull(userRequestBean.getCity()));
            userInfoBean.setState( ParseUtil.checkNull(userRequestBean.getState()));
            userInfoBean.setCountry( ParseUtil.checkNull(userRequestBean.getCountry()));
            userInfoBean.setZipcode( ParseUtil.checkNull(userRequestBean.getPostalCode()));
        }
        return userInfoBean;
    }

    public Integer  createUserInfo(UserInfoBean userInfoBean) throws EditUserInfoException {
        Integer iNumOfRecords = 0;
        if(userInfoBean!=null && !"".equalsIgnoreCase(userInfoBean.getUserInfoId())) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.insertUserInfo(userInfoBean);
            if(iNumOfRecords<=0) {
                appLogging.error("User Info was not created : " + userInfoBean);
                throw new EditUserInfoException();
            }
        } else {
            appLogging.error("User Info was not created because some data is missing : " + ParseUtil.checkNullObject(userInfoBean)  );
            throw new EditUserInfoException();
        }
        return iNumOfRecords;
    }


    public Integer  updateUserInfo(UserInfoBean userInfoBean) throws EditUserInfoException{
        Integer iNumOfRecords = 0;
        if(userInfoBean!=null && !"".equalsIgnoreCase(userInfoBean.getUserInfoId())) {
            BuildUserData buildUserData = new BuildUserData();
            iNumOfRecords = buildUserData.updateUserInfo(userInfoBean);
        }  else {
            appLogging.error("User Info was not updated because some data is missing userid : " + ParseUtil.checkNullObject(userInfoBean)  );
            throw new EditUserInfoException();
        }
        return iNumOfRecords;
    }
}
