package com.events.event.website;

import com.events.bean.event.website.*;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.AccessEventWebsiteData;
import com.events.data.event.website.BuildEventWebsiteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventWebsite {

    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public EventWebsiteBean saveEventWebsite(EventWebsiteRequestBean eventWebsiteRequestBean) {
        EventWebsiteBean eventWebsiteBean = new EventWebsiteBean();
        if(eventWebsiteRequestBean!=null && !Utility.isNullOrEmpty(eventWebsiteRequestBean.getEventId()) ){

            AccessEventWebsite accessEventWebsite = new AccessEventWebsite();
            eventWebsiteBean = accessEventWebsite.getEventWebsite( eventWebsiteRequestBean ); // get the current event website bean
            if(eventWebsiteBean==null || (eventWebsiteBean!=null && Utility.isNullOrEmpty(eventWebsiteBean.getEventWebsiteId())
                    && !Utility.isNullOrEmpty(eventWebsiteRequestBean.getWebsiteThemId()) ) ){
                // this assumes that there is none so create one from default values


                AllWebsiteThemeRequestBean allWebsiteThemeRequestBean = new AllWebsiteThemeRequestBean();
                allWebsiteThemeRequestBean.setWebsiteThemeId( eventWebsiteRequestBean.getWebsiteThemId() );

                AccessWebsiteColor accessWebsiteColor = new AccessWebsiteColor();
                WebsiteColorBean websiteColorBean = accessWebsiteColor.getDefaultWebsiteColor( allWebsiteThemeRequestBean );

                AccessWebsiteFont accessWebsiteFont = new AccessWebsiteFont();
                WebsiteFontBean websiteFontBean = accessWebsiteFont.getDefaultWebsiteFont(allWebsiteThemeRequestBean);

                eventWebsiteBean.setWebsiteThemeId( eventWebsiteRequestBean.getWebsiteThemId() );
                eventWebsiteBean.setWebsiteColorId( websiteColorBean.getWebsiteColorId() );
                eventWebsiteBean.setWebsiteFontId( websiteFontBean.getWebsiteFontId() );
            }
            eventWebsiteBean = generateEventWebsiteBean( eventWebsiteBean , eventWebsiteRequestBean ); // merging current bean with new values

            if(Utility.isNullOrEmpty(eventWebsiteRequestBean.getEventWebsiteId())) {
                eventWebsiteBean.setEventWebsiteId( Utility.getNewGuid() );
                eventWebsiteBean = createEventWebsite(eventWebsiteBean);
                if(eventWebsiteBean!=null && !Utility.isNullOrEmpty(eventWebsiteBean.getEventWebsiteId())) {
                    AccessEventWebsitePage accessEventWebsitePage = new AccessEventWebsitePage();
                    ArrayList<EventWebsitePageBean> arrEventWebsitePageBean = accessEventWebsitePage.getEventWebsitePage( eventWebsiteBean ) ;
                    if(arrEventWebsitePageBean == null || (arrEventWebsitePageBean!=null && arrEventWebsitePageBean.isEmpty()) ) {
                        ArrayList<ThemePageBean> arrThemePageBean = getThemePages( eventWebsiteBean );
                        ArrayList<EventWebsitePageBean> arrEventWebsitePage = createDefaultEventWebsitePages(arrThemePageBean, eventWebsiteBean );
                    }

                }
            } else {
                eventWebsiteBean = updateEventWebsite( eventWebsiteBean );
            }
        }
        return eventWebsiteBean;
    }

    public EventWebsiteBean createEventWebsite(EventWebsiteBean eventWebsiteBean) {
        Integer numOfRowsInserted = 0 ;
        if(eventWebsiteBean!=null){
            BuildEventWebsiteData buildEventWebsiteData = new BuildEventWebsiteData();
            numOfRowsInserted = buildEventWebsiteData.insertEventWebsite( eventWebsiteBean );
        }
        if(numOfRowsInserted<=0){
            eventWebsiteBean = new EventWebsiteBean();
        }
        return eventWebsiteBean;
    }


    public EventWebsiteBean updateEventWebsite(EventWebsiteBean eventWebsiteBean) {
        Integer numOfRowsInserted = 0 ;
        if(eventWebsiteBean!=null){
            BuildEventWebsiteData buildEventWebsiteData = new BuildEventWebsiteData();
            numOfRowsInserted = buildEventWebsiteData.updateEventWebsite(eventWebsiteBean);
        }
        if(numOfRowsInserted<=0){
            eventWebsiteBean = new EventWebsiteBean();
        }
        return eventWebsiteBean;
    }

    public EventWebsiteBean generateEventWebsiteBean(EventWebsiteBean sourceEventWebsiteBean , EventWebsiteRequestBean eventWebsiteRequestBean) {
        EventWebsiteBean eventWebsiteBean = new EventWebsiteBean();
        if(sourceEventWebsiteBean!=null  && eventWebsiteRequestBean!=null) {
            eventWebsiteBean = sourceEventWebsiteBean;
            if( !Utility.isNullOrEmpty( eventWebsiteRequestBean.getEventId() )) {
                eventWebsiteBean.setEventId(  ParseUtil.checkNull(eventWebsiteRequestBean.getEventId()) );
            }

            if( !Utility.isNullOrEmpty( eventWebsiteRequestBean.getEventWebsiteId() )) {
                eventWebsiteBean.setEventWebsiteId(ParseUtil.checkNull(eventWebsiteRequestBean.getEventWebsiteId()));
            }

            if( !Utility.isNullOrEmpty( eventWebsiteRequestBean.getWebsiteThemId())) {
                eventWebsiteBean.setWebsiteThemeId(ParseUtil.checkNull(eventWebsiteRequestBean.getWebsiteThemId()));
            }

            if( !Utility.isNullOrEmpty( eventWebsiteRequestBean.getWebsiteFontId())) {
                eventWebsiteBean.setWebsiteFontId(  ParseUtil.checkNull(eventWebsiteRequestBean.getWebsiteFontId()) );
            }

            if( !Utility.isNullOrEmpty( eventWebsiteRequestBean.getWebsiteColorId())) {
                eventWebsiteBean.setWebsiteColorId(  ParseUtil.checkNull(eventWebsiteRequestBean.getWebsiteColorId()) );
            }

            if( !Utility.isNullOrEmpty( eventWebsiteRequestBean.getUserId())) {
                eventWebsiteBean.setUserId(  ParseUtil.checkNull(eventWebsiteRequestBean.getUserId()) );
            }
        }
        return eventWebsiteBean;
    }
    private  ArrayList<ThemePageBean> getThemePages(EventWebsiteBean eventWebsiteBean) {
        ArrayList<ThemePageBean> arrThemePageBean = new ArrayList<ThemePageBean>();
        if(eventWebsiteBean!=null && !Utility.isNullOrEmpty(eventWebsiteBean.getWebsiteThemeId())){
            AllWebsiteThemeRequestBean allWebsiteThemeRequestBean = new AllWebsiteThemeRequestBean();
            allWebsiteThemeRequestBean.setWebsiteThemeId(  eventWebsiteBean.getWebsiteThemeId() );

            AccessThemeWebsitePage accessThemeWebsitePage = new AccessThemeWebsitePage();
            arrThemePageBean = accessThemeWebsitePage.getThemePage( allWebsiteThemeRequestBean );
        }
        return arrThemePageBean;
    }

    private ArrayList<EventWebsitePageBean> createDefaultEventWebsitePages(ArrayList<ThemePageBean> arrThemePageBean , EventWebsiteBean eventWebsiteBean){
        ArrayList<EventWebsitePageBean> arrEventWebsitePage = new ArrayList<EventWebsitePageBean>();
        if(eventWebsiteBean!=null){

            if(arrThemePageBean!=null && !arrThemePageBean.isEmpty()) {

                ThemePageFeature themePageFeature = new ThemePageFeature();
                ArrayList<EventWebsitePageFeatureBean> arrEventWebsitePageFeatureBean = new ArrayList<EventWebsitePageFeatureBean>();
                for(ThemePageBean themePageBean : arrThemePageBean){

                    String sNewPageId =  Utility.getNewGuid();
                    EventWebsitePageBean eventWebsitePageBean = new EventWebsitePageBean();
                    eventWebsitePageBean.setEventWebsiteId( eventWebsiteBean.getEventWebsiteId() );
                    eventWebsitePageBean.setWebsiteThemeId( eventWebsiteBean.getWebsiteThemeId() );
                    eventWebsitePageBean.setEventWebsitePageId( sNewPageId );
                    eventWebsitePageBean.setShow( themePageBean.isShow() );
                    eventWebsitePageBean.setType( themePageBean.getType() );

                    arrEventWebsitePage.add( eventWebsitePageBean );


                    ArrayList<ThemePageFeatureBean> arrMultipleFeatureBean =  themePageFeature.getMultipleFeatures(themePageBean.getThemePageId() );

                    if(arrMultipleFeatureBean!=null && !arrMultipleFeatureBean.isEmpty()) {
                        for(ThemePageFeatureBean themePageFeatureBean : arrMultipleFeatureBean ){
                            EventWebsitePageFeatureBean eventWebsitePageFeatureBean = new EventWebsitePageFeatureBean();
                            eventWebsitePageFeatureBean.setUserId( eventWebsiteBean.getUserId() );
                            eventWebsitePageFeatureBean.setEventWebsitePageId( sNewPageId );
                            eventWebsitePageFeatureBean.setEventWebsitePageFeatureId( Utility.getNewGuid() );
                            eventWebsitePageFeatureBean.setFeatureDescription( themePageFeatureBean.getFeatureDescription() );
                            eventWebsitePageFeatureBean.setFeatureName( themePageFeatureBean.getFeatureName() );
                            eventWebsitePageFeatureBean.setFeatureType( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.valueOf( themePageFeatureBean.getFeatureName()  ));
                            eventWebsitePageFeatureBean.setValue( themePageFeatureBean.getValue() );


                            arrEventWebsitePageFeatureBean.add( eventWebsitePageFeatureBean );

                        }
                    }



                }

                if(arrEventWebsitePageFeatureBean!=null && !arrEventWebsitePageFeatureBean.isEmpty() ){
                    EventWebsitePageFeature eventWebsitePageFeature = new EventWebsitePageFeature();
                    for(EventWebsitePageFeatureBean eventWebsitePageFeatureBean : arrEventWebsitePageFeatureBean){
                        eventWebsitePageFeature.setFeatureValue(eventWebsitePageFeatureBean);
                    }

                }

                if(arrEventWebsitePage!=null && !arrEventWebsitePage.isEmpty() ) {
                    BuildEventWebsitePage buildEventWebsitePage = new BuildEventWebsitePage();
                    Integer iNumOfPagesInserted = buildEventWebsitePage.createEventWebsitePage( arrEventWebsitePage );
                    if(iNumOfPagesInserted!=arrThemePageBean.size()) {
                        appLogging.error("There was error inserting Theme pages for Event Website : " + eventWebsiteBean );
                        buildEventWebsitePage.deleteEventWebsitePage(arrEventWebsitePage);
                        arrEventWebsitePage = new ArrayList<EventWebsitePageBean>();
                    }
                }
            }
        }
        return arrEventWebsitePage;
    }
}
