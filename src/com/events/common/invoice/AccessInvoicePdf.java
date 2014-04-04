package com.events.common.invoice;

import com.events.bean.users.UserBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.Folder;
import com.events.common.Utility;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/4/14
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccessInvoicePdf {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

    public String getInvoicePdfLocation(UserBean userBean, String invoiceId){
        String invoiceFilePath = Constants.EMPTY;
        if(!Utility.isNullOrEmpty(invoiceId)) {


            String fileUploadLocation = applicationConfig.get(Constants.FILE_UPLOAD_LOCATION);

            String sFolderPath = fileUploadLocation + "/" + getUserFolderName(userBean,fileUploadLocation) ;

            invoiceFilePath = sFolderPath + "/" + invoiceId+ ".pdf";
        }
        return invoiceFilePath;
    }

    public String getUserFolderName(UserBean userBean,String fileUploadLocation ){

        Folder folder = new Folder();
        String sUserFolderName = folder.getFolderForUser(userBean, fileUploadLocation);

        return sUserFolderName;
    }
}
