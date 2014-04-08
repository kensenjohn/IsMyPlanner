package com.events.bean.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorResponseBean {
    private String vendorId = Constants.EMPTY;
    private String userId = Constants.EMPTY;
    private UserBean userBean = new UserBean();
    private String userInfoId = Constants.EMPTY;
    private UserInfoBean userInfoBean = new UserInfoBean();
    private String subDomain = Constants.EMPTY;
    private String vendorWebsiteId = Constants.EMPTY;
    private VendorWebsiteBean vendorWebsiteBean = new VendorWebsiteBean();

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public VendorWebsiteBean getVendorWebsiteBean() {
        return vendorWebsiteBean;
    }

    public void setVendorWebsiteBean(VendorWebsiteBean vendorWebsiteBean) {
        this.vendorWebsiteBean = vendorWebsiteBean;
    }

    public String getVendorWebsiteId() {
        return vendorWebsiteId;
    }

    public void setVendorWebsiteId(String vendorWebsiteId) {
        this.vendorWebsiteId = vendorWebsiteId;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    private VendorBean vendorBean = new VendorBean();

    public VendorBean getVendorBean() {
        return vendorBean;
    }

    public void setVendorBean(VendorBean vendorBean) {
        this.vendorBean = vendorBean;
    }
}
