package com.events.bean.common.conversation;

import com.events.common.Constants;
import com.events.common.ParseUtil;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/2/14
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConversationRequestBean {
    private String conversationId = Constants.EMPTY;
    private String conversationName = Constants.EMPTY;
    private String conversationBody = Constants.EMPTY;
    private String currentUserId = Constants.EMPTY;
    private boolean isHiddenConversation = false;
    private boolean isDeletedConversation = false;
    private ArrayList<String> arrConversationUserId = new ArrayList<String>();
    private String timeZone = Constants.EMPTY;
    private  ArrayList<String> arrUploadId = new ArrayList<String>();
    private boolean canManageEveryConversation = false;
    private String vendorId = Constants.EMPTY;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public boolean isCanManageEveryConversation() {
        return canManageEveryConversation;
    }

    public void setCanManageEveryConversation(boolean canManageEveryConversation) {
        this.canManageEveryConversation = canManageEveryConversation;
    }

    public ArrayList<String> getArrUploadId() {
        return arrUploadId;
    }

    public void setArrUploadId(ArrayList<String> arrUploadId) {
        this.arrUploadId = arrUploadId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isDeletedConversation() {
        return isDeletedConversation;
    }

    public void setDeletedConversation(boolean deletedConversation) {
        isDeletedConversation = deletedConversation;
    }

    public boolean isHiddenConversation() {
        return isHiddenConversation;
    }

    public void setHiddenConversation(boolean hiddenConversation) {
        isHiddenConversation = hiddenConversation;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationBody() {
        return conversationBody;
    }

    public void setConversationBody(String conversationBody) {
        this.conversationBody = conversationBody;
    }

    public ArrayList<String> getArrConversationUserId() {
        return arrConversationUserId;
    }

    public void setArrConversationUserId(ArrayList<String> arrConversationUserId) {
        this.arrConversationUserId = arrConversationUserId;
    }
}
