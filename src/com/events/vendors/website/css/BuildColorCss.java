package com.events.vendors.website.css;

import com.events.bean.vendors.website.ColorCSSBean;
import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/25/14
 * Time: 7:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildColorCss {
    public String getColorCss(ColorCSSBean colorCSSBean) {
        String sColorCssTemplate = Constants.EMPTY;
        if(colorCSSBean!=null) {
            AccessColorCSSTemplate accessColorCSSTemplate = new AccessColorCSSTemplate();
            sColorCssTemplate = accessColorCSSTemplate.getColorCssTemplate();

            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.BKG.getText(),colorCSSBean.getBackground() );
            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.HIGHLIGHTED.getText(),colorCSSBean.getHighlightedTextOrLink() );
            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.TEXT.getText(),colorCSSBean.getText() );
            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.NAVBAR_BREADCRUMB_TAB_BKG.getText(),colorCSSBean.getNavBreadTabBackground() );

            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.BORDER.getText(),colorCSSBean.getBorder() );
            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.FILLED_BUTTON.getText(),colorCSSBean.getFilledButton() );
            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.FILLED_BUTTON_TEXT.getText(),colorCSSBean.getFilledButtonText() );
            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.PLAIN_BUTTON.getText(),colorCSSBean.getPlainButton() );
            sColorCssTemplate = sColorCssTemplate.replaceAll(Constants.COLOR_TEMPLATE.PLAIN_BUTTON_TEXT.getText(),colorCSSBean.getPlainButtonText() );
        }
        return sColorCssTemplate;
    }
}
