package com.events.bean.common.files;

import com.events.bean.upload.UploadBean;
import com.events.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/30/14
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SharedFilesResponseBean {
    private SharedFilesGroupBean sharedFilesGroupBean = new SharedFilesGroupBean();
    private ArrayList<SharedFilesBean> arrSharedFilesBean = new ArrayList<SharedFilesBean>();
    private HashMap<String,UploadBean> hmUploadBean = new HashMap<String, UploadBean>();
    private String sharedFileGroupId = Constants.EMPTY;
    private SharedFilesCommentsBean sharedFilesCommentsBean = new SharedFilesCommentsBean();
    private ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean = new ArrayList<SharedFilesViewersBean>();
    private ArrayList<SharedFilesGroupBean>  arrSharedFilesGroupBean = new ArrayList<SharedFilesGroupBean>();


    public ArrayList<SharedFilesGroupBean> getArrSharedFilesGroupBean() {
        return arrSharedFilesGroupBean;
    }

    public void setArrSharedFilesGroupBean(ArrayList<SharedFilesGroupBean> arrSharedFilesGroupBean) {
        this.arrSharedFilesGroupBean = arrSharedFilesGroupBean;
    }

    public ArrayList<SharedFilesViewersBean> getArrSharedFilesViewersBean() {
        return arrSharedFilesViewersBean;
    }

    public void setArrSharedFilesViewersBean(ArrayList<SharedFilesViewersBean> arrSharedFilesViewersBean) {
        this.arrSharedFilesViewersBean = arrSharedFilesViewersBean;
    }

    public SharedFilesCommentsBean getSharedFilesCommentsBean() {
        return sharedFilesCommentsBean;
    }

    public void setSharedFilesCommentsBean(SharedFilesCommentsBean sharedFilesCommentsBean) {
        this.sharedFilesCommentsBean = sharedFilesCommentsBean;
    }

    public String getSharedFileGroupId() {
        return sharedFileGroupId;
    }

    public void setSharedFileGroupId(String sharedFileGroupId) {
        this.sharedFileGroupId = sharedFileGroupId;
    }

    public HashMap<String, UploadBean> getHmUploadBean() {
        return hmUploadBean;
    }

    public void setHmUploadBean(HashMap<String, UploadBean> hmUploadBean) {
        this.hmUploadBean = hmUploadBean;
    }

    public SharedFilesGroupBean getSharedFilesGroupBean() {
        return sharedFilesGroupBean;
    }

    public void setSharedFilesGroupBean(SharedFilesGroupBean sharedFilesGroupBean) {
        this.sharedFilesGroupBean = sharedFilesGroupBean;
    }

    public ArrayList<SharedFilesBean> getArrSharedFilesBean() {
        return arrSharedFilesBean;
    }

    public void setArrSharedFilesBean(ArrayList<SharedFilesBean> arrSharedFilesBean) {
        this.arrSharedFilesBean = arrSharedFilesBean;
    }
}
