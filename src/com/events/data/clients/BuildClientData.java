package com.events.data.clients;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
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
 * User: root
 * Date: 12/17/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildClientData {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertClientData(ClientBean clientBean) {
        int numOfRowsInserted = 0;
        if(clientBean!=null) {
            String sQuery = "insert into GTCLIENT ( CLIENTID,CLIENTNAME,IS_CORPORATE_CLIENT,   CREATEDATE,HUMANCREATEDATE,FK_VENDORID,    IS_LEAD) VALUES(?,?,?,  ?,?,?,   ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(clientBean.getClientId(), clientBean.getClientName(), (clientBean.isCorporateClient()?"1":"0"),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), clientBean.getVendorId(),
                    clientBean.isLead()?"1":"0");
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildClientData.java", "insertClientData() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateClientData(ClientBean clientBean) {
        int numOfRowsInserted = 0;
        if(clientBean!=null) {
            String sQuery = "UPDATE GTCLIENT SET CLIENTID =?,CLIENTNAME =?,IS_CORPORATE_CLIENT =?,    MODIFIEDDATE =?,HUMANMODIFIEDDATE =?,FK_VENDORID = ?,     " +
                    " IS_LEAD=? WHERE CLIENTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(clientBean.getClientId(), clientBean.getClientName(), (clientBean.isCorporateClient()?"1":"0"),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(),clientBean.getVendorId(),
                    clientBean.isLead()?"1":"0", clientBean.getClientId());
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildClientData.java", "updateClientData() ");
        }
        return  numOfRowsInserted;
    }

    public Integer updateLeadStatusOfClient(ClientRequestBean clientRequestBean){
        int numOfRowsInserted = 0;
        if(clientRequestBean!=null && !Utility.isNullOrEmpty(clientRequestBean.getClientId())) {
            String sQuery = "UPDATE GTCLIENT SET IS_LEAD=? WHERE CLIENTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint( clientRequestBean.isLead()?"1":"0", clientRequestBean.getClientId());
            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildClientData.java", "updateLeadStatusOfClient() ");
        }
        return  numOfRowsInserted;
    }

    public Integer deleteClientData(ClientBean clientBean) {
        int numOfRowsDeleted = 0;
        if(clientBean!=null) {
            String sQuery = "DELETE FROM GTCLIENT WHERE CLIENTID =?";
            ArrayList<Object> aParams = DBDAO.createConstraint(clientBean.getClientId() );
            numOfRowsDeleted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "BuildClientData.java", "deleteClientData() ");
        }
        return numOfRowsDeleted;
    }
}
