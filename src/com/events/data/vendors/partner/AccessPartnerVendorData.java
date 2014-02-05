package com.events.data.vendors.partner;

import com.events.bean.vendors.partner.PartnerVendorBean;
import com.events.bean.vendors.partner.PartnerVendorRequestBean;
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
 * Date: 2/4/14
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessPartnerVendorData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public PartnerVendorBean getPartnerVendorBean (PartnerVendorRequestBean partnerVendorRequestBean) {
        PartnerVendorBean partnerVendorBean = new PartnerVendorBean();
        if(partnerVendorRequestBean!=null && !Utility.isNullOrEmpty(partnerVendorRequestBean.getPartnerVendorId())) {
            String sQuery = "SELECT * FROM GTPARTNERVENDORS WHERE FK_VENDORID_PARTNER = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(partnerVendorRequestBean.getPartnerVendorId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessPartnerVendorData.java", "getPartnerVendorBean()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    partnerVendorBean = new PartnerVendorBean(hmResult);
                }
            }
        }
        return partnerVendorBean;
    }
    public ArrayList<PartnerVendorBean> getAllPartnerVendorBean (PartnerVendorRequestBean partnerVendorRequestBean) {
        ArrayList<PartnerVendorBean> arrPartnerVendorBean = new ArrayList<PartnerVendorBean>();
        if(partnerVendorRequestBean!=null && !Utility.isNullOrEmpty(partnerVendorRequestBean.getVendorId())) {
            String sQuery = "SELECT * FROM GTPARTNERVENDORS WHERE FK_VENDORID = ?";
            ArrayList<Object> aParams = DBDAO.createConstraint(partnerVendorRequestBean.getVendorId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessPartnerVendorData.java", "getAllPartnerVendorBean()");
            if(arrResult!=null) {
                for(HashMap<String, String> hmResult : arrResult ) {
                    PartnerVendorBean partnerVendorBean = new PartnerVendorBean(hmResult);
                    arrPartnerVendorBean.add(partnerVendorBean);
                }
            }
        }
        return arrPartnerVendorBean;
    }

}
