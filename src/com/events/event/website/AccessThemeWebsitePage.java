package com.events.event.website;

import com.events.bean.event.website.AllWebsiteThemeRequestBean;
import com.events.bean.event.website.ThemePageBean;
import com.events.common.Utility;
import com.events.data.event.website.AccessThemePageData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/21/14
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessThemeWebsitePage {
    public ArrayList<ThemePageBean> getThemePage(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        ArrayList<ThemePageBean> arrThemePageBean = new ArrayList<ThemePageBean>();
        if(allWebsiteThemeRequestBean!=null && !Utility.isNullOrEmpty(allWebsiteThemeRequestBean.getWebsiteThemeId())  ) {
            AccessThemePageData accessThemePageData = new AccessThemePageData();
            arrThemePageBean = accessThemePageData.getThemePage(allWebsiteThemeRequestBean);
        }
        return arrThemePageBean;
    }
}
