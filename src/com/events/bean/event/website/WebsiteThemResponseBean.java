package com.events.bean.event.website;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/18/14
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteThemResponseBean {
    private ArrayList<WebsiteThemeBean> arrWebsiteTheme = new ArrayList<WebsiteThemeBean>();
    private ArrayList<WebsiteFontBean> arrWebsiteFont = new ArrayList<WebsiteFontBean>();
    private ArrayList<WebsiteColorBean> arrWebsiteColors = new ArrayList<WebsiteColorBean>();

    public ArrayList<WebsiteThemeBean> getArrWebsiteTheme() {
        return arrWebsiteTheme;
    }

    public void setArrWebsiteTheme(ArrayList<WebsiteThemeBean> arrWebsiteTheme) {
        this.arrWebsiteTheme = arrWebsiteTheme;
    }

    public ArrayList<WebsiteFontBean> getArrWebsiteFont() {
        return arrWebsiteFont;
    }

    public void setArrWebsiteFont(ArrayList<WebsiteFontBean> arrWebsiteFont) {
        this.arrWebsiteFont = arrWebsiteFont;
    }

    public ArrayList<WebsiteColorBean> getArrWebsiteColors() {
        return arrWebsiteColors;
    }

    public void setArrWebsiteColors(ArrayList<WebsiteColorBean> arrWebsiteColors) {
        this.arrWebsiteColors = arrWebsiteColors;
    }


}
