package com.events.data.vendors.website;

import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/27/14
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendorWebsiteData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    // VENDORWEBSITEID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL,FK_USERID VARCHAR(45) NOT NULL,
    // CREATEDATE BIGINT(20) NOT NULL DEFAULT 0, HUMANCREATEDATE
    public Integer insertVendorWebsite(VendorWebsiteBean vendorWebsiteBean) {
        Integer numOfRowsInserted = 0;
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())){
            String sQuery = "INSERT INTO GTVENDORWEBSITE ( VENDORWEBSITEID,FK_VENDORID,FK_USERID,       CREATEDATE,HUMANCREATEDATE) VALUES (?,?,?,    ?,?) ";
            ArrayList<Object> aParams = DBDAO.createConstraint(vendorWebsiteBean.getVendorWebsiteId(), vendorWebsiteBean.getVendorId(),vendorWebsiteBean.getUserId(),
                    DateSupport.getEpochMillis(),DateSupport.getUTCDateTime());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildVendorWebsiteData.java", "insertVendorWebsite() ");
        }
        return numOfRowsInserted;
    }

    public Integer updateVendorWebsite(VendorWebsiteBean vendorWebsiteBean) {
        Integer numOfRowsInserted = 0;
        if(vendorWebsiteBean!=null && !Utility.isNullOrEmpty(vendorWebsiteBean.getVendorWebsiteId())){
            String sQuery = "UPDATE GTVENDORWEBSITE SET CREATEDATE = ?,HUMANCREATEDATE =?,FK_USERID =? WHERE VENDORWEBSITEID =? AND FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(DateSupport.getEpochMillis(),DateSupport.getUTCDateTime(),vendorWebsiteBean.getUserId(),
                    vendorWebsiteBean.getVendorWebsiteId(), vendorWebsiteBean.getVendorId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildVendorWebsiteData.java", "updateVendorWebsite() ");
        }
        return numOfRowsInserted;
    }
}
