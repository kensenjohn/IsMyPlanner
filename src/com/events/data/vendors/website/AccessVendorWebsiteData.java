package com.events.data.vendors.website;

import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/27/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessVendorWebsiteData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTVENDORWEBSITE( VENDORWEBSITEID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL,FK_USERID
    public VendorWebsiteBean getVendorWebsiteByVendorId(VendorWebsiteBean tmpVendorWebsiteBean) {
        VendorWebsiteBean vendorLandingPageBean = new VendorWebsiteBean();
        if(tmpVendorWebsiteBean!=null && !Utility.isNullOrEmpty(tmpVendorWebsiteBean.getVendorId())) {
            String sQuery = "SELECT * FROM GTVENDORWEBSITE WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(tmpVendorWebsiteBean.getVendorId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessVendorWebsiteData.java", "getVendorWebsiteByVendorId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    vendorLandingPageBean = new VendorWebsiteBean(hmResult);
                }
            }
        }
        return vendorLandingPageBean;
    }

    public VendorWebsiteBean getVendorWebsiteByWebsiteId(VendorWebsiteBean tmpVendorWebsiteBean) {
        VendorWebsiteBean vendorLandingPageBean = new VendorWebsiteBean();
        if(tmpVendorWebsiteBean!=null && !Utility.isNullOrEmpty(tmpVendorWebsiteBean.getVendorWebsiteId())) {
            String sQuery = "SELECT * FROM GTVENDORWEBSITE WHERE VENDORWEBSITEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(tmpVendorWebsiteBean.getVendorWebsiteId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessVendorLandingPageData.java", "getVendorWebsiteByWebsiteId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    vendorLandingPageBean = new VendorWebsiteBean(hmResult);
                }
            }
        }
        return vendorLandingPageBean;
    }
}
