package com.events.event.website;

import com.events.bean.event.website.*;
import com.events.common.Utility;
import com.events.data.event.website.AccessWebsiteColorData;
import com.events.data.event.website.AccessWebsiteFontData;
import com.events.data.event.website.AccessWebsiteThemesData;
import com.events.data.event.website.AccessWebsiteThemesData;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/18/14
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessWebsiteThemes {
    public WebsiteThemResponseBean getAllWebsiteThemeDetails(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean){
        WebsiteThemResponseBean websiteThemResponseBean = new WebsiteThemResponseBean();
        if( allWebsiteThemeRequestBean!=null && allWebsiteThemeRequestBean.getArrVendorId()!=null && !allWebsiteThemeRequestBean.getArrVendorId().isEmpty() ){
            AccessWebsiteThemesData accessWebsiteThemesData = new AccessWebsiteThemesData();
            ArrayList<WebsiteThemeBean> arrWebsiteTheme = accessWebsiteThemesData.getWebsiteThemesByVendor( allWebsiteThemeRequestBean );

            ArrayList<WebsiteFontBean> arrWebsiteFont = new ArrayList<WebsiteFontBean>();
            ArrayList<WebsiteColorBean> arrWebsiteColors = new ArrayList<WebsiteColorBean>();
            if(arrWebsiteTheme!=null && !arrWebsiteTheme.isEmpty()) {

                AccessWebsiteColorData accessWebsiteColorData = new AccessWebsiteColorData();
                arrWebsiteColors = accessWebsiteColorData.getWebsiteColor( arrWebsiteTheme );

                AccessWebsiteFontData accessWebsiteFontData = new AccessWebsiteFontData();
                arrWebsiteFont = accessWebsiteFontData.getWebsiteFont(arrWebsiteTheme);


                websiteThemResponseBean.setArrWebsiteTheme( arrWebsiteTheme );
                websiteThemResponseBean.setArrWebsiteColors( arrWebsiteColors );
                websiteThemResponseBean.setArrWebsiteFont( arrWebsiteFont );
            }
        }
        return websiteThemResponseBean;
    }
    public WebsiteThemResponseBean getEventWebsiteThemeDetail(AllWebsiteThemeRequestBean allWebsiteThemeRequestBean){
        WebsiteThemResponseBean websiteThemResponseBean = new WebsiteThemResponseBean();
        if(allWebsiteThemeRequestBean!=null && !Utility.isNullOrEmpty(allWebsiteThemeRequestBean.getWebsiteThemeId())) {
            AccessWebsiteThemesData accessWebsiteThemesData = new AccessWebsiteThemesData();
            WebsiteThemeBean websiteThemeBean = accessWebsiteThemesData.getWebsiteTheme(allWebsiteThemeRequestBean) ;

            if(websiteThemeBean!=null && !Utility.isNullOrEmpty(websiteThemeBean.getWebsiteThemeId())) {
                ArrayList<WebsiteThemeBean> arrWebsiteTheme = new ArrayList<WebsiteThemeBean>();
                arrWebsiteTheme.add(websiteThemeBean);


                AccessWebsiteColor accessWebsiteColor = new AccessWebsiteColor();
                ArrayList<WebsiteColorBean> arrWebsiteColors =  accessWebsiteColor.getWebsiteThemeColors( allWebsiteThemeRequestBean );

                AccessWebsiteFont accessWebsiteFont = new AccessWebsiteFont();
                ArrayList<WebsiteFontBean> arrWebsiteFonts =  accessWebsiteFont.getWebsiteThemeFonts(  allWebsiteThemeRequestBean);

                websiteThemResponseBean.setArrWebsiteTheme( arrWebsiteTheme );
                websiteThemResponseBean.setArrWebsiteColors( arrWebsiteColors );
                websiteThemResponseBean.setArrWebsiteFont( arrWebsiteFonts );
            }
        }
        return websiteThemResponseBean;
    }
    public JSONObject getAllWebsiteThemeJson(WebsiteThemResponseBean websiteThemResponseBean){
        JSONObject jsonObject = new JSONObject();
        if(websiteThemResponseBean!=null){
            ArrayList<WebsiteThemeBean> arrWebsiteTheme = websiteThemResponseBean.getArrWebsiteTheme();
            ArrayList<WebsiteFontBean> arrWebsiteFont = websiteThemResponseBean.getArrWebsiteFont();
            ArrayList<WebsiteColorBean> arrWebsiteColor = websiteThemResponseBean.getArrWebsiteColors();
            if(arrWebsiteTheme!=null && !arrWebsiteTheme.isEmpty()){
                Integer iNumOfThemes = 0;
                for(WebsiteThemeBean websiteThemeBean : arrWebsiteTheme )   {
                    String sWebsiteThemeId = websiteThemeBean.getWebsiteThemeId();
                    JSONObject jsonWebsiteTheme = websiteThemeBean.toJson();

                    if(arrWebsiteFont!=null && !arrWebsiteFont.isEmpty()) {
                        Integer iNumOfFonts = 0;
                        JSONObject jsonWebsiteFont = new JSONObject();
                        for(WebsiteFontBean websiteFontBean : arrWebsiteFont )   {
                            if(websiteFontBean.getWebsiteThemeId().equalsIgnoreCase( sWebsiteThemeId )) {
                                jsonWebsiteFont.put(iNumOfFonts.toString() , websiteFontBean.toJson()) ;
                                iNumOfFonts++;
                            }
                        }
                        jsonWebsiteTheme.put("font", jsonWebsiteFont);
                        jsonWebsiteTheme.put("num_of_fonts", iNumOfFonts);
                    }

                    if(arrWebsiteColor!=null && !arrWebsiteColor.isEmpty()) {
                        Integer iNumOfColors = 0;
                        JSONObject jsonWebsiteColor = new JSONObject();
                        for(WebsiteColorBean websiteColorBean : arrWebsiteColor )   {
                            if(websiteColorBean.getWebsiteThemeId().equalsIgnoreCase( sWebsiteThemeId )) {
                                jsonWebsiteColor.put(iNumOfColors.toString() , websiteColorBean.toJson()) ;
                                iNumOfColors++;
                            }
                        }
                        jsonWebsiteTheme.put("colors", jsonWebsiteColor);
                        jsonWebsiteTheme.put("num_of_colors", iNumOfColors);
                    }

                    jsonObject.put(iNumOfThemes.toString(), jsonWebsiteTheme );

                    iNumOfThemes++;
                }
                jsonObject.put("num_of_themes", iNumOfThemes);
            }
        }
        return jsonObject;
    }
}
