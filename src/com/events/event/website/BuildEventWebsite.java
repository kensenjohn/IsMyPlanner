package com.events.event.website;

import com.events.bean.event.website.*;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.event.website.AccessEventWebsiteData;
import com.events.data.event.website.BuildEventWebsiteData;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/19/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventWebsite {
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
}
