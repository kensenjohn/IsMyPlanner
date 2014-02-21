package com.events.event.website;

import com.events.bean.event.website.EventWebsitePageBean;
import com.events.data.event.website.BuildEventWebsitePageData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/21/14
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildEventWebsitePage {
    public Integer createEventWebsitePage( ArrayList<EventWebsitePageBean> arrEventWebsitePage) {
        Integer iNumOfRowsInserted = 0;
        if(arrEventWebsitePage!=null && !arrEventWebsitePage.isEmpty()) {
            BuildEventWebsitePageData buildEventWebsitePageData = new BuildEventWebsitePageData();
            iNumOfRowsInserted = buildEventWebsitePageData.insertEventWebsitePageBatch( arrEventWebsitePage );
        }
        return iNumOfRowsInserted;
    }

    public Integer deleteEventWebsitePage( ArrayList<EventWebsitePageBean> arrEventWebsitePage) {
        Integer iNumOfRowsDeleted = 0;
        if(arrEventWebsitePage!=null && !arrEventWebsitePage.isEmpty()) {
            BuildEventWebsitePageData buildEventWebsitePageData = new BuildEventWebsitePageData();
            iNumOfRowsDeleted = buildEventWebsitePageData.deleteEventWebsitePageBatch( arrEventWebsitePage );
        }
        return iNumOfRowsDeleted;
    }
}
