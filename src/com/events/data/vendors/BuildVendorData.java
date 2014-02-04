package com.events.data.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/20/13
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendorData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertVendor(VendorBean vendorBean) {
        String sQuery = "INSERT INTO GTVENDOR ( VENDORID,VENDORNAME,CREATEDATE,    HUMANCREATEDATE,MODIFIEDDATE,HUMANMODIFIEDDATE,   " +
                "    FOLDER)"
                + " VALUES ( ?,?,?,   ?,?,?,   ?)";
        ArrayList<Object> aParams = DBDAO.createConstraint(
                vendorBean.getVendorId(), vendorBean.getVendorName(), DateSupport.getEpochMillis(),
                DateSupport.getUTCDateTime(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                vendorBean.getFolder());

        int numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildVendorData.java", "insertVendor() ");

        return numOfRowsInserted;
    }

    public Integer updateVendor(VendorBean vendorBean) {
        String sQuery = "UPDATE GTVENDOR SET VENDORNAME=?,MODIFIEDDATE=?,HUMANMODIFIEDDATE=?    WHERE  VENDORID=?";
        ArrayList<Object> aParams = DBDAO.createConstraint( vendorBean.getVendorName(), DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),
                vendorBean.getVendorId());

        int numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildVendorData.java", "updateVendor() ");

        return numOfRowsInserted;
    }
}
