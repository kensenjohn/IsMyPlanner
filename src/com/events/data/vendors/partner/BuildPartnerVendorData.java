package com.events.data.vendors.partner;

import com.events.bean.vendors.partner.PartnerVendorBean;
import com.events.bean.vendors.partner.PartnerVendorRequestBean;
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
 * Date: 2/4/14
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildPartnerVendorData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    //GTPARTNERVENDORS(PARTNERID VARCHAR(45) NOT NULL, FK_VENDORID VARCHAR(45) NOT NULL, FK_VENDORID_PARTNER
    public Integer insertPartnerVendor(PartnerVendorBean partnerVendorBean){
        int numOfRowsInserted = 0;
        if(partnerVendorBean!=null && !Utility.isNullOrEmpty(partnerVendorBean.getPartnerId())  && !Utility.isNullOrEmpty(partnerVendorBean.getVendorId())
                && !Utility.isNullOrEmpty(partnerVendorBean.getPartnerVendorId() ) ) {
            String sQuery = "INSERT INTO GTPARTNERVENDORS ( PARTNERID,FK_VENDORID,FK_VENDORID_PARTNER)"
                    + " VALUES ( ?,?,? )";
            ArrayList<Object> aParams = DBDAO.createConstraint(
                    partnerVendorBean.getPartnerId(), partnerVendorBean.getVendorId(), partnerVendorBean.getPartnerVendorId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildPartnerVendorData.java", "insertPartnerVendor() ");
        }
        return numOfRowsInserted;
    }

    public Integer deletePartnerVendor( PartnerVendorRequestBean partnerVendorRequest){
        int numOfRowsInserted = 0;
        if(partnerVendorRequest!=null && !Utility.isNullOrEmpty(partnerVendorRequest.getPartnerVendorId() ) ) {
            String sQuery = "DELETE FROM GTPARTNERVENDORS WHERE FK_VENDORID_PARTNER =? ";
            ArrayList<Object> aParams = DBDAO.createConstraint(partnerVendorRequest.getPartnerVendorId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildPartnerVendorData.java", "deletePartnerVendor() ");
        }
        return numOfRowsInserted;
    }
}
