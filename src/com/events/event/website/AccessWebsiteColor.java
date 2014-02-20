package com.events.event.website;

import com.events.bean.event.website.AllWebsiteThemeRequestBean;
import com.events.bean.event.website.WebsiteColorBean;
import com.events.bean.event.website.WebsiteThemeBean;
import com.events.common.Utility;
import com.events.data.event.website.AccessWebsiteColorData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessWebsiteColor {
    public WebsiteColorBean getDefaultWebsiteColor(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        WebsiteColorBean websiteColorBean = new WebsiteColorBean();
        if(allWebsiteThemeRequestBean!=null && !Utility.isNullOrEmpty(allWebsiteThemeRequestBean.getWebsiteThemeId())) {
            WebsiteThemeBean websiteThemeBean = new WebsiteThemeBean();
            websiteThemeBean.setWebsiteThemeId( allWebsiteThemeRequestBean.getWebsiteThemeId() );

            AccessWebsiteColorData accessWebsiteColorData = new AccessWebsiteColorData();
            websiteColorBean = accessWebsiteColorData.getDefaultWebsiteColor(websiteThemeBean);
        }
        return websiteColorBean;
    }

    public ArrayList<WebsiteColorBean> getWebsiteThemeColors(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        ArrayList<WebsiteColorBean> arrWebsiteColors =  new ArrayList<WebsiteColorBean>();
        if(allWebsiteThemeRequestBean!=null && !Utility.isNullOrEmpty(allWebsiteThemeRequestBean.getWebsiteThemeId())) {
            WebsiteThemeBean websiteThemeBean = new WebsiteThemeBean();
            websiteThemeBean.setWebsiteThemeId( allWebsiteThemeRequestBean.getWebsiteThemeId() );

            ArrayList<WebsiteThemeBean> arrWebsiteTheme = new ArrayList<WebsiteThemeBean>();
            arrWebsiteTheme.add(websiteThemeBean);

            AccessWebsiteColorData accessWebsiteColorData = new AccessWebsiteColorData();
            arrWebsiteColors = accessWebsiteColorData.getWebsiteColor( arrWebsiteTheme );
        }
        return arrWebsiteColors;
    }

    public JSONObject getWebsiteThemeColorsJson(ArrayList<WebsiteColorBean>  arrWebsiteColors )  {
        JSONObject jsonColors = new JSONObject();
        if(arrWebsiteColors!=null && !arrWebsiteColors.isEmpty()) {
            Integer iNumOfColors = 0;
            for(WebsiteColorBean websiteColorBean : arrWebsiteColors ){
                jsonColors.put( iNumOfColors.toString() ,websiteColorBean.toJson() );
                iNumOfColors++;
            }

            jsonColors.put( "num_of_colors" , iNumOfColors );
        }
        return jsonColors;
    }
}
