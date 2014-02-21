package com.events.event.website;

import com.events.bean.event.website.ThemePageFeatureBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.ThemePageFeatureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/21/14
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThemePageFeature {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public ThemePageFeatureBean getFeature(ThemePageFeatureBean themePageFeatureBean) {

        ThemePageFeatureBean themePageFeatureBeanFromDB = new ThemePageFeatureBean();
        if(themePageFeatureBean!=null &&  !Utility.isNullOrEmpty(themePageFeatureBean.getFeatureType().toString()) ) {
            ThemePageFeatureData themePageFeatureData = new ThemePageFeatureData();
            themePageFeatureBeanFromDB = themePageFeatureData.getEventWebsitePageFeature(themePageFeatureBean);
        }
        return themePageFeatureBeanFromDB;
    }

    public ArrayList<ThemePageFeatureBean> getMultipleFeatures(ArrayList<ThemePageFeatureBean> arrThemePageFeatureBean,  String sThemePageId) {
        ArrayList<ThemePageFeatureBean> arrMultipleFeatureBean = new ArrayList<ThemePageFeatureBean>();
        if(arrThemePageFeatureBean!=null ) {
            ThemePageFeatureData themePageFeatureData = new ThemePageFeatureData();
            arrMultipleFeatureBean = themePageFeatureData.getMultipleFeatures(arrThemePageFeatureBean , sThemePageId );
        }
        return arrMultipleFeatureBean;
    }

    public Integer setFeatureValue(ThemePageFeatureBean themePageFeatureBean) {
        Integer iNumOfRows = 0;
        if(themePageFeatureBean!=null && !"".equalsIgnoreCase(themePageFeatureBean.getValue())
                && !"".equalsIgnoreCase(ParseUtil.checkNull(themePageFeatureBean.getFeatureType().toString()))) {

            ThemePageFeatureBean currentThemePageFeatureBean = getFeature(themePageFeatureBean);
            ThemePageFeatureData themePageFeatureData = new ThemePageFeatureData();

            if(currentThemePageFeatureBean!=null && !"".equalsIgnoreCase(currentThemePageFeatureBean.getThemePageFeatureId())) {
                themePageFeatureBean.setThemePageFeatureId(currentThemePageFeatureBean.getThemePageFeatureId());
                iNumOfRows = themePageFeatureData.updateFeature(themePageFeatureBean);
            } else {
                themePageFeatureBean.setThemePageFeatureId(Utility.getNewGuid());
                iNumOfRows = themePageFeatureData.insertFeature(themePageFeatureBean);
            }

        }
        return iNumOfRows;
    }

    public Integer deleteFeatureValue(ThemePageFeatureBean themePageFeatureBean) {
        Integer iNumOfRows = 0;
        if(themePageFeatureBean!=null && !Utility.isNullOrEmpty(themePageFeatureBean.getThemePageId())
                && themePageFeatureBean.getFeatureType()!=null ) {
            ThemePageFeatureData eventVendorFeatureData = new ThemePageFeatureData();
            iNumOfRows = eventVendorFeatureData.deleteFeature( themePageFeatureBean );
        }
        return iNumOfRows;
    }

    public static ThemePageFeatureBean generateThemePageFeatureBean(Constants.EVENT_WEBSITE_PAGE_FEATURETYPE eventWebsitePageFeaturetype) {
        ThemePageFeatureBean themePageFeatureBean = new ThemePageFeatureBean();
        themePageFeatureBean.setFeatureType(eventWebsitePageFeaturetype);
        return themePageFeatureBean;
    }
}
