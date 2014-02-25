package com.events.proc.upload;

import com.events.bean.common.FeatureBean;
import com.events.bean.event.EventRequestBean;
import com.events.bean.event.website.EventWebsiteBean;
import com.events.bean.event.website.EventWebsitePageBean;
import com.events.bean.event.website.EventWebsitePageFeatureBean;
import com.events.bean.event.website.EventWebsiteRequestBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.bean.users.UserBean;
import com.events.common.*;
import com.events.common.exception.ExceptionHandler;
import com.events.common.feature.FeatureType;
import com.events.event.AccessEvent;
import com.events.event.website.AccessEventWebsite;
import com.events.event.website.AccessEventWebsitePage;
import com.events.event.website.BuildEventWebsite;
import com.events.event.website.EventWebsitePageFeature;
import com.events.json.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/24/14
 * Time: 8:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcUploadEventImage extends HttpServlet {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    /**
     * @see HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespObjectProc responseObject = new RespObjectProc();
        JSONObject jsonResponseObj = new JSONObject();
        ArrayList<Text> arrOkText = new ArrayList<Text>();
        ArrayList<Text> arrErrorText = new ArrayList<Text>();
        RespConstants.Status responseStatus = RespConstants.Status.ERROR;

        if (!ServletFileUpload.isMultipartContent(request)) {
            appLogging.error("Request is not multipart, please 'multipart/form-data' enctype for your form.");
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        String imageHost = Utility.getImageUploadHost();

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

        String imageUploadLocation = applicationConfig.get(Constants.IMAGE_LOCATION);


        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) && !Utility.isNullOrEmpty(imageUploadLocation) && Folder.isFolderExists( imageUploadLocation) ) {

            String sFolderPath = imageUploadLocation;
            String sEventId = Constants.EMPTY;
            String sPageType = Constants.EMPTY;
            String sFeatureType = Constants.EMPTY;
            String sEventImageUploadFolder = Constants.EMPTY;
            try {
                List<FileItem> items = uploadHandler.parseRequest(request);
                if(items!=null){


                    ArrayList<FileItem> arrfileToUpload = new ArrayList<FileItem>();
                    for (FileItem item : items) {
                        if (item.isFormField()) {
                            if ("event_id".equals(item.getFieldName()) ) {
                                sEventId = item.getString();

                                EventRequestBean eventRequestBean = new EventRequestBean();
                                eventRequestBean.setEventId( sEventId );

                                AccessEvent accessEvent = new AccessEvent();
                                FeatureBean featureBean = accessEvent.getFeatureValue( eventRequestBean, FeatureType.image_location);
                                if(featureBean!=null ) {
                                    sEventImageUploadFolder =  ParseUtil.checkNull(featureBean.getValue());
                                    sFolderPath =  imageUploadLocation + ParseUtil.checkNull(featureBean.getValue());

                                    if( !Folder.isFolderExists( sFolderPath ) ){
                                        Folder folder = new Folder();
                                        if(!folder.createFolderForUser( sFolderPath )) {
                                            sFolderPath = Constants.EMPTY;
                                        }
                                    }
                                }

                            }  else if ("page_type".equals(item.getFieldName()) ) {
                                sPageType = item.getString();
                            }  else if ("feature_type".equals(item.getFieldName()) ) {
                                sFeatureType = item.getString();
                            }
                        } else if (!item.isFormField()) {
                            arrfileToUpload.add( item );
                        }
                    }

                    if(arrfileToUpload!=null && !arrfileToUpload.isEmpty() && !Utility.isNullOrEmpty(sFolderPath)) {
                        for(FileItem fileToUpload : arrfileToUpload ) {
                            String sRandomFilename = UploadFile.geenerateRandomeFileName( fileToUpload.getName() );


                            File file = new  File(sFolderPath,sRandomFilename );
                            fileToUpload.write(file);

                            EventWebsiteRequestBean eventWebsiteRequestBean = new EventWebsiteRequestBean();
                            eventWebsiteRequestBean.setEventId( sEventId );

                            AccessEventWebsite accessEventWebsite = new AccessEventWebsite();
                            EventWebsiteBean eventWebsiteBean = accessEventWebsite.getEventWebsite( eventWebsiteRequestBean );

                            EventWebsitePageBean eventWebsitePageBeanReq = new EventWebsitePageBean();
                            eventWebsitePageBeanReq.setEventWebsiteId(eventWebsiteBean.getEventWebsiteId());
                            eventWebsitePageBeanReq.setWebsiteThemeId( eventWebsiteBean.getWebsiteThemeId() );
                            eventWebsitePageBeanReq.setType( sPageType );


                            AccessEventWebsitePage accessEventWebsitePage = new AccessEventWebsitePage();
                            EventWebsitePageBean eventWebsitePageBean = accessEventWebsitePage.getEventWebsitePageByType(eventWebsitePageBeanReq);

                            EventWebsitePageFeatureBean requestEWPFBean = new EventWebsitePageFeatureBean();
                            requestEWPFBean.setEventWebsitePageId( eventWebsitePageBean.getEventWebsitePageId() );
                            requestEWPFBean.setFeatureType( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.valueOf(  sFeatureType ) );

                            EventWebsitePageFeature eventWebsitePageFeature = new EventWebsitePageFeature();
                            EventWebsitePageFeatureBean eventEWPFFromDB = eventWebsitePageFeature.getFeature( requestEWPFBean );

                            if(eventEWPFFromDB==null || (eventEWPFFromDB!=null && Utility.isNullOrEmpty(eventEWPFFromDB.getEventWebsitePageFeatureId())) ) {

                                eventEWPFFromDB.setUserId( eventWebsiteBean.getUserId() );
                                eventEWPFFromDB.setEventWebsitePageId( eventWebsitePageBean.getEventWebsitePageId() );
                                eventEWPFFromDB.setEventWebsitePageFeatureId( Utility.getNewGuid() );

                                eventEWPFFromDB.setFeatureDescription( "-" );
                                eventEWPFFromDB.setFeatureName( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.valueOf(  sFeatureType ).toString() );
                                eventEWPFFromDB.setFeatureType( Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.valueOf(  sFeatureType ) );
                            }

                            eventEWPFFromDB.setValue( sRandomFilename );

                            Integer iNumOfImageCreated = eventWebsitePageFeature.setFeatureValue( eventEWPFFromDB );


                            UploadRequestBean uploadRequestBean = new UploadRequestBean();
                            uploadRequestBean.setFilename( sRandomFilename );
                            uploadRequestBean.setPath( sFolderPath );

                            UploadFile uploadFile = new UploadFile();
                            UploadResponseBean uploadResponseBean = uploadFile.saveUploadFileInfo(uploadRequestBean);

                            if( iNumOfImageCreated> 0 ){
                                Text okText = new OkText("The image was successfully uploaded","status_mssg") ;
                                arrOkText.add(okText);
                                responseStatus = RespConstants.Status.OK;

                                jsonResponseObj.put("name", fileToUpload.getName());
                                jsonResponseObj.put("size", fileToUpload.getSize());



                                uploadRequestBean.setFolderName( sEventImageUploadFolder );
                                uploadRequestBean.setImageHost( imageHost );
                                uploadRequestBean.setImageSize( fileToUpload.getSize() );
                                uploadRequestBean.setJsonResponseObj(jsonResponseObj );
                                JSONObject jsono = UploadFile.generateSuccessUploadJson(uploadRequestBean);
                                json.put(jsono);

                            }  else {
                                appLogging.info("There was error uploading file" + uploadRequestBean );
                                Text errorText = new ErrorText("Oops!! We were unable to process your request at this time. Please try again later.(uploadEventImage - 002)","err_mssg") ;
                                arrErrorText.add(errorText);

                                responseStatus = RespConstants.Status.ERROR;
                                json.put(UploadFile.generateErrorUploadJson());
                            }
                        }
                    }
                } else {
                    json.put(UploadFile.generateErrorUploadJson());
                    appLogging.info(" item list is null : ");
                }

            } catch (FileUploadException e) {
                json.put(UploadFile.generateErrorUploadJson());
                appLogging.error("FileUploadException : " + ExceptionHandler.getStackTrace(e));
            } catch (Exception e) {
                json.put(UploadFile.generateErrorUploadJson());
                appLogging.error("Exception : " + ExceptionHandler.getStackTrace(e));
            }finally {
                appLogging.debug("After Upload is complete : " + json.toString());
                writer.write(json.toString());
                writer.close();
            }
        }
    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpg")) {
                mimetype = "image/jpg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpeg")) {
                mimetype = "image/jpeg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("gif")) {
                mimetype = "image/gif";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }


    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
}
