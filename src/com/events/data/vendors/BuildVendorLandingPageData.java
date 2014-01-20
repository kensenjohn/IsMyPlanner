package com.events.data.vendors;

import com.events.bean.vendors.VendorLandingPageBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/17/14
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendorLandingPageData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);


    public Integer insertVendorLandingPage(VendorLandingPageBean vendorLandingPageBean) {
        Integer numOfRowsInserted = 0;
        if(vendorLandingPageBean!=null && !Utility.isNullOrEmpty(vendorLandingPageBean.getVendorLandingPageId())){
            String sQuery = "INSERT INTO GTVENDORLANDINGPAGE ( VENDORLANDINGPAGEID,FK_VENDORID, THEME) VALUES (?,?,?) ";
            ArrayList<Object> aParams = DBDAO.createConstraint( vendorLandingPageBean.getVendorLandingPageId(), vendorLandingPageBean.getVendorId(),
                    vendorLandingPageBean.getTheme());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildVendorLandingPageData.java", "insertVendorLandingPage() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateVendorLandingPage(VendorLandingPageBean vendorLandingPageBean) {
        Integer numOfRowsInserted = 0;
        if(vendorLandingPageBean!=null && !Utility.isNullOrEmpty(vendorLandingPageBean.getVendorLandingPageId())){
            String sQuery = "UPDATE GTVENDORLANDINGPAGE SET THEME = ? WHERE VENDORLANDINGPAGEID =? AND FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(vendorLandingPageBean.getTheme(), vendorLandingPageBean.getVendorLandingPageId(),
                    vendorLandingPageBean.getVendorId());

             numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildVendorLandingPageData.java", "insertVendorLandingPage() ");
        }
        return numOfRowsInserted;
    }
}
