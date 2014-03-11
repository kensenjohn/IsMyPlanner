package com.events.data.vendors.website;

import com.events.bean.vendors.VendorBean;
import com.events.bean.vendors.VendorResponseBean;
import com.events.bean.vendors.website.VendorWebsiteBean;
import com.events.bean.vendors.website.VendorWebsiteFeatureBean;
import com.events.bean.vendors.website.VendorWebsiteRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
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

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessVendorWebsiteData.java", "getVendorWebsiteByWebsiteId()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    vendorLandingPageBean = new VendorWebsiteBean(hmResult);
                }
            }
        }
        return vendorLandingPageBean;
    }

    public VendorResponseBean getVendorBySubDomain(VendorWebsiteRequestBean vendorWebsiteRequestBean) {
        VendorResponseBean vendorResponseBean = new VendorResponseBean();
        if(vendorWebsiteRequestBean!=null && !Utility.isNullOrEmpty(vendorWebsiteRequestBean.getSubDomain())) {
            String sSubDomain = vendorWebsiteRequestBean.getSubDomain();
            String sQuery = "SELECT V.* , VW.* FROM GTVENDORWEBSITEFEATURES VF, GTVENDORWEBSITE VW, GTVENDOR V WHERE  " +
                    " VF.FEATURENAME = '"+Constants.VENDOR_WEBSITE_FEATURETYPE.subdomain_name.toString()+"' AND VF.FK_VENDORWEBSITEID = VW.VENDORWEBSITEID " +
                    " AND VF.VALUE = ? AND V.VENDORID = VW.FK_VENDORID";
            appLogging.info("getVendorBySubDomain " + sQuery + " -  " + sSubDomain);
            ArrayList<Object> aParams = DBDAO.createConstraint(sSubDomain);
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessVendorWebsiteData.java", "getVendorBySubDomain()");


            if( arrResult!=null && !arrResult.isEmpty() ) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    VendorBean vendorBean = new VendorBean(hmResult);
                    VendorWebsiteBean vendorWebsiteBean = new VendorWebsiteBean(hmResult);

                    vendorResponseBean.setVendorBean( vendorBean );
                    vendorResponseBean.setSubDomain( sSubDomain );
                    vendorResponseBean.setVendorWebsiteId(ParseUtil.checkNull(vendorWebsiteBean.getVendorWebsiteId()) );
                    vendorResponseBean.setVendorWebsiteBean( vendorWebsiteBean );
                }
            }

        }
        return vendorResponseBean;
    }
}
