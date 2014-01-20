package com.events.data.vendors;

import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorLandingPageBean;
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
 * User: root
 * Date: 1/17/14
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessVendorLandingPageData {
        Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
        private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
        private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public VendorLandingPageBean getVendorLandingPageByVendorId(VendorLandingPageBean tmpVendorLandingPageBean) {
        VendorLandingPageBean vendorLandingPageBean = new VendorLandingPageBean();
        if(tmpVendorLandingPageBean!=null && !Utility.isNullOrEmpty(tmpVendorLandingPageBean.getVendorId())) {
            String sQuery = "SELECT * FROM GTVENDORLANDINGPAGE WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(tmpVendorLandingPageBean.getVendorId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessVendorLandingPageData.java", "getVendorLandingPageByVendorId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    vendorLandingPageBean = new VendorLandingPageBean(hmResult);
                }
            }
        }
        return vendorLandingPageBean;
    }

    public VendorLandingPageBean getVendorLandingPageByLandingPageId(VendorLandingPageBean tmpVendorLandingPageBean) {
        VendorLandingPageBean vendorLandingPageBean = new VendorLandingPageBean();
        if(tmpVendorLandingPageBean!=null && !Utility.isNullOrEmpty(tmpVendorLandingPageBean.getVendorLandingPageId())) {
            String sQuery = "SELECT * FROM GTVENDORLANDINGPAGE WHERE VENDORLANDINGPAGEID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(tmpVendorLandingPageBean.getVendorLandingPageId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessVendorLandingPageData.java", "getVendorLandingPageByLandingPageId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    vendorLandingPageBean = new VendorLandingPageBean(hmResult);
                }
            }
        }
        return vendorLandingPageBean;
    }
}
