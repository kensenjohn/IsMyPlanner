package com.events.bean.vendors.website;

import com.events.common.Constants;
import com.events.common.Utility;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/12/14
 * Time: 6:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorWebsiteURLBean {
    private String protocol = Constants.EMPTY;
    private String subDomain = Constants.EMPTY;
    private String domain = Constants.EMPTY;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl(){
        String sTmpProtocol = Constants.EMPTY;
        String sTmpSubDomain = Constants.EMPTY;
        String sTmpDomain = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(this.protocol)){
            sTmpProtocol = this.protocol + "://";
        } else {
            sTmpProtocol = "//";
        }
        if(Utility.isNullOrEmpty(this.subDomain) && !"localhost".equalsIgnoreCase(this.domain)) {
            sTmpSubDomain = "www";
        }

        if(!Utility.isNullOrEmpty(this.subDomain) ){
            sTmpSubDomain = this.subDomain + ".";
        }

        return  sTmpProtocol + sTmpSubDomain + this.domain;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VendorWebsiteURLBean{");
        sb.append("protocol='").append(protocol).append('\'');
        sb.append(", subDomain='").append(subDomain).append('\'');
        sb.append(", domain='").append(domain).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
