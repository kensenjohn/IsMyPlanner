package com.events.common;

import com.events.bean.common.ParentSiteEnabledBean;
import com.events.bean.users.UserRequestBean;
import com.events.data.ParentSiteEnabledData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/2/14
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentSiteEnabled {
    public ParentSiteEnabledBean getParentSiteEnabledStatusForUser(UserRequestBean userRequestBean){
        ParentSiteEnabledBean parentSiteEnabledBean = new ParentSiteEnabledBean();
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId())) {
            ParentSiteEnabledData parentSiteEnabledData = new ParentSiteEnabledData();
            parentSiteEnabledBean = parentSiteEnabledData.getParentSiteEnabledStatusByUser( userRequestBean );
        }
        return parentSiteEnabledBean;
    }

    public boolean toggleParentSiteEnableStatus(UserRequestBean userRequestBean){
        if(userRequestBean!=null && !Utility.isNullOrEmpty(userRequestBean.getUserId() )) {

            ParentSiteEnabledBean newParentSiteEnabledBean = new ParentSiteEnabledBean();
            boolean currentWebsiteStatus = false;
            ParentSiteEnabledBean parentSiteEnabledBean = getParentSiteEnabledStatusForUser(userRequestBean);

            ParentSiteEnabledData parentSiteEnabledData = new ParentSiteEnabledData();
            if(parentSiteEnabledBean!=null && !Utility.isNullOrEmpty(parentSiteEnabledBean.getParentSiteEnabledId())) {
                newParentSiteEnabledBean.setParentSiteEnabledId( parentSiteEnabledBean.getParentSiteEnabledId() );
                newParentSiteEnabledBean.setUserId( parentSiteEnabledBean.getUserId() );
                currentWebsiteStatus = parentSiteEnabledBean.isAllowed();
                currentWebsiteStatus = !currentWebsiteStatus;
                newParentSiteEnabledBean.setAllowed( currentWebsiteStatus);

                parentSiteEnabledData.updateParentSiteEnabledStatus(newParentSiteEnabledBean);
            } else {
                newParentSiteEnabledBean.setUserId( userRequestBean.getUserId() );
                newParentSiteEnabledBean.setParentSiteEnabledId( Utility.getNewGuid() );
                currentWebsiteStatus = true;
                newParentSiteEnabledBean.setAllowed( currentWebsiteStatus);

                parentSiteEnabledData.insertParentSiteEnabledStatus(newParentSiteEnabledBean) ;
            }
        }
        return false;
    }
}
