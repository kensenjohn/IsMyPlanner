package com.events.users;

import com.events.bean.users.CookieRequestBean;
import com.events.bean.users.CookieUserBean;
import com.events.bean.users.CookieUserResponseBean;
import com.events.common.Utility;
import com.events.data.users.CookieUserData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/8/14
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CookieUser {
    public CookieUserResponseBean getCookieUser(CookieRequestBean cookieRequestBean) {
        CookieUserResponseBean cookieUserResponseBean = new CookieUserResponseBean();
        if(cookieRequestBean!=null && !Utility.isNullOrEmpty(cookieRequestBean.getCookieUserId())){
            CookieUserData cookieUserData = new CookieUserData();
            CookieUserBean cookieUserBean = cookieUserData.getCookieUser(cookieRequestBean);
            if(cookieUserBean!=null){
                cookieUserResponseBean.setCookieUserId( cookieUserBean.getCookieUserId() );
                cookieUserResponseBean.setCookieUserBean( cookieUserBean );
            }
        }
        return cookieUserResponseBean;
    }

    public CookieUserResponseBean saveCookieUser(CookieRequestBean cookieRequestBean) {

        CookieUserResponseBean cookieUserResponseBean = new CookieUserResponseBean();
        if(cookieRequestBean!=null && !Utility.isNullOrEmpty(cookieRequestBean.getUserId())) {
            cookieRequestBean.setCookieUserId( Utility.getNewGuid() );
            CookieUserData cookieUserData = new CookieUserData();
            Integer iNumOfRows = cookieUserData.insertCookieUser(cookieRequestBean);
            if(iNumOfRows>0){
                cookieUserResponseBean.setCookieUserId( cookieRequestBean.getCookieUserId() );
            }
        }
        return cookieUserResponseBean;
    }
}
