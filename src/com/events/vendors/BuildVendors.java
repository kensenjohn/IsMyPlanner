package com.events.vendors;

import com.events.bean.users.UserBean;
import com.events.bean.vendors.VendorBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.exception.users.EditUserException;
import com.events.common.exception.vendors.EditVendorException;
import com.events.data.users.BuildUserData;
import com.events.data.vendors.BuildVendorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/20/13
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildVendors {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer createVendor(VendorBean vendorBean) throws EditVendorException {
        Integer iNumOfRecords = 0;
        if(vendorBean!=null && !"".equalsIgnoreCase(ParseUtil.checkNull(vendorBean.getVendorId()))) {
            BuildVendorData buildVendorData = new BuildVendorData();
            iNumOfRecords = buildVendorData.insertVendor(vendorBean);
            if(iNumOfRecords<=0) {
                appLogging.error("Vendor was not be created : " + vendorBean);
                throw new EditVendorException();
            }
        } else {
            appLogging.error("VEndor not created because some data is missing : " + ParseUtil.checkNullObject(vendorBean)  );
            throw new EditVendorException();
        }
        return iNumOfRecords;
    }

    public VendorBean registerVendor()  throws EditVendorException  {
        VendorBean vendorBean = new VendorBean();
        vendorBean.setVendorId(Utility.getNewGuid());

        Integer iNumOfVendorRecords = createVendor( vendorBean );
        if(iNumOfVendorRecords<=0){
            vendorBean = new VendorBean();
        }
        return vendorBean;
    }
}
