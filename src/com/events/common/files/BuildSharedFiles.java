package com.events.common.files;

import com.events.bean.clients.ClientRequestBean;
import com.events.bean.clients.ClientResponseBean;
import com.events.bean.common.files.*;
import com.events.bean.common.notify.NotifyBean;
import com.events.clients.AccessClients;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.common.notify.Notification;
import com.events.data.files.BuildSharedFilesData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/29/14
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildSharedFiles {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public SharedFilesResponseBean saveSharedFiles(SharedFilesRequestBean sharedFilesRequestBean) {
        SharedFilesResponseBean sharedFilesResponseBean = new SharedFilesResponseBean();
        if(sharedFilesRequestBean!=null) {

            AccessSharedFiles accessSharedFiles = new AccessSharedFiles();
            SharedFilesGroupBean sharedFilesGroupBean = new SharedFilesGroupBean();
            if( sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId()) ) {
                sharedFilesGroupBean = accessSharedFiles.getSharedFileGroup( sharedFilesRequestBean );
            }
            sharedFilesGroupBean = generateSharedFilesGroupBean(sharedFilesRequestBean, sharedFilesGroupBean );

            BuildSharedFilesData buildSharedFilesData = new BuildSharedFilesData();
            Integer iNumOfFileGroups = 0;
            if( sharedFilesGroupBean!=null && !Utility.isNullOrEmpty(sharedFilesGroupBean.getSharedFilesGroupId()) ) {
                iNumOfFileGroups = buildSharedFilesData.updateSharedFileGroup( sharedFilesGroupBean );
            } else {
                sharedFilesGroupBean.setSharedFilesGroupId( Utility.getNewGuid() );
                sharedFilesRequestBean.setSharedFilesGroupId( sharedFilesGroupBean.getSharedFilesGroupId() );
                iNumOfFileGroups = buildSharedFilesData.insertSharedFileGroup( sharedFilesGroupBean );
                sharedFilesResponseBean.setNewFileGroup( true ); // a new file group was created.
            }

            if(iNumOfFileGroups>0){
                sharedFilesResponseBean.setSharedFileGroupId( sharedFilesGroupBean.getSharedFilesGroupId() );
                sharedFilesResponseBean.setSharedFilesGroupBean( sharedFilesGroupBean );
                if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getUploadId())) {
                    SharedFilesBean sharedFilesBean = generateSharedFilesBean(sharedFilesRequestBean);
                    Integer numOfFiles =  buildSharedFilesData.insertSharedFiles( sharedFilesBean );
                    if(numOfFiles>0) {
                        ArrayList<SharedFilesBean> arrSharedFilesBean = new ArrayList<SharedFilesBean>();
                        arrSharedFilesBean.add( sharedFilesBean );
                        sharedFilesResponseBean.setArrSharedFilesBean( arrSharedFilesBean );
                    }
                }

                // Update the Shared File Viewers only if it is done by Vendor. No access for Clients.
                if(!sharedFilesRequestBean.isLoggedInUserAClient() && !sharedFilesRequestBean.isUploadFileInvoked()) {
                    buildSharedFilesData.deleteSharedFilesViewer(sharedFilesRequestBean);

                    ArrayList<String> arrViewerIds = sharedFilesRequestBean.getArrViewerId();
                    if(arrViewerIds!=null && !arrViewerIds.isEmpty()) {
                        for(String sViewerId : arrViewerIds ) {
                            SharedFilesViewersBean sharedFilesViewersBean = generateSharedFilesViewersBean(sharedFilesRequestBean,sViewerId);
                            buildSharedFilesData.insertSharedFilesViewer( sharedFilesViewersBean );
                        }
                    }
                }


                if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getComment())) {
                    SharedFilesCommentsBean sharedFilesCommentsBean =generateSharedFilesCommentsBean( sharedFilesRequestBean );
                    Integer iNumOfComments = buildSharedFilesData.insertSharedFilesComments( sharedFilesCommentsBean );

                    if(iNumOfComments>0){
                        sharedFilesResponseBean.setSharedFilesCommentsBean(sharedFilesCommentsBean);
                    }

                }

            }
        }
        return sharedFilesResponseBean;
    }

    public boolean deleteSharedFiles( SharedFilesRequestBean sharedFilesRequestBean ){
        boolean isFileDeleted = false;
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFileId())){
            BuildSharedFilesData buildSharedFilesData = new BuildSharedFilesData();
            Integer numOfRowsInserted = buildSharedFilesData.deleteSharedFiles(sharedFilesRequestBean);
            if(numOfRowsInserted>0){
                isFileDeleted = true;
            }
        }
        return isFileDeleted;
    }

    private SharedFilesGroupBean generateSharedFilesGroupBean(SharedFilesRequestBean sharedFilesRequestBean, SharedFilesGroupBean sharedFilesGroupBean ) {
        if(sharedFilesRequestBean!=null && sharedFilesGroupBean!=null){
            sharedFilesGroupBean.setClientId( ParseUtil.checkNull(sharedFilesRequestBean.getClientId()) );
            sharedFilesGroupBean.setVendorId( ParseUtil.checkNull(sharedFilesRequestBean.getVendorId()) );
            if(!Utility.isNullOrEmpty(sharedFilesRequestBean.getFileGroupName()))  {
                sharedFilesGroupBean.setFilesGroupName( ParseUtil.checkNull(sharedFilesRequestBean.getFileGroupName()) );
            }
            sharedFilesGroupBean.setSharedFilesGroupId( ParseUtil.checkNull(sharedFilesRequestBean.getSharedFilesGroupId()) );
            sharedFilesGroupBean.setUserId( ParseUtil.checkNull(sharedFilesRequestBean.getUserId()) );
        }
        return sharedFilesGroupBean;
    }

    private SharedFilesBean generateSharedFilesBean(SharedFilesRequestBean sharedFilesRequestBean ){
        SharedFilesBean sharedFilesBean = new SharedFilesBean();
        if( sharedFilesRequestBean!=null ){
            sharedFilesBean.setSharedFilesId( Utility.getNewGuid() );
            sharedFilesBean.setSharedFilesGroupId( sharedFilesRequestBean.getSharedFilesGroupId() );
            sharedFilesBean.setFileName( sharedFilesRequestBean.getUploadId() );
            sharedFilesBean.setVendorId( sharedFilesRequestBean.getVendorId() );
            sharedFilesBean.setUserId( sharedFilesRequestBean.getUserId() );
        }
        return sharedFilesBean;
    }

    private SharedFilesViewersBean generateSharedFilesViewersBean( SharedFilesRequestBean sharedFilesRequestBean , String sViewerId) {
        SharedFilesViewersBean sharedFilesViewersBean = new SharedFilesViewersBean();
        if( sharedFilesRequestBean!=null ){
            sharedFilesViewersBean.setSharedFilesViewersId( Utility.getNewGuid() );
            sharedFilesViewersBean.setSharedFilesGroupId( sharedFilesRequestBean.getSharedFilesGroupId() );
            sharedFilesViewersBean.setUserId( sharedFilesRequestBean.getUserId() );
            sharedFilesViewersBean.setViewerType( Constants.USER_TYPE.CLIENT );
            sharedFilesViewersBean.setParentId( sViewerId );
        }
        return sharedFilesViewersBean;
    }

    private SharedFilesCommentsBean generateSharedFilesCommentsBean(SharedFilesRequestBean sharedFilesRequestBean){
        SharedFilesCommentsBean sharedFilesCommentsBean = new SharedFilesCommentsBean();
        if( sharedFilesRequestBean!=null ){
            sharedFilesCommentsBean.setSharedFilesCommentsId(Utility.getNewGuid());
            sharedFilesCommentsBean.setSharedFilesGroupId(sharedFilesRequestBean.getSharedFilesGroupId());
            sharedFilesCommentsBean.setFromUserId(sharedFilesRequestBean.getUserId());
            sharedFilesCommentsBean.setComment( sharedFilesRequestBean.getComment() );
            sharedFilesCommentsBean.setFormattedCreateDate( "Just Now");
        }
        return sharedFilesCommentsBean;
    }

    public void createNotifications( SharedFilesRequestBean sharedFilesRequestBean , String sMessage){
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getUserId()) && !Utility.isNullOrEmpty(sharedFilesRequestBean.getVendorId() )
                && !Utility.isNullOrEmpty( sMessage ) ){

            NotifyBean notifyBean = new NotifyBean();
            notifyBean.setFrom(sharedFilesRequestBean.getUserId());

            notifyBean.setMessage( sMessage );
            if(sharedFilesRequestBean.isLoggedInUserAClient()){
                notifyBean.setTo( Constants.NOTIFICATION_RECEPIENTS.ALL_PLANNERS.toString() );

                Notification.createNewNotifyRecord(notifyBean);
            } else {
                if(sharedFilesRequestBean.getArrViewerId()!=null && !sharedFilesRequestBean.getArrViewerId().isEmpty()) {
                    ArrayList<String> arrViewerId = sharedFilesRequestBean.getArrViewerId();
                    for(String sViewerId : arrViewerId ){
                        ClientRequestBean clientRequestBean = new ClientRequestBean();
                        clientRequestBean.setClientId( sViewerId );
                        clientRequestBean.setVendorId( sharedFilesRequestBean.getVendorId() );

                        AccessClients accessClients = new AccessClients();
                        ClientResponseBean clientResponseBean =accessClients.getClientContactInfo( clientRequestBean );
                        if(clientResponseBean!=null && !Utility.isNullOrEmpty(clientResponseBean.getUserId())){
                            notifyBean.setTo( clientResponseBean.getUserId() );

                            Notification.createNewNotifyRecord(notifyBean);
                        }
                    }
                }


            }
        }
    }
}
