package com.events.data.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/21/13
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessVendorData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public VendorBean getVendorFromUserId(VendorRequestBean vendorRequestBean) {
        VendorBean vendorBean = new VendorBean();

        if(vendorRequestBean!=null) {
            String sQuery  = "SELECT * FROM GTUSER U, GTVENDOR V WHERE U.USERID = ? AND U.FK_PARENTID = V.VENDORID AND U.USERTYPE = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(vendorRequestBean.getUserId(),Constants.USER_TYPE.VENDOR.getType());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessVendorData.java", "getVendorFromUserId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    vendorBean = new VendorBean(hmResult);
                }
            }
        }
        return vendorBean;
    }
}
