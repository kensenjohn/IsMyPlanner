package com.events.bean.clients;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/17/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientBean {
    private String clientName = Constants.EMPTY;
    private String clientId = Constants.EMPTY;
    private String userBeanId = Constants.EMPTY;
    private String vendorId = Constants.EMPTY;
    private boolean isCorporateClient = false;
    private boolean isLead = false;


    public ClientBean() {}
    public ClientBean(HashMap<String, String> hmClientRes) {
        this.clientId =  ParseUtil.checkNull(hmClientRes.get("CLIENTID"));
        this.clientName =  ParseUtil.checkNull(hmClientRes.get("CLIENTNAME"));
        this.isCorporateClient =  ParseUtil.sTob(hmClientRes.get("IS_CORPORATE_CLIENT"));
        this.userBeanId =  ParseUtil.checkNull(hmClientRes.get("FK_USERID"));
        this.vendorId =  ParseUtil.checkNull(hmClientRes.get("FK_VENDORID"));
        this.isLead =  ParseUtil.sTob(hmClientRes.get("IS_LEAD"));
    }

    public boolean isLead() {
        return isLead;
    }

    public void setLead(boolean lead) {
        isLead = lead;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserBeanId() {
        return userBeanId;
    }

    public void setUserBeanId(String userBeanId) {
        this.userBeanId = userBeanId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isCorporateClient() {
        return isCorporateClient;
    }

    public void setCorporateClient(boolean corporateClient) {
        isCorporateClient = corporateClient;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_name", this.clientName );
            jsonObject.put("client_id", this.clientId );
            jsonObject.put("is_coporate_client", this.isCorporateClient );
            jsonObject.put("client_user_id", this.userBeanId);
            jsonObject.put("vendor_id", this.vendorId);
            jsonObject.put("is_lead", this.isLead);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
