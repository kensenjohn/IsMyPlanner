package com.events.event.website;

import com.events.bean.event.website.AllWebsiteThemeRequestBean;
import com.events.bean.event.website.WebsiteFontBean;
import com.events.bean.event.website.WebsiteThemeBean;
import com.events.common.Utility;
import com.events.data.event.website.AccessWebsiteFontData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessWebsiteFont {
    public WebsiteFontBean getDefaultWebsiteFont(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        WebsiteFontBean websiteFontBean = new WebsiteFontBean();
        if(allWebsiteThemeRequestBean!=null && !Utility.isNullOrEmpty(allWebsiteThemeRequestBean.getWebsiteThemeId())) {
            WebsiteThemeBean websiteThemeBean = new WebsiteThemeBean();
            websiteThemeBean.setWebsiteThemeId( allWebsiteThemeRequestBean.getWebsiteThemeId() );

            AccessWebsiteFontData accessWebsiteFontData = new AccessWebsiteFontData();
            websiteFontBean = accessWebsiteFontData.getDefaultWebsiteFont( websiteThemeBean );
        }
        return websiteFontBean;
    }

    public ArrayList<WebsiteFontBean> getWebsiteThemeFonts(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean) {
        ArrayList<WebsiteFontBean> arrWebsiteFonts =  new ArrayList<WebsiteFontBean>();
        if(allWebsiteThemeRequestBean!=null && !Utility.isNullOrEmpty(allWebsiteThemeRequestBean.getWebsiteThemeId())) {
            WebsiteThemeBean websiteThemeBean = new WebsiteThemeBean();
            websiteThemeBean.setWebsiteThemeId( allWebsiteThemeRequestBean.getWebsiteThemeId() );

            ArrayList<WebsiteThemeBean> arrWebsiteTheme = new ArrayList<WebsiteThemeBean>();
            arrWebsiteTheme.add(websiteThemeBean);

            AccessWebsiteFontData accessWebsiteFontData = new AccessWebsiteFontData();
            arrWebsiteFonts = accessWebsiteFontData.getWebsiteFont(arrWebsiteTheme);
        }
        return arrWebsiteFonts;
    }

    public JSONObject getWebsiteThemeFontsJson(ArrayList<WebsiteFontBean>  arrWebsiteFonts )  {
        JSONObject jsonFonts = new JSONObject();
        if(arrWebsiteFonts!=null && !arrWebsiteFonts.isEmpty()) {
            Integer iNumOfFonts = 0;
            for(WebsiteFontBean websiteFontBean : arrWebsiteFonts ){
                jsonFonts.put( iNumOfFonts.toString() ,websiteFontBean.toJson() );
                iNumOfFonts++;
            }

            jsonFonts.put( "num_of_fonts" , iNumOfFonts );
        }
        return jsonFonts;
    }
}
