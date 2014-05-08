package com.events.common.files;

import com.events.bean.common.files.*;
import com.events.bean.upload.UploadBean;
import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.UploadFile;
import com.events.common.Utility;
import com.events.data.files.AccessSharedFilesData;
import com.events.users.AccessUsers;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/29/14
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessSharedFiles {

    public SharedFilesResponseBean getVendorsSharedFileGroup(SharedFilesRequestBean sharedFilesRequestBean){
        SharedFilesResponseBean sharedFilesResponseBean = new SharedFilesResponseBean();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getVendorId())) {
            AccessSharedFilesData accessSharedFilesData = new AccessSharedFilesData();
            ArrayList<SharedFilesGroupBean>  arrSharedFilesGroupBean = accessSharedFilesData.getVendorSharedFileGroup( sharedFilesRequestBean );
            sharedFilesResponseBean.setArrSharedFilesGroupBean( arrSharedFilesGroupBean );
        }
        return sharedFilesResponseBean;
    }
    public SharedFilesResponseBean getClientsSharedFileGroup(SharedFilesRequestBean sharedFilesRequestBean){
        SharedFilesResponseBean sharedFilesResponseBean = new SharedFilesResponseBean();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getClientId())) {
            AccessSharedFilesData accessSharedFilesData = new AccessSharedFilesData();
            ArrayList<SharedFilesGroupBean>  arrSharedFilesGroupBean = accessSharedFilesData.getClientSharedFileGroup(sharedFilesRequestBean);
            sharedFilesResponseBean.setArrSharedFilesGroupBean(arrSharedFilesGroupBean);
        }
        return sharedFilesResponseBean;
    }

    public SharedFilesGroupBean getSharedFileGroup(SharedFilesRequestBean sharedFilesRequestBean){
        SharedFilesGroupBean sharedFilesGroupBean = new SharedFilesGroupBean();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId())) {
            AccessSharedFilesData accessSharedFilesData = new AccessSharedFilesData();
            sharedFilesGroupBean = accessSharedFilesData.getSharedFileGroup(sharedFilesRequestBean);
        }
        return sharedFilesGroupBean;
    }

    public ArrayList<SharedFilesBean> getSharedFiles(SharedFilesRequestBean sharedFilesRequestBean){
        ArrayList<SharedFilesBean> arrSharedFilesBean = new ArrayList<SharedFilesBean>();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId())) {
            AccessSharedFilesData accessSharedFilesData = new AccessSharedFilesData();
            arrSharedFilesBean = accessSharedFilesData.getSharedFiles( sharedFilesRequestBean );
        }
        return arrSharedFilesBean;
    }
    public ArrayList<SharedFilesBean> getSharedFilesDetails(SharedFilesRequestBean sharedFilesRequestBean){
        ArrayList<SharedFilesBean> arrSharedFilesBean = new ArrayList<SharedFilesBean>();
        ArrayList<SharedFilesBean>  arrTmpSharedFilesBean = getSharedFiles(sharedFilesRequestBean);
        if(arrTmpSharedFilesBean!=null && !arrTmpSharedFilesBean.isEmpty()) {
            for(SharedFilesBean sharedFilesBean : arrTmpSharedFilesBean ) {
                UserRequestBean userRequestBean = new UserRequestBean();
                userRequestBean.setUserId( sharedFilesBean.getUserId() );

                AccessUsers accessUsers = new AccessUsers();
                UserBean userBean = accessUsers.getUserById( userRequestBean );
                UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId(userRequestBean );

                String sGivenName = Constants.EMPTY;
                if(userInfoBean!=null && !Utility.isNullOrEmpty( userInfoBean.getFirstName() )) {
                    sGivenName = ParseUtil.checkNull(  userInfoBean.getFirstName() ) + " " + ParseUtil.checkNull(  userInfoBean.getLastName() );
                }

                if( userBean!=null && Utility.isNullOrEmpty( sGivenName ) ) {
                    if(Constants.USER_TYPE.VENDOR.getType().equalsIgnoreCase( userBean.getUserType().getType() )) {
                        sGivenName = "(Planner) "+ userInfoBean.getEmail();
                    } else if(Constants.USER_TYPE.CLIENT.getType().equalsIgnoreCase( userBean.getUserType().getType() )) {
                        sGivenName = "(Client) " + userInfoBean.getEmail();
                    } else {
                        sGivenName = "User";
                    }

                }

                sharedFilesBean.setUploadedBy( sGivenName );
                arrSharedFilesBean.add( sharedFilesBean );

            }
        }
        return arrSharedFilesBean;
    }

    public SharedFilesBean getSharedFilesFromUploadId(SharedFilesRequestBean sharedFilesRequestBean){
        SharedFilesBean sharedFilesBean = new SharedFilesBean();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getUploadId() )) {
            AccessSharedFilesData accessSharedFilesData = new AccessSharedFilesData();
            sharedFilesBean = accessSharedFilesData.getSharedFilesFromUploadId( sharedFilesRequestBean );
        }
        return sharedFilesBean;
    }


    public ArrayList<SharedFilesViewersBean> getSharedFilesViewers(SharedFilesRequestBean sharedFilesRequestBean){
        ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean = new ArrayList<SharedFilesViewersBean>();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId())) {
            AccessSharedFilesData accessSharedFilesData = new AccessSharedFilesData();
            arrSharedFilesViewersBean = accessSharedFilesData.getSharedFilesViewer(sharedFilesRequestBean);
        }
        return arrSharedFilesViewersBean;
    }

    public SharedFilesResponseBean loadAllFilesFromGroup( SharedFilesRequestBean sharedFilesRequestBean ){
        SharedFilesResponseBean sharedFilesResponseBean = new SharedFilesResponseBean();
        if(sharedFilesRequestBean!=null && !Utility.isNullOrEmpty(sharedFilesRequestBean.getSharedFilesGroupId())) {
            SharedFilesGroupBean sharedFilesGroupBean = getSharedFileGroup(sharedFilesRequestBean);
            ArrayList<SharedFilesBean> arrSharedFilesBean = getSharedFilesDetails(sharedFilesRequestBean);
            HashMap<String,UploadBean> hmUploadBean = getUploadedFiles(arrSharedFilesBean);
            ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean = getSharedFilesViewers(sharedFilesRequestBean);

            sharedFilesResponseBean.setSharedFilesGroupBean(sharedFilesGroupBean);
            sharedFilesResponseBean.setArrSharedFilesBean( arrSharedFilesBean );
            sharedFilesResponseBean.setHmUploadBean( hmUploadBean );
            sharedFilesResponseBean.setArrSharedFilesViewersBean( arrSharedFilesViewersBean );

            if(sharedFilesGroupBean!=null && !Utility.isNullOrEmpty(sharedFilesGroupBean.getSharedFilesGroupId())) {
                sharedFilesResponseBean.setSharedFileGroupId(sharedFilesGroupBean.getSharedFilesGroupId());
            }



        }
        return sharedFilesResponseBean;
    }

    public HashMap<String,UploadBean> getUploadedFiles(ArrayList<SharedFilesBean> arrSharedFilesBean){
        HashMap<String,UploadBean> hmUploadBean = new HashMap<String, UploadBean>();
        if(arrSharedFilesBean!=null && !arrSharedFilesBean.isEmpty() ) {
            for(SharedFilesBean sharedFilesBean : arrSharedFilesBean ) {
                UploadBean uploadBean = getUploadedFiles(sharedFilesBean);
                hmUploadBean.put( sharedFilesBean.getSharedFilesId(), uploadBean);

            }
        }
        return hmUploadBean;
    }

    public UploadBean getUploadedFiles( SharedFilesBean sharedFilesBean ) {
        UploadBean uploadBean = new UploadBean();
        if(sharedFilesBean!=null){
            UploadRequestBean uploadRequestBean = new UploadRequestBean();
            uploadRequestBean.setUploadId( ParseUtil.checkNull(sharedFilesBean.getFileName()));

            UploadFile uploadFile = new UploadFile();
            UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo( uploadRequestBean );
            uploadBean = uploadResponseBean.getUploadBean();
        }
        return uploadBean;
    }

    public JSONObject getJsonSharedFiles(ArrayList<SharedFilesBean> arrSharedFilesBean, HashMap<String,UploadBean> hmUploadBean ){
        JSONObject jsonFilesBean = new JSONObject();
        if(arrSharedFilesBean!=null && !arrSharedFilesBean.isEmpty() && hmUploadBean!=null && !hmUploadBean.isEmpty() ) {
            Integer iFileCount = 0;
            for(SharedFilesBean sharedFilesBean : arrSharedFilesBean ){
                jsonFilesBean.put(ParseUtil.iToS( iFileCount ) + "_shared_files", sharedFilesBean.toJson() );

                UploadBean uploadBean = hmUploadBean.get( sharedFilesBean.getSharedFilesId() );
                if(uploadBean!=null && !Utility.isNullOrEmpty(uploadBean.getUploadId())){
                    jsonFilesBean.put(ParseUtil.iToS( iFileCount ) + "_uploaded_files", uploadBean.toJson() );
                }

                iFileCount++;
            }

            jsonFilesBean.put("num_of_files" , iFileCount );
        }
        return jsonFilesBean;
    }

    public JSONObject getJsonSharedFilesViewers(ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean ){
        JSONObject jsonFilesViewersBean = new JSONObject();
        if(arrSharedFilesViewersBean!=null && !arrSharedFilesViewersBean.isEmpty() ) {
            Integer iFileViewerCount = 0;
            for(SharedFilesViewersBean sharedFilesViewersBean : arrSharedFilesViewersBean ){
                jsonFilesViewersBean.put(ParseUtil.iToS( iFileViewerCount )  , sharedFilesViewersBean.toJson() );

                iFileViewerCount++;
            }

            jsonFilesViewersBean.put("num_of_files_viewers" , iFileViewerCount );
        }
        return jsonFilesViewersBean;
    }

    public JSONObject getJsonSharedFilesGroups(ArrayList<SharedFilesGroupBean> arrSharedFilesGroupBean ){
        JSONObject jsonFilesGroupBean = new JSONObject();
        if(arrSharedFilesGroupBean!=null && !arrSharedFilesGroupBean.isEmpty() ) {
            Integer iFileGroupCount = 0;
            for(SharedFilesGroupBean sharedFilesGroupBean : arrSharedFilesGroupBean ){
                jsonFilesGroupBean.put(ParseUtil.iToS( iFileGroupCount )  , sharedFilesGroupBean.toJson() );

                iFileGroupCount++;
            }

            jsonFilesGroupBean.put("num_of_files_groups" , iFileGroupCount );
        }
        return jsonFilesGroupBean;
    }
}
