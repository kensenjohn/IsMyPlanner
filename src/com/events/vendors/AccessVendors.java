package com.events.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.vendors.AccessVendorData;
import com.events.users.AccessUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 8:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessVendors {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public VendorBean getVendorByUserId(VendorRequestBean vendorRequestBean) {
        VendorBean vendorBean = new VendorBean();
        if(vendorRequestBean!=null && !Utility.isNullOrEmpty(vendorRequestBean.getUserId())   ) {
            AccessVendorData accessVendorData = new AccessVendorData();
            vendorBean = accessVendorData.getVendorFromUserId(vendorRequestBean);
        }
        return vendorBean;
    }

    public VendorBean getVendor(VendorRequestBean vendorRequestBean) {
        VendorBean vendorBean = new VendorBean();
        if(vendorRequestBean!=null && !Utility.isNullOrEmpty(vendorRequestBean.getVendorId()) ) {
            AccessVendorData accessVendorData = new AccessVendorData();
            vendorBean = accessVendorData.getVendor(vendorRequestBean);
        }
        return vendorBean;
    }


    public VendorResponseBean getVendorContactInfo(VendorRequestBean vendorRequestBean)  {
        VendorResponseBean vendorResponseBean =new VendorResponseBean();
        if(vendorRequestBean!=null  && !Utility.isNullOrEmpty(vendorRequestBean.getVendorId())  ) {

            VendorBean vendorBean =  getVendor(vendorRequestBean);
            if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId() ) ) {
                vendorResponseBean = getVendorContactInfo(vendorBean);

            } else {
                appLogging.info("Could not find a valid Vendor data for vendor request : " + vendorRequestBean );
            }
        } else {
            appLogging.error("Invalid request  : " + ParseUtil.checkNullObject(vendorRequestBean));
        }
        return  vendorResponseBean;
    }
    public VendorResponseBean getVendorContactInfo(VendorBean vendorBean)  {
        VendorResponseBean vendorResponseBean =new VendorResponseBean();
        if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId() ) ) {
            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setParentId(vendorBean.getVendorId() );

            AccessUsers accessUsers = new AccessUsers();
            UserBean userBean = accessUsers.getUserByParentId(userRequestBean);

            appLogging.info("Vendor Contact Information from UserBean : " + userBean );
            if(userBean!=null &&  !Utility.isNullOrEmpty(userBean.getUserId()))  {
                userRequestBean.setUserInfoId( userBean.getUserInfoId() );
                UserInfoBean userInfoBean = accessUsers.getUserInfoFromInfoId(userRequestBean);

                appLogging.info("Vendor Contact UserInfo : " + userInfoBean );
                vendorResponseBean.setVendorId(vendorBean.getVendorId());
                vendorResponseBean.setVendorBean(vendorBean);

                vendorResponseBean.setUserId( userBean.getUserId() );
                vendorResponseBean.setUserBean(userBean);

                vendorResponseBean.setUserInfoId( userInfoBean.getUserInfoId() );
                vendorResponseBean.setUserInfoBean(userInfoBean);
            } else {
                appLogging.info("Could not find a valid User Bean data for vendor - "  + vendorBean );
            }
        }
        return vendorResponseBean;
    }
}
