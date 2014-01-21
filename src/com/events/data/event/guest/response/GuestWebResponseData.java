package com.events.data.event.guest.response;

import com.events.bean.event.guest.response.GuestWebResponseBean;
import com.events.bean.event.guest.response.WebRespRequest;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/20/14
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuestWebResponseData {

    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public Integer insertGuestWebResponse(GuestWebResponseBean guestWebResponseBean) {
        Integer numOfRowsInserted = 0;
        if(guestWebResponseBean!=null && !Utility.isNullOrEmpty(guestWebResponseBean.getGuestWebResponseId())
                && guestWebResponseBean.getWebResponseType() !=null
                && !Utility.isNullOrEmpty(guestWebResponseBean.getLinkId())) {
            String sQuery = "INSERT into GTGUESTWEBRESPONSE(GUESTWEBRESPONSEID,WEB_RESPONSE_TYPE,LINKID,      LINK_DOMAIN,FK_GUESTGROUPID,FK_EVENTID,    " +
                    " CREATEDATE,HUMANCREATEDATE,RESPONSE_STATUS,     FK_USERID ) VALUES " +
                    " (?,?,?,    ?,?,?,    ?,?,?,    ?)";
            ArrayList<Object> aParams = DBDAO.createConstraint(guestWebResponseBean.getGuestWebResponseId(),guestWebResponseBean.getWebResponseType().toString(), guestWebResponseBean.getLinkId(),
                    guestWebResponseBean.getLinkDomain(),guestWebResponseBean.getGuestGroupId(),guestWebResponseBean.getEventId(),
                    DateSupport.getEpochMillis(), DateSupport.getUTCDateTime(), guestWebResponseBean.getResponseStatus().toString(),
                    guestWebResponseBean.getUserId());

            numOfRowsInserted = DBDAO.putRowsQuery(sQuery, aParams, EVENTADMIN_DB, "GuestWebResponseData.java", "insertGuestWebResponse() ");
        }
        return numOfRowsInserted;
    }

    public GuestWebResponseBean getGuestWebResponse (WebRespRequest webRequestBean){
        GuestWebResponseBean guestWebResponseBean = new GuestWebResponseBean();
        if(webRequestBean!=null) {
            String sQuery = "SELECT * FROM GTGUESTWEBRESPONSE GTWR WHERE GTWR.LINKID = ? AND  GTWR.WEB_RESPONSE_TYPE = ?";

            ArrayList<Object> aParams = DBDAO.createConstraint(webRequestBean.getLinkId(), webRequestBean.getGuestWebResponseType().name());

            ArrayList<HashMap<String, String>> arrWebResponse = DBDAO .getDBData(EVENTADMIN_DB, sQuery, aParams, true, "GuestWebResponseData.java", "getGuestWebResponse()");

            if(arrWebResponse!=null && !arrWebResponse.isEmpty()) {
                for(HashMap<String, String> hmWebResponse : arrWebResponse ){
                    guestWebResponseBean = new GuestWebResponseBean(hmWebResponse);
                }
            }
        }
        return guestWebResponseBean;
    }

}
