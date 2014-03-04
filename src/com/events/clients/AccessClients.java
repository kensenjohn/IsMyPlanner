package com.events.clients;

import com.events.bean.clients.ClientBean;
import com.events.bean.clients.ClientRequestBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.clients.AccessClientData;
import com.events.users.AccessUsers;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/17/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessClients {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public HashMap<Integer,ClientBean> getAllClientsSummary(ClientRequestBean clientRequestBean) {
        HashMap<Integer,ClientBean> hmClientBean =new HashMap<Integer,ClientBean>();
        if(clientRequestBean!=null && !"".equalsIgnoreCase(clientRequestBean.getVendorId())) {
            AccessClientData accessClientData = new AccessClientData();
            hmClientBean = accessClientData.getAllClientsSummary(clientRequestBean) ;
        }
        return  hmClientBean;
    }
    public ClientBean getAllClientsDetails(ClientRequestBean clientRequestBean) {
        ClientBean clientBean =new ClientBean();
        return  clientBean;
    }

    public ClientBean getClientDataByVendorAndClient(ClientRequestBean clientRequestBean) {
        ClientBean clientBean = new ClientBean();
        if(clientRequestBean!=null && !Utility.isNullOrEmpty(clientRequestBean.getClientId())  && !"".equalsIgnoreCase(clientRequestBean.getVendorId())  ) {
            AccessClientData accessClientData = new AccessClientData();
            clientBean = accessClientData.getClientByVendor(clientRequestBean) ;
        }
        return clientBean;
    }

    public ClientBean getClient(ClientRequestBean clientRequestBean) {
        ClientBean clientBean = new ClientBean();
        if(clientRequestBean!=null && !Utility.isNullOrEmpty(clientRequestBean.getClientId()) ) {
            AccessClientData accessClientData = new AccessClientData();
            clientBean = accessClientData.getClient(clientRequestBean) ;
        }
        return clientBean;
    }


    public ClientResponseBean getClientContactInfo(ClientRequestBean clientRequestBean)  {
        ClientResponseBean clientResponseBean =new ClientResponseBean();
        if(clientRequestBean!=null && !Utility.isNullOrEmpty(clientRequestBean.getClientId()) && !Utility.isNullOrEmpty(clientRequestBean.getVendorId())  ) {

            ClientBean clientBean = getClientDataByVendorAndClient(clientRequestBean);
            if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getClientId()) ) {

                UserRequestBean userRequestBean = new UserRequestBean();
                userRequestBean.setParentId(clientBean.getClientId());

                AccessUsers accessUsers = new AccessUsers();
                UserBean userBean = accessUsers.getUserByParentId(userRequestBean);

                if(userBean!=null &&  !Utility.isNullOrEmpty(userBean.getUserInfoId()))  {
                    userRequestBean.setUserInfoId( userBean.getUserInfoId() );
                    UserInfoBean userInfoBean = accessUsers.getUserInfoFromInfoId(userRequestBean);

                    clientResponseBean.setClientId(clientBean.getClientId());
                    clientResponseBean.setClientBean(clientBean);

                    clientResponseBean.setUserId( userBean.getUserId() );
                    clientResponseBean.setUserBean(userBean);

                    clientResponseBean.setUserInfoId( userInfoBean.getUserInfoId() );
                    clientResponseBean.setUserInfoBean(userInfoBean);
                } else {
                    appLogging.info("Could not find a valid User Bean data for client - "  + clientRequestBean  + clientBean );
                }
            } else {
                appLogging.info("Could not find a valid Client data for client request : " + clientRequestBean );
            }
        } else {
            appLogging.error("Invalid request  : " + ParseUtil.checkNullObject(clientRequestBean));
        }
        return  clientResponseBean;
    }

    public JSONObject convertAllClientsSummaryToJson(HashMap<Integer,ClientBean> hmClientBean) {
        JSONObject jsonObject = new JSONObject();
        if(hmClientBean!=null && !hmClientBean.isEmpty()) {
            try {
                for(Map.Entry<Integer,ClientBean> mapClientBean : hmClientBean.entrySet() ) {
                    jsonObject.put(ParseUtil.iToI(mapClientBean.getKey()).toString(), mapClientBean.getValue().toJson());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
