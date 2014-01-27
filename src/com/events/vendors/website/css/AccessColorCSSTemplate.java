package com.events.vendors.website.css;

import com.events.common.Configuration;
import com.events.common.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/25/14
 * Time: 7:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessColorCSSTemplate {

    Configuration colorCssTemplateProp = Configuration.getInstance(Constants.COLOR_CSS_TEMPLATE_PROP);
    public String getColorCssTemplate() {
        return colorCssTemplateProp.get("color_css_template");
    }
}
