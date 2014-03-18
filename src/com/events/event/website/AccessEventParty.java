package com.events.event.website;

import com.events.bean.event.website.EventPartyBean;
import com.events.bean.event.website.EventPartyRequest;
import com.events.bean.event.website.SocialMediaBean;
import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.common.UploadFile;
import com.events.common.Utility;
import com.events.data.event.website.AccessEventPartyData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/26/14
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessEventParty {
    public EventPartyBean getEventParty(EventPartyRequest eventPartyRequest){
        EventPartyBean eventPartyBean = new EventPartyBean();
        if(eventPartyRequest!=null){
            AccessEventPartyData accessEventPartyData = new AccessEventPartyData();
            eventPartyBean = accessEventPartyData.getEventParty( eventPartyRequest );
        }
        return eventPartyBean;
    }

    public ArrayList<EventPartyBean> getEventPartyByWebsite(EventPartyRequest eventPartyRequest){
        ArrayList<EventPartyBean> arrEventPartyBean =  new ArrayList<EventPartyBean>();
        if(eventPartyRequest!=null){
            AccessEventPartyData accessEventPartyData = new AccessEventPartyData();
            arrEventPartyBean = accessEventPartyData.getEventPartyByWebsite(eventPartyRequest);
        }
        return arrEventPartyBean;
    }

    public ArrayList<EventPartyBean> getEventPartyListByTypeAndWebsite(EventPartyRequest eventPartyRequest){
        ArrayList<EventPartyBean> arrEventPartyBean =  new ArrayList<EventPartyBean>();
        if(eventPartyRequest!=null){
            AccessEventPartyData accessEventPartyData = new AccessEventPartyData();
            arrEventPartyBean = accessEventPartyData.getEventPartyListByTypeAndWebsite(eventPartyRequest);
        }
        return arrEventPartyBean;
    }

    public EventPartyBean getEventPartyByTypeAndWebsite(EventPartyRequest eventPartyRequest){
        EventPartyBean eventPartyBean =  new EventPartyBean();
        if(eventPartyRequest!=null){
            AccessEventPartyData accessEventPartyData = new AccessEventPartyData();
            eventPartyBean = accessEventPartyData.getEventPartyByTypeAndWebsite(eventPartyRequest);
        }
        return eventPartyBean;
    }

    public JSONObject getEventPartyJson(ArrayList<EventPartyBean> arrEventPartyBean , ArrayList<SocialMediaBean> arrSocialMediaBean,
                                        HashMap<String,UploadBean> hmUploadBean ){
        JSONObject jsonObject = new JSONObject();
        if(arrEventPartyBean!=null && !arrEventPartyBean.isEmpty()){


            Integer iNumOfEventParty = 0;
            for(EventPartyBean eventPartyBean : arrEventPartyBean ){
                JSONObject jsonEventParty = eventPartyBean.toJson();

                Integer iNumOfSocialMedia = 0;
                if(arrSocialMediaBean!=null && !arrSocialMediaBean.isEmpty()) {


                    JSONObject jsonSocialMedia = new JSONObject();
                    for(SocialMediaBean socialMediaBean : arrSocialMediaBean ){
                        if(eventPartyBean.getEventPartyId().equalsIgnoreCase(socialMediaBean.getEventPartyId())  ) {
                            jsonSocialMedia.put(iNumOfSocialMedia.toString() , socialMediaBean.toJson() );
                            iNumOfSocialMedia++;
                        }
                    }
                    if(iNumOfSocialMedia>0){
                        jsonEventParty.put("social_media_bean", jsonSocialMedia );
                    }

                }
                jsonEventParty.put("num_of_social_media" ,iNumOfSocialMedia);
                if(hmUploadBean!=null && !hmUploadBean.isEmpty()){
                    jsonEventParty.put("image_uploaded" ,hmUploadBean.get(eventPartyBean.getEventPartyId()).toJson());
                }


                jsonObject.put(iNumOfEventParty.toString(), jsonEventParty) ;
                iNumOfEventParty++;
            }
            jsonObject.put("num_of_event_party" ,iNumOfEventParty);
        }
        return jsonObject;
    }

    public HashMap<String,UploadBean>  getEventPartyImage(ArrayList<EventPartyBean> arrEventPartyBean){
        HashMap<String,UploadBean> hmUploadBean = new HashMap<String, UploadBean>();
        if(arrEventPartyBean!=null && !arrEventPartyBean.isEmpty() ) {
            for(EventPartyBean eventPartyBean : arrEventPartyBean ){
                UploadRequestBean uploadRequestBean = new UploadRequestBean();
                uploadRequestBean.setUploadId( eventPartyBean.getUploadId() );

                UploadFile uploadFile = new UploadFile();
                UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo( uploadRequestBean ) ;
                if(uploadResponseBean!=null && !Utility.isNullOrEmpty(uploadResponseBean.getUploadId()))  {
                    hmUploadBean.put( eventPartyBean.getEventPartyId() ,uploadResponseBean.getUploadBean() ) ;
                }
            }
        }
        return hmUploadBean;
    }

    public UploadBean  getEventPartyImage(EventPartyBean eventPartyBean){
        UploadBean uploadBean = new UploadBean();
        if(eventPartyBean!=null && !Utility.isNullOrEmpty(eventPartyBean.getUploadId()) ) {
            UploadRequestBean uploadRequestBean = new UploadRequestBean();
            uploadRequestBean.setUploadId( eventPartyBean.getUploadId() );

            UploadFile uploadFile = new UploadFile();
            UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo( uploadRequestBean ) ;
            if(uploadResponseBean!=null && !Utility.isNullOrEmpty(uploadResponseBean.getUploadId()))  {
                uploadBean = uploadResponseBean.getUploadBean();
            }
        }
        return uploadBean;
    }
}
