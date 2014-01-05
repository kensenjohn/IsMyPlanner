package com.events.data.clients;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
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
 * Date: 12/17/13
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessClientData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public HashMap<Integer,ClientBean> getAllClientsSummary(ClientRequestBean clientRequestBean) {
        HashMap<Integer,ClientBean> hmClientBean = new HashMap<Integer, ClientBean>();
        if(clientRequestBean!=null) {
            String sQuery  = "SELECT * FROM GTCLIENT WHERE FK_VENDORID = ? ORDER BY CREATEDATE DESC";
            ArrayList<Object> aParams = DBDAO.createConstraint(clientRequestBean.getVendorId());
            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessUserData.java", "getUserDataByUserName()");

            int iNumOfClients = 0;
            for( HashMap<String, String> hmResult : arrResult ) {
                ClientBean clientBean = new ClientBean(hmResult);
                hmClientBean.put(iNumOfClients, clientBean);
                iNumOfClients++;
            }
        }
        return hmClientBean;
    }

    public ClientBean getClient(ClientRequestBean clientRequestBean) {
        ClientBean clientBean = new ClientBean();
        if(clientRequestBean!=null && !"".equalsIgnoreCase(clientRequestBean.getClientId()) && !"".equalsIgnoreCase(clientRequestBean.getVendorId())) {
            String sQuery  = "SELECT * FROM GTCLIENT WHERE FK_VENDORID = ? AND CLIENTID = ? ORDER BY CREATEDATE DESC";
            ArrayList<Object> aParams = DBDAO.createConstraint(clientRequestBean.getVendorId(),clientRequestBean.getClientId());

            ArrayList<HashMap<String, String>> arrResult = DBDAO.getDBData(EVENTADMIN_DB, sQuery, aParams, false, "AccessClientData.java", "getClient()");

            for( HashMap<String, String> hmResult : arrResult ) {
                clientBean = new ClientBean(hmResult);
            }
        }
        return  clientBean;
    }
}
