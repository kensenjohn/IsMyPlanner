package com.events.common.invoice;

import com.events.bean.invoice.InvoicePdfRequestBean;
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

    public String getInvoicePdfLocation(InvoicePdfRequestBean invoicePdfRequestBean ){
        String invoiceFilePath = Constants.EMPTY;
        if(invoicePdfRequestBean!=null ) {
            UserBean userBean = invoicePdfRequestBean.getUserBean();
            String invoiceId = invoicePdfRequestBean.getInvoiceId() ;
            if(userBean!=null && !Utility.isNullOrEmpty(invoiceId)) {
                String fileUploadLocation = applicationConfig.get(Constants.FILE_UPLOAD_LOCATION);

                invoiceFilePath = fileUploadLocation + "/" + getUserFolderName(userBean,fileUploadLocation) ;
            }
        }
        return invoiceFilePath;
    }

    public String getUserFolderName(UserBean userBean,String fileUploadLocation ){

        Folder folder = new Folder();
        String sUserFolderName = folder.getFolderForUser(userBean, fileUploadLocation);

        return sUserFolderName;
    }
}
