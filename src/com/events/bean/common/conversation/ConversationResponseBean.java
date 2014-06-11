package com.events.bean.common.conversation;

import com.events.bean.upload.UploadBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/2/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConversationResponseBean {
    private ConversationBean conversationBean = new ConversationBean();
    private ArrayList<UserConversationBean> arrUserConversationBean = new ArrayList<UserConversationBean>();
    private ConversationMessageBean conversationMessageBean = new ConversationMessageBean();
    private ConversationMessageUserBean conversationMessageUserBean = new ConversationMessageUserBean();

    private ArrayList<ConversationBean> arrConversationBean = new ArrayList<ConversationBean>();
    private HashMap<String, ArrayList<UserConversationBean> > hmUserConversationBean = new HashMap<String, ArrayList<UserConversationBean>>();
    private HashMap<String, ArrayList<String> > hmUserNames = new HashMap<String, ArrayList<String>>();
    private HashMap<Long, ConversationMessageBean > hmConversationMessageBean = new HashMap<Long, ConversationMessageBean>();
    private HashMap<String, ConversationMessageUserBean >  hmConversationMessageUserBean = new HashMap<String, ConversationMessageUserBean >();
    private ArrayList<UploadBean> arrUploadBean = new ArrayList<UploadBean>();
    private HashMap<String, ArrayList<UploadBean> > hmConversationMessageAttachment = new HashMap<String, ArrayList<UploadBean>>();


    public HashMap<String, ArrayList<UploadBean>> getHmConversationMessageAttachment() {
        return hmConversationMessageAttachment;
    }

    public void setHmConversationMessageAttachment(HashMap<String, ArrayList<UploadBean>> hmConversationMessageAttachment) {
        this.hmConversationMessageAttachment = hmConversationMessageAttachment;
    }

    public ArrayList<UploadBean> getArrUploadBean() {
        return arrUploadBean;
    }

    public void setArrUploadBean(ArrayList<UploadBean> arrUploadBean) {
        this.arrUploadBean = arrUploadBean;
    }

    public HashMap<Long, ConversationMessageBean> getHmConversationMessageBean() {
        return hmConversationMessageBean;
    }

    public void setHmConversationMessageBean(HashMap<Long, ConversationMessageBean> hmConversationMessageBean) {
        this.hmConversationMessageBean = hmConversationMessageBean;
    }

    public HashMap<String, ConversationMessageUserBean> getHmConversationMessageUserBean() {
        return hmConversationMessageUserBean;
    }

    public void setHmConversationMessageUserBean(HashMap<String, ConversationMessageUserBean> hmConversationMessageUserBean) {
        this.hmConversationMessageUserBean = hmConversationMessageUserBean;
    }

    public HashMap<String, ArrayList<String>> getHmUserNames() {
        return hmUserNames;
    }

    public void setHmUserNames(HashMap<String, ArrayList<String>> hmUserNames) {
        this.hmUserNames = hmUserNames;
    }

    public ArrayList<ConversationBean> getArrConversationBean() {
        return arrConversationBean;
    }

    public void setArrConversationBean(ArrayList<ConversationBean> arrConversationBean) {
        this.arrConversationBean = arrConversationBean;
    }

    public HashMap<String, ArrayList<UserConversationBean>> getHmUserConversationBean() {
        return hmUserConversationBean;
    }

    public void setHmUserConversationBean(HashMap<String, ArrayList<UserConversationBean>> hmUserConversationBean) {
        this.hmUserConversationBean = hmUserConversationBean;
    }

    public ConversationBean getConversationBean() {
        return conversationBean;
    }

    public void setConversationBean(ConversationBean conversationBean) {
        this.conversationBean = conversationBean;
    }

    public ArrayList<UserConversationBean> getArrUserConversationBean() {
        return arrUserConversationBean;
    }

    public void setArrUserConversationBean(ArrayList<UserConversationBean> arrUserConversationBean) {
        this.arrUserConversationBean = arrUserConversationBean;
    }

    public ConversationMessageBean getConversationMessageBean() {
        return conversationMessageBean;
    }

    public void setConversationMessageBean(ConversationMessageBean conversationMessageBean) {
        this.conversationMessageBean = conversationMessageBean;
    }

    public ConversationMessageUserBean getConversationMessageUserBean() {
        return conversationMessageUserBean;
    }

    public void setConversationMessageUserBean(ConversationMessageUserBean conversationMessageUserBean) {
        this.conversationMessageUserBean = conversationMessageUserBean;
    }
}
