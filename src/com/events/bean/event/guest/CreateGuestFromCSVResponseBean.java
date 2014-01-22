package com.events.bean.event.guest;

import com.events.common.Constants;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 1/22/14
 * Time: 12:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateGuestFromCSVResponseBean {
    private boolean isFileTooBig = false;
    private boolean isFileExists = false;
    private Integer iNumOfGuestGroups = 0;
    private String message = Constants.EMPTY;
    private List guestListEntries = null;

    public boolean isFileExists() {
        return isFileExists;
    }

    public void setFileExists(boolean fileExists) {
        isFileExists = fileExists;
    }

    public boolean isFileTooBig() {
        return isFileTooBig;
    }

    public void setFileTooBig(boolean fileTooBig) {
        isFileTooBig = fileTooBig;
    }

    public Integer getiNumOfGuestGroups() {
        return iNumOfGuestGroups;
    }

    public void setiNumOfGuestGroups(Integer iNumOfGuestGroups) {
        this.iNumOfGuestGroups = iNumOfGuestGroups;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getGuestListEntries() {
        return guestListEntries;
    }

    public void setGuestListEntries(List guestListEntries) {
        this.guestListEntries = guestListEntries;
    }
}
