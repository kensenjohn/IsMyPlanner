package com.events.bean.users;

import com.events.bean.clients.ClientBean;
import com.events.bean.vendors.VendorBean;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/17/14
 * Time: 6:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParentTypeBean {
    private ClientBean clientBean = new ClientBean();
    private VendorBean vendorBean = new VendorBean();
    private boolean isUserAClient = false;
    private boolean isUserAVendor = false;

    public ClientBean getClientBean() {
        return clientBean;
    }

    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }

    public VendorBean getVendorBean() {
        return vendorBean;
    }

    public void setVendorBean(VendorBean vendorBean) {
        this.vendorBean = vendorBean;
    }

    public boolean isUserAClient() {
        return isUserAClient;
    }

    public void setUserAClient(boolean userAClient) {
        isUserAClient = userAClient;
    }

    public boolean isUserAVendor() {
        return isUserAVendor;
    }

    public void setUserAVendor(boolean userAVendor) {
        isUserAVendor = userAVendor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ParentTypeBean{");
        sb.append("clientBean=").append(clientBean);
        sb.append(", vendorBean=").append(vendorBean);
        sb.append(", isUserAClient=").append(isUserAClient);
        sb.append(", isUserAVendor=").append(isUserAVendor);
        sb.append('}');
        return sb.toString();
    }
}
