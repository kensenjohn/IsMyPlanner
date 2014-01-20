package com.events.proc.upload;

import com.events.bean.upload.UploadRequestBean;
import com.events.bean.upload.UploadResponseBean;
import com.events.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 1/15/14
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcDisplayImage  extends HttpServlet {
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    /**
     * @see HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sImageID = ParseUtil.checkNull(request.getParameter("img"));

        if(!Utility.isNullOrEmpty(sImageID)){
            UploadRequestBean uploadRequestBean = new UploadRequestBean();
            uploadRequestBean.setUploadId(sImageID);

            UploadFile uploadFile = new UploadFile();
            UploadResponseBean uploadResponseBean = uploadFile.getUploadFileInfo(uploadRequestBean);

            if(uploadResponseBean!=null) {

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
